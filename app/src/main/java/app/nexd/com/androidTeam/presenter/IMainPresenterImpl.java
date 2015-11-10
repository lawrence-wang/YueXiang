package app.nexd.com.androidTeam.presenter;

import android.content.Context;
import android.graphics.Point;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import app.nexd.com.androidTeam.activity.inter.IMainActivity;
import app.nexd.com.androidTeam.presenter.inter.IMainPresenter;

/**
 * Created by lawrence on 2015/11/10.
 */
public class IMainPresenterImpl implements IMainPresenter {
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = null;
    private boolean isFirstLocation = true;
    private IMainActivity iMainActivity;
    private MapView mMapView;


    public IMainPresenterImpl(Context context, IMainActivity iMainActivity, MapView mMapView) {
        this.iMainActivity = iMainActivity;
        this.mMapView = mMapView;
        mLocationClient = new LocationClient(context);     //声明LocationClient类
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
    }

    @Override
    public void initBDMap() {
        initLocation();
    }

    @Override
    public void startLocation() {
        mLocationClient.start();
    }

    @Override
    public void stopLocation() {
        mLocationClient.stop();
    }

    @Override
    public void routePlan() {

    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 3000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }


    private class MyLocationListener implements BDLocationListener {


        MapStatusUpdate u = null;

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null) {
                return;
            }
            mMapView.setZoomControlsPosition(new Point((int) location.getLatitude(), (int) location.getLongitude()));
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            if (isFirstLocation) {
                isFirstLocation = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                u = MapStatusUpdateFactory.newLatLng(ll);
            }
            iMainActivity.setLocationDataAndUpdate(locData, u);
        }
    }
}