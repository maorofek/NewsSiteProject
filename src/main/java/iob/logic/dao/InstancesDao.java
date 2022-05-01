package iob.logic.dao;

import iob.data.InstanceEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InstancesDao extends PagingAndSortingRepository<InstanceEntity, String> {

    List<InstanceEntity> findAllByActiveIsTrue(Pageable pageable);

    Optional<InstanceEntity> findByInstanceIdAndActiveIsTrue(@Param("instanceId") String instanceId);


    // TODO: Add search functions as described here:
    // https://drive.google.com/file/d/1PnDtyr2uDuNh6P9XWJpFFcqa6RmSme-Q/view
}