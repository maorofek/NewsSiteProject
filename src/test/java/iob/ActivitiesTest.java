package iob;

import org.junit.jupiter.api.Test;

public class ActivitiesTest extends TestTemplate { // TODO: update to support new URL scheme

    // test invoking an instance activity
    @Test
    public void testActivityInvoking() {
//        createAndSaveNewUser("testUser@gmail.com", "PLAYER", "testUser", "anAvatarURL");
        createAndSaveNewActivity("testUser@gmail.com", "PLAYER", "testUser", "anAvatarURL");
    }
}
