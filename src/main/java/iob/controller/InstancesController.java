package iob.controller;

import iob.boundary.*;
import iob.logic.EnhancedInstancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static iob.controller.ControllerConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @see <a href="https://drive.google.com/file/d/1IEDICBpfOxERCd3FKGwqzGWsJwF2-Ezi/view">Specification</a>
 */
@RestController
public class InstancesController {
    private final EnhancedInstancesService instancesService;

    @Autowired
    public InstancesController(EnhancedInstancesService instancesService) {
        this.instancesService = instancesService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/" + IOB_PATH +
                    "/" + INSTANCES_PATH,
            produces = APPLICATION_JSON_VALUE
    )
    public InstanceBoundary[] getAllInstances(
            @RequestParam(name = "userDomain") String userDomain,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "page") int page
    ) {
        return instancesService.getAllInstances(userDomain, userEmail, size, page).toArray(new InstanceBoundary[0]);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/" + IOB_PATH +
                    "/" + INSTANCES_PATH +
                    "/{instanceDomain}/{instanceId}",
            produces = APPLICATION_JSON_VALUE
    )
    public InstanceBoundary getInstance(
            @PathVariable("instanceDomain") String instanceDomain,
            @PathVariable("instanceId") String instanceId,
            @RequestParam(name = "userDomain") String userDomain,
            @RequestParam(name = "userEmail") String userEmail
    ) {
        return instancesService.getSpecificInstance(userDomain, userEmail, instanceDomain, instanceId);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/" + IOB_PATH +
                    "/" + INSTANCES_PATH +
                    "/{instanceDomain}/{instanceId}",
            consumes = APPLICATION_JSON_VALUE
    )
    public void updateInstance(
            @RequestBody InstanceBoundary instanceDetails,
            @PathVariable("instanceDomain") String instanceDomain,
            @PathVariable("instanceId") String instanceId,
            @RequestParam(name = "userDomain") String userDomain,
            @RequestParam(name = "userEmail") String userEmail
    ) {
        instancesService.updateInstance(userDomain, userEmail, instanceDomain, instanceId, instanceDetails);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/" + IOB_PATH +
                    "/" + INSTANCES_PATH,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public InstanceBoundary createInstance(
            @RequestBody InstanceBoundary instanceDetails
    ) {
        return instancesService.createInstance(instanceDetails);
    }

    // TODO: Add search functions as described here:
    // https://drive.google.com/file/d/1PnDtyr2uDuNh6P9XWJpFFcqa6RmSme-Q/view

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/" + IOB_PATH +
                    "/" + INSTANCES_PATH +
                    "/" + SEARCH_PATH +
                    "/byName" +
                    "/{name}",
            produces = APPLICATION_JSON_VALUE
    )
    public InstanceBoundary[] getInstancesByName(
            @PathVariable("name") String instanceName,
            @RequestParam(name = "userDomain") String userDomain,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "page") int page
    ) {
        throw new UnsupportedOperationException("TODO");
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/" + IOB_PATH +
                    "/" + INSTANCES_PATH +
                    "/" + SEARCH_PATH +
                    "/byType" +
                    "/{type}",
            produces = APPLICATION_JSON_VALUE
    )
    public InstanceBoundary[] getInstancesByType(
            @PathVariable("type") String instanceType,
            @RequestParam(name = "userDomain") String userDomain,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "page") int page
    ) {
        throw new UnsupportedOperationException("TODO");
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/" + IOB_PATH +
                    "/" + INSTANCES_PATH +
                    "/" + SEARCH_PATH +
                    "/near/{lat}/{lng}/{distance}",
            produces = APPLICATION_JSON_VALUE
    )
    public InstanceBoundary[] getInstancesByLocation(
            @PathVariable("lat") String instanceLat,
            @PathVariable("lng") String instanceLng,
            @PathVariable("distance") String instanceDistance,
            @RequestParam(name = "userDomain") String userDomain,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "page") int page
    ) {
        throw new UnsupportedOperationException("TODO");
    }
}
