package app.nexd.com.androidTeam.mode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 城市属性实体类
 *
 * @author gugalor
 */
public class CityModel implements Parcelable {
    private String cityName; //城市名字
    private String nameSort; //城市首字母
    private String cityCode; // 城市编号
    private String cityEname; // 城市英文名

    public CityModel() {

    }

    protected CityModel(Parcel in) {
        cityName = in.readString();
        nameSort = in.readString();
        cityCode = in.readString();
        cityEname = in.readString();
    }

    public static final Creator<CityModel> CREATOR = new Creator<CityModel>() {
        @Override
        public CityModel createFromParcel(Parcel in) {
            return new CityModel(in);
        }

        @Override
        public CityModel[] newArray(int size) {
            return new CityModel[size];
        }
    };

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getNameSort() {
        return nameSort;
    }

    public void setNameSort(String nameSort) {
        this.nameSort = nameSort;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityCode() {
        return this.cityCode;
    }

    public String getCityEname() {
        return cityEname;
    }

    public void setCityEname(String cityEname) {
        this.cityEname = cityEname;
        String nameSort = this.cityEname.toUpperCase().substring(0, 1);
        setNameSort(nameSort);
    }

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
        dest.writeString(cityName);
        dest.writeString(nameSort);
        dest.writeString(cityCode);
        dest.writeString(cityEname);
    }
}
