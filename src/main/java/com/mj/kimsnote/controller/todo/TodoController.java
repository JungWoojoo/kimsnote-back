package com.mj.kimsnote.controller.todo;

import com.mj.kimsnote.service.todo.create.TodoAddService;
import com.mj.kimsnote.service.todo.read.TodoFindService;
import com.mj.kimsnote.service.todo.update.TodoModifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoAddService todoAddService;
    private final TodoFindService todoFindService;
    private final TodoModifyService todoModifyService;
}
