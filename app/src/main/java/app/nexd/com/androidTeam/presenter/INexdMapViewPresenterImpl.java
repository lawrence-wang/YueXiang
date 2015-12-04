package app.nexd.com.androidTeam.presenter;

import android.content.Context;
import android.graphics.PointF;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import app.nexd.com.androidTeam.activity.inter.INexdMapViewActivity;
import app.nexd.com.androidTeam.application.MyApplication;
import app.nexd.com.androidTeam.http.InputStreamVolleyRequest;
import app.nexd.com.androidTeam.mode.FloorModel;
import app.nexd.com.androidTeam.mode.MallModel;
import app.nexd.com.androidTeam.presenter.inter.INexdMapViewPresenter;
import app.nexd.com.androidTeam.util.FileUtil;
import app.nexd.com.androidTeam.util.UrlUtil;
import cn.nexd.map.NexdLocationAgent;
import cn.nexd.map.NexdLocationListener;
import cn.nexd.map.location.utils.Log;
import cn.nexd.map.rendering.core.componet.SVGMapView;
import cn.nexd.map.rendering.overlay.SVGMapLocationOverlay;

/**
 * Created by lawrence on 2015/12/4.
 */
public class INexdMapViewPresenterImpl implements INexdMapViewPresenter {

    private NexdLocationAgent nexdLocationAgent;
    private SVGMapView nexdMap;
    private FloorModel floorModel;
    private Map<String, List<FloorModel>> floorModels;
    private Context context;
    private MallModel mallModel;
    private INexdMapViewActivity iNexdMapViewActivity;
    private String mallpath = "";

    private SVGMapLocationOverlay svgMapLocationOverlay;

    public INexdMapViewPresenterImpl(SVGMapView nexdMap, Context context, MallModel mallModel, INexdMapViewActivity iNexdMapViewActivity) {
        this.context = context;
        this.nexdMap = nexdMap;
        this.mallModel = mallModel;
        this.iNexdMapViewActivity = iNexdMapViewActivity;
        this.mallpath = UrlUtil.BASE_FILE_PATH + File.separator + mallModel.getId() + File.separator;
    }

    @Override
    public void changeFloor() {

    }

    @Override
    public void setLocationConfig() {
        nexdLocationAgent.setLocationConfig(this.mallpath, floorModel.getFloorId());
        setLocationListener();
    }

    private void setLocationListener() {
        nexdLocationAgent.setLocationListener(new NexdLocationListener() {
            @Override
            public void onLocationStart() {
                Log.d("LocationListener", "开始定位");
            }

            @Override
            public void onLocationSuccess(PointF pointF, String floorId) {
                Log.d("LocationListener", "定位成功");
                if (svgMapLocationOverlay == null) {
                    svgMapLocationOverlay = new SVGMapLocationOverlay(nexdMap);
                }
                if (nexdMap.isMapLoadFinsh()) {
                    svgMapLocationOverlay.setPosition(pointF);
                    nexdMap.addOverlay(svgMapLocationOverlay, 0);
                    iNexdMapViewActivity.refreshNexdMap();
                }
            }

            @Override
            public void onLocationFailed(String s) {
                Log.d("LocationListener", "定位失败");
                iNexdMapViewActivity.showToast("定位失败");
            }
        });
    }

    @Override
    public void loadMap(FloorModel floorModel) {
        this.floorModel = floorModel;
        Log.d("svgPath", getSvgPath(floorModel.getFloorSvg()));
        iNexdMapViewActivity.loadMap(getSvgPath(floorModel.getFloorSvg()));
//        checkFileExist(getSvgPath(floorModel.getFloorSvg()));
    }

    private void checkFileExist(String svgPath) {
        if (FileUtil.checkExist(svgPath)) {
            checkUpdate(mallModel.getId());
        } else {
            StringBuffer url = new StringBuffer();
            url.append(UrlUtil.BASE_REQUEST_URL);
            url.append("/v1.0/downloadProp.do?buildingCode=");
            url.append(mallModel.getId());
            downloadFile(url.toString());
        }
    }

