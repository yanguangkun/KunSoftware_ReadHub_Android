package cn.readhub;

import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
import android.widget.Toast;
import cn.readhub.entity.BooksEntity;
import cn.readhub.util.DBManager;


public class AddBookActivity extends Activity {

	private EditText bookName;
	private EditText author;
	private EditText pageNumber;
	
	private Button saveBook;
	private Button back;
	private DBManager mgr; 
	
	private String bookId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_book);
		 
		Intent intent = getIntent();
		bookId  = intent.getStringExtra("id");
		
		mgr = new DBManager(this);  
		bookName = (EditText)findViewById(R.id.bookName);
		author = (EditText)findViewById(R.id.author);
		pageNumber = (EditText)findViewById(R.id.pageNumber);
		saveBook = (Button)findViewById(R.id.saveBook); 
		saveBook.setOnClickListener(saveBookListener);
		back = (Button)findViewById(R.id.back);
		back.setOnClickListener(backListener);
		
		if(StringUtils.isNotBlank(bookId)) {
			BooksEntity booksEntity = mgr.getBook(bookId);
			bookName.setText(booksEntity.getName());
			author.setText(booksEntity.getAuthor());
			pageNumber.setText(ObjectUtils.toString(booksEntity.getPageNumber()));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.add_book, menu);
		return true;
	}

	public OnClickListener saveBookListener = new OnClickListener() {
        public void onClick(View v) {
        	
        	if("".equals(bookName.getText().toString()) ) {
        		Toast.makeText(AddBookActivity.this, "书名不能为空!", Toast.LENGTH_LONG).show();
        		return ;
        	}
        	BooksEntity book = mgr.getBook(bookId);
        	
        	book.setName(bookName.getText().toString());
        	book.setAuthor(author.getText().toString());
        	book.setPageNumber(NumberUtils.toInt(pageNumber.getText().toString()));
        	
        	if(StringUtils.isEmpty(book.getCreateTime()))
        		book.setCreateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        	book.setUpdateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        	
        	if(book.getId() == null) {
        		mgr.addBook(book);
        	} else {
        		mgr.updateBook(book);
        	}
        	Intent intent = new Intent(AddBookActivity.this, MainActivity.class);
        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };
    
    public OnClickListener backListener = new OnClickListener() {
    	
        public void onClick(View v) { 
        	Intent intent = new Intent(AddBookActivity.this, MainActivity.class);
        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };
}
