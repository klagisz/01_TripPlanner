package pl.soth.planer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPasswordDto {

    @NotNull
    private Long userId;

    @NotEmpty
    @Size(min = 5)
    private String password;

    @NotEmpty
    @Size(min = 5)
    private String NewPassword;
    private String NewPasswordConfirm;
}
