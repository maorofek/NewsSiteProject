package iob.logic;

import iob.boundary.ActivityBoundary;

import java.util.List;

/**
 * @see <a href="https://drive.google.com/file/d/1McrGExlCJioxVlxf1VoxI4h58cen-4Tc/view">Specification</a>
 */
public interface ActivitiesService {

    Object invokeActivity(ActivityBoundary activity);

    @Deprecated
    List<ActivityBoundary> getAllActivities();

    @Deprecated
    void deleteAllActivities();

}
