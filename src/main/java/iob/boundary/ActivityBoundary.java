package iob.boundary;

import iob.boundary.inner.ActivityID;
import iob.boundary.inner.ActivityInstanceBoundary;
import iob.boundary.inner.InvokerBoundary;
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
public class ActivityBoundary {

    private ActivityID activityId;
    private String type;
    private ActivityInstanceBoundary instance;
    private Date createdTimestamp;
    private InvokerBoundary invokedBy;
    private Map<String, Object> activityAttributes;

}
