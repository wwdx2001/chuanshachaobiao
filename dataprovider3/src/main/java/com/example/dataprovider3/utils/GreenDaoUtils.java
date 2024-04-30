package com.example.dataprovider3.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dataprovider3.greendaoDao.DaoMaster;
import com.example.dataprovider3.greendaoDao.DaoSession;

import org.greenrobot.greendao.async.AsyncSession;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2020/1/9 13:05
 */
public class GreenDaoUtils {

    public DaoSession sDaoSession;
    private SQLiteDatabase db;
    private AsyncSession sAsyncSession;
    private static String yuangongh;
    private String IMEI="IMEI";
    private String IMAGES7="IMAGES7";

    private GreenDaoUtils() {
    }

    public static GreenDaoUtils getInstance() {
        return SingleHolder.singleInstance;
    }

    public DaoSession getDaoSession(Context context) {
        if (sDaoSession == null) {
            DaoMaster.DevOpenHelper helper = getDevOpenHelper(context);
            db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            sDaoSession = daoMaster.newSession();

        }
        return sDaoSession;
    }

    public void clearAll(Context context) {
        DaoMaster.DevOpenHelper helper = getDevOpenHelper(context);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoMaster.dropAllTables(daoMaster.getDatabase(), true);
//        DaoMaster.createAllTables(daoMaster.getDatabase(), true);
    }

    public void clearOther(Context context) {
        DaoMaster.DevOpenHelper helper = getDevOpenHelper(context);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoMaster.dropAllTables(daoMaster.getDatabase(), true);
        DaoMaster.createAllTables(daoMaster.getDatabase(), true);
    }

    public AsyncSession getAsyncSession(Context context) {
        if (sDaoSession == null) {
            DaoMaster.DevOpenHelper helper = getDevOpenHelper(context);
            db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            sDaoSession = daoMaster.newSession();
        }
        sAsyncSession = sDaoSession.startAsyncSession();
        return sAsyncSession;
    }

    private static DaoMaster.DevOpenHelper getDevOpenHelper(Context context) {
        DatabaseContext databaseContext = new DatabaseContext(context);
//        return new DaoMaster.DevOpenHelper(databaseContext, "xunjian.db", null);
        return new DaoMaster.DevOpenHelper(databaseContext, "main.cbj");
    }


    static class SingleHolder {
        public static GreenDaoUtils singleInstance = new GreenDaoUtils();
    }

  /**
   * 方法1：检查某表列是否存在
   *
   * @param db
   * @param tableName  表名
   * @param columnName 列名
   * @return
   */
  private static boolean checkColumnExist(SQLiteDatabase db, String tableName, String columnName) {
    boolean result = false;
    Cursor cursor = null;
    try {
      //查询一行
      cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
      result = cursor != null && cursor.getColumnIndex(columnName) != -1;
    } catch (Exception e) {
      Log.e("", "checkColumnExists..." + e.getMessage());
    } finally {
      if (null != cursor && !cursor.isClosed()) {
        cursor.close();
      }
    }

    return result;
  }

  /**
   * 向某表添加一列
   *
   * @param tableName  表名
   * @param columnName 列名
   */
  private static void addColumn(SQLiteDatabase _db,String tableName, String columnName) {

    String sql = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " TEXT;";

    _db.beginTransaction();//开始事务
    try {
      _db.execSQL(sql);
      _db.setTransactionSuccessful();//调用此方法会在执行到endTransaction() 时提交当前事务，如果不调用此方法会回滚事务
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      _db.endTransaction();//由事务的标志决定是提交事务，还是回滚事务
    }
  }
}
