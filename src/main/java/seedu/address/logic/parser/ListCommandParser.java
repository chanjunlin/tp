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

        boolean isEmpty = trimmedArgs.isEmpty();
        boolean isCheckup = trimmedArgs.equals("checkup");
        boolean isValidAppointment = Appointment.isValidAppointment(trimmedArgs);

        if (isEmpty) {
            return new ListCommand(null);
        }

        if (isCheckup) {
            return new ListCommand(true);
        }

        if (!isValidAppointment) {
            throw new ParseException("Invalid input type! Only 'nurse', 'patient' or 'checkup' are allowed.");
        }

        return new ListCommand(new Appointment(trimmedArgs));
    }
}
