package com.sh3h.dataprovider.greendaoDao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sh3h.dataprovider.IDataProvider;
import com.sh3h.dataprovider.entity.ConditionInfo;
import com.sh3h.dataprovider.entity.GongDan;
import com.sh3h.dataprovider.entity.GongDanCHAOBIAOZT;
import com.sh3h.dataprovider.entity.GongDanWORDS;
import com.sh3h.dataprovider.entity.GongDanYONGHUPZ;
import com.sh3h.dataprovider.greendaoEntity.BIAOKAXX;
import com.sh3h.dataprovider.greendaoEntity.BiaoWuGD;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoBZ;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoGJ;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoInfo;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoJL;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoRW;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoSJ;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoZT;
import com.sh3h.dataprovider.greendaoEntity.ChaoBiaoZTFL;
import com.sh3h.dataprovider.greendaoEntity.CiYuXX;
import com.sh3h.dataprovider.greendaoEntity.DengLuLS;
import com.sh3h.dataprovider.greendaoEntity.DingEJJBL;
import com.sh3h.dataprovider.greendaoEntity.DuoMeiTXX;
import com.sh3h.dataprovider.greendaoEntity.FeiYongZC;
import com.sh3h.dataprovider.greendaoEntity.FeiYongZKL;
import com.sh3h.dataprovider.greendaoEntity.GongGaoXX;
import com.sh3h.dataprovider.greendaoEntity.GuiJi;
import com.sh3h.dataprovider.greendaoEntity.HuanBiaoJL;
import com.sh3h.dataprovider.greendaoEntity.JiChaRW;
import com.sh3h.dataprovider.greendaoEntity.JiChaSJ;
import com.sh3h.dataprovider.greendaoEntity.JianHao;
import com.sh3h.dataprovider.greendaoEntity.JianHaoMX;
import com.sh3h.dataprovider.greendaoEntity.JiaoFeiXX;
import com.sh3h.dataprovider.greendaoEntity.MetaInfo;
import com.sh3h.dataprovider.greendaoEntity.QianFeiXX;
import com.sh3h.dataprovider.greendaoEntity.RenwuXX;
import com.sh3h.dataprovider.greendaoEntity.RushPayRW;
import com.sh3h.dataprovider.greendaoEntity.ShuiLiangFTXX;
import com.sh3h.dataprovider.greendaoEntity.UserInfo;
import com.sh3h.dataprovider.greendaoEntity.Users;
import com.sh3h.dataprovider.greendaoEntity.WaiFuCBSJ;
import com.sh3h.dataprovider.greendaoEntity.WaiFuGDDetail;
import com.sh3h.dataprovider.greendaoEntity.WaiFuGDMain;
import com.sh3h.dataprovider.greendaoEntity.WordsInfo;
import com.sh3h.dataprovider.greendaoEntity.XinXiBG;
import com.sh3h.dataprovider.greendaoEntity.YanChiBiao;
import com.sh3h.dataprovider.greendaoEntity.YuanGongXX;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;
import com.sh3h.serverprovider.entity.BiaoKaWholeEntity;
import com.sh3h.serverprovider.entity.XJXXWordBean;
import com.sh3h.serverprovider.entity.XunJianTaskBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by liurui on 2015/8/3.
 */
