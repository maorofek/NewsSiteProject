package iob.logic;

import iob.data.UserEntity;
import iob.data.UserRole;
import iob.logic.dao.UsersDao;
import iob.util.ObjectConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class Authorizer {

    private final UsersDao usersDao;
    private final ObjectConverter converter;

    @Autowired
    public Authorizer(UsersDao usersDao, ObjectConverter converter) {
        this.usersDao = usersDao;
        this.converter = converter;
    }

    @Transactional(readOnly = true)
    public UserEntity authorize(String requesterDomain, String requesterEmail, UserRole... authorizedRoles) {
        UserEntity requester = usersDao
                .findById(converter.toEntity(requesterDomain, requesterEmail))
                .orElseThrow(() -> new EntityNotFoundException("no such user: ", requesterDomain, requesterEmail));

        for (UserRole authorizedRole : authorizedRoles) {
            if (requester.getRole() == authorizedRole) {
                return requester;
            }
        }

        throw new UnauthorizedException(
                String.format(
                        "Role mismatch for user [ %s/%s ]: one of these roles %s is required, while user is [ %s ]",
                        requesterDomain,
                        requesterEmail,
                        Arrays.toString(authorizedRoles),
                        requester.getRole()
                ));

    }

}
