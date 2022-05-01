package iob.logic;

import iob.boundary.UserBoundary;

import java.util.List;

public interface EnhancedUsersService extends UsersService {

    List<UserBoundary> getAllUsers(String requesterDomain, String requesterEmail, int page, int size);

    void deleteAllUsers(String requesterDomain, String requesterEmail);


    // TODO: Add search functions as described here:
    // https://drive.google.com/file/d/1PnDtyr2uDuNh6P9XWJpFFcqa6RmSme-Q/view

}
