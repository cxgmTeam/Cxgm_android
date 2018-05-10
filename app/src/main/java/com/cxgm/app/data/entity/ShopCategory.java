package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

import java.util.List;

/**
 * 商品分类
 *
 * @author dean
 * @time 2018/5/10 上午11:05
 */
public class ShopCategory extends BaseEntity {


    /**
     * id : 0
     * name : string
     * shopCategoryList : [{}]
     */

    private int id;
    private String name;
    private List<ShopCategory> shopCategoryList;

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

    public List<ShopCategory> getShopCategoryList() {
        return shopCategoryList;
    }

    public void setShopCategoryList(List<ShopCategory> shopCategoryList) {
        this.shopCategoryList = shopCategoryList;
    }

}
