package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.AssignDeleteCommand;
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
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("2"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("2 a"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_tooManyArgs_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("1 2 3"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(""));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_assignDeleteMissingArgs_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("delete john lee"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignDeleteCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_assignDeleteInvalidPatientIndex_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("delete john lee a"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignDeleteCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_assignDeleteNoArgs_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("delete"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignDeleteCommand.MESSAGE_USAGE),
                exception.getMessage());
    }
}
