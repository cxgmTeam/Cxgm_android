package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

/**
 * 版本
 *
 * @author dean
 * @time 2018/6/25 下午2:25
 */
public class Version extends BaseEntity {


    /**
     * createTime : 2018-06-25T06:22:49.380Z
     * id : 0
     * url : string
     * versionNum : string
     */

    private String createTime;
    private int id;
    private String url;
    private String versionNum;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }
}
