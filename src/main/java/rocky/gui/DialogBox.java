package rocky.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * An example of a custom control using FXML
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    public DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setFont(new Font("Verdana", 14));
        dialog.setText(text);

        displayPicture.setImage(img);

        // @@author flljy940-reused
        // Reused from https://stackoverflow.com/questions/20489908/border-radius-and-shadow-on-imageview
        // with some minor modifications
        Rectangle clip = new Rectangle(
                displayPicture.getFitHeight(), displayPicture.getFitHeight()
        );
        clip.setArcWidth(displayPicture.getFitWidth());
        clip.setArcHeight(displayPicture.getFitHeight());
        displayPicture.setClip(clip);
        // @@author
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

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    public static DialogBox getRockyDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
