package salon.ekat.hairStylist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import salon.ekat.hairStylist.dto.ServiceDTO;
import salon.ekat.hairStylist.mapper.ServiceMapper;
import salon.ekat.hairStylist.repository.ServiceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Optional<ServiceDTO> findById(Long id) {
        Optional<salon.ekat.hairStylist.entity.Service> service = serviceRepository.findById(id);

        if (service.isPresent()) {
            return service.map(ServiceMapper::mapToDTO);
        }

        return Optional.empty();
    }

    @Override
    public List<ServiceDTO> findAll() {
        return ServiceMapper.mapToListDTO(serviceRepository.findAll());
    }

    @Override
    public ServiceDTO save(ServiceDTO serviceDTO) {
        return ServiceMapper.mapToDTO(
                serviceRepository.save(ServiceMapper.mapToObject(serviceDTO))
        );
    }

    @Override
    public void deleteById(Long id) {
        serviceRepository.deleteById(id);
    }
}
