package app.nexd.com.androidTeam.activity.inter;

/**
 * Created by lawrence on 2015/12/4.
 */
public interface INexdMapViewActivity extends IBaseActivity {

    void loadMap(String svgPath);

    void refreshNexdMap();
}
