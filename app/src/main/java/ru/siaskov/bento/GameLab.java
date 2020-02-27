package ru.siaskov.bento;

import android.content.Context;
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
import java.util.List;
import java.util.UUID;

import static ru.siaskov.bento.GameListFragment.TAG;


/**
 * Singleton to create Games;
 */
public class GameLab {

    private static GameLab sGameLab;

    private List<Game> mGames;


    public static GameLab get(Context context, String res) throws JSONException {

        if (sGameLab == null) {
            sGameLab = new GameLab(context, res);
        }

        return sGameLab;
    }

    private GameLab(Context context, String res) throws JSONException {

        mGames = new ArrayList<>();
        mGames = getGameDetails(res);

    }

    public List<Game> getGames() {
        return mGames;
    }

    public Game getGame(UUID id) {
        for (Game game : mGames) {
            if (game.getId().equals(id)) {
                return game;
            }
        }
        return null;
    }


    /**
     * Parse String from JSON to Array List of Game
     * @param response String with response from API
     * @return Array List of Game
     * @throws JSONException if something goes wrong with JSON parse
     */
    private static List<Game> getGameDetails(String response) throws JSONException {

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

    private static int toInt(String jsonString) {
        return Integer.parseInt(jsonString);
    }
}
