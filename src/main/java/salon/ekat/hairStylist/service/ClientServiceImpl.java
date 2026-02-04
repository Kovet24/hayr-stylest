package salon.ekat.hairStylist.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import salon.ekat.hairStylist.dto.ClientDTO;
import salon.ekat.hairStylist.entity.Client;
import salon.ekat.hairStylist.mapper.ClientMapper;
import salon.ekat.hairStylist.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<ClientDTO> findById(Long id) {
        Optional<Client> client = clientRepository.findById(id);

        if (client.isPresent()) {
            return client.map(ClientMapper::mapToDTO);
        }
        return Optional.empty();
    }

    @Override
    public List<ClientDTO> findAll() {
        return ClientMapper.mapToListDTO(clientRepository.findAll());
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        Client savedClient = clientRepository.save(ClientMapper.mapToObject(clientDTO));
        return ClientMapper.mapToDTO(savedClient);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
