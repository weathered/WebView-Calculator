package com.alamin.webviewcalculator;

public class History {
	private int _id;
	private String _history;
	
	public History(){
		
	}
	
	public History(String history){
		this._history = history;
	}
	
	public void set_id(int id){
		this._id = id;
	}
	
	public void set_history(String history){
		this._history = history;
	}
	
	public int get_id(){
		return _id;
	}
	
	public String get_history(){
		return _history;
	}
	
}
