package com.kodzo.tim.skoladroidprod.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kodzo.tim.skoladroidprod.R;
import com.kodzo.tim.skoladroidprod.model.Comment;

import java.util.List;

/**
 * Created by Milos PC on 03-Jul-16.
 */
public class SchoolCommentAdapter extends BaseAdapter {

    private View view;
    private Context context;
    private List<Comment> mComments;

    public SchoolCommentAdapter (View view1, Context context1, List<Comment> comments) {

        view = view1;
        context = context1;
        mComments = comments;
    }

    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Object getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater li = LayoutInflater.from(context);
            v = li.inflate(R.layout.comment_item, null, false);

        }

        TextView username = (TextView) v.findViewById(R.id.commentUserName);
        TextView comment = (TextView) v.findViewById(R.id.commentText);


        username.setText(mComments.get(position).getUsername() + ":");
        comment.setText(mComments.get(position).getComment());

        if (position%2 == 0) {
            LinearLayout li = (LinearLayout) v.findViewById(R.id.commentListItem);
            li.setBackgroundColor(Color.parseColor("#e0a7d2eb"));
        }
        else {
            LinearLayout li = (LinearLayout) v.findViewById(R.id.commentListItem);
            li.setBackgroundColor(Color.parseColor("#C9A7D2EB"));
        }

        return v;
    }
}