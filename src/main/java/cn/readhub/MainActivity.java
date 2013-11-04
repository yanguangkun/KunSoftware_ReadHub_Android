package cn.readhub;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.readhub.entity.BooksEntity;
import cn.readhub.util.DBManager;
import cn.readhub.util.MyAdapter;


public class MainActivity extends Activity {

	private DBManager mgr;  
	private ListView listView;
	private Button addBook;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main); 
		listView = (ListView) findViewById(R.id.listView);  
		addBook = (Button)findViewById(R.id.addBook);
		addBook.setOnClickListener(addBookListener);

		mgr = new DBManager(this);  
		List<BooksEntity> list = mgr.query(); 
        MyAdapter myAdapter = new MyAdapter(this,list);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new OnItemClickListener(){ 
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            	 
            	BooksEntity book = (BooksEntity)listView.getItemAtPosition(arg2); 
            	Intent intent = new Intent(MainActivity.this, BookMarkerActivity.class);
        		intent.putExtra("id", book.getId().toString());
        		startActivity(intent); 
            }

        });
        
        //添加长按点击   
        listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {   
               
	        @Override  
	        public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {   
	            menu.setHeaderTitle("操作");      
	            menu.add(0, 0, 0, "编辑");         
	        }   
        });  
	}
	
	public OnClickListener addBookListener = new OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent(MainActivity.this, AddBookActivity.class); 
            startActivity(intent);
        }
    }; 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	//长按菜单响应函数  
	@Override  
	public boolean onContextItemSelected(MenuItem item) {  
	     
		AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();                
		String id = ((TextView)menuInfo.targetView.findViewById(R.id.id)).getText().toString();
         
		Intent intent = new Intent(MainActivity.this, AddBookActivity.class); 
     
		intent.putExtra("id", id);
		startActivity(intent);
		
	    return super.onContextItemSelected(item);  
	}  
}
