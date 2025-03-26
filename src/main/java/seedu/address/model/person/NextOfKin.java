package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Next of Kin in the address book.
 */
public class NextOfKin {

    public static final String MESSAGE_CONSTRAINTS =
            "Next of Kin must be in the format: 'Name Phone', e.g., 'Jane 91234567'.";

    public final String value;

    /**
     * Constructs a {@code NextOfKin} from a single input string.
     * Accepts null or blank as an empty NextOfKin.
     *
     * @param input A valid string in the format "Name Phone", or null/empty.
     */
    public NextOfKin(String input) {
        if (input == null || input.trim().isEmpty()) {
            this.value = "Next of Kin not provided";
        } else {
            checkArgument(isValidNextOfKin(input), MESSAGE_CONSTRAINTS);
            this.value = input.trim();
        }
    }

    /**
     * Returns true if the given input is a valid next of kin string.
     * Expected format: "Name Phone"
     */
    public static boolean isValidNextOfKin(String input) {
        requireNonNull(input);
        if (input.equals("Next of Kin not provided")) {
            return true;
        }
        String[] parts = input.trim().split(" ", 2);
        if (parts.length < 2) {
            return false;
        }
        String name = parts[0];
        String phone = parts[1];
        return Name.isValidName(name) && Phone.isValidPhone(phone);
    }

    @Override
    public String toString() {
        return value.isEmpty() ? "" : value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NextOfKin)) {
            return false;
        }

        NextOfKin otherNok = (NextOfKin) other;
        return value.equals(otherNok.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
