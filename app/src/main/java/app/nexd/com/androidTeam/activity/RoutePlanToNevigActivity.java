package app.nexd.com.androidTeam.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import app.nexd.com.androidTeam.R;

/**
 * Created by lawrence on 2015/11/9.
 */
public class RoutePlanToNevigActivity extends Activity {

    private final static String APP_FOLDER_NAME = "yuexiang";
    private String mSDCardPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_plan);

        if (initDirs()) {
            initNavi();
        }
    }

    public void startRoutePlan(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaiduNaviManager.isNaviInited()) {
                    routeplanToNavi(BNRoutePlanNode.CoordinateType.BD09_MC);
                }
            }
        });
    }

    private void routeplanToNavi(BNRoutePlanNode.CoordinateType coType) {
        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;
        switch (coType) {
            case GCJ02: {
                sNode = new BNRoutePlanNode(116.30142, 40.05087,
                        "百度大厦", null, coType);
                eNode = new BNRoutePlanNode(116.39750, 39.90882,
                        "北京天安门", null, coType);
                break;
            }
            case WGS84: {
                sNode = new BNRoutePlanNode(116.300821, 40.050969,
                        "百度大厦", null, coType);
                eNode = new BNRoutePlanNode(116.397491, 39.908749,
                        "北京天安门", null, coType);
                break;
            }
            case BD09_MC: {
                sNode = new BNRoutePlanNode(12947471, 4846474,
                        "百度大厦", null, coType);
                eNode = new BNRoutePlanNode(12958160, 4825947,
                        "北京天安门", null, coType);
                break;
            }
            default:
        }
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
        }
    }


    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(
                Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private void initNavi() {
        BaiduNaviManager.getInstance().setNativeLibraryPath(mSDCardPath + "/BaiduNaviSDK_SO");
        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME,
                new BaiduNaviManager.NaviInitListener() {
                    @Override
                    public void onAuthResult(int status, String msg) {
                        final String authinfo;
                        if (0 == status) {
                            authinfo = "key校验成功!";
                        } else {
                            authinfo = "key校验失败, " + msg;
                        }
                        RoutePlanToNevigActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(RoutePlanToNevigActivity.this, authinfo, Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    public void initSuccess() {
                        Toast.makeText(RoutePlanToNevigActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                    }

                    public void initStart() {
                        Toast.makeText(RoutePlanToNevigActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
                    }

                    public void initFailed() {
                        Toast.makeText(RoutePlanToNevigActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
                    }
                }, null /*mTTSCallback*/);
    }

    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
//            Intent intent = new Intent(BNDemoMainActivity.this, BNDemoGuideActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
//            intent.putExtras(bundle);
//            startActivity(intent);
        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub

        }
    }

    private BNOuterTTSPlayerCallback mTTSCallback = new BNOuterTTSPlayerCallback() {

        @Override
        public void stopTTS() {
            // TODO Auto-generated method stub

        }

        @Override
        public void resumeTTS() {
            // TODO Auto-generated method stub

        }

        @Override
        public void releaseTTSPlayer() {
            // TODO Auto-generated method stub

        }

        @Override
        public int playTTSText(String speech, int bPreempt) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public void phoneHangUp() {
            // TODO Auto-generated method stub

        }

        @Override
        public void phoneCalling() {
            // TODO Auto-generated method stub

        }

        @Override
        public void pauseTTS() {
            // TODO Auto-generated method stub

        }

        @Override
        public void initTTSPlayer() {
            // TODO Auto-generated method stub

        }

        @Override
        public int getTTSState() {
            // TODO Auto-generated method stub
            return 0;
        }
    };


}
