package com.example.codedex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
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

        holder.moveName.setText(name.substring(0, 1).toUpperCase() + name.substring(1));

        //Glide.with(context).load("https://img.pokemondb.net/artwork/large/"+name+".jpg").into(holder.image);


        String url = chainList.getSpecies().getUrl();
        String[] urlSplice = url.split("/");
        int imgId = Integer.parseInt(urlSplice[urlSplice.length -1]);

        Glide.with(context)
                .asBitmap()
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+imgId+".png")
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.image.setImageBitmap(trim(resource));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });


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

    public void addOneItem(ChainLink chainLink){
        dataset.add(chainLink);

    }

    public  void  clearAllData(){
        dataset.clear();
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView moveName;
        private EvolutionChainRootAdapter evolutionChainRootAdapter;
        private ImageView image;

        onItemListener onItemListener;

        public ViewHolder(@NonNull View itemView, onItemListener onItemListener) {
            super(itemView);


            moveName = (TextView) itemView.findViewById(R.id.evolution_pokemon);
            image = (ImageView) itemView.findViewById(R.id.evolution_pokemon_image);

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


    //Trims transparent borders from image
    //https://stackoverflow.com/a/41785424
    static Bitmap trim(Bitmap source) {
        int firstX = 0, firstY = 0;
        int lastX = source.getWidth();
        int lastY = source.getHeight();
        int[] pixels = new int[source.getWidth() * source.getHeight()];
        source.getPixels(pixels, 0, source.getWidth(), 0, 0, source.getWidth(), source.getHeight());
        loop:
        for (int x = 0; x < source.getWidth(); x++) {
            for (int y = 0; y < source.getHeight(); y++) {
                if (pixels[x + (y * source.getWidth())] != Color.TRANSPARENT) {
                    firstX = x;
                    break loop;
                }
            }
        }
        loop:
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = firstX; x < source.getWidth(); x++) {
                if (pixels[x + (y * source.getWidth())] != Color.TRANSPARENT) {
                    firstY = y;
                    break loop;
                }
            }
        }
        loop:
        for (int x = source.getWidth() - 1; x >= firstX; x--) {
            for (int y = source.getHeight() - 1; y >= firstY; y--) {
                if (pixels[x + (y * source.getWidth())] != Color.TRANSPARENT) {
                    lastX = x;
                    break loop;
                }
            }
        }
        loop:
        for (int y = source.getHeight() - 1; y >= firstY; y--) {
            for (int x = source.getWidth() - 1; x >= firstX; x--) {
                if (pixels[x + (y * source.getWidth())] != Color.TRANSPARENT) {
                    lastY = y;
                    break loop;
                }
            }
        }
        return Bitmap.createBitmap(source, firstX, firstY, lastX - firstX, lastY - firstY);
    }




}


