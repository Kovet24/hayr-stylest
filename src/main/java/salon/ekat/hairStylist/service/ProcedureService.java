package salon.ekat.hairStylist.service;

import salon.ekat.hairStylist.dto.ProcedureDTO;

import java.util.List;
import java.util.Optional;

public interface ProcedureService {
    Optional<ProcedureDTO> findById(Long id);

    List<ProcedureDTO> findAll();

    ProcedureDTO save(ProcedureDTO procedureDTO);

    void deleteById(Long id);
}
