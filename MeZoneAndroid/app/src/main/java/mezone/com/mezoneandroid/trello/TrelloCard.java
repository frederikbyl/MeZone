package mezone.com.mezoneandroid.trello;

import android.location.Location;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by frederik on 24/03/18.
 */

public class TrelloCard {

    private String key = "de550134f2d5442e79227c8301ddd52b";
    private String token = "1e4cc673de293cee599bd2e5da4f105d1b4a2498564b9d4c52be899b15408108";
    private String id;
    private String name;
    private String desc;
    private Location location;
    private String idList =  "5aacd91834ba1bb670d136bc";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void toOutputStream(OutputStream out) {
        try (PrintWriter p = new PrintWriter(out)) {
            p.print("key="+this.key);
            p.print("&token="+this.token);
            p.print("&idList="+this.idList);
            p.print("&name="+this.name);
            p.print("&desc="+this.desc);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
