package view;

import javafx.scene.layout.BorderPane;


public class RootPane extends BorderPane {


	private ModulePickerRootPane rpmp = new ModulePickerRootPane();
	private MyMenuBar mmb = new MyMenuBar();

	public RootPane() {
		this.getStylesheets().add("view/styles.css"); //gets css stylesheet 

		this.setTop(mmb);
		this.setCenter(rpmp);

	}
	public MyMenuBar getMyMenuBar() {
		return mmb;
	}
	public ModulePickerRootPane getTabPane()
	{
		return rpmp;
	}
}
