package iob.data;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InstanceEntity {

    @Id
    private String instanceId;
    private String type;
    private String name;
    private Boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTimestamp;
    private String createdBy;
    private String lat;
    private String lng;

    @Lob
    private String instanceAttributes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InstanceEntity that = (InstanceEntity) o;
        return instanceId != null && Objects.equals(instanceId, that.instanceId);
    }

    @Override
    public int hashCode() {
        int result = instanceId != null ? instanceId.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (createdTimestamp != null ? createdTimestamp.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        result = 31 * result + (instanceAttributes != null ? instanceAttributes.hashCode() : 0);
        return result;
    }
}
