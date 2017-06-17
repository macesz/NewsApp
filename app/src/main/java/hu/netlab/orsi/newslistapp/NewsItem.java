package hu.netlab.orsi.newslistapp;

import java.util.Date;

public class NewsItem {

    private String mTitle;
    private String mSectionName;
    private Date mPublishedDate;
    private String mUrl;
    private String mThumbnail;
    private String mContentText;

    public NewsItem(String title, String section_name, Date published_date, String url, String thumbnail, String content_text) {
        mTitle = title;
        mSectionName = section_name;
        mPublishedDate = published_date;
        mUrl = url;
        mThumbnail = thumbnail;
        mContentText = content_text;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public Date getPublishedDate() {
        return mPublishedDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public String getContentText() {
        return mContentText;
    }

}
