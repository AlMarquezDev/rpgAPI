package com.almardev.rpgAPI.service;

import com.almardev.rpgAPI.model.SaveState;
import com.almardev.rpgAPI.model.User;
import com.almardev.rpgAPI.repository.SaveStateRepository;
import com.almardev.rpgAPI.repository.UserRepository;
import com.almardev.rpgAPI.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SaveStateRepository saveStateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        return userRepository.save(user);
    }

    public String authenticateUser(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            return JwtUtil.generateToken(user.getUsername());
        }
        return null;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void saveOrUpdateSaveState(User user, int slot, String saveData) {
        SaveState target = null;
        List<SaveState> states = user.getSaveStates();

        for (SaveState state : states) {
            if (state.getSlot() == slot) {
                target = state;
                break;
            }
        }

        if (target != null) {
            target.setSaveData(saveData);
            saveStateRepository.save(target);
        } else {
            SaveState newState = new SaveState();
            newState.setUser(user);
            newState.setSlot(slot);
            newState.setSaveData(saveData);
            user.getSaveStates().add(newState);
            userRepository.save(user);
        }
    }
}