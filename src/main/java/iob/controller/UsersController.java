package iob.controller;

import iob.boundary.NewUserBoundary;
import iob.boundary.UserBoundary;
import iob.boundary.inner.UserID;
import iob.logic.EnhancedUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import static iob.controller.ControllerConstants.IOB_PATH;
import static iob.controller.ControllerConstants.USERS_PATH;

/**
 * @see <a href="https://drive.google.com/file/d/1IEDICBpfOxERCd3FKGwqzGWsJwF2-Ezi/view">Specification</a>
 */
@RestController
public class UsersController {
    private final EnhancedUsersService usersService;

    @Autowired
    public UsersController(EnhancedUsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/" + IOB_PATH +
                    "/" + USERS_PATH,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public UserBoundary createNewUser(
            @RequestBody NewUserBoundary userDetails
    ) {
        return usersService.createUser(new UserBoundary(
                new UserID("2020b.elazar.fine", userDetails.getEmail()),
                userDetails.getRole(),
                userDetails.getUsername(),
                userDetails.getAvatar()
        ));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/" + IOB_PATH +
                    "/" + USERS_PATH +
                    "/login/{userDomain}/{userEmail}",
            produces = APPLICATION_JSON_VALUE
    )
    public UserBoundary login(
            @PathVariable("userDomain") String userDomain,
            @PathVariable("userEmail") String userEmail
    ) {
        return usersService.login(userDomain, userEmail);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/" + IOB_PATH +
                    "/" + USERS_PATH +
                    "/{userDomain}/{userEmail}",
            consumes = APPLICATION_JSON_VALUE
    )
    public void updateUser(
            @RequestBody UserBoundary userDetails,
            @PathVariable("userDomain") String userDomain,
            @PathVariable("userEmail") String userEmail
    ) {
        usersService.updateUser(userDomain, userEmail, userDetails);
    }
}
