
package ro.teamnet.scheduler.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import ro.teamnet.bootstrap.domain.util.CustomDateTimeDeserializer;
import ro.teamnet.bootstrap.domain.util.CustomDateTimeSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Schedule.
 */
@Entity
@Table(name = "T_SCHEDULE")
public class Schedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "recurrent")
    private Boolean recurrent;

    @Column(name = "cron")
    private String cron;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "start_time", nullable = false)
    private DateTime startTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "end_time", nullable = false)
    private DateTime endTime;

    @Column(name = "repetitions")
    private Long repetitions;

    @Column(name = "years")
    private String years;

    @Column(name = "days_in_month")
    private String daysInMonth;

    @Column(name = "hours")
    private String hours;

    @Column(name = "minutes")
    private String minutes;

    @ManyToOne
    private TimeInterval timeInterval;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "T_SCHEDULE_T_MONTH",
            inverseJoinColumns = {@JoinColumn(name = "month_id", referencedColumnName = "id")},
            joinColumns = {@JoinColumn(name = "schedule_id", referencedColumnName = "id")})
    private Set<Month> months = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "T_SCHEDULE_T_DAYOFWEEK",
            inverseJoinColumns = {@JoinColumn(name = "dayofweek_id", referencedColumnName = "id")},
            joinColumns = {@JoinColumn(name = "schedule_id", referencedColumnName = "id")})
    private Set<DayOfWeek> dayOfWeeks = new HashSet<>();

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<RecurrentYear> recurrentYears = new HashSet<>();

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<RecurrentDay> recurrentDays = new HashSet<>();

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<RecurrentHour> recurrentHours = new HashSet<>();

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<RecurrentMinute> recurrentMinutes = new HashSet<>();

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<RecurrentTimeUnit> recurrentTimeUnits = new HashSet<>();


    //other entity fields relations

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getRecurrent() {
        return recurrent;
    }

    public void setRecurrent(Boolean recurrent) {
        this.recurrent = recurrent;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public Long getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Long repetitions) {
        this.repetitions = repetitions;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getDaysInMonth() {
        return daysInMonth;
    }

    public void setDaysInMonth(String daysInMonth) {
        this.daysInMonth = daysInMonth;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }

    public Set<Month> getMonths() {
        return months;
    }

    public void setMonths(Set<Month> months) {
        this.months = months;
    }

    public Set<DayOfWeek> getDayOfWeeks() {
        return dayOfWeeks;
    }

    public void setDayOfWeeks(Set<DayOfWeek> dayOfWeeks) {
        this.dayOfWeeks = dayOfWeeks;
    }

    public Set<RecurrentYear> getRecurrentYears() {
        return recurrentYears;
    }

    public void setRecurrentYears(Set<RecurrentYear> recurrentYears) {
        this.recurrentYears = recurrentYears;
    }

    public Set<RecurrentDay> getRecurrentDays() {
        return recurrentDays;
    }

    public void setRecurrentDays(Set<RecurrentDay> recurrentDays) {
        this.recurrentDays = recurrentDays;
    }

    public Set<RecurrentHour> getRecurrentHours() {
        return recurrentHours;
    }

    public void setRecurrentHours(Set<RecurrentHour> recurrentHours) {
        this.recurrentHours = recurrentHours;
    }

    public Set<RecurrentMinute> getRecurrentMinutes() {
        return recurrentMinutes;
    }

    public void setRecurrentMinutes(Set<RecurrentMinute> recurrentMinutes) {
        this.recurrentMinutes = recurrentMinutes;
    }

    public Set<RecurrentTimeUnit> getRecurrentTimeUnits() {
        return recurrentTimeUnits;
    }

    public void setRecurrentTimeUnits(Set<RecurrentTimeUnit> recurrentTimeUnits) {
        this.recurrentTimeUnits = recurrentTimeUnits;
    }


    //other entity methods relations

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Schedule schedule = (Schedule) o;

        if (id != null ? !id.equals(schedule.id) : schedule.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }



    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", active='" + active + "'" +
                ", recurrent='" + recurrent + "'" +
                ", cron='" + cron + "'" +
                ", startTime='" + startTime + "'" +
                ", endTime='" + endTime + "'" +
                ", repetitions='" + repetitions + "'" +
                ", years='" + years + "'" +
                ", daysInMonth='" + daysInMonth + "'" +
                ", hours='" + hours + "'" +
                ", minutes='" + minutes + "'" +
                '}';
    }
}