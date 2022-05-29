package com.example.codedex.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codedex.R;
import com.example.codedex.RetrofitInstance;
import com.example.codedex.TypeViewActivity;
import com.example.codedex.models.Move;
import com.example.codedex.models.MoveData;
import com.example.codedex.models.Nature;
import com.example.codedex.models.NatureData;
import com.example.codedex.models.TypeData;
import com.example.codedex.pokeapi.PokemonClient;

import org.parceler.Parcels;

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

public class AllNaturesAdapter extends RecyclerView.Adapter<AllNaturesAdapter.ViewHolder> {

    private ArrayList<Nature> dataset;
    private ArrayList<Nature> datasetOriginal;
    private Context context;
    private onItemListener onItemListener;
    RetrofitInstance retrofitInstance;

    public AllNaturesAdapter(Context context, onItemListener onItemListener){
        this.context = context;
        dataset= new ArrayList<>();
        datasetOriginal=new ArrayList<>();
        this.onItemListener = onItemListener;


        // this.mOnItemListener = onItemListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nature,parent,false);

        return new ViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Nature movelist = dataset.get(position);
        String name = movelist.getName();
        String id = String.valueOf(movelist.getId());

        retrofitInstance = new RetrofitInstance();
        retrofitInstance.startRetrofit();

        holder.natureName.setText(name.toUpperCase());

        PokemonClient client =  retrofitInstance.retrofit.create(PokemonClient.class);
        Call<NatureData> call = client.getNature(id);
        call.enqueue(new Callback<NatureData>() {
            @Override
            public void onResponse(Call<NatureData> call, Response<NatureData> response) {

                NatureData natureData = response.body();
                if(natureData.getIncreased_stat()!=null){
                    holder.natureStrong.setText(natureData.getIncreased_stat().getName());
                    holder.natureWeak.setText(natureData.getDecreased_stat().getName());
                }else{
                    holder.linearLayout.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<NatureData> call, Throwable t) {

            }
        });






    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public ArrayList<Nature> getCurrentList(){

        return dataset;
    }

    public void addMoveItem(ArrayList<Nature> MoveList) {
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
                List<Nature> collection = dataset.stream()
                        .filter(i -> i.getName().toLowerCase().contains(search.replace(' ','-').toLowerCase()))
                        .collect(Collectors.toList());

                dataset.clear();
                dataset.addAll(collection);
            }else{
                for (Nature m: datasetOriginal){
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

        private TextView natureName;
        private TextView natureStrong;
        private TextView natureWeak;
        private LinearLayout linearLayout;


        onItemListener onItemListener;

        public ViewHolder(@NonNull View itemView, onItemListener onItemListener) {
            super(itemView);


            natureName = (TextView) itemView.findViewById(R.id.itemNatureName);
            natureStrong = (TextView) itemView.findViewById(R.id.itemNatureStrong);
            natureWeak = (TextView) itemView.findViewById(R.id.itemNatureWeak);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.itemNatureLine);

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


