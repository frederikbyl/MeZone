package mezone.com.mezoneandroid.event;

import android.location.Location;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

    private String uid;
    private String desc;
    private Date date;
    private EventLocation eventLocation;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EventLocation getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(EventLocation eventLocation) {
        this.eventLocation = eventLocation;
    }

}
