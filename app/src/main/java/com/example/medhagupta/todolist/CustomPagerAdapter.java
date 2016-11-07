package com.example.medhagupta.todolist;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Medha Gupta on 11/5/2016.
 */
public class CustomPagerAdapter extends PagerAdapter{

    Context mContext;
    LayoutInflater mLayoutInflater;
    private DatabaseHandler dbHandler;
    TextView title;
    TextView details;

    public CustomPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return MainActivity.doList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ScrollView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.view_item, container, false);

        //TextView titleName = (TextView) itemView.findViewById(R.id.title);
        //TextView detailsTask = (TextView) itemView.findViewById(R.id.details);
        title=(TextView)itemView.findViewById(R.id.textViewTitle);
        details=(TextView)itemView.findViewById(R.id.textViewDetails);

        String titleList = MainActivity.doList.get(position).getTitle();
        String detailsList = MainActivity.doList.get(position).getDetails();

        title.setText(titleList);
        details.setText(detailsList);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ScrollView) object);
    }

}
