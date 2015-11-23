package app.nexd.com.androidTeam.mode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lawrence on 2015/11/12.
 */
public class MallInfo implements Serializable {

    private String id;
    private String name;
    private String openTime;
    private String closeTime;
    private String url;
    private String picUrl;
    private String star;
    private String addr;
    private String tel;
    private String lat;
    private String lng;
    private List<FloorInfo> floors;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public List<FloorInfo> getFloors() {
        return floors;
    }

    public void setFloors(List<FloorInfo> floors) {
        this.floors = floors;
    }
}
