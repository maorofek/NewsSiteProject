package iob.logic;

import iob.boundary.InstanceBoundary;

import java.util.List;

public interface EnhancedInstancesService extends InstancesService {

    InstanceBoundary updateInstance(
            String requesterDomain,
            String requesterEmail,
            String instanceDomain,
            String instanceId,
            InstanceBoundary update
    );

    InstanceBoundary getSpecificInstance(
            String requesterDomain,
            String requesterEmail,
            String instanceDomain,
            String instanceId
    );

    List<InstanceBoundary> getAllInstances(
            String requesterDomain,
            String requesterEmail,
            int size,
            int page
    );

    void deleteAllInstances(
            String requesterDomain,
            String requesterEmail
    );

}
