package com.sh3h.dataprovider.greendaoDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sh3h.serverprovider.entity.BiaoKaListBean;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.QueryBuilder;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "BIAO_KA_LIST_BEAN".
*/
public class BiaoKaListBeanDao extends AbstractDao<BiaoKaListBean, Long> {

    public static final String TABLENAME = "BIAO_KA_LIST_BEAN";

  public List<BiaoKaListBean> getBiaokaList(String xiaogenghao) {
      QueryBuilder qb = this.queryBuilder();
      switch (xiaogenghao){
          case "all" :
              return this.loadAll();
          case "yes":
              qb.where(Properties.XUNJIANZT.eq("已巡检"));
              break;
          case "no":
              qb.where(Properties.XUNJIANZT.eq("待巡检"));
              break;
          default:

              qb.where(Properties.S_CID.eq(xiaogenghao));

              break;
      }
      return qb.list();

  }

    public List<BiaoKaListBean> getBiaokaListBeans(String renwuhao, String type) {
      if (type.equals("yc")){
          QueryBuilder qb = this.queryBuilder();
          qb.where(Properties.S_RENWUMC.eq(renwuhao), Properties.IsSave.eq(1));
          return qb.list();
      }else {
          QueryBuilder qb = this.queryBuilder();
          qb.where(Properties.S_RENWUMC.eq(renwuhao), Properties.IsSave.eq(0), Properties.IsCommit.eq(0), Properties.IsUploadImage.eq(0));
          return qb.list();
      }

    }

    public List<BiaoKaListBean> getXunJianhistoryBK() {
        QueryBuilder qb = this.queryBuilder();
        qb.where(Properties.IsCommit.eq(1));
        return qb.list();
    }

  public List<BiaoKaListBean> getWcorYcBiaoKalistbean(String renwumc, String type) {
    QueryBuilder qb = this.queryBuilder();
    if (type.equals("wc")){
      return qb.where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renwumc))
        .where(BiaoKaListBeanDao.Properties.IsCommit.eq(0))
        .where(BiaoKaListBeanDao.Properties.IsSave.eq(0))
        .where(BiaoKaListBeanDao.Properties.TYPE.eq("远传巡检"))
        .list();
    }else {
      return qb.where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renwumc))
        .where(BiaoKaListBeanDao.Properties.TYPE.eq("远传巡检"))
        .where(BiaoKaListBeanDao.Properties.IsSave.eq(1))
        .list();
    }

  }

  public List<BiaoKaListBean> getBiaoKaListBean(String renwuid) {
    QueryBuilder qb = this.queryBuilder();
    return qb .where(BiaoKaListBeanDao.Properties.S_RENWUID.eq(renwuid)).list();
  }

  public void deleteBiaoKaListBean() {
    QueryBuilder qb = this.queryBuilder();
    qb.whereOr(BiaoKaListBeanDao.Properties.TYPE.eq("远传巡检"),
      BiaoKaListBeanDao.Properties.TYPE.isNull())
      .where(BiaoKaListBeanDao.Properties.IsCommit.eq(0))
      .buildDelete().executeDeleteWithoutDetachingEntities();

  }

  public List<BiaoKaListBean> getWcorYcBiaoKalistbean2(String renwumc, String type) {
    QueryBuilder qb = this.queryBuilder();
    if (type.equals("yc")){
      return qb.where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renwumc))
        .where(BiaoKaListBeanDao.Properties.IsSave.eq(1)).list();
    }else {
      return qb.where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renwumc))
        .where(BiaoKaListBeanDao.Properties.IsCommit.eq(0))
        .where(BiaoKaListBeanDao.Properties.IsUploadImage.eq(0))
        .where(BiaoKaListBeanDao.Properties.IsSave.eq(0)).list();
    }
  }

  public void saveXunjianBKlistBean(BiaoKaListBean biaoKaListBean) {
    insertOrReplace(biaoKaListBean);
  }

  public List<BiaoKaListBean> getBiaoKaListBeanDao(String renwumc) {
    QueryBuilder qb = this.queryBuilder();
  return qb.where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renwumc)).list();
  }

  public List<BiaoKaListBean> getBiaoKaListBeanDao(String renwumc, int iscommit, int isuploadimage) {
    QueryBuilder qb = this.queryBuilder();
    return qb.where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renwumc))
      .whereOr(BiaoKaListBeanDao.Properties.IsCommit.eq(iscommit),
        BiaoKaListBeanDao.Properties.IsUploadImage.eq(isuploadimage)).list();
  }

  public long getBiaoKaListBean2(String renwumc, int iscommit, int isuploadimage) {
    QueryBuilder qb = this.queryBuilder();
    return qb.where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renwumc))
            .whereOr(BiaoKaListBeanDao.Properties.IsCommit.eq(iscommit), BiaoKaListBeanDao.Properties.IsUploadImage.eq(isuploadimage))
            .count();
  }

  public List<BiaoKaListBean> getTiJiaoBiaoKaListBean(String renwumc, int iscommit) {
    QueryBuilder qb = this.queryBuilder();
    return qb .where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renwumc))
        .where(BiaoKaListBeanDao.Properties.IsCommit.eq(iscommit))
        .list();
  }

  public List<BiaoKaListBean> getZCBiaoKaListBean(String renwumc, String type) {
    QueryBuilder qb = this.queryBuilder();
    if (type.equals("yc")){
      return qb.where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renwumc))
              .where(BiaoKaListBeanDao.Properties.TYPE.eq("正常巡检"))
              .where(BiaoKaListBeanDao.Properties.IsSave.eq(1)).list();
    }else if (type.equals("wc")){
      return qb.where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renwumc))
        .where(BiaoKaListBeanDao.Properties.IsCommit.eq(0))
