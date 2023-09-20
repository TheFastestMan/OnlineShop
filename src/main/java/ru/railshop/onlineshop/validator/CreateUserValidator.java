package ru.railshop.onlineshop.validator;

import lombok.NoArgsConstructor;
import ru.railshop.onlineshop.dto.UserDto;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<UserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidateResult isValid(UserDto object) {
        var validateResult = new ValidateResult();

        if (object.description().isEmpty()) {
            validateResult.add(Error.of("invalid description", "Description is invalid"));
        }
        return validateResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }

}
