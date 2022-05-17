package com.example.codedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codedex.models.Move;
import com.example.codedex.models.MoveList;
import com.example.codedex.models.Pokemon;


import java.util.ArrayList;

public class ListMovesAdapter  extends RecyclerView.Adapter<ListMovesAdapter.ViewHolder> {

    private ArrayList<MoveList> dataset;
    private Context context;
    private onItemListener onItemListener;

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

        holder.moveName.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
        holder.moveLearn.setText(learnMethod.substring(0, 1).toUpperCase() + learnMethod.substring(1));




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

        onItemListener onItemListener;

        public ViewHolder(@NonNull View itemView, onItemListener onItemListener) {
            super(itemView);


            moveName = (TextView) itemView.findViewById(R.id.moveName);
            moveLearn = (TextView) itemView.findViewById(R.id.moveLearn);
            moveLevel = (TextView) itemView.findViewById(R.id.moveLevel);
            movePower = (TextView) itemView.findViewById(R.id.movePower);
            movePP = (TextView) itemView.findViewById(R.id.movePP);

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


