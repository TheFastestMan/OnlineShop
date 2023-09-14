package ru.railshop.onlineshop.validator;

import lombok.NoArgsConstructor;
import ru.railshop.onlineshop.dto.CreateUserDto;
import ru.railshop.onlineshop.entity.Role;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidateResult isValid(CreateUserDto object) {
        var validateResult = new ValidateResult();

        if (object.getUsername().isEmpty()) {
            validateResult.add(Error.of("invalid name", "Name is invalid"));
        }
        if (object.getPassword().isEmpty()) {
            validateResult.add(Error.of("invalid password", "Password is invalid"));
        }
        if (object.getEmail().isEmpty()) {
            validateResult.add(Error.of("invalid email", "Email is invalid"));
        }
        if (object.getRole().isEmpty()) {
            validateResult.add(Error.of("invalid role", "Role is invalid"));
        }
        if (object.getGender().isEmpty()) {
            validateResult.add(Error.of("invalid gender", "Gender is invalid"));
        }
        return validateResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }

}
