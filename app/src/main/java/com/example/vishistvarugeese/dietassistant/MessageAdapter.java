package com.example.vishistvarugeese.dietassistant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishist Varugeese on 18-03-2018.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    private List<Message> chat_list = new ArrayList<Message>();
    private TextView txtMessage, txtDate,txtName;
    Context ctx;

    public MessageAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        ctx = context;
    }

    @Override
    public void add(@Nullable Message object) {
        super.add(object);
        chat_list.add(object);
    }

    @Override
    public int getCount() {
        return chat_list.size();
    }

    @Nullable
    @Override
    public Message getItem(int position) {
        return chat_list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        txtName = (TextView) convertView.findViewById(R.id.txtName);
        txtDate = (TextView) convertView.findViewById(R.id.txtDate);
        txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
        String stringMessage;
        String stringDate;
        String stringName;
        boolean POSITION;


        Message provider = getItem(position);
        stringMessage = provider.message;
        stringDate = provider.date;
        stringName = provider.name1;
        POSITION = provider.position;

        txtName.setText(stringName);
        txtDate.setText(stringDate);
        txtMessage.setText(stringMessage);

        if(POSITION == true){
            txtMessage.setBackgroundResource(R.drawable.round_textview);
            txtMessage.setTextColor(ContextCompat.getColor(ctx, R.color.colorPrimary));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            txtName.setLayoutParams(params);
            txtDate.setLayoutParams(params);
            txtMessage.setLayoutParams(params);

        } else if(POSITION == false) {
            txtMessage.setBackgroundResource(R.drawable.round_textview1);
            txtMessage.setTextColor(ContextCompat.getColor(ctx, R.color.colorAccent));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.LEFT;
            txtName.setLayoutParams(params);
            txtDate.setLayoutParams(params);
            txtMessage.setLayoutParams(params);
        }

        return convertView;
    }
}
