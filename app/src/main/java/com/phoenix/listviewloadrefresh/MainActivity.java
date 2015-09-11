package com.phoenix.listviewloadrefresh;

import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;

import com.phoenix.listviewloadrefresh.weight.LoadMoreListView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,LoadMoreListView.IXListViewListener{
    private LoadMoreListView mListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinkedList<String> datas = new LinkedList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mListView = (LoadMoreListView)findViewById(R.id.loadmore_listview);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        mListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(Build.VERSION.SDK_INT >= 16) {
                    mListView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else {
                    mListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                swipeRefreshLayout.setRefreshing(true);
                refreshData();
            }
        });
    }
    private void refreshData(){
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                start = ++refreshCnt;
                datas.clear();
                initDatas();
                // mAdapter.notifyDataSetChanged();
                mAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, datas);
                mListView.setAdapter(mAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
    private ArrayAdapter<String> mAdapter;
    private static int refreshCnt = 0;
    private int start=0;
    private void initDatas(){
        for (int i = 0; i != 5; ++i) {
            datas.add("items:" + (start++));
        }
    }
    @Override
    public void onRefresh() {
        refreshData();

        /*tv.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setText("刷新完成");
                swipeRefreshLayout.setRefreshing(false);
            }
        },6000);*/
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setText("刷新完成");
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 6000);*/
    }

    @Override
    public void onLoadMore() {
        mListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                initDatas();
                mAdapter.notifyDataSetChanged();
                mListView.stopLoadMore();
            }
        }, 2000);
    }
}
