package pl.soth.planer.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AddPlaceToTripDto {

    @NotNull
    private Long tripId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String adress;

    private String note;

    private String type;

    private boolean isVisited;

}
