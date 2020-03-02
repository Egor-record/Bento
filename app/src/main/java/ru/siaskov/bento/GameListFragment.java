package ru.siaskov.bento;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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



    public static final String TAG = "MyTag";
    private StringRequest stringRequest; // Assume this exists.
    private RequestQueue requestQueue;  // Assume this exists.
    private RecyclerView mGamesRecyclerView;
    private GameAdapter mAdapter;
    private Context context;
    private static String url = "http://192.168.43.130:8080/api/v1/gameses";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.game_list_layout, container, false);

        mGamesRecyclerView = (RecyclerView) view
                .findViewById(R.id.my_recycler_view);

        mGamesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI(getActivity());

        return view;

    }

    private void updateUI(FragmentActivity activity)  {


        RequestQueue queue = Volley.newRequestQueue(activity);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            GameLab gameLab  = GameLab.get(getActivity(), response);

                            System.out.println(gameLab);

                            List<Game> games = gameLab.getGames();
                            mAdapter = new GameAdapter(games);
                            mGamesRecyclerView.setAdapter(mAdapter);


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

    private class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mFistTeamNameTextView;
        private TextView mSecondTeamNameTextView;
        private TextView mFirstTeamScoreTextView;
        private TextView mSecondTeamScoreTextView;
        private Game mGame;


        public GameHolder(@NonNull View itemView) {
            super(itemView);

            mFistTeamNameTextView = (TextView) itemView.findViewById(R.id.teamFirst);
            mSecondTeamNameTextView = (TextView) itemView.findViewById(R.id.teamSecond);
            mFirstTeamScoreTextView = (TextView) itemView.findViewById(R.id.scoreFirstTeam);
            mSecondTeamScoreTextView = (TextView) itemView.findViewById(R.id.scoreSecondTeam);

            itemView.setOnClickListener(this);

        }

        public void bindGame(Game game) {
            mGame = game;

            mFistTeamNameTextView.setText(mGame.getTeamFirst());
            mFirstTeamScoreTextView.setText(String.valueOf(mGame.getScoreFirstTeam()));

            mSecondTeamNameTextView.setText(mGame.getTeamSecond());
            mSecondTeamScoreTextView.setText(String.valueOf(mGame.getScoreSecondTeam()));


        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mGame.getTeamFirst(), Toast.LENGTH_SHORT).show();
          //  Intent intent = GameActivity.newIntent(getActivity(), mGame.getId());

            Intent intent = new Intent(getActivity(), GameActivity.class);
            intent.putExtra("id", mGame.getTeamFirst());
            startActivity(intent);
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

            View view = layoutInflater.inflate(R.layout.game_item_layout, parent, false);

            return new GameHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GameHolder holder, int position) {
            Game game = mGames.get(position);
            holder.bindGame(game);

        }

        @Override
        public int getItemCount() {
            return mGames.size();
        }
    }




}
