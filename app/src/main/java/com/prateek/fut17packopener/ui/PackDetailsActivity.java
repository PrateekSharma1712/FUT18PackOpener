package com.prateek.fut17packopener.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.games.Games;
import com.prateek.fut17packopener.AppController;
import com.prateek.fut17packopener.CustomPreference;
import com.prateek.fut17packopener.PackDetailsAdapter;
import com.prateek.fut17packopener.PlayerManager;
import com.prateek.fut17packopener.R;
import com.prateek.fut17packopener.database.PlayerDAO;
import com.prateek.fut17packopener.databinding.ActivityPackDetailsBinding;
import com.prateek.fut17packopener.models.PackOpenerPlayer;

import ru.bullyboo.text_animation.TextCounter;

public class PackDetailsActivity extends AppCompatActivity implements PackDetailsAdapter.ItemClickListener {

    private ActivityPackDetailsBinding binding;
    private PackDetailsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pack_details);
        
        binding.packValue.setTypeface(AppController.getTypeface());

        if (AppController.getGoogleApiHelper().getGoogleApiClient().isConnected()) {
            Games.Achievements.unlock(AppController.getGoogleApiHelper().getGoogleApiClient(), getString(R.string.achievement_open_pack));
        }

        showPackValue();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        binding.packMembersList.setLayoutManager(layoutManager);
        binding.packMembersList.setHasFixedSize(true);

        mAdapter = new PackDetailsAdapter(this, this);
        binding.packMembersList.setAdapter(mAdapter);

        binding.saveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerDAO playerDAO = new PlayerDAO(PackDetailsActivity.this);
                playerDAO.saveUserPlayer(PlayerManager.getInstance().getPackPlayers());
                finish();
            }
        });
    }

    private void showPackValue() {
        int packValue = 0;
        for (PackOpenerPlayer player : PlayerManager.getInstance().getPackPlayers()) {
            packValue += player.getRating();
        }
        packValue = (int) Math.ceil(packValue/PlayerManager.getInstance().getPackPlayers().size());

        TextCounter.newBuilder()
                .setTextView(binding.packValue)
                .from(0)
                .to(packValue)
                .setDuration(packValue*20)
                .build()
                .start();

        CustomPreference.updateTotalPackValue(CustomPreference.getTotalPackValue() + (long)packValue);
        Games.Leaderboards.submitScore(AppController.getGoogleApiHelper().getGoogleApiClient(), getString(R.string.leaderboard_total_pack_value), CustomPreference.getTotalPackValue());
        Games.Leaderboards.submitScore(AppController.getGoogleApiHelper().getGoogleApiClient(), getString(R.string.leaderboard_best_pack), packValue);

        int bestPackValue = CustomPreference.getBestPackValue();
        if (bestPackValue < packValue) {
            CustomPreference.updateBestPackValue(packValue);
        }
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
    public void onItemClicked(final PackOpenerPlayer player) {
        long sellValue = 0;
        binding.playerCardView.setValues(player);
        binding.playerDetailView.setVisibility(View.VISIBLE);
        if (PlayerManager.getInstance().isPlayerDuplicate(PackDetailsActivity.this, player)) {
            binding.sell.setVisibility(View.VISIBLE);
            sellValue = PlayerManager.getInstance().getSellValue(player);
            binding.sell.setText("Sell for " + sellValue);
        }
        final long finalSellValue = sellValue;
        binding.sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.playerDetailView.setVisibility(View.GONE);
                PlayerManager.getInstance().deleteDuplicateCard(PackDetailsActivity.this, player);
                CustomPreference.updateCoins(CustomPreference.getCoins()+ finalSellValue);
                updateAdapter(player);
            }
        });
    }

    private void updateAdapter(PackOpenerPlayer player) {
        mAdapter.removePlayer(player);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppController.getGoogleApiHelper().getGoogleApiClient().isConnected()) {
            Games.setViewForPopups(AppController.getGoogleApiHelper().getGoogleApiClient(), getWindow().getDecorView());
        }
    }
}
