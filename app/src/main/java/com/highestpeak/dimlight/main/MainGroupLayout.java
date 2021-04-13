package com.highestpeak.dimlight.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.highestpeak.dimlight.R;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;
import com.mikepenz.iconics.view.IconicsTextView;

import lombok.Data;

/**
 * 列表项: 图标 标题 数量
 *
 * @author highestpeak
 */
@Data
public class MainGroupLayout {
    private final View groupLayout;
    private IconicsImageView iconImageView;
    private IconicsTextView nameTextView;
    private IconicsTextView countTextView;

    /**
     * 超过该数量则显示为 MAX_COUNT_FULL_SHOW+
     */
    private static final int MAX_COUNT_FULL_SHOW = 99;
    private static final String MAX_COUNT_FULL_SHOW_TEXT = MAX_COUNT_FULL_SHOW + "+";

    public MainGroupLayout(ConstraintLayout layout) {
        groupLayout = layout;
        assignView();
    }

    public MainGroupLayout(LayoutInflater layoutInflater, ViewGroup root) {
        groupLayout = layoutInflater.inflate(R.layout.main_group_layout, root, false);
        assignView();
    }

    private void assignView(){
        iconImageView = (IconicsImageView) groupLayout.findViewById(R.id.group_item_icon);
        nameTextView = (IconicsTextView) groupLayout.findViewById(R.id.group_item_name);
        countTextView = (IconicsTextView) groupLayout.findViewById(R.id.group_item_count);
        setDefault();
    }

    private void setDefault() {
        countTextView.setText("");
    }

    public MainGroupLayout icon(IconicsDrawable icon) {
        iconImageView.setIcon(icon);
        return this;
    }

    public MainGroupLayout name(String name) {
        nameTextView.setText(name);
        // feature: 字体
        return this;
    }

    public MainGroupLayout count(int count) {
        if (count >= MAX_COUNT_FULL_SHOW) {
            countTextView.setText(MAX_COUNT_FULL_SHOW_TEXT);
        } else {
            countTextView.setText(String.valueOf(count));
        }
        // feature: color
        return this;
    }

    public MainGroupLayout clickListener(View.OnClickListener clickListener){
        groupLayout.setOnClickListener(clickListener);
        return this;
    }

    public View getView() {
        groupLayout.setId(View.generateViewId());
        return groupLayout;
    }
}
