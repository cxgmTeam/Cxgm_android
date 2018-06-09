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
    private String imageUrl;//TODO 需要增加图片字段

    public ShopCategory() {
    }

    public ShopCategory(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public List<ShopCategory> getShopCategoryList() {
        return shopCategoryList;
    }

    public void setShopCategoryList(List<ShopCategory> shopCategoryList) {
        this.shopCategoryList = shopCategoryList;
    }

}
