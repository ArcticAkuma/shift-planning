package haw.hof.shiftplanning;

import haw.hof.shiftplanning.department.Department;
import haw.hof.shiftplanning.department.DepartmentRepository;
import haw.hof.shiftplanning.user.User;
import haw.hof.shiftplanning.user.UserRepository;
import haw.hof.shiftplanning.user.contract.Contract;
import haw.hof.shiftplanning.user.contract.ContractRepository;
import haw.hof.shiftplanning.user.util.UserLanguage;
import haw.hof.shiftplanning.user.util.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ShiftPlanningApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiftPlanningApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(DepartmentRepository departmentRepository, UserRepository userRepository, ContractRepository contractRepository) {
        return args -> {
            Department department = new Department("Logistics 1");
            departmentRepository.saveAll(List.of(department,
                    new Department("Human Resources"),
                    new Department("Logistics 2")));

            User user1 = new User("Heiz", "Mayer", department, "email@at.com", "TEST", 1);
            User user2 = new User("Pia", "LLL", department, "test@at.com", "TEST", 2);
            userRepository.saveAll(List.of(user1, user2));

            user2.setRole(UserRole.ADMINISTRATOR);
            userRepository.save(user2);

            contractRepository.saveAll(List.of(
                    new Contract(user1, 8, LocalDate.of(2025, 3, 31), LocalDate.of(2027, 3, 31)),
                    new Contract(user2, 6, LocalDate.of(2024, 3, 31), LocalDate.of(2025, 3, 31))
            ));
        };
    }
}
