package com.example.shenhaichen.mobileassistant.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shenhaichen on 02/01/2018.
 */

public class AppInfo implements Serializable{

    private int addTime;
    private boolean hasSameDevApp;
    private int videoId;
    private String source;
    private String versionName;
    private HdIconEntity hdIcon;
    private Float ratingScore;
    private String briefShow;
    private int developerId;
    private int fitness;
    private int id;
    private int level1CategoryId;
    private String releaseKeyHash;
    private boolean relateAppHasMore;
    private int rId;
    private int suitableType;
    private boolean briefUseIntro;
    private int ads;
    private String publisherName;
    private int level2CategoryId;
    private int position;
    private boolean favorite;
    private boolean isFavorite;
    private int appendSize;
    private List<AppInfo> relateAppInfoList;
    private String level1CategoryName;
    private boolean samDevAppHasMore;
    private String displayName;
    private String icon;
    private String changeLog;
    private String screenshot;
    private String permissionIds;
    private int ratingTotalCount;
    private int adType;
    private String web;
    private int apkSize;
    private String packageName;
    //    private List<AppInfo> appArticleInfoList;
    private String introduction;
    private String keyWords;
    private long updateTime;
    private int grantCode;
    private String detailHeaderImage;
    //    private List<AppInfo> moduleInfoList;
    private int versionCode;
    private List<Tag> appTags;
    private int diffFileSize;
    private List<AppInfo> sameDevAppInfoList;
    private String categoryId;
    private AppDownloadInfo mAppDownloadInfo;

    public AppDownloadInfo getAppDownloadInfo() {
        return mAppDownloadInfo;
    }

    public void setAppDownloadInfo(AppDownloadInfo appDownloadInfo) {
        mAppDownloadInfo = appDownloadInfo;
    }

    public int getAddTime() {
        return addTime;
    }

    public void setAddTime(int addTime) {
        this.addTime = addTime;
    }

    public boolean isHasSameDevApp() {
        return hasSameDevApp;
    }

    public void setHasSameDevApp(boolean hasSameDevApp) {
        this.hasSameDevApp = hasSameDevApp;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public HdIconEntity getHdIcon() {
        return hdIcon;
    }

    public void setHdIcon(HdIconEntity hdIcon) {
        this.hdIcon = hdIcon;
    }

    public Float getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(Float ratingScore) {
        this.ratingScore = ratingScore;
    }

    public String getBriefShow() {
        return briefShow;
    }

    public void setBriefShow(String briefShow) {
        this.briefShow = briefShow;
    }

    public int getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(int developerId) {
        this.developerId = developerId;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel1CategoryId() {
        return level1CategoryId;
    }

    public void setLevel1CategoryId(int level1CategoryId) {
        this.level1CategoryId = level1CategoryId;
    }

    public String getReleaseKeyHash() {
        return releaseKeyHash;
    }

    public void setReleaseKeyHash(String releaseKeyHash) {
        this.releaseKeyHash = releaseKeyHash;
    }

    public boolean isRelateAppHasMore() {
        return relateAppHasMore;
    }

    public void setRelateAppHasMore(boolean relateAppHasMore) {
        this.relateAppHasMore = relateAppHasMore;
    }

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public int getSuitableType() {
        return suitableType;
    }

    public void setSuitableType(int suitableType) {
        this.suitableType = suitableType;
    }

    public boolean isBriefUseIntro() {
        return briefUseIntro;
    }

    public void setBriefUseIntro(boolean briefUseIntro) {
        this.briefUseIntro = briefUseIntro;
    }

    public int getAds() {
        return ads;
    }

    public void setAds(int ads) {
        this.ads = ads;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public int getLevel2CategoryId() {
        return level2CategoryId;
    }

    public void setLevel2CategoryId(int level2CategoryId) {
        this.level2CategoryId = level2CategoryId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getAppendSize() {
        return appendSize;
    }

    public void setAppendSize(int appendSize) {
        this.appendSize = appendSize;
    }

    public List<AppInfo> getRelateAppInfoList() {
        return relateAppInfoList;
    }

    public void setRelateAppInfoList(List<AppInfo> relateAppInfoList) {
        this.relateAppInfoList = relateAppInfoList;
    }

    public String getLevel1CategoryName() {
        return level1CategoryName;
    }

    public void setLevel1CategoryName(String level1CategoryName) {
        this.level1CategoryName = level1CategoryName;
    }

    public boolean isSamDevAppHasMore() {
        return samDevAppHasMore;
    }

    public void setSamDevAppHasMore(boolean samDevAppHasMore) {
        this.samDevAppHasMore = samDevAppHasMore;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public String getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(String permissionIds) {
        this.permissionIds = permissionIds;
    }

    public int getRatingTotalCount() {
        return ratingTotalCount;
    }

    public void setRatingTotalCount(int ratingTotalCount) {
        this.ratingTotalCount = ratingTotalCount;
    }

    public int getAdType() {
        return adType;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public int getApkSize() {
        return apkSize;
    }

    public void setApkSize(int apkSize) {
        this.apkSize = apkSize;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getGrantCode() {
        return grantCode;
    }

    public void setGrantCode(int grantCode) {
        this.grantCode = grantCode;
    }

    public String getDetailHeaderImage() {
        return detailHeaderImage;
    }

    public void setDetailHeaderImage(String detailHeaderImage) {
        this.detailHeaderImage = detailHeaderImage;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public List<Tag> getAppTags() {
        return appTags;
    }

    public void setAppTags(List<Tag> appTags) {
        this.appTags = appTags;
    }

    public int getDiffFileSize() {
        return diffFileSize;
    }

    public void setDiffFileSize(int diffFileSize) {
        this.diffFileSize = diffFileSize;
    }

    public List<AppInfo> getSameDevAppInfoList() {
        return sameDevAppInfoList;
    }

    public void setSameDevAppInfoList(List<AppInfo> sameDevAppInfoList) {
        this.sameDevAppInfoList = sameDevAppInfoList;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public class  Tag implements Serializable{
        /**
         * tagId : 107
         * link : sametag/107
         * tagName : 二手
         */
        private int tagId;
        private String link;
        private String tagName;

        public void setTagId(int tagId) {
            this.tagId = tagId;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public int getTagId() {
            return tagId;
        }

        public String getLink() {
            return link;
        }

        public String getTagName() {
            return tagName;
        }
    }

    public class HdIconEntity implements Serializable {
        /**
         * main : AppStore/01712d4cde311460f2415c0d2cbd6f37212d405fc
         */
        private String main;

        public void setMain(String main) {
            this.main = main;
        }

        public String getMain() {
            return main;
        }
    }
}
