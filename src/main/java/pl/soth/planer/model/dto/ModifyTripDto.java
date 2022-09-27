package pl.soth.planer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyTripDto {
    @NotNull
    private Long tripId;

    @NotEmpty
    private String name;
    @NotEmpty
    private String beginDate;
    @NotEmpty
    private String endDate;

    private String comments;
}
