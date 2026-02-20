package salon.ekat.hairStylist.mapper;

import lombok.experimental.UtilityClass;
import salon.ekat.hairStylist.dto.ProcedureDTO;
import salon.ekat.hairStylist.entity.Procedure;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ProcedureMapper {
    public ProcedureDTO mapToDTO(Procedure procedure) {
        return ProcedureDTO.builder()
                .id(procedure.getId())
                .name(procedure.getName())
                .description(procedure.getDescription())
                .price(procedure.getPrice())
                .duration(procedure.getDuration())
                .build();
    }

    public Procedure mapToObject(ProcedureDTO procedureDTO) {
        return Procedure.builder()
                .id(procedureDTO.getId())
                .name(procedureDTO.getName())
                .description(procedureDTO.getDescription())
                .price(procedureDTO.getPrice())
                .duration(procedureDTO.getDuration())
                .build();
    }

    public List<ProcedureDTO> mapToListDTO(List<Procedure> procedures) {
        if (procedures == null) {
            return new ArrayList<>();
        }

        return procedures.stream()
                .map(ProcedureMapper::mapToDTO)
                .toList();
    }

    public List<Procedure> mapToList(List<ProcedureDTO> proceduresDTO) {
        if (proceduresDTO == null) {
            return new ArrayList<>();
        }

        return proceduresDTO.stream()
                .map(ProcedureMapper::mapToObject)
                .toList();
    }
}
