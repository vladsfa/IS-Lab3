import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ScheduleGeneticAlgorithm alg = new ScheduleGeneticAlgorithm.Builder()
                .dayCount(20)
                .maxLessonsPerDay(3)
                .populationSize(100)
                .populationsCount(1000)
                .build();

        Set<Teacher> teachers = new HashSet<>() {{
            add(new Teacher(
                    "Василь",
                    new HashSet<>() {{
                        add("Математика");
                        add("Інформатика");
                        add("Фізика");
                    }},
                    25
            ));
            add(new Teacher(
                    "Петро",
                    new HashSet<>() {{
                        add("Математика");
                        add("Фізика");
                    }},
                    10
            ));
            add(new Teacher(
                    "Петро",
                    new HashSet<>() {{
                        add("Інформатика");
                        add("Фізика");
                    }},
                    10
            ));
        }};

        Set<Group> groups = new HashSet<>() {{
            add(new Group(
                    "ТТП-42",
                    new HashMap<>() {{
                        put("Математика", 9);
                        put("Інформатика", 5);
                        put("Фізика", 6);
                    }}
            ));
            add(new Group(
                    "ТТП-41",
                    new HashMap<>() {{
                        put("Математика", 9);
                        put("Інформатика", 5);
                        put("Фізика", 6);
                    }}
            ));
        }};

        Schedule populations = alg.getSchedule(groups, teachers);
        writeScheduleToFile(List.of(populations), "Schedule.txt");
    }

    public static void writeScheduleToFile(List<Schedule> schedules, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {

            for (Schedule schedule : schedules) {
                List<DaySchedule> days = schedule.getDaysSchedule();
                for (int dayNumber = 0; dayNumber < days.size(); dayNumber++) {
                    writer.write("День " + (dayNumber + 1));
                    writer.newLine();
                    writer.newLine();

                    Set<GroupSchedule> groupsSchedule = days.get(dayNumber).getGroupsSchedule();
                    for (GroupSchedule groupSchedule : groupsSchedule) {
                        writer.write("Група " + groupSchedule.getGroup().getName());
                        writer.newLine();

                        List<Lesson> lessons = groupSchedule.getLessons();
                        for (int lessonNumber = 0; lessonNumber < lessons.size(); lessonNumber++) {
                            Lesson lesson = lessons.get(lessonNumber);

                            writer.write(String.valueOf(lessonNumber + 1));
                            writer.newLine();
                            if (lesson.IsExist()){
                                writer.write(lesson.getName());
                                writer.newLine();
                                writer.write(lesson.getTeacher().getName());
                                writer.newLine();
                            }
                            else {
                                writer.write("Немає");
                                writer.newLine();
                            }
                        }

                        writer.newLine();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

