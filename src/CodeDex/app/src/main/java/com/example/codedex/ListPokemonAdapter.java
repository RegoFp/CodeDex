package com.example.codedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private onItemListener mOnItemListener;
    private Animation animation;

    //TODO https://www.youtube.com/watch?v=2I1NkJNBz9M&t=183s


    public ListPokemonAdapter(Context context, onItemListener onItemListener){
        this.context = context;
        dataset=new ArrayList<>();
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        animation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.bounce);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon,parent,false);

        return new ViewHolder(view, mOnItemListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = dataset.get(position);
        holder.pokeName.setText(pokemon.getName()) ;
        holder.pokeId.setText("#"+String.format("%03d", pokemon.getId()));


        Animation slide = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide);
        holder.itemView.startAnimation(slide);



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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pokeName;
        private ImageView pokefoto;
        private TextView pokeId;

        onItemListener onItemListener;

        public ViewHolder(View itemView, onItemListener onItemListener){
            super(itemView);

            pokeName = (TextView) itemView.findViewById(R.id.itemName);
            pokefoto = (ImageView) itemView.findViewById(R.id.itemImage);
            pokeId = (TextView) itemView.findViewById(R.id.itemId);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());

            //TODO No reconoce bien cual esta siendo clicado
            v.startAnimation(animation);
        }
    }

    public interface onItemListener{
        void onItemClick(int position);

    }

}
