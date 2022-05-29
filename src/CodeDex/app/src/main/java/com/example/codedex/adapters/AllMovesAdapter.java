package com.example.codedex.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codedex.R;
import com.example.codedex.models.Move;
import com.example.codedex.models.MoveData;
import com.example.codedex.pokeapi.PokemonClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllMovesAdapter extends RecyclerView.Adapter<AllMovesAdapter.ViewHolder> {

    private ArrayList<Move> dataset;
    private ArrayList<Move> datasetOriginal;
    private Context context;
    private onItemListener onItemListener;
    private Retrofit retrofit;

    public AllMovesAdapter(Context context, onItemListener onItemListener){
        this.context = context;
        dataset= new ArrayList<>();
        datasetOriginal=new ArrayList<>();
        this.onItemListener = onItemListener;

        // this.mOnItemListener = onItemListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_item_move,parent,false);
        startRetrofit();
        return new ViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Move movelist = dataset.get(position);
        String name = movelist.getName();
        holder.moveName.setText(name.substring(0, 1).toUpperCase() + name.substring(1).replace("-"," "));
        holder.movePP.setVisibility(View.GONE);
        holder.movePower.setVisibility(View.GONE);



        PokemonClient client = retrofit.create(PokemonClient.class);
        Call<MoveData> call = client.getMoveData(name);
        call.enqueue(new Callback<MoveData>() {
            @Override
            public void onResponse(Call<MoveData> call, Response<MoveData> response) {
                MoveData moveData = response.body();

                String type = moveData.getType().getName();
                String uri = "@drawable/type_"+type;  // where myresource (without the extension) is the file
                int imageResource = holder.itemView.getContext().getResources().getIdentifier(uri, null, holder.itemView.getContext().getPackageName());
                Drawable res = holder.itemView.getContext().getResources().getDrawable(imageResource);
                holder.type.setImageDrawable(res);


                String dmg = moveData.getDamage_class().getName();
                String uri2 = "@drawable/dmg_"+dmg;  // where myresource (without the extension) is the file
                int imageResource2 = holder.itemView.getContext().getResources().getIdentifier(uri2, null, holder.itemView.getContext().getPackageName());
                Drawable res2 = holder.itemView.getContext().getResources().getDrawable(imageResource2);
                holder.dmg.setImageDrawable(res2);


                holder.movePP.setText(""+moveData.getPp());
                holder.movePower.setText(""+moveData.getPower());

                //getActivity().overridePendingTransition(R.anim.slide_up,R.anim.nothing);
            }

            @Override
            public void onFailure(Call<MoveData> call, Throwable t) {

            }
        });

        //holder.pokeId.setText("#"+String.format("%03d", pokemon.getId()));


        //Animation slide = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide);
        //holder.itemView.startAnimation(slide);


    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public ArrayList<Move> getCurrentList(){

        return dataset;
    }

    public void addMoveItem(ArrayList<Move> MoveList) {
        dataset.addAll(MoveList);
        datasetOriginal.addAll(MoveList);
        notifyDataSetChanged();

    }

    public void filtderData(String search){
        if (search.length()==0){
            dataset.clear();
            dataset.addAll(datasetOriginal);
        }else{
            dataset.clear();
            dataset.addAll(datasetOriginal);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Move> collection = dataset.stream()
                        .filter(i -> i.getName().toLowerCase().contains(search.replace(' ','-').toLowerCase()))
                        .collect(Collectors.toList());

                dataset.clear();
                dataset.addAll(collection);
            }else{
                for (Move m: datasetOriginal){
                    if(m.getName().toLowerCase().contains(search.toLowerCase())){
                        dataset.add(m);
                    }

                }

            }

        }

        notifyDataSetChanged();
    }

    public  void  clearAllData(){
        dataset.clear();
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView moveName;
        private TextView moveLearn;
        private TextView moveLevel;
        private TextView movePower;
        private TextView movePP;
        private ImageView type;
        private ImageView dmg;

        onItemListener onItemListener;

        public ViewHolder(@NonNull View itemView, onItemListener onItemListener) {
            super(itemView);


            moveName = (TextView) itemView.findViewById(R.id.moveName);
            moveLevel = (TextView) itemView.findViewById(R.id.moveLevel);
            movePower = (TextView) itemView.findViewById(R.id.movePower);
            movePP = (TextView) itemView.findViewById(R.id.movePP);
            type = (ImageView) itemView.findViewById(R.id.moveType);
            dmg = (ImageView) itemView.findViewById(R.id.moveDmg);


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


