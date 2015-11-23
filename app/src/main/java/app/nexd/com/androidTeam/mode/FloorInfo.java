package app.nexd.com.androidTeam.mode;

import java.io.Serializable;

/**
 * Created by lawrence on 2015/11/12.
 */
public class FloorInfo implements Serializable {
    private String id;
    private String name;


    public FloorInfo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
