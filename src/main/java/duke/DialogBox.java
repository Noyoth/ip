package duke;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * A custom control using HBox and FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a user dialog box with the speaker on the right.
     *
     * @param text The message text from the user.
     * @param img  The user display picture.
     * @return A DialogBox configured for user input.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a user dialog box with the speaker on the right.
     *
     * @param l  The label containing user text.
     * @param iv The user display picture view.
     * @return A DialogBox configured for user input.
     */
    public static DialogBox getUserDialog(Label l, ImageView iv) {
        return getUserDialog(l.getText(), iv.getImage());
    }

    /**
     * Creates a TBC dialog box with the speaker flipped to the left.
     *
     * @param text The response text from TBC.
     * @param img  The TBC display picture.
     * @return A DialogBox configured for TBC response.
     */
    public static DialogBox getTbcDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }

    /**
     * Creates a TBC dialog box with the speaker flipped to the left.
     *
     * @param l  The label containing TBC text.
     * @param iv The TBC display picture view.
     * @return A DialogBox configured for TBC response.
     */
    public static DialogBox getTbcDialog(Label l, ImageView iv) {
        return getTbcDialog(l.getText(), iv.getImage());
    }

    /**
     * Creates a Duke dialog box with the speaker flipped to the left.
     *
     * @param text The response text from Duke.
     * @param img  The Duke display picture.
     * @return A DialogBox configured for Duke response.
     */
    public static DialogBox getDukeDialog(String text, Image img) {
        return getTbcDialog(text, img);
    }

    /**
     * Creates a Duke dialog box with the speaker flipped to the left.
     *
     * @param l  The label containing Duke text.
     * @param iv The Duke display picture view.
     * @return A DialogBox configured for Duke response.
     */
    public static DialogBox getDukeDialog(Label l, ImageView iv) {
        return getTbcDialog(l, iv);
    }
}
