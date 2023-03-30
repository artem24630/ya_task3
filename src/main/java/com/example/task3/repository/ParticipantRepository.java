package com.example.task3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.task3.domain.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
