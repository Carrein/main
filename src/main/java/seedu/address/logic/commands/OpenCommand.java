/* @@author itszp */
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_FILE_NOT_FOUND;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;
import seedu.address.model.image.Image;

/**
 * Opens a previously imported image for editing.
 */

public class OpenCommand extends Command {
    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens previously imported image. "
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD + " paris.jpg";

    public static final String MESSAGE_SUCCESS = "Image successfully opened.";

    private Album album = Album.getInstance();
    private String fileName;

    /**
     * Creates an OpenCommand to add the specified {@code args}
     */
    public OpenCommand(String args) {
        this.fileName = args;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history) throws CommandException {
        requireNonNull(currentEdit);
        Image toOpen;

        if (album.checkFileExist(fileName)) {
            toOpen = album.retrieveImage(fileName);
        } else {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        }
        currentEdit.openImage(toOpen);
        currentEdit.updateExif();
        currentEdit.displayTempImage();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toOpen));
    }
    /* @@author*/

    /* @@author carrein */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OpenCommand // instanceof handles nulls
                && fileName.equals(((OpenCommand) other).fileName)); // state check
    }
}
