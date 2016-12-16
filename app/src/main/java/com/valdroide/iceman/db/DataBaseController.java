package com.valdroide.iceman.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.valdroide.iceman.entities.Client;
import com.valdroide.iceman.entities.IceSale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LEO on 2/12/2016.
 */
public class DataBaseController {

    Context context;
    SQLiteDataBase sqLiteDataBase;
    SQLiteDatabase database;

    public DataBaseController(Context context) {
        this.context = context;
    }

    public DataBaseController openDataBase() throws SQLException {
        sqLiteDataBase = new SQLiteDataBase(context,
                "DataBase.db", null, 1);
        database = sqLiteDataBase.getWritableDatabase();
        return this;
    }

    public void closeDataBase() {
        sqLiteDataBase.close();
    }

    //    INSERT/UPDATE/DELETE Client
    public boolean insertClient(String client)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        openDataBase();
        try {
            cv.put("NAME", client);

            long valor = database.insert("CLIENT", null, cv);
            closeDataBase();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            closeDataBase();
            return false;
        }
    }

    public boolean updateClient(Client Client)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        openDataBase();
        try {
            cv.put("NAME", Client.getNAME());

            long valor = database.update("CLIENT", cv, "ID_CLIENT = " + Client.getID_CLIENT(), null);
            closeDataBase();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            closeDataBase();
            return false;
        }
    }

    public List<Client> selectAllClient() {
        String sql = "SELECT * FROM CLIENT ORDER BY ID_CLIENT DESC";
        List<Client> arrayClients = new ArrayList<>();
        String name = null;
        int id;
        Cursor cursor = null;
        openDataBase();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        Client client= null;
                        id = cursor.getInt(cursor.getColumnIndex("ID_CLIENT"));
                        name = cursor.getString(cursor.getColumnIndex("NAME"));
                        client = new Client(id, name);
                        arrayClients.add(client);
                    }
                }
            } catch (Exception e) {
                arrayClients = null;
            }
        } else {
            arrayClients = null;
        }
        closeDataBase();
        sql = null;
        cursor = null;
        database = null;
        name = null;
        return arrayClients;
    }

    //    INSERT/UPDATE/DELETE SALE
    public boolean insertSale(IceSale sale)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        openDataBase();
        try {
            cv.put("ID_CLIENT", sale.getID_CLIENT());
            cv.put("QUANTITY", sale.getQUANTITY());
            cv.put("AMOUNT", sale.getAMOUNT());
            cv.put("DATE_SALE", sale.getDATE());

            long valor = database.insert("SALE", null, cv);
            closeDataBase();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            closeDataBase();
            return false;
        }
    }
