package app.nexd.com.androidTeam.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.nexd.com.androidTeam.R;
import app.nexd.com.androidTeam.activity.inter.IBDMapViewActivity;
import app.nexd.com.androidTeam.mode.CityModel;
import app.nexd.com.androidTeam.mode.MallModel;
import app.nexd.com.androidTeam.presenter.IBDMapViewPresenterImpl;
import app.nexd.com.androidTeam.presenter.inter.IBDMapViewPresenter;

public class BDMapViewActivity extends Activity implements IBDMapViewActivity, View.OnClickListener {
    private MapView mMapView;
    private IBDMapViewPresenter ibdMapViewPresenter;
    private BaiduMap baiduMap;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;
    private TextView cityName;
    private RelativeLayout city_layout;
    PopupWindow popupWindow;
    //    private GridView gridView;
    private String cityDatas = "";
    private ArrayList<CityModel> citys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_column);
        setContentView(R.layout.activity_main);
        cityName = (TextView) this.findViewById(R.id.city_name);
        city_layout = (RelativeLayout) this.findViewById(R.id.city_layout);
        city_layout.setOnClickListener(this);
        mMapView = (MapView) findViewById(R.id.bmapView);
        ibdMapViewPresenter = new IBDMapViewPresenterImpl(getApplicationContext(), this, mMapView);
//        iMapViewPresenter.requestAllCitys();
        baiduMap = mMapView.getMap();
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        cityDatas = getIntent().getStringExtra("citys");
        parseCityDatas(cityDatas);
        // 设置自定义定位点的样式， 传null进去为回复默认
//        mCurrentMarker = BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_geo);
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
        baiduMap.setMyLocationEnabled(true); // 开启定位图层
        ibdMapViewPresenter.initMap();
        ibdMapViewPresenter.startLocation();


        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                popupWindow = new PopupWindow(getLayoutInflater().inflate(R.layout.popuwindown, null),
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                ImageButton button = (ImageButton) popupWindow.getContentView().findViewById(R.id.imageButton);
                TextView mallName = (TextView) popupWindow.getContentView().findViewById(R.id.mallName);

                final MallModel mallModel = marker.getExtraInfo().getParcelable("MallModel");
                mallName.setText(mallModel.getName());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ibdMapViewPresenter.requestFloors(popupWindow.getContentView().getContext(),mallModel);
                    }
                });
                popupWindow.setTouchable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

                popupWindow.getContentView().setFocusableInTouchMode(true);
                popupWindow.getContentView().setFocusable(true);
                popupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                                && event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (popupWindow != null && popupWindow.isShowing()) {
                                popupWindow.dismiss();
                            }
                            return true;
                        }
                        return false;
                    }
                });
                popupWindow.showAtLocation(mMapView, Gravity.CENTER, 2, 2);
//                popupWindow.showAsDropDown(popupWindow.getContentView());
                return false;
            }
        });

//
//
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
        ibdMapViewPresenter.stopLocation();
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
        ibdMapViewPresenter.stopLocation();
        super.onStop();
    }

    @Override
    public void setLocationDataAndUpdate(MyLocationData locationData, MapStatusUpdate mapStatusUpdate) {
        baiduMap.setMyLocationData(locationData);
        baiduMap.animateMapStatus(mapStatusUpdate);
    }

    @Override
    public void setMallOverlay(List<MallModel> mallModels) {
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_shopping);
        for (MallModel mallModel : mallModels) {

            LatLng point = new LatLng(mallModel.getLng(), mallModel.getLat());

            Bundle bundle = new Bundle();
            bundle.putParcelable("MallModel", mallModel);

            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .title(mallModel.getName())
                    .extraInfo(bundle)
                    .icon(bitmap);

            baiduMap.addOverlay(option);
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }

    private void parseCityDatas(String cityDatas) {
        citys = new ArrayList<>();
        if (TextUtils.isEmpty(cityDatas)) {
            return;
        }
        try {
            JSONArray citysJson = new JSONArray(cityDatas);
            for (int i = 0; i < citysJson.length(); i++) {
                JSONObject city = citysJson.getJSONObject(i);
                CityModel cityModel = new CityModel();
                cityModel.setCityName(city.getString("cityCName"));
                cityModel.setCityEname(city.getString("cityEName"));
                cityModel.setCityCode(city.getString("cityCode"));
                citys.add(cityModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                Intent intent = new Intent(BDMapViewActivity.this, CityListActivity.class);
//                intent.putExtra("citys", cityDatas);
                intent.putParcelableArrayListExtra("citys", citys);
                BDMapViewActivity.this.startActivity(intent);
                break;
        }
    }
}
