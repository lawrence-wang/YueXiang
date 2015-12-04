package app.nexd.com.androidTeam.util;

import java.util.ArrayList;
import java.util.List;

import app.nexd.com.androidTeam.mode.MallModel;


/**
 * 初始化数据源类
 * Created by lawrence on 2015/10/15.
 */
public class DataUtil {

    public static List<MallModel> getMalls() {
        List<MallModel> data = new ArrayList<>();
//        // youtang
//        MallInfo youTangMall = new MallInfo();
//        youTangMall.setId("10100123");
//        youTangMall.setName("悠唐广场");
//        youTangMall.setAddr("北京市朝阳区三丰北里2号楼");
//        youTangMall.setOpenTime("9:00");
//        youTangMall.setCloseTime("21:00");
//        youTangMall.setLat("39.1231231231");
//        youTangMall.setLng("144.123123123");
//        youTangMall.setUrl("http://123.57.157.163:8000/10100123.zip");
//        youTangMall.setPicUrl("http://images.55bbs.com/day_121210/20121210_c34d5147cdd68a780bbbYG3yvZm19iOQ.jpg");
//        List<FloorInfo> youTangFloors = new ArrayList<>();
//        for (int i = 1; i <= 7; i++) {
//            if (i == 6)
//                continue;
//
//            youTangFloors.add(new FloorInfo("10100123000" + i, "F" + i));
//        }
//        youTangMall.setFloors(youTangFloors);
//        data.add(youTangMall);
//
//        // kaidemao
//        MallInfo kaiDeMaoMall = new MallInfo();
//        kaiDeMaoMall.setId("10101706");
//        kaiDeMaoMall.setName("凯德茂太阳宫");
//        kaiDeMaoMall.setAddr("北京市朝阳区三丰北里2号楼");
//        kaiDeMaoMall.setOpenTime("9:00");
//        kaiDeMaoMall.setCloseTime("21:00");
//        kaiDeMaoMall.setLat("39.97727303596737");
//        kaiDeMaoMall.setLng("116.45443985215113");
//        kaiDeMaoMall.setUrl("http://123.57.157.163:8900/map_svg/10101706.zip");
//        kaiDeMaoMall.setPicUrl("https://www.google.co.jp/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0CAcQjRxqFQoTCNG90JWYiskCFWUjpgodgTUNAA&url=http%3A%2F%2F590yy.com%2F%25BF%25AD%25B5%25C2logo.html&psig=AFQjCNF76ZMY8MMdSNuGe1QUnrIPhMNkiw&ust=1447393758470689");
//        List<FloorInfo> kaiDeMaoFloor = new ArrayList<>();
//        for (int i = 3; i <= 6; i++) {
//            kaiDeMaoFloor.add(new FloorInfo("10101706000" + i, "F" + (i - 2)));
//        }
//        kaiDeMaoMall.setFloors(youTangFloors);
//        data.add(kaiDeMaoMall);
//

        return data;
    }


//    public static List<CityVo> initDeafultData() {
//        List<CityVo> cityVos = new ArrayList<>();
//
//        List<MallVo> mallVos = new ArrayList<>();
//
//        List<FloorVo> aiQinHaiFloor = new ArrayList<>();
//        // 爱琴海购物中心
//        for (int i = 1; i <= 6; i++) {
//            aiQinHaiFloor.add(new FloorVo("10107001000" + i, "F" + i, "10107001000" + i, "AiQinHaiGouWuZhongXin.FloorF" + i));
//        }
//        MallVo aiQinHaiMall = new MallVo("10107001", "爱琴海购物中心", "AiQinHaiGouWuZhongXin", "", aiQinHaiFloor);
//
//        // 百盛购物中心 （太阳宫店）
//        List<FloorVo> baiShengFloor = new ArrayList<>();
//        for (int i = 2; i <= 8; i++) {
//            if (i == 7) {
//                continue;
//            }
//            baiShengFloor.add(new FloorVo("10100101000" + i, "F" + (i - 1), "10100101000" + i, "BaiShengGouWuZhongXin (TaiYangGongDian ).FloorF" + (i - 1)));
//        }
//        MallVo baiShengMall = new MallVo("10100101", "百盛购物中心 （太阳宫店）", "BaiShengGouWuZhongXin (TaiYangGongDian )", "", baiShengFloor);
//
//        // 北京颐堤港
//        List<FloorVo> yiDiGangFloor = new ArrayList<>();
//        for (int i = 1; i <= 5; i++) {
//            if (i == 1) {
//                yiDiGangFloor.add(new FloorVo("101017040001", "B1", "101017040001", "BeiJingYiDiGang.FloorLG"));
//            } else {
//                yiDiGangFloor.add(new FloorVo("10101704000" + i, "F" + (i - 1), "10101704000" + i, "BeiJingYiDiGang.FloorL" + (i - 1)));
//            }
//        }
//        MallVo yiDiGangMall = new MallVo("10101704", "北京颐堤港", "YiDiGang", "", yiDiGangFloor);
//
//        // 凯德茂太阳宫
//        List<FloorVo> kaiDeMaoFloor = new ArrayList<>();
//        for (int i = 3; i <= 6; i++) {
//            kaiDeMaoFloor.add(new FloorVo("10101706000" + i, "F" + (i - 2), "10101706000" + i, "KaiDeMaoTaiYangGong.FloorF" + (i - 2)));
//        }
//        MallVo kaiDeMaoMall = new MallVo("10101706", "凯德茂太阳宫", "KaiDeMaoTaiYangGong", "", kaiDeMaoFloor);
//
//        // 悠唐广场
//        List<FloorVo> youTangGuangChangFloor = new ArrayList<>();
//        for (int i = 1; i <= 7; i++) {
//            if (i == 1) {
//                youTangGuangChangFloor.add(new FloorVo("10100123000" + i, "B1", "10100123000" + i, "YouTangGuangChang.FloorB" + i));
//            } else {
//                youTangGuangChangFloor.add(new FloorVo("10100123000" + i, "F" + (i - 1), "10100123000" + i, "YouTangGuangChang.FloorF" + (i - 1)));
//            }
//        }
//        MallVo youTangMall = new MallVo("10100123", "悠唐广场", "YouTangGuangChang", "", youTangGuangChangFloor);
//
//
//        // 将商场数据整合
//        mallVos.add(aiQinHaiMall);
//        mallVos.add(baiShengMall);
//        mallVos.add(yiDiGangMall);
//        mallVos.add(kaiDeMaoMall);
//        mallVos.add(youTangMall);
//        CityVo beijing = new CityVo("beijing", "北京", mallVos);
//        cityVos.add(beijing);
//        return cityVos;
//    }
//
//    public static List<CityVo> initDataSources(String jsonData) {
//        Log.i("DataUtil", jsonData);
//        List<CityVo> cityVos = null;
//        try {
//            JSONArray cityArrays = new JSONArray(jsonData);
//            // 城市列表
//            cityVos = new ArrayList<>();
//            for (int i = 0, cityLen = cityArrays.length(); i < cityLen; i++) {
//                JSONObject cityJson = cityArrays.getJSONObject(i);
//                JSONArray mallJsons = cityJson.getJSONArray("malls");
//                // 城市内所有商场的列表
//                List<MallVo> mallVos = new ArrayList<>();
//                for (int j = 0, mallLen = mallJsons.length(); j < mallLen; j++) {
//                    JSONObject mallJson = mallJsons.getJSONObject(j);
//                    JSONArray floorJsons = mallJson.getJSONArray("floors");
//                    // 商场所有楼层的列表
//                    List<FloorVo> floorVos = new ArrayList<>();
//                    for (int k = 0, floorLen = floorJsons.length(); k < floorLen; k++) {
//                        JSONObject floorJson = floorJsons.getJSONObject(k);
//                        floorVos.add(new FloorVo(floorJson.getString("floorId"), floorJson.getString("floorName"), floorJson.getString("floorConfig"), floorJson.getString("floorSVGName")));
//                    }
//                    mallVos.add(new MallVo(mallJson.getString("mallId"), mallJson.getString("mallCName"), mallJson.getString("mallEName"), mallJson.getString("url"), floorVos));
//                }
//                cityVos.add(new CityVo(cityJson.getString("cityEName"), cityJson.getString("cityCName"), mallVos));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return cityVos;
//    }

}
