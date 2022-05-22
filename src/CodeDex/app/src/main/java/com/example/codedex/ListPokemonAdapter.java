package com.example.codedex;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
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
import com.example.codedex.models.MoveData;
import com.example.codedex.models.Pokemon;
import com.example.codedex.models.PokemonData;
import com.example.codedex.models.Type;
import com.example.codedex.models.TypesList;
import com.example.codedex.pokeapi.PokemonClient;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder>{

    private ArrayList<Pokemon> dataset;
    private Context context;
    private onItemListener mOnItemListener;
    private Animation animation;
    private Retrofit retrofit;

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
        startRetrofit();
        return new ViewHolder(view, mOnItemListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = dataset.get(position);
        String name = pokemon.getName();
        holder.pokeName.setText(name.substring(0, 1).toUpperCase() + name.substring(1).replace("-"," "));

        if(pokemon.getId()>10000){
            holder.pokeId.setText("   ---");

        }else{

            holder.pokeId.setText("#"+String.format("%03d", pokemon.getId()));
        }





        Animation slide = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide);
        holder.itemView.startAnimation(slide);

        //Pokemon's image
        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokemon.getId()+".png")
                .into(holder.pokefoto);




        PokemonClient client =  retrofit.create(PokemonClient.class);
        Call<PokemonData> call = client.getPokemonById(""+pokemon.getId());
        call.enqueue(new Callback<PokemonData>() {
            @Override
            public void onResponse(Call<PokemonData> call, Response<PokemonData> response) {
                PokemonData pokemonData = response.body();

                String uri = "@drawable/type_"+pokemonData.getTypes().get(0).getType().getName();  // where myresource (without the extension) is the file
                int imageResource = holder.itemView.getContext().getResources().getIdentifier(uri, null, holder.itemView.getContext().getPackageName());
                Drawable res = holder.itemView.getContext().getResources().getDrawable(imageResource);
                holder.ivType1.setImageDrawable(res);


                if(pokemonData.getTypes().size()>1){

                    holder.ivType2.setVisibility(View.VISIBLE);
                    String uri2 = "@drawable/type_"+pokemonData.getTypes().get(1).getType().getName();  // where myresource (without the extension) is the file
                    int imageResource2 = holder.itemView.getContext().getResources().getIdentifier(uri2, null, holder.itemView.getContext().getPackageName());
                    Drawable res2 = holder.itemView.getContext().getResources().getDrawable(imageResource2);
                    holder.ivType2.setImageDrawable(res2);

                }else{
                    holder.ivType2.setVisibility(View.GONE);


                }


            }

            @Override
            public void onFailure(Call<PokemonData> call, Throwable t) {

            }
        });

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
        ImageView ivType1;
        ImageView ivType2;

        onItemListener onItemListener;

        public ViewHolder(View itemView, onItemListener onItemListener){
            super(itemView);

            pokeName = (TextView) itemView.findViewById(R.id.itemName);
            pokefoto = (ImageView) itemView.findViewById(R.id.itemImage);
            pokeId = (TextView) itemView.findViewById(R.id.itemId);
            ivType1 = (ImageView) itemView.findViewById(R.id.itemPokeType1);
            ivType2 = (ImageView) itemView.findViewById(R.id.itemPokeType2);

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


    private void startRetrofit() {
        //Iniciar Retrofit
        String API_BASE_URL = "https://pokeapi.co/api/v2/";

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();


    }



}
