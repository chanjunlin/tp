package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindNurseCommand;
import seedu.address.logic.commands.FindPatientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String PREFIX_FIND_NURSE = "nurse of patient";
    public static final String PREFIX_FIND_PATIENT = "patient of nurse";

    public static final String PATIENT_INDEX = "PATIENT_INDEX";
    public static final String NURSE_INDEX = "NURSE_INDEX";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.startsWith(PREFIX_FIND_NURSE)) {
            return findNurseScenario(trimmedArgs);
        } else if (trimmedArgs.startsWith(PREFIX_FIND_PATIENT)) {
            return findPatientScenario(trimmedArgs);
        }

        return findScenario(trimmedArgs);
    }

    /**
     * Splits the input arguments into separate components and ensures that the correct number of arguments
     * are provided. If there are not enough arguments, a ParseException is thrown with the appropriate message.
     *
     * @param args The arguments provided by the user, as a string.
     * @param commandType The command type, which helps in determining the expected number of arguments.
     * @return An array of split arguments.
     * @throws ParseException If the number of arguments is incorrect for the given command type.
     */
    public String[] splitArguments(String args, String commandType) throws ParseException {
        String[] splitArgs = args.split("\\s+");
        if (splitArgs.length != 4) {
            String indexType = commandType.equals(PREFIX_FIND_NURSE) ? PATIENT_INDEX : NURSE_INDEX;
            throw new ParseException(String.format("Usage: find %s %s", commandType, indexType));
        }
        return splitArgs;
    }

    /**
     * Parses the input arguments when the command involves finding a nurse for a specific patient.
     * It splits the arguments and converts the index into an {@link Index} object before creating a
     * {@link FindNurseCommand}.
     *
     * @param trimmedArgs The trimmed user input arguments for finding a nurse.
     * @return A FindNurseCommand object with the parsed nurse index.
     * @throws ParseException If the input format is invalid or the index cannot be parsed.
     */
    public FindNurseCommand findNurseScenario(String trimmedArgs) throws ParseException {
        String[] splitArgs = splitArguments(trimmedArgs, PREFIX_FIND_NURSE);

        Index nurseIndex = ParserUtil.parseIndex(splitArgs[3]);
        return new FindNurseCommand(nurseIndex);

    }

    /**
     * Parses the input arguments when the command involves finding a patient for a specific nurse.
     * It splits the arguments and converts the index into an {@link Index} object before creating a
     * {@link FindPatientCommand}.
     *
     * @param trimmedArgs The trimmed user input arguments for finding a patient.
     * @return A FindPatientCommand object with the parsed patient index.
     * @throws ParseException If the input format is invalid or the index cannot be parsed.
     */
    public FindPatientCommand findPatientScenario(String trimmedArgs) throws ParseException {
        String[] splitArgs = splitArguments(trimmedArgs, PREFIX_FIND_PATIENT);

        Index patientIndex = ParserUtil.parseIndex(splitArgs[3]);
        return new FindPatientCommand(patientIndex);

    }

    /**
     * Parses the input arguments when the command involves searching for a patient or nurse by name.
     * It splits the arguments by whitespace and creates a {@link FindCommand} with the keywords as the predicate.
     *
     * @param trimmedArgs The trimmed user input arguments containing keywords to search by name.
     * @return A FindCommand object containing the NameContainsKeywordsPredicate with the parsed keywords.
     * @throws ParseException If the user input is invalid or cannot be parsed correctly.
     */
    public FindCommand findScenario(String trimmedArgs) throws ParseException {
        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
