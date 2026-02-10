package salon.ekat.hairStylist.mapper;

import lombok.experimental.UtilityClass;
import salon.ekat.hairStylist.dto.ServiceDTO;
import salon.ekat.hairStylist.entity.Service;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ServiceMapper {
    public ServiceDTO mapToDTO(Service service) {
        return ServiceDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .price(service.getPrice())
                .duration(service.getDuration())
                .build();
    }

    public Service mapToObject(ServiceDTO serviceDTO) {
        return Service.builder()
                .id(serviceDTO.getId())
                .name(serviceDTO.getName())
                .description(serviceDTO.getDescription())
                .price(serviceDTO.getPrice())
                .duration(serviceDTO.getDuration())
                .build();
    }

    public List<ServiceDTO> mapToListDTO(List<Service> services) {
        if (services == null) {
            return new ArrayList<>();
        }

        return services.stream()
                .map(ServiceMapper::mapToDTO)
                .toList();
    }

    public List<Service> mapToList(List<ServiceDTO> servicesDTO) {
        if (servicesDTO == null) {
            return new ArrayList<>();
        }

        return servicesDTO.stream()
                .map(ServiceMapper::mapToObject)
                .toList();
    }
}