//                                .where(BiaoKaListBeanDao.Properties.IsUploadImage.eq(0))
        .where(BiaoKaListBeanDao.Properties.TYPE.eq("正常巡检"))
        .where(BiaoKaListBeanDao.Properties.IsSave.eq(0)).list();
    }else if (type.equals("ytj")){
      return qb.where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renwumc))
        .where(BiaoKaListBeanDao.Properties.TYPE.eq("正常巡检"))
        .where(BiaoKaListBeanDao.Properties.IsCommit.eq(1)).list();
    }else {
      return new ArrayList<>();
    }
  }

  public long getZCBiaoKaListBeanCount(String renWuMc, String type) {
    QueryBuilder qb = this.queryBuilder();
    if (type.equals("ytj")){
      return qb.where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renWuMc))
        .where(BiaoKaListBeanDao.Properties.TYPE.eq("正常巡检"))
        .where(BiaoKaListBeanDao.Properties.IsCommit.eq(1))
//                        .where(BiaoKaListBeanDao.Properties.IsUploadImage.eq(0))
        .count();
    }else if (type.equals("ybc")){
      return qb.where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renWuMc))
        .where(BiaoKaListBeanDao.Properties.TYPE.eq("正常巡检"))
        .where(BiaoKaListBeanDao.Properties.IsSave.eq(1)).count();
    }else if(type.equals("wc")){
      return qb.where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renWuMc))
        .where(BiaoKaListBeanDao.Properties.IsCommit.eq(0))
