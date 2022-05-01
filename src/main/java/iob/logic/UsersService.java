package iob.logic;

import iob.boundary.UserBoundary;

import java.util.List;

/**
 * @see <a href="https://drive.google.com/file/d/1McrGExlCJioxVlxf1VoxI4h58cen-4Tc/view">Specification</a>
 */
public interface UsersService {
    UserBoundary createUser(UserBoundary user);

    UserBoundary login(String userDomain, String userEmail);

    UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update);

    @Deprecated
    List<UserBoundary> getAllUsers();

    @Deprecated
    void deleteAllUsers(String userDomain, String userEmail);

}
