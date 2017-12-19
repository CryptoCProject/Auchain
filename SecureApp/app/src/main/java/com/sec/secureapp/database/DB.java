package com.sec.secureapp.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DB";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

    public void createTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_TABLE_KEYS = "CREATE TABLE IF NOT EXISTS Keys (myPrivateKey TEXT, serverPublicKey TEXT)";
        db.execSQL(CREATE_TABLE_KEYS);
        db.close();
    }

    public void initializeValues() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO Keys (myPrivateKey, serverPublicKey) VALUES(?, ?)";
        SQLiteStatement insertStmt = db.compileStatement(sql);
        insertStmt.clearBindings();
        byte[] b = new byte[1024];
        insertStmt.bindBlob(1, b);
        insertStmt.bindBlob(2, b);
        insertStmt.executeInsert();
        db.close();
        insertStmt.close();
    }

    public void setMyPrivateKey(byte [] pkFile) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("myPrivateKey", pkFile);
            db.update("Keys", values, null, null );
            db.close();
        } catch (Exception e) {
            db.close();
            e.printStackTrace();
        }
    }

    public void setServerPublicKey(byte [] pkFile) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("serverPublicKey", pkFile);
            db.update("Keys", values, null, null );
            db.close();
        } catch (Exception e) {
            db.close();
            e.printStackTrace();
        }
    }

    public PrivateKey getMyPrivateKey() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT myPrivateKey FROM Keys";
        Cursor cursor = db.rawQuery(sql, new String[] {});

        byte[] pk = null;
        if(cursor.moveToFirst()){
            pk = cursor.getBlob(0);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        if(cursor.getCount() == 0){
            return null;
        } else {
            try{
                File pkf = File.createTempFile("private", ".key");
                FileOutputStream outputStream = new FileOutputStream(pkf);
                outputStream.write(pk);
                outputStream.close();
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(pkf));
                PrivateKey privateKey = (PrivateKey) inputStream.readObject();
                inputStream.close();
                db.close();
                cursor.close();
                return privateKey;
            }catch(Exception e){
                e.printStackTrace();
                db.close();
                cursor.close();
                return null;
            }
        }
    }

    public PublicKey getServerPublicKey() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT serverPublicKey FROM Keys";
        Cursor cursor = db.rawQuery(sql, new String[] {});

        byte[] pk = null;
        if(cursor.moveToFirst()){
            pk = cursor.getBlob(0);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        if(cursor.getCount() == 0){
            return null;
        } else {
            try{
                File pkf = File.createTempFile("public", ".key");
                FileOutputStream outputStream = new FileOutputStream(pkf);
                outputStream.write(pk);
                outputStream.close();
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(pkf));
                PublicKey publicKey = (PublicKey) inputStream.readObject();
                inputStream.close();
                db.close();
                cursor.close();
                return publicKey;
            }catch(Exception e){
                e.printStackTrace();
                db.close();
                cursor.close();
                return null;
            }
        }
    }

}
