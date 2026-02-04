package salon.ekat.hairStylist.service;

import salon.ekat.hairStylist.dto.ClientDTO;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<ClientDTO> findById(Long id);

    List<ClientDTO> findAll();

    ClientDTO save(ClientDTO clientDTO);

    void deleteById(Long id);
}
