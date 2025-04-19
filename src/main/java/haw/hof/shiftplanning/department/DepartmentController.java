package haw.hof.shiftplanning.department;

import haw.hof.shiftplanning.shift.type.ShiftTypeDTO;
import haw.hof.shiftplanning.user.User;
import haw.hof.shiftplanning.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping(path = "/department")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getDepartments(@RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(this.departmentService.getDepartmentsDTO(offset, limit), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Integer id) {
        return new ResponseEntity<>(this.departmentService.getDepartmentDTOById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/users")  //todo: DTO
    public ResponseEntity<List<User>> getDepartmentsEmployees(@PathVariable Integer id, @RequestParam(defaultValue = "true") Boolean active, @RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(this.userService.getByDepartment(this.departmentService.getDepartmentById(id), active, offset, limit), HttpStatus.OK);
    }

    @GetMapping("/{id}/shift-types")
    public ResponseEntity<List<ShiftTypeDTO>> getShiftTypes(@PathVariable Integer id, @RequestParam(defaultValue = "true") Boolean active, @RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(this.departmentService.getShiftTypes(this.departmentService.getDepartmentById(id), active, offset, limit), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DepartmentDTO>> searchDepartments(@RequestParam  String searchTerm, @RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(this.departmentService.searchDepartments(searchTerm, offset, limit), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody @Valid DepartmentDTO department) {
        return new ResponseEntity<>(this.departmentService.addDepartment(department), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Integer id, @RequestBody @Valid DepartmentDTO department) {
        return new ResponseEntity<>(this.departmentService.updateDepartment(id, department), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentDTO> deleteDepartment(@PathVariable Integer id) {
        return new ResponseEntity<>(this.departmentService.deleteDepartment(id), HttpStatus.OK);
    }
}
