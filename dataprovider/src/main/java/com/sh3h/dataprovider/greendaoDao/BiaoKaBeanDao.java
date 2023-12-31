package com.sh3h.dataprovider.greendaoDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sh3h.serverprovider.entity.BiaoKaBean;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "BIAO_KA_BEAN".
*/
public class BiaoKaBeanDao extends AbstractDao<BiaoKaBean, Long> {

    public static final String TABLENAME = "BIAO_KA_BEAN";

  public List<BiaoKaBean> getXunJianBK(String xiaogenghao) {
    QueryBuilder qb = this.queryBuilder();
    qb.where(Properties.XIAOGENH.eq(xiaogenghao));
    return qb.list();
  }

    public List<BiaoKaBean> getBiaoKaBean(String xiaogenhao) {
        QueryBuilder qb = this.queryBuilder();
        return qb.where(BiaoKaBeanDao.Properties.XIAOGENH.eq(xiaogenhao)).list();
    }

  public boolean savebiaoKaBean(List<BiaoKaBean> biaoKaBeans) {
    try {
      insertOrReplaceInTx(biaoKaBeans);
      return true;
    }catch (Exception e){
      return false;
    }

  }


  /**
     * Properties of entity BiaoKaBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "ID", true, "_id");
        public final static Property S_STATE = new Property(1, String.class, "S_STATE", false, "S__STATE");
        public final static Property XIAOGENH = new Property(2, String.class, "XIAOGENH", false, "XIAOGENH");
        public final static Property S_ST = new Property(3, String.class, "S_ST", false, "S__ST");
        public final static Property HUMING = new Property(4, String.class, "HUMING", false, "HUMING");
        public final static Property DIZHI = new Property(5, String.class, "DIZHI", false, "DIZHI");
        public final static Property BIAOKAZT = new Property(6, String.class, "BIAOKAZT", false, "BIAOKAZT");
        public final static Property XUNJIANZT = new Property(7, String.class, "XUNJIANZT", false, "XUNJIANZT");
        public final static Property YONGSHUIXZ = new Property(8, String.class, "YONGSHUIXZ", false, "YONGSHUIXZ");
        public final static Property BIAOHAO = new Property(9, String.class, "BIAOHAO", false, "BIAOHAO");
        public final static Property ISNB = new Property(10, int.class, "ISNB", false, "ISNB");
        public final static Property SHANGCICM = new Property(11, String.class, "SHANGCICM", false, "SHANGCICM");
        public final static Property QIANSANCIPJ = new Property(12, int.class, "QIANSANCIPJ", false, "QIANSANCIPJ");
        public final static Property CHAOBIAORQ = new Property(13, String.class, "CHAOBIAORQ", false, "CHAOBIAORQ");
        public final static Property CHAOBIAOYL = new Property(14, int.class, "CHAOBIAOYL", false, "CHAOBIAOYL");
        public final static Property S_X = new Property(15, String.class, "S_X", false, "S__X");
        public final static Property S_Y = new Property(16, String.class, "S_Y", false, "S__Y");
        public final static Property CJMC = new Property(17, String.class, "CJMC", false, "CJMC");
        public final static Property BXMC = new Property(18, String.class, "BXMC", false, "BXMC");
        public final static Property XJLX = new Property(19, String.class, "XJLX", false, "XJLX");
        public final static Property TDYY = new Property(20, String.class, "TDYY", false, "TDYY");
        public final static Property TDBZ = new Property(21, String.class, "TDBZ", false, "TDBZ");
        public final static Property TDSJ = new Property(22, String.class, "TDSJ", false, "TDSJ");
        public final static Property TDR = new Property(23, String.class, "TDR", false, "TDR");
        public final static Property ISYC = new Property(24, String.class, "ISYC", false, "ISYC");
        public final static Property GDDS_COUNT = new Property(25, String.class, "GDDS_COUNT", false, "GDDS__COUNT");
        public final static Property I_SHUIBIAOZL = new Property(26, String.class, "I_SHUIBIAOZL", false, "I__SHUIBIAOZL");
    }


    public BiaoKaBeanDao(DaoConfig config) {
        super(config);
    }
    
    public BiaoKaBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BIAO_KA_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: ID
                "\"S__STATE\" TEXT," + // 1: S_STATE
                "\"XIAOGENH\" TEXT," + // 2: XIAOGENH
                "\"S__ST\" TEXT," + // 3: S_ST
                "\"HUMING\" TEXT," + // 4: HUMING
                "\"DIZHI\" TEXT," + // 5: DIZHI
                "\"BIAOKAZT\" TEXT," + // 6: BIAOKAZT
                "\"XUNJIANZT\" TEXT," + // 7: XUNJIANZT
                "\"YONGSHUIXZ\" TEXT," + // 8: YONGSHUIXZ
                "\"BIAOHAO\" TEXT," + // 9: BIAOHAO
                "\"ISNB\" TEXT ," + // 10: ISNB
                "\"SHANGCICM\" TEXT," + // 11: SHANGCICM
                "\"QIANSANCIPJ\" TEXT ," + // 12: QIANSANCIPJ
                "\"CHAOBIAORQ\" TEXT," + // 13: CHAOBIAORQ
                "\"CHAOBIAOYL\" TEXT ," + // 14: CHAOBIAOYL
                "\"S__X\" TEXT," + // 15: S_X
                "\"S__Y\" TEXT," + // 16: S_Y
                "\"CJMC\" TEXT," + // 17: CJMC
                "\"BXMC\" TEXT," + // 18: BXMC
                "\"XJLX\" TEXT," + // 19: XJLX
                "\"TDYY\" TEXT," + // 20: TDYY
                "\"TDBZ\" TEXT," + // 21: TDBZ
                "\"TDSJ\" TEXT," + // 22: TDSJ
                "\"TDR\" TEXT," + // 23: TDR
                "\"ISYC\" TEXT," + // 24: ISYC
                "\"GDDS__COUNT\" TEXT," + // 25: GDDS_COUNT
                "\"I__SHUIBIAOZL\" TEXT);"); // 26: I_SHUIBIAOZL
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BIAO_KA_BEAN\"";
        db.execSQL(sql);
    }



    @Override
    protected final void bindValues(SQLiteStatement stmt, BiaoKaBean entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
 
        String S_STATE = entity.getS_STATE();
        if (S_STATE != null) {
            stmt.bindString(2, S_STATE);
        }
 
        String XIAOGENH = entity.getXIAOGENH();
        if (XIAOGENH != null) {
            stmt.bindString(3, XIAOGENH);
        }
 
        String S_ST = entity.getS_ST();
        if (S_ST != null) {
            stmt.bindString(4, S_ST);
        }
 
        String HUMING = entity.getHUMING();
        if (HUMING != null) {
            stmt.bindString(5, HUMING);
        }
 
        String DIZHI = entity.getDIZHI();
        if (DIZHI != null) {
            stmt.bindString(6, DIZHI);
        }
 
        String BIAOKAZT = entity.getBIAOKAZT();
        if (BIAOKAZT != null) {
            stmt.bindString(7, BIAOKAZT);
        }
 
        String XUNJIANZT = entity.getXUNJIANZT();
        if (XUNJIANZT != null) {
            stmt.bindString(8, XUNJIANZT);
        }
 
        String YONGSHUIXZ = entity.getYONGSHUIXZ();
        if (YONGSHUIXZ != null) {
            stmt.bindString(9, YONGSHUIXZ);
        }
 
        String BIAOHAO = entity.getBIAOHAO();
        if (BIAOHAO != null) {
            stmt.bindString(10, BIAOHAO);
        }
        stmt.bindString(11, entity.getISNB());
 
        String SHANGCICM = entity.getSHANGCICM();
        if (SHANGCICM != null) {
            stmt.bindString(12, SHANGCICM);
        }
        stmt.bindString(13, entity.getQIANSANCIPJ());
 
        String CHAOBIAORQ = entity.getCHAOBIAORQ();
        if (CHAOBIAORQ != null) {
            stmt.bindString(14, CHAOBIAORQ);
        }
        stmt.bindString(15, entity.getCHAOBIAOYL());
 
        String S_X = entity.getS_X();
        if (S_X != null) {
            stmt.bindString(16, S_X);
        }
 
        String S_Y = entity.getS_Y();
        if (S_Y != null) {
            stmt.bindString(17, S_Y);
        }
 
        String CJMC = entity.getCJMC();
        if (CJMC != null) {
            stmt.bindString(18, CJMC);
        }
 
        String BXMC = entity.getBXMC();
        if (BXMC != null) {
            stmt.bindString(19, BXMC);
        }
 
        String XJLX = entity.getXJLX();
        if (XJLX != null) {
            stmt.bindString(20, XJLX);
        }
 
        String TDYY = entity.getTDYY();
        if (TDYY != null) {
            stmt.bindString(21, TDYY);
        }
 
        String TDBZ = entity.getTDBZ();
        if (TDBZ != null) {
            stmt.bindString(22, TDBZ);
        }
 
        String TDSJ = entity.getTDSJ();
        if (TDSJ != null) {
            stmt.bindString(23, TDSJ);
        }
 
        String TDR = entity.getTDR();
        if (TDR != null) {
            stmt.bindString(24, TDR);
        }
 
        String ISYC = entity.getISYC();
        if (ISYC != null) {
            stmt.bindString(25, ISYC);
        }
 
        String GDDS_COUNT = entity.getGDDS_COUNT();
        if (GDDS_COUNT != null) {
            stmt.bindString(26, GDDS_COUNT);
        }
 
        String I_SHUIBIAOZL = entity.getI_SHUIBIAOZL();
        if (I_SHUIBIAOZL != null) {
            stmt.bindString(27, I_SHUIBIAOZL);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public BiaoKaBean readEntity(Cursor cursor, int offset) {
        BiaoKaBean entity = new BiaoKaBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // S_STATE
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // XIAOGENH
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // S_ST
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // HUMING
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // DIZHI
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // BIAOKAZT
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // XUNJIANZT
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // YONGSHUIXZ
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // BIAOHAO
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // ISNB
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // SHANGCICM
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // QIANSANCIPJ
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // CHAOBIAORQ
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // CHAOBIAOYL
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // S_X
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // S_Y
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // CJMC
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // BXMC
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // XJLX
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // TDYY
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // TDBZ
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // TDSJ
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // TDR
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // ISYC
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // GDDS_COUNT
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26) // I_SHUIBIAOZL
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BiaoKaBean entity, int offset) {
        entity.setID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setS_STATE(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setXIAOGENH(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setS_ST(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setHUMING(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDIZHI(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBIAOKAZT(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setXUNJIANZT(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setYONGSHUIXZ(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setBIAOHAO(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setISNB(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setSHANGCICM(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setQIANSANCIPJ(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setCHAOBIAORQ(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setCHAOBIAOYL(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setS_X(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setS_Y(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setCJMC(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setBXMC(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setXJLX(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setTDYY(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setTDBZ(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setTDSJ(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setTDR(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setISYC(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setGDDS_COUNT(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setI_SHUIBIAOZL(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BiaoKaBean entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BiaoKaBean entity) {
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
  public boolean insertXunJianBK(List<BiaoKaBean> biaoKaBeans) {
    if (biaoKaBeans == null) {
      return false;
    }

    insertOrReplaceInTx(biaoKaBeans);
    return true;
  }
    
}
