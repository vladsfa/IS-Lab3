import java.util.ArrayList;
import java.util.List;

public class GroupSchedule implements Cloneable{
    private List<Lesson> _lessons;
    private final Group _group;
    public GroupSchedule(Integer maxLessonsPerDay, Group group){
        this._group = group;
        this._lessons = new ArrayList<>();
        for (int i = 0; i < maxLessonsPerDay; i++){
            this._lessons.add(new Lesson());
        }
    }

    public void assignLesson(Integer lessonNumber, Teacher teacher, String lessonName){
        Lesson lesson = this._lessons.get(lessonNumber);
        lesson.setLesson(teacher, lessonName);
    }

    public Lesson getLesson(Integer lessonNumber){
        return this._lessons.get(lessonNumber);
    }

    public void setLesson(Integer lessonNumber, Lesson lesson){
        this._lessons.set(lessonNumber, lesson);
    }

    public List<Lesson> getLessons(){
        return this._lessons;
    }

    public Integer getAssignLessonCount(){
        int res = 0;
        for (Lesson lesson : this._lessons){
            if (lesson.IsExist()){
                res += 1;
            }
        }
        return res;
    }
    @Override
    public GroupSchedule clone() {
        try {
            GroupSchedule clone = (GroupSchedule) super.clone();
            List<Lesson> lessons = new ArrayList<>();
            for (Lesson lesson : clone._lessons){
                lessons.add(lesson.clone());
            }
            clone._lessons = lessons;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Group getGroup() {
        return _group;
    }
}
