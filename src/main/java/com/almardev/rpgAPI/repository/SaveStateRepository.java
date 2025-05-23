package com.almardev.rpgAPI.repository;

import com.almardev.rpgAPI.model.SaveState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveStateRepository extends JpaRepository<SaveState, Long> {
}