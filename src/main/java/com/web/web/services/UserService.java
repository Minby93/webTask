package com.web.web.services;

import com.web.web.dto.UserDTO;
import com.web.web.models.Note;
import com.web.web.models.User;
import com.web.web.repositories.UserRepository;
import com.web.web.security.CustomEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private CustomEncoder customPasswordEncoder;
    @Autowired
    public UserService(UserRepository userRepository,
                       @Lazy CustomEncoder customPasswordEncoder){
        this.userRepository = userRepository;
        this.customPasswordEncoder = customPasswordEncoder;
    }

    public void registerUser(User user) {
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(customPasswordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);
    }
    public UserDTO findUserById(int id) throws ClassNotFoundException {
        Optional<User> userOpt = userRepository.findById(id);

        if(userOpt.isEmpty()) {
            throw new ClassNotFoundException("User not found");
        }
        return new UserDTO(userOpt.get());

    }
}
