package com.prateek.fut17packopener.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.prateek.fut17packopener.models.Club;
import com.prateek.fut17packopener.models.League;
import com.prateek.fut17packopener.models.Nation;
import com.prateek.fut17packopener.models.PackOpenerPlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.prateek.fut17packopener.database.Queries.COLUMN_CLUB_ABBR_NAME;
import static com.prateek.fut17packopener.database.Queries.COLUMN_CLUB_ID;
import static com.prateek.fut17packopener.database.Queries.COLUMN_CLUB_ID_FK;
import static com.prateek.fut17packopener.database.Queries.COLUMN_CLUB_IMG_LARGE;
import static com.prateek.fut17packopener.database.Queries.COLUMN_CLUB_NAME;
import static com.prateek.fut17packopener.database.Queries.COLUMN_COLOR;
import static com.prateek.fut17packopener.database.Queries.COLUMN_DEFEND;
import static com.prateek.fut17packopener.database.Queries.COLUMN_DRIBBLE;
import static com.prateek.fut17packopener.database.Queries.COLUMN_ID;
import static com.prateek.fut17packopener.database.Queries.COLUMN_IMAGE;
import static com.prateek.fut17packopener.database.Queries.COLUMN_LEAGUE_ABBR_NAME;
import static com.prateek.fut17packopener.database.Queries.COLUMN_LEAGUE_ID;
import static com.prateek.fut17packopener.database.Queries.COLUMN_LEAGUE_ID_FK;
import static com.prateek.fut17packopener.database.Queries.COLUMN_LEAGUE_IMG_LARGE;
import static com.prateek.fut17packopener.database.Queries.COLUMN_LEAGUE_NAME;
import static com.prateek.fut17packopener.database.Queries.COLUMN_NAME;
import static com.prateek.fut17packopener.database.Queries.COLUMN_NATION_ABBR_NAME;
import static com.prateek.fut17packopener.database.Queries.COLUMN_NATION_ID;
import static com.prateek.fut17packopener.database.Queries.COLUMN_NATION_ID_FK;
import static com.prateek.fut17packopener.database.Queries.COLUMN_NATION_IMG_LARGE;
import static com.prateek.fut17packopener.database.Queries.COLUMN_NATION_NAME;
import static com.prateek.fut17packopener.database.Queries.COLUMN_PACE;
import static com.prateek.fut17packopener.database.Queries.COLUMN_PASS;
import static com.prateek.fut17packopener.database.Queries.COLUMN_PHYSICAL;
import static com.prateek.fut17packopener.database.Queries.COLUMN_POSITION;
import static com.prateek.fut17packopener.database.Queries.COLUMN_RATING;
import static com.prateek.fut17packopener.database.Queries.COLUMN_SHOT;
import static com.prateek.fut17packopener.database.Queries.CREATE_QUERY_PLAYER_TABLE;
import static com.prateek.fut17packopener.database.Queries.CREATE_QUERY_TABLE_CLUB;
import static com.prateek.fut17packopener.database.Queries.CREATE_QUERY_TABLE_LEAGUE;
import static com.prateek.fut17packopener.database.Queries.CREATE_QUERY_TABLE_NATION;
import static com.prateek.fut17packopener.database.Queries.CREATE_QUERY_USER_PLAYER_TABLE;
import static com.prateek.fut17packopener.database.Queries.FETCH_PLAYERS;
import static com.prateek.fut17packopener.database.Queries.FETCH_TOTAL_PLAYERS_COUNT;
import static com.prateek.fut17packopener.database.Queries.FETCH_USER_DUPLICATE_PLAYERS;
import static com.prateek.fut17packopener.database.Queries.FETCH_USER_DUPLICATE_PLAYERS_COUNT;
import static com.prateek.fut17packopener.database.Queries.FETCH_USER_UNIQUE_PLAYERS;
import static com.prateek.fut17packopener.database.Queries.FETCH_USER_UNIQUE_PLAYERS_COUNT;
import static com.prateek.fut17packopener.database.Queries.FETCH_USER_UNIQUE_PLAYERS_FILTER;
import static com.prateek.fut17packopener.database.Queries.TABLE_CLUB;
import static com.prateek.fut17packopener.database.Queries.TABLE_LEAGUE;
import static com.prateek.fut17packopener.database.Queries.TABLE_NATION;
import static com.prateek.fut17packopener.database.Queries.TABLE_PLAYER;
import static com.prateek.fut17packopener.database.Queries.USER_PLAYERS_TABLE;

