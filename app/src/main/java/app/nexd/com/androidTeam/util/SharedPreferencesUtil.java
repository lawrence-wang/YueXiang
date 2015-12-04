package app.nexd.com.androidTeam.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lawrence on 2015/10/12.
 */
public class SharedPreferencesUtil {

    public static SharedPreferences getLastVisitMall(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("last_visit_mall", Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static void setLastVisitMall(Context context, String cityName, String mallName, String floorName) {

        SharedPreferences sharedPreferences = getLastVisitMall(context);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("city", cityName);
        edit.putString("mall", mallName);
        edit.putString("floorName", floorName);
        edit.commit();
    }

    public static SharedPreferences getFileMd5(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("build_md5", Context.MODE_PRIVATE);
        return sharedPreferences;
    }

//    public static void setFileMd5(Context context, String buildId, String md5) {
//        SharedPreferences sharedPreferences = getFileMd5(context);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(buildId, md5);
//        editor.commit();
//    }

//    public static voidid setFileMd5(Context context, JSONArray arrays){
//        SharedPreferences sharedPreferences = getFileMd5(context);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        for (JSONObject jsonObject : arrays) {
//
//        }
//    }
}
