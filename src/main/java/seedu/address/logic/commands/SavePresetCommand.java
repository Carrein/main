/* @@author thamsimun */
package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;
import seedu.address.model.TransformationSet;
import seedu.address.model.image.Image;

/**
 * This command allows users to save a preset of commands.
 */
public class SavePresetCommand extends Command {
    public static final String COMMAND_WORD = "savepreset";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Saves a preset list of image editing commands used on your currently editing image.\n"
        + "Name of the preset list should not contain any spaces.\n"
        + "Example: " + COMMAND_WORD + "preset1\n";

    private String presetName;

    /**
     * Creates a SavePresetCommand object.
     * @param presetName name given to the preset set by user
     */
    public SavePresetCommand(String presetName) {
        this.presetName = presetName;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history)
        throws CommandException {
        List<Command> presetList = new ArrayList<>();
        Image initialImage = currentEdit.getTempImage();
        if (initialImage == null) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }
        List<Command> commandList = currentEdit.getTempSubHistory();
        for (Command command: commandList) {
            if (!(command instanceof SetPresetCommand)) {
                presetList.add(command);
            }
        }
        System.out.print(presetList);
        if (presetList.size() < 1) {
            throw new CommandException(Messages.MESSAGE_SAVEPRESET_FAIL_EMPTY);
        }
        TransformationSet transformationSet = TransformationSet.getInstance();
        boolean duplicate = transformationSet.isPresent(presetName);
        if (duplicate) {
            throw new CommandException(Messages.MESSAGE_SAVEPRESET_FAIL_DUPLICATE);
        }
        transformationSet.addTransformation(presetName, presetList);
        StringBuilder toPrint = new StringBuilder();

        for (Command command: presetList) {
            toPrint.append("[" + command.toString() + "]");
        }

        return new CommandResult(Messages.MESSAGE_SAVEPRESET_SUCCESS + ": \n" + toPrint.toString());
    }
}
/* @@author */
