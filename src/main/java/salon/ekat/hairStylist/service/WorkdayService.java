package salon.ekat.hairStylist.service;

import salon.ekat.hairStylist.dto.WorkdayDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkdayService {
    Optional<WorkdayDTO> getWorkday(Long masterId, LocalDate date);

    List<WorkdayDTO> getAllWorkdays(Long masterId);

    WorkdayDTO save(WorkdayDTO workdayDTO);

    void delete(Long masterId, LocalDate date);
}
