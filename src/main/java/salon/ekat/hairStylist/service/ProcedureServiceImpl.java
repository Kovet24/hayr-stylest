package salon.ekat.hairStylist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import salon.ekat.hairStylist.dto.ProcedureDTO;
import salon.ekat.hairStylist.entity.Procedure;
import salon.ekat.hairStylist.mapper.ProcedureMapper;
import salon.ekat.hairStylist.repository.ProcedureRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProcedureServiceImpl implements ProcedureService {
    private final ProcedureRepository procedureRepository;

    @Autowired
    public ProcedureServiceImpl(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    @Override
    public Optional<ProcedureDTO> findById(Long id) {
        Optional<Procedure> procedure = procedureRepository.findById(id);

        if (procedure.isPresent()) {
            return procedure.map(ProcedureMapper::mapToDTO);
        }

        return Optional.empty();
    }

    @Override
    public List<ProcedureDTO> findAll() {
        return ProcedureMapper.mapToListDTO(procedureRepository.findAll());
    }

    @Override
    public ProcedureDTO save(ProcedureDTO procedureDTO) {
        return ProcedureMapper.mapToDTO(
                procedureRepository.save(ProcedureMapper.mapToObject(procedureDTO))
        );
    }

    @Override
    public void deleteById(Long id) {
        procedureRepository.deleteById(id);
    }
}
