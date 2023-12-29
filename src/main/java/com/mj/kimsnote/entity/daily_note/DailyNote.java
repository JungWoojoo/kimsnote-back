package com.mj.kimsnote.entity.daily_note;

import com.mj.kimsnote.entity.BaseEntity;
import com.mj.kimsnote.entity.daily_note.enums.Mood;
import com.mj.kimsnote.entity.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class DailyNote extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailyNoteId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Mood mood;
    private String title;
    private String content;
    private boolean isPrivacy;
    private String password;

    public DailyNote() {
    }

    @Builder
    public DailyNote(Long dailyNoteId, Member member, Mood mood, String title, String content, boolean isPrivacy, String password) {
        this.dailyNoteId = dailyNoteId;
        this.member = member;
        this.mood = mood;
        this.title = title;
        this.content = content;
        this.isPrivacy = isPrivacy;
        this.password = password;
    }
}
