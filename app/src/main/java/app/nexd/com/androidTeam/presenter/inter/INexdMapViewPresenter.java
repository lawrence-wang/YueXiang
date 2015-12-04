package app.nexd.com.androidTeam.presenter.inter;

import app.nexd.com.androidTeam.mode.FloorModel;

/**
 * Created by lawrence on 2015/12/4.
 */
public interface INexdMapViewPresenter extends IMapViewPresenter {

    void changeFloor();

    void setLocationConfig();

    void loadMap(FloorModel floorModel);

}
