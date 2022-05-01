package iob;

import iob.boundary.ActivityBoundary;
import iob.boundary.InstanceBoundary;
import iob.boundary.NewUserBoundary;
import iob.boundary.UserBoundary;
import iob.boundary.inner.ActivityInstanceBoundary;
import iob.boundary.inner.CreatorBoundary;
import iob.boundary.inner.InvokerBoundary;
import iob.data.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

import static iob.controller.ControllerConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class TestTemplate { // TODO: update to support new URL scheme
    protected int port;
    protected String baseUrl, activitiesUrl, instancesUrl, usersUrl;
    protected UserBoundary adminUser;
    protected RestTemplate restTemplate = new RestTemplate();

    @LocalServerPort
    public void setPort(int port) {
        this.port = port;
    }

    @PostConstruct
    public void init() {
        baseUrl = "http://localhost:" + port + "/" + IOB_PATH;
        activitiesUrl = baseUrl + "/" + ACTIVITIES_PATH;
        instancesUrl = baseUrl + "/" + INSTANCES_PATH;
        usersUrl = baseUrl + "/" + USERS_PATH;
    }

//    protected UserBoundary createAndSaveNewUser() {
    protected UserBoundary createAndSaveNewUser(String email, String role, String username, String avatar) {
//        String email = "testUser@gmail.com", role = "PLAYER", username = "testUser", avatar = "anAvatarURL";

        UserBoundary newUser = restTemplate.postForObject(
                baseUrl + "/" + USERS_PATH,
                new NewUserBoundary(email, role, username, avatar),
                UserBoundary.class
        );

        assertThat(newUser).isNotNull();
        assertThat(newUser.getUserId()).isNotNull();
        assertThat(newUser.getUserId().getDomain()).isNotNull();
        assertThat(newUser.getUserId().getEmail()).isEqualTo(email);
        assertThat(newUser.getRole()).isEqualTo(role);
        assertThat(newUser.getUsername()).isEqualTo(username);
        assertThat(newUser.getAvatar()).isEqualTo(avatar);

        return newUser;
    }

//    protected InstanceBoundary createAndSaveNewInstance() {
    protected InstanceBoundary createAndSaveNewInstance(String email, String role, String username, String avatar) {
        InstanceBoundary newInstance = new InstanceBoundary();
        newInstance.setName("testInstance");
        newInstance.setType("testType");
        Map<String, Object> attributesMap = new HashMap<>();
        attributesMap.put("testKey", "testValue");
        newInstance.setInstanceAttributes(attributesMap);
        newInstance.setCreatedBy(new CreatorBoundary(createAndSaveNewUser(email, role, username, avatar).getUserId()));
        InstanceBoundary savedInstance = restTemplate.postForObject(
                instancesUrl,
                newInstance,
                InstanceBoundary.class
        );

        assertThat(savedInstance).isNotNull();
        assertThat(savedInstance.getInstanceId()).isNotNull();
        assertThat(savedInstance.getInstanceId().getDomain()).isNotNull();
        assertThat(savedInstance.getInstanceId().getId()).isNotNull();
        assertThat(savedInstance.getName()).isEqualTo(newInstance.getName());
        assertThat(savedInstance.getType()).isEqualTo(newInstance.getType());
        assertThat(savedInstance.getInstanceAttributes()).isEqualTo(newInstance.getInstanceAttributes());
        assertThat(savedInstance.getCreatedBy()).isEqualTo(newInstance.getCreatedBy());

        return savedInstance;
    }

//    protected ActivityBoundary createAndSaveNewActivity() {
    protected ActivityBoundary createAndSaveNewActivity(String email, String role, String username, String avatar) {
        Map<String, Object> attributesMap = new HashMap<>();
        attributesMap.put("testKey", "testValue");
        InstanceBoundary instanceBoundary = createAndSaveNewInstance(email, role, username, avatar);
        ActivityBoundary activityBoundary = new ActivityBoundary();
        activityBoundary.setType("Test Type");
        activityBoundary.setInvokedBy(new InvokerBoundary(instanceBoundary.getCreatedBy().getUserId()));
        activityBoundary.setActivityAttributes(attributesMap);
        activityBoundary.setInstance(new ActivityInstanceBoundary(instanceBoundary.getInstanceId()));
        ActivityBoundary savedActivity = restTemplate.postForObject(
                activitiesUrl,
                activityBoundary,
                ActivityBoundary.class
        );
        assertThat(savedActivity).isNotNull();
        assertThat(savedActivity.getActivityId()).isNotNull();
        assertThat(savedActivity.getActivityId().getDomain()).isNotNull();
        assertThat(savedActivity.getActivityId().getId()).isNotNull();
        assertThat(savedActivity.getType()).isEqualTo(activityBoundary.getType());
        assertThat(savedActivity.getActivityAttributes()).isEqualTo(activityBoundary.getActivityAttributes());
        assertThat(savedActivity.getInvokedBy()).isEqualTo(activityBoundary.getInvokedBy());

        return savedActivity;
    }

    // make sure that after each test, the database is empty
    @AfterEach
    public void tearDown() {
        adminUser = createAndSaveNewUser("adminUser@gmail.com", "ADMIN", "adminUser", "anAvatarURL");
        String adminDomain = adminUser.getUserId().getDomain();
        String adminEmail = adminUser.getUserId().getEmail();
        restTemplate.delete(baseUrl + "/" + ADMIN_PATH + "/" + ACTIVITIES_PATH + "?userDomain=" + adminDomain + "&userEmail=" + adminEmail);
        restTemplate.delete(baseUrl + "/" + ADMIN_PATH + "/" + INSTANCES_PATH + "?userDomain=" + adminDomain + "&userEmail=" + adminEmail);
        restTemplate.delete(baseUrl + "/" + ADMIN_PATH + "/" + USERS_PATH + "?userDomain=" + adminDomain + "&userEmail=" + adminEmail);
    }

}
