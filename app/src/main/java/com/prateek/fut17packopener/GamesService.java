package com.prateek.fut17packopener;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.games.Game;
import com.google.android.gms.games.Games;


public class GamesService extends IntentService {

    public static final String ACTION = "ACTION";
    public static final String ACHIEVEMENT_SELL_PLAYER = "ACHIEVEMENT_SELL_PLAYER";
    public static final String ACHIEVEMENT_OPEN_PACK = "ACHIEVEMENT_OPEN_PLAYER";
    public static final String ACHIEVEMENT_TOTS_PACK = "ACHIEVEMENT_TOTS_PLAYER";
    public static final String ACHIEVEMENT_MEGA_PACK = "ACHIEVEMENT_MEGA_PLAYER";
    public static final String ACHIEVEMENT_TOTW_PACK = "ACHIEVEMENT_TOTW_PLAYER";
    public static final String ACHIEVEMENT_PRIME_PACK = "ACHIEVEMENT_PRIME_PLAYER";
    public static final String ACHIEVEMENT_PREMIUM_PACK = "ACHIEVEMENT_PREMIUM_PLAYER";
    public static final String ACHIEVEMENT_RARE_PACK = "ACHIEVEMENT_RARE_PLAYER";

    public GamesService() {
        super("GamesService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (AppController.getGoogleApiHelper().getGoogleApiClient().isConnected()) {
            if (intent != null) {
                String action = intent.getAction();
                if (action != null) {
                    switch (action) {
                        case ACHIEVEMENT_SELL_PLAYER:
                            Games.Achievements.unlock(AppController.getGoogleApiHelper().getGoogleApiClient(), getString(R.string.achievement_sell_player));
                            break;
                        case ACHIEVEMENT_OPEN_PACK:
                            Games.Achievements.unlock(AppController.getGoogleApiHelper().getGoogleApiClient(), getString(R.string.achievement_open_pack));
                            break;
                        case ACHIEVEMENT_TOTS_PACK:
                            Games.Achievements.unlock(AppController.getGoogleApiHelper().getGoogleApiClient(), getString(R.string.achievement_tots_pack));
                            break;
                        case ACHIEVEMENT_MEGA_PACK:
                            Games.Achievements.unlock(AppController.getGoogleApiHelper().getGoogleApiClient(), getString(R.string.achievement_mega_pack));
                            break;
                        case ACHIEVEMENT_TOTW_PACK:
                            Games.Achievements.unlock(AppController.getGoogleApiHelper().getGoogleApiClient(), getString(R.string.achievement_totw_pack));
                            break;
                        case ACHIEVEMENT_PRIME_PACK:
                            Games.Achievements.unlock(AppController.getGoogleApiHelper().getGoogleApiClient(), getString(R.string.achievement_prime_players_pack));
                            break;
                        case ACHIEVEMENT_PREMIUM_PACK:
                            Games.Achievements.unlock(AppController.getGoogleApiHelper().getGoogleApiClient(), getString(R.string.achievement_premium_players_pack));
                            break;
                        case ACHIEVEMENT_RARE_PACK:
                            Games.Achievements.unlock(AppController.getGoogleApiHelper().getGoogleApiClient(), getString(R.string.achievement_rare_players_pack));
                            break;
                    }
                }
            }
        }
    }
}
