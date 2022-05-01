package iob.logic;

import iob.boundary.InstanceBoundary;
import iob.boundary.inner.InstanceID;
import iob.data.InstanceEntity;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.logic.dao.InstancesDao;
import iob.util.ObjectConverter;
import iob.util.MiscUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JpaInstancesService implements EnhancedInstancesService {

    private final InstancesDao instancesDao;
    private final ObjectConverter converter;
    private final Authorizer authorizer;


    @Value("${spring.application.name}")
    private String domain;

    @Autowired
    public JpaInstancesService(InstancesDao instancesDao, ObjectConverter converter, Authorizer authorizer) {
        this.instancesDao = instancesDao;
        this.converter = converter;
        this.authorizer = authorizer;
    }

    // ***************************** Deprecated ***************************

    @Override
    @Deprecated
    public InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update) {
        throw new UnsupportedOperationException("Method not supported, use paginated method instead.");
    }

    @Override
    @Deprecated
    public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId) {
        throw new UnsupportedOperationException("Method not supported, use paginated method instead.");
    }

    @Override
    @Deprecated
    public List<InstanceBoundary> getAllInstances() {
        throw new UnsupportedOperationException("Method not supported, use paginated method instead.");
    }

    @Override
    @Deprecated
    public void deleteAllInstances() {
        throw new UnsupportedOperationException("Method not supported, use paginated method instead.");
    }

    // ***************************************************************************

    @Override
    @Transactional
    public InstanceBoundary createInstance(InstanceBoundary instanceBoundary) {
        if (instanceBoundary.getName() == null || instanceBoundary.getName().isEmpty()) {
            throw new InvalidInputException("blank instance name");
        }
        if (instanceBoundary.getType() == null || instanceBoundary.getType().isEmpty()) {
            throw new InvalidInputException("blank instance type");
        }
        if (instanceBoundary.getCreatedBy() == null || instanceBoundary.getCreatedBy().getUserId() == null) {
            throw new InvalidInputException("blank `createdBy` field");
        }

        authorizer.authorize(
                instanceBoundary.getCreatedBy().getUserId().getDomain(),
                instanceBoundary.getCreatedBy().getUserId().getEmail(),
                UserRole.MANAGER
        );

        if (instanceBoundary.getInstanceId() == null) {
            instanceBoundary.setInstanceId(new InstanceID());
        }
        instanceBoundary.getInstanceId().setId(MiscUtils.instance().getNewID());
        instanceBoundary.getInstanceId().setDomain(domain);
        instanceBoundary.setCreatedTimestamp(new Date() /* now */);

        InstanceEntity entity = converter.toEntity(instanceBoundary);

        return converter.toBoundary(instancesDao.save(entity));
    }

    @Override
    @Transactional
    public InstanceBoundary updateInstance(String requesterDomain, String requesterEmail, String instanceDomain, String instanceId, InstanceBoundary update) {
        authorizer.authorize(requesterDomain, requesterEmail, UserRole.MANAGER);

        if (
                update.getInstanceId() != null
                        && (!update.getInstanceId().getDomain().equals(instanceDomain)
                        || !update.getInstanceId().getId().equals(instanceId))
        ) {
            throw new InvalidInputException("Updating an instance's domain/email is forbidden");
        }


        InstanceEntity entity = instancesDao
                .findById(converter.toEntity(instanceDomain, instanceId))
                .orElseThrow(() -> new EntityNotFoundException("no such instance", instanceDomain, instanceId));

        if (
                !converter.toEntity(update.getCreatedBy()).equals(entity.getCreatedBy())
                        || !update.getCreatedTimestamp().equals(entity.getCreatedTimestamp())
        ) {
            throw new InvalidInputException("Updating an instance's creation attributes is forbidden");
        }

        if (update.getName() != null) {
            entity.setName(update.getName());
        }
        if (update.getType() != null) {
            entity.setType(update.getType());
        }
        if (update.getActive() != null) {
            entity.setActive(update.getActive());
        }
        if (update.getLocation() != null) {
            if (update.getLocation().getLat() != null) {
                entity.setLat(update.getLocation().getLat().toString());
            }
            if (update.getLocation().getLng() != null) {
                entity.setLng(update.getLocation().getLng().toString());
            }
        }
        if (update.getInstanceAttributes() != null) {
            Map<String, Object> attrs = converter.jsonStringToMap(entity.getInstanceAttributes());
            if (attrs == null) attrs = new HashMap<>();
            attrs.putAll(update.getInstanceAttributes());
            entity.setInstanceAttributes(converter.mapToJsonString(attrs));
        }

        return converter.toBoundary(instancesDao.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public InstanceBoundary getSpecificInstance(
            String requesterDomain,
            String requesterEmail,
            String instanceDomain,
            String instanceId
    ) {
        UserEntity authorized = authorizer.authorize(requesterDomain, requesterEmail, UserRole.MANAGER, UserRole.PLAYER);

        Optional<InstanceEntity> optionalInstance =
                authorized.getRole() == UserRole.MANAGER
                        ? instancesDao.findById(converter.toEntity(instanceDomain, instanceId))
                        : instancesDao.findByInstanceIdAndActiveIsTrue(converter.toEntity(instanceDomain, instanceId));

        return converter.toBoundary(
                optionalInstance.orElseThrow(() -> new EntityNotFoundException("no such instance", instanceDomain, instanceId))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstanceBoundary> getAllInstances(String requesterDomain, String requesterEmail, int page, int size) {
        UserEntity authorizedRequester = authorizer.authorize(
                requesterDomain,
                requesterEmail,
                UserRole.PLAYER,
                UserRole.MANAGER
        );

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdTimestamp", "instanceId");

        List<InstanceEntity> instances =
                authorizedRequester.getRole() == UserRole.MANAGER
                        ? instancesDao.findAll(pageable).getContent()
                        : instancesDao.findAllByActiveIsTrue(pageable);

        return instances.stream()
                .map(converter::toBoundary)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllInstances(String requesterDomain, String requesterEmail) {
        authorizer.authorize(requesterDomain, requesterEmail, UserRole.ADMIN);
        instancesDao.deleteAll();
    }
}
