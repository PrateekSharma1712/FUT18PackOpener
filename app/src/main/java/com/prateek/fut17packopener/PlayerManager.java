package com.prateek.fut17packopener;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.prateek.fut17packopener.database.PlayerDAO;
import com.prateek.fut17packopener.models.Bounds;
import com.prateek.fut17packopener.models.ClubList;
import com.prateek.fut17packopener.models.League;
import com.prateek.fut17packopener.models.LeagueList;
import com.prateek.fut17packopener.models.Nation;
import com.prateek.fut17packopener.models.NationList;
import com.prateek.fut17packopener.models.PackOpenerPlayer;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by prateek on 10/8/17.
 */

public class PlayerManager {

    public static final String GET_TIME = "GET_TIME";
    public static final String GET_PLAYERS = "GET_PLAYERS";

    private FirebaseDatabase mDatabase;
    private DatabaseReference mPlayerReference;
    private DatabaseReference mTimeReference;
    private HashMap<Integer, Bounds> boundsMap;
    private ArrayList<Bounds> randomBounds;
    private ArrayList<Bounds> higherBounds;
    private static PlayerDAO playerDAO;
    private int queryCounter = 0;
    private ArrayList<PackOpenerPlayer> packPlayers;


    private static PlayerManager mInstance;

    public ArrayList<PackOpenerPlayer> getPackPlayers() {
        return packPlayers;
    }

    public void loadDefaultPack(Context context) {

        HashSet<PackOpenerPlayer> playersSet = new HashSet<>();
        boundsMap =  new HashMap<Integer, Bounds>();
        boundsMap.put(1, new Bounds(75,77));
        boundsMap.put(2, new Bounds(78,80));
        boundsMap.put(3, new Bounds(81,83));
        boundsMap.put(4, new Bounds(84,86));
        boundsMap.put(5, new Bounds(87,89));
        boundsMap.put(6, new Bounds(90,92));
        boundsMap.put(7, new Bounds(93,95));
        boundsMap.put(8, new Bounds(96,99));

        higherBounds = new ArrayList<>();
        higherBounds.add(boundsMap.get(3));
        higherBounds.add(boundsMap.get(4));
        higherBounds.add(boundsMap.get(5));
        higherBounds.add(boundsMap.get(6));
        higherBounds.add(boundsMap.get(7));
        higherBounds.add(boundsMap.get(8));

        fillRandomBounds();

        for (Bounds bound : randomBounds) {
            playersSet.addAll(playerDAO.fetchPackPlayers(bound.getStart(), bound.getEnd()));
        }

        ArrayList<PackOpenerPlayer> players = new ArrayList<>(playersSet);
        Collections.shuffle(players);
        ArrayList<PackOpenerPlayer> tempPlayers = new ArrayList<>();
        int counter = 0;
        boolean higherRatedPlayerIncluded = false;
        while (tempPlayers.size() < 9) {
            if (players.get(counter).getRating() > 85) {
                if (!higherRatedPlayerIncluded) {
                    tempPlayers.add(players.get(counter));
                    higherRatedPlayerIncluded = true;
                }
            } else {
                tempPlayers.add(players.get(counter));
            }
            counter++;
        }
        Collections.sort(tempPlayers);
        packPlayers = new ArrayList<>(tempPlayers);
    }

    public ArrayList<PackOpenerPlayer> getUserUniquePlayers(Context context) {

        return playerDAO.fetchUserUniquePlayers();

    }

    public Set<PackOpenerPlayer> getDuplicateCards(Context context) {

        return playerDAO.fetchDuplicatePlayers();
    }

    public int getDuplicateCardsCount(Context context) {

        return playerDAO.fetchDuplicatePlayersCount();
    }

    public int getUserUniquePlayersCount(Context context) {


        return playerDAO.fetchUserUniquePlayersCount();
    }

    public void deleteAllDuplicateCards(Context context, ArrayList<PackOpenerPlayer> players) {


        playerDAO.deleteAllDuplicatePlayers(players);
    }

    public int getTotalPlayersCount(Context context) {


        return playerDAO.getTotalPlayersCount();
    }

    public void resetPackPlayers() {
        packPlayers = new ArrayList<>();
    }

    public void deleteDuplicateCard(Context context, PackOpenerPlayer player) {


        playerDAO.deleteDuplicateCard(player);
    }

    public boolean isPlayerDuplicate(Context context, PackOpenerPlayer player) {

        return playerDAO.isPlayerDuplicate(player);
    }

    public ArrayList<Nation> getNations(Context context) {


        return playerDAO.getNations();
    }

