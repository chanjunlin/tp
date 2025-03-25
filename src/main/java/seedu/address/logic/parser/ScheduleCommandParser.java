package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ScheduleCommand.MESSAGE_USAGE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * test
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {
    private static final String DATE_VALIDATION_REGEX = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
    private static final String TIME_VALIDATION_REGEX = "^([01][0-9]|2[0-3])[0-5][0-9]$";

    /**
     * test
     * @param args test
     * @return test
     * @throws ParseException test
     */
    public ScheduleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            ScheduleCommand.MESSAGE_USAGE));
        }
        Index patientIndex = getPatientIndex(trimmedArgs);

        LocalDate checkupDate = getCheckupDate(trimmedArgs);
        LocalTime checkupTime = getCheckupTime(trimmedArgs);
        return new ScheduleCommand(patientIndex, checkupDate, checkupTime);
    }

    public Index getPatientIndex(String trimmedArgs) throws ParseException {
        String[] parsedArgument = parseArguments(trimmedArgs);
        return ParserUtil.parseIndex(parsedArgument[0]);
    }


    public LocalDate getCheckupDate(String trimmedArgs) throws ParseException {
        String[] parsedArgument = parseArguments(trimmedArgs);
        String dateString = parsedArgument[1];

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

    public LocalTime getCheckupTime(String trimmedArgs) throws ParseException {
        String[] parsedArgument = parseArguments(trimmedArgs);
        String timeString = parsedArgument[2];
        if (!timeString.matches(TIME_VALIDATION_REGEX)) {
            throw new ParseException("Invalid time values. Use HHmm");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        return LocalTime.parse(timeString, formatter);
    }

    /**
     * test
     * @param trimmedArgs test
     * @return test
     * @throws ParseException test
     */
    public String[] parseArguments(String trimmedArgs) throws ParseException {
        String[] parsedArguments = trimmedArgs.split("\\s+");
        if (parsedArguments.length != 3) {
            throw new ParseException(MESSAGE_USAGE);
        }
        return parsedArguments;
    }
}
