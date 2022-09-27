package pl.soth.planer.model.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {

    private Long id;
    private String name;
    private String surname;
    private String email;

}