/**
 * Created by Prateek on 7/19/17.
 */

public class FutDatabaseHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "FUT_17_PACK_OPENER";


    private SQLiteDatabase db;
    private final Context context;

    public FutDatabaseHelper(Context context) {
        this.context = context;
    }

    public void insertPlayer(PackOpenerPlayer player) {
        if (null != player) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_ID, player.getId());
            cv.put(COLUMN_NAME, player.getName());
            cv.put(COLUMN_IMAGE, player.getImage());
            cv.put(COLUMN_LEAGUE_ID_FK, player.getLeagueId());
            cv.put(COLUMN_CLUB_ID_FK, player.getClubId());
            cv.put(COLUMN_NATION_ID_FK, player.getNationId());
            cv.put(COLUMN_POSITION, player.getPosition());
            cv.put(COLUMN_RATING, player.getRating());
            cv.put(COLUMN_PACE, player.getPace());
            cv.put(COLUMN_SHOT, player.getShot());
            cv.put(COLUMN_PASS, player.getPass());
            cv.put(COLUMN_DRIBBLE, player.getDribble());
            cv.put(COLUMN_DEFEND, player.getDefend());
            cv.put(COLUMN_PHYSICAL, player.getPhysical());
            cv.put(COLUMN_COLOR, player.getColor());
            long rowId = db.insert(TABLE_PLAYER, null, cv);
            Log.d("ROWID", String.valueOf(rowId));
        }
    }

    public ArrayList<PackOpenerPlayer> fetchPlayers() {
        ArrayList<PackOpenerPlayer>  players = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_PLAYER, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                PackOpenerPlayer player = parsePackOpenerPlayer(cursor);
                players.add(player);
                //Log.d("DB PLAYER", String.valueOf(players.size()));
            } while (cursor.moveToNext());
        }
        return players;
    }

    public int fetchTotalPlayersCount() {
        Cursor cursor = db.rawQuery(FETCH_TOTAL_PLAYERS_COUNT, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            return cursor.getInt(0);
        }
        return -1;
    }

    public int fetchDuplicatePlayersCount() {
        Cursor cursor = db.rawQuery(FETCH_USER_DUPLICATE_PLAYERS_COUNT, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getCount();
        }
        return -1;
    }

    public int fetchUserUniquePlayersCount() {
        Cursor cursor = db.rawQuery(FETCH_USER_UNIQUE_PLAYERS_COUNT, null, null);
        int count = 0;
        if (cursor != null) {
            count = cursor.getCount();
        }
        return count;
    }

    public void insertUserPlayers(ArrayList<PackOpenerPlayer> players) {
        if (null != players && players.size() > 0) {
            for (PackOpenerPlayer player : players) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_ID, player.getId());
                db.insert(USER_PLAYERS_TABLE, null, cv);
            }
        }
    }

    public void insertUserPlayer(PackOpenerPlayer player) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, player.getId());
        db.insert(USER_PLAYERS_TABLE, null, cv);
    }

    public ArrayList<PackOpenerPlayer> fetchUserUniquePlayers() {
        ArrayList<PackOpenerPlayer> players = new ArrayList<>();
        Cursor cursor = db.rawQuery(FETCH_USER_UNIQUE_PLAYERS, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            do {
                PackOpenerPlayer player = parsePackOpenerPlayer(cursor);
                players.add(player);
                Log.d("USER PLAYERS", String.valueOf(players.size()));
            } while (cursor.moveToNext());
        }
        return players;
    }

    public ArrayList<PackOpenerPlayer> fetchUserUniquePlayersFilters(List<Object> nations, List<Object> leagues) {
        ArrayList<PackOpenerPlayer> players = new ArrayList<>();
        String nationIdString = "";
        String leagueIdString = "";
        if (nations.size() > 0) {
            StringBuilder nationIdStringBuilder = new StringBuilder();
            for (Object nation : nations) {
                nationIdStringBuilder.append(((Nation) nation).id).append(",");
            }
            nationIdString = nationIdStringBuilder.substring(0, nationIdStringBuilder.lastIndexOf(",")).toString();
        }

        if (leagues.size() > 0) {
            StringBuilder leagueIdStringBuilder = new StringBuilder();
            for (Object league : leagues) {
                leagueIdStringBuilder.append(((League) league).id).append(",");
            }
            leagueIdString = leagueIdStringBuilder.substring(0, leagueIdStringBuilder.lastIndexOf(",")).toString();
        }

        String query = FETCH_USER_UNIQUE_PLAYERS_FILTER + " WHERE P." + COLUMN_NATION_ID_FK + " IN (" + nationIdString + ") AND P." + COLUMN_LEAGUE_ID_FK + " IN (" + leagueIdString+ ") GROUP BY P." + COLUMN_ID;;
        Cursor cursor = db.rawQuery(query, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            do {
                PackOpenerPlayer player = parsePackOpenerPlayer(cursor);
                players.add(player);
                Log.d("USER PLAYERS", String.valueOf(players.size()));
            } while (cursor.moveToNext());
        }
        return players;
    }

    @NonNull
    private PackOpenerPlayer parsePackOpenerPlayer(Cursor cursor) {
        PackOpenerPlayer player = new PackOpenerPlayer();
        player.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
        player.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        player.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
        player.setLeagueId(cursor.getInt(cursor.getColumnIndex(COLUMN_LEAGUE_ID_FK)));
        player.setClubId(cursor.getInt(cursor.getColumnIndex(COLUMN_CLUB_ID_FK)));
        player.setNationId(cursor.getInt(cursor.getColumnIndex(COLUMN_NATION_ID_FK)));
        player.setPosition(cursor.getString(cursor.getColumnIndex(COLUMN_POSITION)));
        player.setRating(cursor.getInt(cursor.getColumnIndex(COLUMN_RATING)));
        player.setPace(cursor.getInt(cursor.getColumnIndex(COLUMN_PACE)));
        player.setShot(cursor.getInt(cursor.getColumnIndex(COLUMN_SHOT)));
        player.setPass(cursor.getInt(cursor.getColumnIndex(COLUMN_PASS)));
        player.setDribble(cursor.getInt(cursor.getColumnIndex(COLUMN_DRIBBLE)));
        player.setDefend(cursor.getInt(cursor.getColumnIndex(COLUMN_DEFEND)));
        player.setPhysical(cursor.getInt(cursor.getColumnIndex(COLUMN_PHYSICAL)));
        player.setColor(cursor.getString(cursor.getColumnIndex(COLUMN_COLOR)));
        Nation nation = new Nation();
        nation.id = cursor.getInt(cursor.getColumnIndex(COLUMN_NATION_ID));
        nation.name = cursor.getString(cursor.getColumnIndex(COLUMN_NATION_NAME));
        nation.abbrName = cursor.getString(cursor.getColumnIndex(COLUMN_NATION_ABBR_NAME));
        nation.image = cursor.getString(cursor.getColumnIndex(COLUMN_NATION_IMG_LARGE));
        player.setNation(nation);
        League league = new League();
        league.id = cursor.getInt(cursor.getColumnIndex(COLUMN_LEAGUE_ID));
        league.name = cursor.getString(cursor.getColumnIndex(COLUMN_LEAGUE_NAME));
        league.abbrName = cursor.getString(cursor.getColumnIndex(COLUMN_LEAGUE_ABBR_NAME));
        league.image = cursor.getString(cursor.getColumnIndex(COLUMN_LEAGUE_IMG_LARGE));
        player.setLeague(league);
        Club club = new Club();
        club.id = cursor.getInt(cursor.getColumnIndex(COLUMN_CLUB_ID));
        club.name = cursor.getString(cursor.getColumnIndex(COLUMN_CLUB_NAME));
        club.abbrName = cursor.getString(cursor.getColumnIndex(COLUMN_CLUB_ABBR_NAME));
        club.image = cursor.getString(cursor.getColumnIndex(COLUMN_CLUB_IMG_LARGE));
        club.leagueId = cursor.getInt(cursor.getColumnIndex(COLUMN_LEAGUE_ID_FK));
        player.setClub(club);
        return player;
    }

    public Set<PackOpenerPlayer> fetchDuplicatePlayers() {
        Set<PackOpenerPlayer> players = new HashSet<>();
        Cursor cursor = db.rawQuery(FETCH_USER_DUPLICATE_PLAYERS, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            do {
                PackOpenerPlayer player = parsePackOpenerPlayer(cursor);
                players.add(player);
                Log.d("USER PLAYERS", String.valueOf(players.size()));
            } while (cursor.moveToNext());
        }
        return players;
    }

    public void deleteAllDuplicatePlayers(ArrayList<PackOpenerPlayer> players) {
        for (PackOpenerPlayer player : players) {
            int delete = db.delete(USER_PLAYERS_TABLE, COLUMN_ID + " = '" + player.getId() + "'", null);
        }

        insertUserPlayers(players);
    }

    public ArrayList<PackOpenerPlayer> fetchPlayers(int start, int end) {
        ArrayList<PackOpenerPlayer>  players = new ArrayList<>();
        String query = FETCH_PLAYERS +" WHERE " + COLUMN_RATING + ">=" + start +" AND " + COLUMN_RATING +"<=" + end;
        Cursor cursor = db.rawQuery(query, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                PackOpenerPlayer player = parsePackOpenerPlayer(cursor);
                players.add(player);
                //Log.d("DB PLAYER", String.valueOf(players.size()));
            } while (cursor.moveToNext());
        }
        return players;
    }

    public ArrayList<PackOpenerPlayer> fetchRarePlayers() {
        ArrayList<PackOpenerPlayer>  players = new ArrayList<>();
        String query = FETCH_PLAYERS + " WHERE " + COLUMN_COLOR + " != 'gold'";
        Cursor cursor = db.rawQuery(query, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                PackOpenerPlayer player = parsePackOpenerPlayer(cursor);
                players.add(player);
                //Log.d("DB PLAYER", String.valueOf(players.size()));
            } while (cursor.moveToNext());
        }
        return players;
    }

    public ArrayList<PackOpenerPlayer> fetchTOTSPlayers() {
        ArrayList<PackOpenerPlayer>  players = new ArrayList<>();
        String query = FETCH_PLAYERS + " WHERE " + COLUMN_COLOR + " = 'tots_gold'";
        Cursor cursor = db.rawQuery(query, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                PackOpenerPlayer player = parsePackOpenerPlayer(cursor);
                players.add(player);
            } while (cursor.moveToNext());
        }
        return players;
    }

    public ArrayList<PackOpenerPlayer> fetchNonTOTSPlayers() {
        ArrayList<PackOpenerPlayer>  players = new ArrayList<>();
        String query = FETCH_PLAYERS + " WHERE " + COLUMN_COLOR + " != 'tots_gold'";
        Cursor cursor = db.rawQuery(query, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                PackOpenerPlayer player = parsePackOpenerPlayer(cursor);
                players.add(player);
            } while (cursor.moveToNext());
        }
        return players;
    }

    public ArrayList<PackOpenerPlayer> fetchTOTWPlayers() {
        ArrayList<PackOpenerPlayer>  players = new ArrayList<>();
        String query = FETCH_PLAYERS + " WHERE " + COLUMN_COLOR + " = 'totw_gold'";
        Cursor cursor = db.rawQuery(query, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                PackOpenerPlayer player = parsePackOpenerPlayer(cursor);
                players.add(player);
            } while (cursor.moveToNext());
        }
        return players;
    }

    public ArrayList<PackOpenerPlayer> fetchNonTOTWPlayers() {
        ArrayList<PackOpenerPlayer>  players = new ArrayList<>();
        String query = FETCH_PLAYERS + " WHERE " + COLUMN_COLOR + " != 'totw_gold'";
        Cursor cursor = db.rawQuery(query, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                PackOpenerPlayer player = parsePackOpenerPlayer(cursor);
                players.add(player);
            } while (cursor.moveToNext());
        }
        return players;
    }

    public ArrayList<PackOpenerPlayer> fetch82PlusRatedPlayers() {
        ArrayList<PackOpenerPlayer>  players = new ArrayList<>();
        String query = FETCH_PLAYERS + " WHERE " + COLUMN_RATING + " >= 82";
        Cursor cursor = db.rawQuery(query, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                PackOpenerPlayer player = parsePackOpenerPlayer(cursor);
                players.add(player);
            } while (cursor.moveToNext());
        }
        return players;
    }

    public ArrayList<PackOpenerPlayer> fetch81PlusRatedPlayers() {
        ArrayList<PackOpenerPlayer>  players = new ArrayList<>();
        String query = FETCH_PLAYERS + " WHERE " + COLUMN_RATING + " >= 81";
        Cursor cursor = db.rawQuery(query, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                PackOpenerPlayer player = parsePackOpenerPlayer(cursor);
                players.add(player);
            } while (cursor.moveToNext());
        }
        return players;
    }

    public void deleteDuplicatePlayer(PackOpenerPlayer player) {
        int delete = db.delete(USER_PLAYERS_TABLE, COLUMN_ID + " = '" + player.getId() + "'", null);
        for (int i = 0;i < delete-1;i++) {
            insertUserPlayer(player);
        }
    }

    public boolean isPlayerDuplicate(PackOpenerPlayer player) {
        boolean result = false;
        Cursor cursor = db.rawQuery("SELECT * FROM "+USER_PLAYERS_TABLE +" WHERE " + COLUMN_ID + " = '"+player.getId()+"'", null, null);
        if (cursor != null) {
            result = (cursor.getCount() > 1);
        }
        return result;
    }

    public void insertNation(Nation nation) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NATION_ID, nation.id);
        cv.put(COLUMN_NATION_NAME, nation.name);
        cv.put(COLUMN_NATION_ABBR_NAME, nation.abbrName);
        cv.put(COLUMN_NATION_IMG_LARGE, nation.image);
        long rowId = db.insert(TABLE_NATION, null, cv);
    }

    public ArrayList<Nation> getNations() {
        ArrayList<Nation> nations = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NATION + " ORDER BY " + COLUMN_NATION_NAME, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Nation nation = new Nation();
                nation.id = cursor.getInt(cursor.getColumnIndex(COLUMN_NATION_ID));
                nation.name = cursor.getString(cursor.getColumnIndex(COLUMN_NATION_NAME));
                nation.abbrName = cursor.getString(cursor.getColumnIndex(COLUMN_NATION_ABBR_NAME));
                nation.image = cursor.getString(cursor.getColumnIndex(COLUMN_NATION_IMG_LARGE));
                nations.add(nation);
            } while (cursor.moveToNext());
        }
        return nations;
    }

    public void insertLeague(League league) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LEAGUE_ID, league.id);
        cv.put(COLUMN_LEAGUE_NAME, league.name);
        cv.put(COLUMN_LEAGUE_ABBR_NAME, league.abbrName);
        cv.put(COLUMN_LEAGUE_IMG_LARGE, league.image);
        long rowId = db.insert(TABLE_LEAGUE, null, cv);
    }

    public void insertClub(Club club) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CLUB_ID, club.id);
        cv.put(COLUMN_CLUB_NAME, club.name);
        cv.put(COLUMN_CLUB_ABBR_NAME, club.abbrName);
        cv.put(COLUMN_CLUB_IMG_LARGE, club.image);
        cv.put(COLUMN_LEAGUE_ID_FK, club.leagueId);
        long rowId = db.insert(TABLE_CLUB, null, cv);
    }

    public void clearPlayerData() {
        db.delete(TABLE_PLAYER, null, null);
        db.delete(TABLE_NATION, null, null);
        db.delete(TABLE_LEAGUE, null, null);
        db.delete(TABLE_CLUB, null, null);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NATION);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CLUB);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_LEAGUE);
        db.execSQL(CREATE_QUERY_PLAYER_TABLE);
        db.execSQL(CREATE_QUERY_TABLE_NATION);
        db.execSQL(CREATE_QUERY_TABLE_LEAGUE);
        db.execSQL(CREATE_QUERY_TABLE_CLUB);
    }

    public ArrayList<League> getLeagues() {
        ArrayList<League> leagues = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_LEAGUE + " ORDER BY " + COLUMN_LEAGUE_NAME, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                League league = new League();
                league.id = cursor.getInt(cursor.getColumnIndex(COLUMN_LEAGUE_ID));
                league.name = cursor.getString(cursor.getColumnIndex(COLUMN_LEAGUE_NAME));
                league.abbrName = cursor.getString(cursor.getColumnIndex(COLUMN_LEAGUE_ABBR_NAME));
                league.image = cursor.getString(cursor.getColumnIndex(COLUMN_LEAGUE_IMG_LARGE));
                leagues.add(league);
            } while (cursor.moveToNext());
        }
        return leagues;
    }

    public static class FUTDatabase extends SQLiteOpenHelper {

        /**
         * Constructor
         *
         * @param context application context
         */
        public FUTDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_QUERY_PLAYER_TABLE);
            db.execSQL(CREATE_QUERY_TABLE_NATION);
            db.execSQL(CREATE_QUERY_TABLE_LEAGUE);
            db.execSQL(CREATE_QUERY_TABLE_CLUB);
            db.execSQL(CREATE_QUERY_USER_PLAYER_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NATION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEAGUE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLUB);
            onCreate(db);
        }

    }

    public void open() {
        FUTDatabase database = new FUTDatabase(context);
        db = database.getWritableDatabase();
    }

    public void close() {
        if (null != db) {
            db.close();
        }
    }

    public String getPath() {
        return db.getPath();
    }
}
