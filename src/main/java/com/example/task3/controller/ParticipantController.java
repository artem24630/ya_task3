package com.example.task3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task3.domain.Participant;
import com.example.task3.service.ParticipantService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participants")
public class ParticipantController {
    @Autowired
    ParticipantService participantService;

    @GetMapping
    public ResponseEntity<List<Participant>> findAll() {
        return ResponseEntity.ok(participantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participant> findById(@PathVariable Long id) {
        Optional<Participant> participant = participantService.findById(id);
        return participant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Long> findById(@RequestBody Participant participant) {
        Participant participant1 = participantService.save(participant);
        return new ResponseEntity<>(participant1.getId(), HttpStatus.CREATED);
    }


}
