package ru.railshop.onlineshop.validator;

import lombok.NoArgsConstructor;
import ru.railshop.onlineshop.dao.UserDao;
import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.entity.Gender;
import ru.railshop.onlineshop.entity.Role;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<UserDto> {

    private UserDao userDao = UserDao.getInstance();
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidateResult isValid(UserDto user) {
        var validateResult = new ValidateResult();

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            validateResult.add(Error.of("invalidUsername", "Username is required"));
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            validateResult.add(Error.of("invalidEmail", "Email is required"));
        } else if (!user.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            validateResult.add(Error.of("invalidEmailFormat", "Email format is invalid"));
        } else if (emailExistsInDatabase(user.getEmail())) {
            validateResult.add(Error.of("emailExists", "Email already exists"));
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            validateResult.add(Error.of("invalidPassword", "Password is required"));
        } else if (user.getPassword().length() < 4) {
            validateResult.add(Error.of("shortPassword", "Password should be at least 8 characters"));
        }

        if (user.getRole() == null || !isValidRole(String.valueOf(user.getRole()))) {
            validateResult.add(Error.of("invalidRole", "Role is missing or invalid"));
        }

        if (user.getGender() == null || !isValidGender(String.valueOf(user.getGender()))) {
            validateResult.add(Error.of("invalidGender", "Gender is missing or invalid"));
        }

        return validateResult;
    }

    private boolean isValidRole(String role) {
        for (Role r : Role.values()) {
            if (r.name().equalsIgnoreCase(role)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidGender(String gender) {
        for (Gender g : Gender.values()) {
            if (g.name().equalsIgnoreCase(gender)) {
                return true;
            }
        }
        return false;
    }

    private boolean emailExistsInDatabase(String email) {
        try {
            return userDao.findByEmail(email).isPresent();
        } catch (Exception e) {
            throw new RuntimeException("Error checking email existence", e);
        }
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
