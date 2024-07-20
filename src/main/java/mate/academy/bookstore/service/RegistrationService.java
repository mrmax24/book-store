package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.user.UserRegistrationRequestDto;
import mate.academy.bookstore.dto.user.UserResponseDto;

public interface RegistrationService {
    UserResponseDto registerUser(UserRegistrationRequestDto requestDto);
}
