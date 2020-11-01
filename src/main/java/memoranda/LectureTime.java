package main.java.memoranda;

public class LectureTime {

    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY 
    }

    public Day day;
    public int hour;
    public int min;

    public LectureTime() {
        this.day = Day.MONDAY;
        this.hour = 12;
        this.min = 0;
    }

    public String dayToString() {
        return day.toString();
    }
}
