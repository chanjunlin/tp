package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ScheduleCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;

public class ScheduleCommandParserTest {

    private ScheduleCommandParser parser = new ScheduleCommandParser();
    private Index patientIndex;
    private LocalDate checkupDate;
    private LocalTime checkupTime;
    private Boolean addCheckup;
    private Boolean deleteCheckup;
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateFormat() {
        String invalidDateFormat = "Invalid date format. Use dd/MM/yyyy";
        assertParseFailure(parser, "add 1 12-12-2025 1200", invalidDateFormat);
        assertParseFailure(parser, "add 1 12-12/2025 1200", invalidDateFormat);
        assertParseFailure(parser, "add 1 12/12/2025/01 1200", invalidDateFormat);
    }

    @Test
    public void parse_invalidDate() {
        String invalidDate = "Invalid day for the given month/year";
        assertParseFailure(parser, "add 1 31/02/2025 1200", invalidDate);
        assertParseFailure(parser, "add 1 31/04/2025 1200", invalidDate);
    }

    @Test
    public void parse_invalidTimeFormat() {
        String invalidTime = "Invalid time values. Use HHmm";
        assertParseFailure(parser, "add 1 12/12/2025 3025", invalidTime);
        assertParseFailure(parser, "add 1 12/12/2025 abcd", invalidTime);
        assertParseFailure(parser, "add 1 12/12/2025 12:00", invalidTime);
    }

    @Test
    public void parse_invalidNumOfArguments() {
        String invalidArguments = MESSAGE_USAGE;
        assertParseFailure(parser, "12/12/2025 1200", invalidArguments);
        assertParseFailure(parser, "1 1200", invalidArguments);
        assertParseFailure(parser, "1200", invalidArguments);
    }

}
