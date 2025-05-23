package com.almardev.rpgAPI.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveStateDTO {
    private Long id;
    private int slot;
    private String saveData;

}