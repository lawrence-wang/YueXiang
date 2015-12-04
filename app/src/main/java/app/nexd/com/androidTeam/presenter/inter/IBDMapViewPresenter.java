package app.nexd.com.androidTeam.presenter.inter;

import android.content.Context;

import app.nexd.com.androidTeam.mode.MallModel;

/**
 * Created by lawrence on 2015/11/10.
 */
public interface IBDMapViewPresenter extends IMapViewPresenter {

    void requestAllCitys();

    void requestAllMalls(String cityCode);

    void requestFloors(Context context, MallModel mallModel);

    void routePlan();
}
