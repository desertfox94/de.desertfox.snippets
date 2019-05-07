package de.desertfox.snippets.javafx.progressDialog;

import java.io.IOException;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ProgressViewController<T> {

	private static final String VIEW = "ProgressView.fxml";

	@FXML
	private ProgressBar progressBar;

	@FXML
	private Label header;

	@FXML
	private Label details;

	@FXML
	private Button cancelButton;

	@FXML
	private GridPane root;

	public void initialize() {
	}

	public Pane getRoot() {
		return root;
	}

	public void start(Task<T> task) {
		start(task, null);
	}

	public void start(Task<T> task, Window owner) {

		progressBar.setProgress(-1);
		progressBar.progressProperty().bind(task.progressProperty());
		header.textProperty().bind(task.titleProperty());
		details.textProperty().bind(task.messageProperty());

		root.layout();

		Stage dialog = new Stage();
		dialog.setTitle("Fortschritt");
		Scene scene = new Scene(root);
		dialog.setScene(scene);
		dialog.initOwner(owner);
		dialog.initModality(Modality.APPLICATION_MODAL);
		// TODO: add application icon here:
		// dialog.getIcons().add(App.icon());

		cancelButton.setOnAction(e -> task.cancel(true));
		task.runningProperty().addListener((v, o, n) -> {
			if (o == null)
				return;
			if (o == true && n == false) {
				dialog.close();
			}
		});
		new Thread(task).start();
		dialog.showAndWait();
	}

	public void setCancelable(boolean cancelable) {
		cancelButton.setDisable(!cancelable);
	}

	public static <S> void show(Task<S> task, Runnable onSucceed) {
		try {
			FXMLLoader loader = new FXMLLoader(ProgressViewController.class.getResource(VIEW));
			loader.load();
			ProgressViewController<S> controller = loader.getController();
			if (onSucceed != null) {
				task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					@Override
					public void handle(WorkerStateEvent event) {
						onSucceed.run();
					}
				});
			}
			controller.start(task);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static <S> void show(Task<S> task) {
		show(task, null);
	}

}
