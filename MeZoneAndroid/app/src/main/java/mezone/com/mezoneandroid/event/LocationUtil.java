package mezone.com.mezoneandroid.event;

import android.location.Location;

public class LocationUtil {
    public static EventLocation toEventLocation(Location location) {
        EventLocation eventLocation = new EventLocation();
        eventLocation.setAccuracy(location.getAccuracy());
        eventLocation.setAltitude(location.getAltitude());
        eventLocation.setBearing(location.getBearing());
        //eventLocation.setBearingAccuracyDegrees(location.getBearingAccuracyDegrees());
        eventLocation.setElapsedRealtimeNanos(location.getElapsedRealtimeNanos());
        //eventLocation.setExtras(location.getExtras());

        eventLocation.setLatitude(location.getLatitude());
        eventLocation.setLongitude(location.getLongitude());
        eventLocation.setProvider(location.getProvider());
        eventLocation.setSpeed(location.getSpeed());
        eventLocation.setTime(location.getTime());

        return eventLocation;
    }
}
