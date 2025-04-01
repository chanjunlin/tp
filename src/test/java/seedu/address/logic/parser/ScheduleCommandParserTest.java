package seedu.address.logic.parser;

import static seedu.address.logic.commands.ScheduleCommand.MESSAGE_USAGE;
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

    private static final String INVALID_FORMAT_DATE = INVALID_DATE_FORMAT + "\n" + MESSAGE_USAGE;
    private static final String INVALID_FORMAT_TIME = INVALID_TIME_FORMAT + "\n" + MESSAGE_USAGE;
    private static final String INVALID_DATE = INVALID_DAY_FOR_MONTH + "\n" + MESSAGE_USAGE;
    private static final String COMMAND = "\n" + MESSAGE_USAGE;
    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateFormat() {
        assertParseFailure(parser, "add for patient 1 12-12-2025 1200", INVALID_FORMAT_DATE);
        assertParseFailure(parser, "add for patient 1 12-12/2025 1200", INVALID_FORMAT_DATE);
        assertParseFailure(parser, "delete for patient 1 12/12/2025/01 1200", INVALID_FORMAT_DATE);
    }

    @Test
    public void parse_invalidDate() {
        assertParseFailure(parser, "add for patient 1 31/02/2025 1200", INVALID_DATE);
        assertParseFailure(parser, "add for patient 1 31/04/2025 1200", INVALID_DATE);
    }

    @Test
    public void parse_invalidTimeFormat() {
        assertParseFailure(parser, "add for patient 1 12/12/2025 3025", INVALID_FORMAT_TIME);
        assertParseFailure(parser, "add for patient 1 12/12/2025 abcd", INVALID_FORMAT_TIME);
        assertParseFailure(parser, "add for patient 1 12/12/2025 12:00", INVALID_FORMAT_TIME);
    }

    @Test
    public void parse_invalidNumOfArguments() {
        assertParseFailure(parser, " 1 12/12/2025 1200", MISSING_FIRST_PARAMETER
                + COMMAND);
        assertParseFailure(parser, "1 1200", MISSING_FIRST_PARAMETER + COMMAND);
        assertParseFailure(parser, "add for patient ", MISSING_INDEX + COMMAND);
        assertParseFailure(parser, "add for patient 1 ", MISSING_DATE + COMMAND);
        assertParseFailure(parser, "add for patient 1 01/01/2001", MISSING_TIME
                + COMMAND);
    }

}
