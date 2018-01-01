package hemabh.mayur.com.smsuser;

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

	public void insertData(String name, String phone, String location, String site_id) {
		ContentValues cv = new ContentValues();
		cv.put(DBHelper.MEMBER_NAME, name);
		cv.put(DBHelper.MEMBER_PHONE, phone);
		cv.put(DBHelper.MEMBER_LOCATION, location);
		cv.put(DBHelper.MEMBER_LOC_ID, site_id);
		database.insert(DBHelper.TABLE_MEMBER, null, cv);
	}

	public Cursor readData() {
		String[] allColumns = new String[] { DBHelper.MEMBER_ID, DBHelper.MEMBER_NAME, DBHelper.MEMBER_PHONE, DBHelper.MEMBER_LOCATION, DBHelper.MEMBER_LOC_ID};
		Cursor c = database.query(DBHelper.TABLE_MEMBER, allColumns, null,
				null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}



	public int updateData(long memberID, String memberName, String memberPhone, String memberLocation, String memberSiteID) {
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(DBHelper.MEMBER_NAME, memberName);
		cvUpdate.put(DBHelper.MEMBER_PHONE, memberPhone);
		cvUpdate.put(DBHelper.MEMBER_LOCATION, memberLocation);
		cvUpdate.put(DBHelper.MEMBER_LOC_ID, memberSiteID);
		
		int i = database.update(DBHelper.TABLE_MEMBER, cvUpdate,
				DBHelper.MEMBER_ID + " = " + memberID, null);
		return i;
	}

	public void deleteData(long memberID) {
		database.delete(DBHelper.TABLE_MEMBER, DBHelper.MEMBER_ID + "="
				+ memberID, null);
	}


	public Cursor searchPopulate (String searchKeyword){


		Cursor a = database.rawQuery(" SELECT * FROM "+DBHelper.TABLE_MEMBER+" WHERE" +
				" "+DBHelper.MEMBER_NAME+" LIKE '"+searchKeyword+"%' " +
				"OR "+DBHelper.MEMBER_PHONE+" LIKE '"+searchKeyword+"%' " +
				"OR "+DBHelper.MEMBER_LOCATION+" LIKE '"+searchKeyword+"%' " +
				"OR "+DBHelper.MEMBER_LOC_ID+" LIKE '"+searchKeyword+"%'",null);

		if (a != null) {
			a.moveToFirst();
		}

		return a;
	}

}// outer class end
