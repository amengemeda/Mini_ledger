package com.cat.miniledger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Mitch on 2016-05-13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mini_ledger.db";
    public static final String TABLE_NAME = "transactions";
    public static final String COL1 = "transaction_id";
    public static final String COL2 = "transaction_amount";
    public static final String COL3 = "transaction_type";
    public static final String COL4 = "transaction_date";
    public static final String COL5 = "transaction_reason";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("+ COL1 +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " DOUBLE NOT NULL,"+COL3 +" VARCHAR(10) NOT NULL," + COL4 + " VARCHAR(20) NOT NULL,"+ COL5 + " TEXT)";

//        String createTableSql = "CREATE TABLE transactions (transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, transaction_amount " +
//                "DOUBLE NOT NULL, transaction_type VARCHAR(10) NOT NULL, transaction_date VARCHAR(20))";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(double transactionAmount, String transactionType, String transactionDate, String transactionReason  ) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, transactionAmount);
        contentValues.put(COL3, transactionType);
        contentValues.put(COL4, transactionDate);
        contentValues.put(COL5, transactionReason);


        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public String getBalance(){
        double netBalance=0;
        SQLiteDatabase db = this.getWritableDatabase();
        String[] incomeArg = {"Income"};
        Cursor incomeCursor = db.rawQuery("SELECT sum(transaction_amount) FROM " + TABLE_NAME + " WHERE transaction_type = ?", incomeArg);
        if (incomeCursor.getCount()!=0){
            if(incomeCursor.moveToNext()){
                netBalance+= incomeCursor.getDouble(0);
            }
        }
        String[] expenditureArg = {"Expense"};
        Cursor expenditureCursor = db.rawQuery("SELECT sum(transaction_amount) FROM " + TABLE_NAME + " WHERE transaction_type = ?", expenditureArg);
        if (expenditureCursor.getCount()!=0){
            if(expenditureCursor.moveToNext()){
                netBalance-= expenditureCursor.getDouble(0);
            }
        }

        return String.valueOf(netBalance);
    }

    public String getTotalIncome(){
        String totalIncome="";
        SQLiteDatabase db = this.getWritableDatabase();
        String[] incomeArg = {"Income"};
        Cursor incomeCursor = db.rawQuery("SELECT sum(transaction_amount) FROM " + TABLE_NAME + " WHERE transaction_type = ?", incomeArg);
        if (incomeCursor.getCount()!=0){
            if(incomeCursor.moveToNext()){
                totalIncome= incomeCursor.getString(0);
            }
        }else{
            totalIncome= "0 ksh";
        }
        return totalIncome;
    }
    public String getTotalExpenditure(){
        String totalExpenditure="";
        SQLiteDatabase db = this.getWritableDatabase();
        String[] incomeArg = {"Expense"};
        Cursor incomeCursor = db.rawQuery("SELECT sum(transaction_amount) FROM " + TABLE_NAME + " WHERE transaction_type = ?", incomeArg);
        if (incomeCursor.getCount()!=0){
            if(incomeCursor.moveToNext()){
                totalExpenditure= incomeCursor.getString(0);
            }
        }else{
            totalExpenditure= "0 ksh";
        }
        return totalExpenditure;
    }
}