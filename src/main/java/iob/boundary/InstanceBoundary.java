package iob.boundary;

import iob.boundary.inner.CreatorBoundary;
import iob.boundary.inner.InstanceID;
import iob.boundary.inner.LocationBoundary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstanceBoundary {

    private InstanceID instanceId;
    private String type;
    private String name;
    private Boolean active;
    private Date createdTimestamp;
    private CreatorBoundary createdBy;
    private LocationBoundary location;
    private Map<String, Object> instanceAttributes;

}
