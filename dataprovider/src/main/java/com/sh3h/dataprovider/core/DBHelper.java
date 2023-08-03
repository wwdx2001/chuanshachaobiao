/**
 * @author qiweiwei 2013-6-25 下午1:37:29
 *
 */
package com.sh3h.mrdataprovider.core;

import java.io.File;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * DBHelper
 */
public class DBHelper {

	private static SQLiteDatabase _db = null;

	public static SQLiteDatabase openDateBase(Context context) {
		File sdDir = Environment.getExternalStorageDirectory();
		File dataDir = new File(sdDir, "sh3h/meterreading/data");
		File dataFile = new File(dataDir, "main.cbj");
		//File dataFile = new File(dataDir, "DzgasPDA.db");
		_db = openDateBase(context, dataFile.getPath());
		return _db;
	}

	public static SQLiteDatabase getSQLiteDatabase() {
		return _db;
	}

	/**
	 * 打开本地数据库文件
	 *
	 * @param context
	 *            上下文
	 * @return SQLiteDatabase实例
	 */
	private static SQLiteDatabase openDateBase(Context context, String dbPath) {

		SQLiteDatabase db = context.openOrCreateDatabase(dbPath,
				Context.MODE_PRIVATE, null);
		// insertChaoBiaoSJ(db);

		return db;
	}

