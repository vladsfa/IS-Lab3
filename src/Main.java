import java.util.*;

public class Main {
    public static void main(String[] args) {
        ScheduleGeneticAlgorithm alg = new ScheduleGeneticAlgorithm.Builder()
                .dayCount(7)
                .maxLessonsPerDay(3)
                .populationSize(5)
                .populationsCount(10)
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
                   "ТТП-41",
                   new HashMap<>() {{
                       put("Математика", 6);
                       put("Інформатика", 7);
                       put("Фізика", 5);
                   }}
           ));
            add(new Group(
                    "ТТП-42",
                    new HashMap<>() {{
                        put("Математика", 9);
                        put("Інформатика", 5);
                        put("Фізика", 6);
                    }}
            ));
        }};

        List<Schedule> populations = alg.GetInitialPopulations(groups, teachers);
    }
}

