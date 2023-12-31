package com.sh3h.dataprovider.greendaoDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sh3h.serverprovider.entity.BiaoKaWholeEntity;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.QueryBuilder;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "BIAO_KA_WHOLE_ENTITY".
*/
public class BiaoKaWholeEntityDao extends AbstractDao<BiaoKaWholeEntity, Long> {

    public static final String TABLENAME = "BIAO_KA_WHOLE_ENTITY";

    public boolean insertBKWholeEntity(BiaoKaWholeEntity newWholeEntity) {
        if (newWholeEntity == null) {
            return false;
        }

        try {
          insertOrReplace(newWholeEntity);
        }catch (Exception e){
          return false;
        }
        return true;
    }

    public List<BiaoKaWholeEntity> getBiaokaListBeans(long id) {

            QueryBuilder qb = this.queryBuilder();
            qb.where(BiaoKaWholeEntityDao.Properties.RENWUID.eq(id));
            return qb.list();

    }

    public List<BiaoKaWholeEntity> getisSaveBiaoKaWholeEntity() {
        QueryBuilder qb = this.queryBuilder();
        qb.where(BiaoKaWholeEntityDao.Properties.IsSave.eq(1));
        return qb.list();
    }

    public boolean insertBiaoKaWholeEntity(List<BiaoKaWholeEntity> biaoKaWholeEntities) {
        if (biaoKaWholeEntities == null) {
            return false;
        }
        try {
          insertOrReplaceInTx(biaoKaWholeEntities);
        }catch (Exception e){
          return false;
        }

        return true;
    }

    public List<BiaoKaWholeEntity> getBiaoKaWholeEntity(String renWuId, String xiaogenhao) {
        QueryBuilder qb = this.queryBuilder();
        return qb.where(BiaoKaWholeEntityDao.Properties.ID.eq((long) (renWuId + xiaogenhao).hashCode())).list();
    }

    public void saveBiaoKaWholeEntityDao(BiaoKaWholeEntity biaoKaWholeEntity) {
        insertOrReplace(biaoKaWholeEntity);
    }

  public List<BiaoKaWholeEntity> getBiaoKaWholeEntity2(String renwumc, int issave) {
    QueryBuilder qb = this.queryBuilder();
    return qb.where(BiaoKaWholeEntityDao.Properties.RENWUMC.eq(renwumc))
        .where(BiaoKaWholeEntityDao.Properties.IsSave.eq(issave))
        .list();
  }

  public List<BiaoKaWholeEntity> getBiaoKaWholeEntityDao(String renwumc, String denji) {
    QueryBuilder qb = this.queryBuilder();
    return qb.where(BiaoKaWholeEntityDao.Properties.RENWUMC.eq(renwumc))
      .where(BiaoKaWholeEntityDao.Properties.GONGDANDJ.notEq(denji))
      .list();
  }

  public int getYDJBiaoKaWholeEntityDao(String scid) {
      QueryBuilder qb = this.queryBuilder();
      return qb.where(BiaoKaWholeEntityDao.Properties.XIAOGENH.eq(scid))
        .where(BiaoKaWholeEntityDao.Properties.GONGDANDJ.notEq("0"))
        .list().size();
  }

  public List<BiaoKaWholeEntity> getBiaoKaWholeEntity(long id) {
    QueryBuilder qb = this.queryBuilder();
      return qb.where(BiaoKaWholeEntityDao.Properties.ID.eq(id)).list();
  }

  public List<BiaoKaWholeEntity> getXGHlikeBiaoKaWholeEntities(String renwumc, int type) {
    QueryBuilder qb = this.queryBuilder();
    return qb.where(BiaoKaWholeEntityDao.Properties.XIAOGENH.like(renwumc))
      .where(BiaoKaWholeEntityDao.Properties.IsSave.eq(type))
      .list();
  }

  public List<BiaoKaWholeEntity> getYTJBiaoKaWholeEntityDao(String renwumc, int type) {
    QueryBuilder qb = this.queryBuilder();
    return qb.where(BiaoKaWholeEntityDao.Properties.RENWUMC.eq(renwumc))
      .where(BiaoKaWholeEntityDao.Properties.IsCommit.eq(type))
      .list();
  }

