package pl.soth.planer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyAppUserDto {

    @NotNull
    private Long userId;

    private String name;
    private String surname;
    private String email;

}
