package cby.date.persian;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Objects;

public class PersianDateTime implements Comparable<PersianDateTime>{
    private final LocalDateTime dateTime;
    private final PersianDate date;
    private final LocalTime time;
    
    private PersianDateTime(LocalDateTime dateTime) {
        this.dateTime = Objects.requireNonNull(dateTime, "cannot input null value");
        this.date = PersianDate.ofLocalDate(dateTime.toLocalDate());
        this.time = dateTime.toLocalTime();
    }
    
    public static PersianDateTime now() {
        return new PersianDateTime(LocalDateTime.now(ZoneId.systemDefault()));
    }
    
    public static PersianDateTime of(PersianDate date, LocalTime time) {
        return new PersianDateTime(LocalDateTime.of(date.getLocalDate(), time));
    }
    
    public static PersianDateTime ofLocalDateTime(LocalDateTime dateTime) {
        return new PersianDateTime(dateTime);
    }
    
    public LocalDateTime getLocalDateTime() {
        return dateTime;
    }
    
    public PersianDate date() {
        return date;
    }
    
    public LocalTime time() {
        return time;
    }
    
    @Override
    public String toString() {
        return date.toString() + ", " + time.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersianDateTime pdt = (PersianDateTime) o;
        return Objects.equals(dateTime, pdt.dateTime);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(dateTime);
    }
    
    @Override
    public int compareTo(PersianDateTime persianDateTime) {
        return dateTime.compareTo(persianDateTime.dateTime);
    }
}
