package com.alamin.webviewcalculator;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import android.content.ContentValues;

public class DBHandler extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "calculatorfinal.db";
	public static final String TABLE_CALC = "calchistory";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_HISTORY = "history";
	
	private Context myContext;
	
	public DBHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		myContext = context;
	}
	
	public DBHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
		myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE " + TABLE_CALC + "(" + 
						COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
						COLUMN_HISTORY + " TEXT " +
						");";
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_CALC);
		onCreate(db);
	}
	
	public void addHistory(History history){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_HISTORY, history.get_history());
		
		long newRowId = db.insert(TABLE_CALC, null, values);
	}
	
	public void deleteHistory(){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("TRUNCATE TABLE " + TABLE_CALC);
	}
	
	public String displayHistory(){
		SQLiteDatabase db = this.getReadableDatabase();
		
		String finalResult = "";
		
		String[] projection = { this.COLUMN_ID, this.COLUMN_HISTORY };
		
		Cursor cursor = db.query(this.TABLE_CALC, projection, null, null, null, null, null);
			
		while(cursor.moveToNext()) {
			String id = cursor.getString(0);
			String history = cursor.getString(1);
			finalResult += id + "  |  " + history + "<br>";
		}
		
		cursor.close();
		return finalResult;
	}
	
}