public class DataProviderImpl implements
        IDataProvider {

    protected static final int MAX_IN_SIZE = 800;
    private SQLiteDatabase db;
    private Context context;

    private DaoMaster daoMaster;
    private DaoSession daoSession;

    public static final String YICHAO = "已抄";
    public static final String WEICHAO = "未抄";
    public static final String GUCHAO = "估算";

    private BIAOKAXXDao biaokaxxDao;
    private ChaoBiaoZTDao chaoBiaoZTDao;
    private ChaoBiaoZTFLDao chaoBiaoZTFLDao;
    private DuoMeiTXXDao duoMeiTXXDao;
    private GuiJiDao guiJiDao;
    private NeiFuBZDao neiFuBZDao;
    private ZhuangTaiLXCSDao zhuangTaiLXCSDao;
    private RenwuXXDao renwuXXDao;
    private UsersDao userDao;
    private WaiFuGDDetailDao waiFuGDDetailDao;
    private WaiFuGDMainDao waiFuGDMainDao;
    private YanChiBiaoDao yanChiBiaoDao;
    private JiaoFeiXXDao jiaoFeiXXDao;
    private GongGaoXXDao gongGaoXXDao;
    private YuanGongXXDao yuanGongXXDao;
    private UserInfoDao userInfoDao;
    private QianFeiXXDao qianFeiXXDao;
    private JianHaoMXDao jianhaoMXDao;
    private HuanBiaoJLDao huanBiaoJLDao;
    private ChaoBiaoBZDao chaoBiaoBZDao;
    private ChaoBiaoGJDao chaoBiaoGJDao;
    private ChaoBiaoJLDao chaoBiaoJLDao;
    private ChaoBiaoRWDao chaoBiaoRWDao;
    private ChaoBiaoSJDao chaoBiaoSJDao;
    private CiYuXXDao ciYuXXDao;
    private ReXianGDDao reXianGDDao;
    private WordsInfoDao wordsInfoDao;
    private DingEJJBLDao dingEJJBLDao;
    private FeiYongZCDao feiYongZCDao;
    private JianHaoDao jianHaoDao;
    private FeiYongZKLDao feiYongZKLDao;
    private ShuiLiangFTXXDao shuiLiangFTXXDao;
    private XinXiBGDao xinXiBGDao;
    private DengLuLSDao dengLuLSDao;
    private MetaInfoDao metaInfoDao;
    private BiaoWuGDDao biaoWuDGDao;
    private RushPayRWDao rushPayRWDao;
    private JiChaSJDao jiChaSJDao;
    private JiChaRWDao jiChaRWDao;
    private WaiFuCBSJDao waiFuCBSJDao;
    private BiaoKaBeanDao biaoKaBeanDao;
    private BiaoKaListBeanDao biaoKaListBeanDao;
    private BiaoKaWholeEntityDao biaoKaWholeEntityDao;
    private XunJianTaskBeanDao xunJianTaskBeanDao;
    private XJXXWordBeanDao xjxxWordBeanDao;



//    private GongDanCHAOBIAOZTDao gongDanChaoBiaoDao;
//    private GongDanWORDSDao gongDanWordsDao;
//    private GongDanYONGHUPZDao gongDanYongHuPZDao;

    public DataProviderImpl() {
        db = null;
        context = null;
    }

    @Override
    public synchronized boolean init(String path, Context context) {
        this.context = context;
        db = DaoMaster.getSQLiteDatabase(path, context);
        if (db == null) {
            return false;
        }
        DaoMaster.createAllTables(db, true);

        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        if (DaoMaster.SCHEMA_VERSION == 3) {
            // 升级、数据库迁移操作
            Log.i("greenDAO", "Upgrading schema from version ");
//            MigrationHelper.getInstance().migrateQianFeiXX(db);
            daoMaster.updateQianFeiXX(3, db);
        }

        biaokaxxDao = daoSession.getBIAOKAXXDao();
        chaoBiaoZTDao = daoSession.getChaoBiaoZTDao();
        chaoBiaoZTFLDao = daoSession.getChaoBiaoZTFLDao();
        duoMeiTXXDao = daoSession.getDuoMeiTXXDao();
        guiJiDao = daoSession.getGuiJiDao();
        neiFuBZDao = daoSession.getNeiFuBZDao();
        zhuangTaiLXCSDao = daoSession.getZhuangTaiLXCSDao();
        renwuXXDao = daoSession.getRenwuXXDao();
        userDao = daoSession.getUsersDao();
        waiFuGDDetailDao = daoSession.getWaiFuGDDetailDao();
        waiFuGDMainDao = daoSession.getWaiFuGDMainDao();
        yanChiBiaoDao = daoSession.getYanChiBiaoDao();
        jiaoFeiXXDao = daoSession.getJiaoFeiXXDao();
        gongGaoXXDao = daoSession.getGongGaoXXDao();
        yuanGongXXDao = daoSession.getYuanGongXXDao();
        userInfoDao = daoSession.getUserInfoDao();
        qianFeiXXDao = daoSession.getQianFeiXXDao();
        jianhaoMXDao = daoSession.getJianHaoMXDao();
        huanBiaoJLDao = daoSession.getHuanBiaoJLDao();
        chaoBiaoBZDao = daoSession.getChaoBiaoBZDao();
        chaoBiaoGJDao = daoSession.getChaoBiaoGJDao();
        chaoBiaoJLDao = daoSession.getChaoBiaoJLDao();
        chaoBiaoRWDao = daoSession.getChaoBiaoRWDao();
        chaoBiaoSJDao = daoSession.getChaoBiaoSJDao();
        ciYuXXDao = daoSession.getCiYuXXDao();
        reXianGDDao = daoSession.getReXianGDDao();
        wordsInfoDao = daoSession.getWordsInfoDao();
        dingEJJBLDao = daoSession.getDingEJJBLDao();
        feiYongZCDao = daoSession.getFeiYongZCDao();
        jianHaoDao = daoSession.getJianHaoDao();
        feiYongZKLDao = daoSession.getFeiYongZKLDao();
        shuiLiangFTXXDao = daoSession.getShuiLiangFTXXDao();
        xinXiBGDao = daoSession.getXinXiBGDao();
        dengLuLSDao = daoSession.getDengLuLSDao();
        metaInfoDao = daoSession.getMetaInfoDao();
        biaoWuDGDao = daoSession.getBiaoWuGDDao();
        rushPayRWDao = daoSession.getRushPayRWDao();
        jiChaRWDao = daoSession.getJiChaRWDao();
        jiChaSJDao = daoSession.getJiChaSJDao();
        waiFuCBSJDao = daoSession.getWaiFuCBSJDao();
        biaoKaBeanDao = daoSession.getBiaoKaBeanDao();
        biaoKaListBeanDao = daoSession.getBiaoKaListBeanDao();
        biaoKaWholeEntityDao=daoSession.getBiaoKaWholeEntityDao();
        xunJianTaskBeanDao=daoSession.getXunJianTaskBeanDao();
        xjxxWordBeanDao=daoSession.getXJXXWordBeanDao();


        return true;
    }

    @Override
    public synchronized void destroy() {
        if (db != null) {
            db.close();
            db = null;
        }

        biaokaxxDao = null;
        chaoBiaoZTDao = null;
        chaoBiaoZTFLDao = null;
        duoMeiTXXDao = null;
        guiJiDao = null;
        neiFuBZDao = null;
        zhuangTaiLXCSDao = null;
        renwuXXDao = null;
        userDao = null;
        waiFuGDDetailDao = null;
        waiFuGDMainDao = null;
        yanChiBiaoDao = null;
        jiaoFeiXXDao = null;
        gongGaoXXDao = null;
        yuanGongXXDao = null;
        userInfoDao = null;
        qianFeiXXDao = null;
        jianhaoMXDao = null;
        huanBiaoJLDao = null;
        chaoBiaoBZDao = null;
        chaoBiaoGJDao = null;
        chaoBiaoJLDao = null;
        chaoBiaoRWDao = null;
        chaoBiaoSJDao = null;
        ciYuXXDao = null;
        reXianGDDao = null;
        wordsInfoDao = null;
        dingEJJBLDao = null;
        feiYongZCDao = null;
        jianHaoDao = null;
        feiYongZKLDao = null;
        shuiLiangFTXXDao = null;
        xinXiBGDao = null;
        dengLuLSDao = null;
        metaInfoDao = null;
        biaoWuDGDao = null;
    }

    @Override
    public synchronized MetaInfo getMetaInfo(String account) {
        if (metaInfoDao == null) {
            return null;
        }

        return metaInfoDao.get(account);
    }

    @Override
    public synchronized List<ChaoBiaoRW> getChaoBiaoRWList(String account) {
        if (chaoBiaoRWDao == null) {
            return null;
        }

        return chaoBiaoRWDao.get(account);
    }

    @Override
    public synchronized List<ChaoBiaoRW> getChaoBiaoRWList(String account, int rwType) {
        if (chaoBiaoRWDao == null) {
            return null;
        }

        return chaoBiaoRWDao.get(account, rwType);
    }

    @Override
    public int isUploaded(String account, String ch) {
        if (chaoBiaoSJDao == null){
            return 0;
        }

        if (!chaoBiaoSJDao.isUpload(account, ch)){
            return 0;
        }

        if (duoMeiTXXDao == null){
            return 1;
        }

        return duoMeiTXXDao.isUpload(account, ch) ? 2 : 1;
    }

    @Override
    public synchronized ChaoBiaoRW getChaoBiaoRW(int id) {
        if (chaoBiaoRWDao == null) {
            return null;
        }

        return chaoBiaoRWDao.get(id);
    }

    @Override
    public synchronized ChaoBiaoRW getChaoBiaoRW(String account, int taskId, String ch) {
        if (chaoBiaoRWDao == null) {
            return null;
        }

        return chaoBiaoRWDao.getChaoBiaoRW(account, taskId, ch);
    }

    @Override
    public synchronized ChaoBiaoRW getChaoBiaoRW(String account, int taskId) {
        if (chaoBiaoRWDao == null) {
            return null;
        }

        return chaoBiaoRWDao.getChaoBiaoRW(account, taskId);
    }

    @Override
    public synchronized List<ChaoBiaoRW> getChaoBiaoRWList(String account, String ids) {
        if (chaoBiaoRWDao == null) {
            return null;
        }

        return chaoBiaoRWDao.getChaoBiaoRWList(account, ids);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getListChaoBiaoSJ(String cH, int sortType) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getList(cH, sortType);
    }

    @Override
    public List<ChaoBiaoSJ> getLgldChaoBiaoSJ(String account, int sortType) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getLgldChaoBiaoSJ(account, sortType);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getListChaoBiaoSJ(int taskId, String cH, int sortType) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getList(taskId, cH, sortType);
    }

    @Override
    public List<ChaoBiaoSJ> getListChaoBiaoSJHighOrLow(int taskId, String cH, int sortType, double waterHighN) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getList(taskId, cH, sortType, waterHighN);
    }

    @Override
    public List<YanChiBiao> getListYanChiBiao(String account, int sortType) {
        if (yanChiBiaoDao == null){
            return null;
        }
        return yanChiBiaoDao.getListYanChiBiao(account, sortType);
    }

    @Override
    public List<YanChiBiao> getListYanChiBiao(String account, String key) {
        if (yanChiBiaoDao == null){
            return null;
        }
        return yanChiBiaoDao.getListYanChiBiao(account, key);
    }

    @Override
    public synchronized int getChaoJianS(String account, int taskId, String volume, String chaoJianZTS) {
        if (chaoBiaoSJDao == null) {
            return 0;
        }

        return chaoBiaoSJDao.getChaoJianS(account, taskId, volume, chaoJianZTS);
    }

    @Override
    public synchronized ChaoBiaoSJ getChaoBiaoSJ(int taskId, String cH, String cId) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.get(taskId, cH, cId);
    }

    @Override
    public synchronized ChaoBiaoSJ getChaoBiaoSJByGroupId(int taskId, int groupId, String S_CID) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getChaoBiaoSJByGroupId(taskId, groupId, S_CID);
    }

    @Override
    public synchronized ChaoBiaoSJ getNextChaoBiaoSJNew(String account, int taskId, String S_CID, boolean isNotReading) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getNextChaoBiaoSJNew(account, taskId, S_CID, isNotReading);
    }

    @Override
    public synchronized ChaoBiaoSJ getPreviousChaoBiaoSJNew(String account, int taskId, String S_CID, boolean isNotReading) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getPreviousChaoBiaoSJNew(account, taskId, S_CID, isNotReading);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getChaoBiaoSJList(int taskId, String ch, List<String> cids) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.get(taskId, ch, cids);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getChaoBiaoSJList(int taskId, int groupId, List<String> cids) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.get(taskId, groupId, cids);
    }

    @Override
    public synchronized ChaoBiaoSJ getNextChaoBiaoSJ(int taskId, String ch, long ceneixh, boolean isNotReading) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getNextChaoBiaoSJ(taskId, ch, ceneixh, isNotReading);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getNextUnfinishedChaoBiaoSJWithCeNeiXH(int renWuBH, String ch) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getNextUnfinishedChaoBiaoSJWithCeNeiXH(renWuBH, ch);
    }

    @Override
    public synchronized ChaoBiaoSJ getPreviousChaoBiaoSJ(int taskId, String ch, long ceneixh, boolean isNotReading) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getPreviousChaoBiaoSJ(taskId, ch, ceneixh, isNotReading);
    }

    @Override
    public synchronized BIAOKAXX getBiaoKaXX(String cId) {
        if (biaokaxxDao == null) {
            return null;
        }

        return this.biaokaxxDao.get(cId);
    }

    @Override
    public long getCountByTaskId(int taskId) {
        if (biaokaxxDao == null) {
            return 0;
        }

        return this.biaokaxxDao.getCountByTaskId(taskId);
    }

    @Override
    public synchronized List<ChaoBiaoInfo> getchaoBiaoSJ(int chaoBiaoBZ, String cH, String chaoBiaoY) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getchaoBiaoPieSJ(chaoBiaoBZ, cH, chaoBiaoY);
    }

    @Override
    public synchronized int getBiaoKaCount(String s_ch) {
        if (chaoBiaoSJDao == null) {
            return 0;
        }

        return chaoBiaoSJDao.getBiaoKaCount(s_ch);
    }

    @Override
    public synchronized ChaoBiaoSJ getChaoBSJBySCHAndCeNeiPX(String S_CH, Integer I_CeNeiPX) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getChaoBSJBySCHAndCeNeiPX(S_CH, I_CeNeiPX);
    }

    @Override
    public synchronized Map<String, Integer> getChaoBiaoSJCount(String cH, String guSuan, String chaoBiaoY) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        int sjCount = chaoBiaoSJDao.getChaoBiaoPillarSJ(cH, chaoBiaoY);
        int weiChao = chaoBiaoSJDao.getChaoBiaoPillarWCSJ(cH, chaoBiaoY);
        int guSuanBM = chaoBiaoSJDao.getChaoBiaoPillarYCSJ(cH, guSuan,
                chaoBiaoY);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put(GUCHAO, guSuanBM);
        map.put(WEICHAO, weiChao);
        map.put(YICHAO, sjCount);

        return map;
    }

    @Override
    public synchronized ChaoBiaoSJ getUnWorkChaoBiaoSJ(String CH) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getFirstUnWorkChaoBiaoSJ(CH);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getSelectChaoBiaoSJ(String str, String cH) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getSelectChaoBiaoSJ(str, cH);
    }

  @Override
  public synchronized boolean insertXunJianBK(List<BiaoKaBean> biaoKaBeans) {
    if (biaoKaBeanDao == null) {
      return false;
    }

    return biaoKaBeanDao.insertXunJianBK(biaoKaBeans);
  }

  @Override
  public synchronized List<BiaoKaListBean> getXunJianListBK() {
    if (biaoKaListBeanDao == null) {
      return null;
    }

    return biaoKaListBeanDao.loadAll();
  }

  @Override
  public synchronized List<BiaoKaListBean> getXunJianListBK(String xiaogenghao) {
    if (biaoKaListBeanDao == null) {
      return null;
    }

    return biaoKaListBeanDao.getBiaokaList(xiaogenghao);
  }

  @Override
  public synchronized List<BiaoKaBean> getXunJianBK() {
    if (biaoKaBeanDao == null) {
      return null;
    }

    return biaoKaBeanDao.loadAll();
  }

  @Override
  public synchronized List<BiaoKaBean> getXunJianBK(String xiaogenghao) {
    if (biaoKaBeanDao == null) {
      return null;
    }

    return biaoKaBeanDao.getXunJianBK(xiaogenghao);
  }

  @Override
  public synchronized List<BiaoKaListBean> getXunJianHistoryBK() {
    if (biaoKaListBeanDao == null) {
      return null;
    }

    return biaoKaListBeanDao.getXunJianhistoryBK();
  }

  @Override
  public synchronized List<XJXXWordBean> getXunJianWord(String type) {
    if (xjxxWordBeanDao == null) {
      return null;
    }

    return xjxxWordBeanDao.getXunJianWord(type);
  }

  @Override
  public synchronized List<XJXXWordBean> getHotlineWordData(String type) {
    if (xjxxWordBeanDao == null) {
      return null;
    }

    return xjxxWordBeanDao.getHotlineWordData(type);
  }

  @Override
  public synchronized List<XJXXWordBean> getHotlineWordData(String type,String yongshuixz) {
    if (xjxxWordBeanDao == null) {
      return null;
    }

    return xjxxWordBeanDao.getHotlineWordData(type,yongshuixz);
  }

  @Override
  public synchronized List<BiaoKaListBean> getBiaokaListBeans(String renwuhao,String type) {
    if (biaoKaListBeanDao == null) {
      return null;
    }

    return biaoKaListBeanDao.getBiaokaListBeans(renwuhao,type);
  }

  @Override
  public synchronized List<BiaoKaWholeEntity> getbiaoKaWholeEntitys(long id) {
    if (biaoKaWholeEntityDao == null) {
      return null;
    }

    return biaoKaWholeEntityDao.getBiaokaListBeans(id);
  }

  @Override
  public synchronized List<BiaoKaListBean> getWcorYcBiaoKalistbean2(String renwumc,String type) {
    if (biaoKaListBeanDao == null) {
      return null;
    }

    return biaoKaListBeanDao.getWcorYcBiaoKalistbean2(renwumc,type);
  }

  @Override
  public synchronized List<BiaoKaBean> getBiaoKaBean(String Xiaogenhao) {
    if (biaoKaBeanDao == null) {
      return null;
    }

    return biaoKaBeanDao.getBiaoKaBean(Xiaogenhao);
  }

  @Override
  public synchronized List<BiaoKaWholeEntity> getBiaoKaWholeEntity(String renWuId,String Xiaogenhao) {
    if (biaoKaWholeEntityDao == null) {
      return null;
    }

    return biaoKaWholeEntityDao.getBiaoKaWholeEntity(renWuId,Xiaogenhao);
  }

  @Override
  public synchronized List<BiaoKaWholeEntity> getBiaoKaWholeEntity(long id) {
    if (biaoKaWholeEntityDao == null) {
      return new ArrayList<>();
    }

    return biaoKaWholeEntityDao.getBiaoKaWholeEntity(id);
  }

  @Override
  public synchronized boolean insertXunjianWord(List<XJXXWordBean> data) {
    if (xjxxWordBeanDao == null) {
      return false;
    }

    return xjxxWordBeanDao.insertXunjianWord(data);
  }

  @Override
  public synchronized boolean deleteXunjianWord() {
    if (xjxxWordBeanDao == null) {
      return false;
    }

    return xjxxWordBeanDao.deleteXunjianWord();
  }

  @Override
  public synchronized boolean insertXunjianBKlist(List<BiaoKaListBean> biaoKaBeans) {
    if (biaoKaListBeanDao == null) {
      return false;
    }

    return biaoKaListBeanDao.insertXunjianBKlist(biaoKaBeans);
  }

  @Override
  public synchronized List<XunJianTaskBean> getXunJianFuHeTaskBean(String xunjiantaskType) {
    if (xunJianTaskBeanDao == null) {
      return null;
    }

    return xunJianTaskBeanDao.getXunJianFuHeTaskBean(xunjiantaskType);
  }

  @Override
  public synchronized List<BiaoKaListBean> getTiJiaoBiaoKaListBean(String renwumc,int iscommit) {
    if (biaoKaListBeanDao == null) {
      return null;
    }

    return biaoKaListBeanDao.getTiJiaoBiaoKaListBean(renwumc,iscommit);
  }

  @Override
  public synchronized boolean insertBKWholeEntity(BiaoKaWholeEntity newWholeEntity) {
    if ( biaoKaWholeEntityDao == null) {
      return false;
    }

    return biaoKaWholeEntityDao.insertBKWholeEntity(newWholeEntity);
  }

  @Override
  public synchronized List<XunJianTaskBean> getLocalXunJianTasks(String type) {
    if (xunJianTaskBeanDao == null) {
      return null;
    }

    return xunJianTaskBeanDao.getLocalXunJianTasks(type);
  }

  @Override
  public synchronized List<BiaoKaListBean> getWcorYcBiaoKalistbean(String renwumc,String type) {
    if (biaoKaListBeanDao == null) {
      return null;
    }

    return biaoKaListBeanDao.getWcorYcBiaoKalistbean(renwumc,type);
  }

  @Override
  public synchronized List<XunJianTaskBean> getXunJianTaskBean(String renwumc) {
    if (xunJianTaskBeanDao == null) {
      return null;
    }

    return xunJianTaskBeanDao.getXunJianTaskBean(renwumc);
  }

  @Override
  public synchronized List<BiaoKaListBean> getBiaoKaListBean(String renwuid) {
    if (biaoKaListBeanDao == null) {
      return null;
    }

    return biaoKaListBeanDao.getBiaoKaListBean(renwuid);
  }

  @Override
  public synchronized void deleteBiaoKaListBean() {
    if (biaoKaListBeanDao == null) {
      return;
    }

    biaoKaListBeanDao.deleteBiaoKaListBean();
  }

  @Override
  public synchronized void saveXunjianBKlistBean(BiaoKaListBean biaoKaListBean) {
    if (biaoKaListBeanDao == null) {
      return ;
    }

    biaoKaListBeanDao.saveXunjianBKlistBean(biaoKaListBean);
  }

  @Override
  public synchronized List<BiaoKaWholeEntity>  getBiaoKaWholeEntity2(String renwumc, int issave) {
    if (biaoKaWholeEntityDao == null) {
      return null;
    }

    return biaoKaWholeEntityDao.getBiaoKaWholeEntity2(renwumc,issave);
  }

  @Override
  public synchronized boolean SaveXunJianTasks(List<XunJianTaskBean> xunJianTaskBeans) {
    if (xunJianTaskBeanDao == null) {
      return false;
    }

    return xunJianTaskBeanDao.SaveXunJianTasks(xunJianTaskBeans);
  }

  @Override
  public synchronized boolean saveBiaoKaListBean(List<BiaoKaListBean> biaoKaList) {
    if (biaoKaListBeanDao == null) {
      return false;
    }

    return biaoKaListBeanDao.saveBiaoKaListBean(biaoKaList);
  }

  @Override
  public synchronized boolean savebiaoKaBean(List<BiaoKaBean> biaoKaBeans) {
    if (biaoKaBeanDao == null) {
      return false;
    }

    return biaoKaBeanDao.savebiaoKaBean(biaoKaBeans);
  }

  @Override
  public synchronized void saveBiaoKaWholeEntityDao(BiaoKaWholeEntity biaoKaWholeEntity) {
    if (biaoKaWholeEntityDao == null) {
      return ;
    }

    biaoKaWholeEntityDao.saveBiaoKaWholeEntityDao(biaoKaWholeEntity);
  }

