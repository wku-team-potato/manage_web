package org.example.manage_web.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @Column(name = "user_id")
    private Long userid;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AuthUser authUser;

    @Column(name = "username")
    private String username;
    @Column(name = "total_points")
    private int totalPoints;
    @Column(name = "height")
    private double height;
    @Column(name = "weight")
    private double weight;
    @Column(name = "consecutive_attendance_days")
    private int consecutiveAttendanceDays;
    @Column(name = "cumulative_attendance_days")
    private int cumulativeAttendanceDays;
    @Column(name = "consecutive_goals_achieved")
    private int consecutiveGoalsAchieved;
    @Column(name = "cumulative_goals_achieved")
    private int cumulativeGoalsAchieved;
    @Column(name = "last_attendance_date")
    private String lastAttendanceDate;
    @Column(name = "last_goal_date")
    private String lastGoalDate;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private String updatedAt;
}
