package com.mj.kimsnote.vo.todo.response;

import com.mj.kimsnote.vo.member.response.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class TodoResponse {
    private Long todoId;
    private MemberResponse member;
    private String content;
    private String createdDate;
    private String updatedDate;
}
