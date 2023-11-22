import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ScheduleGeneticAlgorithm {
    private final Integer _maxLessonsPerDay;
    private final Integer _populationSize;
    private final Integer _populationsCount;
    private final Integer _dayCount;
    private ScheduleGeneticAlgorithm(Builder builder) {
        this._maxLessonsPerDay = builder._maxLessonsPerDay;
        this._populationSize = builder._populationSize;
        this._populationsCount = builder._populationsCount;
        this._dayCount = builder._dayCount;
    }

    public static class Builder {
        private Integer _maxLessonsPerDay;
        private Integer _populationSize;
        private Integer _populationsCount;
        private Integer _dayCount;

        public Builder maxLessonsPerDay(Integer maxLessonsPerDay) {
            this._maxLessonsPerDay = maxLessonsPerDay;
            return this;
        }

        public Builder populationSize(Integer maxPopulationSize) {
            this._populationSize = maxPopulationSize;
            return this;
        }

        public Builder populationsCount(Integer maxPopulationsCount){
            this._populationsCount = maxPopulationsCount;
            return this;
        }

        public Builder dayCount(Integer dayCount){
            this._dayCount = dayCount;
            return this;
        }

        public ScheduleGeneticAlgorithm build() {
            return new ScheduleGeneticAlgorithm(this);
        }
    }

    private void GetSchedule(Set<Group> groups, Set<Teacher> teachers){
        for (int i = 0; i < this._populationsCount; i++){

        }
    }

    public List<Schedule> GetInitialPopulations(Set<Group> groups, Set<Teacher> teachers){
        List<Schedule> populations = new ArrayList<>();
        for (int i = 0; i < this._populationSize; i++){
            Schedule schedule = new Schedule();
            for (Group group : groups){
                int currentDay = 0;
                int currentLessonNumber = 0;
                for (String lessonName: group.getLessons().keySet()){
                    Integer hoursRemain = group.getLessons().get(lessonName);
                    for (Teacher teacher : teachers){
                        if (teacher.IsTeach(lessonName)){
                            Integer hours = hoursRemain;
                            if (hours > teacher.GetHoursRemain()){
                                hours = teacher.GetHoursRemain();
                            }
                            for (int j = 0; j < hours; j++){
                                schedule.setLesson(
                                        group.getName(),
                                        currentDay,
                                        currentLessonNumber,
                                        teacher,
                                        lessonName);
                                currentLessonNumber += 1;
                                if (currentLessonNumber > this._maxLessonsPerDay){
                                    currentDay += 1;
                                    currentLessonNumber = 0;
                                }
                                hoursRemain -= 1;
                            }
                            if (hoursRemain.equals(0)){
                                break;
                            }
                        }
                    }
                }
            }
            populations.add(schedule);
        }
        return populations;
    }

    private void GetReproduction(){

    }

    private void GetMutation(){

    }

    private void GetGoalValues(){

    }

    private void GetSelection(){

    }
}
