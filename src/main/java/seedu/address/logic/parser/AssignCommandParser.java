package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse input arguments and creates a new AssignCommand object.
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignCommand
     * and returns an AssignCommand object for execution.
     * @param args
     * @return test
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, new Prefix(""));

        try {
            String[] splitArgs = args.trim().split("\\s+");

            if (splitArgs.length != 2) {
                throw new ParseException("Usage: assign PATIENT_INDEX NURSE_INDEX");
            }

            Index patientIndex = ParserUtil.parseIndex(splitArgs[0]);
            Index nurseIndex = ParserUtil.parseIndex(splitArgs[1]);

            return new AssignCommand(patientIndex, nurseIndex);
        } catch (NumberFormatException e) {
            throw new ParseException("Indexes must be valid positive integers.");
        }
    }
}
