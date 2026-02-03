package salon.ekat.hairStylist.service;

import salon.ekat.hairStylist.dto.ClientDTO;

import java.util.List;

public interface ClientService {
    ClientDTO getClient(Long id);

    List<ClientDTO> getClients();

    ClientDTO createClient(ClientDTO clientDTO);

    ClientDTO updateClient(ClientDTO clientDTO);
}
