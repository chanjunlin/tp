package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input argument and creates a new ViewCommand object.
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     *
     * @param args
     * @return ViewCommand object
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        try {
            String[] splitArgs = args.trim().split("\\s");
            if (splitArgs.length != 1) {
                throw new ParseException("Usage: view INDEX");
            }
            Index index = ParserUtil.parseIndex(splitArgs[0]);
            return new ViewCommand(index);
        } catch (NumberFormatException e) {
            throw new ParseException("Index must be a valid positive integer.");
        }
    }
}
