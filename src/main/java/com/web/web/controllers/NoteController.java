package com.web.web.controllers;

import com.web.web.dto.NoteDTO;
import com.web.web.models.Note;
import com.web.web.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }


    @PostMapping("/add")
    public ResponseEntity<String> addNote(@RequestBody Note note){
        try {
            this.noteService.addNote(note);
            return ResponseEntity.status(200).body("Note added successfully");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNote(@PathVariable("id") int id){
        try {
            return ResponseEntity.status(200).body(this.noteService.findNoteById(id));
        }
        catch (ClassNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found");
        }
        catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
