package com.example.task3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.task3.domain.Group;
import com.example.task3.domain.Participant;
import com.example.task3.repository.GroupRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;
    private final ParticipantService participantService;

    public GroupService(GroupRepository groupRepository,
                        ParticipantService participantService) {
        this.groupRepository = groupRepository;
        this.participantService = participantService;
    }

    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public void delete(Group group) {
        groupRepository.delete(group);
    }

    public void addParticipant(Long groupId, Participant participant) throws Exception {
        Optional<Group> group = getGroupById(groupId);
        if (group.isEmpty()) {
            throw new Exception("Group not fount");
        }
        participant.setGroup(group.get());
        participantService.save(participant);
        group.get().getParticipants().add(participant);
    }

    public void removeParticipant(Long groupId, Long participantId) throws Exception {
        Optional<Group> group = getGroupById(groupId);
        if (group.isEmpty()) {
            throw new Exception("Group not fount");
        }
        Optional<Participant> participant = participantService.findById(participantId);
        if (participant.isPresent()) {
            group.get().getParticipants().remove(participant.get());
            participantService.delete(participant.get());
        } else {
            throw new Exception("Participant not fount");
        }
    }

    public void runToss(Long groupId) throws Exception {
        Optional<Group> group = getGroupById(groupId);
        if (group.isEmpty()) {
            throw new Exception("Group not fount");
        }
        List<Participant> participants = group.get().getParticipants();
        if (participants.size() < 3) {
            throw new Exception("Not enough participants");
        }
        participantService.runToss(participants);
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }
}
