/*

import static com.av.Main.logs;
import com.av.util.LoggerCreator;
import com.av.util.LoggerUtil;
import com.av.view.Customers;
import java.io.IOException;

import com.av.view.MainPageController;
import com.av.view.PackageController;
import com.av.view.Packages;
import com.av.view.RegisterController;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


 

public class Main extends Application {
    
        
        
	public static LoggerUtil  logs=null;
        public static LoggerCreator ls=null;
        public static LoggerCreator le=null;
	public static Stage primarystage;
	public static Stage registerCustomerStage;
        public static Stage editCustomerStage;
        public static Stage searchCustomerStage;
        public static Stage searchPackageStage;
        public static Stage editPackageStage;
	public static Stage mainPageStage;
	private Pane mainLayout;
	
	public Main() {
		super();
	}
	
	
	@Override
	public void start(Stage primaryStage) throws IOException, FileNotFoundException, InterruptedException {
                System.out.println("INSIDE FIRST");
                logs=new LoggerUtil();
                ls=logs.loggerSuccessUtil();
                le= logs.loggerErrorUtil();
                this.primarystage = primaryStage;
		this.primarystage.setTitle("ANAND VIHARI 'MyTravel' App");
		this.primarystage.setResizable(false);
		//this.registerCustomerStage.setTitle("Register Page");
		//this.registerCustomerStage.setResizable(false);
		showMainPageView();
                /*WebView webview = new WebView();
                webview.getEngine().load("http://www.google.com"
                 );
                webview.setPrefSize(1000, 720);
                primaryStage.setScene(new Scene(webview));
                primaryStage.show();
	}
	
	private void showMainPageView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/Login.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		primarystage.setScene(scene);
		scene.getStylesheets().add(Main.class.getResource("stylesheet.css").toExternalForm());
		//scene.getStylesheets().add("stylesheet.css");
		primarystage.show();
		Alert alert = new Alert(AlertType.INFORMATION);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(
		Main.class.getResource("stylesheet.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");
		//showMainPageView();
		alert.setTitle("INFO");
		alert.setTitle("WARNING !!!");
		alert.setHeaderText("CLOSING APP");
		alert.setContentText("EXITING...");
		primarystage.setOnCloseRequest(e -> alert.showAndWait());
               
                
               
	}


	public static void main(String[] args) {
		launch(args);
		//DBConnection.connect();
	}


	public void ShowRegisterCustomers() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainPageController.class.getResource("Register.fxml"));
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(Main.class.getResource("stylesheet.css").toExternalForm());
		registerCustomerStage = new Stage();
		registerCustomerStage.setTitle("REGISTER CUSTOMERS");
		registerCustomerStage.setResizable(false);
		registerCustomerStage.setScene(scene);
		registerCustomerStage.show();
		mainPageStage.hide();
		registerCustomerStage.setOnCloseRequest(e -> mainPageStage.show());
	}

        
        public void ShowEditCustomers(Customers s) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainPageController.class.getResource("Edit.fxml"));
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(Main.class.getResource("stylesheet.css").toExternalForm());
		editCustomerStage = new Stage();
		editCustomerStage.setTitle("EDIT CUSTOMER DETAILS");
		editCustomerStage.setResizable(false);
		editCustomerStage.setScene(scene);
                editCustomerStage.setUserData(s);
		editCustomerStage.show();
		registerCustomerStage.hide();
		editCustomerStage.setOnCloseRequest(e -> registerCustomerStage.show());
                
                RegisterController controller = loader.getController();
                controller.setCustomer(s);
	        //controller.setName((String) s);
	      
	}
        
        
        
	public void ShowCustomersDetails() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainPageController.class.getResource("SearchDetails.fxml"));
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(Main.class.getResource("stylesheet.css").toExternalForm());
		registerCustomerStage = new Stage();
		registerCustomerStage.setTitle("SEARCHED CUSTOMERS");
		registerCustomerStage.setResizable(false);
		registerCustomerStage.setScene(scene);
		registerCustomerStage.show();
		mainPageStage.hide();
		registerCustomerStage.setOnCloseRequest(e -> mainPageStage.show());
	}
	
	
	

	public void showErrorMessage() throws IOException {
		//FXMLLoader loader = new FXMLLoader();
		//loader.setLocation(MainPageController.class.getResource("Register.fxml"));
		//Scene scene = new Scene(loader.load());
		//registerCustomerStage = new Stage();
		//registerCustomerStage.setTitle("Register Page");
		//registerCustomerStage.setScene(scene);
		//registerCustomerStage.show();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("SUCCESS");
		alert.setHeaderText("REGISTRATION COMPLETE");
//		//alert.setContentText("You are existing from Application!");
		//registerCustomerStage.setOnShowing(e -> registerCustomerStage.show());
		
	}
	
	public void customerMonthBackAction() throws IOException {
		registerCustomerStage.hide();
		mainPageStage.show();
	}
	
	public void customerTripBackAction() throws IOException {
		registerCustomerStage.hide();
		mainPageStage.show();
	}
	
	public void customerPhoneBackAction() throws IOException {
		registerCustomerStage.hide();
		mainPageStage.show();
	}
	
	public void customerRegBackAction() throws IOException {
		registerCustomerStage.hide();
		mainPageStage.show();
	}


	public void ShowRegisterPackages() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainPageController.class.getResource("Enterpackages.fxml"));
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(Main.class.getResource("stylesheet.css").toExternalForm());
		registerCustomerStage = new Stage();
		registerCustomerStage.setTitle("REGISTER PACKAGES");
		registerCustomerStage.setResizable(false);
		registerCustomerStage.setScene(scene);
		registerCustomerStage.show();
		mainPageStage.hide();
		registerCustomerStage.setOnCloseRequest(e -> mainPageStage.show());		
	}


	public void ShowSearchPakages() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainPageController.class.getResource("SearchPackageDetails.fxml"));
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(Main.class.getResource("stylesheet.css").toExternalForm());
		registerCustomerStage = new Stage();
		registerCustomerStage.setTitle("SEARCHED PACKAGES");
		registerCustomerStage.setResizable(false);
		registerCustomerStage.setScene(scene);
		registerCustomerStage.show();
		mainPageStage.hide();
		registerCustomerStage.setOnCloseRequest(e -> mainPageStage.show());		
	}
        
        
        public void ShowEditPackages(Packages p) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainPageController.class.getResource("Editpackages.fxml"));
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(Main.class.getResource("stylesheet.css").toExternalForm());
		editPackageStage = new Stage();
		editPackageStage.setTitle("EDIT PACKAGES");
		editPackageStage.setResizable(false);
		editPackageStage.setScene(scene);
                editPackageStage.setUserData(p);
		editPackageStage.show();
		registerCustomerStage.hide();
		editPackageStage.setOnCloseRequest(e -> registerCustomerStage.show());
                
                PackageController controller = loader.getController();
                controller.setPackage(p);
	                    
                
	      
	}
        
        


	public void navigateToAdminPage() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainPageController.class.getResource("MainPage.fxml"));
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(Main.class.getResource("stylesheet.css").toExternalForm());
		mainPageStage = new Stage();
		mainPageStage.setTitle("MAIN PAGE");
		mainPageStage.setResizable(false);
		mainPageStage.setScene(scene);
		mainPageStage.show();
		primarystage.hide();
		mainPageStage.setOnCloseRequest(e -> primarystage.show());
		
	}


	public void logoutAction(String currentUser) {
                System.out.println("THE CURRENT USER IS : "+currentUser);
                ls.info(currentUser + "  :  Logged Out Successfully");
		primarystage.show();
		mainPageStage.hide();
		//registerCustomerStage.hide();
                backupDataWithOutDatabase("c:/FOLDER/", "127.0.0.1", "3306", "root", "krishna!8", "anandvihari", "c:/FOLDER/");
                
	}
	
        public boolean backupDataWithOutDatabase(String dumpExePath, String host, String port, String user, String password, String database, String backupPath) {
boolean status = false;
try {
Process p = null;
 
DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
Date date = new Date();
String filepath = "backup(without_DB)-" + database + "-" + host + "-(" + dateFormat.format(date) + ").sql";
 
String batchCommand = "";
if (password != "") {
//only backup the data not included create database
batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --password=" + password + " " + database + " -r \"" + backupPath + "" + filepath + "\"";
} else {
batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " " + database + " -r \"" + backupPath + "" + filepath + "\"";
}
 
Runtime runtime = Runtime.getRuntime();
p = runtime.exec(batchCommand);
int processComplete = p.waitFor();
 
if (processComplete == 0) {
status = true;
    System.out.println("Backup created successfully for without DB " + database + " in " + host + ":" + port);
} else {
status = false;
System.out.println("Could not create the backup for without DB " + database + " in " + host + ":" + port);
}
 
} catch (IOException ioe) {
System.out.println(ioe.getCause());
} catch (Exception e) {
System.out.println(e.getCause());
}
return status;
}

    }
*/