    public ArrayList<League> getLeagues(Context context) {


        return playerDAO.getLeagues();
    }

    public ArrayList<PackOpenerPlayer> getUserUniquePlayersFilter(Context context, List<Object> nations, List<Object> leagues) {


        return playerDAO.fetchUserUniquePlayersFilters(nations, leagues);
    }

    public interface DataListener {
        void onDataLoaded(String tag, String toString);
    }

    public static PlayerManager getInstance() {
        if (mInstance == null) {
            mInstance = new PlayerManager();
        }
        initPlayerDAO();
        return mInstance;
    }

    private static void initPlayerDAO() {
        if (null == playerDAO) {
            playerDAO = new PlayerDAO(AppController.getInstance());
        }
    }

    private synchronized void parse(DataSnapshot dataSnapshot, String tag, DataListener listener) {
        new ParseTask(dataSnapshot, tag, listener).execute();
    }

    class ParseTask extends AsyncTask {

        DataSnapshot dataSnapshot;
        String tag;
        DataListener listener;

        ParseTask(DataSnapshot dataSnapshot, String tag, DataListener listener) {
            this.dataSnapshot = dataSnapshot;
            this.tag = tag;
            this.listener = listener;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            PlayerDAO playerDAO = new PlayerDAO(AppController.getInstance().getApplicationContext());
            playerDAO.clearPlayerData();
            loadMetaData(playerDAO);
            queryCounter++;
            Log.d("DATASNAPSHOT", "CHILDREN COUNT" + dataSnapshot.getChildrenCount());
            Iterator<DataSnapshot> children = dataSnapshot.getChildren().iterator();
            do {
                PackOpenerPlayer player = children.next().getValue(PackOpenerPlayer.class);
                playerDAO.savePlayer(player);
            } while (children.hasNext());
            Log.d(tag, new Date().toString());
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            this.listener.onDataLoaded(tag, "");
        }
    }

    private Bounds getRandomBound(int number) {
        if (number % 45 == 1) {
            return boundsMap.get(8);
        } else if(number % 44 == 0) {
            return boundsMap.get(7);
        } else if (number % 43 == 0) {
            return boundsMap.get(6);
        } else if (number % 42 == 0) {
            return boundsMap.get(5);
        } else if (number % 41 == 0) {
            return boundsMap.get(4);
        } else if (number % 8 == 0) {
            return boundsMap.get(3);
        } else if (number % 3 == 0) {
            return boundsMap.get(2);
        } else {
            return boundsMap.get(1);
        }
    }

    private void fillRandomBounds() {
        randomBounds = new ArrayList<>();
        Random random = new Random();
        boolean isHigherBoundPresent = false;
        boolean isHigherBond = false;
        while(randomBounds.size() < 4) {
            Bounds randomBound = getRandomBound(random.nextInt(89));
            isHigherBond = isHigherBound(randomBound);
            if(isHigherBond && !isHigherBoundPresent){
                if(!randomBounds.contains(randomBound)){
                    randomBounds.add(randomBound);
                    isHigherBoundPresent = true;
                }
            } else if(!isHigherBond){
                randomBounds.add(randomBound);
            }
        }
    }

    private boolean isHigherBound(Bounds randomBound) {
        if (higherBounds.contains(randomBound)) {
            return true;
        }
        return false;
    }

    public void loadMegaPlayersPack(Context context) {
        //44 PLAYERS, ALL RARE
        PlayerDAO playerDAO = new PlayerDAO(context);
        ArrayList<PackOpenerPlayer> players = playerDAO.getRarePlayers();

        Collections.shuffle(players);
        ArrayList<PackOpenerPlayer> tempPlayers = new ArrayList<>();
        int counter = 0;
        boolean higherRatedPlayerIncluded = false;
        while (tempPlayers.size() < 44) {
            if (players.get(counter).getRating() > 85) {
                if (!higherRatedPlayerIncluded) {
                    tempPlayers.add(players.get(counter));
                    higherRatedPlayerIncluded = true;
                }
            } else {
                tempPlayers.add(players.get(counter));
            }
            counter++;
        }
        Collections.sort(tempPlayers);
        packPlayers = new ArrayList<>(tempPlayers);
    }

