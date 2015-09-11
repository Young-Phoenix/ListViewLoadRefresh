package com.phoenix.listviewloadrefresh.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phoenix.listviewloadrefresh.R;


/**
 * @FileName:cn.net.xyd.mvpdemo.weight.ListViewFooter.java
 * @author:Phoenix
 * @date:2015-09-11 08:44
 * @Version:V1.0
 */
public class ListViewFooter extends LinearLayout {
    private static final String TAG = "ListViewFooter";

    public static final int STATE_NORMAL = 0;
    public static final int STATE_READY = 1;
    public static final int STATE_LOADING = 2;

    private RelativeLayout mContentView;
    private ProgressBar mProgressBar;
    private TextView mHintVIew;

    public ListViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public ListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public void initView(Context context){
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        mContentView = (RelativeLayout)LayoutInflater.from(context).inflate(R.layout.xlistview_footer,null);
        addView(mContentView,lp);
        this.setBackgroundResource(R.drawable.background);
        mProgressBar = (ProgressBar)findViewById(R.id.xlistview_footer_progressbar);
        mHintVIew = (TextView)findViewById(R.id.xlistview_footer_hint_textview);
    }
    public void setState(int state) {
        switch (state) {
            case STATE_NORMAL:
                normal();
                break;
            case STATE_READY:
                mHintVIew.setVisibility(VISIBLE);
                mHintVIew.setText(R.string.xlistview_footer_ready_hint);
                break;
            case STATE_LOADING:
                loading();
                break;
        }
    }
    public void setBottomMargin(int height) {
        if (height < 0) return;
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }
    public int getBottomMargin() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        return lp.bottomMargin;
    }

    /**
     * normal state
     */
    public void normal() {
        mHintVIew.setVisibility(VISIBLE);
        mProgressBar.setVisibility(INVISIBLE);
        mHintVIew.setText(R.string.xlistview_footer_normal_hint);
    }

    /**
     * loading state
     */
    public void loading() {
        mHintVIew.setText(R.string.xlistview_footer_loading_hint);
        mProgressBar.setVisibility(VISIBLE);
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = 0;
        mContentView.setLayoutParams(lp);
    }

    /**
     * show footer
     */
    public void show(){
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        mContentView.setLayoutParams(lp);
    }
}
