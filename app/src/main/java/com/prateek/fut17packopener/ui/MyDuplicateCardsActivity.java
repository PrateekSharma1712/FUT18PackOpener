package com.prateek.fut17packopener.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.games.Games;
import com.prateek.fut17packopener.AppController;
import com.prateek.fut17packopener.CustomPreference;
import com.prateek.fut17packopener.GamesService;
import com.prateek.fut17packopener.PackDetailsAdapter;
import com.prateek.fut17packopener.PlayerManager;
import com.prateek.fut17packopener.R;
import com.prateek.fut17packopener.databinding.ActivityMyDuplicateCardsBinding;
import com.prateek.fut17packopener.models.PackOpenerPlayer;

import java.util.ArrayList;

public class MyDuplicateCardsActivity extends AppCompatActivity implements PackDetailsAdapter.ItemClickListener {

    private ActivityMyDuplicateCardsBinding binding;
    private PackDetailsAdapter mAdapter;
    private long totalSellValue;
    private ArrayList<PackOpenerPlayer> players;
    private Intent gamesServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_duplicate_cards);

        gamesServiceIntent = new Intent(MyDuplicateCardsActivity.this, GamesService.class);
        gamesServiceIntent.setAction(GamesService.ACHIEVEMENT_SELL_PLAYER);

        binding.sellingValue.setTypeface(AppController.getTypeface());
        binding.sellAll.setTypeface(AppController.getTypeface());
        binding.sell.setTypeface(AppController.getTypeface());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        binding.myDuplicateCardList.setLayoutManager(layoutManager);
        binding.myDuplicateCardList.setHasFixedSize(true);

        players = new ArrayList<>(PlayerManager.getInstance().getDuplicateCards(MyDuplicateCardsActivity.this));

        mAdapter = new PackDetailsAdapter(this, players);
        binding.myDuplicateCardList.setAdapter(mAdapter);

        calculateSellValue();
        binding.sellingValue.setText(String.valueOf(totalSellValue));

        binding.sellAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(gamesServiceIntent);
                PlayerManager.getInstance().deleteAllDuplicateCards(MyDuplicateCardsActivity.this, players);
                CustomPreference.updateCoins(CustomPreference.getCoins()+totalSellValue);
                finish();
            }
        });
    }

    public void calculateSellValue() {
        totalSellValue = 0;
        for (PackOpenerPlayer player : players) {
            totalSellValue += PlayerManager.getInstance().getSellValue(player);
        }
    }

    @Override
    public void onItemClicked(final PackOpenerPlayer player) {
        final long sellValue = PlayerManager.getInstance().getSellValue(player);
        binding.playerCardView.setValues(player);
        binding.playerDetailView.setVisibility(View.VISIBLE);
        binding.sell.setText("Sell for " + sellValue);
        binding.sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.playerDetailView.setVisibility(View.GONE);
                PlayerManager.getInstance().deleteDuplicateCard(MyDuplicateCardsActivity.this, player);
                CustomPreference.updateCoins(CustomPreference.getCoins()+sellValue);
                startService(gamesServiceIntent);
                updateAdapter();
            }
        });
    }

    private void updateAdapter() {
        players = new ArrayList<>(PlayerManager.getInstance().getDuplicateCards(MyDuplicateCardsActivity.this));
        calculateSellValue();
        binding.sellingValue.setText(String.valueOf(totalSellValue));
        mAdapter.updateData(players);
    }

    @Override
    public void onBackPressed() {
        if (binding.playerDetailView.getVisibility() == View.VISIBLE) {
            binding.playerDetailView.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppController.getGoogleApiHelper().isConnected()) {
            Games.setViewForPopups(AppController.getGoogleApiHelper().getGoogleApiClient(), getWindow().getDecorView());
        }
    }
}
