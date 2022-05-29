package com.example.codedex.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codedex.R;
import com.example.codedex.models.Type;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class ListTypeRelationsAdapter extends RecyclerView.Adapter<ListTypeRelationsAdapter.ViewHolder>{

    private ArrayList<Type> dataset;
    private Context context;
    private onItemListener mOnItemListener;
    private Animation animation;
    private Retrofit retrofit;

    //TODO https://www.youtube.com/watch?v=2I1NkJNBz9M&t=183s


    public ListTypeRelationsAdapter(Context context, onItemListener onItemListener){
        this.context = context;
        dataset=new ArrayList<>();
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        animation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.bounce);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_relation,parent,false);
        return new ViewHolder(view, mOnItemListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Type type = dataset.get(position);
        String typeName = type.getName();

        String uri = "@drawable/type_"+typeName;  // where myresource (without the extension) is the file

        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);
        holder.typeImg.setImageDrawable(res);


    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public ArrayList<Type> getCurrentList(){
        return dataset;
    }


    public void addItem(ArrayList<Type> pokemonList) {
        dataset.addAll(pokemonList);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView typeImg;

        onItemListener onItemListener;

        public ViewHolder(View itemView, onItemListener onItemListener){
            super(itemView);

            typeImg = (ImageView) itemView.findViewById(R.id.itemTypeRelation);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener. onItemClick(getAdapterPosition());

            //TODO No reconoce bien cual esta siendo clicado
        }
    }

    public interface onItemListener{
        void onItemClick(int position);

    }

    private void typeSelector(ImageView imageView, String type) {




    }

}