//    @Override
//    public synchronized boolean updateChaobiaoSJ(String S_CID, int I_ZhuangTaiBM, String S_ZhuangTaiMC,
//                                    int I_BenCiCM, int I_ChaoJianSL, double N_JE, String S_CH,
//                                    int I_LiangGaoLDYYBM, String S_ChaoBiaoBeiZhu, double x1,
//                                    double y1, int chaoBiaoBiaoZhi, String jieTiTS) {
//        return chaoBiaoSJDao.updateChaobiaoSJ(S_CID, I_ZhuangTaiBM,
//                S_ZhuangTaiMC, I_BenCiCM, I_ChaoJianSL, N_JE,
//                S_CH, I_LiangGaoLDYYBM, S_ChaoBiaoBeiZhu,
//                x1, y1, chaoBiaoBiaoZhi, jieTiTS);
//    }

    @Override
    public synchronized List<JianHaoMX> getJianHaoMXByJH(String S_JH) {
        if (jianhaoMXDao == null) {
            return null;
        }

        return jianhaoMXDao.getJianHaoMXByJH(S_JH);
    }

    @Override
    public synchronized ChaoBiaoZT getChaoBiaoZT(int i_chaobiaoztbm) {
        return null;
    }

    @Override
    public synchronized List<ChaoBiaoZT> getAllChaobiaozt() {
        if (chaoBiaoZTDao == null) {
            return null;
        }

        return chaoBiaoZTDao.getAllChaobiaozt();
    }

    @Override
    public synchronized List<JiaoFeiXX> getListJiaoFeiXX(String cId) {
        if (jiaoFeiXXDao == null) {
            return null;
        }

        return jiaoFeiXXDao.getList(cId);
    }

    @Override
    public synchronized List<QianFeiXX> getListQianFeiXX(String cId) {
        if (qianFeiXXDao == null) {
            return null;
        }

        return qianFeiXXDao.getList(cId);
    }

    @Override
    public synchronized List<ChaoBiaoJL> getListChaoBiaoJL(String cId) {
        if (chaoBiaoJLDao == null) {
            return null;
        }

        return chaoBiaoJLDao.getList(cId);
    }

    @Override
    public synchronized List<ChaoBiaoJL> getAllListChaoBiaoJL() {
        if (chaoBiaoJLDao == null) {
            return null;
        }

        return chaoBiaoJLDao.getAllList();
    }

    @Override
    public synchronized List<ChaoBiaoZTFL> getAllChaobiaoZTFL() {
        if (chaoBiaoZTFLDao == null) {
            return null;
        }

        return this.chaoBiaoZTFLDao.getAllChaobiaoztfl();
    }

    @Override
    public synchronized List<ChaoBiaoZT> getChaoBiaoZTFL(int zhuangtaiFLBM) {
        return null;
    }

    @Override
    public synchronized List<ChaoBiaoZT> getZhuangTaiBMList(String zhuangTaiBM) {
        if (chaoBiaoZTDao == null) {
            return null;
        }

        return this.chaoBiaoZTDao.getAllChaobiaozt(zhuangTaiBM);
    }

    @Override
    public synchronized void deleteShuiLiangFTXX(String CIDAll) {
        if (shuiLiangFTXXDao == null) {
            return;
        }

        shuiLiangFTXXDao.deleteShuiLiangFTXX(CIDAll);
    }

    @Override
    public synchronized boolean insertShuiLiangFTXX(ShuiLiangFTXX shuiLiangFTXX) {
        if (shuiLiangFTXXDao == null) {
            return false;
        }

        return shuiLiangFTXXDao.insertShuiLiangFTXX(shuiLiangFTXX);
    }

    @Override
    public synchronized boolean updateShuiLiangFTXX(ShuiLiangFTXX shuiLiangFTXX) {
        if (shuiLiangFTXXDao == null) {
            return false;
        }

        return shuiLiangFTXXDao.updateShuiLiangFTXX(shuiLiangFTXX);
    }

    @Override
    public synchronized void insertOrUpdateShuiLiangFTXXList(List<ShuiLiangFTXX> shuiLiangFTXXList) {
        if (shuiLiangFTXXDao == null) {
            return;
        }

        shuiLiangFTXXDao.insertOrUpdateShuiLiangFTXXList(shuiLiangFTXXList);
    }

    @Override
    public synchronized List<ShuiLiangFTXX> getShuiLiangFenTXX(String s_cid) {
        if (shuiLiangFTXXDao == null) {
            return null;
        }

        return shuiLiangFTXXDao.getShuiLiangFenTXX(s_cid);
    }

    @Override
    public synchronized List<String> getCH() {
        if (chaoBiaoRWDao == null) {
            return null;
        }

        return chaoBiaoRWDao.getCH();
    }

    @Override
    public synchronized boolean insertDuoMeiTXX(DuoMeiTXX duoMeiTXX) {
        if (duoMeiTXXDao == null) {
            return false;
        }

        return duoMeiTXXDao.insertDuoMeiTXX(duoMeiTXX);
    }

    @Override
    public synchronized boolean updateNJE(int taskId, String cH, String S_CID, double N_JE) {
        if (chaoBiaoSJDao == null) {
            return false;
        }

        return chaoBiaoSJDao.updateNJE(taskId, cH, S_CID, N_JE);
    }

    @Override
    public synchronized List<DuoMeiTXX> getDuoMeiTXXList(int chaoBiaoID, String CID) {
        if (duoMeiTXXDao == null) {
            return null;
        }

        return duoMeiTXXDao.getDuoMeiTXXList(chaoBiaoID, CID);
    }

    @Override
    public synchronized List<DuoMeiTXX> getNotUploadedDuoMeiTXXList(String account, int renwubh, String ch) {
        if (duoMeiTXXDao == null) {
            return null;
        }

        return duoMeiTXXDao.getNotUploadedDuoMeiTXXList(account, renwubh, ch);
    }

    @Override
    public synchronized List<DuoMeiTXX> getMoreDuoMeiTXXList(String account, int taskId,
                                                             String volume, int offset, int limit) {
        if (duoMeiTXXDao == null) {
            return null;
        }

        return duoMeiTXXDao.getMoreDuoMeiTXXList(account, taskId, volume, offset, limit);
    }

    @Override
    public synchronized List<DuoMeiTXX> getMoreDuoMeiTXXList(String account, int offset, int limit) {
        if (duoMeiTXXDao == null) {
            return null;
        }
        return duoMeiTXXDao.getMoreDuoMeiTXXWaiFuList(account, offset, limit);
    }

    @Override
    public List<DuoMeiTXX> getMoreDelayDuoMeiTXXList(String account, int offset, int limit) {
        if (duoMeiTXXDao == null) {
            return null;
        }
        return duoMeiTXXDao.getMoreDelayDuoMeiTXXList(account, offset, limit);
    }

    @Override
    public synchronized boolean deleteDuoMeiTXX(String wenJianMC) {
        if (duoMeiTXXDao == null) {
            return false;
        }

        return duoMeiTXXDao.deleteDuoMeiTXX(wenJianMC);
    }

    @Override
    public synchronized List<JianHao> getJianHaoList() {
        return null;
    }

    @Override
    public synchronized JianHao getJianHao(String strZhongLei) {
        return null;
    }

    @Override
    public synchronized List<BIAOKAXX> QueryBiaoKaxxsWhere(String swhere, int GuBiaoLXCS, int LiangGaoLDLXCS) {
        if (biaokaxxDao == null) {
            return null;
        }

        return biaokaxxDao.QueryBiaoKaxxsWhere(swhere, GuBiaoLXCS,
                LiangGaoLDLXCS);
    }

    @Override
    public synchronized List<BIAOKAXX> QueryBiaoKaxxsWhere(String key, String ChaoBiaoRWCHAll) {
        if (biaokaxxDao == null) {
            return null;
        }

        return biaokaxxDao.QueryBiaoKaxxsWhere(key, ChaoBiaoRWCHAll);
    }

    @Override
    public synchronized List<CiYuXX> getLiangGaoYY() {
        if (ciYuXXDao == null) {
            return null;
        }

        return ciYuXXDao.getLiangGaoYY();
    }

    @Override
    public synchronized List<CiYuXX> getLiangDiYY() {
        if (ciYuXXDao == null) {
            return null;
        }

        return ciYuXXDao.getLiangDiYY();
    }

    @Override
    public List<CiYuXX> getNoReadReason() {
        if (ciYuXXDao==null){
            return null;
        }

        return ciYuXXDao.getNoReadReason();
    }

    @Override
    public synchronized boolean authInterface(String account) {
        if (metaInfoDao == null) {
            return false;
        }

        return metaInfoDao.authenticationInterface(account);
    }

    @Override
    public synchronized double getDingEJiaGeByCIDAndJH(String S_CID, String S_JH) {
        if (jianhaoMXDao == null) {
            return 0;
        }

        return jianhaoMXDao.getDingEJiaGeByCIDAndJH(S_CID, S_JH);
    }

    @Override
    public synchronized List<DingEJJBL> getAllDingEJJBL() {
        if (dingEJJBLDao == null) {
            return null;
        }

        return dingEJJBLDao.getAllDingEJJBL();
    }

    @Override
    public synchronized boolean existCiYuXX(int id) {
        if (ciYuXXDao == null) {
            return false;
        }

        return ciYuXXDao.existCiYuXX(id);
    }

    @Override
    public synchronized List<CiYuXX> getCiYuXX(int belongid) {
        if (ciYuXXDao == null) {
            return null;
        }

        return ciYuXXDao.getCiYuXX(belongid);
    }

    @Override
    public synchronized boolean deleteCiYu() {
        if (ciYuXXDao == null) {
            return false;
        }

        return ciYuXXDao.delete();
    }

    @Override
    public synchronized boolean deleteCiYu(String code) {
        if (ciYuXXDao == null) {
            return false;
        }

        return ciYuXXDao.delete(code);
    }

    @Override
    public synchronized void insertCiYu(CiYuXX ciyuXX) {
        if (ciYuXXDao == null) {
            return;
        }

        ciYuXXDao.insertCiyuXX(ciyuXX);
    }

    @Override
    public synchronized YuanGongXX getYuanGongXX(String account) {
        if (yuanGongXXDao == null) {
            return null;
        }

        return yuanGongXXDao.get(account);
    }

    @Override
    public synchronized int auth(String pwd, String account) {
        if (dengLuLSDao == null) {
            return 0;
        }

        return dengLuLSDao.auth(pwd, account);
    }

    @Override
    public synchronized boolean updateDengLuLS(DengLuLS dengLuLS) {
        if (dengLuLSDao == null) {
            return false;
        }

        return dengLuLSDao.updateDengLuLS(dengLuLS);
    }

    @Override
    public synchronized int updateYHxx(String s_CID, String s_LianXiDH, String s_LIANXISJ, String s_JianHao, String s_BEIZHU) {
        if (xinXiBGDao == null) {
            return 0;
        }

        XinXiBG xinxi = new XinXiBG();
        xinxi.setS_CID(s_CID);
        xinxi.setS_LianXiDH(s_LianXiDH);
        xinxi.setS_LIANXISJ(s_LIANXISJ);
        xinxi.setS_JianHao(s_JianHao);
        xinxi.setS_BEIZHU(s_BEIZHU);

        return xinXiBGDao.InsertXinXiBG(xinxi);
    }

    @Override
    public synchronized List<HuanBiaoJL> getHuanBiaoJLList(String cId) {
        if (huanBiaoJLDao == null) {
            return null;
        }

        return huanBiaoJLDao.getList(cId);
    }

    @Override
    public synchronized boolean clearHuanBiaoJL(String cId) {
        if (huanBiaoJLDao == null) {
            return false;
        }

        return huanBiaoJLDao.clear(cId);
    }

    @Override
    public synchronized boolean insertHuanBiaoJL(HuanBiaoJL huanbiaojl) {
        if (huanBiaoJLDao == null) {
            return false;
        }

        return huanBiaoJLDao.insertData(huanbiaojl);
    }

    @Override
    public synchronized boolean insertHuanBiaoJLs(List<HuanBiaoJL> huanBiaoJLList) {
        if (huanBiaoJLDao == null) {
            return false;
        }

        return huanBiaoJLDao.insertHuanBiaoJLs(huanBiaoJLList);
    }

    @Override
    public synchronized boolean updateBiaoKaXX(BIAOKAXX biaoKaXX) {
        if (biaokaxxDao == null) {
            return false;
        }

        return biaokaxxDao.updateSingle(biaoKaXX);
    }

    @Override
    public synchronized boolean updateBiaoKaXXList(List<BIAOKAXX> biaoKaXXList) {
        if (biaokaxxDao == null) {
            return false;
        }

        return biaokaxxDao.updateBiaoKaXXList(biaoKaXXList);
    }

    @Override
    public synchronized boolean clearChaoBiaoJL(String cId) {
        if (chaoBiaoJLDao == null) {
            return false;
        }

        return chaoBiaoJLDao.clear(cId);
    }

    @Override
    public synchronized boolean insertChaoBiaoJL(ChaoBiaoJL chaoBiaoJL) {
        if (chaoBiaoJLDao == null) {
            return false;
        }

        return chaoBiaoJLDao.insertData(chaoBiaoJL);
    }

    @Override
    public synchronized boolean insertChaoBiaoJLList(List<ChaoBiaoJL> chaoBiaoJLList) {
        if (chaoBiaoJLDao == null) {
            return false;
        }

        return chaoBiaoJLDao.insertChaoBiaoJLList(chaoBiaoJLList);
    }

    @Override
    public synchronized boolean clearJiaoFeiXX(String cId) {
        if (jiaoFeiXXDao == null) {
            return false;
        }

        return jiaoFeiXXDao.clear(cId);
    }

    @Override
    public synchronized boolean insertJiaoFeiXX(JiaoFeiXX jiaoFeiXX) {
        if (jiaoFeiXXDao == null) {
            return false;
        }

        return jiaoFeiXXDao.insertData(jiaoFeiXX);
    }

    @Override
    public synchronized boolean insertJiaoFeiXXs(List<JiaoFeiXX> jiaoFeiXXList) {
        if (jiaoFeiXXDao == null) {
            return false;
        }

        return jiaoFeiXXDao.insertJiaoFeiXXs(jiaoFeiXXList);
    }

    @Override
    public synchronized boolean clearQianFeiXX(String cId) {
        if (qianFeiXXDao == null) {
            return false;
        }

        return qianFeiXXDao.clear(cId);
    }

    @Override
    public synchronized boolean clearQianFeiXX(List<String> cids) {
        if (qianFeiXXDao == null) {
            return false;
        }

        return qianFeiXXDao.clear(cids);
    }

    @Override
    public synchronized boolean insertQianFeiXX(QianFeiXX qianFeiXX) {
        if (qianFeiXXDao == null) {
            return false;
        }

        return qianFeiXXDao.insertData(qianFeiXX);
    }

    @Override
    public synchronized boolean insertQianFeiXXs(List<QianFeiXX> qianFeiXXList) {
        if (qianFeiXXDao == null) {
            return false;
        }

        return qianFeiXXDao.insertQianFeiXXs(qianFeiXXList);
    }

    @Override
    public synchronized List<CiYuXX> getCiYuXXlist(int belongid) {
        return null;
    }

    @Override
    public synchronized List<GongGaoXX> getGongGaoXXList() {
        if (gongGaoXXDao == null) {
            return null;
        }

        return gongGaoXXDao.getGongGaoXXList();
    }

    @Override
    public synchronized GongGaoXX getGongGaoXX(int gongGaoBH) {
        if (gongGaoXXDao == null) {
            return null;
        }

        return gongGaoXXDao.getGongGaoXX(gongGaoBH);
    }

    @Override
    public synchronized boolean insertGongGaoXX(GongGaoXX gongGaoXX) {
        if (gongGaoXXDao == null) {
            return false;
        }

        return gongGaoXXDao.insertGongGaoXX(gongGaoXX);
    }

    @Override
    public synchronized DengLuLS getDengLuLS(String account) {
        if (dengLuLSDao == null) {
            return null;
        }

        return dengLuLSDao.getDengLuLS(account);
    }

    @Override
    public synchronized void insertChaoBiaoZT(ChaoBiaoZT chaoBiaoZT) {
        if (chaoBiaoZTDao == null) {
            return;
        }

        chaoBiaoZTDao.insertChaoBiaoZT(chaoBiaoZT);
    }

    @Override
    public synchronized void deleteChaoBiaoZT() {
        if (chaoBiaoZTDao == null) {
            return;
        }

        chaoBiaoZTDao.deleteChaoBiaoZT();
    }

    @Override
    public synchronized boolean existChaoBiaoZT(int zhuangTaiBM) {
        return false;
    }

    @Override
    public synchronized boolean existChaoBiaoZTFL(int fenLeiBM) {
        if (chaoBiaoZTFLDao == null) {
            return false;
        }

        return chaoBiaoZTFLDao.existChaoBiaoZTFL(fenLeiBM);
    }

    @Override
    public synchronized void deleteChaoBiaoZTFL() {
        if (chaoBiaoZTFLDao == null) {
            return;
        }

        chaoBiaoZTFLDao.deleteChaoBiaoZTFL();
    }

    @Override
    public synchronized void insertChaoBiaoZTFL(ChaoBiaoZTFL chaoBiaoZTFL) {
        if (chaoBiaoZTFLDao == null) {
            return;
        }

        chaoBiaoZTFLDao.insertChaoBiaoZTFL(chaoBiaoZTFL);
    }

    @Override
    public synchronized boolean existDingEJJBL(int id) {
        if (dingEJJBLDao == null) {
            return false;
        }

        return dingEJJBLDao.existDingEJJBL(id);
    }

    @Override
    public synchronized void deleteDingEJJBL() {
        if (dingEJJBLDao == null) {
            return;
        }

        dingEJJBLDao.deleteDingEJJBL();
    }

    @Override
    public synchronized void insertDingEJJBL(DingEJJBL dingEJJBL) {
        if (dingEJJBLDao == null) {
            return;
        }

        dingEJJBLDao.insertDingEJJBL(dingEJJBL);
    }

    @Override
    public synchronized boolean existFeiYongZC(int id) {
        if (feiYongZCDao == null) {
            return false;
        }

        return feiYongZCDao.existFeiYongZC(id);
    }

    @Override
    public synchronized void deleteFeiYongZC() {
        if (feiYongZCDao == null) {
            return;
        }

        feiYongZCDao.deleteFeiYongZC();
    }

    @Override
    public synchronized void insertFeiYongZC(FeiYongZC feiYongZC) {
        if (feiYongZCDao == null) {
            return;
        }

        feiYongZCDao.insertFeiYongZC(feiYongZC);
    }

    @Override
    public synchronized boolean existJianhao(int id) {
        if (jianHaoDao == null) {
            return false;
        }

        return jianHaoDao.existJianhao(id);
    }

    @Override
    public synchronized void deleteJianHao() {
        if (jianHaoDao == null) {
            return;
        }

        jianHaoDao.deleteJianHao();
    }

    @Override
    public synchronized void insertJianHao(JianHao Jianhao) {
        if (jianHaoDao == null) {
            return;
        }

        jianHaoDao.insertJianHao(Jianhao);
    }

    @Override
    public synchronized boolean existJianHaoMX(int id) {
        if (jianhaoMXDao == null) {
            return false;
        }

        return jianhaoMXDao.existJianHaoMX(id);
    }

    @Override
    public synchronized void deleteJianHaoMX() {
        if (jianhaoMXDao == null) {
            return;
        }

        jianhaoMXDao.deleteJianHaoMX();
    }

    @Override
    public synchronized void insertJianHaoMX(JianHaoMX jianHaoMX) {
        if (jianhaoMXDao == null) {
            return;
        }

        jianhaoMXDao.insertJianHaoMX(jianHaoMX);
    }

    @Override
    public synchronized boolean insertBiaoKaXX(BIAOKAXX biaoKaXX) {
        if (biaokaxxDao == null) {
            return false;
        }

        return biaokaxxDao.insertData(biaoKaXX);
    }

    @Override
    public synchronized void insertOrUpdateBiaoKaXXList(List<BIAOKAXX> biaoKaXXList) {
        if (biaokaxxDao == null) {
            return;
        }

        biaokaxxDao.insertOrUpdate(biaoKaXXList);
    }

    @Override
    public synchronized long getGongGaoFaBuSJ() {
        if (gongGaoXXDao == null) {
            return 0;
        }

        return gongGaoXXDao.getGongGaoSJ();
    }

    @Override
    public synchronized int getGongGaoXXUnreadCount() {
        if (gongGaoXXDao == null) {
            return 0;
        }

        return gongGaoXXDao.getGongGaoXXUnreadCount();
    }

    @Override
    public synchronized void updateCongGaoXXYD(int gongGaoBH) {
        if (gongGaoXXDao == null) {
            return;
        }

        gongGaoXXDao.updateCongGaoXXYD(gongGaoBH);
    }

    @Override
    public synchronized double getFeiYongZKL(String S_CID, String S_JH, int I_FeiYongDLBH) {
        if (feiYongZKLDao == null) {
            return 0;
        }

        return feiYongZKLDao.getFeiYongZKL(S_CID, S_JH, I_FeiYongDLBH);
    }

    @Override
    public synchronized void insertChaoBiaoSJ(ChaoBiaoSJ chaoBiaoSJ) {
        if (chaoBiaoSJDao == null) {
            return;
        }

        chaoBiaoSJDao.insertChaoBiaoSJ(chaoBiaoSJ);
    }

    @Override
    public synchronized void insertChaoBiaoSJList(List<ChaoBiaoSJ> chaoBiaoSJList, boolean needDeletingFirstly) {
        if (chaoBiaoSJDao == null) {
            return;
        }

        chaoBiaoSJDao.insertChaoBiaoSJList(chaoBiaoSJList, needDeletingFirstly);
    }

    @Override
    public synchronized void updateChaoBiaoSJ(ChaoBiaoSJ chaoBiaoSJ) {
        if (chaoBiaoSJDao == null) {
            return;
        }

        chaoBiaoSJDao.updateChaoBiaoSJ(chaoBiaoSJ);
    }

    @Override
    public synchronized void updateChaoBiaoSJList(List<ChaoBiaoSJ> chaoBiaoSJList) {
        if (chaoBiaoSJDao == null) {
            return;
        }

        chaoBiaoSJDao.updateChaoBiaoSJList(chaoBiaoSJList);
    }

    @Override
    public synchronized void deleteChaoBiaoSJ(int renWuBH, String chaoBiaoYuan) {
        if (chaoBiaoSJDao == null) {
            return;
        }

        chaoBiaoSJDao.deleteChaoBiaoSJ(renWuBH, chaoBiaoYuan);
    }

    @Override
    public synchronized boolean deleteChaoBiaoSJ(String account, int renWuBH, String ch) {
        if (chaoBiaoSJDao == null) {
            return false;
        }

//        if (qianFeiXXDao != null){
//            qianFeiXXDao.deleteQianFeiXXs(ch);
//        }
//
//        List<ChaoBiaoSJ> list = chaoBiaoSJDao.getVolumnChaoBiaoSJ(account, renWuBH, ch);
//        if (list != null && list.size() > 0){
//            List<String> allCids = new ArrayList<>();
//            for (ChaoBiaoSJ chaoBiaoSJ : list){
//                allCids.add(chaoBiaoSJ.getS_CID());
//            }
//
//            int length = allCids.size() / MAX_IN_SIZE + 1;
//            int number;
//            List<String> cids;
//            if (chaoBiaoJLDao != null){
//                number = 0;
//                while (number < length){
//                    cids = allCids.subList(number * MAX_IN_SIZE,
//                            number + 1 == length ? allCids.size() : (number + 1) * MAX_IN_SIZE);
//                    chaoBiaoJLDao.deleteChaoBiaoJL(cids);
//                    number++;
//                }
//            }
//
//            if (huanBiaoJLDao != null){
//                number = 0;
//                while (number < length){
//                    cids = allCids.subList(number * MAX_IN_SIZE,
//                            number + 1 == length ? allCids.size() : (number + 1) * MAX_IN_SIZE);
//                    huanBiaoJLDao.deleteChaoBiaoJL(cids);
//                    number++;
//                }
//            }
//
//            if (jiaoFeiXXDao != null){
//                number = 0;
//                while (number < length){
//                    cids = allCids.subList(number * MAX_IN_SIZE,
//                            number + 1 == length ? allCids.size() : (number + 1) * MAX_IN_SIZE);
//                    jiaoFeiXXDao.deleteJiaoFeiXX(cids);
//                    number++;
//                }
//            }
//        }

        return chaoBiaoSJDao.deleteChaoBiaoSJ(account, renWuBH, ch);
    }

    @Override
    public synchronized boolean deleteChaoBiaoSJ(String account, int renWuBH, int groupId) {
        if (chaoBiaoSJDao == null) {
            return false;
        }

        return chaoBiaoSJDao.deleteChaoBiaoSJ(account, renWuBH, groupId);
    }

    @Override
    public synchronized boolean clearChaoBiaoRW(String chaoBiaoYBH, int renWuBH) {
        if (chaoBiaoRWDao == null) {
            return false;
        }

        return chaoBiaoRWDao.clear(chaoBiaoYBH, renWuBH);
    }

    @Override
    public synchronized boolean updateChaoBiaoRW(ChaoBiaoRW chaoBiaoRW) {
        if (chaoBiaoRWDao == null) {
            return false;
        }

        return chaoBiaoRWDao.updateChaoBiaoRW(chaoBiaoRW);
    }

    @Override
    public synchronized boolean updateChaoBiaoRW(String account, String ch, int renwuBH, int finishednum, int flag) {
        if (chaoBiaoRWDao == null) {
            return false;
        }

        return chaoBiaoRWDao.updateChaoBiaoRW(account, ch, renwuBH, finishednum, flag);
    }

    @Override
    public synchronized boolean updateChaoBiaoRW(String account, int renwuBH, String ch, boolean needSync) {
        if (chaoBiaoRWDao == null) {
            return false;
        }

        return chaoBiaoRWDao.updateChaoBiaoRW(account, renwuBH, ch, needSync);
    }

    @Override
    public synchronized boolean updateChaoBiaoRW(String account, int renwuBH, boolean needSync, int typeRM) {
        if (chaoBiaoRWDao == null) {
            return false;
        }

        return chaoBiaoRWDao.updateChaoBiaoRW(account, renwuBH, needSync, typeRM);
    }

    @Override
    public synchronized boolean updateChaoBiaoRW(String account, int renwuBH, String ch, int yichaos) {
        if (chaoBiaoRWDao == null) {
            return false;
        }

        return chaoBiaoRWDao.updateChaoBiaoRW(account, renwuBH, ch, yichaos);
    }

    @Override
    public synchronized boolean insertChaoBiaoRW(ChaoBiaoRW chaoBiaoRW) {
        if (chaoBiaoRWDao == null) {
            return false;
        }

        return chaoBiaoRWDao.insertData(chaoBiaoRW);
    }

    @Override
    public synchronized void delectChaoBiaoRW(String chaoBiaoYBH, String renWuBHAll) {
        if (chaoBiaoRWDao == null) {
            return;
        }

        chaoBiaoRWDao.delectChaoBiaoRW(chaoBiaoYBH, renWuBHAll);
    }

    @Override
    public synchronized String selectNonentityChaoBiaoRW(String chaoBiaoYBH, String renWuBHAll) {
        if (chaoBiaoRWDao == null) {
            return null;
        }

        return chaoBiaoRWDao.selectNonentityChaoBiaoRW(chaoBiaoYBH, renWuBHAll);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getChaoBiaoSJ(int chaoBiaoBZ, int shangChuanBZ, String chaobiaoRWAll) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getChaoBiaoSJ(chaoBiaoBZ, shangChuanBZ, chaobiaoRWAll);
    }

    @Override
    public synchronized void delectChaoBiaoSJ(String renWuBHAll, String chaoBiaoYBH) {
        if (chaoBiaoSJDao == null) {
            return;
        }

        chaoBiaoSJDao.delectChaoBiaoSJ(renWuBHAll, chaoBiaoYBH);
    }

    @Override
    public synchronized String getChaoBiaoRenWuBH(String Account) {
        return null;
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getChaoBiaoSJRenWuBH(int chaoBiaoBZ, int shangChuanBZ, int renWuBH, int flag) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getChaoBiaoSJRenWuBH(chaoBiaoBZ,
                shangChuanBZ, renWuBH, flag);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getChaoBiaoSJCBY(String chaoBiaoY,
                                                          int renWuBH,
                                                          String ch,
                                                          int chaoBiaoBZ,
                                                          int shangChuanBZ,
                                                          Integer[] chaoBiaoSJType) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getChaoBiaoSJCBY(chaoBiaoY, renWuBH, ch, chaoBiaoBZ, shangChuanBZ, chaoBiaoSJType);
    }

    @Override
    public List<ChaoBiaoSJ> getNotUploadChaoBiaoSJ(String chaoBiaoY, int renWuBH, String ch) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getNotUploadChaoBiaoSJ(chaoBiaoY, renWuBH, ch);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getAllChaoBiaoSJCBY(String chaoBiaoY, int renWuBH, String ch, int chaoBiaoBZ) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getAllChaoBiaoSJCBY(chaoBiaoY, renWuBH, ch, chaoBiaoBZ);
    }

    @Override
    public synchronized List<Integer> getCeNetXH(String ch, String account) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getCeNetXH(ch, account);
    }

    @Override
    public synchronized void UpdateShangChuanBIaoZhi(int taskId, String cH, String cid, int ShangChuanBZ) {
        if (chaoBiaoSJDao == null) {
            return;
        }

        chaoBiaoSJDao.UpdateShangChuanBIaoZhi(taskId, cH, cid, ShangChuanBZ);
    }

    @Override
    public synchronized boolean delectGongGaoXX(int gongGaoBH) {
        if (gongGaoXXDao == null) {
            return false;
        }

        return gongGaoXXDao.delectGongGaoXX(gongGaoBH);
    }

    @Override
    public synchronized List<String> getChaoBiaoSJCID(String ChaoBiaoYBH, String renWuBHAll) {
        return null;
    }

    @Override
    public synchronized void deleteBiaoKaXXs(String ch) {
        if (biaokaxxDao == null) {
            return;
        }

        biaokaxxDao.deleteBiaoKaXXs(ch);
    }

    @Override
    public void deleteBiaoKaXXs(List<String> chs) {
        if (biaokaxxDao == null) {
            return;
        }

        biaokaxxDao.deleteBiaoKaXXs(chs);
    }

    @Override
    public synchronized void deleteBiaoKaXXs(int groupId) {
        if (biaokaxxDao == null) {
            return;
        }

        biaokaxxDao.deleteBiaoKaXXs(groupId);
    }

    @Override
    public synchronized void deleteBiaoKaXX(String cid) {
        if (biaokaxxDao == null) {
            return;
        }

        biaokaxxDao.deleteBiaoKaXX(cid);
    }

    @Override
    public synchronized void delectQianFeiXX(String cidAll) {
        if (qianFeiXXDao == null) {
            return;
        }

        qianFeiXXDao.delectQianFeiXX(cidAll);
    }

    @Override
    public synchronized void delectJiaoFeiXX(String cidAll) {
        if (jiaoFeiXXDao == null) {
            return;
        }

        jiaoFeiXXDao.delectJiaoFeiXX(cidAll);
    }

    @Override
    public synchronized void delectHuanBiaoJL(String cidAll) {
        delectHuanBiaoJL(cidAll);
    }

    @Override
    public synchronized boolean insertFeiYongZKL(FeiYongZKL feiYongZKL) {
        return feiYongZKLDao != null && feiYongZKLDao.insertFeiYongZKL(feiYongZKL);
    }

    @Override
    public synchronized void insertFeiYongZKLList(List<FeiYongZKL> feiYongZKLList) {
        if (feiYongZKLDao == null) {
            return;
        }

        feiYongZKLDao.insertFeiYongZKLList(feiYongZKLList);
    }

    @Override
    public synchronized void delectFeiYongZKL(String cidAll) {
        if (feiYongZKLDao == null) {
            return;
        }

        feiYongZKLDao.delectFeiYongZKL(cidAll);
    }

    @Override
    public synchronized void clearFeiYongZKL(String S_CID, String jianHao, int feiYongDLBH) {
        if (feiYongZKLDao == null) {
            return;
        }

        feiYongZKLDao.clearFeiYongZKL(S_CID, jianHao, feiYongDLBH);
    }

    @Override
    public synchronized int getBiaoKaXXCount(String S_CH) {
        if (biaokaxxDao == null) {
            return 0;
        }

        return biaokaxxDao.getBiaoKaCount(S_CH);
    }

    @Override
    public synchronized FeiYongZKL getFeiYongZKL(String S_CID, int I_FeiYongDLBH, String S_JH) {
        if (feiYongZKLDao == null) {
            return null;
        }

        return feiYongZKLDao.getFeiYongZKL(S_CID, I_FeiYongDLBH, S_JH);
    }

    @Override
    public synchronized List<BiaoWuGD> getBiaoWuGDList(String caoZuoR) {
        if (biaoWuDGDao == null) {
            return null;
        }

        return biaoWuDGDao.getBiaoWuGDList(caoZuoR);
    }

    @Override
    public synchronized void deleteBiaoWuGD() {
        if (biaoWuDGDao == null) {
            return;
        }

        biaoWuDGDao.deleteBiaoWuGD();
    }

    @Override
    public synchronized void deleteBiaoWuGDByBH(int renwubh) {
        if (biaoWuDGDao == null) {
            return;
        }

        biaoWuDGDao.deleteBiaoWuGDByBH(renwubh);
    }

    @Override
    public synchronized void insertBiaoWuGD(BiaoWuGD gd) {
        if (biaoWuDGDao == null) {
            return;
        }

        biaoWuDGDao.insertBiaoWuGD(gd);
    }

    @Override
    public synchronized BiaoWuGD getBiaoWuGD(int renWuBH) {
        if (biaoWuDGDao == null) {
            return null;
        }

        return biaoWuDGDao.getBiaoWuGD(renWuBH);
    }

    @Override
    public synchronized boolean insertYanChiBiao(YanChiBiao yanChiBiao) {
        if (yanChiBiaoDao == null) {
            return false;
        }

        return yanChiBiaoDao.insertData(yanChiBiao);
    }

    @Override
    public void insertYanChiBiaoList(List<YanChiBiao> yanChiBiaos) {
        if (yanChiBiaoDao == null) {
            return;
        }

        if (yanChiBiaos == null){
            return;
        }

        List<YanChiBiao> existYanChis = yanChiBiaoDao.queryBuilder().list();
        if (existYanChis != null){
            List<String> deleteChs = new ArrayList<>();
            String ch;
            if (yanChiBiaos.size() > 0){
                for (YanChiBiao existYanChi : existYanChis){
                    ch = existYanChi.getS_CH();
                    if (!isExistCh(ch, yanChiBiaos) && !deleteChs.contains(ch)){
                        deleteChs.add(ch);
                    }
                }
            }else {
                for (YanChiBiao existYanChi : existYanChis){
                    ch = existYanChi.getS_CH();
                    if (!deleteChs.contains(ch)){
                        deleteChs.add(ch);
                    }
                }
            }

            biaokaxxDao.deleteBiaoKaXXs(deleteChs);
            qianFeiXXDao.deleteQianFeiXXs(deleteChs);
        }

        QueryBuilder<YanChiBiao> queryBuilder;
        List<String> cids = new ArrayList<>();
        if (yanChiBiaos.size() < DataProviderImpl.MAX_IN_SIZE){
            for (YanChiBiao yanChiBiao : yanChiBiaos){
                cids.add(yanChiBiao.getS_CID());
            }
            yanChiBiaoDao.queryBuilder().where(YanChiBiaoDao.Properties.S_CID.notIn(cids)).buildDelete()
                    .executeDeleteWithoutDetachingEntities();
        }else {
            List<YanChiBiao> all = yanChiBiaoDao.queryBuilder().list();
            for (YanChiBiao exist : all){
                if (!isExist(exist.getS_CID(), yanChiBiaos)){
                    yanChiBiaoDao.queryBuilder().where(YanChiBiaoDao.Properties.S_CID.eq(exist.getS_CID()))
                            .buildDelete().executeDeleteWithoutDetachingEntities();
                }
            }
        }

        List<YanChiBiao> list = new ArrayList<>();
        for (YanChiBiao yanChiBiao : yanChiBiaos){
            queryBuilder = yanChiBiaoDao.queryBuilder()
                    .where(YanChiBiaoDao.Properties.S_CID.eq(yanChiBiao.getS_CID()));
            if (queryBuilder.count() == 0){
                yanChiBiao.setID(-1L);
                list.add(yanChiBiao);
            }
        }

        if (list.size() > 0){
            yanChiBiaoDao.insertInTx(list);
        }
    }

    @Override
    public void updateYanChiBiaoList(List<YanChiBiao> list) {
        if (yanChiBiaoDao == null){
            return;
        }

        yanChiBiaoDao.updateYanChiBiaoList(list);
    }

    @Override
    public synchronized List<YanChiBiao> getYanChiBiaoList(String chaoBiaoYId, int yanChiLX, int sortType) {
        if (yanChiBiaoDao == null) {
            return null;
        }

        return yanChiBiaoDao.getList(chaoBiaoYId, yanChiLX, sortType);
    }

    @Override
    public synchronized void deleteRenWuXX(String account) {
        if (renwuXXDao == null) {
            return;
        }

        renwuXXDao.deleteRenWuXX(account);
    }

    @Override
    public synchronized void deleteRenWuXXByBH(int renwubh) {
        if (renwuXXDao == null) {
            return;
        }

        renwuXXDao.deleteRenWuXXByBH(renwubh);
    }

    @Override

    public synchronized void deleteNotExistBiaoWuGD(String account, String AllRenWuBH) {
        if (biaoWuDGDao == null) {
            return;
        }

        biaoWuDGDao.deleteNotExistBiaoWuGD(account, AllRenWuBH);
    }

    @Override
    public synchronized void insertRenWuXX(RenwuXX rw) {
        if (renwuXXDao == null) {
            return;
        }

        renwuXXDao.insertRenWuXX(rw);
    }

    @Override
    public synchronized List<BiaoWuGD> getWeiShangChuanBiaoWuGD(String account) {
        if (biaoWuDGDao == null) {
            return null;
        }

        return biaoWuDGDao.getWeiShangChuanBiaoWuGD(account);
    }

    @Override
    public synchronized void UpdateBiaoWuGD(BiaoWuGD gd) {
        if (biaoWuDGDao == null) {
            return;
        }

        biaoWuDGDao.insertAndUpdateBiaoWuGD(gd);
    }

    @Override
    public synchronized void updateBiaoWuShangChuanZT(String account, BiaoWuGD gd) {
        if (biaoWuDGDao == null) {
            return;
        }

        biaoWuDGDao.updateShangChuanZT(account, gd);
    }

    @Override
    public synchronized boolean updateBiaoWuGDDate(BiaoWuGD gd) {
        if (biaoWuDGDao == null) {
            return false;
        }

        return biaoWuDGDao.updateBiaoWuGDDate(gd);
    }

    @Override
    public synchronized String getAllRenWuXXRenWuBH(String account) {
        if (renwuXXDao == null) {
            return null;
        }

        return renwuXXDao.getAllRenWuXXRenWuBH(account);
    }

    @Override
    public synchronized String getAllRenWuXXYanChiBH(String account) {
        if (renwuXXDao == null) {
            return null;
        }

        return renwuXXDao.getAllRenWuXXYanChiBH(account);
    }

    @Override
    public synchronized String selectNonentityBiaoWuGDBH(String accoount, String renWuBHAll) {
        if (biaoWuDGDao == null) {
            return null;
        }

        return biaoWuDGDao.selectNonentityBiaoWuGDBH(accoount, renWuBHAll);
    }

    @Override
    public synchronized void deleteNotExistYanChiXX(String account, String AllYanChiBH) {
        if (yanChiBiaoDao == null) {
            return;
        }

        yanChiBiaoDao.deleteNotExistYanChiXX(account, AllYanChiBH);
    }

    @Override
    public synchronized String selectNonentityYanChiBH(String accoount, String yanChiBHAll) {
        if (yanChiBiaoDao == null) {
            return null;
        }

        return yanChiBiaoDao.selectNonentityYanChiBH(accoount, yanChiBHAll);
    }

    @Override
    public synchronized int getBiaoWuGDCount(String account) {
        if (biaoWuDGDao == null) {
            return 0;
        }

        return biaoWuDGDao.getBiaoWuGDCount(account);
    }

    @Override
    public synchronized void updateImgPath(int renWuBH, String imgPath) {
        if (biaoWuDGDao == null) {
            return;
        }

        biaoWuDGDao.updateImgPath(renWuBH, imgPath);
    }

    @Override
    public synchronized String getBiaoWuImgName(int renWuBH) {
        if (biaoWuDGDao == null) {
            return null;
        }

        return biaoWuDGDao.getBiaoWuImgName(renWuBH);
    }

    @Override
    public synchronized void updateSignaturePath(int renWuBH, String signaturePath) {
        if (biaoWuDGDao == null) {
            return;
        }

        biaoWuDGDao.updateSignaturePath(renWuBH, signaturePath);
    }

    @Override
    public synchronized List<BiaoWuGD> getBiaoWuGDWeiChaoList(String account) {
        if (biaoWuDGDao == null) {
            return null;
        }

        return biaoWuDGDao.getBiaoWuGDWeiChaoList(account);
    }

    @Override
    public synchronized boolean updateYanChiSJ(String S_CID, int I_ZhuangTaiBM,
                                               String S_ZhuangTaiMC, int I_BenCiCM, int I_ChaoJianSL,
                                               double N_JE, String S_CH, int I_LiangGaoLDYYBM,
                                               String S_ChaoBiaoBeiZhu, int chaoBiaoBiaoZhi, String jieTiTS) {
        if (yanChiBiaoDao == null) {
            return false;
        }

        return yanChiBiaoDao.updateYanChiSJs(S_CID, I_ZhuangTaiBM,
                S_ZhuangTaiMC, I_BenCiCM, I_ChaoJianSL, N_JE, S_CH,
                I_LiangGaoLDYYBM, S_ChaoBiaoBeiZhu, chaoBiaoBiaoZhi, jieTiTS);
    }

    @Override
    public YanChiBiao getYanChiBiaoXX(int taskId, String cH, String S_CID) {
        if (yanChiBiaoDao == null){
            return null;
        }

        return yanChiBiaoDao.getYanChiBiaoXX(taskId, cH, S_CID);
    }

    @Override
    public synchronized String getBiaoKaXXCIDS(String CIDS) {
        if (biaokaxxDao == null) {
            return null;
        }

        return biaokaxxDao.getBiaoKaXXCIDS(CIDS);
    }

    @Override
    public synchronized List<String> getBiaoKaXXCIDS(String str, String s_ch) {
        if (biaokaxxDao == null) {
            return null;
        }

        return biaokaxxDao.getBiaoKaXXCIDS(str, s_ch);
    }

    @Override
    public synchronized List<String> getBiaoKaXXCIDS(String str, int groupId) {
        if (biaokaxxDao == null) {
            return null;
        }

        return biaokaxxDao.getBiaoKaXXCIDS(str, groupId);
    }

    @Override
    public synchronized boolean updateYanChiBZ(String S_CID, int yanChiLX, String S_CH, String chaobiaobeizhu) {
        if (chaoBiaoSJDao == null) {
            return false;
        }

        return chaoBiaoSJDao.updateYanChiBZ(S_CID, yanChiLX, S_CH,
                chaobiaobeizhu);
    }

    @Override
    public synchronized boolean updateYanChiShangChuanBZ(String S_CID, int shangchuanBZ) {
        if (yanChiBiaoDao == null) {
            return false;
        }

        return yanChiBiaoDao.updateYanChiShangChuanBZ(S_CID, shangchuanBZ);
    }

    @Override
    public synchronized boolean updateYanChiSJ(YanChiBiao yanChiBiao) {
        if (yanChiBiaoDao == null) {
            return false;
        }

        return yanChiBiaoDao.updateYanChiSJ(yanChiBiao);
    }

    @Override
    public synchronized List<YanChiBiao> getWeiShangChuanYanChiBiaoList(String account, int type) {
        if (yanChiBiaoDao == null) {
            return null;
        }

        return yanChiBiaoDao.getWeiShangChuanYanChiBiaoList(account, type);
    }

    @Override
    public synchronized YanChiBiao getYanChiByCHAndCeNeiPX(String S_CH, Integer I_CeNeiPX) {
        if (yanChiBiaoDao == null) {
            return null;
        }

        return yanChiBiaoDao.getYanChiByCHAndCeNeiPX(S_CH, I_CeNeiPX);
    }

    @Override
    public synchronized boolean insertWaiFuGDMain(WaiFuGDMain waiFuGDMain) {
        if (waiFuGDMainDao == null) {
            return false;
        }

        return waiFuGDMainDao.insertData(waiFuGDMain);
    }

    @Override
    public synchronized List<WaiFuGDMain> getWaiFuGDMainList(String account) {
        if (waiFuGDMainDao == null) {
            return null;
        }

        return waiFuGDMainDao.getWaiFuGDMainList(account);
    }

    @Override
    public synchronized WaiFuGDMain getWaiFuGDMain(int renWuBH) {
        if (waiFuGDMainDao == null) {
            return null;
        }

        return waiFuGDMainDao.getWaiFuGDMain(renWuBH);
    }

    @Override
    public synchronized String getAllRenWuXXWaiFuGDBH(String account) {
        if (renwuXXDao == null) {
            return null;
        }

        return renwuXXDao.getAllRenWuXXWaiFuGDBH(account);
    }

    @Override
    public synchronized void deleteNotExistWaiFuGD(String account, String AllRenWuBH) {
        if (waiFuGDMainDao == null) {
            return;
        }

        waiFuGDMainDao.deleteNotExistWaiFuGD(account, AllRenWuBH);
    }

    @Override
    public synchronized String selectNonentityWaiFuGDBH(String accoount, String renWuBHAll) {
        if (waiFuGDMainDao == null) {
            return null;
        }

        return waiFuGDMainDao.selectNonentityWaiFuGDBH(accoount, renWuBHAll);
    }

    @Override
    public synchronized List<WaiFuGDMain> getWeiShangChuanWaiFuGDMainList(String account) {
        if (waiFuGDMainDao == null) {
            return null;
        }

        return waiFuGDMainDao.getWeiShangChuanWaiFuGDMainList(account);
    }

    @Override
    public synchronized List<WaiFuGDDetail> getupdateWaiFuGDDetailList(String renwuBHs) {
        return null;
    }

    @Override
    public synchronized void deleteWaiFuGDDetailByBH(int renwubh) {
        if (waiFuGDDetailDao == null) {
            return;
        }

        waiFuGDDetailDao.deleteWaiFuGDDetailByBH(renwubh);
    }

    @Override
    public synchronized int getWeiShangChuanYanChiCount(String account) {
        if (yanChiBiaoDao == null) {
            return 0;
        }

        return yanChiBiaoDao.getWeiShangChuanYanChiCount(account);
    }

    @Override
    public synchronized int getWeiShangChuanWaiFuGDCount(String account) {
        if (waiFuGDMainDao == null) {
            return 0;
        }

        return waiFuGDMainDao.getWeiShangChuanWaiFuGDCount(account);
    }

    @Override
    public synchronized List<WaiFuGDMain> getWaiFuGDMainWeiWanChengList(String account) {
        if (waiFuGDMainDao == null) {
            return null;
        }

        return waiFuGDMainDao.getWaiFuGDMainWeiWanChengList(account);
    }

    @Override
    public synchronized void insertUsers(Users user) {
        if (userDao == null) {
            return;
        }

        userDao.insertUsers(user);
    }

    @Override
    public synchronized List<Users> getUserList(String account, String station, int type) {
        if (userDao == null) {
            return null;
        }

        return userDao.getUserList(account, station, type);
    }

    @Override
    public synchronized void deleteUsers(String account, int type) {
        if (userDao == null) {
            return;
        }

        userDao.deleteUsers(account, type);
    }

    @Override
    public synchronized boolean updateWaiFuGDMain(int renWuBH, WaiFuGDMain waiFuGDMain, int type) {
        if (waiFuGDMainDao == null) {
            return false;
        }

        return waiFuGDMainDao.updateWaiFuGDMain(renWuBH, waiFuGDMain, type);
    }

    @Override
    public synchronized void deleteWaiFuGDMainByBH(int renwubh) {
        if (waiFuGDMainDao == null) {
            return;
        }

        waiFuGDMainDao.deleteWaiFuGDMainByBH(renwubh);
    }

    @Override
    public synchronized void updateWaiFuMainShangChuanZT(String account, WaiFuGDMain wfm) {
        if (waiFuGDMainDao == null) {
            return;
        }

        waiFuGDMainDao.updateWaiFuMainShangChuanZT(account, wfm);
    }

    @Override
    public synchronized boolean insertWaiFuGDDetailData(WaiFuGDDetail waiFuGDDetail) {
        if (waiFuGDDetailDao == null) {
            return false;
        }

        return waiFuGDDetailDao.insertData(waiFuGDDetail);
    }

    @Override
    public synchronized List<WaiFuGDDetail> getWaiFuGDDetailList(int renwuBH, boolean isAll) {
        if (waiFuGDDetailDao == null) {
            return null;
        }

        return waiFuGDDetailDao.getWaiFuGDDetailList(renwuBH, isAll);
    }

    @Override
    public synchronized void deleteNotExistWaiFuGDDetail(String account, String AllRenWuBH) {
        if (waiFuGDDetailDao == null) {
            return;
        }

        waiFuGDDetailDao.deleteNotExistWaiFuGDDetail(account, AllRenWuBH);
    }

    @Override
    public synchronized void deleteWaiFuGDDetail(String account, int renWuBH) {
        if (waiFuGDDetailDao == null) {
            return;
        }

        waiFuGDDetailDao.deleteWaiFuGDDetail(account, renWuBH);
    }

    @Override
    public synchronized boolean updateYuanChuanBZZHC(int renWubh, String cid, String fuChaJG, int tiaoJianSL) {
        if (waiFuGDDetailDao == null) {
            return false;
        }

        return waiFuGDDetailDao.updateYuanChuanBZZHC(renWubh, cid, fuChaJG,
                tiaoJianSL);
    }

    @Override
    public synchronized void updateBiaoWuGDChaoBiaoBZ(String account, int renWuBH) {
        if (biaoWuDGDao == null) {
            return;
        }

        biaoWuDGDao.updateBiaoWuGDChaoBiaoBZ(account, renWuBH);
    }

    @Override
    public synchronized boolean updateYingCangBZ(int renWubh, String cid) {
        if (waiFuGDDetailDao == null) {
            return false;
        }

        return waiFuGDDetailDao.updateYingCangBZ(renWubh, cid);
    }

    @Override
    public synchronized String getWaiFuMainImgName(int renWuBH) {
        if (waiFuGDMainDao == null) {
            return null;
        }

        return waiFuGDMainDao.getWaiFuMainImgName(renWuBH);
    }

    @Override
    public synchronized void updateImgPathToWaiFuMain(int renWuBH, String imgPath) {
        if (waiFuGDMainDao == null) {
            return;
        }

        waiFuGDMainDao.updateImgPathToWaiFuMain(renWuBH, imgPath);
    }

    @Override
    public synchronized void updateSignaturePathToWaiFuMain(int renWuBH, String signaturePath) {
        if (waiFuGDMainDao == null) {
            return;
        }

        waiFuGDMainDao.updateSignaturePathToWaiFuMain(renWuBH, signaturePath);
    }

    @Override
    public synchronized void UpdateWaiFuMainGD(WaiFuGDMain gd) {
        if (waiFuGDMainDao == null) {
            return;
        }

        waiFuGDMainDao.insertAndUpdateWaiFuMainGD(gd);
    }

    @Override
    public synchronized void deleteWaiFuGDDetail_one(String account, int renWuBH, String CID) {
        if (waiFuGDDetailDao == null) {
            return;
        }

        waiFuGDDetailDao.deleteWaiFuGDDetail_one(account, renWuBH, CID);
    }

    @Override
    public synchronized WaiFuGDDetail getWaiFuGDDetail(int renwuBH, String CID) {
        if (waiFuGDDetailDao == null) {
            return null;
        }

        return waiFuGDDetailDao.getWaiFuGDDetail(renwuBH, CID);
    }

    @Override
    public synchronized int getWeiWanChengYanChiCount(String account, int yanChiLX) {
        if (yanChiBiaoDao == null) {
            return 0;
        }

        return yanChiBiaoDao.getWeiWanChengYanChiCount(account, yanChiLX);
    }

    @Override
    public synchronized int getWeiWanChengYanChiCount(String account) {
        if (yanChiBiaoDao == null) {
            return 0;
        }

        return yanChiBiaoDao.getWeiWanChengYanChiCount(account);
    }

    @Override
    public synchronized int getWeiWanChengWFGDCount(String account) {
        if (waiFuGDMainDao == null) {
            return 0;
        }

        return waiFuGDMainDao.getWeiWanChengWFGDCount(account);
    }

    @Override
    public synchronized int getWeiWanChengBWGDCount(String account) {
        if (biaoWuDGDao == null) {
            return 0;
        }

        return biaoWuDGDao.getWeiWanChengBWGDCount(account);
    }

    @Override
    public synchronized boolean insertGuiJiData(List<GuiJi> guiJiList) {
        if (guiJiDao == null) {
            return false;
        }

        return guiJiDao.insertGuiJiData(guiJiList);
    }

    @Override
    public synchronized void deleteGuiJiData() {
        if (guiJiDao == null) {
            return;
        }

        guiJiDao.deleteGuiJiData();
    }

    @Override
    public synchronized List<GuiJi> getGuiJiData(String ch) {
        if (guiJiDao == null) {
            return null;
        }

        return guiJiDao.getGuiJiData(ch);
    }

    @Override
    public synchronized int Login(String mima, String account) {
        if (userInfoDao == null) {
            return 0;
        }

        return userInfoDao.Login(mima, account);
    }

    @Override
    public synchronized UserInfo getUserInfo(String account) {
        if (userInfoDao == null) {
            return null;
        }

        return userInfoDao.getUserInfo(account);
    }

    @Override
    public synchronized List<BIAOKAXX> getBiaoKaXXList(String ch) {
        if (biaokaxxDao == null) {
            return null;
        }

        return biaokaxxDao.getBiaoKaXXList(ch);
    }

    @Override
    public synchronized List<BIAOKAXX> getBiaoKaXXListByGroupId(int groupId) {
        if (biaokaxxDao == null) {
            return null;
        }

        return biaokaxxDao.getBiaoKaXXListByGroupId(groupId);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getNextChaoBiaoSJList(String ch) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getNextChaoBiaoSJList(ch);
    }

    @Override
    public YanChiBiao getNextYanChiCB(int renWuId, String ch, long ceNeiXH, boolean chaoBiaoBZ) {
        if (yanChiBiaoDao == null) {
            return null;
        }

        return yanChiBiaoDao.getNextYanChiCB(renWuId, ch, ceNeiXH, chaoBiaoBZ);
    }

    @Override
    public YanChiBiao getPreviousYanChiCB(int renWuId, String ch, long ceNeiXH, boolean chaoBiaoBZ) {
        if (yanChiBiaoDao == null) {
            return null;
        }

        return yanChiBiaoDao.getPreviousYanChiCB(renWuId, ch, ceNeiXH, chaoBiaoBZ);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getLiangGaoLiangDiChaoBiaoSJ(String account, int loadType) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getLiangGaoLiangDiChaoBiaoSJ(account, loadType);
    }

    @Override
    public synchronized List<BIAOKAXX> getBiaoKaXX() {
        if (biaokaxxDao == null) {
            return null;
        }

        return biaokaxxDao.getBiaoKaXX();
    }

    @Override
    public synchronized boolean insertChaoBiaoGJ(ChaoBiaoGJ chaoBiaoGJ) {
        if (chaoBiaoGJDao == null) {
            return false;
        }

        return chaoBiaoGJDao.insertChaoBiaoGJ(chaoBiaoGJ);
    }

    @Override
    public synchronized ChaoBiaoGJ getChaoBiaoGJ(String ch, String cid, int type) {
        if (chaoBiaoGJDao == null) {
            return null;
        }

        return chaoBiaoGJDao.getChaoBiaoGJ(ch, cid, type);
    }

    @Override
    public synchronized List<ChaoBiaoGJ> getChaoBiaoGJList(String ch, int type) {
        if (chaoBiaoGJDao == null) {
            return null;
        }

        return chaoBiaoGJDao.getChaoBiaoGJList(ch, type);
    }

    @Override
    public synchronized List<ChaoBiaoGJ> getChaoBiaoGJList(String account) {
        if (chaoBiaoGJDao == null) {
            return null;
        }

        return chaoBiaoGJDao.getChaoBiaoGJList(account);
    }

    @Override
    public synchronized List<ChaoBiaoGJ> getChaoBiaoGJList(String account, int renwuBH, String ch) {
        if (chaoBiaoGJDao == null) {
            return null;
        }

        return chaoBiaoGJDao.getChaoBiaoGJList(account, renwuBH, ch);
    }

    @Override
    public synchronized List<YanChiBiao> getLiangGaoLiangDiYanChiSJ(String account, int loadType) {
        if (yanChiBiaoDao == null) {
            return null;
        }

        return yanChiBiaoDao.getLiangGaoLiangDiYanChiSJ(account, loadType);
    }

    @Override
    public synchronized List<WordsInfo> getWordsInfoList() {
        if (wordsInfoDao == null) {
            return null;
        }

        return wordsInfoDao.getWordsInfoList();
    }

    @Override
    public synchronized void updateChaoBiaoBeiZhu(String s_cid, String beiZhu) {
        if (chaoBiaoSJDao == null) {
            return;
        }

        chaoBiaoSJDao.updateChaoBiaoBeiZhu(s_cid, beiZhu);
    }

    @Override
    public synchronized void updateYanChiBeiZhu(String s_cid, String beiZhu) {
        if (yanChiBiaoDao == null) {
            return;
        }

        yanChiBiaoDao.updateYanChiBeiZhu(s_cid, beiZhu);
    }

    @Override
    public synchronized List<DuoMeiTXX> getDuoMeiTXXList(String account, String cid, int type) {
        if (duoMeiTXXDao == null) {
            return null;
        }

        return duoMeiTXXDao.getDuoMeiTXXList(account, cid, type);
    }

    @Override
    public synchronized List<DuoMeiTXX> getDuoMeiTXXList(String account, String cid, String ch, int renwuBH, int type) {
        if (duoMeiTXXDao == null) {
            return null;
        }

        return duoMeiTXXDao.getDuoMeiTXXList(account, cid, ch, renwuBH, type);
    }

    @Override
    public synchronized boolean updateDuoMeiTXX(DuoMeiTXX duoMeiTXX) {
        if (duoMeiTXXDao == null) {
            return false;
        }

        return duoMeiTXXDao.updateDuoMeiTXX(duoMeiTXX);
    }

    @Override
    public synchronized boolean updateBiaoKaXXPhoneNum(String CID, String phoneNum, String type) {
        if (biaokaxxDao == null) {
            return false;
        }

        return biaokaxxDao.updateBiaoKaXXPhoneNum(CID, phoneNum, type);
    }

    @Override
    public synchronized boolean IsExistencePhone(String cid) {
        if (duoMeiTXXDao == null) {
            return false;
        }

        return duoMeiTXXDao.IsExistencePhone(cid);
    }

    @Override
    public synchronized boolean updateChaoMa(int benCiCM, String CID) {
        return false;
    }

    @Override
    public synchronized boolean updateChaoBiaoSjYanChiYY(String cid, String yanChiYY) {
        if (chaoBiaoSJDao == null) {
            return false;
        }

        return chaoBiaoSJDao.updateChaoBiaoSjYanChiYY(cid, yanChiYY);
    }

    @Override
    public synchronized boolean updateBiaoKaXXYanChiDialog(String cid, String kouJing, String biaoHao, long huanBiaoRQ) {
        if (biaokaxxDao == null) {
            return false;
        }

        return biaokaxxDao.updateBiaoKaXXYanChiDialog(cid, kouJing, biaoHao,
                huanBiaoRQ);
    }

    @Override
    public synchronized List<GongDan> getGongDanXXList(String account, int type) {
        return null;
    }

    @Override
    public synchronized GongDan getGongDanXX(String gongdanbh) {
        return null;
    }

    @Override
    public synchronized GongDan getPrevGongDanXX(String gongdanbh) {
        return null;
    }

    @Override
    public synchronized GongDan getNextGongDanXX(String gongdanbh) {
        return null;
    }

    @Override
    public synchronized boolean updateGongDanXX(GongDan gongDan) {
        return false;
    }

    @Override
    public synchronized boolean insertGongDanChaoBiaoZT(GongDanCHAOBIAOZT gd_chaoBiaoZT) {
        return false;
//        return gongDanChaoBiaoDao.insertGongDanChaoBiaoZT(gd_chaoBiaoZT);
    }

    @Override
    public synchronized void deleteGongDanYONGHUPZ() {
//        gongDanYongHuPZDao.deleteGongDanYongHuPZ();
    }

    @Override
    public synchronized void deleteGongDanWords() {
//        gongDanWordsDao.deleteGongDanWords();
    }

    @Override
    public synchronized boolean insertGongDanWords(GongDanWORDS gd_word) {
        return false;
//        return gongDanWordsDao.insertGongDanWords(gd_word);
    }

    @Override
    public synchronized void deleteGongDanChaoBiaoZT() {
//        gongDanChaoBiaoDao.deleteGongDanChaoBiaoZT();
    }

    @Override
    public synchronized boolean insertGongDanYONGHUPZ(GongDanYONGHUPZ gd_YongHuPZ) {
        return false;
//        return gongDanYongHuPZDao.insertGongDanYONGHUPZ(gd_YongHuPZ);
    }

    @Override
    public synchronized List<GongDanCHAOBIAOZT> getGongDanChaoBiaoZTList() {
        return null;
    }

    @Override
    public synchronized List<GongDanWORDS> getGongDanWords(int belongId) {
        return null;
    }

    @Override
    public synchronized List<GongDanWORDS> getGongDanWords() {
        return null;
    }

    @Override
    public synchronized List<GongDanYONGHUPZ> getGongDanYongHuPZList() {
        return null;
    }

    @Override
    public synchronized List<ChaoBiaoBZ> getChaoBiaoBZList() {
        if (chaoBiaoBZDao == null) {
            return null;
        }

        return chaoBiaoBZDao.getChaoBiaoBZList();
    }

    @Override
    public synchronized List<GongDanYONGHUPZ> getGongDanYongHuPZList(int I_LISHUID) {
        return null;
    }

    @Override
    public synchronized List<GongDanYONGHUPZ> getGongDanYongHuPZList(int gangDantype, int ciyuLX, String CANSHUZ) {
        return null;
    }

    @Override
    public synchronized List<BIAOKAXX> getCombinedQuery(ConditionInfo info) {
        if (biaokaxxDao == null) {
            return null;
        }

        return biaokaxxDao.getCombinedQuery(info);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getAllFinishedChaoBiaoSJ(int taskId, String volume, String account, int chaobiaobzYichao) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getAllFinishedChaoBiaoSJ(taskId, volume, account, chaobiaobzYichao);
    }

    @Override
    public synchronized List<DuoMeiTXX> getAllDuoMeiTXXList(String account, int renwubh, String ch) {
        if (duoMeiTXXDao == null) {
            return null;
        }

        return duoMeiTXXDao.getAllDuoMeiTXXList(account, renwubh, ch);
    }

    @Override
    public List<DuoMeiTXX> getUploadDuoMeiTXXList() {
        if (duoMeiTXXDao == null) {
            return null;
        }

        return duoMeiTXXDao.getUploadDuoMeiTXXList();
    }

    @Override
    public synchronized List<DuoMeiTXX> getAllDuoMeiTXXList(String account, int renwubh) {
        if (duoMeiTXXDao == null) {
            return null;
        }

        return duoMeiTXXDao.getAllDuoMeiTXXList(account, renwubh);
    }

    @Override
    public synchronized List<BIAOKAXX> getAllBiaoKaXXList() {
        if (biaokaxxDao == null) {
            return null;
        }

        return biaokaxxDao.getAllBiaoKaXXList();
    }

    @Override
    public List<BIAOKAXX> getBiaoKaXXListByTaskId(int taskId) {
        if (biaokaxxDao == null) {
            return null;
        }

        return biaokaxxDao.getBiaoKaXXListByTaskId(taskId);
    }

    @Override
    public List<BIAOKAXX> getDelayBiaoKaXXList(String account) {
        if (biaokaxxDao == null) {
            return null;
        }

        return biaokaxxDao.getDelayBiaoKaXXList(account);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getAllChaoBiaoSJ(String account) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getAllChaoBiaoSJ(account);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getAllChaoBiaoSJ(String account, int taskId, String volume) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getAllChaoBiaoSJ(account, taskId, volume);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getAllChaoBiaoSJ(String account, int taskId) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getAllChaoBiaoSJ(account, taskId);
    }

    @Override
    public synchronized boolean deleteRecords(String account, int taskId, String volume, String cid) {
        if (chaoBiaoSJDao == null) {
            return false;
        }

        return chaoBiaoSJDao.deleteRecords(account, taskId, volume, cid);
    }

    @Override
    public synchronized List<DuoMeiTXX> getAllDuoMeiTXXList(String account, String taskIdsArry) {
        if (duoMeiTXXDao == null) {
            return null;
        }

        return duoMeiTXXDao.getAllDuoMeiTXXList(account, taskIdsArry);
    }

    @Override
    public synchronized boolean deleteDuoMeiTXX(String account, List<String> mediaList) {
        if (duoMeiTXXDao == null) {
            return false;
        }

        return duoMeiTXXDao.deleteDuoMeiTXX(account, mediaList);
    }

    @Override
    public synchronized boolean deleteDuoMeiTXX(String account, int renwubh, String ch) {
        if (duoMeiTXXDao == null) {
            return false;
        }

        return duoMeiTXXDao.deleteDuoMeiTXX(account, renwubh, ch);
    }

    @Override
    public synchronized boolean deleteDuoMeiTXX(String account, int renwubh) {
        if (duoMeiTXXDao == null) {
            return false;
        }

        return duoMeiTXXDao.deleteDuoMeiTXX(account, renwubh);
    }

    @Override
    public synchronized void deleteCHaoBiaoRW(String taskId, String account) {
        if (chaoBiaoRWDao == null) {
            return;
        }

        chaoBiaoRWDao.delectChaoBiaoRW(account, taskId);
    }

    @Override
    public synchronized boolean deleteChaoBiaoRW(String account, int taskId, String ch) {
        if (chaoBiaoRWDao == null) {
            return false;
        }

        return chaoBiaoRWDao.deleteChaoBiaoRW(account, taskId, ch);
    }

    @Override
    public synchronized boolean deleteChaoBiaoRW(String account, int taskId, int groupId) {
        if (chaoBiaoRWDao == null) {
            return false;
        }

        return chaoBiaoRWDao.deleteChaoBiaoRW(account, taskId, groupId);
    }

    @Override
    public synchronized void deleteChaoBiaoSJByTaskId(String account, String taskIdsArr) {
        if (chaoBiaoSJDao == null) {
            return;
        }

        chaoBiaoSJDao.deleteChaoBiaoSJByTaskId(account, taskIdsArr);
    }

    @Override
    public synchronized List<ChaoBiaoRW> getRemovedChaoBiaoRW(String account, List<String> renWuBHList) {
        if (chaoBiaoRWDao == null) {
            return null;
        }

        return chaoBiaoRWDao.getRemovedChaoBiaoRW(account, renWuBHList);
    }

    @Override
    public List<DuoMeiTXX> getDeleteDelayDuoMeiTXX(List<String> list) {
        if (duoMeiTXXDao == null || list == null){
            return null;
        }

        return duoMeiTXXDao.getDelayDuoMeiTXXList(list);
    }

    @Override
    public void deleteDelayDuoMeiTXX(List<String> list) {
        if (duoMeiTXXDao == null || list == null){
            return;
        }

        duoMeiTXXDao.deleteDelayDuoMeiTXX(list);
    }

    @Override
    public boolean getDeleteDelayDuoMeiTXXAddYanChiBiao(String account, List<Integer> ids) {
        if (duoMeiTXXDao == null || yanChiBiaoDao == null){
            return false;
        }

        duoMeiTXXDao.deleteDuoMeiTXXByTaskId(account, ids);
        return true;
    }

    @Override
    public synchronized boolean deleteDuoMeiTXXByTaskId(String account, String taskIdsArry) {
        if (duoMeiTXXDao == null) {
            return false;
        }

        return duoMeiTXXDao.deleteDuoMeiTXXByTaskId(account, taskIdsArry);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getAllChaoBiaoSJWithLimit(String account, int taskId, String volume, long orderNumber) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getAllChaoBiaoSJWithLimit(account, taskId, volume, orderNumber);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getListChaoBiaoSJWithLimit(int taskId, String volume, int type, List<String> cIds, long orderNumber) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getListChaoBiaoSJWithLimit(taskId, volume, type, cIds, orderNumber);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getChaoBiaoSJListWithLimit(int taskId, String volume, List<String> userIds, long orderNumber) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getChaoBiaoSJListWithLimit(taskId, volume, userIds, orderNumber);
    }

    @Override
    public synchronized ChaoBiaoRW getChaoBiaoRWByS_CH(String s_ch) {
        if (chaoBiaoRWDao == null) {
            return null;
        }

        return chaoBiaoRWDao.getChaoBiaoRWByS_CH(s_ch);
    }

    @Override
    public synchronized ChaoBiaoSJ getChaoBiaoSJByID(int id) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getChaoBiaoSJByID(id);
    }

    @Override
    public synchronized List<ChaoBiaoSJ> getTemporaryChaoBiaoSJ(String account) {
        if (chaoBiaoSJDao == null) {
            return null;
        }

        return chaoBiaoSJDao.getTemporaryChaoBiaoSJ(account);
    }

    @Override
    public synchronized void deleteTemporaryChaoBiaoSJ(String account) {
        if (chaoBiaoSJDao == null) {
            return;
        }

        chaoBiaoSJDao.deleteTemporaryChaoBiaoSJ(account);
    }

    @Override
    public synchronized List<BIAOKAXX> getBiaoKaXXTemporaryList(String account) {
        if (biaokaxxDao == null) {
            return null;
        }

        return biaokaxxDao.getBiaoKaXXTemporaryList(account);
    }

    @Override
    public synchronized void deleteTemporaryBiaoKaXX(String account) {
        if (biaokaxxDao == null) {
            return;
        }

        biaokaxxDao.deleteTemporaryBiaoKaXX(account);
    }

    @Override
    public synchronized List<RushPayRW> getRushPayRWList(String account, int type) {
        if (rushPayRWDao == null) {
            return null;
        }
        return rushPayRWDao.getRushPayRWList(account, type);
    }

    @Override
    public synchronized boolean insertRushPayRW(RushPayRW rushPayRW) {
        if (rushPayRWDao == null) {
            return false;
        }
        return rushPayRWDao.insertRushPayRW(rushPayRW);
    }

    @Override
    public synchronized void updateRushPayList(List<RushPayRW> rushPayRWs) {
        if (rushPayRWDao == null) {
            return;
        }
        rushPayRWDao.updateRushPayList(rushPayRWs);
    }

    @Override
    public synchronized List<RushPayRW> getRemovedRushPayRW(String account, List<String> renWuBHList) {
        if (rushPayRWDao == null) {
            return null;
        }
        return rushPayRWDao.getRemovedRushPayRW(account, renWuBHList);
    }

    @Override
    public synchronized boolean deleteRushPayRW(String account, int taskId) {
        if (rushPayRWDao == null) {
            return false;
        }
        return rushPayRWDao.deleteRushPayRW(account, taskId);
    }

    @Override
    public synchronized void updateRushPayTaskList(List<RushPayRW> rushPayRWList) {
        if (rushPayRWDao == null) {
            return;
        }
        rushPayRWDao.updateRushPayTaskList(rushPayRWList);
    }

    @Override
    public synchronized RushPayRW getNextRushPayTask(String account, int taskId) {
        if (rushPayRWDao == null) {
            return null;
        }
        return rushPayRWDao.getNextRushPayTask(account, taskId);
    }

    @Override
    public synchronized RushPayRW getPreviousRushPayTask(String account, int taskId) {
        if (rushPayRWDao == null) {
            return null;
        }
        return rushPayRWDao.getPreviousRushPayTask(account, taskId);
    }

    @Override
    public synchronized RushPayRW getRushPayRW(String account, int taskId) {
        if (rushPayRWDao == null) {
            return null;
        }
        return rushPayRWDao.getRushPayRW(account, taskId);
    }

    @Override
    public synchronized List<DuoMeiTXX> getRushPayDuoMeiTXXList(String account, int renwubh) {
        if (duoMeiTXXDao == null) {
            return null;
        }

        return duoMeiTXXDao.getRushPayDuoMeiTXXList(account, renwubh);
    }

    @Override
    public synchronized int weiShangchuanRecordCount(long time, String account) {
        if (chaoBiaoSJDao == null) {
            return 0;
        }
        return chaoBiaoSJDao.weiShangchuanRecordCount(time, account);
    }

    @Override
    public synchronized int weiShangchuanJiChaRecordCount(long timg, String account) {
//        if (jiChaSJDao == null) {
        return 0;
//        }
//        return jiChaSJDao.weiShangchuanRecordCount(time, account);
    }

    @Override
    public synchronized JiChaRW getJiChaRW(String account, int taskId) {
        if (jiChaRWDao == null) {
            return null;
        }
        return jiChaRWDao.getJiChaRW(account, taskId);
    }

    @Override
    public synchronized List<JiChaSJ> getListJiChaSJ(int taskId, int sortType) {
        if (jiChaSJDao == null) {
            return null;
        }

        return jiChaSJDao.getList(taskId, sortType);
    }

    @Override
    public synchronized boolean deleteJiChaSJ(String account, int renWuBH) {
        if (jiChaSJDao == null) {
            return false;
        }
        return jiChaSJDao.deleteJiChaSJ(account, renWuBH);
    }

    @Override
    public synchronized boolean deleteJiChaRW(String account, int taskId) {
        if (jiChaRWDao == null) {
            return false;
        }

        return jiChaRWDao.delectJiChaRW(account, taskId);
    }

    @Override
    public synchronized List<DuoMeiTXX> getJiChaDuoMeiTXXList(String account, int renwubh) {
        if (duoMeiTXXDao == null) {
            return null;
        }

        return duoMeiTXXDao.getJiChaDuoMeiTXXList(account, renwubh);
    }

    @Override
    public synchronized List<JiChaRW> getJiChaRWList(String account) {
        if (jiChaRWDao == null) {
            return null;
        }
        return jiChaRWDao.get(account);
    }

    @Override
    public synchronized boolean insertJiChaRW(JiChaRW jiChaRW) {
        if (jiChaRWDao == null) {
            return false;
        }
        return jiChaRWDao.insertData(jiChaRW);
    }

    @Override
    public synchronized List<JiChaRW> getRemovedJiChaRW(String account, List<String> renWuBHList) {
        if (jiChaRWDao == null) {
            return null;
        }

        return jiChaRWDao.getRemovedJiChaRW(account, renWuBHList);
    }

    @Override
    public synchronized boolean updateJiChaRW(String account, int renwuBH, boolean needSync) {
        if (jiChaRWDao == null) {
            return false;
        }
        return jiChaRWDao.updateJiChaRW(account, renwuBH, needSync);
    }

    @Override
    public synchronized boolean updateJiChaRW(JiChaRW jiChaRW) {
        if (jiChaRWDao == null) {
            return false;
        }
        return jiChaRWDao.updateJiChaRW(jiChaRW);
    }

    @Override
    public synchronized List<WaiFuCBSJ> getWaiFuCBSJList(String account, int filterType) {
        if (waiFuCBSJDao == null) {
            return null;
        }

        return waiFuCBSJDao.getWaiFuCBSJList(account, filterType);
    }

    @Override
    public synchronized List<String> getBiaoKaXXByKeyWaiFu(String key) {
        if (biaokaxxDao == null) {
            return null;
        }

        return biaokaxxDao.getBiaoKaXXByKeyWaiFu(key);
    }

    @Override
    public synchronized List<WaiFuCBSJ> getWaiFuCBSJs(List<String> cids) {
        if (waiFuCBSJDao == null) {
            return null;
        }

        return waiFuCBSJDao.getWaiFuCBSJs(cids);
    }

    @Override
    public synchronized WaiFuCBSJ getWaiFuCBSJ(int taskId, String cH, String S_CID) {
        if (waiFuCBSJDao == null) {
            return null;
        }

        return waiFuCBSJDao.getWaiFuCBSJ(taskId, cH, S_CID);
    }

    @Override
    public synchronized WaiFuCBSJ getPreviousWaiFuCBSJ(String account, String cid) {
        if (waiFuCBSJDao == null) {
            return null;
        }

        return waiFuCBSJDao.getPreviousWaiFuCBSJ(account, cid);
    }

    @Override
    public synchronized WaiFuCBSJ getNextWaiFuCBSJ(String account, String cid) {
        if (waiFuCBSJDao == null) {
            return null;
        }

        return waiFuCBSJDao.getNextWaiFuCBSJ(account, cid);
    }

    @Override
    public synchronized void updateWaiFuCBSJList(List<WaiFuCBSJ> waiFuCBSJList) {
        if (waiFuCBSJDao == null) {
            return;
        }

        waiFuCBSJDao.updateWaiFuCBSJList(waiFuCBSJList);
    }

    @Override
    public synchronized void insertJiChaSJList(List<JiChaSJ> jiChaSJList, boolean needDeletingFirstly) {
        if (jiChaSJDao == null) {
            return;
        }
        jiChaSJDao.insertJiChaSJList(jiChaSJList, needDeletingFirstly);
    }

    @Override
    public synchronized void updateJiChaSJList(List<JiChaSJ> jiChaSJList) {
        if (jiChaSJDao == null) {
            return;
        }
        jiChaSJDao.updateJiChaSJList(jiChaSJList);
    }

    @Override
    public synchronized List<JiChaSJ> getJiChaSJList(int taskId, List<String> cids) {
        if (jiChaSJDao == null) {
            return null;
        }
        return jiChaSJDao.get(taskId, cids);
    }

    @Override
    public synchronized List<JiChaSJ> getNextUnfinishedJiChaSJWithCeNeiXH(int renWuBH, String ch) {
        if (jiChaSJDao == null) {
            return null;
        }

        return jiChaSJDao.getNextUnfinishedJiChaSJWithCeNeiXH(renWuBH, ch);
    }

    @Override
    public synchronized List<JiChaSJ> getAllFinishedJiChaSJ(int taskId, String volume, String account, int chaobiaobzYichao) {
        if (jiChaSJDao == null) {
            return null;
        }

        return jiChaSJDao.getAllFinishedJiChaSJ(taskId, volume, account, chaobiaobzYichao);
    }

    @Override
    public synchronized List<JiChaSJ> getAllJiChaSJ(String account) {
        if (jiChaSJDao == null) {
            return null;
        }

        return jiChaSJDao.getAllJiChaSJ(account);
    }

    @Override
    public synchronized List<JiChaSJ> getAllJiChaSJ(String account, int taskId) {
        if (jiChaSJDao == null) {
            return null;
        }
        return jiChaSJDao.getAllJiChaSJ(account, taskId);
    }

    @Override
    public synchronized List<JiChaSJ> getJiChaSJCBY(String chaoBiaoY, int renWuBH, String ch, int chaoBiaoBZ, int shangChuanBZ) {
        if (jiChaSJDao == null) {
            return null;
        }

        return jiChaSJDao.getJiChaSJCBY(chaoBiaoY, renWuBH, ch, chaoBiaoBZ, shangChuanBZ);
    }

    @Override
    public synchronized JiChaSJ getJiChaSJ(int taskId, String cH, String S_CID) {
        if (jiChaSJDao == null) {
            return null;
        }
        return jiChaSJDao.getJiChaSJ(taskId, cH, S_CID);
    }

    @Override
    public synchronized JiChaSJ getPreviousJiChaSJNew(String account, int taskId, String S_CID, boolean isNotReading) {
        if (jiChaSJDao == null) {
            return null;
        }
        return jiChaSJDao.getPreviousJiChaSJNew(account, taskId, S_CID, isNotReading);
    }

    @Override
    public synchronized JiChaSJ getNextJiChaSJNew(String account, int taskId, String S_CID, boolean isNotReading) {
        if (jiChaSJDao == null) {
            return null;
        }
        return jiChaSJDao.getNextJiChaSJNew(account, taskId, S_CID, isNotReading);
    }

    @Override
    public synchronized void updateJiChaSJ(JiChaSJ jiChaSJ) {
        if (jiChaSJDao == null) {
            return;
        }
        jiChaSJDao.updateJiChaSJ(jiChaSJ);
    }

    @Override
    public synchronized void insertWaiFuCBSJList(List<WaiFuCBSJ> waiFuCBSJList, String account) {
        if (waiFuCBSJDao == null) {
            return;
        }
        waiFuCBSJDao.insertWaiFuCBSJList(waiFuCBSJList, account);
    }

    @Override
    public synchronized List<WaiFuCBSJ> getRemoveWaiFuCBSJList(List<WaiFuCBSJ> waiFuCBSJList, String account) {
        if (waiFuCBSJDao == null) {
            return null;
        }
        return waiFuCBSJDao.getRemoveWaiFuCBSJList(waiFuCBSJList, account);
    }

    @Override
    public synchronized List<BIAOKAXX> getBiaoKaXXWaiFuCBSJList(String account) {
        if (biaokaxxDao == null) {
            return null;
        }
        return biaokaxxDao.getBiaoKaXXWaiFuCBSJList(account);
    }

    private boolean isExist(String cid, List<YanChiBiao> yanChiBiaos){
        boolean exist = false;
        for (YanChiBiao yanChiBiao : yanChiBiaos){
            if (yanChiBiao.getS_CID().equals(cid)){
                exist = true;
                break;
            }
        }
        return exist;
    }

    private boolean isExistCh(String ch, List<YanChiBiao> yanChiBiaos){
        boolean exist = false;
        for (YanChiBiao yanChiBiao : yanChiBiaos){
            if (yanChiBiao.getS_CH().equals(ch)){
                exist = true;
                break;
            }
        }
        return exist;
    }

}
