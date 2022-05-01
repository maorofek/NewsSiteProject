package iob.logic;

import iob.boundary.ActivityBoundary;

import java.util.List;

public interface EnhancedActivitiesService extends ActivitiesService {

    List<ActivityBoundary> getAllActivities(String requesterDomain, String requesterEmail, int page, int size);

    void deleteAllActivities(String requesterDomain, String requesterEmail);

}
