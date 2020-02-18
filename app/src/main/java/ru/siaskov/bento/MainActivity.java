package ru.siaskov.bento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

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

    private ActivityMainBinding mainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = DataBindingUtil.setContentView(this,
               R.layout.activity_main);

        mainBinding.myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.myRecyclerView.setHasFixedSize(true );

        sendRequestToApi(url);

    }

    @Override
    protected void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }


    /**
     * Send request to API
     * @param url - to api
     */
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

    /**
     * Parse String from JSON to Array List of Game
     * @param response String with response from API
     * @return Array List of Game
     * @throws JSONException if something goes wrong with JSON parse
     */
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
