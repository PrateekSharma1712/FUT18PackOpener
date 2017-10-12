package com.prateek.fut17packopener.database;

import android.content.Context;

import com.prateek.fut17packopener.models.Club;
import com.prateek.fut17packopener.models.League;
import com.prateek.fut17packopener.models.Nation;
import com.prateek.fut17packopener.models.PackOpenerPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayerDAO {

    private final FutDatabaseHelper futDatabaseHelper;

    public PlayerDAO(Context context) {
        futDatabaseHelper = new FutDatabaseHelper(context);
    }

    public void savePlayer(PackOpenerPlayer player) {
        futDatabaseHelper.open();
        futDatabaseHelper.insertPlayer(player);
        futDatabaseHelper.close();
    }

    public ArrayList<PackOpenerPlayer> getPlayers() {
        ArrayList<PackOpenerPlayer> players = null;
        futDatabaseHelper.open();
        players = futDatabaseHelper.fetchPlayers();
        futDatabaseHelper.close();
        return players;
    }

    public int getTotalPlayersCount() {
        int playerCount = 0;
        futDatabaseHelper.open();
        playerCount = futDatabaseHelper.fetchTotalPlayersCount();
        futDatabaseHelper.close();
        return playerCount;
    }

    public int fetchDuplicatePlayersCount() {
        int playerCount = 0;
        futDatabaseHelper.open();
        playerCount = futDatabaseHelper.fetchDuplicatePlayersCount();
        futDatabaseHelper.close();
        return playerCount;
    }

    public int fetchUserUniquePlayersCount() {
        int playerCount = 0;
        futDatabaseHelper.open();
        playerCount = futDatabaseHelper.fetchUserUniquePlayersCount();
        futDatabaseHelper.close();
        return playerCount;
    }

    public void saveUserPlayer(ArrayList<PackOpenerPlayer> players) {
        futDatabaseHelper.open();
        futDatabaseHelper.insertUserPlayers(players);
        futDatabaseHelper.close();
    }

    public ArrayList<PackOpenerPlayer> fetchPackPlayers() {
        return getPlayers();
    }

    public ArrayList<PackOpenerPlayer> fetchUserUniquePlayers() {
        ArrayList<PackOpenerPlayer> players = null;
        futDatabaseHelper.open();
        players = futDatabaseHelper.fetchUserUniquePlayers();
        futDatabaseHelper.close();
        return players;
    }

    public Set<PackOpenerPlayer> fetchDuplicatePlayers() {
        Set<PackOpenerPlayer> players = null;
        futDatabaseHelper.open();
        players = futDatabaseHelper.fetchDuplicatePlayers();
        futDatabaseHelper.close();
        return players;
    }

    public void deleteAllDuplicatePlayers(ArrayList<PackOpenerPlayer> players) {
        futDatabaseHelper.open();
        futDatabaseHelper.deleteAllDuplicatePlayers(players);
        futDatabaseHelper.close();
    }

    public List<PackOpenerPlayer> fetchPackPlayers(int start, int end) {
        ArrayList<PackOpenerPlayer> players = null;
        futDatabaseHelper.open();
        players = futDatabaseHelper.fetchPlayers(start, end);
        futDatabaseHelper.close();
        return players;
    }

    public ArrayList<PackOpenerPlayer> getRarePlayers() {
        ArrayList<PackOpenerPlayer> players = null;
        futDatabaseHelper.open();
        players = futDatabaseHelper.fetchRarePlayers();
        futDatabaseHelper.close();
        return players;
    }

    public ArrayList<PackOpenerPlayer> getTOTSPlayers() {
        ArrayList<PackOpenerPlayer> players = null;
        futDatabaseHelper.open();
        players = futDatabaseHelper.fetchTOTSPlayers();
        futDatabaseHelper.close();
        return players;
    }

    public ArrayList<PackOpenerPlayer> getNonTOTSPlayers() {
        ArrayList<PackOpenerPlayer> players = null;
        futDatabaseHelper.open();
        players = futDatabaseHelper.fetchNonTOTSPlayers();
        futDatabaseHelper.close();
        return players;
    }

    public ArrayList<PackOpenerPlayer> getNonTOTWPlayers() {
        ArrayList<PackOpenerPlayer> players = null;
        futDatabaseHelper.open();
        players = futDatabaseHelper.fetchNonTOTWPlayers();
        futDatabaseHelper.close();
        return players;
    }

    public ArrayList<PackOpenerPlayer> getTOTWPlayers() {
        ArrayList<PackOpenerPlayer> players = null;
        futDatabaseHelper.open();
        players = futDatabaseHelper.fetchTOTWPlayers();
        futDatabaseHelper.close();
        return players;
    }

    public ArrayList<PackOpenerPlayer> get82PlusRatedPlayers() {
        ArrayList<PackOpenerPlayer> players = null;
        futDatabaseHelper.open();
        players = futDatabaseHelper.fetch82PlusRatedPlayers();
        futDatabaseHelper.close();
        return players;
    }

    public ArrayList<PackOpenerPlayer> get81PlusRatedPlayers() {
        ArrayList<PackOpenerPlayer> players = null;
        futDatabaseHelper.open();
        players = futDatabaseHelper.fetch81PlusRatedPlayers();
        futDatabaseHelper.close();
        return players;
    }

    public void deleteDuplicateCard(PackOpenerPlayer player) {
        futDatabaseHelper.open();
        futDatabaseHelper.deleteDuplicatePlayer(player);
        futDatabaseHelper.close();
    }

    public boolean isPlayerDuplicate(PackOpenerPlayer player) {
        boolean result = false;
        futDatabaseHelper.open();
        result = futDatabaseHelper.isPlayerDuplicate(player);
        futDatabaseHelper.close();
        return result;
    }

    public void clearPlayerData() {
        futDatabaseHelper.open();
        futDatabaseHelper.clearPlayerData();
        futDatabaseHelper.close();
    }

    public void insertNation(Nation nation) {
        futDatabaseHelper.open();
        futDatabaseHelper.insertNation(nation);
        futDatabaseHelper.close();
    }

    public ArrayList<Nation> getNations() {
        ArrayList<Nation> nations = null;
        futDatabaseHelper.open();
        nations = futDatabaseHelper.getNations();
        futDatabaseHelper.close();
        return nations;
    }

    public void insertLeague(League league) {
        futDatabaseHelper.open();
        futDatabaseHelper.insertLeague(league);
        futDatabaseHelper.close();
    }

    public void insertClub(Club club) {
        futDatabaseHelper.open();
        futDatabaseHelper.insertClub(club);
        futDatabaseHelper.close();
    }

    public String getPath() {
        String path = null;
        futDatabaseHelper.open();
        path = futDatabaseHelper.getPath();
        futDatabaseHelper.close();
        return path;
    }

    public ArrayList<League> getLeagues() {
        ArrayList<League> leagues = null;
        futDatabaseHelper.open();
        leagues = futDatabaseHelper.getLeagues();
        futDatabaseHelper.close();
        return leagues;
    }

    public ArrayList<PackOpenerPlayer> fetchUserUniquePlayersFilters(List<Object> nations, List<Object> leagues) {
        ArrayList<PackOpenerPlayer> players = null;
        futDatabaseHelper.open();
        players = futDatabaseHelper.fetchUserUniquePlayersFilters(nations, leagues);
        futDatabaseHelper.close();
        return players;
    }
}