	public static void closeDatabase() {
		if (_db != null) {
			_db.close();
			_db = null;
		}
	}
	//
	// public static void insertChaoBiaoSJ(SQLiteDatabase db) {
	//
	// String[] ch = new String[] { "Nd101", "Nd102", "Nd103", "Nd104",
	// "Nd105" };
	// String[] cid = new String[] { "user112", "user113", "user114",
	// "user115", "user116" };
	// // 0未抄1已抄2开账3延迟
	// int[] chaoBiaoBZ = new int[] { 0, 1, 2, 3 };
	// int[] shangCiCM = new int[] { 0, 1, 2, 3, 4, 5 };
	// int[] chaoShuiL = new int[] { 20, 50, 70, 90, 110, 130, 150, 170, 190,
	// 210 };
	//
	// Random rand = new Random();
	// for (int i = 0; i <= 20; i++) {
	// ChaoBiaoSJ sj = new ChaoBiaoSJ();
	// sj.setCH(ch[rand.nextInt(ch.length)]);
	// sj.setCeNeiXH(i);
	// sj.setST(cid[rand.nextInt(cid.length)]);
	// sj.setCID(cid[rand.nextInt(cid.length)]);
	// sj.setShangciCM(shangCiCM[rand.nextInt(shangCiCM.length)]);
	// sj.setChaoJIanSL(chaoShuiL[rand.nextInt(chaoShuiL.length)]);
	// sj.setZhuangTaiBM(rand.nextInt(30));
	// sj.setBenCiCM(shangCiCM[rand.nextInt(shangCiCM.length)]);
	// sj.setChaoBiaoBZ(chaoBiaoBZ[rand.nextInt(chaoBiaoBZ.length)]);
	// sj.setJianHao(ch[rand.nextInt(chaoBiaoBZ.length)]);
	// insetChaoBiaoSj(db, sj);
	// }
	//
	// }
	//
	// public static boolean insetChaoBiaoSj(SQLiteDatabase db, ChaoBiaoSJ cb) {
	//
	// ContentValues values = new ContentValues();
	// // values.put(ChaoBiaoSJColumns.ID, cb.getId());
	// values.put(ChaoBiaoSJColumns.CH, cb.getCH());
	// values.put(ChaoBiaoSJColumns.CENETXH, cb.getCeNeiXH());
	// values.put(ChaoBiaoSJColumns.CID, cb.getCID());
	// values.put(ChaoBiaoSJColumns.ST, cb.getST());
	// values.put(ChaoBiaoSJColumns.CHAOBIAON, cb.getChaoBiaoN());
	// values.put(ChaoBiaoSJColumns.CHAOBIAOYUE, cb.getChaoBiaoYue());
	// values.put(ChaoBiaoSJColumns.CHAOCI, cb.getChaoCi());
	// long chaoBiaoRQ = 0;
	// if (cb.getChaoBiaoRQ() != null) {
	// chaoBiaoRQ = cb.getChaoBiaoRQ().getTime();
	// }
	// values.put(ChaoBiaoSJColumns.CHAOBIAORQ, chaoBiaoRQ);
	// values.put(ChaoBiaoSJColumns.SHANGCICM, cb.getShangciCM());
	// values.put(ChaoBiaoSJColumns.BENCICM, cb.getBenCiCM());
	// values.put(ChaoBiaoSJColumns.CHAOJIANSL, cb.getChaoJIanSL());
	// values.put(ChaoBiaoSJColumns.ZHUANGTAIBM, cb.getZhuangTaiBM());
	// values.put(ChaoBiaoSJColumns.ZHUANGTAIMC, cb.getZhuangTaiMC());
	// long shangciCBRQ = 0;
	// if (cb.getShangciCBRQ() != null) {
	// shangciCBRQ = cb.getShangciCBRQ().getTime();
	// }
	// values.put(ChaoBiaoSJColumns.SHANGCICBRQ, shangciCBRQ);
	// values.put(ChaoBiaoSJColumns.SHANGCIZTBM, cb.getShangCiZTBM());
	// values.put(ChaoBiaoSJColumns.SHANGCIZTMC, cb.getShangCiZTMC());
	// values.put(ChaoBiaoSJColumns.SHANGCICJSL, cb.getShangCiCJSL());
	// values.put(ChaoBiaoSJColumns.SHANGCIZTLXS, cb.getShangCiZTLXS());
	// values.put(ChaoBiaoSJColumns.PINGJUN1, cb.getPingJunL1());
	// values.put(ChaoBiaoSJColumns.PINGJUN2, cb.getPingJunL2());
	// values.put(ChaoBiaoSJColumns.PINGJUN3, cb.getPingJunL3());
	// values.put(ChaoBiaoSJColumns.JE, cb.getJE());
	// values.put(ChaoBiaoSJColumns.ZONGBIAOCID, cb.getZongBiaoCID());
	// values.put(ChaoBiaoSJColumns.CHAOBIAOYUAN, cb.getChaoBiaoYue());
	// values.put(ChaoBiaoSJColumns.CHAOBIAOBIAOZHI, cb.getChaoBiaoBZ());
	// values.put(ChaoBiaoSJColumns.JIUBIAOCM, cb.getJiuBiaoCM());
	// values.put(ChaoBiaoSJColumns.XINBIAODM, cb.getXinBiaoDM());
	// long huanBiaoRQ = 0;
	// if (cb.getHuanBiaoRQ() != null) {
	// huanBiaoRQ = cb.getHuanBiaoRQ().getTime();
	// }
	// values.put(ChaoBiaoSJColumns.HUANBIAORQ, huanBiaoRQ);
	// values.put(ChaoBiaoSJColumns.FANGSHIBM, cb.getFangShiBM());
	// values.put(ChaoBiaoSJColumns.LIANGGAOLDYYBM, cb.getLiangGaoLDYYBM());
	// values.put(ChaoBiaoSJColumns.ZHUANGTAILXS, cb.getZhuangTaiLXS());
	// values.put(ChaoBiaoSJColumns.SHUIBIAOBL, cb.getShuiBiaoBL());
	// values.put(ChaoBiaoSJColumns.YONGSHUIZKL, cb.getYongShuiZKL());
	// values.put(ChaoBiaoSJColumns.PAISHUIZKL, cb.getPaiShuiZKL());
	// values.put(ChaoBiaoSJColumns.TIAOJIAH, cb.getTiaoJiaH());
	// values.put(ChaoBiaoSJColumns.JIANHAO, cb.getJianHao());
	// long xiaZaiSJ = 0;
	// if (cb.getXiaZaiSJ() != null) {
	// xiaZaiSJ = cb.getXiaZaiSJ().getTime();
	// }
	// values.put(ChaoBiaoSJColumns.XIAZAISJ, xiaZaiSJ);
	// values.put(ChaoBiaoSJColumns.LINGYONGSLSM, cb.getLingYongSLSM());
	// values.put(ChaoBiaoSJColumns.LIANGGAOSL, cb.getLiangGaoSL());
	// values.put(ChaoBiaoSJColumns.LIANGDISL, cb.getLiangDiSL());
	// values.put(ChaoBiaoSJColumns.X1, cb.getX1());
	// values.put(ChaoBiaoSJColumns.Y1, cb.getY1());
	// values.put(ChaoBiaoSJColumns.X, cb.getX());
	// values.put(ChaoBiaoSJColumns.Y, cb.getY());
	// values.put(ChaoBiaoSJColumns.CHAOBIAOBEIZHU, cb.getChaoBiaoBeiZhu());
	//
	// long id = db.insert(Tables.CB_CHAOBIAOSJ, ChaoBiaoSJColumns.ID, values);
	// return id > 0;
	// }

}
