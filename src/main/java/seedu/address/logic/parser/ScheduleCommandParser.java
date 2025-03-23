package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ScheduleCommand.MESSAGE_USAGE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        Index nurseIndex = getNurseIndex(trimmedArgs);

        LocalDate checkupDate = getCheckupDate(trimmedArgs);
        LocalTime checkupTime = getCheckupTime(trimmedArgs);
        return new ScheduleCommand(patientIndex, nurseIndex, checkupDate, checkupTime);
    }

    public Index getPatientIndex(String trimmedArgs) throws ParseException {
        String[] parsedArgument = parseArguments(trimmedArgs);
        return ParserUtil.parseIndex(parsedArgument[0]);
    }

    public Index getNurseIndex(String trimmedArgs) throws ParseException {
        String[] parsedArgument = parseArguments(trimmedArgs);
        return ParserUtil.parseIndex(parsedArgument[1]);
    }

    public LocalDate getCheckupDate(String trimmedArgs) throws ParseException {
        String[] parsedArgument = parseArguments(trimmedArgs);
        String dateString = parsedArgument[2];

        if (!dateString.matches(DATE_VALIDATION_REGEX)) {
            throw new ParseException("Invalid date format. Use dd/MM/yyyy");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid date values");
        }
    }

    public LocalTime getCheckupTime(String trimmedArgs) throws ParseException {
        String[] parsedArgument = parseArguments(trimmedArgs);
        String timeString = parsedArgument[3];
        if (!timeString.matches(TIME_VALIDATION_REGEX)) {
            throw new ParseException("Invalid date format. Use dd/MM/yyyy");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
            return LocalTime.parse(timeString, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid time values");
        }
    }

    /**
     * test
     * @param trimmedArgs test
     * @return test
     * @throws ParseException test
     */
    public String[] parseArguments(String trimmedArgs) throws ParseException {
        String[] parsedArguments = trimmedArgs.split("\\s+");
        if (parsedArguments.length < 4) {
            throw new ParseException(MESSAGE_USAGE);
        }
        return parsedArguments;
    }
}
