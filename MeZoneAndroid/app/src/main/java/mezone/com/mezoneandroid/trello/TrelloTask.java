package mezone.com.mezoneandroid.trello;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by frederik on 26/03/18.
 */

public class TrelloTask extends AsyncTask<TrelloCard, Void, Boolean>{




    @Override
    protected Boolean doInBackground(TrelloCard... trelloCards) {

        TrelloCard card = trelloCards[0];


        try {
            // name=testfre&desc=lat=1;lat:2&idList=5aacd91834ba1bb670d136bc&keepFromSource=all&key=de550134f2d5442e79227c8301ddd52b&token=63c3a68d47dde6f9099c2aa5535c184801997d3712ec0add584d3b9b566deeec

            String trelloTestUrl = "https://api.trello.com/1/cards?key=de550134f2d5442e79227c8301ddd52b&token=63c3a68d47dde6f9099c2aa5535c184801997d3712ec0add584d3b9b566deeec";
            URL url = new URL(trelloTestUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);

                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                writeStream(out, card);

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                TrelloCard result = readStream(in);



                // Create a new user with a first and last name
                Map<String, Object> event = new HashMap<>();
                event.put("longitude", card.getLocation().getLongitude() );
                event.put("latitude", card.getLocation().getLatitude() );
                event.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                event.put("message", card.getName());
                event.put("trelloTaskId", result.getId());


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                // Add a new document with a generated ID
                db.collection("events")
                        .add(event)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                 Log.d("TrelloTask", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TrelloTask", "Error adding document", e);
                            }
                        });

            } catch (Exception e){
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }



        return true;
    }

    private void writeStream(OutputStream out, TrelloCard card) {
        card.toOutputStream(out);
    }

    //TRELLO
    /*
    API-key = de550134f2d5442e79227c8301ddd52b
    token = 1e4cc673de293cee599bd2e5da4f105d1b4a2498564b9d4c52be899b15408108

    id board = 5aacd915ed738040bcf8592d
    id list = 5aacd91834ba1bb670d136bc


     */


    private TrelloCard readStream(InputStream in) {

        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;
        try {

            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
        } catch (Exception e ) {
            e.printStackTrace();
        }

        Log.d("TRELLO: ", total.toString());

        Gson gson = new Gson();
        TrelloCard card = gson.fromJson(total.toString(), TrelloCard.class);
        return card;
    }

}
