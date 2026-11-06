package cby.date.persian;

import java.util.Objects;

public final class DateHolder {
    private final int year;
    private final int month;
    private final int day;
    
    public DateHolder(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    
    public int year() {
        return year;
    }
    
    public int month() {
        return month;
    }
    
    public int day() {
        return day;
    }
    
    @Override
    public String toString() {
        return "DateHolder{year=" + year + ", month=" + month + ", day=" + day +"}";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateHolder dateHolder = (DateHolder) o;
        return year == dateHolder.year && month == dateHolder.month && day == dateHolder.day;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }
}
