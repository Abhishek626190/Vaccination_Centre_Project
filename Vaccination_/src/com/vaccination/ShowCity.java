package com.vaccination;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class ShowCity {
	Statement stat;
	String sql;
	ResultSet res;
	Connection con;
	ArrayList<City> cityal = new ArrayList<>();

	public void getCity() {

		try {
			con = ConnectDatabase.getConnection();
			sql = " select * from city";
			stat = con.createStatement();
			res = stat.executeQuery(sql);
			while (res.next()) {
				String city = res.getString(2);
				int cid = res.getInt(1);
				String state = res.getString(3);
				// System.out.println("City Id :"+cid+" City_Name : "+city+" State : "+state);
				City obj = new City(cid, city, state);
				cityal.add(obj);

			}
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void printCity() {
		Iterator<City> itr = cityal.iterator();
		System.out.printf("%-8s %-30s %-15s \n", "City Id", "City", "State");

		while (itr.hasNext()) {
			City c = itr.next();
			System.out.printf("%-8d %-30s %-15s\n", c.getCid(), c.getCname(), c.getState());
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ShowCity obj = new ShowCity();
		obj.getCity();
		obj.printCity();

	}

}
