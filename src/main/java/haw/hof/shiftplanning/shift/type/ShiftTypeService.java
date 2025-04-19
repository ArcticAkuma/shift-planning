package haw.hof.shiftplanning.shift.type;

import haw.hof.shiftplanning.department.DepartmentService;
import haw.hof.shiftplanning.exception.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@AllArgsConstructor

@Service
public class ShiftTypeService {

    private final ShiftTypeRepository shiftTypeRepository;
    private final DepartmentService departmentService;

    private final ShiftTypeMapper shiftTypeMapper;

    public List<ShiftType> getShiftTypes(int offset, int limit)  {
        return this.shiftTypeRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public List<ShiftTypeDTO> getShiftTypesDTO(int offset, int limit)  {
        return this.shiftTypeMapper.toDTO(this.getShiftTypes(offset, limit));
    }

    public ShiftType getShiftTypeById(Integer id) {
        return this.shiftTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("shift type", id));
    }

    public ShiftTypeDTO getShiftTypeByODTId(Integer id) {
        return this.shiftTypeMapper.toDTO(this.getShiftTypeById(id));
    }

    public ShiftTypeDTO addShiftType(ShiftTypeDTO shiftType) {
        return this.shiftTypeMapper.toDTO(this.shiftTypeRepository.save(this.shiftTypeMapper.toEntity(shiftType, this.departmentService)));
    }

    @Transactional
    public ShiftTypeDTO updateShiftType(Integer id, ShiftTypeDTO shiftTypeDTO) {
        ShiftType shiftType = this.getShiftTypeById(id);
        shiftType.setDepartment(this.departmentService.getDepartmentById(shiftTypeDTO.departmentId()));
        shiftType.setName(shiftTypeDTO.name());
        shiftType.setDescription(shiftTypeDTO.description());
        shiftType.setColorCode(shiftTypeDTO.colorCode());
        shiftType.setStartTime(shiftTypeDTO.startTime());
        shiftType.setDuration(shiftTypeDTO.duration());
        shiftType.setActive(shiftTypeDTO.active());
        return this.shiftTypeMapper.toDTO(this.shiftTypeRepository.save(shiftType));
    }
}
