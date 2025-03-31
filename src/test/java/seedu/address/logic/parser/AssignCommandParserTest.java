package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

public class AssignCommandParserTest {

    private AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_validArgs_returnsAssignCommand() throws Exception {
        AssignCommand expectedCommand = new AssignCommand(Index.fromOneBased(2),
                Index.fromOneBased(1));
        Command parsedCommand = parser.parse("2 1");

        assertEquals(expectedCommand, parsedCommand);
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("2"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("2 a"));
    }

    @Test
    public void parse_tooManyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("1 2 3"));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }
}
