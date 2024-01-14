package com.vaccination;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

public class Vaccination_Process {

	Connection con;
	String sql;
	Statement stat;
	ResultSet res;
	PreparedStatement pstat;
	CallableStatement cstat;
	int cid, ceid, sid, dose;
	Date d;
	String aadharNo;
	Scanner sc = new Scanner(System.in);

	Vaccination_Process() throws SQLException {
		con = ConnectDatabase.getConnection();

	}

	public void showCity() {
		try {
			sql = "select * from city ";
			stat = con.createStatement();
			res = stat.executeQuery(sql);
			System.out.printf("%-8s %-30s %-15s \n", "City_Id", "City", "State");
			System.out.println("__________________________________________________________");
			while (res.next()) {
				System.out.printf("%-8d %-30s %-15s\n", res.getInt(1), res.getString(2), res.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void showCentre() {
		try {
			System.out.println("Enter City ID");
			cid = sc.nextInt();
			sql = "select * from vaccination_center where city_id=?";
			pstat = con.prepareCall(sql);
			pstat.setInt(1, cid);
			res = pstat.executeQuery();
			System.out.printf("%-10s %-30s %-25s %-40s\n", "Centre_Id", "Centre_Name", "Location", "City_Id");
			while (res.next()) {
				System.out.printf("%-10d %-30s %-25s %-40d\n", res.getInt(1), res.getString(2), res.getString(3),
						res.getInt(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void checkHoliday() {

		try {
			System.out.println("Enter Centre Id From Above Table:");
			ceid = sc.nextInt();
			while (true) {
				System.out.println("Enter Date To Get Vaccinated:");
				d = Date.valueOf(sc.next());
				sql = "call checkHoliday(?,?)";
				cstat = con.prepareCall(sql);
				cstat.setDate(1, d);
				cstat.registerOutParameter(2, Types.INTEGER);
				cstat.execute();
				res = cstat.getResultSet();
				int r = cstat.getInt(2);
				// System.out.println(r);
				if (r == 1) {
					System.out.println("Vaccination Centre Closed ON Holidays:");
					System.out.println("Please Enter Another Date");
					continue;
				} else {
					// System.out.println("Date Available:");
					break;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showSlots() {

		System.out.println("Available Slots ");
		try {
			sql = "call show_slots(?,?)";
			cstat = con.prepareCall(sql);
			cstat.setDate(1, d);
			cstat.setInt(2, ceid);
			res = cstat.executeQuery();
			System.out.printf("%-10s  %-10s\n", "Slot id", "Time");
			while (res.next()) {
				System.out.printf("%-10d  %-10s\n", res.getInt(1), res.getTime(2).toString());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void registerUser() {

		System.out.println("Enter Slot Id: 😍😍😍");
		sid = sc.nextInt();
		System.out.println("Welcome To The Registration Process:😀😀😀");
		System.out.println(" Dose : 1/2 😉😉😉");
		dose = sc.nextInt();
		switch (dose) {

		case 1:
			System.out.println("Register YourSelf 😘😘😘");
			System.out.println("Enter Aadhar Number: 😉😉😉");
			aadharNo = sc.next();
			sc.nextLine();
			try {
				sql = "call checkUser(?,?)";
				cstat = con.prepareCall(sql);
				cstat.setString(1, aadharNo);
				cstat.registerOutParameter(2, Types.INTEGER);
				cstat.execute();
				res = cstat.getResultSet();
				int r = cstat.getInt(2);
				// System.out.println(r);
				if (r == 1) {
					System.out.println("You Are Already Registered: ☹️☹️☹️");
					// System.out.println("Select Dose 2 ");
					break;

				} else {
					// System.out.println("New User:");

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Enter First Name: 😉😉😉");
			String fname = sc.next();
			sc.nextLine();
			System.out.println("Enter Last Name: 😉😉😉");
			String lname = sc.next();
			sc.nextLine();
			System.out.println("Enter Date Of Birth 🎂🎂🎂");
			Date dob = Date.valueOf(sc.next());
			System.out.println("Enter Mobile Number 📞📞📞");
			String mno = sc.next();
			sc.nextLine();
			System.out.println("Enter Email ✉️✉️✉️");
			String email = sc.next();
			try {
				sql = "insert into user (AadharNo,First_name,Last_Name,DOB,Mobile,Mail)" + " values (?,?,?,?,?,?)";
				pstat = con.prepareStatement(sql);
				pstat.setString(1, aadharNo);
				pstat.setString(2, fname);
				pstat.setString(3, lname);
				pstat.setDate(4, dob);
				pstat.setString(5, mno);
				pstat.setString(6, email);
				pstat.executeUpdate();
				System.out.println("Registration Successfull 😉😉😉");
				sql = "insert into slot_booking(AadharNo,Booking_Date,Center_id,slot_id)" + " values(?,?,?,?)";
				pstat = con.prepareStatement(sql);
				pstat.setString(1, aadharNo);
				pstat.setDate(2, d);
				pstat.setInt(3, ceid);
				pstat.setInt(4, sid);
				pstat.executeUpdate();
				// System.out.println("Booking table inserted");
				System.out.println("Thank You See You Soon On Centre 😉😉😉");
				System.out.println(" 🙏🙏🙏:");
				break;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 2:
			System.out.println("Enter Aadhar Number: 😀😀😀");
			aadharNo = sc.next();
			sc.nextLine();
			try {
				sql = "insert into slot_booking(AadharNo,Booking_Date,Center_id,slot_id,dose)" + " values(?,?,?,?.?)";
				pstat = con.prepareStatement(sql);
				pstat.setString(1, aadharNo);
				pstat.setDate(2, d);
				pstat.setInt(3, ceid);
				pstat.setInt(4, sid);
				pstat.setInt(5, dose);
				pstat.executeUpdate();
				// System.out.println("Booking table inserted");
				System.out.println("Thank You See You Soon On Centre 😉😉😉");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("You can take only  one vaccine with a gap of 2 months ");
				System.out.println(" or  You can take only  two doses:");
				System.out.println("Thank You 🙏🙏🙏:");
				e.printStackTrace();
				break;
			}
		default:
			System.out.println("Invalid Input:User Can Take Only Two Dose 😠😠😠");
			break;
		}
	}

	public void showUserInfo()

	{
		try {
			sql = "select aadharNo, concat(first_name,\" \",last_name) as Full_Name,booking_date,center_id,city_id,slot_id,time,dose\r\n"
					+ "from user\r\n" + "inner join slot_booking using(aadharNo)\r\n"
					+ "inner join vaccination_center using(center_id)\r\n" + "inner join slot_master using(slot_id)\r\n"
					+ "where aadharNo=? and dose= ?;";
			pstat = con.prepareCall(sql);
			pstat.setString(1, aadharNo);
			pstat.setInt(2, dose);
			res = pstat.executeQuery();
			System.out.println("Here Is Your Booking Detail: 😉😉😉");
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------------");
			System.out.printf("%-15s  %-20s   %-18s   %-15s   %-18s  %-15s  %-15s  %-15s\n", "Aadhar_No", "Full_Name",
					"Booking_Date", "City_id", "Centre_Id", "Slot_id", "Time", "Dose");
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------------------");
			while (res.next()) {
				System.out.printf("%-15s  %-20s  %-18s  %-15s  %-18s   %-15s   %-15s   %-15s\n", res.getLong(1),
						res.getString(2), res.getDate(3), res.getInt(5), res.getInt(4), res.getInt(6), res.getTime(7),
						res.getInt(8));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws SQLException {
		Vaccination_Process obj = new Vaccination_Process();
		System.out.println("Welcome To The Vaccination Process 👋👋👋");
		obj.showCity();
		obj.showCentre();
		obj.checkHoliday();
		obj.showSlots();
		obj.registerUser();
		obj.showUserInfo();
		System.out.println("Thank You 👋👋👋");
	}

}
