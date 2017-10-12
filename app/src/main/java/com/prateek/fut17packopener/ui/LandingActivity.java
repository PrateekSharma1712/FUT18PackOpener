package com.prateek.fut17packopener.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.games.Games;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prateek.fut17packopener.AppController;
import com.prateek.fut17packopener.CustomPreference;
import com.prateek.fut17packopener.PackType;
import com.prateek.fut17packopener.PlayerManager;
import com.prateek.fut17packopener.R;
import com.prateek.fut17packopener.databinding.ActivityLandingBinding;
import com.prateek.fut17packopener.databinding.ActivityLandingNewBinding;

import java.util.Calendar;
import java.util.Date;

public class LandingActivity extends BaseLoginActivity implements PlayerManager.DataListener {

    private static int REQUEST_ACHIEVEMENTS = 1;
    private static int REQUEST_LEADERBOARDS = 2;

    private int myCardsCount;
    private int myDuplicateCardsCount;
    private int totalPlayersCount;
    private LoadPlayersFromDBTask loadPlayersFromDBTask;
    private ActivityLandingNewBinding binding;

    private boolean achievementsClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_landing_new);

        //load latest timestamp
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mReference = mDatabase.getReference();
        Log.d("TIME IN", String.valueOf(Calendar.getInstance().getTime()));
        Query query = mReference.orderByChild("acceleration").equalTo(80).limitToFirst(10);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("TIME OUT", String.valueOf(Calendar.getInstance().getTime()));
                Log.d("TAG", "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //PlayerManager.getInstance().loadTimeStamp(PlayerManager.GET_TIME, LandingActivity.this);

        /*DATA EXPORT POC START*/
        /*PlayerDAO playerDAO = new PlayerDAO(this);
        String path = playerDAO.getPath();
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/user/0/com.prateek.fut17packopener/databases/FUT_17_PACK_OPENER";
        String backupDBPath = "PACK_OPENER_DB";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.d("EXCEPTION", e.toString());
        }*/
                /*DATA EXPORT POC END*/
        
        binding.tvOpenPack.setTypeface(AppController.getTypeface());
        binding.tvMyCards.setTypeface(AppController.getTypeface());
        binding.tvMyDuplicateCards.setTypeface(AppController.getTypeface());
        binding.tvStore.setTypeface(AppController.getTypeface());
        binding.tvMyCardPlayers.setTypeface(AppController.getTypeface());
        binding.tvMyDuplicateCardPlayers.setTypeface(AppController.getTypeface());
        binding.tvAllPlayers.setTypeface(AppController.getTypeface());
        binding.coins.setTypeface(AppController.getTypeface());
        binding.tvSquadBuilder.setTypeface(AppController.getTypeface());
        binding.tvAchievements.setTypeface(AppController.getTypeface());
        binding.tvLeaderBoard.setTypeface(AppController.getTypeface());

        binding.openPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, OpenPackActivity.class);
                intent.putExtra("PACK", PackType.DEFAULT);
                startActivity(intent);
            }
        });

        binding.myCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myCardsCount > 0) {
                    Intent intent = new Intent(LandingActivity.this, MyCardsActivity.class);
                    startActivity(intent);
                }
            }
        });

        binding.myDuplicateCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myDuplicateCardsCount > 0) {
                    Intent intent = new Intent(LandingActivity.this, MyDuplicateCardsActivity.class);
                    startActivity(intent);
                }
            }
        });

        binding.store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingActivity.this, StoreActivity.class));
            }
        });

        binding.squadBuilder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingActivity.this, SquadBuilderActivity.class));
            }
        });

        binding.leaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLeaderBoards();
            }
        });

        binding.achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAchievements();
            }
        });
    }

    private void openLeaderBoards() {
        if (AppController.getGoogleApiHelper().getGoogleApiClient().isConnected()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(AppController.getGoogleApiHelper().getGoogleApiClient()), REQUEST_ACHIEVEMENTS);
        } else {
            achievementsClicked = false;
            login();
        }
    }

    private void openAchievements() {
        if (AppController.getGoogleApiHelper().getGoogleApiClient().isConnected()) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(AppController.getGoogleApiHelper().getGoogleApiClient()), REQUEST_LEADERBOARDS);
        } else {
            achievementsClicked = true;
            login();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppController.getGoogleApiHelper().getGoogleApiClient().isConnected()) {
            Games.setViewForPopups(AppController.getGoogleApiHelper().getGoogleApiClient(), getWindow().getDecorView());
        }
        binding.coins.setText(String.valueOf(CustomPreference.getCoins()));
        loadPlayersFromDBTask = new LoadPlayersFromDBTask();
        loadPlayersFromDBTask.execute();
    }

    class LoadPlayersFromDBTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.myCardsLoading.setVisibility(View.VISIBLE);
            binding.myDuplicateCardsLoading.setVisibility(View.VISIBLE);
            binding.totalPlayersLoading.setVisibility(View.VISIBLE);
            binding.tvMyCardPlayers.setVisibility(View.GONE);
            binding.tvMyDuplicateCardPlayers.setVisibility(View.GONE);
            binding.tvAllPlayers.setVisibility(View.GONE);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            myCardsCount = PlayerManager.getInstance().getUserUniquePlayersCount(LandingActivity.this);
            myDuplicateCardsCount = PlayerManager.getInstance().getDuplicateCardsCount(LandingActivity.this);
            totalPlayersCount = PlayerManager.getInstance().getTotalPlayersCount(LandingActivity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            binding.myCardsLoading.setVisibility(View.GONE);
            binding.myDuplicateCardsLoading.setVisibility(View.GONE);
            binding.totalPlayersLoading.setVisibility(View.GONE);
            binding.tvMyCardPlayers.setVisibility(View.VISIBLE);
            binding.tvMyDuplicateCardPlayers.setVisibility(View.VISIBLE);
            binding.tvAllPlayers.setVisibility(View.VISIBLE);
            binding.tvMyCardPlayers.setText("");
            binding.tvMyDuplicateCardPlayers.setText("");
            binding.tvAllPlayers.setText("");
            binding.tvMyCardPlayers.setText(myCardsCount + " " + getString(R.string.players));
            binding.tvMyDuplicateCardPlayers.setText(myDuplicateCardsCount + " " + getString(R.string.players));
            binding.tvAllPlayers.setText(totalPlayersCount + " " + getString(R.string.total_players));
            /*PlayerDAO playerDAO = new PlayerDAO(LandingActivity.this);
            ArrayList<PackOpenerPlayer> players = playerDAO.getPlayers();*/
            loadPlayersFromDBTask.cancel(true);
        }
    }

    @Override
    public void onDataLoaded(String tag, String data) {
        Log.d("LandingActivity"+ tag, data);
        //response for timestamp
        if (tag.equals(PlayerManager.GET_TIME)) {
            long savedTimeStamp = CustomPreference.getTime();
            if (savedTimeStamp > 0 && savedTimeStamp < Long.valueOf(data)) {
                //need to update data
                binding.playerDownloading.setVisibility(View.VISIBLE);
                binding.tvAllPlayers.setVisibility(View.GONE);
                binding.tvOpenPack.setVisibility(View.GONE);
                binding.myCards.setEnabled(false);
                binding.myDuplicateCards.setEnabled(false);
                binding.store.setEnabled(false);
                PlayerManager.getInstance().loadAll(PlayerManager.GET_PLAYERS, this);
                CustomPreference.updateTime(Long.valueOf(data));
            } else if (savedTimeStamp < Long.valueOf(data)) {
                // need to update data
                binding.playerDownloading.setVisibility(View.VISIBLE);
                binding.tvAllPlayers.setVisibility(View.GONE);
                binding.tvOpenPack.setVisibility(View.GONE);
                binding.myCards.setEnabled(false);
                binding.myDuplicateCards.setEnabled(false);
                binding.store.setEnabled(false);
                PlayerManager.getInstance().loadAll(PlayerManager.GET_PLAYERS, this);
                CustomPreference.updateTime(Long.valueOf(data));
            } else{
                // no need to update data
            }
        } else {
            binding.playerDownloading.setVisibility(View.GONE);
            binding.tvOpenPack.setVisibility(View.VISIBLE);
            binding.tvAllPlayers.setVisibility(View.VISIBLE);
            binding.myCards.setEnabled(true);
            binding.myDuplicateCards.setEnabled(true);
            binding.store.setEnabled(true);

            totalPlayersCount = PlayerManager.getInstance().getTotalPlayersCount(LandingActivity.this);
            binding.tvAllPlayers.setText("");
            binding.tvAllPlayers.setText(totalPlayersCount + " " + getString(R.string.total_players));
            Log.d("TAG", String.valueOf(totalPlayersCount));
            Log.d("TIME", new Date().toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ACHIEVEMENTS || requestCode == REQUEST_LEADERBOARDS) {
            if(resultCode == 10001) {
                AppController.getGoogleApiHelper().disconnect();
            }
        }
    }

    @Override
    protected void onConnected() {
        if (achievementsClicked) {
            openAchievements();
        } else {
            openLeaderBoards();
        }
    }
}
