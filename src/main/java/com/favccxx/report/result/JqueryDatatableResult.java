package com.favccxx.report.result;

import java.util.List;

public class JqueryDatatableResult {
	
	private String sEcho;
	
	private int iTotalRecords;
	
	private int iTotalDisplayRecords;
	
	@SuppressWarnings("rawtypes")
	private List aaData;

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	@SuppressWarnings("rawtypes")
	public List getAaData() {
		return aaData;
	}

	@SuppressWarnings("rawtypes")
	public void setAaData(List aaData) {
		this.aaData = aaData;
	}
	
	

}
