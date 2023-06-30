package org.example.training;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class InMemoryParticipantsRepository implements ParticipantsRepository {
    private final List<Participant> participants;

    @Override
    public List<Participant> findAll() {
        return participants;
    }
}
