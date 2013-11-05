package cn.readhub;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.readhub.entity.BookMarkerEntity;
import cn.readhub.entity.BooksEntity;
import cn.readhub.util.BookMarkerAdapter;
import cn.readhub.util.DBManager;


public class BookMarkerActivity extends Activity {

	private TextView bookName;
	private TextView author;
	private TextView pageNumber;
	private EditText readPageNumber;
	private Button saveBook;
	private Button back;
	private DBManager mgr; 
	private String bookId;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_book_marker);
		
		Intent intent = getIntent();
		bookId  = intent.getStringExtra("id");
		 
		mgr = new DBManager(this);  
		
		BookMarkerEntity bookMarker = mgr.getBookMarker(bookId);
		BooksEntity booksEntity = mgr.getBook(bookId);
		
		bookName = (TextView)findViewById(R.id.bookName);
		author = (TextView)findViewById(R.id.author);
		pageNumber = (TextView)findViewById(R.id.pageNumber);
		readPageNumber = (EditText)findViewById(R.id.readPageNumber);
		
		Integer bookPage = bookMarker.getReadPageNumber() == null?0:bookMarker.getReadPageNumber();
		
		bookName.setText("书名：" + booksEntity.getName());
		author.setText("作者："+booksEntity.getAuthor());
		pageNumber.setText("总页数：" + ObjectUtils.toString(booksEntity.getPageNumber()));
		readPageNumber.setText(bookPage.toString());
		saveBook = (Button)findViewById(R.id.saveBook); 
		saveBook.setOnClickListener(saveBookListener);
		back = (Button)findViewById(R.id.back);
		back.setOnClickListener(backListener); 
		listView = (ListView) findViewById(R.id.listView);  
		 
		List<BookMarkerEntity> list = mgr.queryBookMarkerList(bookId);
		BookMarkerAdapter bookMarkerAdapter = new BookMarkerAdapter(this,list);
        listView.setAdapter(bookMarkerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.book_marker, menu);
		return true;
	}

	
	public OnClickListener saveBookListener = new OnClickListener() {
		
        public void onClick(View v) { 
        	if("".equals(readPageNumber.getText().toString()) ) {
        		Toast.makeText(BookMarkerActivity.this, "页码不能为空!", Toast.LENGTH_LONG).show();
        		return ;
        	}
        	   
        	BooksEntity book = mgr.getBook(bookId);
        	book.setUpdateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        	mgr.updateBook(book);
        	
        	BookMarkerEntity bookMarker = new BookMarkerEntity();
        	bookMarker.setBookId(NumberUtils.toInt(bookId));
        	bookMarker.setReadPageNumber(NumberUtils.toInt(readPageNumber.getText().toString())); 
        	bookMarker.setCreateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")); 
        	mgr.addBookMarker(bookMarker);
        	Intent intent = new Intent(BookMarkerActivity.this, MainActivity.class); 
        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };
    
    public OnClickListener backListener = new OnClickListener() {
        public void onClick(View v) { 
        	Intent intent = new Intent(BookMarkerActivity.this, MainActivity.class);
        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };
}
