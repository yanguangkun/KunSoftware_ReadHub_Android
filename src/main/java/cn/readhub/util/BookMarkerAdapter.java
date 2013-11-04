package cn.readhub.util;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.readhub.R;
import cn.readhub.entity.BookMarkerEntity;

public class BookMarkerAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<BookMarkerEntity> list;
    private Context c;
    public BookMarkerAdapter(Context c,List<BookMarkerEntity> list) {
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
            View myView = inflater.inflate(R.layout.bookmarker_listitem, null);
            BookMarkerEntity bookMarkerEntity = list.get(position);
            TextView bookPageNumber = (TextView) myView.findViewById(R.id.bookPageNumber);
            TextView createTime = (TextView) myView.findViewById(R.id.createTime);
            bookPageNumber.setText("书签："+(bookMarkerEntity.getReadPageNumber() == null?0:bookMarkerEntity.getReadPageNumber())+"页");
            createTime.setText(bookMarkerEntity.getCreateTime());
            return myView;
    } 

}
