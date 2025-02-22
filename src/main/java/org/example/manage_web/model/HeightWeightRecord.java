package org.example.manage_web.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "height_weight_record")
public class HeightWeightRecord {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AuthUser authUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // @Column(name = "user_id")
    // private Long userId;
    @Column(name = "height")
    private double height;
    @Column(name = "weight")
    private double weight;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