//                    .where(BiaoKaListBeanDao.Properties.IsUploadImage.eq(0))
        .where(BiaoKaListBeanDao.Properties.TYPE.eq("正常巡检"))
        .where(BiaoKaListBeanDao.Properties.IsSave.eq(0)).count();
    }else if(type.equals("null")) {
      return qb.where(BiaoKaListBeanDao.Properties.S_RENWUMC.eq(renWuMc))
        .count();
    }else {
      return 0;
    }
  }

  public List<BiaoKaListBean> getXGHZCBiaoKaListBean(String xiaogenhao, ArrayList<String> mRenwumclist, String type) {
    QueryBuilder qb = this.queryBuilder();
    if (type.equals("yc")){
      return qb.where(BiaoKaListBeanDao.Properties.S_CID.like(xiaogenhao + "%"))
        .where(BiaoKaListBeanDao.Properties.S_RENWUMC.in(mRenwumclist))
        .where(BiaoKaListBeanDao.Properties.TYPE.eq("正常巡检"))
        .where(BiaoKaListBeanDao.Properties.IsSave.eq(1)).list();
    }else if (type.equals("wc")){
      return qb.where(BiaoKaListBeanDao.Properties.S_CID.like(xiaogenhao + "%"))
        .where(BiaoKaListBeanDao.Properties.IsCommit.eq(0))
//                                    .where(BiaoKaListBeanDao.Properties.IsUploadImage.eq(0))
        .where(BiaoKaListBeanDao.Properties.S_RENWUMC.in(mRenwumclist))
        .where(BiaoKaListBeanDao.Properties.TYPE.eq("正常巡检"))
        .where(BiaoKaListBeanDao.Properties.IsSave.eq(0)).list();
    }else if (type.equals("ytj")){
      return qb.where(BiaoKaListBeanDao.Properties.S_CID.like(xiaogenhao + "%"))
        .where(BiaoKaListBeanDao.Properties.S_RENWUMC.in(mRenwumclist))
        .where(BiaoKaListBeanDao.Properties.TYPE.eq("正常巡检"))
        .where(BiaoKaListBeanDao.Properties.IsCommit.eq(1))
        .list();
    }else {
      return new ArrayList<>();
    }
  }

  public long getXGHZCBiaoKaListBeanCount(String xiaogenhao, ArrayList<String> mRenwumclist, String type) {
    QueryBuilder qb = this.queryBuilder();
    if (type.equals("ytj")){
      return qb.where(BiaoKaListBeanDao.Properties.S_CID.like(xiaogenhao + "%"))
        .where(BiaoKaListBeanDao.Properties.IsCommit.eq(1))
        .where(BiaoKaListBeanDao.Properties.TYPE.eq("正常巡检"))
        .where(BiaoKaListBeanDao.Properties.S_RENWUMC.in(mRenwumclist))
//                            .where(BiaoKaListBeanDao.Properties.IsUploadImage.eq(0))
        .count();
    }else if (type.equals("ybc")){
      return  qb.where(BiaoKaListBeanDao.Properties.S_CID.like(xiaogenhao + "%"))
        .where(BiaoKaListBeanDao.Properties.S_RENWUMC.in(mRenwumclist))
        .where(BiaoKaListBeanDao.Properties.TYPE.eq("正常巡检"))
        .where(BiaoKaListBeanDao.Properties.IsSave.eq(1)).count();
    }else if(type.equals("wc")){
      return qb.where(BiaoKaListBeanDao.Properties.S_CID.like(xiaogenhao + "%"))
        .where(BiaoKaListBeanDao.Properties.IsCommit.eq(0))
//                        .where(BiaoKaListBeanDao.Properties.IsUploadImage.eq(0))
        .where(BiaoKaListBeanDao.Properties.TYPE.eq("正常巡检"))
        .where(BiaoKaListBeanDao.Properties.S_RENWUMC.in(mRenwumclist))
        .where(BiaoKaListBeanDao.Properties.IsSave.eq(0)).count();
    }else {
      return 0;
    }
  }

  public boolean deleteZCBiaoKaListBean() {
    try {
      QueryBuilder qb = this.queryBuilder();
      qb.whereOr(BiaoKaListBeanDao.Properties.TYPE.eq("正常巡检"),
        BiaoKaListBeanDao.Properties.TYPE.isNull())
        .where(BiaoKaListBeanDao.Properties.IsCommit.eq(0))
        .buildDelete().executeDeleteWithoutDetachingEntities();
      return true;
    }catch (Exception e){
      return false;
    }
  }

  public boolean saveBiaoKaListBean(List<BiaoKaListBean> biaoKaList) {
      try {
        insertOrReplaceInTx(biaoKaList);
        return true;
      }catch (Exception e){
        return false;
      }
  }



  /**
     * Properties of entity BiaoKaListBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "ID", true, "_id");
        public final static Property S_RENWUID = new Property(1, String.class, "S_RENWUID", false, "S__RENWUID");
        public final static Property S_RENWUMC = new Property(2, String.class, "S_RENWUMC", false, "S__RENWUMC");
        public final static Property S_CID = new Property(3, String.class, "S_CID", false, "S__CID");
        public final static Property S_HUMING = new Property(4, String.class, "S_HUMING", false, "S__HUMING");
        public final static Property S_DIZHI = new Property(5, String.class, "S_DIZHI", false, "S__DIZHI");
        public final static Property BIAOKAZT = new Property(6, String.class, "BIAOKAZT", false, "BIAOKAZT");
        public final static Property XUNJIANZT = new Property(7, String.class, "XUNJIANZT", false, "XUNJIANZT");
        public final static Property XJLX = new Property(8, String.class, "XJLX", false, "XJLX");
        public final static Property Lat = new Property(9, double.class, "lat", false, "LAT");
        public final static Property Lon = new Property(10, double.class, "lon", false, "LON");
        public final static Property IsCommit = new Property(11, boolean.class, "isCommit", false, "IS_COMMIT");
        public final static Property IsSave = new Property(12, boolean.class, "isSave", false, "IS_SAVE");
        public final static Property IsUploadImage = new Property(13, boolean.class, "isUploadImage", false, "IS_UPLOAD_IMAGE");
        public final static Property ISYC = new Property(14, String.class, "ISYC", false, "ISYC");
        public final static Property GDDS_COUNT = new Property(15, String.class, "GDDS_COUNT", false, "GDDS__COUNT");
        public final static Property TYPE = new Property(16, String.class, "TYPE", false, "TYPE");
        public final static Property XUHAO = new Property(17, String.class, "XUHAO", false, "XUHAO");
    }


    public BiaoKaListBeanDao(DaoConfig config) {
        super(config);
    }
    
    public BiaoKaListBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BIAO_KA_LIST_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: ID
                "\"S__RENWUID\" TEXT," + // 1: S_RENWUID
                "\"S__RENWUMC\" TEXT," + // 2: S_RENWUMC
                "\"S__CID\" TEXT," + // 3: S_CID
                "\"S__HUMING\" TEXT," + // 4: S_HUMING
                "\"S__DIZHI\" TEXT," + // 5: S_DIZHI
                "\"BIAOKAZT\" TEXT," + // 6: BIAOKAZT
                "\"XUNJIANZT\" TEXT," + // 7: XUNJIANZT
                "\"XJLX\" TEXT," + // 8: XJLX
                "\"LAT\" REAL NOT NULL ," + // 9: lat
                "\"LON\" REAL NOT NULL ," + // 10: lon
                "\"IS_COMMIT\" INTEGER NOT NULL ," + // 11: isCommit
                "\"IS_SAVE\" INTEGER NOT NULL ," + // 12: isSave
                "\"IS_UPLOAD_IMAGE\" INTEGER NOT NULL ," + // 13: isUploadImage
                "\"ISYC\" TEXT," + // 14: ISYC
                "\"GDDS__COUNT\" TEXT," + // 15: GDDS_COUNT
                "\"TYPE\" TEXT," + // 16: TYPE
                "\"XUHAO\" TEXT);"); // 17: XUHAO
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BIAO_KA_LIST_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BiaoKaListBean entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
 
        String S_RENWUID = entity.getS_RENWUID();
        if (S_RENWUID != null) {
            stmt.bindString(2, S_RENWUID);
        }
 
        String S_RENWUMC = entity.getS_RENWUMC();
        if (S_RENWUMC != null) {
            stmt.bindString(3, S_RENWUMC);
        }
 
        String S_CID = entity.getS_CID();
        if (S_CID != null) {
            stmt.bindString(4, S_CID);
        }
 
        String S_HUMING = entity.getS_HUMING();
        if (S_HUMING != null) {
            stmt.bindString(5, S_HUMING);
        }
 
        String S_DIZHI = entity.getS_DIZHI();
        if (S_DIZHI != null) {
            stmt.bindString(6, S_DIZHI);
        }
 
        String BIAOKAZT = entity.getBIAOKAZT();
        if (BIAOKAZT != null) {
            stmt.bindString(7, BIAOKAZT);
        }
 
        String XUNJIANZT = entity.getXUNJIANZT();
        if (XUNJIANZT != null) {
            stmt.bindString(8, XUNJIANZT);
        }
 
        String XJLX = entity.getXJLX();
        if (XJLX != null) {
            stmt.bindString(9, XJLX);
        }
        stmt.bindDouble(10, entity.getLat());
        stmt.bindDouble(11, entity.getLon());
        stmt.bindLong(12, entity.getIsCommit() ? 1L: 0L);
        stmt.bindLong(13, entity.getIsSave() ? 1L: 0L);
        stmt.bindLong(14, entity.getIsUploadImage() ? 1L: 0L);
 
        String ISYC = entity.getISYC();
        if (ISYC != null) {
            stmt.bindString(15, ISYC);
        }
 
        String GDDS_COUNT = entity.getGDDS_COUNT();
        if (GDDS_COUNT != null) {
            stmt.bindString(16, GDDS_COUNT);
        }
 
        String TYPE = entity.getTYPE();
        if (TYPE != null) {
            stmt.bindString(17, TYPE);
        }

      String XUHAO = entity.getXUHAO();
      if (XUHAO != null) {
        stmt.bindString(18, XUHAO);
      }
    }



    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public BiaoKaListBean readEntity(Cursor cursor, int offset) {
        BiaoKaListBean entity = new BiaoKaListBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // S_RENWUID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // S_RENWUMC
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // S_CID
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // S_HUMING
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // S_DIZHI
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // BIAOKAZT
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // XUNJIANZT
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // XJLX
            cursor.getDouble(offset + 9), // lat
            cursor.getDouble(offset + 10), // lon
            cursor.getShort(offset + 11) != 0, // isCommit
            cursor.getShort(offset + 12) != 0, // isSave
            cursor.getShort(offset + 13) != 0, // isUploadImage
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // ISYC
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // GDDS_COUNT
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // TYPE
          cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17)
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BiaoKaListBean entity, int offset) {
        entity.setID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setS_RENWUID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setS_RENWUMC(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setS_CID(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setS_HUMING(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setS_DIZHI(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBIAOKAZT(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setXUNJIANZT(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setXJLX(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setLat(cursor.getDouble(offset + 9));
        entity.setLon(cursor.getDouble(offset + 10));
        entity.setIsCommit(cursor.getShort(offset + 11) != 0);
        entity.setIsSave(cursor.getShort(offset + 12) != 0);
        entity.setIsUploadImage(cursor.getShort(offset + 13) != 0);
        entity.setISYC(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setGDDS_COUNT(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setTYPE(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setXUHAO(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BiaoKaListBean entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BiaoKaListBean entity) {
        if(entity != null) {
            return entity.getID();
        } else {
            return null;
        }
    }


    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

  /**
   * 保存巡检表卡
   *
   * @param biaoKaBeans
   * @return boolean
   */
  public boolean insertXunjianBKlist(List<BiaoKaListBean> biaoKaBeans) {
    if (biaoKaBeans == null) {
      return false;
    }

    insertOrReplaceInTx(biaoKaBeans);
    return true;
  }
    
}
