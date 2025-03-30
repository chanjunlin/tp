package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents a person's date of birth.
 */
public class DateOfBirth {
    public static final String MESSAGE_CONSTRAINTS = "Date Of Birth can only take the format:"
            + "DD/MM/YYYY";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Logger logger = LogsCenter.getLogger(DateOfBirth.class);

    public final LocalDate dob;

    /**
     * Constructs a DateOfBirth object from a valid date string.
     *
     * @param dateOfBirth The date of birth in the format "dd-MM-yyyy".
     * @throws NullPointerException if dateOfBirth is null.
     * @throws IllegalArgumentException if dateOfBirth does not conform to the required format.
     */
    public DateOfBirth(String dateOfBirth) {
        requireNonNull(dateOfBirth);
        checkArgument(isValidDate(dateOfBirth), MESSAGE_CONSTRAINTS);
        this.dob = parseDate(dateOfBirth);
    }


    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidDate(String dateString) {
        try {
            logger.info("Date of birth string = " + dateString);
            LocalDate doob = LocalDate.parse(dateString, FORMATTER);
            logger.info("dob = " + doob.format(FORMATTER));
            return true;
        } catch (DateTimeParseException e) {
            logger.warning(e.getMessage());
            return false;
        }
    }

    private LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, FORMATTER);
    }

    @Override
    public String toString() {
        return this.dob.format(FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateOfBirth otherDateOfBirth)) {
            return false;
        }

        return this.dob.equals(otherDateOfBirth.dob);
    }

    @Override
    public int hashCode() {
        return this.dob.hashCode();
    }
}
