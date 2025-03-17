package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Appointment;

/**
 * Tests for ListCommandParser.
 */
public class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() throws Exception {
        assertTrue(parser.parse("").equals(new ListCommand(null)));
        assertTrue(parser.parse("nurse").equals(new ListCommand(new Appointment("Nurse"))));
        assertTrue(parser.parse("patient").equals(new ListCommand(new Appointment("Patient"))));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("xyz"));
        assertThrows(ParseException.class, () -> parser.parse("123"));
        assertThrows(ParseException.class, () -> parser.parse("nurse patient"));
    }
}
