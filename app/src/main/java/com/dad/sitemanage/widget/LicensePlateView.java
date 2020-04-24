package com.dad.sitemanage.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dad.sitemanage.R;
import com.dad.sitemanage.util.ConvertUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 车牌输入法
 */
public class LicensePlateView extends LinearLayout {

    public static final int TYPE_PLATE_PROVINCE = 1;
    public static final int TYPE_PLATE_NUMBER = 2;
    public static final int TYPE_CLEAR = 3;
    public static final int TYPE_DELETE = 4;
    public static final int TYPE_SURE = 5;

    private int mSourceType;
    //数据源
    private List<String> mSourceList = new ArrayList<>();
    //整个View背景颜色
    private final int BACKGROUND_COLOR = Color.parseColor("#DDDDDD");
    //输入法文字颜色
    private final int KEY_TEXT_COLOR = Color.parseColor("#000000");
    //键的间隔
    private final int KEY_FRAME_MARGIN = 15;
    //键盘上下左右的边距
    private final int KEY_FRAME_PADDING = 10;
    //输入法列数
    private int mSpanCount = 7;

    private OnKeyClickListener mOnKeyClickListener;
    private OnButtonClickListener mOnButtonClickListener;
    private RecyclerView mRecyclerView;
    private KeyAdapter mKeyAdapter;
    private LinearLayout mBtnLayout;

    public LicensePlateView(Context context) {
        this(context, null);
    }

    public LicensePlateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LicensePlateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(BACKGROUND_COLOR);

        initRecyclerLayout(context);
        initBtnLayout(context);

        setVisibility(GONE);
    }

    private void initRecyclerLayout(Context context){
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), mSpanCount));
        mRecyclerView.addItemDecoration(new RecycleGridDivider(KEY_FRAME_MARGIN));
        mKeyAdapter = new KeyAdapter(R.layout.recycler_item_license_plate_key, mSourceList);
        mRecyclerView.setAdapter(mKeyAdapter);
        int padding = ConvertUtil.dp2px(context, KEY_FRAME_PADDING);
        mRecyclerView.setPadding(padding, padding, padding, padding);
        addView(mRecyclerView);
    }

    private void initBtnLayout(Context context){
        mBtnLayout = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(
                ConvertUtil.dp2px(context,4),
                ConvertUtil.dp2px(context,0),
                ConvertUtil.dp2px(context,4),
                ConvertUtil.dp2px(context,2)
        );
        mBtnLayout.setLayoutParams(layoutParams);

        layoutParams.weight = 1;
        Button clearBtn = buildButton(context, "清空");
        clearBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnButtonClickListener != null){
                    mOnButtonClickListener.onBtnClick(mSourceType,TYPE_CLEAR);
                }
            }
        });
        Button deleteBtn = buildButton(context, "删除");
        deleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnButtonClickListener != null){
                    mOnButtonClickListener.onBtnClick(mSourceType,TYPE_DELETE);
                }
            }
        });
        Button sureBtn = buildButton(context, "确定");
        sureBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnButtonClickListener != null){
                    mOnButtonClickListener.onBtnClick(mSourceType,TYPE_SURE);
                }
            }
        });
        clearBtn.setLayoutParams(layoutParams);
        deleteBtn.setLayoutParams(layoutParams);
        sureBtn.setLayoutParams(layoutParams);
        mBtnLayout.addView(clearBtn);
        mBtnLayout.addView(deleteBtn);
        mBtnLayout.addView(sureBtn);

        addView(mBtnLayout);
    }

    private Button buildButton(Context context, String text){
        Button button = new Button(context);
        button.setBackgroundResource(R.drawable.bg_frame_filling_green);
        button.setText(text);
        return button;
    }

    public void setSourceList(int sourceType, String[] dataSource){
        mSourceList.clear();
        this.mSourceType = sourceType;
        Collections.addAll(mSourceList, dataSource);
        mKeyAdapter.notifyDataSetChanged();
    }

    public void setSpanCount(int spanCount){
        this.mSpanCount = spanCount;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), mSpanCount));
    }

    public int getCurrentSourceType(){
        return mSourceType;
    }

    public void show(){
        if (!isShow()){
            setVisibility(VISIBLE);
        }
    }

    public void dismiss(){
        if (isShow()){
            setVisibility(GONE);
        }
    }

    public boolean isShow(){
        if (getVisibility() == VISIBLE){
            return true;
        }
        return false;
    }

    private class KeyAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

        public KeyAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_license_plate_item_key, item);
            helper.setTextColor(R.id.tv_license_plate_item_key, KEY_TEXT_COLOR);
            helper.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnKeyClickListener != null){
                        mOnKeyClickListener.onKeyClick(mSourceType,item);
                    }
                }
            });
        }
    }

    public class RecycleGridDivider extends RecyclerView.ItemDecoration {

        private int mSpace;

        public RecycleGridDivider(int space) {
            this.mSpace = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            int span = manager.getSpanCount();
            //为了Item大小均匀，将设定分割线平均分给左右两边Item各一半
            int offset = mSpace / 2;
            //得到View的位置
            int childPosition = parent.getChildAdapterPosition(view);
            //第一排，顶部不画
            if (childPosition < span) {
                //最左边的，左边不画
                if (childPosition % span == 0) {
                    outRect.set(0, 0, offset, 0);
                    //最右边，右边不画
                } else if (childPosition % span == span - 1) {
                    outRect.set(offset, 0, 0, 0);
                } else {
                    outRect.set(offset, 0, offset, 0);
                }
            } else {
                //上下的分割线，就从第二排开始，每个区域的顶部直接添加设定大小，不用再均分了
                if (childPosition % span == 0) {
                    outRect.set(0, mSpace, offset, 0);
                } else if (childPosition % span == span - 1) {
                    outRect.set(offset, mSpace, 0, 0);
                } else {
                    outRect.set(offset, mSpace, offset, 0);
                }
            }
        }
    }

    public void setOnKeyClickListener(OnKeyClickListener listener) {
        this.mOnKeyClickListener = listener;
    }

    public interface OnKeyClickListener {
        void onKeyClick(int sourceType, String key);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener){
        this.mOnButtonClickListener = listener;
    }

    public interface OnButtonClickListener{
        void onBtnClick(int sourceType, int btnType);
    }

}
