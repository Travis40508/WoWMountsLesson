package com.elkcreek.rodneytressler.wowmounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rodneytressler on 5/3/18.
 */

public interface WoWApi {
    //query fields (for mounts) and the api key
    @GET("/wow/character/{realm}/{name}")
    Call<Player> getPlayerData(@Path("realm") String realm, @Path("name") String name, @Query("fields") String fields, @Query("apikey") String apiKey);


    class Player {
        @SerializedName("mounts")
        @Expose private Mounts mounts;

        public Mounts getMounts() {
            return mounts;
        }
    }

    class Mounts {
        @SerializedName("collected")
        @Expose private List<Creatures> creaturesList;

        public List<Creatures> getCreaturesList() {
            return creaturesList;
        }
    }

    class Creatures implements Parcelable{
        @SerializedName("name")
        @Expose private String name;

        protected Creatures(Parcel in) {
            name = in.readString();
        }

        public static final Creator<Creatures> CREATOR = new Creator<Creatures>() {
            @Override
            public Creatures createFromParcel(Parcel in) {
                return new Creatures(in);
            }

            @Override
            public Creatures[] newArray(int size) {
                return new Creatures[size];
            }
        };

        public String getName() {
            return name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
        }
    }
}
