package haw.hof.shiftplanning.user;

import haw.hof.shiftplanning.department.Department;
import haw.hof.shiftplanning.user.contract.Contract;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(@RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(this.userService.getUsers(offset, limit), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        return new ResponseEntity<>(this.userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String searchTerm, @RequestParam(required = false) String department, @RequestParam(required = false) String role,
                                               @RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(this.userService.searchUsers(searchTerm, department, role, offset, limit), HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(this.userService.addUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        return new ResponseEntity<>(this.userService.updateUser(id, user), HttpStatus.OK);
    }

    @GetMapping("/{id}/contracts")
    public ResponseEntity<List<Contract>> getUserContracts(@PathVariable int id, @RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(this.userService.getUserContracts(this.userService.getUserById(id), offset, limit), HttpStatus.OK);
    }

    @GetMapping("/{id}/contracts/active")
    public ResponseEntity<Optional<Contract>> getUserContracts(@PathVariable int id) {  //todo: change from Optional?
        return new ResponseEntity<>(this.userService.getActiveContract(this.userService.getUserById(id)), HttpStatus.OK);
    }

    @PostMapping("/{id}/contracts")
    public ResponseEntity<Contract> updateDepartment(@PathVariable Integer id, @RequestBody Contract contract) {
        return new ResponseEntity<>(this.userService.setUserContract(this.userService.getUserById(id), contract), HttpStatus.CREATED);
    }
}
