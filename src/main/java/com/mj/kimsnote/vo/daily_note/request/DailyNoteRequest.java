package com.mj.kimsnote.vo.daily_note.request;

import com.mj.kimsnote.entity.daily_note.enums.Mood;
import lombok.Getter;

@Getter
public class DailyNoteRequest {
    private Long memberId;
    private Mood mood;
    private String title;
    private String content;
    private boolean isPrivacy;
    private String password;
}
