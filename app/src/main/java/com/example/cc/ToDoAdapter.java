package com.example.cc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cc.db.ToDoDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoBean> mToDoList;
    private Context context;
    private String date;
    private static final String TAG = "ToDoAdapter";
    ToDoDatabaseHelper mHelper;

    public ToDoAdapter(Context context) {
        this.context = context;
        mToDoList = new ArrayList<>();
        date = "2019.8.20";
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.to_do_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.textView.setText(mToDoList.get(i).getThing());
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = viewHolder.textView.getText().toString();
                mHelper = new ToDoDatabaseHelper(context, "my.db", null, 4);
                SQLiteDatabase dbDelete = mHelper.getWritableDatabase();
                dbDelete.delete("Thing", "content = ?", new String[]{content});
                dbDelete.delete("Thing", "date = ?", new String[]{date});
                removeData(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mToDoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.to_do_text);
            button = itemView.findViewById(R.id.finish_btn);
        }
    }

    public List<ToDoBean> getDiaryBeanList() {
        mHelper = new ToDoDatabaseHelper(context, "my.db", null, 4);
        mToDoList = new ArrayList<>();
        List<ToDoBean> diaryList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = mHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("Thing", null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                mToDoList.add(new ToDoBean(content, date));
            } while (cursor.moveToNext());
        }
        cursor.close();

        for (int i = mToDoList.size() - 1; i >= 0; i--) {
            diaryList.add(mToDoList.get(i));
        }
        mToDoList = diaryList;
        notifyDataSetChanged();
        return mToDoList;
    }

    public void removeData(int position) {
        mToDoList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}
