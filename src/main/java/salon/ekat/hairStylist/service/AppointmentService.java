package salon.ekat.hairStylist.service;

import salon.ekat.hairStylist.dto.AppointmentDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Optional<AppointmentDTO> findById(Long id);

    Optional<AppointmentDTO> findByMasterIdAndStartDateTime(Long masterId, LocalDateTime startDateTime);

    Optional<AppointmentDTO> findByClientIdAndStartDateTime(Long clientId, LocalDateTime startDateTime);

    List<AppointmentDTO> findAllByMasterId(Long masterId);

    List<AppointmentDTO> findAllByClientId(Long clientId);

    AppointmentDTO save(AppointmentDTO appointmentDTO);

    AppointmentDTO updateStatusById(Long id, String status);

    void deleteById(Long id);
/*
    boolean validateWorkday();

    boolean validateBreak();

    boolean validateNoConflicts();*/
}
