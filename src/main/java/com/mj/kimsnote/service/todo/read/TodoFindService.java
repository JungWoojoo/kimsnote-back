package com.mj.kimsnote.service.todo.read;

import com.mj.kimsnote.repository.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoFindService {
    private final TodoRepository todoRepository;
}
