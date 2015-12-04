package app.nexd.com.androidTeam.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.nexd.com.androidTeam.activity.NexdMapViewActivity;
import app.nexd.com.androidTeam.activity.inter.IBDMapViewActivity;
import app.nexd.com.androidTeam.application.MyApplication;
import app.nexd.com.androidTeam.mode.FloorModel;
import app.nexd.com.androidTeam.mode.MallModel;
import app.nexd.com.androidTeam.presenter.inter.IBDMapViewPresenter;
import app.nexd.com.androidTeam.util.UrlUtil;

/**
 * 百度地图页面业务处理类
 * Created by lawrence on 2015/11/10.
 */
public class IBDMapViewPresenterImpl implements IBDMapViewPresenter {
    private static final String TAG = IBDMapViewPresenterImpl.class.getSimpleName();
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = null;
    private boolean isFirstLocation = true;
    private IBDMapViewActivity ibdMapViewActivity;
    private MapView mMapView;
    private Context context;

    public IBDMapViewPresenterImpl(Context context, IBDMapViewActivity ibdMapViewActivity, MapView mMapView) {
        this.ibdMapViewActivity = ibdMapViewActivity;
        this.mMapView = mMapView;
        this.context = context;
        mLocationClient = new LocationClient(context);     //声明LocationClient类
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
    }

