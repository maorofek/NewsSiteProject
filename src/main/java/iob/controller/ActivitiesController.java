package iob.controller;

import iob.boundary.ActivityBoundary;
import iob.logic.EnhancedActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static iob.controller.ControllerConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @see <a href="https://drive.google.com/file/d/1IEDICBpfOxERCd3FKGwqzGWsJwF2-Ezi/view">Specification</a>
 */
@RestController
public class ActivitiesController {
    private final EnhancedActivitiesService activitiesService;

    @Autowired
    public ActivitiesController(EnhancedActivitiesService activitiesService) {
        this.activitiesService = activitiesService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/" + IOB_PATH +
                    "/" + ACTIVITIES_PATH,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public Object invokeAnInstanceActivity(
            @RequestBody ActivityBoundary activityDetails
    ) {
        return activitiesService.invokeActivity(activityDetails);
    }
}
