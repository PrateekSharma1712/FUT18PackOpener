package com.prateek.fut17packopener.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.games.Games;
import com.prateek.fut17packopener.AppController;
import com.prateek.fut17packopener.PackType;
import com.prateek.fut17packopener.R;
import com.prateek.fut17packopener.databinding.ActivityStoreBinding;

/**
 * Created by prateek on 25/8/17.
 */

public class StoreActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityStoreBinding binding;
    private long coinsToBeSubtracted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store);

        binding.totsPack.setOnClickListener(this);
        binding.megaPack.setOnClickListener(this);
        binding.totwPack.setOnClickListener(this);
        binding.primeGoldPack.setOnClickListener(this);
        binding.premiumGoldPack.setOnClickListener(this);
        binding.rareGoldPack.setOnClickListener(this);

        binding.totsPackTitle.setTypeface(AppController.getTypeface());
        binding.totsPackDescription.setTypeface(AppController.getTypeface());
        binding.totsPackPrice.setTypeface(AppController.getTypeface());
        binding.megaPackTitle.setTypeface(AppController.getTypeface());
        binding.megaPackDescription.setTypeface(AppController.getTypeface());
        binding.megaPackPrice.setTypeface(AppController.getTypeface());
        binding.totwPackTitle.setTypeface(AppController.getTypeface());
        binding.totwPackDescription.setTypeface(AppController.getTypeface());
        binding.totwPackPrice.setTypeface(AppController.getTypeface());
        binding.primeGoldPackTitle.setTypeface(AppController.getTypeface());
        binding.primeGoldPackDescription.setTypeface(AppController.getTypeface());
        binding.primeGoldPackPrice.setTypeface(AppController.getTypeface());
        binding.premiumGoldPackTitle.setTypeface(AppController.getTypeface());
        binding.premiumGoldPackDescription.setTypeface(AppController.getTypeface());
        binding.premiumGoldPackPrice.setTypeface(AppController.getTypeface());
        binding.rareGoldPackTitle.setTypeface(AppController.getTypeface());
        binding.rareGoldPackDescription.setTypeface(AppController.getTypeface());
        binding.rareGoldPackPrice.setTypeface(AppController.getTypeface());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppController.getGoogleApiHelper().getGoogleApiClient().isConnected()) {
            Games.setViewForPopups(AppController.getGoogleApiHelper().getGoogleApiClient(), getWindow().getDecorView());
        }
    }

    @Override
    public void onClick(View view) {
        PackType packType = null;
        switch (view.getId()) {
            case R.id.totsPack:
                packType = PackType.TOTS_PACK;
                coinsToBeSubtracted = 80000;
                break;
            case R.id.megaPack:
                packType = PackType.MEGA_PACK;
                coinsToBeSubtracted = 50000;
                break;
            case R.id.totwPack:
                packType = PackType.TOTW_PACK;
                coinsToBeSubtracted = 40000;
                break;
            case R.id.primeGoldPack:
                packType = PackType.PRIME_PLAYERS_PACK;
                coinsToBeSubtracted = 15000;
                break;
            case R.id.premiumGoldPack:
                packType = PackType.PREMIUM_PLAYERS_PACK;
                coinsToBeSubtracted = 10000;
                break;
            case R.id.rareGoldPack:
                packType = PackType.RARE_PLAYERS_PACK;
                coinsToBeSubtracted = 5000;
                break;
        }

        /*if (CustomPreference.getCoins() > coinsToBeSubtracted) {
            CustomPreference.updateCoins(CustomPreference.getCoins() - coinsToBeSubtracted);*/
            Intent intent = new Intent(StoreActivity.this, OpenPackActivity.class);
            intent.putExtra("PACK", packType);
            startActivity(intent);

        /*} else {
            Toast.makeText(this, "NOT ENOUGH COINS", Toast.LENGTH_LONG).show();
        }*/
    }

}