    public void loadTotsPack(Context context) {
        //ONE GUARANTEED TOTS PLAYER, 10 PLAYERS IN THE PACK
        PlayerDAO playerDAO = new PlayerDAO(context);
        ArrayList<PackOpenerPlayer> totsPlayers = playerDAO.getTOTSPlayers();
        ArrayList<PackOpenerPlayer> otherPlayers = playerDAO.getNonTOTSPlayers();

        Collections.shuffle(totsPlayers);
        Collections.shuffle(otherPlayers);
        ArrayList<PackOpenerPlayer> tempPlayers = new ArrayList<>();
        //adding one tots player
        tempPlayers.add(totsPlayers.get(0));

        //adding other remaining players
        int counter = 0;
        boolean higherRatedPlayerIncluded = false;
        while (tempPlayers.size() < 9) {
            if (otherPlayers.get(counter).getRating() > 83) {
                if (!higherRatedPlayerIncluded) {
                    tempPlayers.add(otherPlayers.get(counter));
                    higherRatedPlayerIncluded = true;
                }
            } else {
                tempPlayers.add(otherPlayers.get(counter));
            }
            counter++;
        }
        Collections.sort(tempPlayers);
        packPlayers = new ArrayList<>(tempPlayers);
    }

    public void loadAll(final String tag, final DataListener listener) {
        Log.d("START", new Date().toString());
        ArrayList<PackOpenerPlayer> players = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mPlayerReference = mDatabase.getReference().child("players");
        Query query = mPlayerReference.orderByChild("rating");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parse(dataSnapshot, tag, listener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadTimeStamp(final String tag, final DataListener listener) {
        mDatabase = FirebaseDatabase.getInstance();
        mTimeReference = mDatabase.getReference("timestamp");
        Query query = mTimeReference.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("TAG", dataSnapshot.getValue().toString());
                listener.onDataLoaded(tag, dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadTOTWPlayersPack(Context context) {
        //GUARANTEED 1 TOTW PLAYER, 10 PLAYERS
        PlayerDAO playerDAO = new PlayerDAO(context);
        ArrayList<PackOpenerPlayer> totwPlayers = playerDAO.getTOTWPlayers();
        ArrayList<PackOpenerPlayer> otherPlayers = playerDAO.getNonTOTWPlayers();

        Collections.shuffle(totwPlayers);
        Collections.shuffle(otherPlayers);
        ArrayList<PackOpenerPlayer> tempPlayers = new ArrayList<>();
        //adding one totw player
        tempPlayers.add(totwPlayers.get(0));

        //adding other remaining players
        int counter = 0;
        boolean higherRatedPlayerIncluded = false;
        while (tempPlayers.size() < 9) {
            if (otherPlayers.get(counter).getRating() > 82) {
                if (!higherRatedPlayerIncluded) {
                    tempPlayers.add(otherPlayers.get(counter));
                    higherRatedPlayerIncluded = true;
                }
            } else {
                tempPlayers.add(otherPlayers.get(counter));
            }
            counter++;
        }
        Collections.sort(tempPlayers);
        packPlayers = new ArrayList<>(tempPlayers);
    }

    public void loadPrimePlayersPack(Context context) {
        //MAKE YOUR CLUB BETTER WITH 82+ RATED GUARANTEED PLAYERS
        PlayerDAO playerDAO = new PlayerDAO(context);
        ArrayList<PackOpenerPlayer> players = playerDAO.get82PlusRatedPlayers();
        Collections.shuffle(players);
        ArrayList<PackOpenerPlayer> tempPlayers = new ArrayList<>();
        int counter = 0;
        boolean higherRatedPlayerIncluded = false;
        while (tempPlayers.size() < 8) {
            if (players.get(counter).getRating() > 83) {
                if (!higherRatedPlayerIncluded) {
                    tempPlayers.add(players.get(counter));
                    higherRatedPlayerIncluded = true;
                }
            } else {
                tempPlayers.add(players.get(counter));
            }
            counter++;
        }
        Collections.sort(tempPlayers);
        packPlayers = new ArrayList<>(tempPlayers);
    }

    public void loadPremiumPlayersPack(Context context) {
        //MAKE YOUR CLUB BETTER WITH 81+ RATED GUARANTEED PLAYERS
        PlayerDAO playerDAO = new PlayerDAO(context);
        ArrayList<PackOpenerPlayer> players = playerDAO.get81PlusRatedPlayers();

        Collections.shuffle(players);
        ArrayList<PackOpenerPlayer> tempPlayers = new ArrayList<>();
        int counter = 0;
        boolean higherRatedPlayerIncluded = false;
        while (tempPlayers.size() < 8) {
            if (players.get(counter).getRating() > 83) {
                if (!higherRatedPlayerIncluded) {
                    tempPlayers.add(players.get(counter));
                    higherRatedPlayerIncluded = true;
                }
            } else {
                tempPlayers.add(players.get(counter));
            }
            counter++;
        }
        Collections.sort(tempPlayers);
        packPlayers = new ArrayList<>(tempPlayers);
    }

    public void loadRarePlayersPack(Context context) {
        //10 GUARANTEED RARE PLAYERS
        PlayerDAO playerDAO = new PlayerDAO(context);
        ArrayList<PackOpenerPlayer> players = playerDAO.getRarePlayers();

        Collections.shuffle(players);
        ArrayList<PackOpenerPlayer> tempPlayers = new ArrayList<>();
        int counter = 0;
        boolean higherRatedPlayerIncluded = false;
        while (tempPlayers.size() < 9) {
            if (players.get(counter).getRating() > 83) {
                if (!higherRatedPlayerIncluded) {
                    tempPlayers.add(players.get(counter));
                    higherRatedPlayerIncluded = true;
                }
            } else {
                tempPlayers.add(players.get(counter));
            }
            counter++;
        }
        Collections.sort(tempPlayers);
        packPlayers = new ArrayList<>(tempPlayers);
    }

    public long getSellValue(PackOpenerPlayer player) {
        long baseCoins = 0;
        switch (player.getRating()) {
            case 75:
                baseCoins = SellValue.BASIC_75.coins();
                break;
            case 76:
                baseCoins = SellValue.BASIC_76.coins();
                break;
            case 77:
                baseCoins = SellValue.BASIC_77.coins();
                break;
            case 78:
                baseCoins = SellValue.BASIC_78.coins();
                break;
            case 79:
                baseCoins = SellValue.BASIC_79.coins();
                break;
            case 80:
                baseCoins = SellValue.BASIC_80.coins();
                break;
            case 81:
                baseCoins = SellValue.BASIC_81.coins();
                break;
            case 82:
                baseCoins = SellValue.BASIC_82.coins();
                break;
            case 83:
                baseCoins = SellValue.BASIC_83.coins();
                break;
            case 84:
                baseCoins = SellValue.BASIC_84.coins();
                break;
            case 85:
                baseCoins = SellValue.BASIC_85.coins();
                break;
            case 86:
                baseCoins = SellValue.BASIC_86.coins();
                break;
            case 87:
                baseCoins = SellValue.BASIC_87.coins();
                break;
            case 88:
                baseCoins = SellValue.BASIC_88.coins();
                break;
            case 89:
                baseCoins = SellValue.BASIC_89.coins();
                break;
            case 90:
                baseCoins = SellValue.BASIC_90.coins();
                break;
            case 91:
                baseCoins = SellValue.BASIC_91.coins();
                break;
            case 92:
                baseCoins = SellValue.BASIC_92.coins();
                break;
            case 93:
                baseCoins = SellValue.BASIC_93.coins();
                break;
            case 94:
                baseCoins = SellValue.BASIC_94.coins();
                break;
            case 95:
                baseCoins = SellValue.BASIC_95.coins();
                break;
            case 96:
                baseCoins = SellValue.BASIC_96.coins();
                break;
            case 97:
                baseCoins = SellValue.BASIC_97.coins();
                break;
            case 98:
                baseCoins = SellValue.BASIC_98.coins();
                break;
            case 99:
                baseCoins = SellValue.BASIC_99.coins();
                break;
        }
        if (player.getColor().contains("rare")) {
            baseCoins = baseCoins*2;
        }
        return baseCoins;
    }

    public void loadMetaData(PlayerDAO playerDAO) {
        Gson gson = new Gson();
        String nationJson = null, leagueJson = null, clubJson = null;
        try {
            InputStream is = AppController.getInstance().getAssets().open("nations.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            nationJson = new String(buffer, "UTF-8");
            is = AppController.getInstance().getAssets().open("leagues.json");
            size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
            leagueJson = new String(buffer, "UTF-8");
            is = AppController.getInstance().getAssets().open("clubs.json");
            size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
            clubJson = new String(buffer, "UTF-8");
            NationList nationList = gson.fromJson(nationJson, new TypeToken<NationList>() {}.getType());
            LeagueList leagueList = gson.fromJson(leagueJson, new TypeToken<LeagueList>() {}.getType());
            ClubList clubList = gson.fromJson(clubJson, new TypeToken<ClubList>() {}.getType());
            for (int i = 0;i<nationList.getNations().size();i++) {
                playerDAO.insertNation(nationList.getNations().get(i));
            }

            for (int i = 0;i<leagueList.getLeagues().size();i++) {
                playerDAO.insertLeague(leagueList.getLeagues().get(i));
            }

            for (int i = 0;i<clubList.getClubs().size();i++) {
                playerDAO.insertClub(clubList.getClubs().get(i));
            }
            Log.d("TAG", "TAG");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
