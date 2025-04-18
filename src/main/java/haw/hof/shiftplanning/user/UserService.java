package haw.hof.shiftplanning.user;

import haw.hof.shiftplanning.department.Department;
import haw.hof.shiftplanning.department.DepartmentRepository;
import haw.hof.shiftplanning.exception.exception.EntityNotFoundException;
import haw.hof.shiftplanning.user.contract.Contract;
import haw.hof.shiftplanning.user.contract.ContractRepository;
import haw.hof.shiftplanning.user.util.UserRole;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    //todo: add oauth2 (DefaultOAuth2UserService ?)

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final ContractRepository contractRepository;

    public UserService(UserRepository userRepository, DepartmentRepository departmentRepository, ContractRepository contractRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.contractRepository = contractRepository;
    }

    public List<User> getUsers(int offset, int limit) {
        return this.userRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public User getUserById(int id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user", id));
    }

    public List<User> searchUsers(String searchTerm, String departmentName, String roleName, int offset, int limit) {
        return userRepository.findAll((root, query, cb) -> {

            Department department = departmentName != null && !departmentName.isBlank() ?
                    this.departmentRepository.findDepartmentByName(departmentName).orElseThrow(() -> new EntityNotFoundException("department", departmentName)) : null;
            UserRole role = roleName != null && !roleName.isBlank() ? UserRole.getUserRole(roleName) : null;

            List<Predicate> predicates = new ArrayList<>();
            if (searchTerm != null && !searchTerm.isBlank())    predicates.add(cb.like(cb.lower(root.get("name")), "%" + searchTerm.toLowerCase() + "%"));
            if (department != null) predicates.add(cb.equal(root.get("department"), department));
            if (role != null)   predicates.add(cb.equal(root.get("role"), role));

            return cb.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(offset, limit)).getContent();

    }

    public List<User> getByDepartment(Department department, int offset, int limit) {
        return this.userRepository.findUserByDepartment(department, PageRequest.of(offset, limit));
    }

    public User addUser(User user) {

            return this.userRepository.save(new User(user.getFirstName(), user.getLastName(), user.getDepartment(), user.getEmail(), user.getPasswordHash(), user.getPersonnelId()));

    }

    public User updateUser(Integer id, User updatedUser) {
        User user = getUserById(id);
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setDepartment(updatedUser.getDepartment());
        user.setRole(updatedUser.getRole());
        user.setPassword(updatedUser.getPasswordHash());  //todo
        user.setLanguage(updatedUser.getLanguage());
        user.setActive(updatedUser.isActive());
        return this.userRepository.save(user);
    }

    public List<Contract> getUserContracts(User user, int offset, int limit) {
        return this.contractRepository.findContractsByUser(user, PageRequest.of(offset, limit));
    }

    public Optional<Contract> getActiveContract(User user) {
        Optional<Contract> activeContract = this.contractRepository.findFirstByUserAndValidToGreaterThanEqualOrderByCreatedAtDesc(user, LocalDate.now());
        if (activeContract.isEmpty() && user.isActive())    log.warn("No active contract found for user {}", user);
        return activeContract;
    }

    @Transactional
    public Contract setUserContract(User user, Contract contract) {
        if (contract.getValidTo().isBefore(LocalDate.now()) || contract.getValidTo().isBefore(contract.getValidFrom())) {  //todo: check if workingHours is in legal boundaries! -> config system [sprint2]
            throw new IllegalArgumentException("Contract is not valid");
        }

        return this.contractRepository.save(new Contract(user, contract.getWorkingHours(), contract.getValidFrom(), contract.getValidTo()));
    }
}
