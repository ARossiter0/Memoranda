package main.java.memoranda;

public class LectureTime {

    public String day;
    public int hour;
    public int min;

    public LectureTime() {
        this.day = "Monday";
        this.hour = 12;
        this.min = 0;
    }
    public LectureTime(String day, int hour, int min) {
        this.day = day;
        this.hour = hour;
        this.min = min;
    }
}
