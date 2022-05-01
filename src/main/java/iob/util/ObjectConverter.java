package iob.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iob.boundary.ActivityBoundary;
import iob.boundary.InstanceBoundary;
import iob.boundary.UserBoundary;
import iob.boundary.inner.*;
import iob.data.ActivityEntity;
import iob.data.InstanceEntity;
import iob.data.UserEntity;
import iob.data.UserRole;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class ObjectConverter {

    private final String DELIMITER = "&";
    private final ObjectMapper mapper = new ObjectMapper();

    public UserID toUserBoundary(String userId) {
        String[] domain_email = userId.split(DELIMITER);
        return new UserID(domain_email[0], domain_email[1]);
    }

    public String toEntity(UserID userId) {
        return toEntity(userId.getDomain(), userId.getEmail());
    }

    public String toEntity(String strFieldA, String strFieldB) {
        return String.join(DELIMITER, strFieldA, strFieldB);
    }

    public UserBoundary toBoundary(UserEntity userEntity) {
        return new UserBoundary(
                toUserBoundary(userEntity.getId()),
                userEntity.getRole().toString(),
                userEntity.getUsername(),
                userEntity.getAvatar()
        );
    }

    public UserEntity toEntity(UserBoundary userBoundary) {
        return new UserEntity(
                toEntity(userBoundary.getUserId()),
                UserRole.valueOf(userBoundary.getRole()),
                userBoundary.getUsername(),
                userBoundary.getAvatar()
        );
    }

    public InstanceID toInstanceIdBoundary(String instanceId) {
        String[] domain_id = instanceId.split(DELIMITER);
        return new InstanceID(domain_id[0], domain_id[1]);
    }

    public String toEntity(InstanceID instanceId) {
        return toEntity(instanceId.getDomain(), instanceId.getId());
    }

    public String toEntity(CreatorBoundary creatorBoundary) {
        return toEntity("CreatorBoundary", toEntity(creatorBoundary.getUserId()));
    }

    public CreatorBoundary toCreatorBoundary(String creatorBoundary) {
        return new CreatorBoundary(toUserBoundary(creatorBoundary.substring(creatorBoundary.indexOf(DELIMITER) + 1)));
    }

    public LocationBoundary toLocationBoundary(String lat, String lng) {
        if (lat == null || lng == null) return null;
        return new LocationBoundary(Double.valueOf(lat), Double.valueOf(lng));
    }

    public InstanceBoundary toBoundary(InstanceEntity entity) {
        return new InstanceBoundary(
                toInstanceIdBoundary(entity.getInstanceId()),
                entity.getType(),
                entity.getName(),
                entity.getActive(),
                entity.getCreatedTimestamp(),
                toCreatorBoundary(entity.getCreatedBy()),
                toLocationBoundary(entity.getLat(), entity.getLng()),
                jsonStringToMap(entity.getInstanceAttributes())
        );
    }

    public InstanceEntity toEntity(InstanceBoundary instanceBoundary) {
        return new InstanceEntity(
                toEntity(instanceBoundary.getInstanceId()),
                instanceBoundary.getType(),
                instanceBoundary.getName(),
                instanceBoundary.getActive(),
                instanceBoundary.getCreatedTimestamp(),
                toEntity(instanceBoundary.getCreatedBy()),
                instanceBoundary.getLocation() != null && instanceBoundary.getLocation().getLat() != null
                        ? instanceBoundary.getLocation().getLat().toString()
                        : null,
                instanceBoundary.getLocation() != null && instanceBoundary.getLocation().getLng() != null
                        ? instanceBoundary.getLocation().getLng().toString()
                        : null,
                mapToJsonString(instanceBoundary.getInstanceAttributes())
        );
    }

    public ActivityID toBoundary(String activityID) {
        String[] domain_id = activityID.split(DELIMITER);
        return new ActivityID(domain_id[0], domain_id[1]);
    }

    public String toEntity(ActivityID activityID) {
        return toEntity(activityID.getDomain(), activityID.getId());
    }

    public ActivityInstanceBoundary toActivityInstanceBoundary(String activityInstance) {
        return new ActivityInstanceBoundary(toInstanceIdBoundary(activityInstance));
    }

    public String toEntity(ActivityInstanceBoundary aib) {
        return toEntity(aib.getInstanceId().getDomain(), aib.getInstanceId().getId());
    }

    public ActivityBoundary toBoundary(ActivityEntity entity) {
        return ActivityBoundary.builder()
                .activityId(toBoundary(entity.getActivityId()))
                .type(entity.getType())
                .createdTimestamp(entity.getCreatedTimestamp())
                .instance(toActivityInstanceBoundary(entity.getInstance()))
                .invokedBy(new InvokerBoundary(toUserBoundary(entity.getInvokedBy())))
                .activityAttributes(jsonStringToMap(entity.getActivityAttributes()))
                .build();
    }

    public ActivityEntity toEntity(ActivityBoundary boundary) {
        return new ActivityEntity(
                toEntity(boundary.getActivityId()),
                boundary.getType(),
                toEntity(boundary.getInstance()),
                boundary.getCreatedTimestamp(),
                toEntity(boundary.getInvokedBy().getUserId()),
                mapToJsonString(boundary.getActivityAttributes())
        );
    }

    public Map<String, Object> jsonStringToMap(String json) {
        if (json == null) return null;
        try {
            return mapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            throw new ConversionException(e);
        }
    }

    public String mapToJsonString(Map<String, Object> attributesMap) {
        if (attributesMap == null) return null;
        try {
            return mapper.writeValueAsString(attributesMap);
        } catch (JsonProcessingException e) {
            throw new ConversionException(e);
        }
    }
}
