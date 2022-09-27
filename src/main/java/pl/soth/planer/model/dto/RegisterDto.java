package pl.soth.planer.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class RegisterDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;

    @NotEmpty
    @Size(min = 5)
    private String password;
    private String passwordConfirm;

    @NotEmpty
    @Email
    private String email;

}
