package com.highestpeak.dimlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.consecutivescroller.ConsecutiveScrollerLayout;
import com.highestpeak.dimlight.data.TopicItemGetHelp;
import com.highestpeak.dimlight.data.WebHelp;
import com.highestpeak.dimlight.data.WebService;
import com.highestpeak.dimlight.data.entity.FeedItemBean;
import com.highestpeak.dimlight.data.entity.FeedListBean;
import com.highestpeak.dimlight.feeds.FeedItemTextImageLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author highestpeak
 */
public class FeedListActivity extends AppCompatActivity {
    public static final String TAG = "FeedListActivity";

    private IntentExtraParam intentExtraParam;
    private TextView feedListNameView;
    private TextView feedListDescView;
    private ConsecutiveScrollerLayout feedListContainerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);

        Intent intent = getIntent();
        intentExtraParam = (IntentExtraParam) intent.getParcelableExtra(IntentExtraParam.KEY_NAME);

        getGroupData();
        showGroupData();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private List<FeedItemBean> feedItemBeans;

    private void getGroupData() {
        // todo: 后端设一个limit只传过来例如40条
        WebHelp.asyncDataGet(() -> {
            WebService.FailCallback failCallback = data -> {
                Toast.makeText(FeedListActivity.this, "拉取数据失败", Toast.LENGTH_SHORT).show();
            };

            WebService.SucceedCallback succeedCallback = data -> {
                FeedListBean feedListBean = (FeedListBean) data;
                feedItemBeans = feedListBean.getFeedItemBeans();
                fillUiData();
            };
            TopicItemGetHelp topicItemGetHelp = new TopicItemGetHelp();
            topicItemGetHelp.getTopicItemList(succeedCallback, failCallback);
        });
    }

    private static final DateTimeFormatter PUB_DATE_IN_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    private static final DateTimeFormatter PUB_DATE_OUT_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private void fillUiData() {
        Context context = getBaseContext();
        LayoutInflater layoutInflater = getLayoutInflater();
        //feedListContainerLayout.getRootView().post(()-> feedListContainerLayout.removeAllViews());
        Looper.prepare();
        Collections.shuffle(feedItemBeans);
        for (FeedItemBean feedItemBean : feedItemBeans) {
            FeedItemTextImageLayout sample = new FeedItemTextImageLayout(layoutInflater, feedListContainerLayout);
            sample.sourceName(feedItemBean.getSourceName());
            sample.title(feedItemBean.getTitleParse());
            // parse desc
            Document doc = Jsoup.parse(feedItemBean.getDescParse());
            sample.abstractDesc(doc.text());
            try {
                Elements imgs = doc.getElementsByTag("img");
                if (imgs != null && imgs.size() > 0) {
                    Element element = imgs.get(0);
                    String src = element.attributes().get("src");
                    if (src != null && src.length() > 0) {
                        sample.image(src);
                    }
                }
            } catch (Exception e) {
                Log.i("fail to get img src", TAG);
            }
            try {
                LocalDateTime dateTime = LocalDateTime.parse(feedItemBean.getPubDate(), PUB_DATE_IN_FORMAT);
                sample.time(dateTime.format(PUB_DATE_OUT_FORMAT));
            } catch (Exception e){
                Log.i("fail to get right time", TAG);
                sample.time("??:??");
            }
            sample.surfaceItemClickListener(v -> {
                Intent intent = new Intent(FeedListActivity.this, FeedReadActivity.class);
                // todo: put data here
                FeedReadActivity.FeedIntentExtraParam feedIntentExtraParam = new FeedReadActivity.FeedIntentExtraParam();
                feedIntentExtraParam.parseFeedItemBean(feedItemBean);
                intent.putExtra(FeedReadActivity.FeedIntentExtraParam.KEY_NAME, feedIntentExtraParam);
                startActivity(intent);
            });
            View.OnClickListener sampleListener = v -> {
                Toast.makeText(FeedListActivity.this, "点击", Toast.LENGTH_SHORT).show();
            };
            sample.delClickListener(sampleListener);
            sample.inboxClickListener(sampleListener);
            sample.starClickListener(sampleListener);
            feedListContainerLayout.getRootView().post(() -> feedListContainerLayout.addView(sample.getView()));
        }
    }

    private void showGroupData() {
        LayoutInflater layoutInflater = getLayoutInflater();
        feedListNameView = findViewById(R.id.feed_list_name);
        feedListDescView = findViewById(R.id.feed_list_desc);
        feedListNameView.setText(intentExtraParam.groupName);
        feedListDescView.setText(intentExtraParam.groupDesc);

        // feed list
        feedListContainerLayout = findViewById(R.id.feed_list_view);
        ConsecutiveScrollerLayout.LayoutParams layoutParams = new ConsecutiveScrollerLayout.LayoutParams(0, 20);

        FeedItemTextImageLayout sample = new FeedItemTextImageLayout(layoutInflater, feedListContainerLayout);
        // build view list according to getGroupData()'s data got
        sample.sourceName("知乎");
        sample.title("如何看待中美两国在阿拉斯加的对话");
        sample.abstractDesc("~~~~没啥好看待的啊~~~~");
        sample.time("11:11");
        sample.surfaceItemClickListener(v -> {
            Intent intent = new Intent(FeedListActivity.this, FeedReadActivity.class);
            startActivity(intent);
        });
        View.OnClickListener sampleListener = v -> {
            Toast.makeText(FeedListActivity.this, "点击", Toast.LENGTH_SHORT).show();
        };
        sample.delClickListener(sampleListener);
        sample.inboxClickListener(sampleListener);
        sample.starClickListener(sampleListener);
        feedListContainerLayout.addView(sample.getView());
    }

    @NoArgsConstructor
    @Data
    public static class IntentExtraParam implements Parcelable {
        public static final String KEY_NAME = "FeedListActivityParam";
        private String groupName;
        private String groupDesc;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.groupName);
            dest.writeString(this.groupDesc);
        }

        public void readFromParcel(Parcel source) {
            this.groupName = source.readString();
            this.groupDesc = source.readString();
        }

        protected IntentExtraParam(Parcel in) {
            this.groupName = in.readString();
            this.groupDesc = in.readString();
        }

        public static final Creator<IntentExtraParam> CREATOR = new Creator<IntentExtraParam>() {
            @Override
            public IntentExtraParam createFromParcel(Parcel source) {
                return new IntentExtraParam(source);
            }

            @Override
            public IntentExtraParam[] newArray(int size) {
                return new IntentExtraParam[size];
            }
        };
    }
}