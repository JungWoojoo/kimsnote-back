package com.mj.kimsnote.service.daily_note.create;

import com.mj.kimsnote.repository.daily_note.DailyNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyNoteAddService {
    private final DailyNoteRepository dailyNoteRepository;

}
