import java.util.*;
import java.util.stream.Collectors;

public class ScheduleGeneticAlgorithm {
    private final Integer _maxLessonsPerDay;
    private final Integer _populationSize;
    private final Integer _populationsCount;
    private final Integer _dayCount;
    private final Random _random;
    private ScheduleGeneticAlgorithm(Builder builder) {
        this._maxLessonsPerDay = builder._maxLessonsPerDay;
        this._populationSize = builder._populationSize;
        this._populationsCount = builder._populationsCount;
        this._dayCount = builder._dayCount;
        this._random = new Random();
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

    public Schedule getSchedule(Set<Group> groups, Set<Teacher> teachers){
        Set<Schedule> population = getInitialPopulations(groups, teachers);

        for (int i = 0; i < this._populationsCount - 1; i++){
            population = getReproduction(population);
            this.mutation(population, groups);
            Map<Integer, Map<String, Double>> points = getScore(population);
            List<PopulationPointsPair> arr = this.getPopulationsRangedByPoints(points);
            population = getSelection(population, points);
        }

        Map<Integer, Map<String, Double>> points = getScore(population);
        return this.getBest(population, points);
    }

    private Set<Schedule> getInitialPopulations(Set<Group> groups, Set<Teacher> teachers){
        Set<Schedule> populations = new HashSet<>();
        for (int i = 0; i < this._populationSize; i++){
            Schedule schedule = new Schedule(this._dayCount, this._maxLessonsPerDay, groups);
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
                                schedule.assignLesson(
                                        currentDay,
                                        group.getName(),
                                        currentLessonNumber,
                                        teacher,
                                        lessonName);
                                currentLessonNumber += 1;
                                if (currentLessonNumber > this._maxLessonsPerDay - 1){
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

    private Set<Schedule> getReproduction(Set<Schedule> population){
        List<Schedule> populationList = new ArrayList<>(population);
        Set<Schedule> reproduction = new HashSet<>(population);
        for (int i = populationList.size(); i < this._populationSize; i++){
            int randomIndex = _random.nextInt(populationList.size());
            reproduction.add(populationList.get(randomIndex).clone());
        }
        return reproduction;
    }

    private void mutation(Set<Schedule> populations, Set<Group> groups){
        List<Schedule> populationsRemain = new ArrayList<>(populations);
        for (int i = 0; i < populations.size(); i++){
            int randomPopulationI = _random.nextInt(populationsRemain.size());
            Schedule randomPopulation = populationsRemain.get(randomPopulationI);
            populationsRemain.remove(randomPopulationI);

            for (Group group : groups){
                int randomFirstDay = _random.nextInt(this._dayCount);
                int randomFirstDayLesson = _random.nextInt(this._maxLessonsPerDay);

                int randomSecondDay = _random.nextInt(this._dayCount);
                int randomSecondDayLesson = _random.nextInt(this._maxLessonsPerDay);

                Lesson firstLesson = randomPopulation
                        .getLesson(randomFirstDay, group.getName(), randomFirstDayLesson);
                Lesson secondLesson = randomPopulation
                        .getLesson(randomSecondDay, group.getName(), randomSecondDayLesson);

                randomPopulation
                        .setLesson(randomFirstDay, group.getName(), randomFirstDayLesson, secondLesson);
                randomPopulation
                        .setLesson(randomSecondDay, group.getName(), randomSecondDayLesson, firstLesson);
            }
        }
    }

    private Map<Integer, Map<String, Double>> getScore(Set<Schedule> populations){
        Map<Integer, Map<String, Double>> goalValues = new HashMap<>();
        for (Schedule population : populations){
            Map<String, Double> groupsPoints = new HashMap<>();
            goalValues.put(population.getId(), groupsPoints);
            for (DaySchedule daySchedule : population.getDaysSchedule()){
                for (GroupSchedule groupSchedule : daySchedule.getGroupsSchedule()){
                    Double avgLessonsNumber =
                            (double) groupSchedule.getGroup().getTotalHours() / this._dayCount;
                    Double points = Math.abs(avgLessonsNumber - groupSchedule.getAssignLessonCount());
                    Double prevPoints = groupsPoints
                            .getOrDefault(groupSchedule.getGroup().getName(), 0.0);
                    groupsPoints.put(groupSchedule.getGroup().getName(), points + prevPoints);
                }
            }
        }
        return goalValues;
    }

    private Set<Schedule> getSelection(Set<Schedule> population, Map<Integer, Map<String, Double>> points){
        List<PopulationPointsPair> populationsRangedByPoints =
                this.getPopulationsRangedByPoints(points);

        int Q70 = (int) Math.round((7/10.0)*(populationsRangedByPoints.size()));
        Set<Integer> populationsId = populationsRangedByPoints
                .subList(Q70, populationsRangedByPoints.size())
                .stream()
                .map(pair -> pair._populationId)
                .collect(Collectors.toSet());

        return population
                .stream()
                .filter(e -> populationsId.contains(e.getId()))
                .collect(Collectors.toSet());
    }

    private static class PopulationPointsPair implements Comparable<PopulationPointsPair>{
        private final Integer _populationId;
        private final Double _points;
        public PopulationPointsPair(Integer populationI, Double points){
            this._populationId = populationI;
            this._points = points;
        }

        public Integer getPopulationId() {
            return this._populationId;
        }

        public Double getPoints() {
            return this._points;
        }

        @Override
        public int compareTo(PopulationPointsPair o) {
            return Double.compare(this._points, o._points);
        }
    }

    private Schedule getBest(Set<Schedule> population, Map<Integer, Map<String, Double>> points){
        List<PopulationPointsPair> populationsRangedByPoints =
                this.getPopulationsRangedByPoints(points);

        Optional<Schedule> res = population.stream()
                .filter(e -> e.getId().equals(populationsRangedByPoints
                        .get(populationsRangedByPoints.size() - 1).getPopulationId()))
                .findFirst();

        if (res.isPresent()){
            return res.get();
        }
        else{
            throw new AssertionError();
        }
    }

    private List<PopulationPointsPair> getPopulationsRangedByPoints(Map<Integer, Map<String, Double>> points){
        List<PopulationPointsPair> populationsRangedByPoints = new ArrayList<>();
        for (Integer populationI : points.keySet()){
            Double sum = 0.0;
            for (String groupName : points.get(populationI).keySet()){
                sum += points.get(populationI).get(groupName);
            }
            populationsRangedByPoints.add(new PopulationPointsPair(populationI, sum));
        }

        populationsRangedByPoints.sort(Collections.reverseOrder());
        return populationsRangedByPoints;
    }
}
