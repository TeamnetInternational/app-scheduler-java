
package ro.teamnet.scheduler.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import ro.teamnet.bootstrap.domain.util.CustomDateTimeDeserializer;
import ro.teamnet.bootstrap.domain.util.CustomDateTimeSerializer;
import ro.teamnet.scheduler.dto.JobExecutionDTO;
import ro.teamnet.scheduler.enums.JobExecutionStatus;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A ScheduledJobExecution.
 */
@Entity
@Table(name = "T_SCHEDULEDJOBEXECUTION")
public class ScheduledJobExecution implements Serializable, Comparable<ScheduledJobExecution> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "scheduled_fire_time", nullable = false)
    private DateTime scheduledFireTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "actual_fire_time", nullable = false)
    private DateTime actualFireTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "last_fire_time", nullable = false)
    private DateTime lastFireTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "next_fire_time", nullable = false)
    private DateTime nextFireTime;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private JobExecutionStatus status;

    @ManyToOne
    @JsonBackReference
    private ScheduledJob scheduledJob;


    //other entity fields relations

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getScheduledFireTime() {
        return scheduledFireTime;
    }

    public void setScheduledFireTime(DateTime scheduledFireTime) {
        this.scheduledFireTime = scheduledFireTime;
    }

    public DateTime getActualFireTime() {
        return actualFireTime;
    }

    public void setActualFireTime(DateTime actualFireTime) {
        this.actualFireTime = actualFireTime;
    }

    public DateTime getLastFireTime() {
        return lastFireTime;
    }

    public void setLastFireTime(DateTime lastFireTime) {
        this.lastFireTime = lastFireTime;
    }

    public DateTime getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(DateTime nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public JobExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(JobExecutionStatus status) {
        this.status = status;
    }

    public ScheduledJob getScheduledJob() {
        return scheduledJob;
    }

    public void setScheduledJob(ScheduledJob scheduledJob) {
        this.scheduledJob = scheduledJob;
    }


    //other entity methods relations

    @Override
    public int compareTo(ScheduledJobExecution obj) {
        if (obj == null || obj.getId() == null) {
            return 1;
        }

        if (id == null) {
            return -1;
        }

        if (actualFireTime == null || obj.getActualFireTime() == null || obj.getActualFireTime().equals(actualFireTime)) {
            return obj.getId().compareTo(id);
        }

        return obj.getActualFireTime().compareTo(actualFireTime);
    }

    @Transient
    @JsonIgnore
    public JobExecutionDTO toDTO(){
        JobExecutionDTO jobExecutionDTO = new JobExecutionDTO();
        jobExecutionDTO.setId(this.getId());
        jobExecutionDTO.setStatus(this.getStatus());
        jobExecutionDTO.setActualFireTime(this.getActualFireTime());
        jobExecutionDTO.setNextFireTime(this.getNextFireTime());
        return jobExecutionDTO;
    }
}
