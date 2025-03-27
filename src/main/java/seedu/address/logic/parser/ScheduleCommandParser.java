package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ScheduleCommand.MESSAGE_USAGE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;

/**
 * Parses input arguments and creates a ScheduleCommand.
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {
    private static final String DATE_VALIDATION_REGEX = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
    private static final String TIME_VALIDATION_REGEX = "^([01][0-9]|2[0-3])[0-5][0-9]$";
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    /**
     * Parses the given {@code String} representation of arguments
     * and returns a ScheduleCommand object for execution.
     *
     * @param args The user input args to be parsed.
     * @return A ScheduleCommand based on the parsed arguments.
     * @throws ParseException If the input does not conform to expected formats.
     */
    public ScheduleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }

        boolean action = getCheckupAction(trimmedArgs);
        Index patientIndex = getPatientIndex(trimmedArgs);
        LocalDate checkupDate = getCheckupDate(trimmedArgs);
        LocalTime checkupTime = getCheckupTime(trimmedArgs);
        logger.info("creating schedule command");
        return new ScheduleCommand(action, patientIndex, checkupDate, checkupTime);
    }

    public boolean getCheckupAction(String trimmedArgs) throws ParseException {
        String[] parsedArgument = parseArguments(trimmedArgs);
        if (parsedArgument[0].equals(ScheduleCommand.DELETE_SCHEDULE_COMMAND)) {
            return false;
        } else if (parsedArgument[0].equals(ScheduleCommand.ADD_SCHEDULE_COMMAND)) {
            return true;
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Retrieves and parses the patient index from the input arguments.
     *
     * @param trimmedArgs The input arguments from which to retrieve the index.
     * @return The Patient Index extracted from the arguments.
     * @throws ParseException If the index is not valid.
     */
    public Index getPatientIndex(String trimmedArgs) throws ParseException {
        String[] parsedArgument = parseArguments(trimmedArgs);
        return ParserUtil.parseIndex(parsedArgument[1]);
    }

    /**
     * Retrieves and parses the checkup date from the input arguments.
     *
     * @param trimmedArgs The input arguments from which to retrieve the date.
     * @return The LocalDate object representing the checkup date.
     * @throws ParseException If the date is not in the expected format or is invalid.
     */
    public LocalDate getCheckupDate(String trimmedArgs) throws ParseException {
        String[] parsedArgument = parseArguments(trimmedArgs);
        String dateString = parsedArgument[2];

        if (!dateString.matches(DATE_VALIDATION_REGEX)) {
            throw new ParseException("Invalid date format. Use dd/MM/yyyy");
        }

        try {
            return getLocalDate(dateString);
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
    }

    private static LocalDate getLocalDate(String dateString) throws ParseException {
        String[] dateParts = dateString.split("/");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        if (day < 1 || day > LocalDate.of(year, month, 1).lengthOfMonth()) {
            throw new ParseException("Invalid day for the given month/year");
        }

        return LocalDate.of(year, month, day);
    }

    /**
     * Retrieves and parses the checkup time from the input arguments.
     *
     * @param trimmedArgs The input arguments from which to retrieve the time.
     * @return The LocalTime object representing the checkup time.
     * @throws ParseException If the time is not in the expected format or is invalid.
     */
    public LocalTime getCheckupTime(String trimmedArgs) throws ParseException {
        String[] parsedArgument = parseArguments(trimmedArgs);
        String timeString = parsedArgument[3];
        if (!timeString.matches(TIME_VALIDATION_REGEX)) {
            throw new ParseException("Invalid time values. Use HHmm");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        return LocalTime.parse(timeString, formatter);
    }

    /**
     * Parses the input arguments to ensure the correct number of parameters are provided.
     *
     * @param trimmedArgs The input arguments to be split.
     * @return An array of arguments extracted from the input string.
     * @throws ParseException If the number of arguments is incorrect.
     */
    public String[] parseArguments(String trimmedArgs) throws ParseException {
        String[] parsedArguments = trimmedArgs.split("\\s+");
        if (parsedArguments.length != 4) {
            throw new ParseException(MESSAGE_USAGE);
        }
        return parsedArguments;
    }
}
