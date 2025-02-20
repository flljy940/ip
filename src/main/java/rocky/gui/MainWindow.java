package rocky.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import rocky.Rocky;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    private static final Font FONT = new Font("Georgia", 18);

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Rocky rocky;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/RylandGrace.png"));
    private final Image rockyImage = new Image(this.getClass().getResourceAsStream("/images/Rocky.png"));

    /**
     * Initializes window GUI settings
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        userInput.setFont(FONT);
        sendButton.setFont(FONT);
    }

    /**
     * Sets the Rocky object for this window instance
     * @param rocky Rocky object to interact with
     */
    public void setRocky(Rocky rocky) {
        this.rocky = rocky;
        rockySpeak(rocky.getIntroduction());
    }

    /**
     * Adds the message in a user dialog box
     * @param message message from user
     */
    private void userSpeak(String message) {
        dialogContainer.getChildren().add(DialogBox.getUserDialog(message, userImage));
    }

    /**
     * Adds the message in a Rocky dialog box
     * @param message message from Rocky
     */
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

        if (rocky.isStopped()) {
            closeWindow();
        }
    }

    /**
     * Closes the MainWindow and exits the program.
     */
    private void closeWindow() {
        // Gets the handle to the window object
        Stage stage = (Stage) this.userInput.getScene().getWindow();
        stage.close();
        System.exit(0);
    }
}
