package haw.hof.shiftplanning;

import haw.hof.shiftplanning.department.Department;
import haw.hof.shiftplanning.department.DepartmentRepository;
import haw.hof.shiftplanning.user.User;
import haw.hof.shiftplanning.user.UserRepository;
import haw.hof.shiftplanning.user.util.UserLanguage;
import haw.hof.shiftplanning.user.util.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ShiftPlanningApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiftPlanningApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(DepartmentRepository departmentRepository, UserRepository userRepository) {
        return args -> {
            Department department = new Department("01");
            departmentRepository.saveAll(List.of(department,
                    new Department("02"),
                    new Department("03")));

            userRepository.saveAll(List.of(
                    new User("Heiz", "Mayer", department, "email@at.com", "TEST", 1),
                    new User("Pia", "LLL", department, "test@at.com", "TEST", 2)
            ));
        };
    }
}
