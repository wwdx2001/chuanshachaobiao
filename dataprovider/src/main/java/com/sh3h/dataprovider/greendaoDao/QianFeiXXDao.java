package com.sh3h.dataprovider.greendaoDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sh3h.dataprovider.greendaoEntity.QianFeiXX;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table ZW_QianFeiXX.
 */
public class QianFeiXXDao extends AbstractDao<QianFeiXX, Void> {

    public static final String TABLENAME = "ZW_QianFeiXX";

    /**
     * Properties of entity QianFeiXX.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property S_CH = new Property(0, String.class, "S_CH", false, "S_CH");
        public final static Property S_CID = new Property(1, String.class, "S_CID", false, "S_CID");
        public final static Property I_CHAOBIAON = new Property(2, int.class, "I_CHAOBIAON", false, "I_CHAOBIAON");
        public final static Property I_CHAOBIAOY = new Property(3, int.class, "I_CHAOBIAOY", false, "I_CHAOBIAOY");
        public final static Property I_ZhangWuNY = new Property(4, int.class, "I_ZhangWuNY", false, "I_ZHANGWUNY");
        public final static Property D_ChaoBiaoRQ = new Property(5, long.class, "D_ChaoBiaoRQ", false, "D_CHAOBIAORQ");
        public final static Property I_CHAOCI = new Property(6, int.class, "I_CHAOCI", false, "I_CHAOCI");
        public final static Property I_KAIZHANGSL = new Property(7, int.class, "I_KAIZHANGSL", false, "I_KAIZHANGSL");
        public final static Property N_JE = new Property(8, double.class, "N_JE", false, "N_JE");
        public final static Property I_FEEID = new Property(9, int.class, "I_FEEID", false, "I_FEEID");
        public final static Property N_YINGSHOUWYJ = new Property(10, double.class, "N_YINGSHOUWYJ", false, "N_YINGSHOUWYJ");
        public final static Property N_SHUIFEI = new Property(11, double.class, "N_SHUIFEI", false, "N_SHUIFEI");
        public final static Property N_PAISHUIF = new Property(12, double.class, "N_PAISHUIF", false, "N_PAISHUIF");
    }

    ;


    public QianFeiXXDao(DaoConfig config) {
        super(config);
    }

    public QianFeiXXDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'ZW_QianFeiXX' (" + //
                "'S_CH' TEXT," + // 0: S_CH
                "'S_CID' TEXT NOT NULL ," + // 1: S_CID
                "'I_CHAOBIAON' INTEGER NOT NULL ," + // 2: I_CHAOBIAON
                "'I_CHAOBIAOY' INTEGER NOT NULL ," + // 3: I_CHAOBIAOY
                "'I_ZHANGWUNY' INTEGER," + // 4: I_ZhangWuNY
                "'D_CHAOBIAORQ' INTEGER," + // 5: D_ChaoBiaoRQ
                "'I_CHAOCI' INTEGER," + // 6: I_CHAOCI
                "'I_KAIZHANGSL' INTEGER," + // 7: I_KAIZHANGSL
                "'N_JE' REAL NOT NULL ," + // 8: N_JE
                "'I_FEEID' INTEGER," + // 9: I_FEEID
                "'N_YINGSHOUWYJ' REAL," +
                "'N_SHUIFEI' REAL NOT NULL ," +
                "'N_PAISHUIF' REAL NOT NULL);"); // 10: N_YINGSHOUWYJ
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ZW_QianFeiXX'";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, QianFeiXX entity) {
        stmt.clearBindings();

        String S_CH = entity.getS_CH();
        if (S_CH != null) {
            stmt.bindString(1, S_CH);
        }
        stmt.bindString(2, entity.getS_CID());
        stmt.bindLong(3, entity.getI_CHAOBIAON());
        stmt.bindLong(4, entity.getI_CHAOBIAOY());

        int I_ZhangWuNY = entity.getI_ZhangWuNY();
        //if (I_ZhangWuNY != null) {
        stmt.bindLong(5, I_ZhangWuNY);
        //}

        long D_ChaoBiaoRQ = entity.getD_ChaoBiaoRQ();
        //if (D_ChaoBiaoRQ != null) {
        stmt.bindLong(6, D_ChaoBiaoRQ);
        //}

        int I_CHAOCI = entity.getI_CHAOCI();
        //if (I_CHAOCI != null) {
        stmt.bindLong(7, I_CHAOCI);
        //}

        int I_KAIZHANGSL = entity.getI_KAIZHANGSL();
        //if (I_KAIZHANGSL != null) {
        stmt.bindLong(8, I_KAIZHANGSL);
        //}
        stmt.bindDouble(9, entity.getN_JE());

        int I_FEEID = entity.getI_FEEID();
        //if (I_FEEID != null) {
        stmt.bindLong(10, I_FEEID);
        //}

        double N_YINGSHOUWYJ = entity.getN_YINGSHOUWYJ();
        //if (N_YINGSHOUWYJ != null) {
        stmt.bindDouble(11, N_YINGSHOUWYJ);
        //}


        double N_SHUIFEI = entity.getN_SHUIFEI();
        //if (N_YINGSHOUWYJ != null) {
        stmt.bindDouble(12, N_SHUIFEI);

        double N_PAISHUIF = entity.getN_PAISHUIF();
        //if (N_YINGSHOUWYJ != null) {
        stmt.bindDouble(13, N_PAISHUIF);
    }

    /**
     * @inheritdoc
     */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }

    /**
     * @inheritdoc
     */
    @Override
    public QianFeiXX readEntity(Cursor cursor, int offset) {
        QianFeiXX entity = new QianFeiXX( //
                cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // S_CH
                cursor.getString(offset + 1), // S_CID
                cursor.getInt(offset + 2), // I_CHAOBIAON
                cursor.getInt(offset + 3), // I_CHAOBIAOY
                cursor.isNull(offset + 4) ? 0 : cursor.getInt(offset + 4), // I_ZhangWuNY
                cursor.isNull(offset + 5) ? 0 : cursor.getInt(offset + 5), // D_ChaoBiaoRQ
                cursor.isNull(offset + 6) ? 0 : cursor.getInt(offset + 6), // I_CHAOCI
                cursor.isNull(offset + 7) ? 0 : cursor.getInt(offset + 7), // I_KAIZHANGSL
                cursor.getDouble(offset + 8), // N_JE
                cursor.isNull(offset + 9) ? 0 : cursor.getInt(offset + 9), // I_FEEID
                cursor.isNull(offset + 10) ? 0 : cursor.getDouble(offset + 10), // N_YINGSHOUWYJ
                cursor.isNull(offset + 11) ? 0 : cursor.getDouble(offset + 11),
                cursor.isNull(offset + 12) ? 0 : cursor.getDouble(offset + 12)
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, QianFeiXX entity, int offset) {
        entity.setS_CH(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setS_CID(cursor.getString(offset + 1));
        entity.setI_CHAOBIAON(cursor.getInt(offset + 2));
        entity.setI_CHAOBIAOY(cursor.getInt(offset + 3));
        entity.setI_ZhangWuNY(cursor.isNull(offset + 4) ? 0 : cursor.getInt(offset + 4));
        entity.setD_ChaoBiaoRQ(cursor.isNull(offset + 5) ? 0 : cursor.getInt(offset + 5));
        entity.setI_CHAOCI(cursor.isNull(offset + 6) ? 0 : cursor.getInt(offset + 6));
        entity.setI_KAIZHANGSL(cursor.isNull(offset + 7) ? 0 : cursor.getInt(offset + 7));
        entity.setN_JE(cursor.getDouble(offset + 8));
        entity.setI_FEEID(cursor.isNull(offset + 9) ? 0 : cursor.getInt(offset + 9));
        entity.setN_YINGSHOUWYJ(cursor.isNull(offset + 10) ? 0 : cursor.getDouble(offset + 10));
        entity.setN_SHUIFEI(cursor.isNull(offset + 11) ? 0 : cursor.getDouble(offset + 11));
        entity.setN_PAISHUIF(cursor.isNull(offset + 12) ? 0 : cursor.getDouble(offset + 12));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Void updateKeyAfterInsert(QianFeiXX entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Void getKey(QianFeiXX entity) {
        return null;
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

    /**
     * 根据任务编号删除不存在renWuBHAll中的欠费信息
     */
    public void delectQianFeiXX(String cidAll) {
        String[] str = cidAll.split(",");
        QueryBuilder<QianFeiXX> qb = this.queryBuilder();
        DeleteQuery<QianFeiXX> bd = qb.where(Properties.S_CID.in((Object[]) str)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }

    public boolean deleteQianFeiXXs(String ch) {
        if (ch == null) {
            return false;
        }

        QueryBuilder<QianFeiXX> qb = this.queryBuilder();
        DeleteQuery<QianFeiXX> bd = qb.where(Properties.S_CH.in(ch)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
        return true;
    }

    public boolean deleteQianFeiXXs(List<String> chs) {
        if (chs == null) {
            return false;
        }

        QueryBuilder<QianFeiXX> qb = this.queryBuilder();
        DeleteQuery<QianFeiXX> bd = qb.where(Properties.S_CH.notIn(chs)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
        return true;
    }

    /**
     * 删除本地换表记录表数据
     */
    public boolean clear(String cId) {
        String[] str = cId.split(",");
        QueryBuilder<QianFeiXX> qb = this.queryBuilder();
        qb.where(Properties.S_CID.eq(cId));
        DeleteQuery<QianFeiXX> bd = qb.where(Properties.S_CID.in((Object[]) str)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
        if (qb.list() != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean clear(List<String> cids) {
        if (cids == null) {
            return false;
        }

        int size = cids.size();
        if (size > DataProviderImpl.MAX_IN_SIZE) {
            int number = size / DataProviderImpl.MAX_IN_SIZE + 1;
            for (int i = 0; i < number; i++) {
                List<String> subList = cids.subList(i * DataProviderImpl.MAX_IN_SIZE,
                        i == number - 1 ? cids.size() : (i + 1) * DataProviderImpl.MAX_IN_SIZE);
                queryBuilder().where(Properties.S_CID.in(subList))
                        .buildDelete().executeDeleteWithoutDetachingEntities();
            }

        } else {
            queryBuilder().where(Properties.S_CID.in(cids))
                    .buildDelete().executeDeleteWithoutDetachingEntities();
        }

        return true;
    }

    /**
     * 插入数据
     */
    public boolean insertData(QianFeiXX qianFeiXX) {
        if (qianFeiXX == null) {
            return false;
        }

        QueryBuilder<QianFeiXX> qb = this.queryBuilder();
        qb.where(Properties.S_CH.eq(qianFeiXX.getS_CH()),
                Properties.S_CID.eq(qianFeiXX.getS_CID()),
                Properties.I_CHAOBIAON.eq(qianFeiXX.getI_CHAOBIAON()),
                Properties.I_CHAOBIAOY.eq(qianFeiXX.getI_CHAOBIAOY()));
        List<QianFeiXX> list = qb.list();
        if ((list != null) && (list.size() > 0)) {
            return true;
        }

        if (this.insertOrReplace(qianFeiXX) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean insertQianFeiXXs(List<QianFeiXX> qianFeiXXList) {
        if (qianFeiXXList == null) {
            return false;
        }

        insertOrReplaceInTx(qianFeiXXList);
        return true;
    }

    /**
     * 解析QianFeiXX实体数据
     *
     * @param cId 本地数据库QianFeiXX实体结果集
     * @return QianFeiXX 实体
     */
    public List<QianFeiXX> getList(String cId) {
        if (cId == null) {
            return null;
        }

        QueryBuilder<QianFeiXX> qb = this.queryBuilder();
        qb.where(Properties.S_CID.eq(cId));
        qb.orderDesc(Properties.I_ZhangWuNY, Properties.I_CHAOCI);
        return qb.list();
    }

}
