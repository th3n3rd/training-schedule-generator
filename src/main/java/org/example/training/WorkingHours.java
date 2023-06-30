package org.example.training;

import java.time.Duration;
import java.time.LocalTime;

class WorkingHours {
    public static int NoHoursShift = 0;
    public static Duration HalfDay = Duration.ofHours(4);
    public static LocalTime StartTime = LocalTime.parse("09:00");
    public static LocalTime EndTime = LocalTime.parse("17:00");
}
