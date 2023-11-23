import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Schedule implements Cloneable{
    private final List<DaySchedule> _days;
    private final Integer _id;
    private static final AtomicInteger _count = new AtomicInteger(0);

    public Schedule(Integer dayCount, Integer maxLessonsPerDay, Set<Group> groups){
        this._days = new ArrayList<>();
        for (int i = 0; i < dayCount; i++){
            this._days.add(new DaySchedule(maxLessonsPerDay, groups));
        }
        this._id = _count.getAndIncrement();
    }

    private Schedule(List<DaySchedule> daySchedules){
        this._days = daySchedules;
        this._id = _count.getAndIncrement();
    }

    public void assignLesson(Integer dayNumber, String groupName, Integer lessonNumber, Teacher teacher, String lesson){
        DaySchedule day = this._days.get(dayNumber);
        day.assignLesson(groupName, lessonNumber, teacher, lesson);
    }

    public Lesson getLesson(Integer dayNumber, String groupName, Integer lessonNumber){
        return this._days.get(dayNumber).getLesson(groupName, lessonNumber);
    }

    public void setLesson(Integer dayNumber, String groupName, Integer lessonNumber, Lesson lesson){
        this._days.get(dayNumber).setLesson(groupName, lessonNumber, lesson);
    }

    public List<DaySchedule> getDaysSchedule(){
        return this._days;
    }

    @Override
    public Schedule clone() {
        List<DaySchedule> copyDaysSchedules = new ArrayList<>();
        for (DaySchedule daySchedule : this._days){
            copyDaysSchedules.add(daySchedule.clone());
        }
        return new Schedule(copyDaysSchedules);
    }

    public Integer getId() {
        return _id;
    }
}