  public List<BiaoKaWholeEntity> getYTJXGHBiaoKaWholeEntityDao(String renwumc, int type) {
    QueryBuilder qb = this.queryBuilder();
    return qb.where(BiaoKaWholeEntityDao.Properties.XIAOGENH.like(renwumc))
                .where(BiaoKaWholeEntityDao.Properties.IsCommit.eq(1))
                .list();
  }


  /**
     * Properties of entity BiaoKaWholeEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "ID", true, "_id");
        public final static Property DetailInfo = new Property(1, String.class, "detailInfo", false, "DETAIL_INFO");
        public final static Property RENWUID = new Property(2, String.class, "RENWUID", false, "RENWUID");
        public final static Property RENWUMC = new Property(3, String.class, "RENWUMC", false, "RENWUMC");
        public final static Property XIAOGENH = new Property(4, String.class, "XIAOGENH", false, "XIAOGENH");
        public final static Property XUNJIANCM = new Property(5, String.class, "XUNJIANCM", false, "XUNJIANCM");
        public final static Property TXM = new Property(6, String.class, "TXM", false, "TXM");
        public final static Property XUNJIANYL = new Property(7, String.class, "XUNJIANYL", false, "XUNJIANYL");
        public final static Property YLSM = new Property(8, String.class, "YLSM", false, "YLSM");
        public final static Property BEIZHU = new Property(9, String.class, "BEIZHU", false, "BEIZHU");
        public final static Property Images1 = new Property(10, String.class, "images1", false, "IMAGES1");
        public final static Property Images2 = new Property(11, String.class, "images2", false, "IMAGES2");
        public final static Property Images3 = new Property(12, String.class, "images3", false, "IMAGES3");
        public final static Property Images4 = new Property(13, String.class, "images4", false, "IMAGES4");
        public final static Property Images5 = new Property(14, String.class, "images5", false, "IMAGES5");
        public final static Property Images6 = new Property(15, String.class, "images6", false, "IMAGES6");
        public final static Property Images7 = new Property(16, String.class, "images7", false, "IMAGES7");
        public final static Property VedioUrl = new Property(17, String.class, "vedioUrl", false, "VEDIO_URL");
        public final static Property IsCommit = new Property(18, boolean.class, "isCommit", false, "IS_COMMIT");
        public final static Property IsSave = new Property(19, boolean.class, "isSave", false, "IS_SAVE");
        public final static Property IsUploadImage = new Property(20, boolean.class, "isUploadImage", false, "IS_UPLOAD_IMAGE");
        public final static Property Cbzt = new Property(21, String.class, "cbzt", false, "CBZT");
        public final static Property GONGDANDJ = new Property(22, String.class, "GONGDANDJ", false, "GONGDANDJ");
        public final static Property GONGDANDJBZ = new Property(23, String.class, "GONGDANDJBZ", false, "GONGDANDJBZ");
        public final static Property BIAOWU_GZYY = new Property(24, String.class, "BIAOWU_GZYY", false, "BIAOWU__GZYY");
        public final static Property MOKUAI_GZYY = new Property(25, String.class, "MOKUAI_GZYY", false, "MOKUAI__GZYY");
        public final static Property SFGHBP = new Property(26, String.class, "SFGHBP", false, "SFGHBP");
        public final static Property GZLB = new Property(27, String.class, "GZLB", false, "GZLB");
        public final static Property GpsTime = new Property(28, String.class, "gpsTime", false, "GPS_TIME");
        public final static Property Lat = new Property(29, String.class, "lat", false, "LAT");
        public final static Property Lon = new Property(30, String.class, "lon", false, "LON");
    }


    public BiaoKaWholeEntityDao(DaoConfig config) {
        super(config);
    }
    
    public BiaoKaWholeEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BIAO_KA_WHOLE_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: ID
                "\"DETAIL_INFO\" TEXT," + // 1: detailInfo
                "\"RENWUID\" TEXT," + // 2: RENWUID
                "\"RENWUMC\" TEXT," + // 3: RENWUMC
                "\"XIAOGENH\" TEXT," + // 4: XIAOGENH
                "\"XUNJIANCM\" TEXT," + // 5: XUNJIANCM
                "\"TXM\" TEXT," + // 6: TXM
                "\"XUNJIANYL\" TEXT," + // 7: XUNJIANYL
                "\"YLSM\" TEXT," + // 8: YLSM
                "\"BEIZHU\" TEXT," + // 9: BEIZHU
                "\"IMAGES1\" TEXT," + // 10: images1
                "\"IMAGES2\" TEXT," + // 11: images2
                "\"IMAGES3\" TEXT," + // 12: images3
                "\"IMAGES4\" TEXT," + // 13: images4
                "\"IMAGES5\" TEXT," + // 14: images5
                "\"IMAGES6\" TEXT," + // 15: images6
                "\"IMAGES7\" TEXT," + // 16: images7
                "\"VEDIO_URL\" TEXT," + // 17: vedioUrl
                "\"IS_COMMIT\" INTEGER NOT NULL ," + // 18: isCommit
                "\"IS_SAVE\" INTEGER NOT NULL ," + // 19: isSave
                "\"IS_UPLOAD_IMAGE\" INTEGER NOT NULL ," + // 20: isUploadImage
                "\"CBZT\" TEXT," + // 21: cbzt
                "\"GONGDANDJ\" TEXT," + // 22: GONGDANDJ
                "\"GONGDANDJBZ\" TEXT," + // 23: GONGDANDJBZ
                "\"BIAOWU__GZYY\" TEXT," + // 24: BIAOWU_GZYY
                "\"MOKUAI__GZYY\" TEXT," + // 25: MOKUAI_GZYY
                "\"SFGHBP\" TEXT," + // 26: SFGHBP
                "\"GZLB\" TEXT," + // 27: GZLB
                "\"GPS_TIME\" TEXT," + // 28: gpsTime
                "\"LAT\" TEXT," + // 29: lat
                "\"LON\" TEXT);"); // 30: lon
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BIAO_KA_WHOLE_ENTITY\"";
        db.execSQL(sql);
    }



    @Override
    protected final void bindValues(SQLiteStatement stmt, BiaoKaWholeEntity entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
 
        String detailInfo = entity.getDetailInfo();
        if (detailInfo != null) {
            stmt.bindString(2, detailInfo);
        }
 
        String RENWUID = entity.getRENWUID();
        if (RENWUID != null) {
            stmt.bindString(3, RENWUID);
        }
 
        String RENWUMC = entity.getRENWUMC();
        if (RENWUMC != null) {
            stmt.bindString(4, RENWUMC);
        }
 
        String XIAOGENH = entity.getXIAOGENH();
        if (XIAOGENH != null) {
            stmt.bindString(5, XIAOGENH);
        }
 
        String XUNJIANCM = entity.getXUNJIANCM();
        if (XUNJIANCM != null) {
            stmt.bindString(6, XUNJIANCM);
        }
 
        String TXM = entity.getTXM();
        if (TXM != null) {
            stmt.bindString(7, TXM);
        }
 
        String XUNJIANYL = entity.getXUNJIANYL();
        if (XUNJIANYL != null) {
            stmt.bindString(8, XUNJIANYL);
        }
 
        String YLSM = entity.getYLSM();
        if (YLSM != null) {
            stmt.bindString(9, YLSM);
        }
 
        String BEIZHU = entity.getBEIZHU();
        if (BEIZHU != null) {
            stmt.bindString(10, BEIZHU);
        }
 
        String images1 = entity.getImages1();
        if (images1 != null) {
            stmt.bindString(11, images1);
        }
 
        String images2 = entity.getImages2();
        if (images2 != null) {
            stmt.bindString(12, images2);
        }
 
        String images3 = entity.getImages3();
        if (images3 != null) {
            stmt.bindString(13, images3);
        }
 
        String images4 = entity.getImages4();
        if (images4 != null) {
            stmt.bindString(14, images4);
        }
 
        String images5 = entity.getImages5();
        if (images5 != null) {
            stmt.bindString(15, images5);
        }
 
        String images6 = entity.getImages6();
        if (images6 != null) {
            stmt.bindString(16, images6);
        }
 
        String images7 = entity.getImages7();
        if (images7 != null) {
            stmt.bindString(17, images7);
        }
 
        String vedioUrl = entity.getVedioUrl();
        if (vedioUrl != null) {
            stmt.bindString(18, vedioUrl);
        }
        stmt.bindLong(19, entity.getIsCommit() ? 1L: 0L);
        stmt.bindLong(20, entity.getIsSave() ? 1L: 0L);
        stmt.bindLong(21, entity.getIsUploadImage() ? 1L: 0L);
 
        String cbzt = entity.getCbzt();
        if (cbzt != null) {
            stmt.bindString(22, cbzt);
        }
 
        String GONGDANDJ = entity.getGONGDANDJ();
        if (GONGDANDJ != null) {
            stmt.bindString(23, GONGDANDJ);
        }
 
        String GONGDANDJBZ = entity.getGONGDANDJBZ();
        if (GONGDANDJBZ != null) {
            stmt.bindString(24, GONGDANDJBZ);
        }
 
        String BIAOWU_GZYY = entity.getBIAOWU_GZYY();
        if (BIAOWU_GZYY != null) {
            stmt.bindString(25, BIAOWU_GZYY);
        }
 
        String MOKUAI_GZYY = entity.getMOKUAI_GZYY();
        if (MOKUAI_GZYY != null) {
            stmt.bindString(26, MOKUAI_GZYY);
        }
 
        String SFGHBP = entity.getSFGHBP();
        if (SFGHBP != null) {
            stmt.bindString(27, SFGHBP);
        }
 
        String GZLB = entity.getGZLB();
        if (GZLB != null) {
            stmt.bindString(28, GZLB);
        }
 
        String gpsTime = entity.getGpsTime();
        if (gpsTime != null) {
            stmt.bindString(29, gpsTime);
        }
 
        String lat = entity.getLat();
        if (lat != null) {
            stmt.bindString(30, lat);
        }
 
        String lon = entity.getLon();
        if (lon != null) {
            stmt.bindString(31, lon);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public BiaoKaWholeEntity readEntity(Cursor cursor, int offset) {
        BiaoKaWholeEntity entity = new BiaoKaWholeEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // detailInfo
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // RENWUID
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // RENWUMC
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // XIAOGENH
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // XUNJIANCM
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // TXM
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // XUNJIANYL
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // YLSM
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // BEIZHU
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // images1
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // images2
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // images3
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // images4
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // images5
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // images6
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // images7
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // vedioUrl
            cursor.getShort(offset + 18) != 0, // isCommit
            cursor.getShort(offset + 19) != 0, // isSave
            cursor.getShort(offset + 20) != 0, // isUploadImage
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // cbzt
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // GONGDANDJ
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // GONGDANDJBZ
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // BIAOWU_GZYY
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // MOKUAI_GZYY
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // SFGHBP
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // GZLB
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // gpsTime
            cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29), // lat
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30) // lon
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BiaoKaWholeEntity entity, int offset) {
        entity.setID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDetailInfo(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setRENWUID(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRENWUMC(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setXIAOGENH(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setXUNJIANCM(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTXM(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setXUNJIANYL(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setYLSM(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setBEIZHU(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setImages1(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setImages2(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setImages3(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setImages4(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setImages5(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setImages6(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setImages7(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setVedioUrl(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setIsCommit(cursor.getShort(offset + 18) != 0);
        entity.setIsSave(cursor.getShort(offset + 19) != 0);
        entity.setIsUploadImage(cursor.getShort(offset + 20) != 0);
        entity.setCbzt(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setGONGDANDJ(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setGONGDANDJBZ(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setBIAOWU_GZYY(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setMOKUAI_GZYY(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setSFGHBP(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setGZLB(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setGpsTime(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
        entity.setLat(cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29));
        entity.setLon(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BiaoKaWholeEntity entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BiaoKaWholeEntity entity) {
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
    
}
