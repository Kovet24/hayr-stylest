package salon.ekat.hairStylist.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import salon.ekat.hairStylist.dto.AppointmentDTO;
import salon.ekat.hairStylist.entity.Appointment;
import salon.ekat.hairStylist.entity.Procedure;
import salon.ekat.hairStylist.mapper.AppointmentMapper;
import salon.ekat.hairStylist.repository.AppointmentRepository;
import salon.ekat.hairStylist.repository.ProcedureRepository;
import salon.ekat.hairStylist.repository.WorkdayRepository;
import salon.ekat.hairStylist.validator.AppointmentValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final WorkdayRepository workdayRepository;
    private final ProcedureRepository procedureRepository;

    @Autowired
    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            WorkdayRepository workdayRepository,
            ProcedureRepository procedureRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.workdayRepository = workdayRepository;
        this.procedureRepository = procedureRepository;
    }

    @Override
    public Optional<AppointmentDTO> findById(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()) {
            return appointment.map(AppointmentMapper::mapToDTO);
        }

        return Optional.empty();
    }

    @Override
    public Optional<AppointmentDTO> findByMasterIdAndStartDateTime(Long masterId, LocalDateTime startDateTime) {
        Optional<Appointment> appointment =
                appointmentRepository.findByMasterIdAndStartDateTime(masterId, startDateTime);

        if (appointment.isPresent()) {
            return appointment.map(AppointmentMapper::mapToDTO);
        }

        return Optional.empty();
    }

    @Override
    public Optional<AppointmentDTO> findByClientIdAndStartDateTime(Long clientId, LocalDateTime startDateTime) {
        Optional<Appointment> appointment =
                appointmentRepository.findByClientIdAndStartDateTime(clientId, startDateTime);

        if (appointment.isPresent()) {
            return appointment.map(AppointmentMapper::mapToDTO);
        }

        return Optional.empty();
    }

    @Override
    public List<AppointmentDTO> findAllByMasterId(Long masterId) {
        return AppointmentMapper.mapToListDTO(appointmentRepository.findAllByMasterId(masterId));
    }

    @Override
    public List<AppointmentDTO> findAllByClientId(Long clientId) {
        return AppointmentMapper.mapToListDTO(appointmentRepository.findAllByClientId(clientId));
    }

    @Override
    public AppointmentDTO save(AppointmentDTO appointmentDTO) {
        Appointment appointment = AppointmentMapper.mapToObject(appointmentDTO);
        AppointmentValidator.validateAppointment(appointment, appointmentRepository, workdayRepository);
        changeEndDateTime(appointment);
        return AppointmentMapper.mapToDTO(appointmentRepository.save(appointment));
    }

    private void changeEndDateTime(Appointment appointment) {
        Optional<Procedure> procedure = procedureRepository.findById(appointment.getProcedureId());
        int duration = 0;

        if (procedure.isPresent()) {
            duration = procedure.get().getDuration();
        }

        LocalDateTime startDateTime = appointment.getStartDateTime();
        appointment.setEndDateTime(startDateTime.plusMinutes(duration));
    }

    @Override
    public AppointmentDTO updateStatusById(Long id, String status) {
        // В репозитории ещё нет реализации
        Appointment updatedAppointment = appointmentRepository.updateStatusById(id, status);
        return AppointmentMapper.mapToDTO(updatedAppointment);
    }

    @Override
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }
}
