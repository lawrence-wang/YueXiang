package app.nexd.com.androidTeam.mode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lawrence on 2015/11/12.
 */
public class MallModel implements Parcelable {

    private String id;
    private String name;
    private String openTime;
    private String closeTime;
    private String url;
    private String picUrl;
    private String star;
    private String addr;
    private String tel;
    private double lat;
    private double lng;
//    private List<FloorInfo> floors;

    protected MallModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        openTime = in.readString();
        closeTime = in.readString();
        url = in.readString();
        picUrl = in.readString();
        star = in.readString();
        addr = in.readString();
        tel = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public MallModel() {
    }

    @Override
    public String toString() {
        return "MallInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", openTime='" + openTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", url='" + url + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", star='" + star + '\'' +
                ", addr='" + addr + '\'' +
                ", tel='" + tel + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }

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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public static final Creator<MallModel> CREATOR = new Creator<MallModel>() {
        @Override
        public MallModel createFromParcel(Parcel in) {
            return new MallModel(in);
        }

        @Override
        public MallModel[] newArray(int size) {
            return new MallModel[size];
        }
    };

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(openTime);
        dest.writeString(closeTime);
        dest.writeString(url);
        dest.writeString(picUrl);
        dest.writeString(star);
        dest.writeString(addr);
        dest.writeString(tel);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }
}
