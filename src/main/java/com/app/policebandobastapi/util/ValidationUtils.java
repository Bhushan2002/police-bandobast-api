package com.app.policebandobastapi.util;

import java.util.Arrays;

import org.springframework.data.repository.CrudRepository;

import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;

// Field checks shared by the service validate() methods; lengths and formats follow BACKEND-API.md
public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static void requireText(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new BadRequestException(label + " is required");
        }
        checkLength(value, label, maxLength);
    }

    public static void optionalText(String value, String label, int maxLength) {
        if (value != null) {
            checkLength(value, label, maxLength);
        }
    }

    private static void checkLength(String value, String label, int maxLength) {
        if (value.trim().length() > maxLength) {
            throw new BadRequestException(label + " cannot exceed " + maxLength + " characters");
        }
    }

    public static void requireMobile(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new BadRequestException(label + " is required");
        }
        if (!value.matches("\\d{10}")) {
            throw new BadRequestException(label + " must be 10 digits");
        }
    }

    public static void optionalEmail(String value) {
        if (value == null || value.isBlank()) {
            return;
        }
        checkLength(value, "Email", 150);
        if (!value.trim().matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            throw new BadRequestException("Email is not valid");
        }
    }

    public static void optionalPincode(String value) {
        if (value != null && !value.isBlank() && !value.matches("\\d{6}")) {
            throw new BadRequestException("Pincode must be 6 digits");
        }
    }

    // For enum values the models still store as String (e.g. inkColor, gpsTagMode)
    public static <E extends Enum<E>> void requireEnumValue(String value, Class<E> type, String label) {
        if (value == null || value.isBlank()) {
            throw new BadRequestException(label + " is required");
        }
        boolean known = Arrays.stream(type.getEnumConstants()).anyMatch(constant -> constant.name().equals(value));
        if (!known) {
            throw new BadRequestException(label + " must be one of " + Arrays.toString(type.getEnumConstants()));
        }
    }

    public static void requireExists(CrudRepository<?, Long> repo, Long id, String label) {
        if (id == null) {
            throw new BadRequestException(label + " is required");
        }
        checkExists(repo, id, label);
    }

    public static void optionalExists(CrudRepository<?, Long> repo, Long id, String label) {
        if (id != null) {
            checkExists(repo, id, label);
        }
    }

    private static void checkExists(CrudRepository<?, Long> repo, Long id, String label) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException(label, id);
        }
    }
}
