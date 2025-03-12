package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Check if the command is for finding by nurse index
        if (trimmedArgs.startsWith("nurse")) {
            String[] splitArgs = trimmedArgs.split("\\s+");
            if (splitArgs.length != 2) {
                throw new ParseException("Invalid format! Usage: find nurse INDEX");
            }

            try {
                int nurseIndex = Integer.parseInt(splitArgs[1]);
                return new FindNurseCommand(nurseIndex);
            } catch (NumberFormatException e) {
                throw new ParseException("INDEX must be a valid integer!");
            }
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
