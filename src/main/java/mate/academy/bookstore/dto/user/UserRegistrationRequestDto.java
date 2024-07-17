package mate.academy.bookstore.dto.user;

import jakarta.validation.constraints.Email;
import mate.academy.bookstore.validation.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(password = "password", repeatPassword = "repeatPassword")
public class UserRegistrationRequestDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Length(min = 6, max = 255)
    private String password;
    @NotBlank
    @Length(min = 6, max = 255)
    private String repeatPassword;
    @NotBlank
    @Length(min = 2, max = 30)
    private String firstName;
    @NotBlank
    @Length(min = 2, max = 30)
    private String lastName;
    @NotBlank
    private String shippingAddress;
}
