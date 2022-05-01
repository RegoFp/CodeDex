package com.example.codedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.codedex.models.Pokemon;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder>{

    private ArrayList<Pokemon> dataset;
    private Context context;

    //TODO https://www.youtube.com/watch?v=2I1NkJNBz9M&t=183s


    public ListPokemonAdapter(Context context){
        this.context = context;
        dataset=new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = dataset.get(position);
        holder.pokeName.setText(pokemon.getName()) ;

        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokemon.getId()+".png")
                .into(holder.pokefoto);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public void addPokemonItem(ArrayList<Pokemon> pokemonList) {
        dataset.addAll(pokemonList);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView pokeName;
        private ImageView pokefoto;

        public ViewHolder(View itemView){
            super(itemView);

            pokeName = (TextView) itemView.findViewById(R.id.itemName);
            pokefoto = (ImageView) itemView.findViewById(R.id.itemImage);

        }

    }

}
