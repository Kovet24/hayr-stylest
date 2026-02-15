package salon.ekat.hairStylist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import salon.ekat.hairStylist.dto.WorkdayDTO;
import salon.ekat.hairStylist.entity.Workday;
import salon.ekat.hairStylist.mapper.WorkdayMapper;
import salon.ekat.hairStylist.repository.WorkdayRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorkdayServiceImpl implements WorkdayService {
    private final WorkdayRepository workdayRepository;

    @Autowired
    public WorkdayServiceImpl(WorkdayRepository workdayRepository) {
        this.workdayRepository = workdayRepository;
    }

    @Override
    public Optional<WorkdayDTO> getWorkday(Long masterId, LocalDate date) {
        Optional<Workday> workday = workdayRepository.findByMasterIdAndDayOfWork(masterId, date);

        if (workday.isPresent()) {
            return workday.map(WorkdayMapper::mapToDTO);
        }

        return Optional.empty();
    }

    @Override
    public List<WorkdayDTO> getAllWorkdays(Long masterId) {
        return WorkdayMapper.mapToListDTO(workdayRepository.findAllByMasterId(masterId));
    }

    @Override
    public WorkdayDTO saveWorkday(WorkdayDTO workdayDTO) {
        Workday savedWorkday = workdayRepository.save(WorkdayMapper.mapToObject(workdayDTO));
        return WorkdayMapper.mapToDTO(savedWorkday);
    }

    @Override
    public void deleteWorkday(Long masterId, LocalDate date) {
        workdayRepository.deleteByMasterIdAndDayOfWork(masterId, date);
    }
}