    private String getSvgPath(String svgName) {
        return mallpath + "SVG" + File.separator + svgName;
    }

    private void checkUpdate(String mallId) {
        //TODO 从sharedPreferenceFile中获取MD5信息，请求校验，判断成功或者失败
        // 失败，获取下载地址，进行下载， 成功，直接load地图信息，执行页面跳转
        StringBuffer url = new StringBuffer();
        url.append(UrlUtil.BASE_REQUEST_URL);
        url.append("/v1.0/checkProp.do?buildingCode=");
        url.append(mallId);
        android.util.Log.d("checkUpdate", url.toString());
        StringRequest checkUpdateRequest = new StringRequest(Request.Method.POST, url.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!TextUtils.isEmpty(response)) {
                    try {
                        JSONObject resultJson = new JSONObject(response);
                        Log.d("checkUpdate", resultJson);
                        if (resultJson.getBoolean("isSuccess")) {
                            if (resultJson.getString("msgCode").equals(UrlUtil.WEB_API_APP_NEED_UPDATE_PROP)) {
                                downloadFile(resultJson.getString("url"));
                            } else {
                                Log.i("checkUpdate", "不需要更新!");
                                iNexdMapViewActivity.loadMap(getSvgPath(floorModel.getFloorSvg()));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApplication.getInstance().getRequestQueue().add(checkUpdateRequest);
        checkUpdateRequest.setRetryPolicy(new DefaultRetryPolicy(6000, 3, 0));
    }

    private void downloadFile(String url) {
        Log.d("downloadFile", url);
        InputStreamVolleyRequest downloadRequest = new InputStreamVolleyRequest(Request.Method.POST, url, new Response.Listener<byte[]>() {
            /**
             * Called when a response is received.
             *
             * @param response
             */
            @Override
            public void onResponse(byte[] response) {
                if (response == null || response.length == 0) {
                    iNexdMapViewActivity.showToast("数据更新失败");
                    return;
                } else {
                    try {
                        StringBuffer savePath = new StringBuffer();
                        savePath.append(UrlUtil.DOWNLOAD_FILE_PATH);
                        savePath.append(mallModel.getId());
                        savePath.append(".zip");
                        // 存储下载文件
                        FileUtil.writeFile(savePath.toString(), response);

                        String filePath = UrlUtil.DOWNLOAD_FILE_PATH + File.separator;
                        FileUtil.unCompressZipFile(new File(savePath.toString()), filePath);
                        FileUtil.deleteFileOrDir(savePath.toString()); // 删除压缩包文件
                        // filePath ==> map_svg/download/10101001
                        String downloadFile = filePath + mallModel.getId();
                        FileUtil.shearFile(downloadFile, UrlUtil.BASE_FILE_PATH); // 将解压出来的文件，剪切到父级目录中
                        iNexdMapViewActivity.showToast("下载完成!");

                        iNexdMapViewActivity.loadMap(getSvgPath(floorModel.getFloorSvg()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i("downloadFile", response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iNexdMapViewActivity.showToast("数据更新失败");
            }
        }, null);
        MyApplication.getInstance().getRequestQueue().add(downloadRequest);
        downloadRequest.setRetryPolicy(new DefaultRetryPolicy(6000, 3, 0));
    }

    @Override
    public void initMap() {
        nexdMap.setMaxZoomValue(300);
        nexdLocationAgent = NexdLocationAgent.getLocationAgent();
        nexdLocationAgent.setContext(context);
        nexdLocationAgent.setShowTextSize(275);
        nexdLocationAgent.setIsHightLight(false);
        nexdLocationAgent.setTextSize(30);
    }

    @Override
    public void startLocation() {
        nexdLocationAgent.startLocation(1500);
    }

    @Override
    public void stopLocation() {
        nexdLocationAgent.stopLocation();
    }
}
