package com.highestpeak.dimlight.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.highestpeak.dimlight.R;

public class MainAccountHeaderLayout {

    private final View headerLayout;

    private ImageView accountAvatar;
    private TextView accountName;
    private TextView accountServerView;
    private ImageView accountSearchButtonView;
    private TextView accountRecentlySyncView;
    private ImageView accountSyncNowView;

    public MainAccountHeaderLayout(ConstraintLayout layout) {
        headerLayout = layout;
        assignView();
        assignData();
    }

    private void assignView() {
        accountAvatar = (ImageView)headerLayout.findViewById(R.id.account_avatar);
        accountName = (TextView)headerLayout.findViewById(R.id.account_name);
        accountServerView = (TextView)headerLayout.findViewById(R.id.account_server);
        accountSearchButtonView = (ImageView)headerLayout.findViewById(R.id.account_search_button);
        accountRecentlySyncView = (TextView)headerLayout.findViewById(R.id.account_recently_sync);
        accountSyncNowView = (ImageView)headerLayout.findViewById(R.id.account_sync_now);
    }

    private void assignData() {
        Context context = headerLayout.getContext();
        accountAvatar.setImageResource(R.drawable.luoxiaohei_avator);
        accountName.setText(context.getString(R.string.default_account_name));
        accountServerView.setText(context.getString(R.string.default_account_server_name));
        accountRecentlySyncView.setText(context.getString(R.string.default_recent_sync_desc));
    }
}
