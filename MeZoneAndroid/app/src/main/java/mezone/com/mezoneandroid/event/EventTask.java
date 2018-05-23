package mezone.com.mezoneandroid.event;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by frederik on 26/03/18.
 */

public class EventTask extends AsyncTask<Event, Void, Boolean>{


    @Override
    protected Boolean doInBackground(Event... events) {

        Event event = events[0];


/*
                // Create a new user with a first and last name
                Map<String, Object> event = new HashMap<>();
                event.put("longitude", card.getLocation().getLongitude() );
                event.put("latitude", card.getLocation().getLatitude() );
                event.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                event.put("message", card.getName());
                event.put("trelloTaskId", result.getId());
*/

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                // Add a new document with a generated ID
                db.collection("events")
                        .add(event)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                 Log.d("EVENT", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("EVENT", "Error adding document", e);
                            }
                        });




        return true;
    }

    //TRELLO
    /*
    API-key = de550134f2d5442e79227c8301ddd52b
    token = 1e4cc673de293cee599bd2e5da4f105d1b4a2498564b9d4c52be899b15408108

    id board = 5aacd915ed738040bcf8592d
    id list = 5aacd91834ba1bb670d136bc


     */

}
