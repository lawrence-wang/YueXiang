package app.nexd.com.androidTeam.util;

import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lawrence on 2015/10/29.
 */
public class SplineUtil {
    public static PointF getPoint(PointF startPoint, PointF endPoint) {
        PointF targetPoint;
        float[] stXY = new float[]{startPoint.x, startPoint.y};
        float[] edXY = new float[]{endPoint.x, endPoint.y};

        int distance = (int) Math.sqrt(Math.pow(stXY[1] - edXY[1], 2) + Math.pow(stXY[0] - edXY[0], 2));
        Log.i("SplineUtil", "distance = " + distance + " m");
        if (distance > 2 && (startPoint.x != 0 && startPoint.y != 0)) {
            float newX = (endPoint.x - startPoint.x) * 2 / distance + startPoint.x;
            float newY = (endPoint.y - startPoint.y) * 2 / distance + startPoint.y;
            targetPoint = new PointF(newX, newY);
//            Log.i("SplineUtil", targetPoint.toString());
        } else {
            targetPoint = endPoint;
        }
        return targetPoint;
    }


    public static List<Float[]> spline(float[] floats, float[] floats1, int i) {

        Double aDouble[] = new Double[]{Double.parseDouble(floats[0] + ""), Double.parseDouble(floats[1] + "")};
        Double bDouble[] = new Double[]{Double.parseDouble(floats1[0] + ""), Double.parseDouble(floats1[1] + "")};
        List<Double[]> doubles = spline(aDouble, bDouble, new Double(i + ""));
        List<Float[]> list = new ArrayList<>();
        for (int a = 0; a < doubles.size(); a++) {
            Double[] cacheDouble = doubles.get(a);

            Float[] cacheFloat = new Float[]{Float.parseFloat(cacheDouble[0] + ""), Float.parseFloat(cacheDouble[1] + "")};
            list.add(cacheFloat);
        }
        return list;
    }

    /*
    Input:
        st Double[2]; 起点坐标
        ed Double[2]; 终点坐标
        stepSize Double; 间隔长度
    Output:
        List<Double[2]> splinePath
 */
    private static List<Double[]> spline(Double[] st, Double[] ed, Double stepSize) {

        List<Double[]> splinePath = new ArrayList<>();

        if (ed[0] != st[0] && ed[1] != st[1]) {
            Double a = (ed[1] - st[1]) / (ed[0] - st[0]);
            Double b = st[1] - a * st[0];
            Double dis = Math.sqrt(Math.pow(st[1] - ed[1], 2) + Math.pow(st[0] - ed[0], 2));
            Double theta = Math.atan2(ed[1] - st[1], ed[0] - st[0]);
            Double marchdis = 0.0;
            Double[] temp = st.clone();
            while (marchdis < dis) {
                splinePath.add(temp.clone());
                temp[0] += stepSize * Math.cos(theta);
                temp[1] += stepSize * Math.sin(theta);
                marchdis += stepSize;
                System.out.println(temp[0] + ";" + temp[1]);
            }
            return splinePath;

        } else if (ed[0] == st[0] && ed[1] == st[1]) {
            // Similar Point
            splinePath.add(st);
            splinePath.add(ed);
            return splinePath;
        } else if (ed[1] == st[1]) {
            Double dir = ((ed[0] - st[0]) > 0) ? 1. : -1.;
            Double pointer = st[0];
            Double[] temp = new Double[2];
            temp[1] = st[1];
            while ((pointer - ed[0]) * dir < 0) {
                temp[0] = pointer;
                splinePath.add(temp);
                pointer += stepSize * dir;
            }
            return splinePath;
        } else {
            // ed[0] == st[0]
            Double dir = ((ed[1] - st[1]) > 0) ? 1. : -1.;
            Double pointer = st[1];
            Double[] temp = new Double[2];
            temp[0] = st[0];
            while ((pointer - ed[1]) * dir < 0) {
                temp[1] = pointer;
                splinePath.add(temp);
                pointer += stepSize * dir;
            }
            return splinePath;
        }
    }

}
