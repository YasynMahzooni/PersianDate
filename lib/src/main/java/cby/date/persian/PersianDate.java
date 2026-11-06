package cby.date.persian;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public final class PersianDate implements Comparable<PersianDate> {
    private final LocalDate date;
    private final int year;
    private final int month;
    private final int day;
    
    private PersianDate(LocalDate date) {
        this.date = Objects.requireNonNull(date, "cannot input null value");
        initialize();
    }
    
    public static PersianDate now() {
        return new PersianDate(LocalDate.now(ZoneId.systemDefault()));
    }
    
    public static PersianDate of(int year, int month, int day) {
        DateHolder dh = DateConverter.persianToGregorian(year, month, day);
        return new PersianDate(LocalDate.of(dh.year(), dh.month(), dh.day()));
    }
    
    public static PersianDate ofLocalDate(LocalDate date) {
        return new PersianDate(date);
    }
    
    public LocalDate getLocalDate() {
        return date;
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
    
    public PersianDayOfWeek dayOfWeek() {
        return PersianDayOfWeek.of(Math.floorMod(date.getDayOfWeek().getValue() + 1, 7) + 1);
    }
    
    public PersianDate plusDays(long days) {
        return new PersianDate(date.plusDays(days));
    }
    
    public PersianDate minusDays(long days) {
        return new PersianDate(date.minusDays(days));
    }
    
    public boolean isBefore(PersianDate persianDate) {
        return this.date.isBefore(persianDate.date);
    }
    
    public boolean isAfter(PersianDate persianDate) {
        return this.date.isAfter(persianDate.date);
    }
    
    public long daysBetween(PersianDate persianDate) {
        return Math.abs(ChronoUnit.DAYS.between(date, persianDate.date));
    }
    
    public int dateId() {
        return year * 10000 + month * 100 + day;
    }
    
    private void initialize() {
        DateHolder dh = DateConverter.gregorianToPersian(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        this.year = dh.year();
        this.month = dh.month();
        this.day = dh.day();
    }
    
    @Override
    public String toString() {
        return year + "-" + month + "-" + day;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersianDate persianDate = (PersianDate) o;
        return Objects.equals(date, persianDate.date);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public int compareTo(PersianDate persianDate) {
        return date.compareTo(Objects.requireNonNull(persianDate.date));
    }
}
