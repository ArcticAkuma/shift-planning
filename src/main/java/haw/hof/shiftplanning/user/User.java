package haw.hof.shiftplanning.user;

import haw.hof.shiftplanning.department.Department;
import haw.hof.shiftplanning.user.util.UserLanguage;
import haw.hof.shiftplanning.user.util.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)  //todo: check if more needed

@Entity
public class User {

    //todo: length constraint

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private int id;

    @Setter
    @Column(nullable = false)
    private String firstName;
    @Setter
    @Column(nullable = false)
    private String lastName;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Department department;
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false, unique = true, updatable = false)
    private String email;
    @ToString.Exclude
    @Setter
    @Column(nullable = false)
    private String passwordHash;
    @Setter
    @Column(nullable = false, updatable = false)
    private LocalDateTime lastPasswordChange;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserLanguage language;

    @Column(nullable = false, unique = true, updatable = false)
    private int personnelId;
    @Setter
    private boolean active;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public User(String firstName, String lastName, Department department, String email, String passwordHash, int personnelId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.email = email;
        this.passwordHash = passwordHash;
        this.personnelId = personnelId;
    }

    @PrePersist
    protected void onCreate() {
        this.role = UserRole.EMPLOYEE;
        this.language = UserLanguage.ENGLISH;
        this.active = true;
        this.createdAt = this.lastPasswordChange = LocalDateTime.now();
    }
}
