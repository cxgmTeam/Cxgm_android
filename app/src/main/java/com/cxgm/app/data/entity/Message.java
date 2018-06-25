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
    @Column(name = "date")
    String date;

    public Message() {
    }

    public Message(UMessage uMessage) {
        title = uMessage.title;
        content = uMessage.custom;
        date = uMessage.text;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