    @Override
    public void initMap() {
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
    public void requestAllCitys() {
//        ProgressDialogUtils.showProgressDialog(MyApplication.getInstance().getApplicationContext(), "request citys", "", false);
        StringBuffer url = new StringBuffer();
        url.append(UrlUtil.BASE_REQUEST_URL);
        url.append("/v1.0/getCityInfo.do");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("isSuccess")) {
                        // 获取所有城市信息，存入到城市列表适配器中
                        JSONArray citys = jsonObject.getJSONArray("citys");
                        String cityDatas = citys.toString();
                        Log.i(TAG, cityDatas);
//                        for (int i = 0; i < citys.length(); i++) {
//
//                        }
                    } else {
                        // 打印toast提示
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
            }
        });
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    @Override
    public void requestAllMalls(String cityCode) {
//        String url = UrlUtil.BASE_REQUEST_URL;
        StringBuffer url = new StringBuffer();
        url.append(UrlUtil.BASE_REQUEST_URL);
        url.append("/v1.0/getBuildingInfo.do?cityCode=" + cityCode);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, response);
                if (!TextUtils.isEmpty(response)) {
                    try {
                        JSONObject resultJson = new JSONObject(response);
                        if (resultJson.getBoolean("isSuccess")) {
                            ArrayList<MallModel> mallModels = new ArrayList<>();
                            JSONArray mallArrays = resultJson.getJSONArray("malls");
                            for (int i = 0; i < mallArrays.length(); i++) {
                                JSONObject mallObj = mallArrays.getJSONObject(i);
                                MallModel mallModel = new MallModel();
                                mallModel.setId(mallObj.getString("mallId"));
                                mallModel.setName(mallObj.getString("mallCName"));
                                mallModel.setLat(Double.parseDouble(mallObj.getString("baiduLat")));
                                mallModel.setLng(Double.parseDouble(mallObj.getString("baiduLng")));
                                mallModels.add(mallModel);
                            }
                            ibdMapViewActivity.setMallOverlay(mallModels);
                        } else {
                            ibdMapViewActivity.showToast(UrlUtil.getCodeMessage(resultJson.getString("msgCode")));
                        }
                    } catch (JSONException e) {

                    }
                } else {
                    ibdMapViewActivity.showToast("商场数据更新失败");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ibdMapViewActivity.showToast("请检查你的网络连接");
//                Log.e(TAG, error.getMessage());
            }
        });
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    @Override
    public void requestFloors(final Context context, final MallModel mallModel) {

        StringBuffer url = new StringBuffer();
        url.append(UrlUtil.BASE_REQUEST_URL);
        url.append("/v1.0/getFloorsInfo.do?buildingCode=");
        url.append(mallModel.getId());
        Log.d("requestFloors", url.toString());
        StringRequest floorsRequest = new StringRequest(Request.Method.POST, url.toString(), new Response.Listener<String>() {
            /**
             * Called when a response is received.
             *
             * @param response
             */
            @Override
            public void onResponse(String response) {
                if (!TextUtils.isEmpty(response)) {
                    Log.d("requestFloors", response);
                    try {
                        JSONObject floorResultJson = new JSONObject(response);
                        if (floorResultJson.getBoolean("isSuccess")) {
                            JSONArray floorArrays = floorResultJson.getJSONArray("floors");
                            ArrayList<FloorModel> floorModelArrayList = new ArrayList<>();
                            for (int i = 0; i < floorArrays.length(); i++) {
                                JSONObject floorModelObj = floorArrays.getJSONObject(i);
                                FloorModel floorModel = new FloorModel(floorModelObj.getString("floorId"),
                                        floorModelObj.getString("floorName"));
                                floorModelArrayList.add(floorModel);
                            }
                            Intent intent = new Intent(context, NexdMapViewActivity.class);
                            intent.putExtra("FloorModels", floorModelArrayList);
                            intent.putExtra("MallModel", mallModel);
                            context.startActivity(intent);
                        } else {
                            ibdMapViewActivity.showToast("数据更新失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            /**
             * Callback method that an error has been occurred with the
             * provided error code and optional user-readable message.
             *
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyApplication.getInstance().getRequestQueue().add(floorsRequest);
        floorsRequest.setRetryPolicy(new DefaultRetryPolicy(6000, 3, 0));
    }

    @Override
    public void routePlan() {

    }

//    private boolean checkMD5(String mallId, String md5) {
//        //TODO 从sharedPreferenceFile中获取MD5信息，请求校验，判断成功或者失败
//        // 失败，获取下载地址，进行下载， 成功，直接load地图信息，执行页面跳转
//        StringBuffer url = new StringBuffer();
//        url.append(UrlUtil.BASE_REQUEST_URL);
//        url.append("/v1.0/checkProp.do?buildingCode=");
//        url.append(mallId);
//        url.append("&MD5=");
//        url.append(md5 == null ? "" : md5 + ".zip");
//        Log.d("checkMd5", url.toString());
//        StringRequest checkMd5Request = new StringRequest(Request.Method.POST, url.toString(), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (!TextUtils.isEmpty(response)) {
//                    try {
//                        JSONObject resultJson = new JSONObject(response);
//                        Log.i(TAG, response);
//                        if (resultJson.getBoolean("isSuccess")) {
//                            if (resultJson.getString("msgCode").equals(UrlUtil.WEB_API_APP_NEED_UPDATE_PROP)) {
//                                Log.i(TAG, resultJson.getString("url"));
//                                updateOrDownloadFile(resultJson.getString("url"));
//                            } else {
//                                Log.i(TAG, "不需要更新!");
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        MyApplication.getInstance().getRequestQueue().add(checkMd5Request);
//        return false;
//    }

//    private void updateOrDownloadFile(String url) {
////        StringBuffer url = new StringBuffer();
////        url.append(UrlUtil.BASE_REQUEST_URL);
////        url.append("/v1.0/downloadProp.do?fileName=8807B97D2A2439BF7B8C698E020D6A75.zip");
//
//        InputStreamVolleyRequest downloadRequest = new InputStreamVolleyRequest(Request.Method.POST, url, new Response.Listener<byte[]>() {
//            /**
//             * Called when a response is received.
//             *
//             * @param response
//             */
//            @Override
//            public void onResponse(byte[] response) {
//                try {
//                    FileUtil.writeFile(UrlUtil.BASE_FILE_PATH + "8807B97D2A2439BF7B8C698E020D6A75.zip", response);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Log.i(TAG, response.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }, null);
//
//        MyApplication.getInstance().getRequestQueue().add(downloadRequest);
//    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 3000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setLocationNotify(true);
        mLocationClient.setLocOption(option);
    }


    private class MyLocationListener implements BDLocationListener {


        MapStatusUpdate u = null;

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null) {
                return;
            }
//            location.get
            Log.i("cityCode", location.getCityCode());
            // 设置  + / - 号
//            mMapView.setZoomControlsPosition(new Point((int) location.getLatitude(), (int) location.getLongitude()));
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
                requestAllMalls("010");
            }
            ibdMapViewActivity.setLocationDataAndUpdate(locData, u);
        }
    }
}
