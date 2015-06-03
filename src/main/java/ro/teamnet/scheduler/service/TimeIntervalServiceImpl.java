package ro.teamnet.scheduler.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.service.AbstractServiceImpl;
import ro.teamnet.scheduler.domain.TimeInterval;
import ro.teamnet.scheduler.repository.TimeIntervalRepository;

import javax.inject.Inject;

@Service
public class TimeIntervalServiceImpl extends AbstractServiceImpl<TimeInterval, Long> implements TimeIntervalService {

    private final Logger log = LoggerFactory.getLogger(TimeIntervalServiceImpl.class);

    @Inject
    private TimeIntervalRepository timeIntervalRepository;

    @Inject
    public TimeIntervalServiceImpl(TimeIntervalRepository repository) {
        super(repository);
    }
}
