package iob.controller;

import iob.boundary.*;
import iob.logic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static iob.controller.ControllerConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @see <a href="https://drive.google.com/file/d/1IEDICBpfOxERCd3FKGwqzGWsJwF2-Ezi/view">Specification</a>
 */
@RestController
public class AdminController {
    private final EnhancedUsersService usersService;
    private final EnhancedInstancesService instancesService;
    private final EnhancedActivitiesService activitiesService;

    @Autowired
    public AdminController(
            EnhancedUsersService usersService,
            EnhancedInstancesService instancesService,
            EnhancedActivitiesService activitiesService
    ) {
        this.usersService = usersService;
        this.instancesService = instancesService;
        this.activitiesService = activitiesService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/" + IOB_PATH +
                    "/" + ADMIN_PATH +
                    "/" + USERS_PATH,
            produces = APPLICATION_JSON_VALUE
    )
    public UserBoundary[] exportAllUsers(
            @RequestParam(name = "userDomain") String userDomain,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "page") int page
    ) {
        return usersService.getAllUsers(userDomain, userEmail, size, page)
                .toArray(new UserBoundary[0]);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/" + IOB_PATH +
                    "/" + ADMIN_PATH +
                    "/" + ACTIVITIES_PATH,
            produces = APPLICATION_JSON_VALUE
    )
    public ActivityBoundary[] exportAllActivities(
            @RequestParam(name = "userDomain") String userDomain,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "page") int page
    ) {
        return activitiesService.getAllActivities(userDomain, userEmail, size, page)
                .toArray(new ActivityBoundary[0]);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/" + IOB_PATH +
                    "/" + ADMIN_PATH +
                    "/" + USERS_PATH
    )
    public void deleteAllUsers(
            @RequestParam(name = "userDomain") String userDomain,
            @RequestParam(name = "userEmail") String userEmail
    ) {
        usersService.deleteAllUsers(userDomain, userEmail);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/" + IOB_PATH +
                    "/" + ADMIN_PATH +
                    "/" + INSTANCES_PATH
    )
    public void deleteAllInstances(
            @RequestParam(name = "userDomain") String userDomain,
            @RequestParam(name = "userEmail") String userEmail
    ) {
        instancesService.deleteAllInstances(userDomain, userEmail);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/" + IOB_PATH +
                    "/" + ADMIN_PATH +
                    "/" + ACTIVITIES_PATH
    )
    public void deleteAllActivities(
            @RequestParam(name = "userDomain") String userDomain,
            @RequestParam(name = "userEmail") String userEmail
    ) {
        activitiesService.deleteAllActivities(userDomain, userEmail);
    }
}
