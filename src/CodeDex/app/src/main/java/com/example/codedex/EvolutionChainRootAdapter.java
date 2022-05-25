package com.example.codedex;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codedex.models.ChainLink;
import com.example.codedex.models.MoveData;
import com.example.codedex.models.MoveList;
import com.example.codedex.pokeapi.PokemonClient;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EvolutionChainRootAdapter extends RecyclerView.Adapter<EvolutionChainRootAdapter.ViewHolder> {

    private ArrayList<ChainLink> dataset;
    private Context context;
    private onItemListener onItemListener;
    private Retrofit retrofit;


    public EvolutionChainRootAdapter(Context context, onItemListener onItemListener){
        this.context = context;
        dataset= new ArrayList<>();
        this.onItemListener = onItemListener;

        // this.mOnItemListener = onItemListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evolution_chain,parent,false);

        return new ViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChainLink chainList = dataset.get(position);
        String name = chainList.getSpecies().getName();

        holder.moveName.setText(name);

        ArrayList<ChainLink> chainLinks = chainList.getEvolves_to();

        holder.evolutionChainRootAdapter.addMoveItem(chainLinks);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addMoveItem(ArrayList<ChainLink> MoveList) {
        dataset.addAll(MoveList);
        notifyDataSetChanged();

    }

    public  void  clearAllData(){
        dataset.clear();
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView moveName;
        private EvolutionChainRootAdapter evolutionChainRootAdapter;

        onItemListener onItemListener;

        public ViewHolder(@NonNull View itemView, onItemListener onItemListener) {
            super(itemView);


            moveName = (TextView) itemView.findViewById(R.id.evolution_pokemon);


            RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.evolution_pokemon_son);


            evolutionChainRootAdapter= new EvolutionChainRootAdapter(itemView.getContext(),null);
            recyclerView.setAdapter(evolutionChainRootAdapter);
            recyclerView.setHasFixedSize(true);

            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            recyclerView.setLayoutManager(layoutManager);



            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());


        }
    }

    public interface onItemListener{
        void onItemClick(int position);

    }




}


