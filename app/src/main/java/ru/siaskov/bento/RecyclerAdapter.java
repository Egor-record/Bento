package ru.siaskov.bento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import ru.siaskov.bento.databinding.GameItemLayoutBinding;

import static ru.siaskov.bento.MainActivity.TAG;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>  {

    private Context context;

    private List<Game> games = new ArrayList<>();

    public RecyclerAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        GameItemLayoutBinding gameItemLayoutBinding = DataBindingUtil.inflate(
              LayoutInflater.from(viewGroup.getContext()),
                                    R.layout.game_item_layout,
                                    viewGroup, false );

        MyViewHolder myViewHolder = new MyViewHolder(gameItemLayoutBinding);

        return myViewHolder;


    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Game game = games.get(position);
        holder.gameItemLayoutBinding.setGame(game);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        GameItemLayoutBinding gameItemLayoutBinding;


        MyViewHolder(@NonNull GameItemLayoutBinding itemView) {
            super(itemView.getRoot());
            gameItemLayoutBinding = itemView;



        }

    }


    public interface OnEntryClickListener {
        void onEntryClick(View view, int position);
    }



}


