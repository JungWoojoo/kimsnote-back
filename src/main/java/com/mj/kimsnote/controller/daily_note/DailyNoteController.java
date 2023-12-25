package com.mj.kimsnote.controller.daily_note;

import com.mj.kimsnote.service.daily_note.create.DailyNoteAddService;
import com.mj.kimsnote.service.daily_note.read.DailyNoteFindService;
import com.mj.kimsnote.service.daily_note.update.DailyNoteModifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daily-note")
public class DailyNoteController {
    private final DailyNoteAddService dailyNoteAddService;
    private final DailyNoteFindService dailyNoteFindService;
    private final DailyNoteModifyService dailyNoteModifyService;
}
