package com.example.task3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.task3.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
