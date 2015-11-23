package app.nexd.com.androidTeam.util;

import java.util.ArrayList;
import java.util.List;

import app.nexd.com.androidTeam.mode.FloorInfo;
import app.nexd.com.androidTeam.mode.MallInfo;

/**
 * Created by lawrence on 2015/11/11.
 */
public class DataUtil {

    public static List<MallInfo> getMalls() {
        List<MallInfo> data = new ArrayList<>();
        // youtang
        MallInfo youTangMall = new MallInfo();
        youTangMall.setId("10100123");
        youTangMall.setName("悠唐广场");
        youTangMall.setAddr("北京市朝阳区三丰北里2号楼");
        youTangMall.setOpenTime("9:00");
        youTangMall.setCloseTime("21:00");
        youTangMall.setLat("39.1231231231");
        youTangMall.setLng("144.123123123");
        youTangMall.setUrl("http://123.57.157.163:8000/10100123.zip");
        youTangMall.setPicUrl("http://images.55bbs.com/day_121210/20121210_c34d5147cdd68a780bbbYG3yvZm19iOQ.jpg");
        List<FloorInfo> youTangFloors = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            if (i == 6)
                continue;

            youTangFloors.add(new FloorInfo("10100123000" + i, "F" + i));
        }
        youTangMall.setFloors(youTangFloors);
        data.add(youTangMall);

        // kaidemao
        MallInfo kaiDeMaoMall = new MallInfo();
        kaiDeMaoMall.setId("10101706");
        kaiDeMaoMall.setName("凯德茂太阳宫");
        kaiDeMaoMall.setAddr("北京市朝阳区三丰北里2号楼");
        kaiDeMaoMall.setOpenTime("9:00");
        kaiDeMaoMall.setCloseTime("21:00");
        kaiDeMaoMall.setLat("39.97727303596737");
        kaiDeMaoMall.setLng("116.45443985215113");
        kaiDeMaoMall.setUrl("http://123.57.157.163:8900/map_svg/10101706.zip");
        kaiDeMaoMall.setPicUrl("https://www.google.co.jp/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0CAcQjRxqFQoTCNG90JWYiskCFWUjpgodgTUNAA&url=http%3A%2F%2F590yy.com%2F%25BF%25AD%25B5%25C2logo.html&psig=AFQjCNF76ZMY8MMdSNuGe1QUnrIPhMNkiw&ust=1447393758470689");
        List<FloorInfo> kaiDeMaoFloor = new ArrayList<>();
        for (int i = 3; i <= 6; i++) {
            kaiDeMaoFloor.add(new FloorInfo("10101706000" + i, "F" + (i - 2)));
        }
        kaiDeMaoMall.setFloors(youTangFloors);
        data.add(kaiDeMaoMall);


        return data;
    }
}
