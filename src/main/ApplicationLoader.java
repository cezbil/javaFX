package main;

import controller.ModulePickerController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.StudentProfile;
import view.RootPane;


public class ApplicationLoader extends Application {

	private RootPane view;
	@Override
	public void init() {
		//create model and view and pass their references to the controller
		StudentProfile model = new StudentProfile();
		view = new RootPane();
		new ModulePickerController(view, model);
			
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setMinWidth(510); //sets min width and height for the stage window
		stage.setMinHeight(300);
		stage.setTitle("module picker GUI");
		stage.setScene(new Scene(view));
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
