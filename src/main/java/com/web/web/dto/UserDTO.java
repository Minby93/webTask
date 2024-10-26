package com.web.web.dto;

import com.web.web.models.Note;
import com.web.web.models.User;

import java.util.List;
import java.util.stream.Collectors;

public record UserDTO(int id, String username, String password, List<Integer> notesId) {
    public UserDTO(User user){
        this(user.getId(), user.getUsername(), user.getPassword(), user.getNotes().stream().map(Note::getId).collect(Collectors.toList()));
    }
}
