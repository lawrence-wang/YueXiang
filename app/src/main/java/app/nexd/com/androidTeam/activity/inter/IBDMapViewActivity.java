package app.nexd.com.androidTeam.activity.inter;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MyLocationData;

import java.util.List;

import app.nexd.com.androidTeam.mode.MallModel;

/**
 * Created by lawrence on 2015/11/10.
 */
public interface IBDMapViewActivity extends IBaseActivity {


    void setLocationDataAndUpdate(MyLocationData locationData, MapStatusUpdate mapStatusUpdate);

    void setMallOverlay(List<MallModel> mallModels);
}
