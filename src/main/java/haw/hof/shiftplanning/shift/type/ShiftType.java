package haw.hof.shiftplanning.shift.type;

import haw.hof.shiftplanning.department.Department;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class ShiftType {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private int id;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Department department;

    @Setter
    @Column(nullable = false)
    private String name;
    @Setter
    @Column(nullable = false)
    private String description;
    @Setter
    @Column(nullable = false)
    private String colorCode;

    @Setter
    @Column(nullable = false)
    private LocalTime startTime;
    @Setter
    @Column(nullable = false)
    private int duration;

    @Setter
    private boolean active;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ShiftType(Department department, String name, String description, String colorCode, LocalTime startTime, int duration) {
        this.department = department;
        this.name = name;
        this.description = description;
        this.colorCode = colorCode;
        this.startTime = startTime;
        this.duration = duration;
        this.active = true;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ShiftType{" +
                "id=" + this.id +
                ", department=" + this.department.getName() +
                ", name='" + this.name + '\'' +
                ", description='" + this.description + '\'' +
                ", colorCode='" + this.colorCode + '\'' +
                ", startTime=" + this.startTime +
                ", duration=" + this.duration +
                ", active=" + this.active +
                '}';
    }
}
