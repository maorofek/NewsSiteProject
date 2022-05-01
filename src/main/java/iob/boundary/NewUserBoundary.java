package iob.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserBoundary {

    private String email;
    private String role;
    private String username;
    private String avatar;

}
