package com.templateproject.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.templateproject.api.entity.ToDoList;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, Long>{

  public List<ToDoList> findByIsFavorite(boolean isFavorite);
}
