package com.prateek.fut17packopener.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.prateek.fut17packopener.PackDetailsAdapter;
import com.prateek.fut17packopener.PlayerManager;
import com.prateek.fut17packopener.R;
import com.prateek.fut17packopener.databinding.ActivityMyCardsBinding;
import com.prateek.fut17packopener.filter.FilterFragment;
import com.prateek.fut17packopener.models.FilterTypes;
import com.prateek.fut17packopener.models.Nation;
import com.prateek.fut17packopener.models.PackOpenerPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Filter;

public class MyCardsActivity extends AppCompatActivity implements PackDetailsAdapter.ItemClickListener, AAH_FabulousFragment.Callbacks, AAH_FabulousFragment.AnimationListener{

    private ActivityMyCardsBinding binding;
    private PackDetailsAdapter mAdapter;
    private ArrayList<PackOpenerPlayer> players;
    private FloatingActionButton fab;
    private FilterFragment filterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_cards);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        filterFragment = FilterFragment.newInstance();
        filterFragment.setParentFab(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterFragment.show(getSupportFragmentManager(), "FILTER_FRAGMENT");
            }
        });

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        binding.myCardList.setLayoutManager(layoutManager);
        binding.myCardList.setHasFixedSize(true);

        players = PlayerManager.getInstance().getUserUniquePlayers(this);
        mAdapter = new PackDetailsAdapter(this, players);
        binding.myCardList.setAdapter(mAdapter);
    }

    @Override
    public void onItemClicked(PackOpenerPlayer player) {
        binding.playerCardView.setValues(player);
        binding.playerDetailView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResult(Object result) {
        if (result.toString().equalsIgnoreCase("swiped_down")) {
            //do something or nothing
        } else {
            if (result != null) {
                Log.d("RESULT", "FILTERS");
                ArrayMap<FilterTypes, List<Object>> applied_filters = (ArrayMap<FilterTypes, List<Object>>) result;
                List<Object> nations = new ArrayList<>();
                List<Object> leagues = new ArrayList<>();
                for (Map.Entry<FilterTypes, List<Object>> entry : applied_filters.entrySet()) {
                    Log.d(((FilterTypes) entry.getKey()).getFilterName(), entry.getValue().toString());
                    if (entry.getKey().equals(FilterTypes.NATION)) {
                        nations = entry.getValue();
                    }
                    if (entry.getKey().equals(FilterTypes.LEAGUE)) {
                        leagues = entry.getValue();
                    }
                }
                players = PlayerManager.getInstance().getUserUniquePlayersFilter(this, nations, leagues);
            }
        }
    }

    @Override
    public void onOpenAnimationStart() {

    }

    @Override
    public void onOpenAnimationEnd() {

    }

    @Override
    public void onCloseAnimationStart() {

    }

    @Override
    public void onCloseAnimationEnd() {

    }

    @Override
    public void onBackPressed() {
        if (binding.playerDetailView.getVisibility() == View.VISIBLE) {
            binding.playerDetailView.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }
}
