package com.mj.kimsnote.service.daily_note.read;

import com.mj.kimsnote.repository.daily_note.DailyNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyNoteFindService {
    private final DailyNoteRepository dailyNoteRepository;

}

