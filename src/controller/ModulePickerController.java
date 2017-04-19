package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import model.Course;
import model.Module;
import model.Name;
import model.StudentProfile;
import view.ModulePickerRootPane;
import view.MyMenuBar;
import view.RootPane;
import view.OverviewSelectionPane;
import view.SelectModulesPane;
import view.SetupModulePicker;

public class ModulePickerController {

	private ModulePickerRootPane view; 
	private SetupModulePicker smp;
	private OverviewSelectionPane osp;
	private SelectModulesPane selectmp;
	private StudentProfile model = null;
	private MyMenuBar mmb;
	private int creditsCount = 0;

	public ModulePickerController(RootPane view2, StudentProfile model) {
		this.model = model;
		this.view = view2.getTabPane();
		smp = view.getSetupModulePicker();
		osp = view.getOverviewSelectionPane();
		selectmp = view.getSelectModulesPane();
		mmb = view2.getMyMenuBar();
		this.attachEventHandlers();
		this.readCourses();
	}


	void readCourses() { //reads in courses form a text file 
		List<Course> courses = new ArrayList<>();
		FileInputStream fis = null;
		try {
			File file = new File("courses.txt");
			if (!file.exists() || file.isDirectory()) {
				return;
			}
			fis = new FileInputStream(file);
			Scanner fileReader = new Scanner(fis);
			String line;
			Course course;
			while (fileReader.hasNextLine()){ //loops while reader can get new line
				line = fileReader.nextLine();
				if (line.length() == 0) {//skips empty line
					continue;
				}
				course = new Course(line);
				courses.add(course);
				readModules(course, fileReader.nextLine());
			}
			fileReader.close();
			smp.populateComboBox(courses);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(ModulePickerController.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				Logger.getLogger(ModulePickerController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	void readCourse(StudentProfile student) { //reading in course from a file
		Course course = student.getCourse();
		List<Module> modules = new ArrayList<>();
		FileInputStream fis = null;
		try {
			File file = new File("courses.txt"); //reads courses from a text file 
			if (!file.exists() || file.isDirectory()) {
				return;
			}
			fis = new FileInputStream(file);
			Scanner fileReader = new Scanner(fis);
			String line;
			while (fileReader.hasNextLine()) {
				line = fileReader.nextLine();
				if (line.length() == 0) {
					continue;
				}
				if(line.compareTo(course.getCourseName()) == 0)
				{
					readModules(course, fileReader.nextLine());
					break;
				}
			}
			fileReader.close();
			modules.addAll(course.getModulesOnCourse());

			for(Module m : student.getSelectedModules())
			{
				for(int i = 0; i<modules.size(); ++i)
				{
					Module m2 = modules.get(i);
					if(m.getModuleCode().compareTo(m2.getModuleCode()) == 0)
						modules.remove(m2);
				}
			}
			selectmp.getChoosableListView().getItems().clear();
			selectmp.getChoosableListView().getItems().addAll(modules);

			selectmp.getChosenListView().getItems().clear();
			selectmp.getChosenListView().getItems().addAll(student.getSelectedModules());
		} catch (FileNotFoundException ex) {
			Logger.getLogger(ModulePickerController.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				Logger.getLogger(ModulePickerController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}


	void readModules(Course course, String fileName) {//this method reads in modules from file using fileinputstream and scanner
		FileInputStream fis = null;
		try {
			File file = new File(fileName);
			if (!file.exists() || file.isDirectory()) {//check if file selected and if file is not a directory
				return;
			}
			fis = new FileInputStream(fileName);
			Scanner fileReader = new Scanner(fis);
			String code, name;
			int credits;
			boolean mandatory;
			while (fileReader.hasNextLine()) {
				code = fileReader.nextLine();
				if (code.length() == 0) {
					continue;
				}
				name = fileReader.nextLine();
				credits = fileReader.nextInt();
				mandatory = fileReader.nextBoolean();
				course.addModule(new Module(code, name, credits, mandatory));//creates new modules from file

			}
			fileReader.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(ModulePickerController.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				Logger.getLogger(ModulePickerController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private void attachEventHandlers() { //attaches handlers to the view
		smp.addCourseListener(new CreateProfileHandler()); //attaches button handler
		selectmp.addAddButtonHandler(new AddButtonModuleHandler());
		selectmp.addResetButtonHandler(new ResetModuleHandler());
		selectmp.addRemoveButtonHandler(new RemoveButtonModuleHandler());
		selectmp.addSubmitButtonModuleHandler(new SubmitButtonModuleHandler());	
		mmb.addExitHandler(new ExitHandler());
		mmb.addAboutHandler(new setAboutHandler());
		osp.addSaveButtonModuleHandler(new setSaveButtonHandler());
		mmb.addSaveHandler(new setSaveButtonMenuHandler());
		mmb.addLoadHandler(new setOpenButtonMenuHandler());
	}


	private class setOpenButtonMenuHandler implements EventHandler<ActionEvent> { //this handler is responsible for opening saved files through menu bar

		@Override
		public void handle(ActionEvent e) {
			FileInputStream fis = null;
			try {
				FileChooser fileChooser = new FileChooser(); //creating new filechooser
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("StudentProfile files (*.StudentProfile)", "*.StudentProfile"); //sets extensions that we will use for our studentprofile
				fileChooser.getExtensionFilters().add(extFilter);
				File file = fileChooser.showOpenDialog(null);
				if(file == null)
					return;
				if (!file.exists() || file.isDirectory()) {
					return;
				}
				fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);//creating new object input stream
				model = (StudentProfile) ois.readUnshared(); // read in Studentprofile from file to model field
				creditsCount = 0;
				for (Module m : model.getSelectedModules()) {
					creditsCount += m.getCredits();
				}
				selectmp.getCreditsLabel().setText(String.valueOf(creditsCount));
				readCourse(model); //reads courses in according to saved file
				smp.setTxtPnum(model.getpNumber());
				smp.setTxtFirstName(model.getStudentName().getFirstName());
				smp.setTxtSurname(model.getStudentName().getFamilyName());
				smp.setTxtEmail(model.getEmail());
				smp.setDatePicker(model.getDate()); //sets text fields to information loaded from file
				Alert loading = new Alert(AlertType.INFORMATION); //alerts user that they loaded information from file
				loading.setHeaderText("loading completed");
				loading.setContentText("Please submit modules selected");
				loading.showAndWait();
				view.changeTab(1);
				try {
					fis.close();
				} catch (IOException ex) {
					Logger.getLogger(ModulePickerController.class.getName()).log(Level.SEVERE, null, ex);
				}
			} catch (FileNotFoundException ex) {
				Logger.getLogger(ModulePickerController.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Logger.getLogger(ModulePickerController.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(ModulePickerController.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

	}

	private class AddButtonModuleHandler implements EventHandler<ActionEvent> { //handler responsible for adding modules

		@Override
		public void handle(ActionEvent e) {
			Module m = selectmp.getChoosableListView().getSelectionModel().getSelectedItem();
			if (m != null && (creditsCount + m.getCredits() <= 120)) { //adds till we reach 120 credits
				model.addSelectedModule(m);

				creditsCount += m.getCredits();
				selectmp.getChoosableListView().getItems().remove(m);//removes added item from 1st list
				selectmp.getChosenListView().getItems().add(m);
				selectmp.getCreditsLabel().setText(String.valueOf(creditsCount));
			}
		}

	}

	private class RemoveButtonModuleHandler implements EventHandler<ActionEvent> { //handler responsible for remove button work

		@Override
		public void handle(ActionEvent e) {
			Module m = selectmp.getChosenListView().getSelectionModel().getSelectedItem(); //gets selected item 
			if (m== null){

				return;
			}
			else{
				if (!m.isMandatory()) {//check if module is mandatory, if no continue
					if (m != null) { //if something is selected remove it
						model.getSelectedModules().remove(m);
						creditsCount -= m.getCredits();
						selectmp.getChoosableListView().getItems().add(m);
						selectmp.getChosenListView().getItems().remove(m);
						selectmp.getCreditsLabel().setText(String.valueOf(creditsCount));
					}
				}
			}
		}

	}


	private class SubmitButtonModuleHandler implements EventHandler<ActionEvent> {//this handler is responsible for submit button work

		@Override
		public void handle(ActionEvent e) {

			ObservableList<Module> items = FXCollections.observableArrayList(model.getSelectedModules());
			if (Integer.valueOf(selectmp.getCreditsLabel().getText()) < 120) {//if less than 120 credits selected show alert
				Alert fail = new Alert(AlertType.INFORMATION);
				fail.setHeaderText("Not enough modules selected");
				fail.setContentText("Please enter all of the modules required");
				fail.showAndWait();
			} else {//else, it prints this message into overview text area for user to review and save
				osp.setSelection(
						"Name: " + model.getStudentName().getFullName()
						+ "\n"
						+ "Pno: " + model.getpNumber()
						+ "\n"
						+ "Email: " + model.getEmail()
						+ "\n"
						+ "Date: " + model.getDate()
						+ "\n"
						+ "Course: " + model.getCourse().getCourseName()
						+ "\n"
						+ "\n"
						+ "\n"
						+ "Selected Modules"
						+ "\n"
						+ "================="
						+ "\n"
						+ "\n"
						);
				for (Module m : items) {//prints out module information
					osp.appendSelection(
							"Module Code: " + m.getModuleCode()
							+ "\n"
							+ "Mandatory on your course? " + m.isMandatory()
							+ "\n"
							+ "Module name: " + m.getModuleName()
							+ "\n"
							+ "Ammount of Credits: " + m.getCredits()
							+ "\n"
							+ "\n"
							);

				}

				view.changeTab(2);

			}
		}
	}

	private class ExitHandler implements EventHandler<ActionEvent> { //handler for exit button

		public void handle(ActionEvent e) {

			Platform.exit();//exits application

		}
		;

	};
	private class setAboutHandler implements EventHandler<ActionEvent> { //handler for menu button about, when clicked shows alert box with information about application and author

		public void handle(ActionEvent e) {

			Alert about = new Alert(AlertType.INFORMATION);//creates new informational alert
			about.setHeaderText("Module picker GUI");
			about.setContentText("This is a module picker for a second year DMU Computer Science and Software Engineering students. " + "\n" + "Author Cezary Bilda 2016");
			about.showAndWait();

		}
		;

	};

	private class setSaveButtonHandler implements EventHandler<ActionEvent> { //overview saving

		public void handle(ActionEvent e) {
			FileChooser fileChooser = new FileChooser();

			//Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(extFilter);

			//Show save file dialog
			File file = fileChooser.showSaveDialog(null);

			if (file != null) {
				SaveFile(osp.getSelection().getText(), file); //saves file from textarea in overview tab

			};

		}
	}

	private class setSaveButtonMenuHandler implements EventHandler<ActionEvent> { // handler for menu save button

		public void handle(ActionEvent e) {

			try {
				FileChooser fileChooser = new FileChooser();
				FileOutputStream out;

				//Set extension filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("StudentProfile files (*.StudentProfile)", "*.StudentProfile");
				fileChooser.getExtensionFilters().add(extFilter);

				//Show save file dialog
				File file = fileChooser.showSaveDialog(null);
				if (file == null) {
					return;
				}
				out = new FileOutputStream(file);
				ObjectOutputStream oout = new ObjectOutputStream(out);
				oout.writeUnshared(model);// here we take our model and we write it to the file with usage of ObjectOutputStream as a binary file instead of text
				out.close();
			} catch (FileNotFoundException ex) {
				Logger.getLogger(ModulePickerController.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Logger.getLogger(ModulePickerController.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}

	private void SaveFile(String content, File file) { //method that describes process of saving file

		FileOutputStream fos = null; 
		try {

			fos = new FileOutputStream(file);
			OutputStreamWriter sw = new OutputStreamWriter(fos);
			String[] lines = content.split("\n");//splits every line of the text so when it is saved it looks exactly as in overview
			try {
				for (String line : lines) {
					sw.write(line);
					sw.write(System.getProperty("line.separator"));//adds new line separator, againt to make saved file look like overview
				}
				sw.close(); //closes stream writer, no leak
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		} catch (FileNotFoundException ex) {
			Logger.getLogger(ModulePickerController.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				fos.close();
			} catch (IOException ex) {
				Logger.getLogger(ModulePickerController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private class ResetModuleHandler implements EventHandler<ActionEvent> { //resets lists by populating them again

		public void handle(ActionEvent e) {
			populateListViews();

		}
	}

	private class CreateProfileHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			if (userValid()) {
				model.setCourse(smp.getSelectedCourse()); //gets data from combo box
				model.setpNumber(smp.getPnumInput());//gets data from text fields and adds it to the model
				model.setDate(smp.getDate()); //gets data from datepicker
				model.setEmail(smp.getEmail());
				Name studentname = new Name(smp.getFNameInput(), smp.getLNameInput());
				model.setStudentName(studentname);

				populateListViews(); //populates lists

				view.changeTab(1); //goes to the next tab
			}
		}
	}

	private void populateListViews() { //this method populates both list views

		creditsCount = 0; 
		selectmp.getChoosableListView().getItems().clear(); //clears choosable list  if there was anything in it
		selectmp.getChosenListView().getItems().clear();
		model.clearSelectedModules(); //clears selected modules in model
		ObservableList<Module> items = FXCollections.observableArrayList(model.getCourse().getModulesOnCourse());
		ObservableList<Module> items2 = FXCollections.observableArrayList();
		for (Module m : items) {//loops through the list of modules to add mandatory modules to chosen list
			if (m.isMandatory()) {

				creditsCount += m.getCredits();

				items2.add(m);
				model.addSelectedModule(m);

			}
		}
		selectmp.getCreditsLabel().setText(String.valueOf(creditsCount)); //adds module credits to total
		for (Module m : items2) {
			items.remove(m); //removes modules from list

		}
		selectmp.populateListView(items); //populates choosable listview
		selectmp.getChosenListView().setItems(items2); //adds items to chosen list, mandatory modules

	}

	private Boolean userValid() { //this method is used for user data validation, if user did not enter all the data needed it will return pop up alert
		if (smp.getPnumInput().isEmpty() || smp.getDate() == null || smp.getEmail().isEmpty() || smp.getFNameInput().isEmpty() || smp.getLNameInput().isEmpty()) {
			Alert fail = new Alert(AlertType.INFORMATION);
			fail.setHeaderText("Not enough data");
			fail.setContentText("Please enter all of the data required");
			fail.showAndWait();
			return false;
		}
		return true;
	}



}
