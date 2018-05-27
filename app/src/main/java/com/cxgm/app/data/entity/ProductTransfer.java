package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

import java.util.List;

public class ProductTransfer extends BaseEntity {


    /**
     * allocatedStock : 0
     * brandName : string
     * cid : 0
     * cname : string
     * cost : 0
     * createBy : string
     * creationDate : 2018-05-10T02:49:23.167Z
     * fullName : string
     * grade : 0
     * hits : 0
     * id : 0
     * image : string
     * introduction : string
     * isGift : true
     * isHot : 0
     * isList : true
     * isMarketable : true
     * isTop : true
     * lastUpdatedBy : string
     * lastUpdatedDate : 2018-05-10T02:49:23.167Z
     * marketPrice : 0
     * memo : string
     * monthHits : 0
     * monthHitsDate : 2018-05-10T02:49:23.167Z
     * monthSales : 0
     * monthSalesDate : 2018-05-10T02:49:23.167Z
     * name : string
     * originPlace : string
     * parentId : 0
     * point : 0
     * price : 0
     * productCategoryId : 0
     * productCategoryName : string
     * productCategoryThirdId : 0
     * productCategoryThirdName : string
     * productCategoryTwoId : 0
     * productCategoryTwoName : string
     * productImageList : []
     * sales : 0
     * score : 0
     * scoreCount : 0
     * shopId : 0
     * sn : string
     * stock : 0
     * stockMemo : string
     * storageCondition : string
     * totalScore : 0
     * unit : string
     * weekHits : 0
     * weekHitsDate : 2018-05-10T02:49:23.168Z
     * weekSales : 0
     * weekSalesDate : 2018-05-10T02:49:23.168Z
     * weight : 0
     */

    private int allocatedStock;
    private String brandName;
    private int cid;
    private String cname;
    private int cost;
    private String createBy;
    private String creationDate;
    private String fullName;
    private int grade;
    private int hits;
    private int id;
    private String image;
    private String introduction;//商品介绍图片
    private boolean isGift;
    private int isHot;
    private boolean isList;
    private boolean isMarketable;
    private boolean isTop;
    private String lastUpdatedBy;
    private String lastUpdatedDate;
    private int marketPrice;
    private String memo;
    private int monthHits;
    private String monthHitsDate;
    private int monthSales;
    private String monthSalesDate;
    private String name;
    private String originPlace;
    private int parentId;
    private int point;
    private float price;
    private int productCategoryId;
    private String productCategoryName;
    private int productCategoryThirdId;
    private String productCategoryThirdName;
    private int productCategoryTwoId;
    private String productCategoryTwoName;
    private int sales;
    private int score;
    private int scoreCount;
    private int shopId;
    private String sn;
    private int stock;
    private String stockMemo;
    private String storageCondition;
    private int totalScore;
    private String unit;
    private int weekHits;
    private String weekHitsDate;
    private int weekSales;
    private String weekSalesDate;
    private float weight;
    private List<ProductImage> productImageList;
    private String goodCode;
    private float originalPrice;
    private List<Promotion> promotionList;
    private int shopCartNum;//当前购物车数量
    private String warrantyPeriod;//保质期
    private int maximumQuantity;
    private int minimumQuantity;
    private int maximumPrice;
    private int minimumPrice;

    public int getMaximumQuantity() {
        return maximumQuantity;
    }

    public void setMaximumQuantity(int maximumQuantity) {
        this.maximumQuantity = maximumQuantity;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public int getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(int maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public int getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(int minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public int getShopCartNum() {
        return shopCartNum;
    }

    public void setShopCartNum(int shopCartNum) {
        this.shopCartNum = shopCartNum;
    }

    public List<Promotion> getPromotionList() {
        return promotionList;
    }

    public void setPromotionList(List<Promotion> promotionList) {
        this.promotionList = promotionList;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public int getAllocatedStock() {
        return allocatedStock;
    }

    public void setAllocatedStock(int allocatedStock) {
        this.allocatedStock = allocatedStock;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public boolean isIsGift() {
        return isGift;
    }

    public void setIsGift(boolean isGift) {
        this.isGift = isGift;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public boolean isIsList() {
        return isList;
    }

    public void setIsList(boolean isList) {
        this.isList = isList;
    }

    public boolean isIsMarketable() {
        return isMarketable;
    }

    public void setIsMarketable(boolean isMarketable) {
        this.isMarketable = isMarketable;
    }

    public boolean isIsTop() {
        return isTop;
    }

    public void setIsTop(boolean isTop) {
        this.isTop = isTop;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public int getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(int marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getMonthHits() {
        return monthHits;
    }

    public void setMonthHits(int monthHits) {
        this.monthHits = monthHits;
    }

    public String getMonthHitsDate() {
        return monthHitsDate;
    }

    public void setMonthHitsDate(String monthHitsDate) {
        this.monthHitsDate = monthHitsDate;
    }

    public int getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(int monthSales) {
        this.monthSales = monthSales;
    }

    public String getMonthSalesDate() {
        return monthSalesDate;
    }

    public void setMonthSalesDate(String monthSalesDate) {
        this.monthSalesDate = monthSalesDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public int getProductCategoryThirdId() {
        return productCategoryThirdId;
    }

    public void setProductCategoryThirdId(int productCategoryThirdId) {
        this.productCategoryThirdId = productCategoryThirdId;
    }

    public String getProductCategoryThirdName() {
        return productCategoryThirdName;
    }

    public void setProductCategoryThirdName(String productCategoryThirdName) {
        this.productCategoryThirdName = productCategoryThirdName;
    }

    public int getProductCategoryTwoId() {
        return productCategoryTwoId;
    }

    public void setProductCategoryTwoId(int productCategoryTwoId) {
        this.productCategoryTwoId = productCategoryTwoId;
    }

    public String getProductCategoryTwoName() {
        return productCategoryTwoName;
    }

    public void setProductCategoryTwoName(String productCategoryTwoName) {
        this.productCategoryTwoName = productCategoryTwoName;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(int scoreCount) {
        this.scoreCount = scoreCount;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getStockMemo() {
        return stockMemo;
    }

    public void setStockMemo(String stockMemo) {
        this.stockMemo = stockMemo;
    }

    public String getStorageCondition() {
        return storageCondition;
    }

    public void setStorageCondition(String storageCondition) {
        this.storageCondition = storageCondition;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getWeekHits() {
        return weekHits;
    }

    public void setWeekHits(int weekHits) {
        this.weekHits = weekHits;
    }

    public String getWeekHitsDate() {
        return weekHitsDate;
    }

    public void setWeekHitsDate(String weekHitsDate) {
        this.weekHitsDate = weekHitsDate;
    }

    public int getWeekSales() {
        return weekSales;
    }

    public void setWeekSales(int weekSales) {
        this.weekSales = weekSales;
    }

    public String getWeekSalesDate() {
        return weekSalesDate;
    }

    public void setWeekSalesDate(String weekSalesDate) {
        this.weekSalesDate = weekSalesDate;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public List<ProductImage> getProductImageList() {
        return productImageList;
    }

    public void setProductImageList(List<ProductImage> productImageList) {
        this.productImageList = productImageList;
    }
}
