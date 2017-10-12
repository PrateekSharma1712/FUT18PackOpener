package com.prateek.fut17packopener.database;

import android.provider.BaseColumns;

/**
 * Created by prateek on 30/7/17.
 */

public class Queries {

    public static final String TABLE_PLAYER = "TABLE_PLAYER";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_IMAGE = "IMAGE";
    public static final String COLUMN_LEAGUE_ID_FK = "LEAGUE_ID_FK";
    public static final String COLUMN_CLUB_ID_FK = "CLUB_ID_FK";
    public static final String COLUMN_NATION_ID_FK = "NATION_ID_FK";
    public static final String COLUMN_POSITION = "POSITION";
    public static final String COLUMN_RATING = "RATING";
    public static final String COLUMN_PACE = "PACE";
    public static final String COLUMN_SHOT = "SHOT";
    public static final String COLUMN_PASS = "PASS";
    public static final String COLUMN_DRIBBLE = "DRIBBLE";
    public static final String COLUMN_DEFEND = "DEFEND";
    public static final String COLUMN_PHYSICAL = "PHYSICAL";
    public static final String COLUMN_COLOR = "COLOR";

    public static final String TABLE_NATION = "TABLE_NATION";
    public static final String COLUMN_NATION_ID = "NATION_ID";
    public static final String COLUMN_NATION_ABBR_NAME = "NATION_ABBR_NAME";
    public static final String COLUMN_NATION_NAME = "NATION_NAME";
    public static final String COLUMN_NATION_IMG_LARGE = "NATION_LARGE";

    public static final String TABLE_LEAGUE = "TABLE_LEAGUE";
    public static final String COLUMN_LEAGUE_ID = "LEAGUE_ID";
    public static final String COLUMN_LEAGUE_ABBR_NAME = "LEAGUE_ABBR_NAME";
    public static final String COLUMN_LEAGUE_NAME = "LEAGUE_NAME";
    public static final String COLUMN_LEAGUE_IMG_LARGE = "LEAGUE_LARGE";

    public static final String TABLE_CLUB = "TABLE_CLUB";
    public static final String COLUMN_CLUB_ID = "CLUB_ID";
    public static final String COLUMN_CLUB_ABBR_NAME = "CLUB_ABBR_NAME";
    public static final String COLUMN_CLUB_NAME = "CLUB_NAME";
    public static final String COLUMN_CLUB_IMG_LARGE = "CLUB_LARGE";

    public static final String CREATE_QUERY_PLAYER_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_PLAYER +" ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ID + " TEXT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_IMAGE + " TEXT, "
            + COLUMN_LEAGUE_ID_FK + " INTEGER, "
            + COLUMN_CLUB_ID_FK + " INTEGER, "
            + COLUMN_NATION_ID_FK + " INTEGER, "
            + COLUMN_POSITION + " TEXT, "
            + COLUMN_RATING + " INTEGER, "
            + COLUMN_PACE + " INTEGER, "
            + COLUMN_SHOT + " INTEGER, "
            + COLUMN_PASS + " INTEGER, "
            + COLUMN_DRIBBLE + " INTEGER, "
            + COLUMN_DEFEND + " INTEGER, "
            + COLUMN_PHYSICAL + " INTEGER, "
            + COLUMN_COLOR + " TEXT)";

    public static final String CREATE_QUERY_TABLE_NATION = "CREATE TABLE IF NOT EXISTS "+ TABLE_NATION +" ("
            + COLUMN_NATION_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_NATION_ABBR_NAME + " TEXT, "
            + COLUMN_NATION_NAME + " TEXT,"
            + COLUMN_NATION_IMG_LARGE + " TEXT)";

    public static final String CREATE_QUERY_TABLE_LEAGUE = "CREATE TABLE IF NOT EXISTS "+ TABLE_LEAGUE +" ("
            + COLUMN_LEAGUE_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_LEAGUE_ABBR_NAME + " TEXT, "
            + COLUMN_LEAGUE_NAME + " TEXT,"
            + COLUMN_LEAGUE_IMG_LARGE + " TEXT)";

    public static final String CREATE_QUERY_TABLE_CLUB = "CREATE TABLE IF NOT EXISTS "+ TABLE_CLUB +" ("
            + COLUMN_CLUB_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_CLUB_ABBR_NAME + " TEXT, "
            + COLUMN_CLUB_NAME + " TEXT,"
            + COLUMN_CLUB_IMG_LARGE + " TEXT,"
            + COLUMN_LEAGUE_ID_FK + " INTEGER)";

    public static final String USER_PLAYERS_TABLE = "USER_PLAYER_TABLE";

    public static final String CREATE_QUERY_USER_PLAYER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_PLAYERS_TABLE + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ID + " TEXT)";

