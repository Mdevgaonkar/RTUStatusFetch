package com.mayur.smsalert2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLcontroller {

	private DBHelper DBHelper;
	private Context ourcontext;
	private SQLiteDatabase database;

	public SQLcontroller(Context c) {
		ourcontext = c;
	}

	public SQLcontroller open() throws SQLException {
		DBHelper = new DBHelper(ourcontext);
		database = DBHelper.getWritableDatabase();
		return this;

	}

	public void close() {
		DBHelper.close();
	}

	public void insertData(String name, String phone) {
		ContentValues cv = new ContentValues();
		cv.put(DBHelper.MEMBER_NAME, name);
		cv.put(DBHelper.MEMBER_PHONE, phone);
		database.insert(DBHelper.TABLE_MEMBER, null, cv);
	}

	public Cursor readData() {
		String[] allColumns = new String[] { DBHelper.MEMBER_ID,DBHelper.MEMBER_NAME,DBHelper.MEMBER_PHONE };
		Cursor c = database.query(DBHelper.TABLE_MEMBER, allColumns, null,
				null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	public String readName( String member) {
		String[] allColumns = new String[] { DBHelper.MEMBER_ID,
				DBHelper.MEMBER_NAME,DBHelper.MEMBER_PHONE };
		Cursor c  = database.query(DBHelper.TABLE_MEMBER, allColumns, null,
				null, null, null, null); ;
		int id=Integer.parseInt(member);
		c.moveToPosition(id+1);
		
		//if (c != null) {
			//c.moveToFirst();
		//}
		
		String personName= c.getString(c.getColumnIndex(DBHelper.MEMBER_NAME));
		return personName;
	}

	public int updateData(long memberID, String memberName, String memberPhone) {
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(DBHelper.MEMBER_NAME, memberName);
		cvUpdate.put(DBHelper.MEMBER_PHONE, memberPhone);
		
		int i = database.update(DBHelper.TABLE_MEMBER, cvUpdate,
				DBHelper.MEMBER_ID + " = " + memberID, null);
		return i;
	}

	public void deleteData(long memberID) {
		database.delete(DBHelper.TABLE_MEMBER, DBHelper.MEMBER_ID + "="
				+ memberID, null);
	}

}// outer class end
