package com.vaccination;

import java.sql.CallableStatement;
//import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.Scanner;

public class MasterClass {
	Connection con;
	PreparedStatement pstat;
	CallableStatement cstat;
	ResultSet res;
	String sql;

	public void getCentres(int id) {


		try {
			con = ConnectDatabase.getConnection();
			sql = "select * from vaccination_center where city_id=?";
			pstat = con.prepareCall(sql);
			pstat.setInt(1, id);
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

	public void getSlot(int cid, Date d) {
		try {
			con = ConnectDatabase.getConnection();
			sql = "call show_slots(?,?)";
			cstat = con.prepareCall(sql);
			cstat.setDate(1, d);
			cstat.setInt(2, cid);
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

	public void addUser(int sid, Date d, int cid) throws SQLException {
		System.out.println("Register YourSelf ");
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter First Name");
		String fname = sc.next();
		sc.nextLine();
		System.out.println("Enter Last Name");
		String lname = sc.next();
		sc.nextLine();
		System.out.println("Enter Aadhar Number");
		String an = sc.next();
		sc.nextLine();
		System.out.println("Enter Date Of Birth");
		Date dob = Date.valueOf(sc.next());
		System.out.println("Enter Mobile Number");
		String mno = sc.next();
		sc.nextLine();
		System.out.println("Enter Email");
		String email = sc.next();
		con = ConnectDatabase.getConnection();
		sql = "insert into user (AadharNo,First_name,Last_Name,DOB,Mobile,Mail)" + " values (?,?,?,?,?,?)";
		pstat = con.prepareStatement(sql);
		pstat.setString(1, an);
		pstat.setString(2, fname);
		pstat.setString(3, lname);
		pstat.setDate(4, dob);
		pstat.setString(5, mno);
		pstat.setString(6, email);
		int res = pstat.executeUpdate();
		System.out.println("Registration Successfull");
		sql = "insert into slot_booking(AadharNo,Booking_Date,Center_id,slot_id)" + " values(?,?,?,?)";
		pstat = con.prepareStatement(sql);
		pstat.setString(1, an);
		pstat.setDate(2, d);
		pstat.setInt(3, cid);
		pstat.setInt(4, sid);
		int res1 = pstat.executeUpdate();
		System.out.println("Booking table inserted");

	}

	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("Welcome to Vaccination Process");
		ShowCity s = new ShowCity();
		s.getCity();
		s.printCity();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Your City Id");
		int cid = sc.nextInt();
		MasterClass obj = new MasterClass();
		obj.getCentres(cid);
		System.out.println("Enter Centre_Id:");
		int ceid = sc.nextInt();
		System.out.println("Enter Date ");
		Date d = Date.valueOf(sc.next());
		obj.getSlot(ceid, d);
		System.out.println("Enter Slot Id");
		int sid = sc.nextInt();
		obj.addUser(sid, d, ceid);

	}

}
