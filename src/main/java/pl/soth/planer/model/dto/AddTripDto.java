package pl.soth.planer.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.soth.planer.model.TripState;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AddTripDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String beginDate;
    @NotEmpty
    private String endDate;

    private String comments;

    @NotEmpty
    private TripState tripState;

    @NotNull
    private Long appuserId;

}
