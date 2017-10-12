package com.prateek.fut17packopener.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jinatonic.confetti.CommonConfetti;
import com.google.android.gms.games.Games;
import com.prateek.fut17packopener.AppController;
import com.prateek.fut17packopener.GamesService;
import com.prateek.fut17packopener.PackType;
import com.prateek.fut17packopener.PlayerCardView;
import com.prateek.fut17packopener.PlayerManager;
import com.prateek.fut17packopener.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import static com.prateek.fut17packopener.R.id.container;

public class OpenPackActivity extends AppCompatActivity {

    private PlayerCardView playerCardView;
    private EasyFlipView cardRotateContainer;
    private TextView tapToOpenCard;
    private RelativeLayout rlContainer;
    private static int CARD_OPEN_DURATION = 2000;
    private static int CARD_CLOSE_DURATION = 100;
    private int futties, onesToWatchName, goldMed, movember;
    private ImageView packCard;
    private Handler celebrationShowHandler = new Handler();
    private Intent selfIntent, gamesServiceIntent;
    private PackType packType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_pack);

        PlayerManager.getInstance().resetPackPlayers();

        rlContainer = (RelativeLayout) findViewById(container);
        futties = ContextCompat.getColor(this, R.color.futties);
        goldMed = ContextCompat.getColor(this, R.color.gold_med);
        onesToWatchName = ContextCompat.getColor(this, R.color.ones_to_watch_name);
        movember = getResources().getColor(R.color.movember);
        cardRotateContainer = (EasyFlipView) findViewById(R.id.rotateView);
        playerCardView = (PlayerCardView) findViewById(R.id.playerCard);
        packCard = (ImageView) findViewById(R.id.packCardImage);
        tapToOpenCard = (TextView) findViewById(R.id.tapForPackOpening);
        tapToOpenCard.setTypeface(AppController.getTypeface());
        cardRotateContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardRotateContainer.isFrontSide()) {
                    openPack();
                } else {
                    if (PlayerManager.getInstance().getPackPlayers() != null) {
                        Intent intent = new Intent(OpenPackActivity.this, PackDetailsActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        selfIntent = getIntent();
        gamesServiceIntent = new Intent(this, GamesService.class);
        if (selfIntent != null) {
            packType = (PackType) selfIntent.getSerializableExtra("PACK");
        }

    }

    private void openPack() {
        new LoadPackPlayersFromDBTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppController.getGoogleApiHelper().getGoogleApiClient().isConnected()) {
            Games.setViewForPopups(AppController.getGoogleApiHelper().getGoogleApiClient(), getWindow().getDecorView());
        }
        if (cardRotateContainer.isBackSide()) {
            cardRotateContainer.setFlipDuration(CARD_CLOSE_DURATION);
            cardRotateContainer.flipTheView();
        }

        switch (packType) {
            case DEFAULT:
                tapToOpenCard.setText(getString(R.string.tap_to_open, getString(R.string.default_pack)));
                packCard.setImageResource(R.drawable.gold_pack);
                break;
            case TOTS_PACK:
                tapToOpenCard.setText(getString(R.string.tap_to_open, getString(R.string.tots_pack_title)));
                packCard.setImageResource(R.drawable.tots_pack);
                gamesServiceIntent.setAction(GamesService.ACHIEVEMENT_TOTS_PACK);
                break;
            case MEGA_PACK:
                tapToOpenCard.setText(getString(R.string.tap_to_open, getString(R.string.mega_pack_title)));
                packCard.setImageResource(R.drawable.mega_pack);
                gamesServiceIntent.setAction(GamesService.ACHIEVEMENT_MEGA_PACK);
                break;
            case TOTW_PACK:
                tapToOpenCard.setText(getString(R.string.tap_to_open, getString(R.string.totw_pack_title)));
                packCard.setImageResource(R.drawable.totw_pack);
                gamesServiceIntent.setAction(GamesService.ACHIEVEMENT_TOTW_PACK);
                break;
            case PRIME_PLAYERS_PACK:
                tapToOpenCard.setText(getString(R.string.tap_to_open, getString(R.string.prime_players_pack_title)));
                packCard.setImageResource(R.drawable.gold_pack);
                gamesServiceIntent.setAction(GamesService.ACHIEVEMENT_PRIME_PACK);
                break;
            case PREMIUM_PLAYERS_PACK:
                tapToOpenCard.setText(getString(R.string.tap_to_open, getString(R.string.premium_players_pack_title)));
                packCard.setImageResource(R.drawable.gold_pack);
                gamesServiceIntent.setAction(GamesService.ACHIEVEMENT_PREMIUM_PACK);
                break;
            case RARE_PLAYERS_PACK:
                tapToOpenCard.setText(getString(R.string.tap_to_open, getString(R.string.rare_players_pack_title)));
                packCard.setImageResource(R.drawable.gold_pack);
                gamesServiceIntent.setAction(GamesService.ACHIEVEMENT_RARE_PACK);
                break;
        }

        startService(gamesServiceIntent);
    }

    class LoadPackPlayersFromDBTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            switch (packType) {
                case DEFAULT:
                    PlayerManager.getInstance().loadDefaultPack(OpenPackActivity.this);
                    break;
                case TOTS_PACK:
                    PlayerManager.getInstance().loadTotsPack(OpenPackActivity.this);
                    break;
                case MEGA_PACK:
                    PlayerManager.getInstance().loadMegaPlayersPack(OpenPackActivity.this);
                    break;
                case TOTW_PACK:
                    PlayerManager.getInstance().loadTOTWPlayersPack(OpenPackActivity.this);
                    break;
                case PRIME_PLAYERS_PACK:
                    PlayerManager.getInstance().loadPrimePlayersPack(OpenPackActivity.this);
                    break;
                case PREMIUM_PLAYERS_PACK:
                    PlayerManager.getInstance().loadPremiumPlayersPack(OpenPackActivity.this);
                    break;
                case RARE_PLAYERS_PACK:
                    PlayerManager.getInstance().loadRarePlayersPack(OpenPackActivity.this);
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            playerCardView.setValues(PlayerManager.getInstance().getPackPlayers().get(0));
            cardRotateContainer.setFlipDuration(CARD_OPEN_DURATION);
            cardRotateContainer.flipTheView(true);
            tapToOpenCard.setText(R.string.tap_for_pack_details);
            if (PlayerManager.getInstance().getPackPlayers().get(0).rating > 87) {
                celebrationShowHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CommonConfetti.rainingConfetti(rlContainer, new int[]{onesToWatchName, futties, movember, goldMed}).stream(4000).setEmissionDuration(2000).setVelocityY(600);
                    }
                }, 1000);
            }

            packType = PackType.DEFAULT;
        }
    }
}
