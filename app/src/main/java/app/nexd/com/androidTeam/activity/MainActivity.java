package app.nexd.com.androidTeam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;

import app.nexd.com.androidTeam.R;
import app.nexd.com.androidTeam.activity.inter.IMainActivity;
import app.nexd.com.androidTeam.presenter.IMainPresenterImpl;
import app.nexd.com.androidTeam.presenter.inter.IMainPresenter;

public class MainActivity extends BaseActivity implements IMainActivity {
    private MapView mMapView;
    private IMainPresenter iMainPresenter;
    private BaiduMap baiduMap;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = (MapView) findViewById(R.id.bmapView);
        iMainPresenter = new IMainPresenterImpl(getApplicationContext(), this, mMapView);

        baiduMap = mMapView.getMap();
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        // 设置自定义定位点的样式， 传null进去为回复默认
//        mCurrentMarker = BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_geo);
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
        baiduMap.setMyLocationEnabled(true); // 开启定位图层
        iMainPresenter.initBDMap();
        iMainPresenter.startLocation();
    }


    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        iMainPresenter.stopLocation();
        // 关闭定位图层
        baiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        iMainPresenter.stopLocation();
        super.onStop();
    }

    public void goToNext(View view) {
        Intent intent = new Intent(this, RoutePlanToNevigActivity.class);
        startActivity(intent);
    }

    @Override
    public void setLocationDataAndUpdate(MyLocationData locationData, MapStatusUpdate mapStatusUpdate) {
        baiduMap.setMyLocationData(locationData);
        baiduMap.animateMapStatus(mapStatusUpdate);
    }
}
