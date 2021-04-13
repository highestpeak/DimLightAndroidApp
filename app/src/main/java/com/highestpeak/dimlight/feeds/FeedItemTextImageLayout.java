package com.highestpeak.dimlight.feeds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.donkingliang.consecutivescroller.ConsecutiveScrollerLayout;
import com.highestpeak.dimlight.R;

/**
 * @author highestpeak
 */
public class FeedItemTextImageLayout {
    private View globalView;
    private SwipeLayout swipeLayout;

    private ImageView feedItemImageView;
    private TextView feedItemSourceView;
    private TextView feedItemTimeView;
    private TextView feedItemTitleView;
    private TextView feedItemAbstractView;

    private ImageView delButton;
    private ImageView starButton;
    private ImageView inboxButton;

    public FeedItemTextImageLayout(LayoutInflater layoutInflater, ViewGroup root) {
        globalView = layoutInflater.inflate(R.layout.feed_item_text_image_view, root, false);
        assignView();
    }

    private static final ConsecutiveScrollerLayout.LayoutParams SWIPE_LAYOUT_PARAMS = new ConsecutiveScrollerLayout.LayoutParams(
            ConsecutiveScrollerLayout.LayoutParams.MATCH_PARENT,
            ConsecutiveScrollerLayout.LayoutParams.WRAP_CONTENT
    );

    private void assignView() {
        // swipeLayout view
        swipeLayout = globalView.findViewById(R.id.swipe_box);
        swipeLayout.setLayoutParams(SWIPE_LAYOUT_PARAMS);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        View leftDragView = swipeLayout.findViewById(R.id.left_wrapper);
        View rightDragView = swipeLayout.findViewById(R.id.right_wrapper);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, leftDragView);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, rightDragView);

        feedItemImageView = swipeLayout.findViewById(R.id.feed_item_image);
        feedItemSourceView = swipeLayout.findViewById(R.id.feed_item_source);
        feedItemTimeView = swipeLayout.findViewById(R.id.feed_item_time);
        feedItemTitleView = swipeLayout.findViewById(R.id.feed_item_title);
        feedItemAbstractView = swipeLayout.findViewById(R.id.feed_item_abstract);

        delButton = leftDragView.findViewById(R.id.mark_del_button);
        starButton = rightDragView.findViewById(R.id.mark_star_button);
        inboxButton = rightDragView.findViewById(R.id.mark_inbox_button);
    }

    public FeedItemTextImageLayout image(String url) {
        // todo: glide image loader
        feedItemImageView.post(()-> Glide.with(globalView.getContext())
                .load(url)
                .into(feedItemImageView));
        return this;
    }

    public FeedItemTextImageLayout sourceName(String sourceName) {
        feedItemSourceView.setText(sourceName);
        return this;
    }

    public FeedItemTextImageLayout time(String time) {
        feedItemTimeView.setText(time);
        return this;
    }

    public FeedItemTextImageLayout title(String title) {
        feedItemTitleView.setText(title);
        return this;
    }

    public FeedItemTextImageLayout abstractDesc(String abstractDesc) {
        feedItemAbstractView.setText(abstractDesc);
        return this;
    }

    public FeedItemTextImageLayout delClickListener(View.OnClickListener clickListener) {
        delButton.setOnClickListener(clickListener);
        return this;
    }

    public FeedItemTextImageLayout starClickListener(View.OnClickListener clickListener) {
        starButton.setOnClickListener(clickListener);
        return this;
    }

    public FeedItemTextImageLayout inboxClickListener(View.OnClickListener clickListener) {
        inboxButton.setOnClickListener(clickListener);
        return this;
    }

    public FeedItemTextImageLayout surfaceItemClickListener(View.OnClickListener clickListener) {
        swipeLayout.getSurfaceView().setOnClickListener(clickListener);
        return this;
    }

    /**
     * todo: 在这之前应该设置好 listener
     */
    public View getView() {
        globalView.setId(View.generateViewId());
        return globalView;
    }
}
