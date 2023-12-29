package com.mj.kimsnote.repository.daily_note;

import com.mj.kimsnote.entity.daily_note.DailyNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyNoteRepository extends JpaRepository<DailyNote, Long> {
}
