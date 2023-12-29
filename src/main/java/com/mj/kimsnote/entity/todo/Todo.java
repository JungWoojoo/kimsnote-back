package com.mj.kimsnote.entity.todo;

import com.mj.kimsnote.entity.BaseEntity;
import com.mj.kimsnote.entity.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String Content;

    public Todo() {
    }

    @Builder
    public Todo(Long todoId, Member member, String content) {
        this.todoId = todoId;
        this.member = member;
        Content = content;
    }
}
