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
import com.example.codedex.RetrofitInstance;
import com.example.codedex.models.MoveData;
import com.example.codedex.models.MoveList;
import com.example.codedex.pokeapi.PokemonClient;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ListMovesAdapter  extends RecyclerView.Adapter<ListMovesAdapter.ViewHolder> {

    private ArrayList<MoveList> dataset;
    private Context context;
    private onItemListener onItemListener;
    private Retrofit retrofit;

    public ListMovesAdapter(Context context, onItemListener onItemListener){
        this.context = context;
        dataset= new ArrayList<>();
        this.onItemListener = onItemListener;

        // this.mOnItemListener = onItemListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_move,parent,false);
        RetrofitInstance retrofitInstance = new RetrofitInstance();
        retrofitInstance.startRetrofit();
        retrofit = retrofitInstance.retrofit;

        return new ViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MoveList movelist = dataset.get(position);
        String name = movelist.getMove().getName();
        String learnMethod = movelist.getVersion_group_details().get(0).getMove_learn_method().getName();

        int level = movelist.getVersion_group_details().get(0).getLevel_learned_at();
        if(level == 0){
            holder.moveLevel.setText("-");

        }else{
            holder.moveLevel.setText(""+level);

        }

        holder.moveName.setText(name.substring(0, 1).toUpperCase() + name.substring(1).replace("-"," "));



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

    public void addMoveItem(ArrayList<MoveList> MoveList) {
        dataset.addAll(MoveList);
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

        onItemListener onItemListener;

        public ViewHolder(@NonNull View itemView, onItemListener onItemListener) {
            super(itemView);


            moveName = (TextView) itemView.findViewById(R.id.moveName);
            moveLevel = (TextView) itemView.findViewById(R.id.moveLevel);
            movePower = (TextView) itemView.findViewById(R.id.movePower);
            movePP = (TextView) itemView.findViewById(R.id.movePP);
            type = (ImageView) itemView.findViewById(R.id.moveType);


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


