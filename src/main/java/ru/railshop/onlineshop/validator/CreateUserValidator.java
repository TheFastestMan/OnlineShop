package ru.railshop.onlineshop.validator;

import lombok.NoArgsConstructor;
import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.entity.Gender;
import ru.railshop.onlineshop.entity.Role;  // Ensure this is the correct import for your Role enum

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<UserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidateResult isValid(UserDto user) {
        var validateResult = new ValidateResult();

        if (user.username() == null || user.username().trim().isEmpty()) {
            validateResult.add(Error.of("invalidUsername", "Username is required"));
        }

        if (user.email() == null || user.email().trim().isEmpty()) {
            validateResult.add(Error.of("invalidEmail", "Email is required"));
        } else if (!user.email().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            validateResult.add(Error.of("invalidEmailFormat", "Email format is invalid"));
        }

        if (user.password() == null || user.password().trim().isEmpty()) {
            validateResult.add(Error.of("invalidPassword", "Password is required"));
        } else if (user.password().length() < 4) {
            validateResult.add(Error.of("shortPassword", "Password should be at least 8 characters"));
        }

        if (user.role() == null || !isValidRole(user.role())) {
            validateResult.add(Error.of("invalidRole", "Role is missing or invalid"));
        }

        if (user.gender() == null || !isValidGender(user.gender())) {
            validateResult.add(Error.of("invalidGender", "Gender is missing or invalid"));
        }

        return validateResult;
    }

    private boolean isValidRole(String role) {
        for (Role r : Role.values()) {
            if (r.name().equals(role)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidGender(String gender) {
        for (Gender g : Gender.values()) {
            if (g.name().equals(gender)) {
                return true;
            }
        }
        return false;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
