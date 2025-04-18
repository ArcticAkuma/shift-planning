package haw.hof.shiftplanning.user.contract;

import haw.hof.shiftplanning.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)  //todo: check if more needed

@Entity
public class Contract {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false, referencedColumnName = "id")
    private User user;

    @Column(nullable = false, updatable = false)
    private int workingHours;

    @Column(nullable = false, updatable = false)
    private LocalDateTime validFrom;
    @Setter
    @Column(nullable = false)
    private LocalDateTime validTo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
