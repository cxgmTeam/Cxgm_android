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
    private String image;//TODO 需要增加图片字段

    public ShopCategory() {
    }

    public ShopCategory(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
