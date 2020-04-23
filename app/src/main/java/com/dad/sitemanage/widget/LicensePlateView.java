package com.dad.sitemanage.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dad.sitemanage.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 项目名:    LicensePlateKeyboard
 * 包名       com.azhon.keyboard
 * 文件名:    LicensePlateView
 * 创建时间:  2019-08-30 on 22:51
 * 描述:     TODO 车牌号输入键盘
 *
 * @author 阿钟
 */

public class LicensePlateView extends LinearLayout implements View.OnClickListener {

    /**
     * 车牌简称
     */
    private List<String> provinceList = new ArrayList<>();
    /**
     * 0~9,A~Z(车牌里没有I、O字母)
     */
    private List<String> numList = new ArrayList<>();
    /**
     * 键盘的背景颜色
     */
    private final int backgroundColor = Color.parseColor("#e9e9e9");
    /**
     * 键盘文字颜色
     */
    private final int keyTextColor = Color.parseColor("#333333");
    /**
     * 键盘列数
     */
    private final int spanCount = 10;
    /**
     * 键盘 键的间隔
     */
    private final int keyButtonMargin = 15;
    /**
     * 键盘上下左右的边距
     */
    private final int keyboardPadding = 10;
    /**
     * 按键点击回调
     */
    private OnKeyClickListener onKeyClickListener;

    private KeyAdapter keyAdapter;

    public LicensePlateView(Context context) {
        super(context);
        init(context);
    }


    public LicensePlateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(backgroundColor);
        initKeys();
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        recyclerView.setLayoutManager(new GridLayoutManager(context, spanCount));
        recyclerView.addItemDecoration(new RecycleGridDivider(keyButtonMargin));
        int padding = dip2px(context, keyboardPadding);
        recyclerView.setPadding(padding, padding, padding, padding);
        addView(recyclerView);

        keyAdapter = new KeyAdapter(this);
        recyclerView.setAdapter(keyAdapter);
        keyAdapter.setNewData(provinceList);
    }

    /**
     * 初始化按键
     */
    private void initKeys() {
        String[] province = getResources().getStringArray(R.array.province);
        String[] num = getResources().getStringArray(R.array.nums);
        Collections.addAll(provinceList, province);
        Collections.addAll(numList, num);
    }

    /**
     * 按键点击事件
     */
    @Override
    public void onClick(View v) {
        TextView tvKey = v.findViewById(R.id.tv_key);
        String key = tvKey.getText().toString();
        if (key.equals("ABC\n123")) {
            //键盘切换
            keyAdapter.setNewData(numList);
            return;
        } else if (key.equals("省")) {
            keyAdapter.setNewData(provinceList);
            return;
        }
        if (onKeyClickListener != null) {
            onKeyClickListener.onKeyClick(key);
        }
    }

    private class KeyAdapter extends RecyclerView.Adapter<KeyAdapter.KeyViewHolder> {

        private List<String> list = new ArrayList<>();
        private OnClickListener listener;

        public KeyAdapter(OnClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public KeyAdapter.KeyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_key, parent, false);
            return new KeyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull KeyAdapter.KeyViewHolder holder, int position) {
            String key = list.get(position);
            holder.tvKey.setText(key);
            holder.itemView.setOnClickListener(listener);
            if (TextUtils.isEmpty(key)) {
                holder.itemView.setBackgroundResource(0);
                //键盘类型切换按键
            } else if (key.equals("ABC\n123") || key.equals("省")) {
                holder.tvKey.setTextSize(10);
                holder.itemView.setBackgroundResource(R.drawable.sel_blue_radius_2);
                holder.tvKey.setTextColor(Color.WHITE);
            } else {
                holder.tvKey.setTextSize(12);
                holder.itemView.setBackgroundResource(R.drawable.sel_white_radius_2);
                holder.tvKey.setTextColor(keyTextColor);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setNewData(List<String> list) {
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }

        private class KeyViewHolder extends RecyclerView.ViewHolder {

            private TextView tvKey;

            public KeyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvKey = itemView.findViewById(R.id.tv_key);
            }
        }
    }

    public class RecycleGridDivider extends RecyclerView.ItemDecoration {

        /**
         * 分割线宽度
         */
        private int space;


        public RecycleGridDivider(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            int span = manager.getSpanCount();
            //为了Item大小均匀，将设定分割线平均分给左右两边Item各一半
            int offset = space / 2;
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
                    outRect.set(0, space, offset, 0);
                } else if (childPosition % span == span - 1) {
                    outRect.set(offset, space, 0, 0);
                } else {
                    outRect.set(offset, space, offset, 0);
                }
            }
        }

    }

    /**
     * 设置按键点击事件
     */
    public void setOnKeyClickListener(OnKeyClickListener listener) {
        this.onKeyClickListener = listener;
    }

    public interface OnKeyClickListener {

        void onKeyClick(String key);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
