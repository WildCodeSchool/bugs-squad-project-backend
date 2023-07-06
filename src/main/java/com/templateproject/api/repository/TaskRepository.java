package com.templateproject.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.templateproject.api.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
