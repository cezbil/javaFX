package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;


public class MyMenuBar extends MenuBar {

	private Menu menu;
	private Menu Help;
	private MenuItem exit;
	private MenuItem aboutItem;
	private MenuItem save;
	private MenuItem load;

	public MyMenuBar() {


		menu = new Menu("File");
		Help = new Menu("Help");

		load = new MenuItem("Load Student Data");
		load.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
		menu.getItems().add(load);

		aboutItem = new MenuItem("About");

		save = new MenuItem("Save Student Data");
		save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		menu.getItems().add(save);

		Help.getItems().add(aboutItem);

		menu.getItems().add(new SeparatorMenuItem());


		exit = new MenuItem("Exit");
		menu.getItems().add(exit);
		exit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));

		this.getMenus().addAll(menu, Help); 

	}

	public void addExitHandler(EventHandler<ActionEvent> handler) {
		exit.setOnAction(handler);
	}

	public void addSaveHandler(EventHandler<ActionEvent> handler) {
		save.setOnAction(handler);
	}

	public void addLoadHandler(EventHandler<ActionEvent> handler) {
		load.setOnAction(handler);
	}

	public void addAboutHandler(EventHandler<ActionEvent> handler) {
		aboutItem.setOnAction(handler);
	}

	public MenuItem getExit() {
		return exit;
	}
}
