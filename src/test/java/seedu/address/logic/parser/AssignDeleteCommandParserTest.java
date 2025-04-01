package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AssignDeleteCommandParserTest {
    private AssignDeleteCommandParser parser = new AssignDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsAssignDeleteCommand() throws Exception {
        AssignDeleteCommand expectedCommand = new AssignDeleteCommand("john lee",
                Index.fromOneBased(2));
        AssignDeleteCommand parsedCommand = parser.parse("john lee 2");

        assertEquals(expectedCommand, parsedCommand);
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("john lee"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignDeleteCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("john lee a"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignDeleteCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(""));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignDeleteCommand.MESSAGE_USAGE),
                exception.getMessage());
    }
}
