package org.example.training;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class TimeSlotsRepository {

    List<TimeSlot> findAll() {
        var slots = new ArrayList<TimeSlot>();
        for (int i = 0; i < 24; i++) {
            slots.add(TimeSlot.halfDay(LocalTime.MIDNIGHT.plusHours(i)));
        }
        return slots;
    }

}
