package com.av.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.av.Main;
import com.av.util.Util;
import java.awt.Desktop;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchPackageController {

	@FXML
	TableView<Packages> packageTable;

	ObservableList<String> tourTypeList = FXCollections.observableArrayList("South India Tours",
			"Domestic Tours by Flight", "North India Tours", "Tours Abroad");
	ObservableList<String> tripCodeList = FXCollections.observableArrayList();

	@FXML
	private ComboBox<String> tourTypeBox;
	@FXML
	private ComboBox<String> tripCodeBox;
	@FXML
	private Button packageDelButton;

	@FXML
	private Label userLabel;

	@FXML
	private void initialize() {
		tourTypeBox.setValue("Select Tour Type");
		tourTypeBox.setItems(tourTypeList);
		tripCodeBox.setValue("Select Trip Code");

		if (Users.getRole().equalsIgnoreCase("EMPLOYEE")) {
			packageDelButton.setVisible(false);
		} else {
			packageDelButton.setVisible(true);
		}
		userLabel.setText(Users.getUserName());

	}

	@FXML
	TableColumn packageNameColPackage;
	@FXML
	TableColumn tourTypeColPackage;
	@FXML
	TableColumn tripCodeColPackage;
	@FXML
	TableColumn fareColPackage;
	@FXML
	TableColumn travelModeColPackage;
	@FXML
	TableColumn citiesColPackage;
	@FXML
	TableColumn noOfDaysColPackage;
	@FXML
	TableColumn departureColPackage;
	@FXML
	TableColumn distanceColPackage;
	@FXML
	TableColumn createdDateColPackage;

	@FXML
	Button packageSearch;

	@FXML
	Button exportToExcel;

	@FXML
	Label packageCount;

	@FXML
	private Main main = new Main();

	@FXML
	private ObservableList<Packages> searchByPackage() throws IOException, ClassNotFoundException {

		packageNameColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("packageName"));
		tripCodeColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("tripCode"));
		noOfDaysColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("noOfDays"));
		fareColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("fare"));
		travelModeColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("travelMode"));
		tourTypeColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("tourType"));// passport
		distanceColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("distance"));// tour
		citiesColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("cities"));// tour
		departureColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("departures"));// tour
		createdDateColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("createdDate"));// tour

		ObservableList<Packages> packages = FXCollections.observableArrayList();

		Connection con = null;
		String retriveCust = null;
		PreparedStatement pstmt = null;
		try {

			Util util = new Util();
			con = util.openConnection();
			if (!tripCodeBox.getValue().toString().equalsIgnoreCase("ALL")) {
				retriveCust = "select * from packages where tour_type like ? and trip_code= ?";
			} else
				retriveCust = "select * from packages where tour_type like ? ";
			pstmt = con.prepareStatement(retriveCust);
			if ("Select Tour Type".equalsIgnoreCase(tourTypeBox.getValue().toString())) {
				showValidationAlert("Select the Tour Type!!!");
				tourTypeBox.requestFocus();
				return null;
			} else if ("Select Trip Code".equalsIgnoreCase(tripCodeBox.getValue().toString())) {
				showValidationAlert("Select Trip Code!!");
				tripCodeBox.requestFocus();
				return null;
			}

			if (!tripCodeBox.getValue().toString().equalsIgnoreCase("ALL"))
				pstmt.setString(2, tripCodeBox.getValue().toString());
			pstmt.setString(1, tourTypeBox.getValue().toString());
			ResultSet rs = pstmt.executeQuery();
			int count = 1;
			while (rs.next()) {
				Packages p = new Packages(rs.getString("packages_name"), rs.getString("trip_code"),
						rs.getString("no_of_days"), rs.getString("fare"), rs.getString("travel_mode"),
						rs.getString("tour_type"), rs.getString("distance"), rs.getString("cities"),
						rs.getString("departures"), rs.getString("created_date"), rs.getString("packages_pk"));
				packages.add(p);
				count++;
			}

			if (count == 1) {
				showValidationAlert("No Records Found!!");
				packageTable.setItems(null);
				packageCount.setText(String.valueOf(0));
				tourTypeBox.setValue("Select Tour Type");
				tripCodeBox.setValue("Select Trip Code");
				return null;
			}
			packageTable.setItems(packages);
			packageCount.setText(String.valueOf(packages.size()));
			return packages;
		} catch (SQLException e) {
                         Main.le.error("Search By Package Error : " , e);
			e.printStackTrace();
		} finally {
			// 5. Close ALL JDBC Objects
			System.out.println("In Finally block");
			try {
				if (con != null) {
					con.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // End of try-catch block
		return packages;
	}// End of main

	private void showValidationAlert(String msg) {

		Alert alert = new Alert(AlertType.INFORMATION);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(Main.class.getResource("stylesheet.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");
		// showMainPageView();
		alert.setTitle("INFO");
		alert.setHeaderText(msg);
		alert.showAndWait();
	}

	@FXML
	private void getCustomerMonthBackAction() throws IOException {
		main.customerMonthBackAction();
	}

	@FXML
	private void getCustomerTripBackAction() throws IOException {
		main.customerTripBackAction();
	}

	@FXML
	private void getCustomerPhoneBackAction() throws IOException {
		main.customerPhoneBackAction();
	}

	@FXML
	private String deleteRegDetails() throws IOException, ClassNotFoundException {

		packageNameColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("packageName"));
		tripCodeColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("tripCode"));
		noOfDaysColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("noOfDays"));
		fareColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("fare"));
		travelModeColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("travelMode"));
		tourTypeColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("tourType"));// passport
		distanceColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("distance"));// tour
		citiesColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("cities"));// tour
		departureColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("departures"));// tour
		createdDateColPackage.setCellValueFactory(new PropertyValueFactory<Packages, String>("createdDate"));// tour

		ObservableList<Packages> packages = FXCollections.observableArrayList();
		Packages p = packageTable.getSelectionModel().getSelectedItem();
		if (null == p) {
			showValidationAlert("Select the Record first!!");
			packageSearch.requestFocus();
			return null;
		}

		String pk = p.getTripCode();
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Util util = new Util();
			con = util.openConnection();
			String retrivePackages = "delete from packages where trip_code = ? "; // and
																					// year
																					// =
																					// ?";
			if ("Select Tour Type".equalsIgnoreCase(tourTypeBox.getValue().toString())) {
				showValidationAlert("Select the Tour Type!!!");
				tourTypeBox.requestFocus();
				return null;
			} else if ("Select Trip Code".equalsIgnoreCase(tripCodeBox.getValue().toString())) {
				showValidationAlert("Select Trip Code!!");
				tripCodeBox.requestFocus();
				return null;
			}
			pstmt = con.prepareStatement(retrivePackages);
			pstmt.setString(1, pk);

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Are you sure , Want to DELETE!!");
			alert.setHeaderText("Look, a record is being deleted!");
			alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				int cou = pstmt.executeUpdate();
                                if(0!=cou){
                                    Main.ls.info("Package deleted successfully :  " + packageTable.getSelectionModel().getSelectedItem().getPackageName());
                                }
			} else {
				return "false";
			}

			searchByPackage();
			return "true";
		} catch (SQLException e) {
                        Main.le.error("Error Deleting Package :  " + packageTable.getSelectionModel().getSelectedItem().getPackageName(), e);
			e.printStackTrace();
		} finally {
			// 5. Close ALL JDBC Objects
			System.out.println("In Finally block");
			try {
				if (con != null) {
					con.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // End of try-catch block
		return "true";

	}

	@FXML
	private void writeToExcel() throws FileNotFoundException, IOException {

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet spreadsheet = workbook.createSheet("sample");

			HSSFRow row = null;
			HSSFCell cell = null;

			row = spreadsheet.createRow(0);
			cell = row.createCell(0);
			cell.setCellValue("SL NO");
			cell = row.createCell(1);
			cell.setCellValue("Package Name");
			cell = row.createCell(2);
			cell.setCellValue("Tour Type");
			cell = row.createCell(3);
			cell.setCellValue("Trip Code");
			cell = row.createCell(4);
			cell.setCellValue("Fare");
			cell = row.createCell(5);
			cell.setCellValue("Travel Mode");
			cell = row.createCell(6);
			cell.setCellValue("Cities");
			cell = row.createCell(7);
			cell.setCellValue("No Of Days");
			cell = row.createCell(8);
			cell.setCellValue("Departures");
			cell = row.createCell(9);
			cell.setCellValue("Distance");
			cell = row.createCell(10);
			cell.setCellValue("Created Date");

			if (packageTable.getItems().size() == 0) {
				showValidationAlert("No Record found to export!!");
				packageSearch.requestFocus();
				return;
			}

			for (int i = 0; i < packageTable.getItems().size(); i++) {
				row = spreadsheet.createRow(i + 1);
				row.createCell(0).setCellValue(i + 1);
				for (int j = 0; j < packageTable.getColumns().size(); j++) {
					row.createCell(j + 1).setCellValue(packageTable.getColumns().get(j).getCellData(i).toString());
				}
			}

			// row = spreadsheet.createRow(2);

			/*
			 * cell = row.createCell(1);
			 * cell.setCellValue(tblComponent.getCellData(1).toString());
			 * 
			 * cell = row.createCell(2);
			 * cell.setCellValue(tblActivity.getCellData(1).toString());
			 * 
			 * cell = row.createCell(3);
			 * cell.setCellValue(tableColumnForTor.getCellData(1).toString());
			 * 
			 * cell = row.createCell(4);
			 * cell.setCellValue(tblContract.getCellData(1).toString());
			 * 
			 * cell = row.createCell(5);
			 * cell.setCellValue(tblfirst.getCellData(1).toString());
			 * 
			 * cell = row.createCell(6);
			 * cell.setCellValue(tblFinal.getCellData(1).toString());
			 * 
			 * cell = row.createCell(7);
			 * cell.setCellValue(tblBank.getCellData(1).toString());
			 * 
			 * cell = row.createCell(8);
			 * cell.setCellValue(tblDisclosure.getCellData(1).toString());
			 * 
			 * cell = row.createCell(9);
			 * cell.setCellValue(tblNema.getCellData(1).toString());
			 * 
			 * cell = row.createCell(10); cell.setCellValue(tblBudgetRe
			 * .getCellData(1).toString());
			 * 
			 * cell = row.createCell(11);
			 * cell.setCellValue(tblBudgetPro.getCellData(1).toString());
			 * 
			 * cell = row.createCell(12);
			 * cell.setCellValue(tblBegin.getCellData(1).toString());
			 * 
			 * cell = row.createCell(13);
			 * cell.setCellValue(tblComments.getCellData(1).toString());
			 */

			/*
			 * Row row = null; Cell cell = null;
			 * 
			 * for (int i=0;i<jtable.getRowCount();i++) { row =
			 * sheet.createRow(i);
			 * 
			 * for (int j=0;j<jtable.getColumnCount();j++) {
			 * 
			 * cell = row.createCell(j); cell.setCellValue((String)
			 * jtable.getValueAt(i, j)); } }
			 */

			// row = spreadsheet.createRow(2);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
			Date date = new Date();
			String file_date = dateFormat.format(date);

			/*FileOutputStream out = new FileOutputStream(new File(Util.getFilePath() + "PackageInfo_"
					+ tourTypeBox.getValue().toString() + "_" + file_date + ".csv"));
			workbook.write(out);
			showValidationAlert("Records Stored in path!! -" + Util.getFilePath() + "PackageInfo_"
					+ tourTypeBox.getValue().toString() + "_" + file_date + ".csv");
			out.close();*/
			System.out.println("Data is wrtten Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void logoutAction() throws IOException {

		main.logoutAction();
		showValidationAlert("Logged out Successfully !!");
	}

	@FXML
	private void editPackageDetails() throws Exception {

		Packages packageDetails = packageTable.getSelectionModel().getSelectedItem();
		if (null == packageDetails) {
			showValidationAlert("Select the Record first!!");
			packageSearch.requestFocus();
			return;
		}

		if (packageDetails != null) {
			main.ShowEditPackages(packageDetails);

		}

	}

	@FXML
	private void getTripCode() throws IOException {
		Connection con = null;
		String retriveCust = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		tripCodeList.clear();
		List<String> tripCodeListquery = new ArrayList<String>();
		try {

			Util util = new Util();
			con = util.openConnection();
			retriveCust = "select * from packages where tour_type like ? ";
			pstmt = con.prepareStatement(retriveCust);
			pstmt.setString(1, tourTypeBox.getValue().toString());
			rs = pstmt.executeQuery();
			tripCodeList.add("ALL");
			while (rs.next())
				tripCodeListquery.add(rs.getString("trip_code"));
			tripCodeList.addAll(tripCodeListquery);

			tripCodeBox.setItems(tripCodeList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
        
        
        @FXML
	public void enableInternet() {
            try {
            Desktop.getDesktop().browse(new URL("http://www.twisters.tech").toURI());
        } 
        catch (MalformedURLException ex) {
            ex.printStackTrace();
        } 
        catch (URISyntaxException | IOException ex){
        ex.printStackTrace();
		
	}
     }
}


