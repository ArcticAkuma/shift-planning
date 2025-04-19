package haw.hof.shiftplanning.user.contract;

import haw.hof.shiftplanning.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)  //todo: check if more needed  + ranks for toString

@Entity
public class Contract {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private int id;

    @Setter  //mapping
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false, referencedColumnName = "id")
    private User user;

    @Column(nullable = false, updatable = false)
    private int workingHours;

    @Column(nullable = false, updatable = false)
    private LocalDate validFrom;
    @Column(nullable = false)
    private LocalDate validTo;

    @ToString.Exclude
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //todo: @NotNull / @Valid ?
    public Contract(User user, int workingHours, LocalDate validFrom, LocalDate validTo) {
        this.user = user;
        this.workingHours = workingHours;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @ToString.Include(name = "user")
    private String getUserName() {
        return this.user != null ? this.user.getFirstName() + " " + this.user.getLastName() : null;
    }
}
