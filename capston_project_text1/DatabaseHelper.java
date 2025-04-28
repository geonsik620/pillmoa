package com.example.capston_project_text1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "personal_info_db";
    private static final int DATABASE_VERSION = 1;

    // 테이블 이름과 컬럼
    public static final String TABLE_PERSONAL_INFO = "personal_info";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BIRTH_YEAR = "birth_year";
    public static final String COLUMN_VISION_STATUS = "vision_status";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_PREGNANCY_STATUS = "pregnancy_status";

    // SQL 쿼리로 테이블 생성
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PERSONAL_INFO + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_BIRTH_YEAR + " text not null, "
            + COLUMN_VISION_STATUS + " integer, "
            + COLUMN_GENDER + " integer, "
            + COLUMN_PREGNANCY_STATUS + " integer"
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAL_INFO);
        onCreate(db);
    }

    // 데이터를 삽입하는 메소드
    public static boolean insertPersonalInfo(SQLiteDatabase db, String birthYear, int visionStatus, int gender, Integer pregnancyStatus) {
        String insertQuery = "INSERT INTO " + TABLE_PERSONAL_INFO +
                " (" + COLUMN_BIRTH_YEAR + ", " + COLUMN_VISION_STATUS + ", " + COLUMN_GENDER + ", " + COLUMN_PREGNANCY_STATUS + ") " +
                "VALUES ('" + birthYear + "', " + visionStatus + ", " + gender + ", " + (pregnancyStatus != null ? pregnancyStatus : "NULL") + ")";
        db.execSQL(insertQuery);
        return true;
    }
}