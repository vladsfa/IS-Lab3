import java.util.*;

public class Schedule {
    private final Map<String, GroupSchedule> _groups;

    public Schedule(){
        this._groups = new HashMap<>();
    }

    public void setLesson(String group, Integer day, Integer lessonNumber, Teacher teacher, String lessonName){
        GroupSchedule schedule = this._groups.get(group);
        schedule.setLesson(day, lessonNumber, teacher, lessonName);
    }
}
