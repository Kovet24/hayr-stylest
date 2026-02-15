package salon.ekat.hairStylist.service;

import salon.ekat.hairStylist.dto.MasterDTO;

import java.util.List;
import java.util.Optional;

public interface MasterService {
    Optional<MasterDTO> findById(Long id);

    List<MasterDTO> findAll();

    MasterDTO save(MasterDTO masterDTO);

    void deleteById(Long id);
}
