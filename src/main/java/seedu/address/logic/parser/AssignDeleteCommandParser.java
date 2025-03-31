package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignDeleteCommand object.
 */
public class AssignDeleteCommandParser implements Parser<AssignDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignDeleteCommand
     * and returns an AssignDeleteCommand object for execution.
     *
     * @param args
     * @return AssignDeleteCommand object
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignDeleteCommand parse(String args) throws ParseException {
        try {
            String[] splitArgs = args.trim().split("\\s+");

            if (splitArgs.length < 2) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignDeleteCommand.MESSAGE_USAGE));
            }

            StringBuilder nurseNameBuilder = new StringBuilder();
            for (int i = 0; i < splitArgs.length - 1; i++) {
                if (i > 0) {
                    nurseNameBuilder.append(" ");
                }
                nurseNameBuilder.append(splitArgs[i]);
            }

            String nurseName = nurseNameBuilder.toString();
            Index patientIndex = ParserUtil.parseIndex(splitArgs[splitArgs.length - 1].trim());

            return new AssignDeleteCommand(nurseName, patientIndex);
        } catch (NumberFormatException e) {
            throw new ParseException("Indexes must be valid positive integers.");
        }
    }
}
