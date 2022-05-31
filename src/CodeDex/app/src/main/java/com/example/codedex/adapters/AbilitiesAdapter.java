package com.example.codedex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codedex.R;
import com.example.codedex.models.Ability;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AbilitiesAdapter extends RecyclerView.Adapter<AbilitiesAdapter.ViewHolder> {

    private ArrayList<Ability> dataset;
    private ArrayList<Ability> datasetOriginal;
    private Context context;
    private onItemListener onItemListener;

    public AbilitiesAdapter(Context context, onItemListener onItemListener){
        this.context = context;
        dataset= new ArrayList<>();
        datasetOriginal=new ArrayList<>();
        this.onItemListener = onItemListener;

        // this.mOnItemListener = onItemListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ability,parent,false);
        return new ViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Ability abilitiesList = dataset.get(position);
        holder.Name.setText(abilitiesList.getName().toUpperCase().replace("-"," "));


        //holder.pokeId.setText("#"+String.format("%03d", pokemon.getId()));


        //Animation slide = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide);
        //holder.itemView.startAnimation(slide);


    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addMoveItem(ArrayList<Ability> abilities) {
        dataset.addAll(abilities);
        datasetOriginal.addAll(abilities);
        notifyDataSetChanged();

    }

    public ArrayList<Ability> getCurrentList(){
        return dataset;
    }

    public void filtderData(String search){
        if (search.length()==0){
            dataset.clear();
            dataset.addAll(datasetOriginal);
        }else{
            dataset.clear();
            dataset.addAll(datasetOriginal);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Ability> collection = dataset.stream()
                        .filter(i -> i.getName().toLowerCase().contains(search.replace(' ','-').toLowerCase()))
                        .collect(Collectors.toList());

                dataset.clear();
                dataset.addAll(collection);
            }else{
                for (Ability a: datasetOriginal){
                    if(a.getName().toLowerCase().contains(search.toLowerCase())){
                        dataset.add(a);
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

        private TextView Name;


        onItemListener onItemListener;

        public ViewHolder(@NonNull View itemView, onItemListener onItemListener) {
            super(itemView);


            Name = (TextView) itemView.findViewById(R.id.itemAbilityName);



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


