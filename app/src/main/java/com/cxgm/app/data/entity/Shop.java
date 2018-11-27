package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

/**
 * 商铺
 *
 * @author dean
 * @time 2018/5/8 上午11:57
 */
public class Shop extends BaseEntity {


    /**
     * id : 7
     * shopName : 菜鲜果美科荟路店
     * shopAddress : 北京市朝阳区林萃西里16号华创生活广场地下一层菜鲜果美
     * longitude : 116.379727
     * dimension : 40.016128
     * imageUrl : http://cxgm.oss-cn-beijing.aliyuncs.com/data/1534324607502.jpg?Expires=1849684597&OSSAccessKeyId=LTAItzO0XhCesKeP&Signature=6swmw/r3Uj5F951rwRaeCA7Mhco%3D
     * description : 商超
     * owner : 万圣
     * electronicFence : 116.372476_40.057155,116.362128_40.054394,116.344162_40.052185,116.360834_40.028764,116.344449_40.028653,116.343587_40.028211,116.344736_40.020476,116.339275_40.020255,116.343874_40.016056,116.344449_40.006329,116.346749_40.006329,116.347324_39.991959,116.397054_39.994391,116.418613_39.994833,116.42465_39.995054,116.42465_40.005224,116.42465_40.010972,116.424362_40.015614,116.399928_40.016498,116.382968_40.016277,116.381531_40.028874,116.382968_40.030642,116.379806_40.038818,116.372907_40.056714,116.372925_40.0567,116.372485_40.057155
     * weixinMchid : null
     * weixinApikey : null
     * aliPartnerid : null
     * aliPrivatekey : 暂无
     * createTime : 2018-07-06 16:39:25
     * needTime : 24
     * distance : 6180
     * monthSales : 8590
     * distributionCosts : 5
     * startDistributionCosts : 25
     * score : 0
     * industryType : null
     */

    private int id;
    private String shopName;
    private String shopAddress;
    private String longitude;
    private String dimension;
    private String imageUrl;
    private String description;
    private String owner;
    private String electronicFence;
    private Object weixinMchid;
    private Object weixinApikey;
    private Object aliPartnerid;
    private String aliPrivatekey;
    private String createTime;
    private int needTime;//配送时间 分钟
    private float distance;//距离 千米
    private int monthSales;//月销
    private float distributionCosts;//配送费
    private float startDistributionCosts;//起送金额
    private float score;//评分
    private Object industryType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getElectronicFence() {
        return electronicFence;
    }

    public void setElectronicFence(String electronicFence) {
        this.electronicFence = electronicFence;
    }

    public Object getWeixinMchid() {
        return weixinMchid;
    }

    public void setWeixinMchid(Object weixinMchid) {
        this.weixinMchid = weixinMchid;
    }

    public Object getWeixinApikey() {
        return weixinApikey;
    }

    public void setWeixinApikey(Object weixinApikey) {
        this.weixinApikey = weixinApikey;
    }

    public Object getAliPartnerid() {
        return aliPartnerid;
    }

    public void setAliPartnerid(Object aliPartnerid) {
        this.aliPartnerid = aliPartnerid;
    }

    public String getAliPrivatekey() {
        return aliPrivatekey;
    }

    public void setAliPrivatekey(String aliPrivatekey) {
        this.aliPrivatekey = aliPrivatekey;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getNeedTime() {
        return needTime;
    }

    public void setNeedTime(int needTime) {
        this.needTime = needTime;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(int monthSales) {
        this.monthSales = monthSales;
    }

    public float getDistributionCosts() {
        return distributionCosts;
    }

    public void setDistributionCosts(float distributionCosts) {
        this.distributionCosts = distributionCosts;
    }

    public float getStartDistributionCosts() {
        return startDistributionCosts;
    }

    public void setStartDistributionCosts(float startDistributionCosts) {
        this.startDistributionCosts = startDistributionCosts;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Object getIndustryType() {
        return industryType;
    }

    public void setIndustryType(Object industryType) {
        this.industryType = industryType;
    }
}
