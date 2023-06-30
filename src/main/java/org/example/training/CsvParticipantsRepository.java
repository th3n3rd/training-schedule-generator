package org.example.training;

import java.nio.file.Paths;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
class CsvParticipantsRepository implements ParticipantsRepository {

    private final String filePath;

    @Override
    public List<Participant> findAll() {
        try {
            var participants = new ArrayList<Participant>();
            try (var scanner = new Scanner(Paths.get(filePath))) {
                scanner.nextLine(); // Skip the header
                while (scanner.hasNextLine()) {
                    var fields = scanner.nextLine().split(",");
                    var fullName = fields[0];
                    var role = fields[1];
                    var region = fields[2];
                    var timezoneOffset = (int) Double.parseDouble(fields[3]);
                    var acceptableHoursShift = (int) Double.parseDouble(fields[4]);
                    participants.add(new Participant(
                        fullName,
                        role,
                        region,
                        ZoneOffset.ofHours(timezoneOffset),
                        acceptableHoursShift
                    ));
                }
            }
            return participants;
        } catch (Exception e) {
            throw new UnableToFindParticipantsException("There was a problem loading the participants", e.getCause());
        }
    }
}
