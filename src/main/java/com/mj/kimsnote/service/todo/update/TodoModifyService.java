package com.mj.kimsnote.service.todo.update;

import com.mj.kimsnote.repository.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoModifyService {
    private final TodoRepository todoRepository;
}
