import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Day {
    private final List<Lesson> _lessons;
    public Day(Integer lessonCount){
        this._lessons = new ArrayList<>();
        for (int i = 0; i < lessonCount; i++){
            this._lessons.add(new Lesson());
        }
    }

    public void setLesson(Integer lessonNumber, Teacher teacher, String lessonName){
        Lesson lesson = this._lessons.get(lessonNumber);
        lesson.setName(lessonName);
        lesson.setTeacher(teacher);
    }
}
