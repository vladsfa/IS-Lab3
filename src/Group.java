import java.util.HashMap;
import java.util.Map;

public class Group {
    private String _name;
    private Map<String, Integer> _lessons;

    public Group(String name, HashMap<String, Integer> lessons) {
        _name = name;
        _lessons = lessons;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public Map<String, Integer> getLessons() {
        return _lessons;
    }

    public void setLessons(Map<String, Integer> lessons) {
        _lessons = lessons;
    }

    public Integer getTotalHours(){
        Integer res = 0;
        for (String lessonName : this._lessons.keySet()){
            res += this._lessons.get(lessonName);
        }
        return res;
    }
}
