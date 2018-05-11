package mezone.com.mezoneandroid.model;

import android.location.Location;

import java.io.OutputStream;
import java.io.PrintWriter;

public class Event {

    private String id;
    private String name;
    private String desc;
    private Location location;

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

            p.print("&name="+this.name);
            p.print("&desc="+this.desc);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
