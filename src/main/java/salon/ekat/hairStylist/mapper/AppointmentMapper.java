package salon.ekat.hairStylist.mapper;

import lombok.experimental.UtilityClass;
import salon.ekat.hairStylist.dto.AppointmentDTO;
import salon.ekat.hairStylist.entity.Appointment;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class AppointmentMapper {
    public AppointmentDTO mapToDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .masterId(appointment.getMasterId())
                .clientId(appointment.getClientId())
                .procedureId(appointment.getProcedureId())
                .startDateTime(appointment.getStartDateTime())
                .endDateTime(appointment.getEndDateTime())
                .build();
    }

    public Appointment mapToObject(AppointmentDTO appointmentDTO) {
        return Appointment.builder()
                .id(appointmentDTO.getId())
                .masterId(appointmentDTO.getMasterId())
                .clientId(appointmentDTO.getClientId())
                .procedureId(appointmentDTO.getProcedureId())
                .startDateTime(appointmentDTO.getStartDateTime())
                .endDateTime(appointmentDTO.getEndDateTime())
                .build();
    }

    public List<AppointmentDTO> mapToListDTO(List<Appointment> appointments) {
        if (appointments == null) {
            return new ArrayList<>();
        }

        return appointments.stream()
                .map(AppointmentMapper::mapToDTO)
                .toList();
    }

    public List<Appointment> mapToList(List<AppointmentDTO> appointmentsDTO) {
        if (appointmentsDTO == null) {
            return new ArrayList<>();
        }

        return appointmentsDTO.stream()
                .map(AppointmentMapper::mapToObject)
                .toList();
    }
}
