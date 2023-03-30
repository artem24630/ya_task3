package com.example.task3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.task3.domain.Participant;
import com.example.task3.repository.ParticipantRepository;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<Participant> findAll() {
        return participantRepository.findAll();
    }

    public Optional<Participant> findById(Long id) {
        return participantRepository.findById(id);
    }

    public Participant save(Participant participant) {
        return participantRepository.save(participant);
    }

    public void delete(Participant participant) {
        participantRepository.delete(participant);
    }

    public void runToss(List<Participant> participants) {
        Random random = new Random();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = participants.get(i);
            int index = random.nextInt(size - 3);
            if (index >= i) {
                index++;
            }
            Participant recipient = participants.get(index);
            if (participant.getId().equals(recipient.getId())) {
                if (i == size - 1) {
                    Participant temp = participants.get(0);
                    recipient = participant.getId().equals(temp.getId()) ? participants.get(1) : temp;
                } else {
                    recipient = participants.get(i + 1);
                }
            }
            participant.setRecipient(recipient);
        }
    }
}
