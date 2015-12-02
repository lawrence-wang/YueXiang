package app.nexd.com.androidTeam.mode;

/**
 * 城市属性实体类
 *
 * @author gugalor
 */
public class CityModel {
    private String cityName; //城市名字
    private String nameSort; //城市首字母
    private String cityCode; // 城市编号

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

}
