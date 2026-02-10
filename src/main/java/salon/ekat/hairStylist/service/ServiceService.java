package salon.ekat.hairStylist.service;

import salon.ekat.hairStylist.dto.ServiceDTO;

import java.util.List;
import java.util.Optional;

public interface ServiceService {
    Optional<ServiceDTO> findById(Long id);

    List<ServiceDTO> findAll();

    ServiceDTO save(ServiceDTO serviceDTO);

    void deleteById(Long id);
}
