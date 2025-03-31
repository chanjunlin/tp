package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignCommand object.
 */
public class AssignCommandParser implements Parser<Command> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignCommand and AssignDeleteCommand
     * and returns an AssignCommand object or an AssignDeleteCommand object for execution.
     *
     * @param args
     * @return AssignCommand or AssignDeleteCommand object
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {
        String argsTrimmed = args.trim();

        if (argsTrimmed.startsWith("delete")) {
            return new AssignDeleteCommandParser().parse(argsTrimmed.substring("delete".length()).trim());
        }

        return parseAssignCommand(argsTrimmed);
    }

    /**
     * Parses the given {@code String} of arguments specifically for the AssignCommand
     * and returns an AssignCommand object for execution.
     *
     * @param args
     * @return AssignCommand object
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCommand parseAssignCommand(String args) throws ParseException {

        try {
            String[] splitArgs = args.trim().split("\\s+");

            if (splitArgs.length != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
            }

            Index patientIndex = ParserUtil.parseIndex(splitArgs[0]);
            Index nurseIndex = ParserUtil.parseIndex(splitArgs[1]);

            return new AssignCommand(patientIndex, nurseIndex);
        } catch (NumberFormatException e) {
            throw new ParseException("Indexes must be valid positive integers.");
        }
    }
}
