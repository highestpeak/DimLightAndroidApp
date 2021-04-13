package com.highestpeak.dimlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.webkit.WebView;
import android.widget.TextView;

import com.highestpeak.dimlight.data.entity.FeedItemBean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author highestpeak
 */
public class FeedReadActivity extends AppCompatActivity {

    private FeedIntentExtraParam intentExtraParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_read);

        Intent intent = getIntent();
        intentExtraParam = (FeedIntentExtraParam) intent.getParcelableExtra(FeedIntentExtraParam.KEY_NAME);

        getGroupData();
        showGroupData();
    }

    private void getGroupData() {

    }

    private void showGroupData() {
        WebView tmpWebView = findViewById(R.id.tmp_webview);
        tmpWebView.loadUrl(intentExtraParam.getLink());
    }

    @NoArgsConstructor
    @Data
    public static class FeedIntentExtraParam implements Parcelable {

        public static final String KEY_NAME = "FeedIntentExtraParam";

        private Integer id;
        private String createTime;
        private String updateTime;
        private String titleParse;
        private String descParse;
        private String link;
        private String guid;
        private String pubDate;
        private String author;
        private String jsonOptionalExtraFields;
        private Integer rssId;
        private String sourceName;

        protected FeedIntentExtraParam(Parcel in) {
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            createTime = in.readString();
            updateTime = in.readString();
            titleParse = in.readString();
            descParse = in.readString();
            link = in.readString();
            guid = in.readString();
            pubDate = in.readString();
            author = in.readString();
            jsonOptionalExtraFields = in.readString();
            if (in.readByte() == 0) {
                rssId = null;
            } else {
                rssId = in.readInt();
            }
            sourceName = in.readString();
        }

        public static final Creator<FeedIntentExtraParam> CREATOR = new Creator<FeedIntentExtraParam>() {
            @Override
            public FeedIntentExtraParam createFromParcel(Parcel in) {
                return new FeedIntentExtraParam(in);
            }

            @Override
            public FeedIntentExtraParam[] newArray(int size) {
                return new FeedIntentExtraParam[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (id == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(id);
            }
            dest.writeString(createTime);
            dest.writeString(updateTime);
            dest.writeString(titleParse);
            dest.writeString(descParse);
            dest.writeString(link);
            dest.writeString(guid);
            dest.writeString(pubDate);
            dest.writeString(author);
            dest.writeString(jsonOptionalExtraFields);
            if (rssId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(rssId);
            }
            dest.writeString(sourceName);
        }

        public void parseFeedItemBean(FeedItemBean feedItemBean){
            this.id = feedItemBean.getId();
            this.createTime = feedItemBean.getCreateTime();
            this.updateTime = feedItemBean.getUpdateTime();
            this.titleParse = feedItemBean.getTitleParse();
            this.descParse = feedItemBean.getDescParse();
            this.link = feedItemBean.getLink();
            this.guid = feedItemBean.getGuid();
            this.pubDate = feedItemBean.getPubDate();
            this.author = feedItemBean.getAuthor();
            this.jsonOptionalExtraFields = feedItemBean.getJsonOptionalExtraFields();
            this.rssId = feedItemBean.getRssId();
            this.sourceName = feedItemBean.getSourceName();
        }
    }
}