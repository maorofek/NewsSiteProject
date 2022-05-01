package iob.data;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ActivityEntity {

    @Id
    private String activityId;
    private String type;
    private String instance;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTimestamp;
    private String invokedBy;

    @Lob
    private String activityAttributes;

}
