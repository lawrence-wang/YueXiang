package app.nexd.com.androidTeam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;

import app.nexd.com.androidTeam.R;
import app.nexd.com.androidTeam.application.MyApplication;
import app.nexd.com.androidTeam.util.UrlUtil;


/**
 * Created by lawrence on 2015/10/12.
 */
public class SplashScreenActivity extends Activity {
    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    private static final String CITYS_INFO_REQUEST_TAG = "citys_info_request_tag";
    private Timer timer;
    private Timer timer2;
    private TextView notice;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splish_screen);
        notice = (TextView) findViewById(R.id.showOpera);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        timer = new Timer();
        timer2 = new Timer();
        notice.setText("正在初始化数据...");
        progressBar.setProgress(50);
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
        progressBar.setSecondaryProgress(70);
//                timer2.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
        requestCitys();
//                    }
//                }, 3000, 5000);
//            }
//        }, 3000);
    }

    private void requestCitys() {
        StringBuffer url = new StringBuffer();
        url.append(UrlUtil.BASE_REQUEST_URL);
        url.append("/v1.0/getCityInfo.do");
        StringRequest citysRequest = new StringRequest(Request.Method.POST, url.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, response);

                if (TextUtils.isEmpty(response)) {
                    showToast("数据更新失败");
                    return;
                }
                try {
                    JSONObject citysJson = new JSONObject(response);
                    if (citysJson.getBoolean("isSuccess")) {
                        // 获取所有城市信息，存入到城市列表适配器中
                        JSONArray citys = citysJson.getJSONArray("citys");
                        jump(citys.toString());
                        Log.i(TAG, citys.toString());
                    } else {
                        // 打印toast提示
                        String msgCode = UrlUtil.getCodeMessage(citysJson.getString("msgCode"));
//                        Toast.makeText(SplashScreenActivity.this, msgCode, Toast.LENGTH_LONG).show();
                        showToast(msgCode);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("请检查你的网络状态");
                jump("");
//                Log.e(TAG, error.getMessage());
            }
        });
        citysRequest.setRetryPolicy(new DefaultRetryPolicy(6 * 1000, 3, 1.0f));
        citysRequest.setTag(CITYS_INFO_REQUEST_TAG);
        MyApplication.getInstance().getRequestQueue().add(citysRequest);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    private void jump(String cityDatas) {
        Intent intent = new Intent(SplashScreenActivity.this, BDMapViewActivity.class);
        timer.cancel();
        timer2.cancel();
        intent.putExtra("citys", cityDatas);
//        if (path == "") {
////            Toast.makeText(this, "需要网络连接下载配置文件!", Toast.LENGTH_SHORT).show();
//            intent.putExtra("cityJson", "");
//        } else {
//            String fileContent;
//            try {
//                fileContent = FileUtil.readFileByLines(path);
//                intent.putExtra("cityJson", fileContent);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        progressBar.setProgress(100);
        SplashScreenActivity.this.startActivity(intent);
        this.finish();
    }
}
