package app.nexd.com.androidTeam.activity.inter;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MyLocationData;

/**
 * Created by lawrence on 2015/11/10.
 */
public interface IMainActivity extends IBaseActivity {


    void setLocationDataAndUpdate(MyLocationData locationData, MapStatusUpdate mapStatusUpdate);
}
