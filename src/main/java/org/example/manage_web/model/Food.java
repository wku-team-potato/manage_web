package org.example.manage_web.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "nutrition_foods")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;
    @Column(name = "food_name")
    private String name;
    @Column(name = "serving_size")
    private String servingSize;
    @Column(name = "energy")
    private String energy;
    @Column(name = "carbohydrate")
    private String carbohydrate;
    @Column(name = "protein")
    private String protein;
    @Column(name = "fat")
    private String fat;
    @Column(name = "sugars")
    private String sugars;
    @Column(name = "calcium")
    private String calcium;
    @Column(name = "phosphorus")
    private String phosphorus;
    @Column(name = "sodium")
    private String sodium;
    @Column(name = "potassium")
    private String potassium;
    @Column(name = "magnesium")
    private String magnesium;
    @Column(name = "iron")
    private String iron;
    @Column(name = "zinc")
    private String zinc;
    @Column(name = "cholesterol")
    private String cholesterol;
    @Column(name = "trans_fat")
    private String transFat;
}
