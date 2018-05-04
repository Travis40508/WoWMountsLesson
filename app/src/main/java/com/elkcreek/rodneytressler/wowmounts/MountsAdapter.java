package com.elkcreek.rodneytressler.wowmounts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rodneytressler on 5/3/18.
 */

public class MountsAdapter extends RecyclerView.Adapter<MountsAdapter.MountsViewHolder> {

    private List<WoWApi.Creatures> creaturesList;

    public MountsAdapter(List<WoWApi.Creatures> creaturesList) {
        this.creaturesList = creaturesList;
    }


    @Override
    public MountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_creature, parent, false);
        return new MountsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MountsViewHolder holder, int position) {
        holder.bindCreature(creaturesList.get(position));
    }

    @Override
    public int getItemCount() {
        return creaturesList.size();
    }

    public class MountsViewHolder extends RecyclerView.ViewHolder {

        protected TextView creatureName;

        public MountsViewHolder(View itemView) {
            super(itemView);
            creatureName = itemView.findViewById(R.id.text_creature_name);
        }

        public void bindCreature(WoWApi.Creatures creature) {
            creatureName.setText(creature.getName());
        }
    }
}
