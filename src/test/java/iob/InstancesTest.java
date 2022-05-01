package iob;

import iob.boundary.InstanceBoundary;
import iob.boundary.inner.LocationBoundary;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class InstancesTest extends TestTemplate { // TODO: update to support new URL scheme

    // test creating new instance
    @Test
    public void testCreateInstance() {
        // TODO: add some user params
//        createAndSaveNewInstance();
    }

    // test getting all instances
    @Test
    public void testGetAllInstances() {
        // TODO: add some user params
//        InstanceBoundary instanceBoundary = createAndSaveNewInstance();
//        InstanceBoundary[] allInstances = restTemplate.getForObject(instancesUrl, InstanceBoundary[].class);
//        assertThat(allInstances).isNotNull();
//        assertThat(allInstances.length).isEqualTo(1);
//        assertThat(allInstances[0]).isEqualTo(instanceBoundary);
    }

    // test getting instance by id
    @Test
    public void testGetInstanceById() {
        // TODO: add some user params
//        InstanceBoundary instanceBoundary = createAndSaveNewInstance();
//        InstanceBoundary instance = restTemplate.getForObject(
//                instancesUrl + "/{instanceDomain}/{instanceId}",
//                InstanceBoundary.class,
//                instanceBoundary.getInstanceId().getDomain(),
//                instanceBoundary.getInstanceId().getId());
//
//        assertThat(instance).isNotNull();
//        assertThat(instance).isEqualTo(instanceBoundary);

    }

    // test updating instance
    @Test
    public void testUpdateInstance() {
        // TODO: add some user params
//        InstanceBoundary update = createAndSaveNewInstance();
//
//        LocationBoundary location = new LocationBoundary(42.0, 13.37);
//        update.setLocation(location);
//        Map<String, Object> updatedAttrMap = new HashMap<>();
//        updatedAttrMap.put("attr1", "updatedAttr1");
//        update.setInstanceAttributes(updatedAttrMap);
//
//        restTemplate.put(
//                instancesUrl + "/{instanceDomain}/{instanceId}",
//                update,
//                update.getInstanceId().getDomain(),
//                update.getInstanceId().getId()
//        );
//
//        InstanceBoundary updated = restTemplate.getForObject(
//                instancesUrl + "/{instanceDomain}/{instanceId}",
//                InstanceBoundary.class,
//                update.getInstanceId().getDomain(),
//                update.getInstanceId().getId()
//        );
//        assertThat(updated).isNotNull();
//        assertThat(updated.getLocation()).isEqualTo(location);
//        assertThat(updated.getInstanceAttributes()).containsAllEntriesOf(update.getInstanceAttributes());
    }

}
