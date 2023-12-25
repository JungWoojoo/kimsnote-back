package com.mj.kimsnote.service.todo.create;

import com.mj.kimsnote.repository.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoAddService {
    private final TodoRepository todoRepository;
}
