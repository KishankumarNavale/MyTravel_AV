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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchController {
	static boolean editFlag = false;
	@FXML
	private ComboBox<String> tripMonthBox;

	@FXML
	private ComboBox<String> tripYearBox;

	@FXML
	TableView<Customers> customerMonthTable;

	@FXML
	TableView<Customers> customerPhoneNumberTable;

	@FXML
	TableView<Customers> customerFollowUpTable;

	@FXML
	TableView<Customers> customerEnquiryTable;

	ObservableList<Customers> customers;

	ObservableList<String> tripMonthList = FXCollections.observableArrayList("JAN", "FEB", "MAR", "APR", "MAY", "JUN",
			"JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
	ObservableList<String> tourTypeList = FXCollections.observableArrayList("South India Tours",
			"Domestic Tours by Flight", "North India Tours", "Tours Abroad", "Customized Trip");
	ObservableList<String> yearMonthList = FXCollections.observableArrayList("2016", "2017", "2018", "2019", "2020");

	ObservableList<String> tripCodeList = FXCollections.observableArrayList();

	@FXML
	private ComboBox<String> tourTypeBox;

	@FXML
	private ComboBox<String> tripCodeBox;

	@FXML
	private Button custphoneDeleteButton;

	@FXML
	private Button custMonthDeleteButton;

	@FXML
	private Button custFollowUpDeleteButton;

	@FXML
	private Button custEnquiryDeleteButton;

	@FXML
	private Label userLabel;

	@FXML
	private TextField tour;

	@FXML
	private TextArea descriptionArea;

	@FXML
	private void initialize() {

		tourTypeBox.setValue("Select Tour Type");
		tripCodeBox.setValue("Select Trip Code");
		tripMonthBox.setValue("Select Month");
		tripYearBox.setValue("Select Year");
		tripMonthBox.setItems(tripMonthList);
		tourTypeBox.setItems(tourTypeList);
		tripYearBox.setItems(yearMonthList);
		if (Users.getRole().equalsIgnoreCase("EMPLOYEE")) {
			custMonthDeleteButton.setVisible(false);
			custphoneDeleteButton.setVisible(false);
			custFollowUpDeleteButton.setVisible(false);
			custEnquiryDeleteButton.setVisible(false);
		} else {
			custMonthDeleteButton.setVisible(true);
			custphoneDeleteButton.setVisible(true);
			custFollowUpDeleteButton.setVisible(true);
			custEnquiryDeleteButton.setVisible(true);
		}
		userLabel.setText(Users.getUserName());

	}

	@FXML
	TableColumn nameColMonth;
	@FXML
	TableColumn emailColMonth;
	@FXML
	TableColumn contactColMonth;
	@FXML
	TableColumn tourTypeColMonth;
	@FXML
	TableColumn tourColMonth;
	@FXML
	TableColumn journeyDateColMonth;
	@FXML
	TableColumn noOfDaysColMonth;
	@FXML
	TableColumn tripCodeColMonth;
	@FXML
	TableColumn noOfPersonsColMonth;
	@FXML
	TableColumn citiesColMonth;
	@FXML
	TableColumn addressColMonth;
	@FXML
	TableColumn remarksColMonth;
	@FXML
	TableColumn followUpDateColMonth;
	@FXML
	TableColumn creationDateColMonth;

	// Follow Up Date Table
	@FXML
	TableColumn nameColFollowUp;
	@FXML
	TableColumn emailColFollowUp;
	@FXML
	TableColumn contactColFollowUp;
	@FXML
	TableColumn tourTypeColFollowUp;
	@FXML
	TableColumn tourColFollowUp;
	@FXML
	TableColumn journeyDateColFollowUp;
	@FXML
	TableColumn noOfDaysColFollowUp;
	@FXML
	TableColumn tripCodeColFollowUp;
	@FXML
	TableColumn noOfPersonsColFollowUp;
	@FXML
	TableColumn citiesColFollowUp;
	@FXML
	TableColumn addressColFollowUp;
	@FXML
	TableColumn remarksColFollowUp;
	@FXML
	TableColumn followUpDateColFollowUp;
	@FXML
	TableColumn creationDateColFollowUp;

	// Enquiry Date Table
	@FXML
	TableColumn nameColEnquiry;
	@FXML
	TableColumn emailColEnquiry;
	@FXML
	TableColumn contactColEnquiry;
	@FXML
	TableColumn tourTypeColEnquiry;
	@FXML
	TableColumn tourColEnquiry;
	@FXML
	TableColumn journeyDateColEnquiry;
	@FXML
	TableColumn noOfDaysColEnquiry;
	@FXML
	TableColumn tripCodeColEnquiry;
	@FXML
	TableColumn noOfPersonsColEnquiry;
	@FXML
	TableColumn citiesColEnquiry;
	@FXML
	TableColumn addressColEnquiry;
	@FXML
	TableColumn remarksColEnquiry;
	@FXML
	TableColumn followUpDateColEnquiry;
	@FXML
	TableColumn creationDateColEnquiry;

	@FXML
	TableColumn nameColPhone;
	@FXML
	TableColumn emailColPhone;
	@FXML
	TableColumn contactColPhone;
	@FXML
	TableColumn tourTypeColPhone;
	@FXML
	TableColumn tourColPhone;
	@FXML
	TableColumn journeyDateColPhone;
	@FXML
	TableColumn noOfDaysColPhone;
	@FXML
	TableColumn tripCodeColPhone;
	@FXML
	TableColumn noOfPersonsColPhone;
	@FXML
	TableColumn citiesColPhone;
	@FXML
	TableColumn addressColPhone;
	@FXML
	TableColumn remarksColPhone;
	@FXML
	TableColumn followUpDateColPhone;
	@FXML
	TableColumn creationDateColPhone;

	@FXML
	Button monthSearch;
	@FXML
	Button monthReset;

	@FXML
	Button tripSearch;

	@FXML
	Button phoneSearch;

	@FXML
	Button exportToExcel;

	@FXML
	Label monthCount;

	@FXML
	Label followUpCount;

	@FXML
	Label enquiryCount;

	@FXML
	Label monthCountBooking;

	@FXML
	Label tripCount;

	@FXML
	Label phoneCount;

	@FXML
	TextField tripCode;

	@FXML
	TextField phoneNumber;

	@FXML
	DatePicker followUpDate;

	@FXML
	DatePicker enquiryDate;

	@FXML
	private Main main = new Main();

	@FXML
	public ObservableList<Customers> searchByMonth() throws IOException, ClassNotFoundException {

		nameColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("name"));
		addressColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("address"));
		emailColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("email"));
		contactColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("contactNo"));
		tourTypeColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("tourType"));
		tripCodeColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("tripCode"));
		journeyDateColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("journeyDate"));
		noOfDaysColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfDays"));
		citiesColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("cities"));
		noOfPersonsColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfPersons"));
		remarksColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("remarks"));
		followUpDateColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("followUpDate"));
		creationDateColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("createdDate"));

		customers = FXCollections.observableArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Util util = new Util();
			con = util.openConnection();
			String retriveCust = null;

			// only for month

			if ("Select Tour Type".equalsIgnoreCase(tourTypeBox.getValue().toString())
					&& (tripCodeBox.getValue() == null
							|| tripCodeBox.getValue().equalsIgnoreCase("Select Trip Code"))) {
				retriveCust = "select * from customers where journey_date like ? ";
			}
			// month + tourtype

			if (!"Select Tour Type".equalsIgnoreCase(tourTypeBox.getValue().toString())
					&& (tripCodeBox.getValue() == null || tripCodeBox.getValue().equalsIgnoreCase(
							"Select Trip Code"))/*
												 * || "Customized Trip"
												 * .equalsIgnoreCase(tourTypeBox
												 * .getValue().toString())
												 */) {
				retriveCust = "select * from customers where journey_date like ? and tour_type= ? ";
			}

			if (!"Select Tour Type".equalsIgnoreCase(tourTypeBox.getValue().toString())
					&& (!tripCodeBox.getValue().equalsIgnoreCase(
							"Select Trip Code"))/*
												 * && !"Customized Trip"
												 * .equalsIgnoreCase(tourTypeBox
												 * .getValue().toString())
												 */) {
				retriveCust = "select * from customers where journey_date like ? and tour_type= ? and trip_code=?";
			} /*
				 * else if(!"Select Tour Type"
				 * .equalsIgnoreCase(tourTypeBox.getValue().toString()) &&
				 * tripCodeBox.getValue()!=null ||
				 * !tripCodeBox.getValue().equalsIgnoreCase("Select Trip Code"))
				 * retriveCust =
				 * "select * from customers where journey_date like ? and tour_type= ?"
				 * ;
				 */
			pstmt = con.prepareStatement(retriveCust);
			if ("Select Month".equalsIgnoreCase(tripMonthBox.getValue().toString())) {
				showValidationAlert("Select the Month!!");
				tripMonthBox.requestFocus();
				return null;
			} else if (tripYearBox.getValue().trim().isEmpty()) {
				showValidationAlert("Year of Booking is Missing!!");
				tripYearBox.requestFocus();
				return null;
			} else if (!tripYearBox.getValue().trim().matches("\\d{4}")) {
				showValidationAlert("Enter Valid Year of Booking");
				tripYearBox.requestFocus();
				return null;

			} /*
				 * else if ("Select Tour Type"
				 * .equalsIgnoreCase(tourTypeBox.getValue().toString())) {
				 * showValidationAlert("Select the Tour Type!!!");
				 * tourTypeBox.requestFocus(); return null; }
				 * 
				 * if(!"Customized Trip"
				 * .equalsIgnoreCase(tourTypeBox.getValue().toString())){
				 * if(null == tripCodeList || tripCodeList.isEmpty()){
				 * showValidationAlert("Select Trip Code!!!"); return null; }
				 * else if(tripCodeBox.getValue()==null ||
				 * tripCodeBox.getValue().equalsIgnoreCase("Select Trip Code")){
				 * showValidationAlert("Select Trip Code!!!");
				 * tripCodeBox.requestFocus(); return null; } }
				 */
			// only for month

			if ("Select Tour Type".equalsIgnoreCase(tourTypeBox.getValue().toString())
					&& (tripCodeBox.getValue() == null
							|| tripCodeBox.getValue().equalsIgnoreCase("Select Trip Code"))) {
				pstmt.setString(1,
						"%" + tripMonthBox.getValue().toString().toUpperCase() + "-" + tripYearBox.getValue());
			}
			// month + tourtype

			if (!"Select Tour Type".equalsIgnoreCase(tourTypeBox.getValue().toString())
					&& (tripCodeBox.getValue() == null
							|| tripCodeBox.getValue().equalsIgnoreCase("Select Trip Code"))) {
				pstmt.setString(1,
						"%" + tripMonthBox.getValue().toString().toUpperCase() + "-" + tripYearBox.getValue());
				pstmt.setString(2, tourTypeBox.getValue().toString());
			}

			if (!"Select Tour Type".equalsIgnoreCase(tourTypeBox.getValue().toString())
					&& (!tripCodeBox.getValue().equalsIgnoreCase(
							"Select Trip Code")) /*
													 * !"Customized Trip"
													 * .equalsIgnoreCase(
													 * tourTypeBox.getValue().
													 * toString())
													 */) {
				pstmt.setString(1,
						"%" + tripMonthBox.getValue().toString().toUpperCase() + "-" + tripYearBox.getValue());
				pstmt.setString(2, tourTypeBox.getValue().toString());
				pstmt.setString(3, tripCodeBox.getValue().toString());
			} /*
				 * else if(!"Select Tour Type"
				 * .equalsIgnoreCase(tourTypeBox.getValue().toString()) &&
				 * tripCodeBox.getValue()!=null ||
				 * !tripCodeBox.getValue().equalsIgnoreCase("Select Trip Code"
				 * )&& "Customized Trip"
				 * .equalsIgnoreCase(tourTypeBox.getValue().toString()))
				 * retriveCust =
				 * "select * from customers where journey_date like ? and tour_type= ?"
				 * ;
				 * 
				 * if(!"Customized Trip"
				 * .equalsIgnoreCase(tourTypeBox.getValue().toString())){
				 * pstmt.setString(3, tripCodeBox.getValue().toString()); }
				 */

			ResultSet rs = pstmt.executeQuery();
			int count = 1;
			int noOfBooinkg = 0;
			while (rs.next()) {
				Customers c = new Customers(rs.getString("full_name"), rs.getString("email"),
						rs.getString("phone_number"), rs.getString("cities"), rs.getString("address"),
						rs.getString("journey_date"), rs.getString("customer_pk"), rs.getString("no_of_days"),
						rs.getString("trip_code"), rs.getString("tour_type"), rs.getString("no_of_person"),
						rs.getString("remarks"), rs.getString("follow_up_date"), rs.getString("created_date"));
				customers.add(c);
				count++;
				noOfBooinkg = noOfBooinkg + Integer.valueOf(rs.getString("no_of_person"));
			}

			if (count == 1) {
				showValidationAlert("No Records Found!!");
				customerMonthTable.setItems(null);
				monthCount.setText(String.valueOf(0));
				tripMonthBox.setValue("Select Month");
				tripYearBox.setValue("Select Year");
				tourTypeBox.setValue("Select Tour Type");
				tripCodeBox.setValue("Select Trip Code");
				return null;
			}
			customerMonthTable.setItems(customers);
			monthCount.setText(String.valueOf(customers.size()));
			monthCountBooking.setText(String.valueOf(noOfBooinkg));
			return customers;
		} catch (SQLException e) {
                        Main.le.error("Search By Month Error : " , e);
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
		return customers;
	}// End of main

	@FXML
	public ObservableList<Customers> searchByPhoneNumber() throws IOException, ClassNotFoundException {

		nameColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("name"));
		emailColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("email"));
		contactColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("contactNo"));
		journeyDateColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("journeyDate"));
		noOfDaysColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfDays"));
		citiesColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("cities"));
		tripCodeColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("tripCode"));
		tourTypeColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("tourType"));
		noOfPersonsColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfPersons"));
		addressColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("address"));
		remarksColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("remarks"));
		followUpDateColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("followUpDate"));
		creationDateColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("createdDate"));

		ObservableList<Customers> customers = FXCollections.observableArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Util util = new Util();
			con = util.openConnection();
			String retriveCust = "select * from customers where phone_number like ? ";
			if ("".equalsIgnoreCase(phoneNumber.getText())) {
				showValidationAlert("Phone Number is required!!");
				phoneNumber.requestFocus();
				return null;
			} else if (!phoneNumber.getText().matches("\\d{10}")) {
				showValidationAlert("Enter Valid Contact Number");
				phoneNumber.requestFocus();
				return null;
			}
			pstmt = con.prepareStatement(retriveCust);
			pstmt.setString(1, "%" + phoneNumber.getText() + "%");
			// pstmt.setString(2, "2016");
			ResultSet rs = pstmt.executeQuery();
			int count = 1;
			while (rs.next()) {
				Customers c = new Customers(rs.getString("full_name"), rs.getString("email"),
						rs.getString("phone_number"), rs.getString("cities"), rs.getString("address"),
						rs.getString("journey_date"), rs.getString("customer_pk"), rs.getString("no_of_days"),
						rs.getString("trip_code"), rs.getString("tour_type"), rs.getString("no_of_person"),
						rs.getString("remarks"), rs.getString("follow_up_date"), rs.getString("created_date"));
				customers.add(c);
				count++;
			}
			if (count == 1) {
				showValidationAlert("No Records Found!!");
				customerPhoneNumberTable.setItems(null);
				phoneCount.setText(String.valueOf(0));
				return null;

			}
			customerPhoneNumberTable.setItems(customers);
			phoneCount.setText(String.valueOf(customers.size()));
			return customers;
		} catch (SQLException e) {
                         Main.le.error("Search By Phone Error : " , e);
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
		return customers;
	}// End of main

	@FXML
	public ObservableList<Customers> searchForFollowUpDate() throws IOException, ClassNotFoundException {

		nameColFollowUp.setCellValueFactory(new PropertyValueFactory<Customers, String>("name"));
		emailColFollowUp.setCellValueFactory(new PropertyValueFactory<Customers, String>("email"));
		contactColFollowUp.setCellValueFactory(new PropertyValueFactory<Customers, String>("contactNo"));
		journeyDateColFollowUp.setCellValueFactory(new PropertyValueFactory<Customers, String>("journeyDate"));
		noOfDaysColFollowUp.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfDays"));
		citiesColFollowUp.setCellValueFactory(new PropertyValueFactory<Customers, String>("cities"));
		tripCodeColFollowUp.setCellValueFactory(new PropertyValueFactory<Customers, String>("tripCode"));
		tourTypeColFollowUp.setCellValueFactory(new PropertyValueFactory<Customers, String>("tourType"));
		noOfPersonsColFollowUp.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfPersons"));
		addressColFollowUp.setCellValueFactory(new PropertyValueFactory<Customers, String>("address"));
		remarksColFollowUp.setCellValueFactory(new PropertyValueFactory<Customers, String>("remarks"));
		followUpDateColFollowUp.setCellValueFactory(new PropertyValueFactory<Customers, String>("followUpDate"));
		creationDateColFollowUp.setCellValueFactory(new PropertyValueFactory<Customers, String>("createdDate"));

		ObservableList<Customers> customers = FXCollections.observableArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Util util = new Util();
			con = util.openConnection();
			String retriveCust = "select * from customers where follow_up_date = ? ";
			if (null == followUpDate.getValue()) {
				showValidationAlert("Follow Up Date is required!!");
				return null;
			}
			pstmt = con.prepareStatement(retriveCust);
			LocalDate date = followUpDate.getValue();
			Instant instant = Instant.from(date.atStartOfDay(ZoneId.systemDefault()));
			Date fDate = Date.from(instant);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			String followUpDateString = sdf.format(fDate);
			pstmt.setString(1, followUpDateString);

			ResultSet rs = pstmt.executeQuery();
			int count = 1;
			while (rs.next()) {
				Customers c = new Customers(rs.getString("full_name"), rs.getString("email"),
						rs.getString("phone_number"), rs.getString("cities"), rs.getString("address"),
						rs.getString("journey_date"), rs.getString("customer_pk"), rs.getString("no_of_days"),
						rs.getString("trip_code"), rs.getString("tour_type"), rs.getString("no_of_person"),
						rs.getString("remarks"), rs.getString("follow_up_date"), rs.getString("created_date"));
				customers.add(c);
				count++;
			}
			if (count == 1) {
				showValidationAlert("No Records Found!!");
				customerFollowUpTable.setItems(null);
				followUpCount.setText(String.valueOf(0));
				return null;

			}
			customerFollowUpTable.setItems(customers);
			followUpCount.setText(String.valueOf(customers.size()));
			return customers;
		} catch (SQLException e) {
                         Main.le.error("Search By Follow Up Date Error : " , e);
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
		return customers;
	}// End of main

	@FXML
	public ObservableList<Customers> searchForEnquiryDate() throws IOException, ClassNotFoundException {

		nameColEnquiry.setCellValueFactory(new PropertyValueFactory<Customers, String>("name"));
		emailColEnquiry.setCellValueFactory(new PropertyValueFactory<Customers, String>("email"));
		contactColEnquiry.setCellValueFactory(new PropertyValueFactory<Customers, String>("contactNo"));
		journeyDateColEnquiry.setCellValueFactory(new PropertyValueFactory<Customers, String>("journeyDate"));
		noOfDaysColEnquiry.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfDays"));
		citiesColEnquiry.setCellValueFactory(new PropertyValueFactory<Customers, String>("cities"));
		tripCodeColEnquiry.setCellValueFactory(new PropertyValueFactory<Customers, String>("tripCode"));
		tourTypeColEnquiry.setCellValueFactory(new PropertyValueFactory<Customers, String>("tourType"));
		noOfPersonsColEnquiry.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfPersons"));
		addressColEnquiry.setCellValueFactory(new PropertyValueFactory<Customers, String>("address"));
		remarksColEnquiry.setCellValueFactory(new PropertyValueFactory<Customers, String>("remarks"));
		followUpDateColEnquiry.setCellValueFactory(new PropertyValueFactory<Customers, String>("followUpDate"));
		creationDateColEnquiry.setCellValueFactory(new PropertyValueFactory<Customers, String>("createdDate"));

		ObservableList<Customers> customers = FXCollections.observableArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Util util = new Util();
			con = util.openConnection();
			String retriveCust = "select * from customers where created_date = ? ";
			if (null == enquiryDate.getValue()) {
				showValidationAlert("Enquiry Date is required!!");
				return null;
			}
			pstmt = con.prepareStatement(retriveCust);
			LocalDate date = enquiryDate.getValue();
			Instant instant = Instant.from(date.atStartOfDay(ZoneId.systemDefault()));
			Date eDate = Date.from(instant);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			String enquiryDateString = sdf.format(eDate);
			pstmt.setString(1, enquiryDateString);
			ResultSet rs = pstmt.executeQuery();
			int count = 1;
			while (rs.next()) {
				Customers c = new Customers(rs.getString("full_name"), rs.getString("email"),
						rs.getString("phone_number"), rs.getString("cities"), rs.getString("address"),
						rs.getString("journey_date"), rs.getString("customer_pk"), rs.getString("no_of_days"),
						rs.getString("trip_code"), rs.getString("tour_type"), rs.getString("no_of_person"),
						rs.getString("remarks"), rs.getString("follow_up_date"), rs.getString("created_date"));
				customers.add(c);
				count++;
			}
			if (count == 1) {
				showValidationAlert("No Records Found!!");
				customerEnquiryTable.setItems(null);
				enquiryCount.setText(String.valueOf(0));
				return null;

			}
			customerEnquiryTable.setItems(customers);
			enquiryCount.setText(String.valueOf(customers.size()));
			return customers;
		} catch (SQLException e) {
                         Main.le.error("Search By Enquiry Date Error : " , e);
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
		return customers;
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

		nameColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("name"));
		emailColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("email"));
		contactColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("contactNo"));
		journeyDateColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("journeyDate"));
		noOfDaysColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfDays"));
		citiesColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("cities"));
		tripCodeColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("tripCode"));
		tourTypeColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("tourType"));
		noOfPersonsColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfPersons"));
		addressColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("address"));
		remarksColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("remarks"));
		followUpDateColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("followUpDate"));
		creationDateColMonth.setCellValueFactory(new PropertyValueFactory<Customers, String>("createdDate"));

		ObservableList<Customers> customers = FXCollections.observableArrayList();
		Customers cust = customerMonthTable.getSelectionModel().getSelectedItem();
		if (null == cust) {
			showValidationAlert("Select the Record first!!");
			monthSearch.requestFocus();
			return null;
		}

		String pk = cust.getCustomer_pk();
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Util util = new Util();
			con = util.openConnection();
			String retriveCust = "delete from customers where customer_pk = ? "; // and
																					// year
																					// =
																					// ?";
			if ("Select Month".equalsIgnoreCase(tripMonthBox.getValue().toString())) {
				showValidationAlert("Select the Month!!");
				tripMonthBox.requestFocus();
				return null;
			} else if (tripYearBox.getValue().trim().isEmpty()) {
				showValidationAlert("Year of Booking is Missing!!");
				tripYearBox.requestFocus();
				return null;
			} else if (!tripYearBox.getValue().trim().matches("\\d{4}")) {
				showValidationAlert("Enter Valid Year of Booking");
				tripYearBox.requestFocus();
				return null;

			}
			pstmt = con.prepareStatement(retriveCust);
			pstmt.setString(1, pk);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Are you sure , Want to DELETE!!");
			alert.setHeaderText("Look, a record is being deleted!");
			alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				int cou = pstmt.executeUpdate();
                                if(0!=cou){
                                    Main.ls.info("Customer deleted successfully :  " + customerMonthTable.getSelectionModel().getSelectedItem().getName());
                                }
                                
			} else {
				return "false";
			}
			searchByMonth();
			return "true";
                         
		} catch (SQLException e) {
                        Main.le.error("Error Deleting Customer :  " + customerMonthTable.getSelectionModel().getSelectedItem().getName() , e);
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
	private String deleteRegPhoneDetails() throws IOException, ClassNotFoundException {

		nameColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("name"));
		emailColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("email"));
		contactColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("contactNo"));
		journeyDateColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("journeyDate"));
		noOfDaysColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfDays"));
		citiesColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("cities"));
		tripCodeColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("tripCode"));
		tourTypeColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("tourType"));
		noOfPersonsColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfPersons"));
		addressColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("address"));
		remarksColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("remarks"));
		followUpDateColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("followUpDate"));
		creationDateColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("createdDate"));

		ObservableList<Customers> customers = FXCollections.observableArrayList();
		Customers cust = customerPhoneNumberTable.getSelectionModel().getSelectedItem();
		if (null == cust) {
			showValidationAlert("Select the Record first!!");
			phoneSearch.requestFocus();
			return null;
		}

		String pk = cust.getCustomer_pk();
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Util util = new Util();
			con = util.openConnection();
			String retriveCust = "delete from customers where customer_pk = ? "; // and
																					// year
																					// =
																					// ?";
			if ("".equalsIgnoreCase(phoneNumber.getText())) {
				showValidationAlert("Phone Number is required!!");
				phoneNumber.requestFocus();
				return null;
			} else if (!phoneNumber.getText().matches("\\d{10}")) {
				showValidationAlert("Enter Valid Contact Number");
				phoneNumber.requestFocus();
				return null;
			}
			pstmt = con.prepareStatement(retriveCust);
			pstmt.setString(1, pk);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Are you sure , Want to DELETE!!");
			alert.setHeaderText("Look, a record is being deleted!");
			alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				int cou = pstmt.executeUpdate();
                                if(0!=cou){
                                    Main.ls.info("Customer deleted successfully :  " + customerPhoneNumberTable.getSelectionModel().getSelectedItem().getName());
                                }
			} else {
				return "false";
			}
			searchByPhoneNumber();
			return "true";
		} catch (SQLException e) {
                        Main.le.error("Error Deleting Customer :  " + customerPhoneNumberTable.getSelectionModel().getSelectedItem().getName() , e);
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
	private void editTripMonthDetails() throws Exception {
		Customers cust = customerMonthTable.getSelectionModel().getSelectedItem();
		editFlag = true;
		if (null == cust) {
			showValidationAlert("Select the Record first!!");
			monthSearch.requestFocus();
			return;
		}
		Customers monthCust = customerMonthTable.getSelectionModel().getSelectedItem();

		if (monthCust != null) {
			main.ShowEditCustomers(monthCust);

		}

	}

	@FXML
	private void editTripPhoneDetails() throws Exception {
		editFlag = true;
		Customers cust = customerPhoneNumberTable.getSelectionModel().getSelectedItem();
		if (null == cust) {
			showValidationAlert("Select the Record first!!");
			phoneSearch.requestFocus();
			return;
		}
		Customers phonCcust = customerPhoneNumberTable.getSelectionModel().getSelectedItem();

		if (phonCcust != null) {
			main.ShowEditCustomers(phonCcust);

		}

	}

	@FXML
	private void writeToExcelMonth() throws FileNotFoundException, IOException {

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet spreadsheet = workbook.createSheet("sample");

			HSSFRow row = null;
			HSSFCell cell = null;

			row = spreadsheet.createRow(0);
			cell = row.createCell(0);
			cell.setCellValue("SL NO");
			cell = row.createCell(1);
			cell.setCellValue("Full Name");
			cell = row.createCell(2);
			cell.setCellValue("Address");
			cell = row.createCell(3);
			cell.setCellValue("Email Id");
			cell = row.createCell(4);
			cell.setCellValue("Contact Number");
			cell = row.createCell(5);
			cell.setCellValue("Tour Type");
			cell = row.createCell(6);
			cell.setCellValue("Trip Code");
			cell = row.createCell(7);
			cell.setCellValue("Journey Date");
			cell = row.createCell(8);
			cell.setCellValue("No of Days");
			cell = row.createCell(9);
			cell.setCellValue("Cities");
			cell = row.createCell(10);
			cell.setCellValue("No of Persons");
			cell = row.createCell(11);
			cell.setCellValue("Remarks");
			cell = row.createCell(12);
			cell.setCellValue("FollowUp Date");
			cell = row.createCell(13);
			cell.setCellValue("Creation Date");
			if (customerMonthTable.getItems().size() == 0) {
				showValidationAlert("No Record found to export!!");
				monthSearch.requestFocus();
				return;
			}
			for (int i = 0; i < customerMonthTable.getItems().size(); i++) {
				row = spreadsheet.createRow(i + 1);
				row.createCell(0).setCellValue(i + 1);
				for (int j = 0; j < customerMonthTable.getColumns().size(); j++) {
					System.out
							.println("Cell Value " + customerMonthTable.getColumns().get(j).getCellData(i).toString());
					row.createCell(j + 1)
							.setCellValue(customerMonthTable.getColumns().get(j).getCellData(i).toString());
				}
			}

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
			Date date = new Date();
			String file_date = dateFormat.format(date);

			FileOutputStream out = new FileOutputStream(
					new File(Util.getFilePathTripWise()+ "CustomerInfo_" + tourTypeBox.getValue().toString() + "_"
							+ tripCodeBox.getValue().toString() + "_" + file_date + ".csv"));
			workbook.write(out);
			showValidationAlert("Records Stored in path!! -" + Util.getFilePathTripWise()+out.getFD().toString());
                        Main.ls.info("Exporting TripWise Successfull :  " + Util.getFilePathTripWise()+ "CustomerInfo_" + tourTypeBox.getValue().toString() + "_"
							+ tripCodeBox.getValue().toString() + "_" + file_date + ".csv");
                        

			out.close();
		} catch (Exception e) {
                        Main.le.error("Error Exporting TripWise :  " +  e);
			e.printStackTrace();
		}

	}

	@FXML
	private void writeToExcelFollowUpDate() throws FileNotFoundException, IOException {

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet spreadsheet = workbook.createSheet("sample");

			HSSFRow row = null;
			HSSFCell cell = null;

			row = spreadsheet.createRow(0);
			cell = row.createCell(0);
			cell.setCellValue("SL NO");
			cell = row.createCell(1);
			cell.setCellValue("Full Name");
			cell = row.createCell(2);
			cell.setCellValue("Address");
			cell = row.createCell(3);
			cell.setCellValue("Email Id");
			cell = row.createCell(4);
			cell.setCellValue("Contact Number");
			cell = row.createCell(5);
			cell.setCellValue("Tour Type");
			cell = row.createCell(6);
			cell.setCellValue("Trip Code");
			cell = row.createCell(7);
			cell.setCellValue("Journey Date");
			cell = row.createCell(8);
			cell.setCellValue("No of Days");
			cell = row.createCell(9);
			cell.setCellValue("Cities");
			cell = row.createCell(10);
			cell.setCellValue("No of Persons");
			cell = row.createCell(11);
			cell.setCellValue("Remarks");
			cell = row.createCell(12);
			cell.setCellValue("FollowUp Date");
			cell = row.createCell(13);
			cell.setCellValue("Creation Date");
			if (customerFollowUpTable.getItems().size() == 0) {
				showValidationAlert("No Record found to export!!");
				return;
			}
			for (int i = 0; i < customerFollowUpTable.getItems().size(); i++) {
				row = spreadsheet.createRow(i + 1);
				row.createCell(0).setCellValue(i + 1);
				for (int j = 0; j < customerFollowUpTable.getColumns().size(); j++) {
					System.out.println(
							"Cell Value " + customerFollowUpTable.getColumns().get(j).getCellData(i).toString());
					row.createCell(j + 1)
							.setCellValue(customerFollowUpTable.getColumns().get(j).getCellData(i).toString());
				}
			}

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
			Date date = new Date();
			String file_date = dateFormat.format(date);

			FileOutputStream out = new FileOutputStream(
					new File(Util.getFilePathFollowUpDateWise()+ "CustomerInfo_FollowUp_" + customerFollowUpTable.getColumns().get(11).getCellData(0).toString() + "____"+file_date +".csv"));
			workbook.write(out);
			showValidationAlert("Records Stored in path!! -" + Util.getFilePathTripWise());
                        Main.ls.info("Exporting FollowUp Datewise Successfull :  " + Util.getFilePathFollowUpDateWise()+ "CustomerInfo_FollowUp_" + customerFollowUpTable.getColumns().get(11).getCellData(0).toString() + "____"+file_date +".csv");

			out.close();
		} catch (Exception e) {
                        Main.le.error("Error Exporting FollowUp Datewise :  " +  e);
			e.printStackTrace();
		}

	}

	@FXML
	private void writeToExcelEnquiryDate() throws FileNotFoundException, IOException {

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet spreadsheet = workbook.createSheet("sample");

			HSSFRow row = null;
			HSSFCell cell = null;

			row = spreadsheet.createRow(0);
			cell = row.createCell(0);
			cell.setCellValue("SL NO");
			cell = row.createCell(1);
			cell.setCellValue("Full Name");
			cell = row.createCell(2);
			cell.setCellValue("Address");
			cell = row.createCell(3);
			cell.setCellValue("Email Id");
			cell = row.createCell(4);
			cell.setCellValue("Contact Number");
			cell = row.createCell(5);
			cell.setCellValue("Tour Type");
			cell = row.createCell(6);
			cell.setCellValue("Trip Code");
			cell = row.createCell(7);
			cell.setCellValue("Journey Date");
			cell = row.createCell(8);
			cell.setCellValue("No of Days");
			cell = row.createCell(9);
			cell.setCellValue("Cities");
			cell = row.createCell(10);
			cell.setCellValue("No of Persons");
			cell = row.createCell(11);
			cell.setCellValue("Remarks");
			cell = row.createCell(12);
			cell.setCellValue("FollowUp Date");
			cell = row.createCell(13);
			cell.setCellValue("Creation Date");
			if (customerEnquiryTable.getItems().size() == 0) {
				showValidationAlert("No Record found to export!!");
				return;
			}
			for (int i = 0; i < customerEnquiryTable.getItems().size(); i++) {
				row = spreadsheet.createRow(i + 1);
				row.createCell(0).setCellValue(i + 1);
				for (int j = 0; j < customerEnquiryTable.getColumns().size(); j++) {
					System.out.println(
							"Cell Value " + customerEnquiryTable.getColumns().get(j).getCellData(i).toString());
					row.createCell(j + 1)
							.setCellValue(customerEnquiryTable.getColumns().get(j).getCellData(i).toString());
				}
			}

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
			Date date = new Date();
			String file_date = dateFormat.format(date);

			FileOutputStream out = new FileOutputStream(
					new File(Util.getFilePathCreationDateWise()+ "CustomerInfo_Enquiry_" + customerEnquiryTable.getColumns().get(12).getCellData(0).toString() + "____"+file_date+ ".csv"));
			workbook.write(out);
			showValidationAlert("Records Stored in path!! -" + Util.getFilePathCreationDateWise());
                        Main.ls.info("Exporting Enquiry Datewise Successfull :  " + Util.getFilePathCreationDateWise()+ "CustomerInfo_Enquiry_" + customerEnquiryTable.getColumns().get(12).getCellData(0).toString() + "____"+file_date+ ".csv");

			out.close();
		} catch (Exception e) {
                        Main.le.error("Error Exporting Enquiry Datewise :  " +  e);
			e.printStackTrace();
		}

	}

	public void onChangeTourTypeBox() throws Exception {
		if ("Customized Trip".equalsIgnoreCase(tourTypeBox.getValue().toString())) {
			// showValidationAlert("Enter cities included in Trip !!");
			// tour.setEditable(true);
			// tour.requestFocus();
			// tripCodeList.clear();
			tripCodeList.add("CUST");
			// descriptionArea.setVisible(false);
			tripCodeBox.setItems(tripCodeList);
			tripCodeBox.setVisible(false);
			return;
		} else {
			tripCodeBox.setVisible(true);
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
				while (rs.next()) {
					tripCodeListquery.add(rs.getString("trip_code"));
				}
				tripCodeList.addAll(tripCodeListquery);
				tripCodeBox.setItems(tripCodeList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// tour.setEditable(false);
			tripCodeBox.requestFocus();
			return;
		}
	}

	public void onChangeTripCode() throws Exception {
		Connection con = null;
		String retriveCust = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Util util = new Util();
			con = util.openConnection();
			retriveCust = "select * from packages where trip_code = ? ";
			pstmt = con.prepareStatement(retriveCust);
			if (tripCodeBox.getValue() != null) {
				pstmt.setString(1, tripCodeBox.getValue().toString());
				rs = pstmt.executeQuery();

			}

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
	private void resetAction() throws IOException {
		tourTypeBox.setValue("Select Tour Type");
		tripCodeBox.setValue("Select Trip Code");
		tripMonthBox.setValue("Select Month");
		tripYearBox.setValue("Select Year");

	}

	@FXML
	private String deleteFollowUpDetails() throws IOException, ClassNotFoundException {

		nameColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("name"));
		emailColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("email"));
		contactColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("contactNo"));
		journeyDateColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("journeyDate"));
		noOfDaysColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfDays"));
		citiesColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("cities"));
		tripCodeColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("tripCode"));
		tourTypeColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("tourType"));
		noOfPersonsColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfPersons"));
		addressColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("address"));
		remarksColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("remarks"));
		followUpDateColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("followUpDate"));
		creationDateColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("createdDate"));

		ObservableList<Customers> customers = FXCollections.observableArrayList();
		Customers cust = customerFollowUpTable.getSelectionModel().getSelectedItem();
		if (null == cust) {
			showValidationAlert("Select the Record first!!");
			return null;
		}

		String pk = cust.getCustomer_pk();
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Util util = new Util();
			con = util.openConnection();
			String retriveCust = "delete from customers where customer_pk = ? "; // and
																					// year
																					// =
																					// ?";
			if (null == followUpDate.getValue()) {
				showValidationAlert("Follow Up Date is required!!");
				return null;
			}
			pstmt = con.prepareStatement(retriveCust);
			pstmt.setString(1, pk);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Are you sure , Want to DELETE!!");
			alert.setHeaderText("Look, a record is being deleted!");
			alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				int cou = pstmt.executeUpdate();
                                if(0!=cou){
                                    Main.ls.info("Customer deleted successfully :  " + customerFollowUpTable.getSelectionModel().getSelectedItem().getName());
                                }
			} else {
				return "false";
			}
			searchForFollowUpDate();
			return "true";
		} catch (SQLException e) {
                        Main.le.error("Error Deleting Customer :  " + customerFollowUpTable.getSelectionModel().getSelectedItem().getName() , e);
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
	private String deleteEnquiryDateDetails() throws IOException, ClassNotFoundException {

		nameColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("name"));
		emailColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("email"));
		contactColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("contactNo"));
		journeyDateColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("journeyDate"));
		noOfDaysColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfDays"));
		citiesColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("cities"));
		tripCodeColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("tripCode"));
		tourTypeColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("tourType"));
		noOfPersonsColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("noOfPersons"));
		addressColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("address"));
		remarksColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("remarks"));
		followUpDateColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("followUpDate"));
		creationDateColPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("createdDate"));

		ObservableList<Customers> customers = FXCollections.observableArrayList();
		Customers cust = customerEnquiryTable.getSelectionModel().getSelectedItem();
		if (null == cust) {
			showValidationAlert("Select the Record first!!");
			return null;
		}

		String pk = cust.getCustomer_pk();
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Util util = new Util();
			con = util.openConnection();
			String retriveCust = "delete from customers where customer_pk = ? "; // and
																					// year
																					// =
																					// ?";

			if (null == enquiryDate.getValue()) {
				showValidationAlert("Enquiry Date is required!!");
				return null;
			}
			pstmt = con.prepareStatement(retriveCust);
			pstmt.setString(1, pk);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Are you sure , Want to DELETE!!");
			alert.setHeaderText("Look, a record is being deleted!");
			alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				int cou = pstmt.executeUpdate();
                                if(0!=cou){
                                    Main.ls.info("Customer deleted successfully :  " + customerEnquiryTable.getSelectionModel().getSelectedItem().getName());
                                }
			} else {
				return "false";
			}
			searchForEnquiryDate();
			return "true";
		} catch (SQLException e) {
                        Main.le.error("Customer Deleting Record :  " + customerEnquiryTable.getSelectionModel().getSelectedItem().getName() , e);
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
	private void editFollowUpDetails() throws Exception {
		editFlag = true;
		Customers cust = customerFollowUpTable.getSelectionModel().getSelectedItem();
		if (null == cust) {
			showValidationAlert("Select the Record first!!");
			return;
		}
		Customers followUpCust = customerFollowUpTable.getSelectionModel().getSelectedItem();

		if (followUpCust != null) {
			main.ShowEditCustomers(followUpCust);

		}

	}

	@FXML
	private void editEnquiryDateDetails() throws Exception {
		editFlag = true;
		Customers cust = customerEnquiryTable.getSelectionModel().getSelectedItem();
		if (null == cust) {
			showValidationAlert("Select the Record first!!");
			return;
		}
		Customers enquiryCust = customerEnquiryTable.getSelectionModel().getSelectedItem();

		if (enquiryCust != null) {
			main.ShowEditCustomers(enquiryCust);

		}

	}
        
        @FXML
	public void enableInternet() {
             try {
            Desktop.getDesktop().browse(new URL("http://www.twisters.tech").toURI());
             Main.ls.info("Connected to URL : Twisters.tech");
            
        } 
        catch (MalformedURLException ex) {
            Main.le.error("Could not connect to URL/Internet");
        } 
        catch (URISyntaxException | IOException ex){
            Main.le.error("Could not connect to URL/Internet");
        }
        }
}
