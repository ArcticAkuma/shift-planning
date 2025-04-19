package haw.hof.shiftplanning.shift;

import haw.hof.shiftplanning.shift.type.ShiftTypeDTO;
import haw.hof.shiftplanning.shift.type.ShiftTypeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor

@RestController
@RequestMapping(path = "/shift")
public class ShiftController {

    private final ShiftTypeService shiftTypeService;

    @GetMapping("/shift-type")
    public ResponseEntity<List<ShiftTypeDTO>> getShiftTypes(@RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(this.shiftTypeService.getShiftTypesDTO(offset, limit), HttpStatus.OK);
    }

    @GetMapping("/shift-type/{id}")
    public ResponseEntity<ShiftTypeDTO> getShiftTypeById(@PathVariable Integer id) {
        return new ResponseEntity<>(this.shiftTypeService.getShiftTypeByODTId(id), HttpStatus.OK);
    }

    @PostMapping("/shift-type")
    public ResponseEntity<ShiftTypeDTO> createDepartment(@RequestBody @Valid ShiftTypeDTO shiftType) {
        return new ResponseEntity<>(this.shiftTypeService.addShiftType(shiftType), HttpStatus.CREATED);
    }

    @PutMapping("/shift-type/{id}")
    public ResponseEntity<ShiftTypeDTO> updateDepartment(@PathVariable Integer id, @RequestBody @Valid ShiftTypeDTO shiftType) {
        return new ResponseEntity<>(this.shiftTypeService.updateShiftType(id, shiftType), HttpStatus.OK);
    }
}
