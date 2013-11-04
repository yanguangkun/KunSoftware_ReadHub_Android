package cn.readhub.util;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.readhub.BookMarkerActivity;
import cn.readhub.R;
import cn.readhub.entity.BookMarkerEntity;
import cn.readhub.entity.BooksEntity;


public class MyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<BooksEntity> list;
    private Context c;
    public MyAdapter(Context c,List<BooksEntity> list) {
            this.inflater = LayoutInflater.from(c);
            this.list = list;
            this.c = c;
    }

    @Override
    public int getCount() {
            return list.size();
    }

    @Override
    public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
    }

   
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            // 取得要显示的行View
            View myView = inflater.inflate(R.layout.book_listitem, null);
            BooksEntity book = list.get(position);
            
            DBManager mgr = new DBManager(c);  
            BookMarkerEntity bookMarker = mgr.getBookMarker(book.getId().toString());
            
            Integer pageNumber = book.getPageNumber();
            Integer readPageNumber = bookMarker.getReadPageNumber() == null?0:bookMarker.getReadPageNumber();
            
            String ret = "0%";
            if(readPageNumber > pageNumber){
            	ret = "100%";
            } else if(readPageNumber != 0) {
            	BigDecimal bd1 = new BigDecimal(pageNumber.toString());
            	BigDecimal bd2 = new BigDecimal(readPageNumber.toString());
            	BigDecimal bd3 = bd2.divide(bd1, 2, BigDecimal.ROUND_CEILING);
            	BigDecimal bd4 = bd3.multiply(new BigDecimal("100"));  
            	ret = bd4.toBigInteger().toString() + "%";
            }
            
            TextView id = (TextView) myView.findViewById(R.id.id);
            TextView bookName = (TextView) myView.findViewById(R.id.bookName);
            TextView bookMarkerInfo = (TextView) myView.findViewById(R.id.bookMarkerInfo);
            TextView bookMarkerRet = (TextView) myView.findViewById(R.id.bookMarkerRet);
            
            id.setText(book.getId().toString()); 
            bookName.setOnClickListener(new lvButtonListener(book)); 
            bookName.setText(book.getName());
            bookMarkerInfo.setText("书签："+readPageNumber+"页 ");
            bookMarkerRet.setText("总页数：" + pageNumber + "页 完成：" + ret);
            return myView;
    }
    
    class lvButtonListener implements OnClickListener { 
    	private BooksEntity book;
    	lvButtonListener(BooksEntity book) {
    		this.book = book;
    	}

    	@Override
    	public void onClick(View v) {

    		Intent intent = new Intent(c, BookMarkerActivity.class);
    		intent.putExtra("id", book.getId().toString());
            //EditText editText = (EditText) findViewById(R.id.edit_message);
            //String message = editText.getText().toString();
            //intent.putExtra(EXTRA_MESSAGE, message);
            c.startActivity(intent);
    	}
	}
    
}
 