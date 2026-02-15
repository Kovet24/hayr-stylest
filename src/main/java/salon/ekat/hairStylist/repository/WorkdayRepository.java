package salon.ekat.hairStylist.repository;

import salon.ekat.hairStylist.entity.Workday;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkdayRepository {
    Optional<Workday> findByMasterIdAndDayOfWork(Long masterId, LocalDate date);

    List<Workday> findAllByMasterId(Long masterId);

    Workday save(Workday workday);

    void deleteByMasterIdAndDayOfWork(Long masterId, LocalDate date);
}
