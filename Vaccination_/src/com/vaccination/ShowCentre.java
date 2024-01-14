package com.vaccination;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class ShowCentre {
	Statement stat;
	String sql;
	ResultSet res;
	Connection con;
	ArrayList<Vaccination_Centre> centreal = new ArrayList<>();

	public void getCity() {

		try {
			con = ConnectDatabase.getConnection();
			sql = " select * from vaccination_center";
			stat = con.createStatement();
			res = stat.executeQuery(sql);
			while (res.next()) {
				String location = res.getString(3);
				int cid = res.getInt(4);
				String cname = res.getString(2);
				int centreId = res.getInt(1);
				// System.out.println("City Id :"+cid+" City_Name : "+city+" State : "+state);
				Vaccination_Centre obj = new Vaccination_Centre(centreId, cid, cname, location);
				centreal.add(obj);

			}
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void printCentre() {
		Iterator<Vaccination_Centre> itr = centreal.iterator();
		System.out.printf("%-10s %-30s %-25s %-40s\n", "Centre_Id", "Centre_Name", "Location", "City_Id");
		while (itr.hasNext()) {
			Vaccination_Centre v = itr.next();
			System.out.printf("%-10d %-30s %-25s %-40d\n", v.getCentreId(), v.getCentreName(), v.getLocation(),
					v.getCityId());
			// System.out.println();
		}
	}

	public static void main(String[] args) {
		ShowCentre obj = new ShowCentre();
		obj.getCity();
		obj.printCentre();
	}

}
