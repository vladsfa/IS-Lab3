import java.util.*;

public class DaySchedule implements Cloneable{
    private Set<GroupSchedule> _groupsSchedule;

    public DaySchedule(Integer maxLessonsPerDay, Set<Group> groups){
        this._groupsSchedule = new HashSet<>();
        for (Group group : groups){
            this._groupsSchedule.add(new GroupSchedule(maxLessonsPerDay, group));
        }
    }

    public void assignLesson(String groupName, Integer lessonNumber, Teacher teacher, String lessonName){
        this.getGroupScheduleByName(groupName)
                .assignLesson(lessonNumber, teacher, lessonName);
    }

    public Lesson getLesson(String groupName, Integer lessonNumber){
        return this.getGroupScheduleByName(groupName).getLesson(lessonNumber);
    }

    public void setLesson(String groupName, Integer lessonNumber, Lesson lesson){
        this.getGroupScheduleByName(groupName).setLesson(lessonNumber, lesson);
    }

    public Set<GroupSchedule> getGroupsSchedule(){
        return this._groupsSchedule;
    }

    @Override
    public DaySchedule clone() {
        try {
            DaySchedule clone = (DaySchedule) super.clone();
            Set<GroupSchedule> groupsSchedule = new HashSet<>();
            for (GroupSchedule groupSchedule : clone._groupsSchedule){
                groupsSchedule.add(groupSchedule.clone());
            }
            clone._groupsSchedule = groupsSchedule;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private GroupSchedule getGroupScheduleByName(String name){
        Optional<GroupSchedule> schedule = this._groupsSchedule.stream()
                .filter(groupSchedule -> groupSchedule.getGroup().getName().equals(name))
                .findFirst();
        if (schedule.isPresent()){
            return schedule.get();
        }
        else {
            throw new AssertionError();
        }
    }
}
