package iob.boundary;

import iob.boundary.inner.UserID;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBoundary {

    private UserID userId;
    private String role;
    private String username;
    private String avatar;

}