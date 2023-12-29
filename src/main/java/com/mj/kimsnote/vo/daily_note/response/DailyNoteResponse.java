package com.mj.kimsnote.vo.daily_note.response;

import com.mj.kimsnote.entity.daily_note.enums.Mood;
import com.mj.kimsnote.vo.member.response.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class DailyNoteResponse {
    private Long dailyNoteId;
    private MemberResponse member;
    private Mood mood;
    private String title;
    private String content;
    private boolean isPrivacy;
    private String password;
    private String createdDate;
    private String updatedDate;
}
