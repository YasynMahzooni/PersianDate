package cby.date.persian;

/**
 * Utility class for converting dates between Gregorian and Jalali (Persian) calendars.
 */
public final class DateConverter {

    private DateConverter() {
        // Prevent instantiation
    }

    /**
     * Converts Gregorian date to Persian (Jalali) date.
     *
     * @param year  Gregorian year
     * @param month Gregorian month
     * @param day   Gregorian day
     * @return DateHolder containing Persian year, month, day
     */
    public static DateHolder gregorianToPersian(int year, int month, int day) {
        int jd = jg2jd(year, month, day, 0);
        int[] jal = jd2jal(jd);
        return new DateHolder(jal[0], jal[1], jal[2]);
    }

    /**
     * Converts Persian (Jalali) date to Gregorian date.
     *
     * @param year  Persian year
     * @param month Persian month
     * @param day   Persian day
     * @return DateHolder containing Gregorian year, month, day
     */
    public static DateHolder persianToGregorian(int year, int month, int day) {
        int jd = jal2jd(year, month, day);
        int[] greg = jd2jg(jd, 0);
        return new DateHolder(greg[0], greg[1], greg[2]);
    }

    /**
     * Calculates the Julian Day number (JG2JD) from Gregorian or Julian
     * calendar dates.
     */
    private static int jg2jd(int year, int month, int day, int j1g0) {
        int jd = (1461 * (year + 4800 + (month - 14) / 12)) / 4
                + (367 * (month - 2 - 12 * ((month - 14) / 12))) / 12
                - (3 * ((year + 4900 + (month - 14) / 12) / 100)) / 4 + day
                - 32075;

        if (j1g0 == 0)
            jd = jd - (year + 100100 + (month - 8) / 6) / 100 * 3 / 4 + 752;

        return jd;
    }

    /**
     * Calculates Gregorian calendar date from the Julian Day number (JD).
     *
     * @return int[] {year, month, day}
     */
    private static int[] jd2jg(int jd, int j1g0) {
        int i, j;
        j = 4 * jd + 139361631;
        if (j1g0 == 0) {
            j = j + (4 * jd + 183187720) / 146097 * 3 / 4 * 4 - 3908;
        }
        i = (j % 1461) / 4 * 5 + 308;
        int gD = (i % 153) / 5 + 1;
        int gM = ((i / 153) % 12) + 1;
        int gY = j / 1461 - 100100 + (8 - gM) / 6;
        return new int[] { gY, gM, gD };
    }

    /**
     * Converts the Julian Day number to a date in the Jalali calendar.
     *
     * @return int[] {year, month, day}
     */
    private static int[] jd2jal(int jdn) {
        int[] greg = jd2jg(jdn, 0);
        int gY = greg[0];
        int jY = gY - 621;

        JalCalResult cal = jalCal(jY);
        int march = cal.march;
        int leap = cal.leap;

        int jdN1F = jg2jd(gY, 3, march, 0);
        int k = jdn - jdN1F;

        int jM, jD;

        if (k >= 0) {
            if (k <= 185) {
                jM = 1 + k / 31;
                jD = (k % 31) + 1;
                return new int[] { jY, jM, jD };
            } else {
                k = k - 186;
            }
        } else {
            jY = jY - 1;
            k = k + 179;
            if (leap == 1)
                k = k + 1;
            // Recompute march/leap for the decremented year
            cal = jalCal(jY);
            leap = cal.leap;
        }

        jM = 7 + k / 30;
        jD = (k % 30) + 1;
        return new int[] { jY, jM, jD };
    }

    /**
     * Converts a date of the Jalali calendar to the Julian Day Number.
     */
    private static int jal2jd(int jY, int jM, int jD) {
        JalCalResult cal = jalCal(jY);
        int gY = cal.gY;
        int march = cal.march;
        int jd = jg2jd(gY, 3, march, 1) + (jM - 1) * 31 - jM / 7 * (jM - 7)
                + jD - 1;
        return jd;
    }

    /**
     * Determines if the Jalali (Persian) year is leap and finds the day in
     * March (Gregorian calendar) of the first day of the Jalali year.
     */
    private static JalCalResult jalCal(int jY) {
        int march = 0;
        int leap = 0;
        int[] breaks = { -61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210,
                1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178 };

        int gY = jY + 621;
        int leapJ = -14;
        int jp = breaks[0];
        int jump = 0;

        for (int j = 1; j <= 19; j++) {
            int jm = breaks[j];
            jump = jm - jp;
            if (jY < jm) {
                int N = jY - jp;
                leapJ = leapJ + N / 33 * 8 + (N % 33 + 3) / 4;
                if ((jump % 33) == 4 && (jump - N) == 4)
                    leapJ = leapJ + 1;
                int leapG = (gY / 4) - (gY / 100 + 1) * 3 / 4 - 150;
                march = 20 + leapJ - leapG;
                if ((jump - N) < 6)
                    N = N - jump + (jump + 4) / 33 * 33;
                leap = ((((N + 1) % 33) - 1) % 4);
                if (leap == -1)
                    leap = 4;
                break;
            }
            leapJ = leapJ + jump / 33 * 8 + (jump % 33) / 4;
            jp = jm;
        }
        return new JalCalResult(gY, march, leap);
    }

    /**
     * Simple holder for the result of jalCal().
     */
    private static final class JalCalResult {
        final int gY;
        final int march;
        final int leap;

        JalCalResult(int gY, int march, int leap) {
            this.gY = gY;
            this.march = march;
            this.leap = leap;
        }
    }
}
