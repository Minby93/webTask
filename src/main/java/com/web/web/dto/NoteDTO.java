package com.web.web.dto;

import com.web.web.models.Note;

public record NoteDTO(int id, String data) {
    public NoteDTO(Note note){
        this(note.getId(), note.getData());
    }
}
