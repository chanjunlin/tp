package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(parser.parse("checkup").equals(new ListCommand(true)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("xyz"));
        assertThrows(ParseException.class, () -> parser.parse("123"));
        assertThrows(ParseException.class, () -> parser.parse("nurse patient"));
    }

    @Test
    public void parse_caseInsensitiveInput_returnsCorrectCommand() throws Exception {
        assertTrue(parser.parse("NuRSe").equals(new ListCommand(new Appointment("Nurse"))));
        assertTrue(parser.parse("PaTient").equals(new ListCommand(new Appointment("Patient"))));
        assertTrue(parser.parse("CheCkUp").equals(new ListCommand(true)));
    }

    @Test
    public void parse_inputWithExtraWhitespace_returnsCorrectCommand() throws Exception {
        assertTrue(parser.parse("  nurse ").equals(new ListCommand(new Appointment("Nurse"))));
        assertTrue(parser.parse("  ").equals(new ListCommand(null)));
    }
}
