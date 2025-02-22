package org.example.manage_web.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "meal_record")
public class MealRecord {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AuthUser authUser;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meal_type")
    private String mealType;

    @Column(name = "serving_size")
    private String servingSize;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
