package com.web.web.dto;

import com.web.web.models.Note;

public record NoteDTO(int id, String data, int userId) {
    public NoteDTO(Note note){
        this(note.getId(), note.getData(), note.getUser().getId());
    }
}
