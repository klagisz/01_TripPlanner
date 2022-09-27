package pl.soth.planer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.soth.planer.model.TypeOfPlace;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPlaceDto {

    @NotNull
    private Long placeId;

    private String name;

    private String adress;

    private String typeOfPlace;

    private String note;

}
