package org.example.training;

import java.time.Duration;
import java.time.LocalTime;

record TimeSlot(LocalTime startTime, Duration duration) {}
