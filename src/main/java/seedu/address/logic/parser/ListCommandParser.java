package seedu.address.logic.parser;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Appointment;

/**
 * Parses input arguments and creates a new ListCommand object.
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();

        if (trimmedArgs.isEmpty()) {
            return new ListCommand(null); // Show all persons if no filter is given
        }

        String formattedAppointment = capitalizeFirstLetter(trimmedArgs);

        if (!Appointment.isValidAppointment(formattedAppointment)) {
            throw new ParseException("Invalid appointment type! Only 'Nurse' or 'Patient' are allowed.");
        }

        return new ListCommand(new Appointment(formattedAppointment));
    }

    /**
     * Capitalizes the first letter of the given string.
     */
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
