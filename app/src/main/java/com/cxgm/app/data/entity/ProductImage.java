package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

public class ProductImage extends BaseEntity {


    /**
     * del : true
     * id : 0
     * name : string
     * url : string
     */

    private boolean del;
    private int id;
    private String name;
    private String url;

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
