package iob.logic;

import iob.boundary.ActivityBoundary;
import iob.boundary.inner.ActivityID;
import iob.data.ActivityEntity;
import iob.data.InstanceEntity;
import iob.data.UserRole;
import iob.logic.dao.ActivitiesDao;
import iob.logic.dao.InstancesDao;
import iob.logic.dao.UsersDao;
import iob.util.ObjectConverter;
import iob.util.MiscUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JpaActivitiesService implements EnhancedActivitiesService {

    private final ActivitiesDao activitiesDao;
    private final UsersDao usersDao;            // for input validation
    private final InstancesDao instancesDao;    // for input validation
    private final ObjectConverter converter;
    private final Authorizer authorizer;

    @Value("${spring.application.name}")
    private String domain;

    @Autowired
    public JpaActivitiesService(
            ActivitiesDao activitiesDao,
            UsersDao usersDao,
            InstancesDao instancesDao,
            ObjectConverter converter,
            Authorizer authorizer
    ) {
        this.activitiesDao = activitiesDao;
        this.usersDao = usersDao;
        this.instancesDao = instancesDao;
        this.converter = converter;
        this.authorizer = authorizer;
    }

    // ***************************** Deprecated *****************************

    @Override
    @Deprecated
    public List<ActivityBoundary> getAllActivities() {
        throw new UnsupportedOperationException("Method not supported, use paginated method instead.");
    }

    @Override
    @Deprecated
    public void deleteAllActivities() {
        throw new UnsupportedOperationException("Method not supported, use paginated method instead.");
    }

    // ***************************************************************************

    @Override
    @Transactional
    public Object invokeActivity(ActivityBoundary activityBoundary) {
        if (activityBoundary.getInvokedBy() == null || activityBoundary.getInvokedBy().getUserId() == null) {
            throw new InvalidInputException("blank `invokedBy` field");
        }

        authorizer.authorize(
                activityBoundary.getInvokedBy().getUserId().getDomain(),
                activityBoundary.getInvokedBy().getUserId().getEmail(),
                UserRole.PLAYER
        );

        if (activityBoundary.getType() == null || activityBoundary.getType().isEmpty()) {
            throw new InvalidInputException("blank activity type");
        }
        usersDao
                .findById(converter.toEntity(activityBoundary.getInvokedBy().getUserId()))
                .orElseThrow(() -> new EntityNotFoundException(activityBoundary.getInvokedBy().getUserId().toString()));

        InstanceEntity instance = instancesDao
                .findById(converter.toEntity(activityBoundary.getInstance().getInstanceId()))
                .orElseThrow(() -> new EntityNotFoundException(activityBoundary.getInstance().getInstanceId().toString()));

        if (!instance.getActive())
            throw new InvalidInputException("Inactive instance was referenced!", instance.getInstanceId());

        if (activityBoundary.getActivityId() == null) {
            activityBoundary.setActivityId(new ActivityID());
        }
        activityBoundary.getActivityId().setId(MiscUtils.instance().getNewID());
        activityBoundary.getActivityId().setDomain(domain);
        activityBoundary.setCreatedTimestamp(new Date() /* now */);
        ActivityEntity entity = converter.toEntity(activityBoundary);

        return converter.toBoundary(activitiesDao.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityBoundary> getAllActivities(String requesterDomain, String requesterEmail, int size, int page) {
        authorizer.authorize(requesterDomain, requesterEmail, UserRole.ADMIN);

        return activitiesDao
                .findAll(PageRequest.of(page, size, Sort.Direction.DESC, "createdTimestamp", "activityId"))
                .getContent().stream()
                .map(converter::toBoundary)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAllActivities(String requesterDomain, String requesterEmail) {
        authorizer.authorize(requesterDomain, requesterEmail, UserRole.ADMIN);
        activitiesDao.deleteAll();
    }
}
