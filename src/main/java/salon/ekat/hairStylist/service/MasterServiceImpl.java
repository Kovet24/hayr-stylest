package salon.ekat.hairStylist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import salon.ekat.hairStylist.dto.MasterDTO;
import salon.ekat.hairStylist.entity.Master;
import salon.ekat.hairStylist.mapper.MasterMapper;
import salon.ekat.hairStylist.repository.MasterRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MasterServiceImpl implements MasterService {
    private final MasterRepository masterRepository;

    @Autowired
    public MasterServiceImpl(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    @Override
    public Optional<MasterDTO> findById(Long id) {
        Optional<Master> master = masterRepository.findById(id);

        if (master.isPresent()) {
            return master.map(MasterMapper::mapToDTO);
        }
        return Optional.empty();
    }

    @Override
    public List<MasterDTO> findAll() {
        return MasterMapper.mapToListDTO(masterRepository.findAll());
    }

    @Override
    public MasterDTO save(MasterDTO masterDTO) {
        Master savedMaster = masterRepository.save(MasterMapper.mapToObject(masterDTO));
        return MasterMapper.mapToDTO(savedMaster);
    }

    @Override
    public void deleteById(Long id) {
        masterRepository.deleteById(id);
    }
}
