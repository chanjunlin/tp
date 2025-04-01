package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validArgs_returnsViewCommand() throws Exception {
        ViewCommand expectedCommand = new ViewCommand(INDEX_FIRST_PERSON);
        ViewCommand actualCommand = parser.parse("1");
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidArgsNonNumeric_throwsParseException() {
        ParseException exception = assertThrows(seedu.address.logic.parser.exceptions.ParseException.class, ()
                -> parser.parse("abc"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), exception.getMessage());
    }

    @Test
    public void parse_invalidArgsNegative_throwsParseException() {
        assertThrows(seedu.address.logic.parser.exceptions.ParseException.class, () -> parser.parse("-1"));
    }

    @Test
    public void parse_invalidArgsZero_throwsParseException() {
        assertThrows(seedu.address.logic.parser.exceptions.ParseException.class, () -> parser.parse("0"));
    }

    @Test
    public void parse_tooManyArgs_throwsParseException() {
        ParseException exception = assertThrows(seedu.address.logic.parser.exceptions.ParseException.class, ()
                -> parser.parse("1 2"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), exception.getMessage());
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(""));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), exception.getMessage());
    }
}
