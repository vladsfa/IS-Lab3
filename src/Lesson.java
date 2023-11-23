public class Lesson implements Cloneable{
    private Teacher _teacher;
    private String _name;
    private Boolean _isExists;

    public Lesson() {
        this._isExists = false;
    }

    public void setLesson(Teacher teacher, String name){
        this._teacher = teacher;
        this._name = name;
        this._isExists = true;
    }

    public Teacher getTeacher(){
        return this._teacher;
    }

    public String getName(){
        return this._name;
    }

    public Boolean IsExist(){
        return this._isExists;
    }

    @Override
    public Lesson clone() {
        try {
            return (Lesson) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
