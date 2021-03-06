package com.highestpeak.dimlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.donkingliang.consecutivescroller.ConsecutiveScrollerLayout;
import com.google.common.collect.Maps;
import com.highestpeak.dimlight.data.TopicGetHelp;
import com.highestpeak.dimlight.data.WebHelp;
import com.highestpeak.dimlight.data.WebService;
import com.highestpeak.dimlight.data.entity.FeedListBean;
import com.highestpeak.dimlight.data.entity.RssSourceCountBean;
import com.highestpeak.dimlight.data.entity.RssSourceJsonBean;
import com.highestpeak.dimlight.data.entity.TopicInfoBean;
import com.highestpeak.dimlight.data.entity.TopicRssListBean;
import com.highestpeak.dimlight.main.MainAccountHeaderLayout;
import com.highestpeak.dimlight.main.MainGroupLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.Map;
import java.util.Objects;

/**
 * @author highestpeak
 */
public class MainActivity extends AppCompatActivity {

    private Map<String, MainGroupLayout> topGroupMap;
    private Map<String, MainGroupLayout> topicGroupMap;

    private ConsecutiveScrollerLayout feedGroupContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initView() {
        Context context = getBaseContext();

        // header
        MainAccountHeaderLayout mainAccountHeaderLayout = new MainAccountHeaderLayout(findViewById(R.id.main_account_header));

        // top and topic group
        feedGroupContainer = findViewById(R.id.main_top_group_recycler_view);
        updateTopGroupMap();
        feedGroupContainer.addView(newDivideLine());
        updateTopicGroupMap();

        // ?????? ??????
        MainGroupLayout settingLayout = new MainGroupLayout(findViewById(R.id.main_bottom_setting))
                .icon(new IconicsDrawable(context, GoogleMaterial.Icon.gmd_settings))
                .name(getString(R.string.setting));
        MainGroupLayout aboutLayout = new MainGroupLayout(findViewById(R.id.main_bottom_about))
                .icon(new IconicsDrawable(context, GoogleMaterial.Icon.gmd_info))
                .name(getString(R.string.about));
    }


    private TopicRssListBean topicRssListBean;

    private void initData() {
        topGroupMap = Maps.newHashMap();
        topicGroupMap = Maps.newHashMap();

        /*
         * ??????rss???
         * ????????????????????????????????????rss?????????item
         * feature: ???????????????????????????????????????????????????
         * feature: network ???????????????????????????
         */
        WebHelp.asyncDataGet(() -> {
            WebService.FailCallback failCallback = data -> {
                Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
            };

            TopicGetHelp topicGetHelp = new TopicGetHelp();
            WebService.SucceedCallback topicSucceedCallback = data -> {
                topicRssListBean = (TopicRssListBean) data;
                fillUiData();
            };
            topicGetHelp.getTopicRssList(topicSucceedCallback, failCallback);
        });
    }

    //private Map<Integer, TopicInfoBean> topicMap;

    private void processRssSourceJsonBean(RssSourceJsonBean sourceJsonBean) {
        //// init topic id
        //topicMap = sourceJsonBean.getTopics();
        //topicMap.forEach((key, value) -> value.setId(key));
        //// get rss map
        //List<RssSourceInfoBean> sources = sourceJsonBean.getSources();
        //rssIdMap = Maps.newHashMapWithExpectedSize(sources.size());
        //sources.forEach(rssSourceInfoBean -> rssIdMap.put(rssSourceInfoBean.getId(), rssSourceInfoBean));
        //
        //topicWithItsRss = Maps.newHashMapWithExpectedSize(topicMap.size());
        //sourceJsonBean.getRssTopics().forEach((rssId, topicIds) -> {
        //    topicIds.forEach(topicId -> {
        //        if (!topicWithItsRss.containsKey(topicId)) {
        //            topicWithItsRss.put(topicId, Lists.newArrayList());
        //        }
        //        List<RssSourceInfoBean> rssList = topicWithItsRss.get(topicId);
        //        rssList.add(rssIdMap.get(rssId));
        //    });
        //});
    }

    //private Map<Integer, List<FeedItemBean>> rssWithItsFeeds;

    private void processFeedListBean(FeedListBean feedListBean) {
        ////System.out.println("test debug");
        //rssWithItsFeeds = Maps.newHashMapWithExpectedSize(rssIdMap.size());
        //List<FeedItemBean> feedItemBeans = feedListBean.getFeedItemBeans();
        //feedItemBeans.forEach(feedItemBean -> {
        //    if (!rssWithItsFeeds.containsKey(feedItemBean.getRssId())){
        //        rssWithItsFeeds.put(feedItemBean.getRssId(),Lists.newArrayList());
        //    }
        //    List<FeedItemBean> feedItemBeanList = rssWithItsFeeds.get(feedItemBean.getRssId());
        //    feedItemBeanList.add(feedItemBean);
        //});
        //// fill ui data
        //fillUiData();
    }