//
//    public boolean deleteCheck(List<Check> checks)
//            throws SQLiteException {
//        boolean result = true;
//        ContentValues cv = new ContentValues();
//        openDataBase();
//        try {
//            for (int i = 0; i < checks.size(); i++) {
//                long valor = database.delete("CHECKS", "ID_CHECK=" + checks.get(i).getId_check(), null);
//                if (valor > 0) {
//                    result = true;
//                } else {
//                    result = false;
//                    break;
//                }
//            }
//            closeDataBase();
//        } catch (SQLiteException e) {
//            closeDataBase();
//            result = false;
//        }
//        return result;
//    }
//
//
    public List<IceSale> selectSales(String since, String until, int id_client) {

        List<IceSale> listSales = new ArrayList<>();
        String sql = "SELECT (SELECT SUM(QUANTITY) FROM SALE WHERE ID_CLIENT = " + id_client + " AND" +
                " DATE(substr(DATE_SALE,5,4) || substr(DATE_SALE,3,2) || substr(DATE_SALE,1,2))" +
                " BETWEEN DATE('" + since + "') AND DATE('" + until + "')) AS QUANTITY_TOTAL," +
                " (SELECT SUM(AMOUNT) FROM SALE WHERE ID_CLIENT = " + id_client + " AND" +
                " DATE(substr(DATE_SALE,5,4) || substr(DATE_SALE,3,2) || substr(DATE_SALE,1,2))" +
                " BETWEEN DATE('" + since + "') AND DATE('" + until + "')) AS AMOUNT_TOTAL," +
                " QUANTITY, AMOUNT, DATE_SALE, C.NAME FROM SALE" +
                " INNER JOIN CLIENT C ON C.ID_CLIENT = SALE.ID_CLIENT" +
                " WHERE SALE.ID_CLIENT = " + id_client + " AND"+
                " DATE(substr(DATE_SALE,5,4) || substr(DATE_SALE,3,2) || substr(DATE_SALE,1,2))" +
                " BETWEEN DATE('" + since + "') AND DATE('" + until + "')";

        IceSale iceSale = null;
        Cursor cursor = null;
        openDataBase();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        //    Check check = null;
                        iceSale = new IceSale();
                        iceSale.setQUANTITY_TOTAL(cursor.getInt(cursor
                                .getColumnIndex("QUANTITY_TOTAL")));
                        iceSale.setAMOUNT_TOTAL(cursor.getInt(cursor
                                .getColumnIndex("AMOUNT_TOTAL")));
                        iceSale.setQUANTITY(cursor.getInt(cursor
                                .getColumnIndex("QUANTITY")));
                        iceSale.setAMOUNT(cursor.getInt(cursor
                                .getColumnIndex("AMOUNT")));
                        iceSale.setDATE(cursor.getString(cursor
                                .getColumnIndex("DATE_SALE")));
                        iceSale.setCLIENT(cursor.getString(cursor
                                .getColumnIndex("NAME")));
                        listSales.add(iceSale);

                    }
                }
            } catch (Exception e) {
                listSales = null;
            }
        } else {
            listSales = null;
        }
        closeDataBase();
        sql = null;
        cursor = null;
        database = null;
        return listSales;
    }

    public List<IceSale> selectSales(String since, String until) {

        List<IceSale> listSales = new ArrayList<>();
        String sql = "SELECT (SELECT SUM(QUANTITY) FROM SALE WHERE " +
                " DATE(substr(DATE_SALE,5,4) || substr(DATE_SALE,3,2) || substr(DATE_SALE,1,2))" +
                " BETWEEN DATE('" + since + "') AND DATE('" + until + "')) AS QUANTITY_TOTAL," +
                " (SELECT SUM(AMOUNT) FROM SALE WHERE" +
                " DATE(substr(DATE_SALE,5,4) || substr(DATE_SALE,3,2) || substr(DATE_SALE,1,2))" +
                " BETWEEN DATE('" + since + "') AND DATE('" + until + "')) AS AMOUNT_TOTAL," +
                " QUANTITY, AMOUNT, DATE_SALE, C.NAME FROM SALE" +
                " INNER JOIN CLIENT C ON C.ID_CLIENT = SALE.ID_CLIENT" +
                " WHERE  DATE(substr(DATE_SALE,5,4) || substr(DATE_SALE,3,2) || substr(DATE_SALE,1,2))" +
                " BETWEEN DATE('" + since + "') AND DATE('" + until + "')";

        IceSale iceSale = null;
        Cursor cursor = null;
        openDataBase();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        //    Check check = null;
                        iceSale = new IceSale();
                        iceSale.setQUANTITY_TOTAL(cursor.getInt(cursor
                                .getColumnIndex("QUANTITY_TOTAL")));
                        iceSale.setAMOUNT_TOTAL(cursor.getInt(cursor
                                .getColumnIndex("AMOUNT_TOTAL")));
                        iceSale.setQUANTITY(cursor.getInt(cursor
                                .getColumnIndex("QUANTITY")));
                        iceSale.setAMOUNT(cursor.getInt(cursor
                                .getColumnIndex("AMOUNT")));
                        iceSale.setDATE(cursor.getString(cursor
                                .getColumnIndex("DATE_SALE")));
                        iceSale.setCLIENT(cursor.getString(cursor
                                .getColumnIndex("NAME")));
                        listSales.add(iceSale);

                    }
                }
            } catch (Exception e) {
                listSales = null;
            }
        } else {
            listSales = null;
        }
        closeDataBase();
        sql = null;
        cursor = null;
        database = null;
        return listSales;
    }
