package com.mj.kimsnote.repository.todo;

import com.mj.kimsnote.entity.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
