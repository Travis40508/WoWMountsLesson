package com.elkcreek.rodneytressler.wowmounts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by rodneytressler on 5/3/18.
 */

public class MountsFragment extends Fragment {

    protected RecyclerView recyclerView;

    private MountsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mounts, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView = getView().findViewById(R.id.recycler_view);
        List<WoWApi.Creatures> creaturesList = getArguments().getParcelableArrayList(MainActivity.MOUNTS_TAG);
        populateRecyclerView(creaturesList);
    }

    private void populateRecyclerView(List<WoWApi.Creatures> creaturesList) {
        adapter = new MountsAdapter(creaturesList);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(llm);
        adapter.notifyDataSetChanged();
    }

    public static MountsFragment newInstance() {

        Bundle args = new Bundle();

        MountsFragment fragment = new MountsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