    public static final String FETCH_USER_DUPLICATE_PLAYERS = "SELECT * FROM " + TABLE_PLAYER + " AS P JOIN " + USER_PLAYERS_TABLE + " AS U"
            + " ON P." + COLUMN_ID + " = " + "U." + COLUMN_ID
            + " JOIN " + TABLE_LEAGUE + " AS L ON P." + COLUMN_LEAGUE_ID_FK + " = L." + COLUMN_LEAGUE_ID
            + " JOIN " + TABLE_CLUB + " AS C ON P." + COLUMN_CLUB_ID_FK + " = C." + COLUMN_CLUB_ID
            + " JOIN " + TABLE_NATION + " AS N ON P." + COLUMN_NATION_ID_FK + " = N." +COLUMN_NATION_ID
            + " WHERE P." + COLUMN_ID + " IN (SELECT " + COLUMN_ID + " FROM " + USER_PLAYERS_TABLE + " GROUP BY "+ COLUMN_ID +" HAVING COUNT("+ COLUMN_ID +") > 1) GROUP BY P." + COLUMN_ID;

    public static final String FETCH_USER_DUPLICATE_PLAYERS_COUNT = "SELECT COUNT(*) FROM " + TABLE_PLAYER + " AS P JOIN " + USER_PLAYERS_TABLE + " AS U"
            + " ON P." + COLUMN_ID + " = " + "U." + COLUMN_ID
            + " WHERE P." + COLUMN_ID + " IN (SELECT " + COLUMN_ID + " FROM " + USER_PLAYERS_TABLE + " GROUP BY "+ COLUMN_ID +" HAVING COUNT("+ COLUMN_ID +") > 1) GROUP BY P." + COLUMN_ID;

    public static final String FETCH_USER_UNIQUE_PLAYERS = "SELECT * FROM " + TABLE_PLAYER + " AS P JOIN " + USER_PLAYERS_TABLE + " AS U"
            + " ON P." + COLUMN_ID + " = " + "U." + COLUMN_ID
            + " JOIN " + TABLE_LEAGUE + " AS L ON P." + COLUMN_LEAGUE_ID_FK + " = L." + COLUMN_LEAGUE_ID
            + " JOIN " + TABLE_CLUB + " AS C ON P." + COLUMN_CLUB_ID_FK + " = C." + COLUMN_CLUB_ID
            + " JOIN " + TABLE_NATION + " AS N ON P." + COLUMN_NATION_ID_FK + " = N." +COLUMN_NATION_ID
            + " GROUP BY P." + COLUMN_ID;

    public static final String FETCH_USER_UNIQUE_PLAYERS_FILTER = "SELECT * FROM " + TABLE_PLAYER + " AS P JOIN " + USER_PLAYERS_TABLE + " AS U"
            + " ON P." + COLUMN_ID + " = " + "U." + COLUMN_ID
            + " JOIN " + TABLE_LEAGUE + " AS L ON P." + COLUMN_LEAGUE_ID_FK + " = L." + COLUMN_LEAGUE_ID
            + " JOIN " + TABLE_CLUB + " AS C ON P." + COLUMN_CLUB_ID_FK + " = C." + COLUMN_CLUB_ID
            + " JOIN " + TABLE_NATION + " AS N ON P." + COLUMN_NATION_ID_FK + " = N." +COLUMN_NATION_ID;
            //+ " GROUP BY P." + COLUMN_ID;

    public static final String FETCH_USER_UNIQUE_PLAYERS_COUNT = "SELECT P."+COLUMN_ID+" FROM " + TABLE_PLAYER + " AS P JOIN " + USER_PLAYERS_TABLE + " AS U"
            + " ON P." + COLUMN_ID + " = " + "U." + COLUMN_ID
            + " JOIN " + TABLE_LEAGUE + " AS L ON P." + COLUMN_LEAGUE_ID_FK + " = L." + COLUMN_LEAGUE_ID
            + " JOIN " + TABLE_CLUB + " AS C ON P." + COLUMN_CLUB_ID_FK + " = C." + COLUMN_CLUB_ID
            + " JOIN " + TABLE_NATION + " AS N ON P." + COLUMN_NATION_ID_FK + " = N." +COLUMN_NATION_ID
            + " GROUP BY P." + COLUMN_ID;

    public static final String FETCH_PLAYERS = "SELECT * FROM " + TABLE_PLAYER + " AS P JOIN " + TABLE_LEAGUE + " AS L ON P." + COLUMN_LEAGUE_ID_FK + " = L." + COLUMN_LEAGUE_ID
            + " JOIN " + TABLE_CLUB + " AS C ON P." + COLUMN_CLUB_ID_FK + " = C." + COLUMN_CLUB_ID
            + " JOIN " + TABLE_NATION + " AS N ON P." + COLUMN_NATION_ID_FK + " = N." +COLUMN_NATION_ID;

    public static final String FETCH_TOTAL_PLAYERS_COUNT = "SELECT count(*) FROM "+ TABLE_PLAYER;

}
