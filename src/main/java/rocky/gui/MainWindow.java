package rocky.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import rocky.Rocky;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private final Font FONT = new Font("Georgia", 18);

    private Rocky rocky;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/RylandGrace.png"));
    private Image rockyImage = new Image(this.getClass().getResourceAsStream("/images/Rocky.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        userInput.setFont(FONT);
        sendButton.setFont(FONT);
    }

    /**
     * Injects the Duke instance
     * @param r rocky
     */
    public void setRocky(Rocky r) {
        rocky = r;
        rockySpeak(rocky.getIntroduction());
    }

    private void userSpeak(String message) {
        dialogContainer.getChildren().add(DialogBox.getUserDialog(message, userImage));
    }

    private void rockySpeak(String message) {
        dialogContainer.getChildren().add(DialogBox.getRockyDialog(message, rockyImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Rocky's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        userSpeak(input);

        String response = rocky.interact(input);
        rockySpeak(response);

        userInput.clear();
    }
}
