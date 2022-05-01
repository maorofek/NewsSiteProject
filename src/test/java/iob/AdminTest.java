package iob;

import iob.boundary.ActivityBoundary;
import iob.boundary.InstanceBoundary;
import iob.boundary.UserBoundary;
import org.junit.jupiter.api.Test;

import static iob.controller.ControllerConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminTest extends TestTemplate { // TODO: update to support new URL scheme

    // test deleting all activities
    @Test
    public void testDeleteAllActivities() {
        // TODO: add admin user params
//        createAndSaveNewActivity();
        restTemplate.delete(baseUrl + "/" + ADMIN_PATH + "/" + ACTIVITIES_PATH);
        ActivityBoundary[] activityBoundaries = restTemplate.getForObject(baseUrl + "/" + ADMIN_PATH + "/" + ACTIVITIES_PATH, ActivityBoundary[].class);
        assertThat(activityBoundaries).isNotNull();
        assertThat(activityBoundaries).isEmpty();
    }

    // test deleting all instances
    @Test
    public void testDeleteAllInstances() {
        // TODO: add admin user params
//        createAndSaveNewInstance();
//        restTemplate.delete(baseUrl + "/" + ADMIN_PATH + "/" + INSTANCES_PATH);
//        InstanceBoundary[] instanceBoundaries = restTemplate.getForObject(instancesUrl, InstanceBoundary[].class);
//        assertThat(instanceBoundaries).isNotNull();
//        assertThat(instanceBoundaries).isEmpty();
    }

    // test deleting all users
    @Test
    public void testDeleteAllUsers() {
        // TODO: add admin user params
//        createAndSaveNewUser();
//        restTemplate.delete(baseUrl + "/" + ADMIN_PATH + "/" + USERS_PATH);
//        UserBoundary[] userBoundaries = restTemplate.getForObject(baseUrl + "/" + ADMIN_PATH + "/" + USERS_PATH, UserBoundary[].class);
//        assertThat(userBoundaries).isNotNull();
//        assertThat(userBoundaries).isEmpty();
    }

    // test exporting all activities
    @Test
    public void testExportAllActivities() {
        // TODO: add admin user params
//        ActivityBoundary activityBoundary = createAndSaveNewActivity();
//        ActivityBoundary[] activityBoundaries = restTemplate.getForObject(baseUrl + "/" + ADMIN_PATH + "/" + ACTIVITIES_PATH, ActivityBoundary[].class);
//        assertThat(activityBoundaries).isNotNull();
//        assertThat(activityBoundaries).hasSize(1);
//        assertThat(activityBoundaries[0]).isEqualTo(activityBoundary);
    }

    // test exporting all users
    @Test
    public void testExportAllUsers() {
//        UserBoundary userBoundary = createAndSaveNewUser();
//        UserBoundary[] userBoundaries = restTemplate.getForObject(baseUrl + "/" + ADMIN_PATH + "/" + USERS_PATH, UserBoundary[].class);
//        assertThat(userBoundaries).isNotNull();
//        assertThat(userBoundaries).hasSize(1);
//        assertThat(userBoundaries[0]).isEqualTo(userBoundary);
    }
}
