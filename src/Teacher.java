import java.util.Set;

public class Teacher {
    private String _name;
    private Set<String> _lessons;
    private Integer _hours;
    private Integer _freeHoursRemain;
    public Teacher(String name, Set<String> lessons, Integer hours){
        this._name = name;
        this._lessons = lessons;
        this._hours = hours;
        this._freeHoursRemain = hours;
    }

    public boolean IsTeach(String lessonName){
        return this._lessons.contains(lessonName);
    }

    public void AssignHours(Integer hours) throws Exception {
        if (this._freeHoursRemain < hours){
            throw new Exception("AssignHours");
        }
        this._freeHoursRemain -= hours;
    }

    public Integer GetHoursRemain(){
        return this._freeHoursRemain;
    }

    public String GetName(){
        return this._name;
    }
}
