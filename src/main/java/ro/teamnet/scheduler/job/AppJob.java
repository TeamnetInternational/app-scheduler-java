package ro.teamnet.scheduler.job;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.scheduler.constants.QuartzSchedulingConstants;
import ro.teamnet.scheduler.domain.ScheduledJobExecution;
import ro.teamnet.scheduler.service.ScheduledJobExecutionService;
import ro.teamnet.scheduler.service.ScheduledJobService;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oana.Mihai on 6/3/2015.
 */
public abstract class AppJob implements Job {

    private String options;
    private Map<Integer, String> taskOptions;
    private Long scheduledJobId;

    @Inject
    private ScheduledJobService scheduledJobService;


    @Inject
    private ScheduledJobExecutionService scheduledJobExecutionService;

    @Override
    public final void execute(JobExecutionContext context) throws JobExecutionException {
        options = context.getMergedJobDataMap().getString(QuartzSchedulingConstants.JOB_OPTIONS);
        readTaskOptions();
        scheduledJobId = context.getMergedJobDataMap().getLong(QuartzSchedulingConstants.JOB_ID);

        ScheduledJobExecution execution = saveExecution(context);
        JobExecutionStatus status = execution.getStatus();
        try {
            run(context);
            status = JobExecutionStatus.FINISHED;
        } catch (Exception e) {
            status = JobExecutionStatus.FAILED;
            e.printStackTrace();
        } finally {
            updateExecutionStatus(execution, status);
        }
    }

    @Transactional
    private void updateExecutionStatus(ScheduledJobExecution execution, JobExecutionStatus status) {
        execution.setStatus(status);
        scheduledJobExecutionService.save(execution);
    }

    @Transactional
    private ScheduledJobExecution saveExecution(JobExecutionContext context) {
        ScheduledJobExecution execution = new ScheduledJobExecution();
        execution.setActualFireTime(new DateTime(context.getFireTime()));
        execution.setScheduledFireTime(new DateTime(context.getScheduledFireTime()));
        execution.setLastFireTime(new DateTime(context.getPreviousFireTime()));
        execution.setNextFireTime(new DateTime(context.getNextFireTime()));
        execution.setState(new JSONObject(context.getMergedJobDataMap()).toString());
        execution.setStatus(JobExecutionStatus.RUNNING);
        execution.setScheduledJob(scheduledJobService.findOne(scheduledJobId));
        return scheduledJobExecutionService.save(execution);
    }

    protected abstract void run(JobExecutionContext context);

    public String getOptions() {
        return options;
    }

    private void readTaskOptions() {
        taskOptions = new HashMap<>();

        //convert JSON string to Map
        try {
            taskOptions = new ObjectMapper().readValue(getOptions(),
                    new TypeReference<HashMap<Integer, String>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, String> getTaskOptions() {
        return taskOptions;
    }
}
