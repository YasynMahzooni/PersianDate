package cby.date.persian;

public enum PersianDayOfWeek {
    SATURDAY(1, "Saturday", "شنبه"),
    SUNDAY(2, "Sunday", "یکشنبه"),
    MONDAY(3, "Monday", "دوشنبه"),
    TUESDAY(4, "Tuesday", "سه‌شنبه"),
    WEDNESDAY(5, "Wednesday", "چهارشنبه"),
    THURSDAY(6, "Thursday", "پنج‌شنبه"),
    FRIDAY(7, "Friday", "جمعه");
    
    private final int value;
    private final String englishName;
    private final String persianName;
    private static final PersianDayOfWeek[] BY_VALUE = new PersianDayOfWeek[8];
    
    static {
        for (PersianDayOfWeek d : values()) BY_VALUE[d.value] = d;
    }
    
    private PersianDayOfWeek(int value, String englishName, String persianName) {
        this.value = value;
        this.englishName = englishName;
        this.persianName = persianName;
    }
    
    public static PersianDayOfWeek of(int day) {
        if (day < 1 || day > 7) throw new IllegalArgumentException("invalid input value: " + day);
        return BY_VALUE[day];
    }
    
    public int value() {
        return value;
    }
    
    public String englishName() {
        return englishName;
    }
    
    public String persianName() {
        return persianName;
    }
    
    public PersianDayOfWeek daysBefore(int days) {
        return of(shiftValue(value, -days));
    }
    
    public PersianDayOfWeek daysAfter(int days) {
        return of(shiftValue(value, days));
    }
    
    public PersianDayOfWeek next() {
        return daysAfter(1);
    }
    
    public PersianDayOfWeek previous() {
        return daysBefore(1);
    }
    
    public boolean isWeekend() {
        return this == FRIDAY;
    }
    
    public boolean isWeekday() {
        return !isWeekend();
    }
    
    private static int shiftValue(int currentValue, int offset) {
        return Math.floorMod(currentValue - 1 + offset, 7) + 1;
    }
}
