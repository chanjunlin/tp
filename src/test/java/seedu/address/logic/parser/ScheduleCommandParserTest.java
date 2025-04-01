package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.ScheduleCommandParser.INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.ScheduleCommandParser.INVALID_DAY_FOR_MONTH;
import static seedu.address.logic.parser.ScheduleCommandParser.INVALID_TIME_FORMAT;
import static seedu.address.logic.parser.ScheduleCommandParser.MISSING_DATE;
import static seedu.address.logic.parser.ScheduleCommandParser.MISSING_FIRST_PARAMETER;
import static seedu.address.logic.parser.ScheduleCommandParser.MISSING_INDEX;
import static seedu.address.logic.parser.ScheduleCommandParser.MISSING_TIME;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleCommand;

public class ScheduleCommandParserTest {

    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateFormat() {
        String invalidDateFormat = INVALID_DATE_FORMAT;
        assertParseFailure(parser, "add for patient 1 12-12-2025 1200", invalidDateFormat);
        assertParseFailure(parser, "add for patient 1 12-12/2025 1200", invalidDateFormat);
        assertParseFailure(parser, "delete for patient 1 12/12/2025/01 1200", invalidDateFormat);
    }

    @Test
    public void parse_invalidDate() {
        String invalidDate = INVALID_DAY_FOR_MONTH;
        assertParseFailure(parser, "add for patient 1 31/02/2025 1200", invalidDate);
        assertParseFailure(parser, "add for patient 1 31/04/2025 1200", invalidDate);
    }

    @Test
    public void parse_invalidTimeFormat() {
        String invalidTime = INVALID_TIME_FORMAT;
        assertParseFailure(parser, "add for patient 1 12/12/2025 3025", invalidTime);
        assertParseFailure(parser, "add for patient 1 12/12/2025 abcd", invalidTime);
        assertParseFailure(parser, "add for patient 1 12/12/2025 12:00", invalidTime);
    }

    @Test
    public void parse_invalidNumOfArguments() {
        assertParseFailure(parser, " 1 12/12/2025 1200", MISSING_FIRST_PARAMETER);
        assertParseFailure(parser, "1 1200", MISSING_FIRST_PARAMETER);
        assertParseFailure(parser, "add for patient ", MISSING_INDEX);
        assertParseFailure(parser, "add for patient 1 ", MISSING_DATE);
        assertParseFailure(parser, "add for patient 1 01/01/2001", MISSING_TIME);
    }

}
