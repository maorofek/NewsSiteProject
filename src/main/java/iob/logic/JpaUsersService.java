package iob.logic;

import iob.boundary.UserBoundary;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.logic.dao.UsersDao;
import iob.util.ObjectConverter;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JpaUsersService implements EnhancedUsersService {

    private final UsersDao usersDao;
    private final ObjectConverter converter;
    private final Authorizer authorizer;


    @Value("${spring.application.name}")
    private String domain;

    @Autowired
    public JpaUsersService(UsersDao usersDao, ObjectConverter converter, Authorizer authorizer) {
        this.usersDao = usersDao;
        this.converter = converter;
        this.authorizer = authorizer;
    }

    // ***************************** Deprecated **********************************

    @Override
    @Deprecated
    public List<UserBoundary> getAllUsers() {
        throw new UnsupportedOperationException("Method not supported, use paginated method instead.");
    }

//    @Override
    @Deprecated
    public void deleteAllUsers() {
        throw new UnsupportedOperationException("Method not supported, use paginated method instead.");
    }

    // ***************************************************************************

    @Override
    @Transactional
    public UserBoundary createUser(UserBoundary userBoundary) {
        if (!EmailValidator.getInstance().isValid(userBoundary.getUserId().getEmail())) {
            throw new InvalidInputException("Invalid email: " + userBoundary.getUserId().getEmail());
        }
        if (userBoundary.getUsername() == null || userBoundary.getUsername().isEmpty()) {
            throw new InvalidInputException("blank username");
        }
        if (userBoundary.getAvatar() == null || userBoundary.getAvatar().isEmpty()) {
            throw new InvalidInputException("blank avatar");
        }

        userBoundary.getUserId().setDomain(domain);
        return converter.toBoundary(usersDao.save(converter.toEntity(userBoundary)));
    }

    @Override
    @Transactional(readOnly = true)
    public UserBoundary login(String userDomain, String userEmail) {
        return converter.toBoundary(
                usersDao
                        .findById(converter.toEntity(userDomain, userEmail))
                        .orElseThrow(() -> new EntityNotFoundException("no such user", userDomain, userEmail))
        );
    }

    @Override
    @Transactional
    public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
        if (update.getUserId() != null && (!update.getUserId().getDomain().equals(userDomain) || !update.getUserId().getEmail().equals(userEmail))) {
            throw new InvalidInputException("Updating a user's domain/email is forbidden");
        }

        UserEntity user = usersDao
                .findById(converter.toEntity(userDomain, userEmail))
                .orElseThrow(() -> new EntityNotFoundException("no such user", userDomain, userEmail));

        if (update.getAvatar() != null) {
            user.setAvatar(update.getAvatar());
        }
        if (update.getRole() != null) {
            user.setRole(UserRole.valueOf((update.getRole())));
        }
        if (update.getUsername() != null) {
            user.setUsername(update.getUsername());
        }

        return converter.toBoundary(usersDao.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserBoundary> getAllUsers(String requesterDomain, String requesterEmail, int size, int page) {
        authorizer.authorize(requesterDomain, requesterEmail, UserRole.ADMIN);

        return usersDao
                .findAll(PageRequest.of(page, size, Sort.Direction.DESC, "id", "username"))
                .getContent().stream()
                .map(converter::toBoundary)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAllUsers(String requesterDomain, String requesterEmail) {
        authorizer.authorize(requesterDomain, requesterEmail, UserRole.ADMIN);
        usersDao.deleteAll();
    }

}
