package ro.teamnet.scheduler.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.scheduler.domain.TimeInterval;
import ro.teamnet.scheduler.enums.TimeUnitCode;

import java.util.List;

/**
 * Spring Data JPA repository for the TimeInterval entity.
 */
public interface TimeIntervalRepository extends AppRepository<TimeInterval, Long> {

    @Query("select timeInterval from TimeInterval timeInterval  where timeInterval.id =:id")
    TimeInterval findOneWithEagerRelationships(@Param("id") Long id);


    @Override
    @Query("select timeInterval from TimeInterval timeInterval left join fetch timeInterval.timeUnit where timeInterval.id =:id")
    TimeInterval findOne(@Param("id") Long id);

    TimeInterval findByName(String name);

    List<TimeInterval> findAllByIntervalAndTimeUnitCode(Long interval, TimeUnitCode timeUnitCode);
}
