package com.cxgm.app.data.entity;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;

/**
 * POI  需要需要加参数，所以做了继承
 *
 * @anthor Dean
 * @time 2018/5/20 0020 14:42
 */
public class UserPoiInfo extends PoiInfo {
    public boolean isChecked;
    public UserPoiInfo(PoiInfo info){
        name = info.name;
        uid = info.uid;
        address = info.address;
        city = info.city;
        phoneNum = info.phoneNum;
        postCode = info.postCode;
        type = info.type;
        location = info.location;
        hasCaterDetails = info.hasCaterDetails;
        isPano = info.isPano;
    }

    public UserPoiInfo(BDLocation location) {
        if (location != null) {
            name = location.getBuildingName();
            address = location.getDistrict() + location.getStreet() + location.getLocationDescribe();
            city = location.getCity();
            this.location = new LatLng(location.getLatitude(),location.getLongitude());
        }
    }

    public UserPoiInfo(UserAddress address){
        if (address!= null){
            name = address.getRealName();
            this.address = address.getArea() + address.getAddress();
            //city = address.getArea();
            location = new LatLng(Double.valueOf(address.getDimension()),Double.valueOf(address.getLongitude()));
        }
    }
}
