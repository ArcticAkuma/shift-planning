package haw.hof.shiftplanning.department;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)  //todo: check if more needed

@Entity
public class Department {

    //todo: length constraints

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private int id;

    @Setter
    @Column(unique = true, nullable = false)
    private String name;

    @ToString.Exclude
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Department(String name) {
        this.name = name;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
