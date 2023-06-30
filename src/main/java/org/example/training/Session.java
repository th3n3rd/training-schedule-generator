package org.example.training;

import java.util.List;

record Session(TimeSlot slot, List<Participant> participants) {}
