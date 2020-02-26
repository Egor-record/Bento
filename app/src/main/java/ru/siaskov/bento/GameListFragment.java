package ru.siaskov.bento;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

import ru.siaskov.bento.databinding.ActivityMainBinding;

public class GameListFragment extends Fragment {

    private ActivityMainBinding mainBinding;

    private String url = "http://192.168.0.151:8080/api/v1/gameses";

    public static final String TAG = "MyTag";
    private StringRequest stringRequest; // Assume this exists.
    private RequestQueue requestQueue;  // Assume this exists.

    private RecyclerView mGamesRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.game_list_layout, container, false);

        mGamesRecyclerView = (RecyclerView) view
                .findViewById(R.id.my_recycler_view);

        mGamesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;

    }

    private class GameHolder extends RecyclerView.ViewHolder {


        public GameHolder(@NonNull View itemView) {
            super(itemView);
        }
    }





    private class GameAdapter extends RecyclerView.Adapter<GameHolder> {

        private List<Game> mGames;

        public GameAdapter(List<Game> games) {
            mGames = games;
        }

        @NonNull
        @Override
        public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            return new GameHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GameHolder holder, int position) {
            Game game = mGames.get(position);

        }

        @Override
        public int getItemCount() {
            return mGames.size();
        }
    }



    /**
     * Send request to API
     * @param url - to api
     */
    private void sendRequestToApi(String url, final Context _context) throws NullPointerException {

        RequestQueue queue = Volley.newRequestQueue(_context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            List<Game> games = getGameDetails(response);




                        } catch (JSONException e) {

                            e.printStackTrace();

                        }   catch (NullPointerException e) {

                            e.printStackTrace();

                        }

                        Log.e(TAG, "Work");


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Dont work");
                error.printStackTrace();
            }
        });


        queue.add(stringRequest);

    }

    /**
     * Parse String from JSON to Array List of Game
     * @param response String with response from API
     * @return Array List of Game
     * @throws JSONException if something goes wrong with JSON parse
     */
    private List<Game> getGameDetails(String response) throws JSONException {

        JSONArray jsonGames = new JSONObject(response).getJSONObject("_embedded").getJSONArray("gameses");

        ArrayList<Game> games = new ArrayList<>();

        for (int j=0 ; j < jsonGames.length() ; j++) {

            games.add(new Game(
                    jsonGames.getJSONObject(j).getString("teamFirst"),
                    jsonGames.getJSONObject(j).getString("teamSecond"),
                    toInt(jsonGames.getJSONObject(j).getString("scoreTeamFirst")),
                    toInt(jsonGames.getJSONObject(j).getString("scoreTeamSecond")),
                    "Football",
                    "Fifa"));
        }

        return games;
    }

    private int toInt(String jsonString) {
        return Integer.parseInt(jsonString);
    }
}
