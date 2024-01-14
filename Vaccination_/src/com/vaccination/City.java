package com.vaccination;

public class City {

	private int cid;
	private String cname, state;

	public City(int cid, String cname, String state) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.state = state;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "City [cid=" + cid + ", cname=" + cname + ", state=" + state + "]";
	}

}
