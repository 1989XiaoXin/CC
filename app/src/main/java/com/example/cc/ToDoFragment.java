package com.example.cc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cc.db.ToDoDatabaseHelper;

import java.util.List;

public class ToDoFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ToDoAdapter toDoAdapter;
    private static final String TAG = "ToDoFragment";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);
        setDate();
        initView(view);
        return view;
    }

    public void setDate() {
        toDoAdapter = new ToDoAdapter(getContext());
        toDoAdapter.getDiaryBeanList();
        Log.d(TAG, "ok");
    }

    private void initView(View view) {

        mRecyclerView = view.findViewById(R.id.to_do_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(toDoAdapter);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }
}
