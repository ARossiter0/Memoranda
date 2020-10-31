
import main.java.memoranda.date.CalendarDate;

public class SpecialCalendarDate {
    private String name;
    private CalendarDate date;

    public SpecialCalendarDate(CalendarDate _date, String _name) {
        this.name = _name;
        this.date = _date;
    }

    public String getName() {
        return this.name;
    }

    public CalendarDate getDate() {
        return this.date;
    }
}
