package de.amedon.red.ui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import de.amedon.red.ui.dialogs.EditableChooseDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class FXDialogs {
	
	public static String openChooseDialog(String title, String headerText, String ContentText, List<String> choices, boolean editable) {
		EditableChooseDialog<String> dialog = new EditableChooseDialog<String>(choices.get(0), choices);
		if (editable) {
			dialog.setEditable(editable);
		}
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(ContentText);
		Optional<String> result = dialog.showAndWait();
		return result.isPresent() ? result.get() : null;
	}
	
	public static String openInputDialog(String title, String headerText, String ContentText) {
		return openInputDialog(title, headerText, ContentText, null);
	}
	
	public static String openInputDialog(String title, String headerText, String ContentText, String defaultContent) {
		TextInputDialog dialog = new TextInputDialog(defaultContent);
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(ContentText);
		Optional<String> result = dialog.showAndWait();
		return result.isPresent() ? result.get() : null;
	}
	
	public static String openChooseDialog(String title, String headerText, String ContentText, List<String> choices) {
		return openChooseDialog(title, headerText, ContentText, choices, false);
	}
	
	public static void info(String title, String headerText, String ContentText) {
		dialog(title, headerText, ContentText, AlertType.INFORMATION);
	}
	
	public static void error(String title, String headerText, String ContentText) {
		dialog(title, headerText, ContentText, AlertType.ERROR);
	}
	
	public static void exception(Exception e) {
		exception("Exception Dialog", "Bei der Verarbeitung ist ein Fehler aufgetreten","", e);
	}

	public static void exception(String title, String headerText, String ContentText, Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(ContentText);

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
	}
	
	public static void dialog(String title, String headerText, String ContentText, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(ContentText);

		alert.showAndWait();
	}
	
}
