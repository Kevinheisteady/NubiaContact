package com.kevin.test.nubia;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kevin-He on 2016/8/26.
 */
public class InfoAdapter extends ArrayAdapter {

    private static final String TAG = "InfoAdapter";
    private int resourceId;
    public InfoAdapter(Context context, int resource, List objects) {
        super(context, resource,objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person item = (Person) getItem(position);
        Log.d(TAG, "getView: "+item.getName());
        ViewHolder viewHolder = null;
        if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(resourceId,null);
                viewHolder.infoName = (TextView) convertView.findViewById(R.id.info_name);
                viewHolder.infoStuNum = (TextView) convertView.findViewById(R.id.info_student_num);
                viewHolder.infoPhoneNum = (TextView) convertView.findViewById(R.id.info_phone_num);
                convertView.setTag(viewHolder);
        }else{
                viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.infoName.setText(item.getName());
        viewHolder.infoPhoneNum.setText(item.getPhoneNum());
        viewHolder.infoStuNum.setText(item.getStuNum());
        return convertView;
    }
    private class ViewHolder{
        public TextView infoName;
        public TextView infoStuNum;
        public TextView infoPhoneNum;
    }
}
