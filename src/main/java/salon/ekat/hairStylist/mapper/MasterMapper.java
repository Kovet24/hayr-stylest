package salon.ekat.hairStylist.mapper;

import lombok.experimental.UtilityClass;
import salon.ekat.hairStylist.dto.MasterDTO;
import salon.ekat.hairStylist.entity.Master;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MasterMapper {
    public MasterDTO mapToDTO(Master master) {
        return MasterDTO.builder()
                .id(master.getId())
                .name(master.getName())
                .number(master.getNumber())
                .build();
    }

    public Master mapToObject(MasterDTO masterDTO) {
        return Master.builder()
                .id(masterDTO.getId())
                .name(masterDTO.getName())
                .number(masterDTO.getNumber())
                .build();
    }

    public List<MasterDTO> mapToListDTO(List<Master> masters) {
        if (masters == null) {
            return new ArrayList<>();
        }

        return masters.stream()
                .map(MasterMapper::mapToDTO)
                .toList();
    }

    public List<Master> mapToList(List<MasterDTO> mastersDTO) {
        if (mastersDTO == null) {
            return new ArrayList<>();
        }

        return mastersDTO.stream()
                .map(MasterMapper::mapToObject)
                .toList();
    }
}
