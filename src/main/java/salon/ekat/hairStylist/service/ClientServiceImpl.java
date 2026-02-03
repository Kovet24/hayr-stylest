package salon.ekat.hairStylist.service;


import org.springframework.stereotype.Service;
import salon.ekat.hairStylist.dto.ClientDTO;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Override
    public ClientDTO getClient(Long id) {
        return null;
    }

    @Override
    public List<ClientDTO> getClients() {
        return List.of();
    }

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        return null;
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) {
        return null;
    }
}
