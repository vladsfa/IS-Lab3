import java.util.ArrayList;
import java.util.List;

public class GroupSchedule {
    private final List<Day> _days;

    public GroupSchedule(){
        this._days = new ArrayList<>();
    }

    public void setLesson(Integer dayNumber, Integer lessonNumber, Teacher teacher, String lesson){
        Day day = this._days.get(dayNumber);
        day.setLesson(lessonNumber, teacher, lesson);
    }
}