    private void fillUiData() {
        Context context = getBaseContext();
        LayoutInflater layoutInflater = getLayoutInflater();

        Map<Integer, RssSourceCountBean> rss = topicRssListBean.getRss();
        int totalFeedCount = rss.values().stream().mapToInt(RssSourceCountBean::getCount).sum();
        MainGroupLayout allFeedGroupLayout = topGroupMap.get(getString(R.string.all_feed));
        assert allFeedGroupLayout != null;
        allFeedGroupLayout.getCountTextView().post(() -> allFeedGroupLayout
                .count(totalFeedCount)
                .clickListener(feedGroupClickListener(
                        TopicInfoBean.builder().name("????????????").desc("???????????????????????????Feed").build()
                ))
        );

        Map<Integer, TopicInfoBean> topicMap = topicRssListBean.getTopics();
        topicMap.forEach((topicId, topic) -> {
            int topicItemCount = Objects.requireNonNull(topicRssListBean.getTopicRss().get(topicId))
                    .stream()
                    .mapToInt(integer -> Objects.requireNonNull(rss.get(integer)).getCount())
                    .sum();

            MainGroupLayout topicGroupLayout = topicGroupMap.get(topic.getName());
            if (topicGroupLayout == null) {
                topicGroupLayout = new MainGroupLayout(layoutInflater, feedGroupContainer)
                        // feature: ???????????????
                        .icon(new IconicsDrawable(context, GoogleMaterial.Icon.gmd_book))
                        .name(topic.getName())
                        .count(topicItemCount)
                        .clickListener(feedGroupClickListener(topic));
                topicGroupMap.put(topic.getName(), topicGroupLayout);
                View groupLayoutView = topicGroupLayout.getView();
                feedGroupContainer.getRootView().post(() -> feedGroupContainer.addView(groupLayoutView));
            } else {
                MainGroupLayout finalTopicGroupLayout = topicGroupLayout;
                topicGroupLayout.getCountTextView().post(() ->
                        finalTopicGroupLayout.count(topicItemCount).clickListener(feedGroupClickListener(topic))
                );
            }
        });

        // todo: ????????????????????? topic ??????
        // todo: feed??????????????????????????????????????????????????????
    }

    /**
     * ????????? getDivideLineParams ????????????
     */
    private static ConsecutiveScrollerLayout.LayoutParams divideLineParams;

    private static class DivideLockClass {
    }

    private static ConsecutiveScrollerLayout.LayoutParams getDivideLineParams() {
        if (divideLineParams == null) {
            synchronized (DivideLockClass.class) {
                if (divideLineParams == null) {
                    divideLineParams = new ConsecutiveScrollerLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, 1
                    );
                    divideLineParams.setMargins(16, 0, 16, 0);
                }
            }
        }
        return divideLineParams;
    }

    private View newDivideLine() {
        ConsecutiveScrollerLayout.LayoutParams layoutParams = getDivideLineParams();
        View view = new View(getBaseContext());
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(Color.GRAY);
        return view;
    }

    /**
     * setting??????????????????
     */
    private void updateTopGroupMap() {
        Context context = getBaseContext();
        LayoutInflater layoutInflater = getLayoutInflater();
        MainGroupLayout allFeeds = new MainGroupLayout(layoutInflater, feedGroupContainer)
                .icon(new IconicsDrawable(context, FontAwesome.Icon.faw_stream))
                .name(getString(R.string.all_feed))
                .clickListener(defaultFeedGroupClickListener(getString(R.string.all_feed)))
                .count(0);
        MainGroupLayout starFeeds = new MainGroupLayout(layoutInflater, feedGroupContainer)
                .icon(new IconicsDrawable(context, GoogleMaterial.Icon.gmd_star_border))
                .name(getString(R.string.star_feed))
                .clickListener(defaultFeedGroupClickListener(getString(R.string.star_feed)))
                .count(0);
        MainGroupLayout inboxFeeds = new MainGroupLayout(layoutInflater, feedGroupContainer)
                .icon(new IconicsDrawable(context, GoogleMaterial.Icon.gmd_inbox))
                .name(getString(R.string.inbox_feed))
                .clickListener(defaultFeedGroupClickListener(getString(R.string.inbox_feed)))
                .count(0);
        topGroupMap.put(getString(R.string.all_feed), allFeeds);
        topGroupMap.put(getString(R.string.star_feed), starFeeds);
        topGroupMap.put(getString(R.string.inbox_feed), inboxFeeds);
        topGroupMap.values().forEach(mainGroupLayout -> feedGroupContainer.addView(mainGroupLayout.getView()));
    }

    /**
     * ??????????????????????????????
     */
    private void updateTopicGroupMap() {
        Context context = getBaseContext();
        LayoutInflater layoutInflater = getLayoutInflater();
        MainGroupLayout dailyFeeds = new MainGroupLayout(layoutInflater, feedGroupContainer)
                .icon(new IconicsDrawable(context, GoogleMaterial.Icon.gmd_wb_sunny))
                .name("????????????")
                .count(0);
        topicGroupMap.put("????????????", dailyFeeds);
        topicGroupMap.values().forEach(mainGroupLayout -> feedGroupContainer.addView(mainGroupLayout.getView()));
    }

    private View.OnClickListener defaultFeedGroupClickListener(String groupName) {
        return v -> {
            Intent myIntent = new Intent(MainActivity.this, FeedListActivity.class);
            FeedListActivity.IntentExtraParam intentExtraParam = new FeedListActivity.IntentExtraParam();
            intentExtraParam.setGroupName(groupName);
            myIntent.putExtra(FeedListActivity.IntentExtraParam.KEY_NAME, intentExtraParam); //Optional parameters
            // MainActivity.this.startActivity(myIntent);
            startActivity(myIntent);
            // feature: ???????????????????????????
        };
    }

    private View.OnClickListener feedGroupClickListener(TopicInfoBean topicInfoBean) {
        return v -> {
            Intent myIntent = new Intent(MainActivity.this, FeedListActivity.class);
            FeedListActivity.IntentExtraParam intentExtraParam = new FeedListActivity.IntentExtraParam();
            intentExtraParam.setGroupName(topicInfoBean.getName());
            intentExtraParam.setGroupDesc(topicInfoBean.getDesc());
            myIntent.putExtra(FeedListActivity.IntentExtraParam.KEY_NAME, intentExtraParam); //Optional parameters
            // MainActivity.this.startActivity(myIntent);
            startActivity(myIntent);
        };
    }
}