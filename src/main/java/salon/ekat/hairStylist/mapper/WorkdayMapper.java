package salon.ekat.hairStylist.mapper;

import lombok.experimental.UtilityClass;
import salon.ekat.hairStylist.dto.WorkdayDTO;
import salon.ekat.hairStylist.entity.Workday;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class WorkdayMapper {
    public WorkdayDTO mapToDTO(Workday workday) {
        return WorkdayDTO.builder()
                .masterId(workday.getMasterId())
                .dayOfWork(workday.getDayOfWork())
                .shiftStart(workday.getShiftStart())
                .shiftEnd(workday.getShiftEnd())
                .breakStart(workday.getBreakStart())
                .breakEnd(workday.getBreakEnd())
                .build();
    }

    public Workday mapToObject(WorkdayDTO workdayDTO) {
        return Workday.builder()
                .masterId(workdayDTO.getMasterId())
                .dayOfWork(workdayDTO.getDayOfWork())
                .shiftStart(workdayDTO.getShiftStart())
                .shiftEnd(workdayDTO.getShiftEnd())
                .breakStart(workdayDTO.getBreakStart())
                .breakEnd(workdayDTO.getBreakEnd())
                .build();
    }

    public List<WorkdayDTO> mapToListDTO(List<Workday> workdays) {
        if (workdays == null) {
            return new ArrayList<>();
        }

        return workdays.stream()
                .map(WorkdayMapper::mapToDTO)
                .toList();
    }

    public List<Workday> mapToList(List<WorkdayDTO> workdaysDTO) {
        if (workdaysDTO == null) {
            return new ArrayList<>();
        }

        return workdaysDTO.stream()
                .map(WorkdayMapper::mapToObject)
                .toList();
    }
}
