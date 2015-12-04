package app.nexd.com.androidTeam.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import app.nexd.com.androidTeam.R;
import app.nexd.com.androidTeam.activity.inter.INexdMapViewActivity;
import app.nexd.com.androidTeam.mode.FloorModel;
import app.nexd.com.androidTeam.mode.MallModel;
import app.nexd.com.androidTeam.presenter.INexdMapViewPresenterImpl;
import app.nexd.com.androidTeam.presenter.inter.INexdMapViewPresenter;
import cn.nexd.map.rendering.SVGMapViewListener;
import cn.nexd.map.rendering.core.componet.SVGMapView;

/**
 * NexdSvgMapView，用于显示室内地图信息
 * Created by lawrence on 2015/12/4.
 */
public class NexdMapViewActivity extends BaseActivity implements INexdMapViewActivity {

    INexdMapViewPresenter iNexdMapViewPresenter;
    SVGMapView nexdMap;
    TextView titleText;

    List<FloorModel> floorModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nexd_map_layout);
        nexdMap = (SVGMapView) this.findViewById(R.id.svg_map);
        titleText = (TextView) this.findViewById(R.id.title_filter_text);

        Intent intent = getIntent();
        MallModel mallModel = intent.getParcelableExtra("MallModel");
        floorModels = intent.getParcelableArrayListExtra("FloorModels");


        // 实例化业务接口
        iNexdMapViewPresenter = new INexdMapViewPresenterImpl(nexdMap, this, mallModel, this);

        // 初始化地图
        iNexdMapViewPresenter.initMap();
        nexdMap.registerMapViewListener(new MySvgMapViewListener());
        iNexdMapViewPresenter.loadMap(floorModels.get(0));
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadMap(String svgPath) {
        try {
            nexdMap.loadMap(new FileInputStream(new File(svgPath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshNexdMap() {
        nexdMap.refresh();
    }


    // 加载地图监听
    private class MySvgMapViewListener implements SVGMapViewListener {

        @Override
        public void onMapLoadComplete(long floorId, final String buildName) {
            if (nexdMap.isMapLoadFinsh()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iNexdMapViewPresenter.setLocationConfig();
                        iNexdMapViewPresenter.startLocation();
                        titleText.setText(buildName);
                    }
                });
            }
        }

        @Override
        public void onMapLoadError() {
            showToast("地图加载失败，请检查您的数据!");
        }

        @Override
        public void onGetCurrentMap(Bitmap bitmap) {

        }
    }
}