//
//
//    public List<Check> selectMaturities(String since, String until) {
//
//        String sql = "SELECT * FROM CHECKS WHERE DATE(substr(EXPIRATION,7,4) || substr(EXPIRATION,4,2) || substr(EXPIRATION,1,2)) " +
//                "BETWEEN DATE('" + since + "') AND DATE('" + until + "') ORDER BY ID_CHECK DESC;";
//        List<Check> checkArrayList = new ArrayList<Check>();
//        String number = null, amount = null, origin = null, date = null, destiny = null, destinyDate = null;
//        int id, type;
//        byte[] photo = null;
//        Cursor cursor = null;
//        openDataBase();
//        if (database != null && database.isOpen()) {
//            try {
//                cursor = database.rawQuery(sql, null);
//                if (cursor != null && cursor.getCount() > 0) {
//                    while (cursor.moveToNext()) {
//                        Check check = null;
//                        id = cursor.getInt(cursor.getColumnIndex("ID_CHECK"));
//                        type = cursor.getInt(cursor.getColumnIndex("TYPE"));
//                        number = cursor.getString(cursor
//                                .getColumnIndex("NUMBER"));
//                        photo = cursor.getBlob(cursor
//                                .getColumnIndex("PHOTO"));
//                        amount = cursor.getString(cursor
//                                .getColumnIndex("AMOUNT"));
//                        origin = cursor.getString(cursor
//                                .getColumnIndex("ORIGIN"));
//                        destiny = cursor.getString(cursor
//                                .getColumnIndex("DESTINY"));
//                        date = cursor.getString(cursor
//                                .getColumnIndex("EXPIRATION"));
//                        destinyDate = cursor.getString(cursor
//                                .getColumnIndex("DESTINYDATE"));
//                        check = new Check(id, type, number, amount, date, origin, destiny, destinyDate, photo);
//                        checkArrayList.add(check);
//                    }
//                }
//            } catch (Exception e) {
//                checkArrayList = null;
//            }
//        } else {
//            checkArrayList = null;
//        }
//        closeDataBase();
//        sql = null;
//        cursor = null;
//        database = null;
//        number = null;
//        amount = null;
//        origin = null;
//        date = null;
//        destiny = null;
//        destinyDate = null;
//        return checkArrayList;
//    }
//
//    public List<Check> selectAllCheckForSearch(String search) {
//        String sql = "SELECT * FROM CHECKS WHERE NUMBER LIKE '%"+ search +"%' OR AMOUNT LIKE '%" + search + "%' OR DESTINY LIKE '%" + search + "%' OR ORIGIN LIKE '%" + search + "%' ORDER BY ID_CHECK DESC";
//        List<Check> arrayChecks = new ArrayList<Check>();
//        String number = null, amount = null, origin = null, date = null, destiny = null, destinyDate = null;
//        int id, type;
//        byte[] photo = null;
//        Cursor cursor = null;
//        openDataBase();
//        if (database != null && database.isOpen()) {
//            try {
//                cursor = database.rawQuery(sql, null);
//                if (cursor != null && cursor.getCount() > 0) {
//                    while (cursor.moveToNext()) {
//                        Check check = null;
//                        id = cursor.getInt(cursor.getColumnIndex("ID_CHECK"));
//                        type = cursor.getInt(cursor.getColumnIndex("TYPE"));
//                        number = cursor.getString(cursor
//                                .getColumnIndex("NUMBER"));
//                        photo = cursor.getBlob(cursor
//                                .getColumnIndex("PHOTO"));
//                        amount = cursor.getString(cursor
//                                .getColumnIndex("AMOUNT"));
//                        origin = cursor.getString(cursor
//                                .getColumnIndex("ORIGIN"));
//                        destiny = cursor.getString(cursor
//                                .getColumnIndex("DESTINY"));
//                        date = cursor.getString(cursor
//                                .getColumnIndex("EXPIRATION"));
//                        destinyDate = cursor.getString(cursor
//                                .getColumnIndex("DESTINYDATE"));
//                        check = new Check(id, type, number, amount, date, origin, destiny, destinyDate, photo);
//                        arrayChecks.add(check);
//                    }
//                }
//            } catch (Exception e) {
//                arrayChecks = null;
//            }
//        } else {
//            arrayChecks = null;
//        }
//        closeDataBase();
//        sql = null;
//        cursor = null;
//        database = null;
//        number = null;
//        amount = null;
//        origin = null;
//        date = null;
//        destiny = null;
//        destinyDate = null;
//        return arrayChecks;
//    }
//
}
