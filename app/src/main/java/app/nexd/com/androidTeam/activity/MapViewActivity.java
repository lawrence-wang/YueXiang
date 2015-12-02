package app.nexd.com.androidTeam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;

import app.nexd.com.androidTeam.R;
import app.nexd.com.androidTeam.activity.inter.IMainActivity;
import app.nexd.com.androidTeam.presenter.IMapViewPresenterImpl;
import app.nexd.com.androidTeam.presenter.inter.IMapViewPresenter;
import app.nexd.com.androidTeam.view.CityListActivity;

public class MapViewActivity extends Activity implements IMainActivity, View.OnClickListener {
    private MapView mMapView;
    private IMapViewPresenter iMapViewPresenter;
    private BaiduMap baiduMap;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;
    private TextView cityName;
    private RelativeLayout city_layout;
    PopupWindow popupWindow;
//    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_column);
        setContentView(R.layout.activity_main);
        cityName = (TextView) this.findViewById(R.id.city_name);
        city_layout = (RelativeLayout) this.findViewById(R.id.city_layout);
        city_layout.setOnClickListener(this);
        mMapView = (MapView) findViewById(R.id.bmapView);
        iMapViewPresenter = new IMapViewPresenterImpl(getApplicationContext(), this, mMapView);

        baiduMap = mMapView.getMap();
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        // 设置自定义定位点的样式， 传null进去为回复默认
//        mCurrentMarker = BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_geo);
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
        baiduMap.setMyLocationEnabled(true); // 开启定位图层
        iMapViewPresenter.initBDMap();
        iMapViewPresenter.startLocation();

//
//        LatLng point1 = new LatLng(39.92813694795621, 116.44467527125767);
//        LatLng point2 = new LatLng(39.92862108460521, 116.45824866751343);
//        //构建Marker图标
//        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_shopping);
//        BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromResource(R.drawable.icon_shopping);
//        //构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions()
//                .position(point1)
//                .title("悠唐")
//                .icon(bitmap2);
//        OverlayOptions option2 = new MarkerOptions()
//                .position(point2)
//                .title("东大桥")
//                .icon(bitmap);
//
//        baiduMap.addOverlay(option2);
//        //在地图上添加Marker，并显示
//        baiduMap.addOverlay(option);

//        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                popupWindow = new PopupWindow(getLayoutInflater().inflate(R.layout.popuwindown, null), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//                ImageButton button = (ImageButton) popupWindow.getContentView().findViewById(R.id.imageButton);
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent inten = new Intent(MainActivity.this, SecondActivity.class);
//                        startActivity(inten);
//                    }
//                });
//                popupWindow.setTouchable(true);
//                popupWindow.setOutsideTouchable(true);
//                popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
//
//                popupWindow.getContentView().setFocusableInTouchMode(true);
//                popupWindow.getContentView().setFocusable(true);
//                popupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
//                    @Override
//                    public boolean onKey(View v, int keyCode, KeyEvent event) {
//                        if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
//                                && event.getAction() == KeyEvent.ACTION_DOWN) {
//                            if (popupWindow != null && popupWindow.isShowing()) {
//                                popupWindow.dismiss();
//                            }
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//                popupWindow.showAtLocation(mMapView, Gravity.CENTER, 2, 2);
////                popupWindow.showAsDropDown(popupWindow.getContentView());
//                return false;
//            }
//        });


//        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                Log.i("MapClick", latLng.latitude + ";" + latLng.longitude);
//
//            }
//
//            @Override
//            public boolean onMapPoiClick(MapPoi mapPoi) {
//                Log.i("PoiClick", mapPoi.getPosition().toString());
//                //定义Maker坐标点
////                LatLng point = new LatLng(mapPoi.getPosition().latitude, mapPoi.getPosition().longitude);
////构建Marker图标
////                BitmapDescriptor bitmap = BitmapDescriptorFactory
////                        .fromResource(R.drawable.icon_gcoding);
//////构建MarkerOption，用于在地图上添加Marker
////                OverlayOptions option = new MarkerOptions()
////                        .position(point)
////                        .icon(bitmap);
//////在地图上添加Marker，并显示
////                baiduMap.addOverlay(option);
//                return false;
//            }
//        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0) {
            if (popupWindow != null && !popupWindow.isShowing()) {
                popupWindow.showAtLocation(findViewById(R.id.bmapView), Gravity.BOTTOM, 0, 0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        iMapViewPresenter.stopLocation();
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
        iMapViewPresenter.stopLocation();
        super.onStop();
    }

//    public void goToNext(View view) {
//        Intent intent = new Intent(this, RoutePlanToNevigActivity.class);
//        startActivity(intent);
//    }

    @Override
    public void setLocationDataAndUpdate(MyLocationData locationData, MapStatusUpdate mapStatusUpdate) {
        baiduMap.setMyLocationData(locationData);
        baiduMap.animateMapStatus(mapStatusUpdate);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.city_layout:
                Intent intent = new Intent(MapViewActivity.this, CityListActivity.class);
                MapViewActivity.this.startActivity(intent);
                break;
        }
    }
}
