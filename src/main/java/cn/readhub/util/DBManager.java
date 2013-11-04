package cn.readhub.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.readhub.entity.BookMarkerEntity;
import cn.readhub.entity.BooksEntity;

public class DBManager {
	private DBHelper helper;
	private SQLiteDatabase db;
	
	public DBManager(Context context) {
		helper = new DBHelper(context);
		//因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
		//所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = helper.getWritableDatabase();
	}
	 
	public void add(List<BooksEntity> books) {
        db.beginTransaction();	//开始事务
        try {
        	for (BooksEntity book : books) {
        		db.execSQL("INSERT INTO books(name,author,page_number,create_time,update_time) " +
        				"VALUES(?,?,?,?,?)", new Object[]{book.getName(),book.getAuthor(),book.getPageNumber()
        				,book.getCreateTime(),book.getUpdateTime()});
        	}
        	db.setTransactionSuccessful();	//设置事务成功完成
        } finally {
        	db.endTransaction();	//结束事务
        }
	}
	
	public BooksEntity getBook(String bookId) {
		
		Cursor c = queryBook(bookId);
		BooksEntity booksEntity =  new BooksEntity();
        if (c.moveToNext()) {
        	booksEntity.setId(c.getInt(c.getColumnIndex("id")));
        	booksEntity.setName(c.getString(c.getColumnIndex("name")));
        	booksEntity.setAuthor(c.getString(c.getColumnIndex("author")));
        	booksEntity.setPageNumber(c.getInt(c.getColumnIndex("page_number")));
        	booksEntity.setCreateTime(c.getString(c.getColumnIndex("create_time"))); 
        	booksEntity.setUpdateTime(c.getString(c.getColumnIndex("update_time")));
        }  
        c.close();
        return booksEntity;
	}
	
	public void addBook(BooksEntity book) {
		
		db.beginTransaction();	//开始事务
        try {
        	db.execSQL("INSERT INTO books(name,author,page_number,create_time,update_time) " +
    				"VALUES(?,?,?,?,?)", new Object[]{book.getName(),book.getAuthor(),book.getPageNumber()
    				,book.getCreateTime(),book.getUpdateTime()});
        	db.setTransactionSuccessful();	//设置事务成功完成
        } finally {
        	db.endTransaction();	//结束事务
        }
	}
	
	/**
	 * update person's age
	 * @param person
	 */
	public void updateBook(BooksEntity book) {
		ContentValues cv = new ContentValues();
		cv.put("name", book.getName());
		cv.put("author", book.getAuthor());
		cv.put("page_number", book.getPageNumber());
		cv.put("create_time", book.getCreateTime());
		cv.put("update_time", book.getUpdateTime());
		db.update("books", cv, "id = ?", new String[]{book.getId().toString()});
	}
	
	/**
	 * delete old person
	 * @param person
	 */
	public void deleteBook(BooksEntity book) {
		db.delete("books", "id = ?", new String[]{book.getId().toString()});
	}
	
	/**
	 * query all persons, return list
	 * @return List<Person>
	 */
	public List<BooksEntity> query() {
		ArrayList<BooksEntity> books = new ArrayList<BooksEntity>();
		Cursor c = queryTheCursor();
		
        while (c.moveToNext()) {
        	BooksEntity book = new BooksEntity();
        	book.setId(c.getInt(c.getColumnIndex("id")));
        	book.setName(c.getString(c.getColumnIndex("name"))); 
        	book.setAuthor(c.getString(c.getColumnIndex("author")));
        	book.setPageNumber(c.getInt(c.getColumnIndex("page_number")));
        	book.setCreateTime(c.getString(c.getColumnIndex("create_time")));
        	book.setUpdateTime(c.getString(c.getColumnIndex("update_time")));
        	books.add(book);
        }
        
        c.close();
        return books;
	}
	
	
	public void addBookMarker(BookMarkerEntity bookMarker) {
		db.beginTransaction();	//开始事务
        try {
        	db.execSQL("INSERT INTO book_marker(book_id,read_page_number,create_time) VALUES(?,?,?)", new Object[]{bookMarker.getBookId(),bookMarker.getReadPageNumber(),bookMarker.getCreateTime()});
        	db.setTransactionSuccessful();	//设置事务成功完成
        } finally {
        	db.endTransaction();	//结束事务
        }
	}
	
	public BookMarkerEntity getBookMarker(String bookId) {
		
		Cursor c = queryBookMarker(bookId);
		BookMarkerEntity bookMarker =  new BookMarkerEntity();
        if (c.moveToNext()) {
        	bookMarker.setReadPageNumber(c.getInt(c.getColumnIndex("read_page_number")));
        	
        }  
        c.close();
        return bookMarker;
	}
	
	public List<BookMarkerEntity> queryBookMarkerList(String bookId) {
		ArrayList<BookMarkerEntity> bookMarkers = new ArrayList<BookMarkerEntity>();
		Cursor c = queryBookMarkerAll(bookId);
		BookMarkerEntity bookMarkerEntity = null;
        while (c.moveToNext()) {
        	bookMarkerEntity = new BookMarkerEntity();
        	bookMarkerEntity.setId(c.getInt(c.getColumnIndex("id")));
        	bookMarkerEntity.setBookId(c.getInt(c.getColumnIndex("book_id")));
        	bookMarkerEntity.setReadPageNumber(c.getInt(c.getColumnIndex("read_page_number")));
        	bookMarkerEntity.setCreateTime(c.getString(c.getColumnIndex("create_time")));
        	bookMarkers.add(bookMarkerEntity);
        }
        c.close();
        return bookMarkers;
	}
	
	/**
	 * query all persons, return cursor
	 * @return	Cursor
	 */
	public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM books order by update_time desc", null);
        return c;
	}
	
	public Cursor queryBook(String pageId) {
		
		Cursor c = db.rawQuery("select * from books where id='"+pageId+"'", null);
        return c;
	}
	
	public Cursor queryBookMarker(String bookId) {
		
		Cursor c = db.rawQuery("select * from book_marker where book_id='"+bookId+"' order by id desc limit 1", null);
        return c;
	}
	
	public Cursor queryBookMarkerAll(String bookId) {
        Cursor c = db.rawQuery("SELECT * FROM book_marker where book_id='"+bookId+"' order by create_time desc", null);
        return c;
	}
	
	/**
	 * close database
	 */
	public void closeDB() {
		db.close();
	}

}
