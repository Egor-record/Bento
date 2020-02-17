package ru.siaskov.bento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.siaskov.bento.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private String url = "http://192.168.0.151:8080/api/v1/gameses";
    public static final String TAG = "MyTag";
    private StringRequest stringRequest; // Assume this exists.
    private RequestQueue requestQueue;  // Assume this exists.
    private Games mGames;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this,
               R.layout.activity_main);

        sendRequestToApi(url);

    }

    @Override
    protected void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }




    private void sendRequestToApi(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            ArrayList<Game> games = getGameDetails(response.toString());


                            Log.e(TAG, games.toString());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                       // binding.setGames(displayGames);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        queue.add(stringRequest);
        stringRequest.setTag(TAG);
        requestQueue.add(stringRequest);

    }

    private ArrayList<Game> getGameDetails(String response) throws JSONException {

        JSONArray jsonGames = new JSONObject(response).getJSONObject("_embedded").getJSONArray("gameses");

        ArrayList<Game> games = new ArrayList<>();

        for (int j=0 ; j < jsonGames.length() ; j++) {
            games.add(new Game(
                                jsonGames.getJSONObject(j).getString("teamFirst"),
                                jsonGames.getJSONObject(j).getString("teamSecond"),
                            0, 0, "Football",
                    "Fifa"));
        }

        return games;
    }


}
