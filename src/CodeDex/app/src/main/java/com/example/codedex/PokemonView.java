package com.example.codedex;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.codedex.models.PokemonData;
import com.example.codedex.models.SpecieData;
import com.example.codedex.pokeapi.PokemonClient;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.parceler.Parcels;

import java.io.InputStream;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonView extends AppCompatActivity {

    private PokemonData pokemonData;
    private Retrofit retrofit;
    private SpecieData specieData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_view);


        Intent intent = getIntent();

        pokemonData = Parcels.unwrap(getIntent().getParcelableExtra("pokemon"));


        //Add data to toplayer
        //Cambiar color topLayer
        ConstraintLayout topLayer = (ConstraintLayout) findViewById(R.id.topLayer);
        topLayer.setBackgroundResource(R.color.pokeRed);

        TextView tv1 = (TextView)findViewById(R.id.pokeName);
        String name = pokemonData.getName();
        tv1.setText(name.substring(0, 1).toUpperCase() + name.substring(1));

        TextView tv2 = (TextView)findViewById(R.id.pokeId);
        tv2.setText("#"+String.format("%03d", pokemonData.getId()));

        ImageView ivType1 = (ImageView) findViewById(R.id.pokeType1);
        ivType1.setImageResource(R.drawable.type_fire);

        typeSelector(ivType1,pokemonData.getTypes().get(0).getType().getName());


        ImageView ivType2 = (ImageView) findViewById(R.id.pokeType2);
        if(pokemonData.getTypes().size()>1){

            ivType2.setVisibility(View.VISIBLE);
            typeSelector(ivType2,pokemonData.getTypes().get(1).getType().getName());

        }else{
            ivType2.setVisibility(View.GONE);


        }


        ImageView imageView = (ImageView) findViewById(R.id.pokeImage);

        //arte
        Glide.with(this).load("https://img.pokemondb.net/artwork/large/"+pokemonData.getName()+".jpg").into(imageView);

        //sprites
        //Glide.with(this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokemonData.getId()+".png").into(imageView);





        //Configure screens
        ViewPager2 viewPager2 = (ViewPager2) findViewById(R.id.viewpagerPokeView);

        viewPager2.setAdapter(
                new viewAdapter(this)
        );

        String[]  tabs ={"details","moves"};

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutPokeView);
        new TabLayoutMediator(
                tabLayout,
                viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                            tab.setText(tabs[position]);

                    }
                }
        ).attach();

    }

    private void typeSelector(ImageView imageView, String type) {

        String uri = "@drawable/type_"+type;  // where myresource (without the extension) is the file

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());


        Drawable res = getResources().getDrawable(imageResource);
        imageView.setImageDrawable(res);



    }


    class viewAdapter extends FragmentStateAdapter{


        public viewAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public viewAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        public viewAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            switch (position) {
                case 0:
                    return new PokeViewFragment(pokemonData);
                case 1:
                    return new PokeViewMovesFragment();
                default:
                    return new PokeViewMovesFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}


