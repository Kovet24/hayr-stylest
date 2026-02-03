package salon.ekat.hairStylist.mapper;

import lombok.experimental.UtilityClass;
import salon.ekat.hairStylist.dto.ClientDTO;
import salon.ekat.hairStylist.entity.Client;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ClientMapper {
    public ClientDTO mapToDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .number(client.getNumber())
                .build();
    }

    public Client mapToObject(ClientDTO clientDTO) {
        return Client.builder()
                .id(clientDTO.getId())
                .name(clientDTO.getName())
                .number(clientDTO.getNumber())
                .build();
    }

    public List<ClientDTO> mapToListDTO(List<Client> clients) {
        if (clients == null) {
            return new ArrayList<>();
        }

        return clients.stream()
                .map(ClientMapper::mapToDTO)
                .toList();
    }

    public List<Client> mapToList(List<ClientDTO> clientsDTO) {
        if (clientsDTO == null) {
            return new ArrayList<>();
        }

        return clientsDTO.stream()
                .map(ClientMapper::mapToObject)
                .toList();
    }
}
