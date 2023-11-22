public class Lesson {
    private Teacher _teacher;
    private String _name;

    public Lesson() {
    }

    public Teacher getTeacher() {
        return _teacher;
    }

    public void setTeacher(Teacher teacher) {
        _teacher = teacher;
    }

    public String getName(){
        return _name;
    }

    public void setName(String name){
        this._name = name;
    }
}
