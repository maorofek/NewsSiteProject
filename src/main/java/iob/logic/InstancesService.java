package iob.logic;

import iob.boundary.InstanceBoundary;

import java.util.List;

/**
 * @see <a href="https://drive.google.com/file/d/1McrGExlCJioxVlxf1VoxI4h58cen-4Tc/view">Specification</a>
 */
public interface InstancesService {

    InstanceBoundary createInstance(InstanceBoundary instanceBoundary);

    @Deprecated
    InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update);

    @Deprecated
    InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId);

    @Deprecated
    List<InstanceBoundary> getAllInstances();

    @Deprecated
    void deleteAllInstances();

}
