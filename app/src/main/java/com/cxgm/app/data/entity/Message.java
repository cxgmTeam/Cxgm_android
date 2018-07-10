package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;
import com.umeng.message.entity.UMessage;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 推送消息
 *
 * @author dean
 * @time 2018/6/25 下午2:25
 */
@Table(name = "Message")
public class Message extends BaseEntity {

    public static final String TYPE_PROMOTION = "限时抢购";
    public static final String TYPE_SERVICE = "客服助手";
    public static final String TYPE_NOTIFICATION = "通知消息";
    public static final String TYPE_NEWS = "最新资讯";

    @Column(name = "id",isId = true)
    int id;
    @Column(name = "title")
    String title;
    @Column(name = "content")
    String content;

    /**
     * time : 2018-07-06 18:33:13
     * type : 0
     * goodcode : 09869
     */

    @Column(name = "time")
    private String time;
    @Column(name = "type")
    private String type;//type为0时跳转商品详情，type为1时直接打开H5连接
    @Column(name = "goodcode")
    private String goodcode;
    @Column(name = "shopId")
    private String shopId;


    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoodcode() {
        return goodcode;
    }

    public void setGoodcode(String goodcode) {
        this.goodcode = goodcode;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
