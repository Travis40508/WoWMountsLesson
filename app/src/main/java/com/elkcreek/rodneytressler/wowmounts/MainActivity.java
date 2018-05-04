package com.elkcreek.rodneytressler.wowmounts;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private MountsFragment mountsFragment;
    private WoWApi wowApi;
    public static final String MOUNTS_TAG = "mounts_tag";
    private static final String FRAGMENT_TAG = "fragment_tag";

    protected EditText nameInput;

    protected EditText realmInput;

    protected ProgressBar progressBar;

    protected Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //allows you to rotate screen without a crash
        if(savedInstanceState != null) {
            mountsFragment = (MountsFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        }

        buildViews();
        buildRetrofit();
    }

    private void buildViews() {
        nameInput = findViewById(R.id.input_name);
        realmInput = findViewById(R.id.input_realm);
        progressBar = findViewById(R.id.progress_bar);
        submitButton = findViewById(R.id.button_submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                mountsFragment = MountsFragment.newInstance();
                String playerName = nameInput.getText().toString();
                String realm = realmInput.getText().toString();
                String fields = "mounts";
                String apiKey = getResources().getString(R.string.api_key);

                if(!playerName.isEmpty() && !realm.isEmpty()) {
                    wowApi.getPlayerData(realm, playerName, fields, apiKey).enqueue(new Callback<WoWApi.Player>() {
                        @Override
                        public void onResponse(Call<WoWApi.Player> call, Response<WoWApi.Player> response) {
                            if(response.isSuccessful()) {

                                //doesn't try to show the fragment until it already has the list
                                List<WoWApi.Creatures> creaturesList = response.body().getMounts().getCreaturesList();
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList(MOUNTS_TAG, (ArrayList<? extends Parcelable>) creaturesList);
                                mountsFragment.setArguments(bundle);
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, mountsFragment, FRAGMENT_TAG).commit();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                toastError();
                            }
                        }

                        @Override
                        public void onFailure(Call<WoWApi.Player> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void toastError() {
        Toast.makeText(this, "Error - please try again!", Toast.LENGTH_SHORT).show();
    }

    private void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        wowApi = retrofit.create(WoWApi.class);
    }

    @Override
    public void onBackPressed() {
        if(mountsFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(mountsFragment).commit();
        } else {
            super.onBackPressed();
        }
    }
}
