package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final String MESSAGE_CONSTRAINTS = """
            Invalid phone number format! Please follow the criterias below:
            - Phone numbers should only contain valid numbers (i.e. no all zeros)
            - Should be at least 3 digits long
            - Phone numbers can be separated by a space with at least 3 digits in each group
            - Phone numbers should not contain any other characters or symbols
            - Total number of digits cannot exceed 17""";
    // the regex format (line 18) is partly done with the assistance of chatGPT
    public static final String VALIDATION_REGEX = "^(?!0+(?:\\s+0+)*$)(?:\\d{3,}(?:\\s+\\d{3,})*)$";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
