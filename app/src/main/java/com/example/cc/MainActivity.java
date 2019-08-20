package com.example.cc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;

import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.TextView;

import com.example.cc.db.ToDoDatabaseHelper;
import com.necer.calendar.BaseCalendar;
import com.necer.calendar.EmuiCalendar;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.painter.InnerPainter;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private static final int MSG_LOAD_COMMENT = 1;
    private static final String TAG = "MainActivity";

    private EmuiCalendar emuiCalendar;
    private TextView TvResult;
    private Toolbar mToolbar;
    private Button mBtnFinish;
    private TextInputEditText mEtThing;
    private ToDoDatabaseHelper mHelper;
    private String date = "2019.8.20";
    private ToDoFragment toDoFragment;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTitle;
    private List<Fragment> mFragment;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnFinish = findViewById(R.id.commit_btn);
        mEtThing = findViewById(R.id.to_do_edit);
        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write();
                mEtThing.setText("");
            }
        });

        emuiCalendar = findViewById(R.id.emuiCalendar);
        emuiCalendar.setSelectedMode(selectedModel);
        emuiCalendar.setDefaultSelectFitst(true);//只在selectedMode==SINGLE_SELECTED有效,是否翻页选中第一个
        emuiCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate) {
                TvResult.setText("" + localDate);

                if (localDate == null) {
                    emuiCalendar.toMonth();
                } else {
                    emuiCalendar.toWeek();
                }
                Log.d(TAG, "当前页面选中:" + localDate);

            }
        });
        initData();
        initView();
    }

    public void write() {
        String content = mEtThing.getText().toString();
        mHelper = new ToDoDatabaseHelper(this, "my.db", null, 4);
        Log.d(TAG, content);
        if (!content.equals("")) {
            SQLiteDatabase db = mHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("content", content);
            values.put("date", date + "");
            db.insert("Thing", null, values);
            values.clear();
            if (mHandler != null) {
                mHandler.sendEmptyMessage(MSG_LOAD_COMMENT);
            }
        }
    }

    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add("待做");
        mTitle.add("Taylor");
        toDoFragment = new ToDoFragment();
        mFragment = new ArrayList<>();
        mFragment.add(toDoFragment);
        mFragment.add(new BroFragment());

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initView() {
        mTabLayout = findViewById(R.id.mTabLayout);
        mViewPager = findViewById(R.id.mViewPager);

        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());
        //mViewPager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int i) {
                return mFragment.get(i);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);

        TvResult = findViewById(R.id.tv_lunar);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mToolbar = findViewById(R.id.toolbar);
        ViewGroup.LayoutParams layoutParams = mToolbar.getLayoutParams();
        layoutParams.height = 200;
        mToolbar.setFitsSystemWindows(true);
        mToolbar.setLayoutParams(layoutParams);
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_LOAD_COMMENT)
                toDoFragment.setDate();
        }
    };


}









