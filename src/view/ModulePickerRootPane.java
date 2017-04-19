package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class ModulePickerRootPane extends TabPane {

	private SetupModulePicker smp;
	private OverviewSelectionPane osp;
	private SelectModulesPane selectmp;
	public ModulePickerRootPane() {

		this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		smp = new SetupModulePicker();
		osp = new OverviewSelectionPane();
		selectmp = new SelectModulesPane();
		Tab t1 = new Tab("Create Profile", smp); //create tabs
		Tab t2 = new Tab("Select Modules", selectmp);
		Tab t3 = new Tab("Overview Results", osp);
		this.getTabs().addAll(t1, t2, t3);
	}

	public SetupModulePicker getSetupModulePicker() {
		return smp;
	}

	public OverviewSelectionPane getOverviewSelectionPane() {
		return osp;
	}

	public SelectModulesPane getSelectModulesPane() { 
		return selectmp;
	}


	//method to allow the controller to change tabs
	public void changeTab(int index) {
		this.getSelectionModel().select(index);
	}

}
