package com.example.task3.controller;

import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.task3.domain.Group;
import com.example.task3.domain.Participant;
import com.example.task3.service.GroupService;
import java.util.List;
import java.util.Optional;

@RestController
public class GroupController {

    private final GroupService groupService;


    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public ResponseEntity<List<Group>> findAll() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @GetMapping("group/{id}/")
    public ResponseEntity<Object> getGroupById(@PathVariable @Min(0) Long id) {
        return ResponseEntity.ok().body(groupService.getGroupById(id));
    }

    @PostMapping("/group")
    public ResponseEntity<Long> create(@RequestBody Group group) {
        Group savedGroup = groupService.save(group);
        return new ResponseEntity<>(savedGroup.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/group/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Group group) {
        Optional<Group> optionalGroup = groupService.getGroupById(id);
        if (optionalGroup.isPresent()) {
            Group existingGroup = optionalGroup.get();
            existingGroup.setName(group.getName());
            existingGroup.setDescription(group.getDescription());
            groupService.save(existingGroup);
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Group> optionalGroup = groupService.getGroupById(id);
        if (optionalGroup.isPresent()) {
            groupService.delete(optionalGroup.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/group/{id}/participant")
    public ResponseEntity<Long> addParticipant(@PathVariable Long id, @RequestBody Participant participant) {
        try {
            groupService.addParticipant(id, participant);
            return new ResponseEntity<>(participant.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/group/{id}/participant/{participantId}")
    public ResponseEntity<Long> removeParticipant(@PathVariable Long id, @PathVariable Long participantId) {
        try {
            groupService.removeParticipant(id, participantId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/group/{id}/toss")
    public ResponseEntity<List<Participant>> runToss(@PathVariable Long id) {
        try {
            groupService.runToss(id);
            Optional<Group> group = groupService.getGroupById(id);
            return group.map(value -> ResponseEntity.ok(value.getParticipants())).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
