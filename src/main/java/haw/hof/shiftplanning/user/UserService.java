package haw.hof.shiftplanning.user;

import haw.hof.shiftplanning.department.Department;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(null);  //todo: exception
    }

    public List<User> getByDepartment(Department department, int offset, int limit) {
        return this.userRepository.findByDepartment(department, PageRequest.of(offset, limit));
    }
}
