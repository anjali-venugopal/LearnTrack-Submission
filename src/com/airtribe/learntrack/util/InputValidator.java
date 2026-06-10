package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

public final class InputValidator {
    private InputValidator() {
    }

    public static void requireNonEmpty(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
    }

    public static int parsePositiveInt(String input, String fieldName) throws InvalidInputException {
        try {
            int value = Integer.parseInt(input.trim());
            if (value <= 0) {
                throw new InvalidInputException(fieldName + " must be a positive number.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid " + fieldName + ". Please enter a valid number.");
        }
    }

    public static int parseNonNegativeInt(String input, String fieldName) throws InvalidInputException {
        try {
            int value = Integer.parseInt(input.trim());
            if (value < 0) {
                throw new InvalidInputException(fieldName + " cannot be negative.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid " + fieldName + ". Please enter a valid number.");
        }
    }
}
