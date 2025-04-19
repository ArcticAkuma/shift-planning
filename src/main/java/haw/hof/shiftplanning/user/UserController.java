package haw.hof.shiftplanning.user;

import haw.hof.shiftplanning.user.contract.Contract;
import haw.hof.shiftplanning.user.contract.ContractDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@RestController
@RequestMapping(path = "/user")
public class UserController {

    //todo: change to DTO

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(this.userService.getUsersDTO(offset, limit), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
        return new ResponseEntity<>(this.userService.getUserDTOById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam(required = false) String searchTerm, @RequestParam(required = false) String department, @RequestParam(required = false) String role,
                                               @RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(this.userService.searchUsers(searchTerm, department, role, offset, limit), HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO user) {
        return new ResponseEntity<>(this.userService.addUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody @Valid UserDTO user) {
        return new ResponseEntity<>(this.userService.updateUser(id, user), HttpStatus.OK);
    }

    @GetMapping("/{id}/contracts")
    public ResponseEntity<List<ContractDTO>> getUserContracts(@PathVariable int id, @RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(this.userService.getUserContractsDTO(this.userService.getUserById(id), offset, limit), HttpStatus.OK);
    }

    @GetMapping("/{id}/contracts/active")
    public ResponseEntity<ContractDTO> getActiveContract(@PathVariable int id) {
        return new ResponseEntity<>(this.userService.getActiveContractDTO(this.userService.getUserById(id)), HttpStatus.OK);
    }

    @PostMapping("/{id}/contracts")
    public ResponseEntity<ContractDTO> setUserContract(@PathVariable Integer id, @RequestBody @Valid ContractDTO contract) {
        return new ResponseEntity<>(this.userService.setUserContract(this.userService.getUserById(id), contract), HttpStatus.CREATED);
    }
}
