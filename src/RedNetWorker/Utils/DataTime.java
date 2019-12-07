package RedNetWorker.Utils;

import java.util.Calendar;

public class DataTime {
    public final int year;
    public final int month;
    public final int day;
    public final int hour;
    public final int minute;
    public final int second;
    public final long unixTimestamp;

    public DataTime(int year,int month,int day,int hour,int minute,int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.unixTimestamp = componentTimeToTimestamp(year, month, day, hour, minute, second);
    }

    public DataTime(long unixTimestamp) {
        Calendar mydate = Calendar.getInstance();
        mydate.setTimeInMillis(unixTimestamp*1000L);
        this.unixTimestamp = unixTimestamp;
        this.year = mydate.get(Calendar.YEAR);
        this.month = mydate.get(Calendar.MONTH);
        this.day = mydate.get(Calendar.DAY_OF_MONTH);
        this.hour = mydate.get(Calendar.HOUR);
        this.minute = mydate.get(Calendar.MINUTE);
        this.second = mydate.get(Calendar.SECOND);
    }

    ///Now date
    public DataTime() {
        Calendar calendar = Calendar.getInstance();
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR);
        this.minute = calendar.get(Calendar.MINUTE);
        this.second = calendar.get(Calendar.SECOND);
        this.unixTimestamp = (calendar.getTimeInMillis() / 1000L);
    }

    public DataTime(Calendar calendar) {
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR);
        this.minute = calendar.get(Calendar.MINUTE);
        this.second = calendar.get(Calendar.SECOND);
        this.unixTimestamp = (calendar.getTimeInMillis() / 1000L);
    }

    //https://stackoverflow.com/questions/4674174/convert-integer-dates-times-to-unix-timestamp-in-java/4674374
    private long componentTimeToTimestamp(int year, int month, int day, int hour, int minute, int second) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, 0);

        return (c.getTimeInMillis() / 1000L);
    }
}
