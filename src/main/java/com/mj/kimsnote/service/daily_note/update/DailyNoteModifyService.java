package com.mj.kimsnote.service.daily_note.update;

import com.mj.kimsnote.repository.daily_note.DailyNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyNoteModifyService {
    private final DailyNoteRepository dailyNoteRepository;

}
