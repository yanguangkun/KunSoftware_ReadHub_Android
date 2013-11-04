package cn.readhub.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "readhub.db";
	private static final int DATABASE_VERSION = 2;
	
	public DBHelper(Context context) {
		//CursorFactory����Ϊnull,ʹ��Ĭ��ֵ
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//���ݿ��һ�α�����ʱonCreate�ᱻ����
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS books" +
				"(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(60),author varchar(100),page_number INTEGER,create_time VARCHAR(60),update_time VARCHAR(60))"); 
		db.execSQL("CREATE TABLE IF NOT EXISTS book_marker" +
				"(id INTEGER PRIMARY KEY AUTOINCREMENT, book_id INTEGER,read_page_number INTEGER,create_time VARCHAR(60))"); 
	}

	//���DATABASE_VERSIONֵ����Ϊ2,ϵͳ�����������ݿ�汾��ͬ,�������onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
		
		//db.execSQL("CREATE TABLE IF NOT EXISTS book_marker" +
		//		"(id INTEGER PRIMARY KEY AUTOINCREMENT, book_id INTEGER,book_page INTEGER,create_time VARCHAR(60))"); 
	}
}
