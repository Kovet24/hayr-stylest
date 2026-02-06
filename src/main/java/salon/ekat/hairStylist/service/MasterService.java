package salon.ekat.hairStylist.service;

import salon.ekat.hairStylist.dto.MasterDTO;

import java.util.List;
import java.util.Optional;

public interface MasterService {
    List<MasterDTO> findAll();

    Optional<MasterDTO> findById(Long id);

    MasterDTO save(MasterDTO masterDTO);

    void deleteById(Long id);
}
