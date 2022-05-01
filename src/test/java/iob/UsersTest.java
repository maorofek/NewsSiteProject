package iob;

import iob.boundary.UserBoundary;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;


public class UsersTest extends TestTemplate { // TODO: update to support new URL scheme

    // test adding new user to the database
    @Test
    public void testCreateUser() {
//        createAndSaveNewUser();
    }

    // test login with existing user
    @Test
    public void testLogin() {
//        UserBoundary user = createAndSaveNewUser();
//
//        UserBoundary user2 = restTemplate.getForObject(
//                usersUrl + "/login/{userDomain}/{userEmail}",
//                UserBoundary.class,
//                user.getUserId().getDomain(),
//                user.getUserId().getEmail()
//        );
//
//        assertThat(user2).isNotNull();
//        assertThat(user2).isEqualTo(user);
    }

    // test login with non-existing user
    @Test
    public void testLoginWithNonExistingUser() {
        HttpClientErrorException thrown = catchThrowableOfType(
                () -> restTemplate.getForObject(
                        usersUrl + "/login/{userDomain}/{userEmail}",
                        UserBoundary.class,
                        "testDomain",
                        "nonExistingUser@gmail.com"
                ),
                HttpClientErrorException.class
        );
        assertThat(thrown).isNotNull();
        assertThat(thrown.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // test updating user
    @Test
    public void testUpdateUser() {
//        UserBoundary user = createAndSaveNewUser();
//
//        String newAvatarUrl = "newAvatarURL";
//        String newRole = "ADMIN";
//        user.setAvatar(newAvatarUrl);
//        user.setRole(newRole);
//
//        restTemplate.put(
//                usersUrl + "/{userDomain}/{userEmail}",
//                user,
//                user.getUserId().getDomain(),
//                user.getUserId().getEmail()
//        );
//
//        UserBoundary updatedUser = restTemplate.getForObject(
//                usersUrl + "/login/{userDomain}/{userEmail}",
//                UserBoundary.class,
//                user.getUserId().getDomain(),
//                user.getUserId().getEmail()
//        );

//        assertThat(updatedUser).isNotNull();
//        assertThat(updatedUser.getAvatar()).isEqualTo(newAvatarUrl);
//        assertThat(updatedUser.getRole()).isEqualTo(newRole);
//        assertThat(updatedUser).isEqualTo(user);
    }
}
