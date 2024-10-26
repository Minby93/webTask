package com.web.web.services;

import com.web.web.dto.NoteDTO;
import com.web.web.models.Note;
import com.web.web.models.User;
import com.web.web.repositories.NoteRepository;
import com.web.web.repositories.UserRepository;
import com.web.web.security.CustomEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Service
public class NoteService {
    private NoteRepository noteRepository;
    private CustomEncoder customEncoder;
    private EncryptService encryptService;
    private UserRepository userRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, CustomEncoder customEncoder,  EncryptService encryptService, UserRepository userRepository){
        this.customEncoder = customEncoder;
        this.noteRepository = noteRepository;
        this.encryptService = encryptService;
        this.userRepository = userRepository;
    }

    public void addNote(Note note) throws AccessDeniedException {
        Note newNote = new Note();
        newNote.setId(note.getId());
        newNote.setData(customEncoder.encode(note.getData()));

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof MyUserDetails) {
            username = ((MyUserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        Optional<User> user = this.userRepository.findByUsername(username);
        if (user.isEmpty()){
            throw new AccessDeniedException("Access denied");
        }
        newNote.setUser(user.get());
        this.noteRepository.save(newNote);
    }

    public NoteDTO findNoteById(int id) throws ClassNotFoundException, AccessDeniedException {
        Optional<Note> noteOpt = this.noteRepository.findById(id);

        if(noteOpt.isEmpty()) {
            throw new ClassNotFoundException("Note not found");
        }
        Note note = noteOpt.get();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof MyUserDetails) {
            username = ((MyUserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        Optional<User> user =  this.userRepository.findByUsername(username);

        if (user.isPresent() && (user.get().getId() == note.getUser().getId())) {
            note.setData(this.encryptService.decodeMessage(note.getData()));
            return new NoteDTO(note);
        }
        else throw new AccessDeniedException("Forbidden");

    }
}
