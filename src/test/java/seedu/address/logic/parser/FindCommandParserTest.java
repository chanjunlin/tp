package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindNurseCommand;
import seedu.address.logic.commands.FindPatientCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nurseArgs_returnsFindNurseCommand() {
        // test when using "nurse"
        FindNurseCommand expectedFindNurseCommand =
                new FindNurseCommand(Index.fromZeroBased(0));
        assertParseSuccess(parser, "nurse 1", expectedFindNurseCommand);

        // test when using "Nurse"
        expectedFindNurseCommand =
                new FindNurseCommand(Index.fromZeroBased(0));
        assertParseSuccess(parser, "Nurse 1", expectedFindNurseCommand);
    }

    @Test
    public void parse_patientArgs_returnsFindPatientCommand() {
        // test when using "patient"
        FindPatientCommand expectedFindPatientCommand =
                new FindPatientCommand(Index.fromZeroBased(0));
        assertParseSuccess(parser, "patient 1", expectedFindPatientCommand);

        // test when using "Patient"
        expectedFindPatientCommand =
                new FindPatientCommand(Index.fromZeroBased(0));
        assertParseSuccess(parser, "Patient 1", expectedFindPatientCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("alice", "bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_nurseMissingIndex_returnsParseException() {
        String nurseMissingIndexErrorMessage = "Usage: find nurse PATIENT_INDEX";
        assertParseFailure(parser, "nurse ", nurseMissingIndexErrorMessage);
    }

    @Test
    public void parse_patientMissingIndex_returnsParseException() {
        String patientMissingIndexErrorMessage = "Usage: find patient NURSE_INDEX";
        assertParseFailure(parser, "patient ", patientMissingIndexErrorMessage);
    }

    @Test
    public void parse_nurseWrongArgument_returnsNumberFormatException() {
        String errorMessage = "Index is not a non-zero unsigned integer.";
        assertParseFailure(parser, "nurse two", errorMessage);
    }

    @Test
    public void parse_patientWrongArgument_returnsNumberFormatException() {
        String errorMessage = "Index is not a non-zero unsigned integer.";
        assertParseFailure(parser, "patient two", errorMessage);
    }




}
