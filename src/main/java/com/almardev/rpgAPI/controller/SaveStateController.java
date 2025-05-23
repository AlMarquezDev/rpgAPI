package com.almardev.rpgAPI.controller;

import com.almardev.rpgAPI.dto.SaveStateDTO;
import com.almardev.rpgAPI.model.User;
import com.almardev.rpgAPI.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/game/save-states")
public class SaveStateController {

    @Autowired
    private UserService userService;

    private final Gson gson = new Gson();

    @GetMapping
    public ResponseEntity<?> getSaveStates(Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.findByUsername(username);

        List<SaveStateDTO> dtoList = user.getSaveStates().stream().map(state -> {
            SaveStateDTO dto = new SaveStateDTO();
            dto.setId(state.getId());
            dto.setSlot(state.getSlot());
            dto.setSaveData(state.getSaveData());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{slot}")
    public ResponseEntity<?> getSaveStateBySlot(Authentication authentication, @PathVariable int slot) {
        if (slot < 1 || slot > 3) {
            return ResponseEntity.badRequest().body("Slot must be between 1 and 3");
        }
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.findByUsername(username);
        return user.getSaveStates().stream()
                .filter(state -> state.getSlot() == slot)
                .findFirst()
                .map(state -> {
                    SaveStateDTO dto = new SaveStateDTO();
                    dto.setId(state.getId());
                    dto.setSlot(state.getSlot());
                    dto.setSaveData(state.getSaveData());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{slot}")
    public ResponseEntity<?> saveOrUpdateSaveState(Authentication authentication,
                                                   @PathVariable int slot,
                                                   @RequestBody Map<String, Object> saveState) {
        if (slot < 1 || slot > 3) {
            return ResponseEntity.badRequest().body("Slot must be between 1 and 3");
        }
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.findByUsername(username);
        String jsonState = gson.toJson(saveState);
        userService.saveOrUpdateSaveState(user, slot, jsonState);
        return ResponseEntity.ok("Game saved successfully in slot " + slot);
    }
}