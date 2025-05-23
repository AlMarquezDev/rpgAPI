package com.almardev.rpgAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Setter
@Getter
@Entity
@Table(name = "save_states", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "slot"})
})
public class SaveState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int slot;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "save_data", columnDefinition = "jsonb")
    private String saveData;

}