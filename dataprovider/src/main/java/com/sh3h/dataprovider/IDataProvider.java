/**
 * @author qiweiwei
 */
package com.sh3h.dataprovider;

import android.content.Context;

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
import com.sh3h.dataprovider.greendaoEntity.YanChiBiao;
import com.sh3h.dataprovider.greendaoEntity.YuanGongXX;
import com.sh3h.serverprovider.entity.BiaoKaBean;
import com.sh3h.serverprovider.entity.BiaoKaListBean;
import com.sh3h.serverprovider.entity.BiaoKaWholeEntity;
import com.sh3h.serverprovider.entity.XunJianTaskBean;

import java.util.List;
import java.util.Map;

/**
 * IMeterReadingDataProvider
 */
public interface IDataProvider {

    // public int auth(String pwd, String account);

    public boolean init(String path, Context context);

    public void destroy();

    /**
     * 根据账户查找元数据
     *
     * @param account
     * @return
     */
    public MetaInfo getMetaInfo(String account);

    public List<ChaoBiaoRW> getChaoBiaoRWList(String account);

    public List<ChaoBiaoRW> getChaoBiaoRWList(String account,int rwType);

    public int isUploaded(String account, String ch);

    public ChaoBiaoRW getChaoBiaoRW(int id);

    public ChaoBiaoRW getChaoBiaoRW(String account, int taskId, String ch);

    public ChaoBiaoRW getChaoBiaoRW(String account, int taskId);

    public List<ChaoBiaoRW> getChaoBiaoRWList(String account, String ids);

    public List<ChaoBiaoSJ> getListChaoBiaoSJ(String cH, int sortType);

    public List<ChaoBiaoSJ> getLgldChaoBiaoSJ(String account, int sortType);

    public List<ChaoBiaoSJ> getListChaoBiaoSJ(int taskId,String cH, int sortType);

    public List<ChaoBiaoSJ> getListChaoBiaoSJHighOrLow(int taskId,String cH, int sortType, double waterHighN);

    public List<YanChiBiao> getListYanChiBiao(String account, int sortType);

    public List<YanChiBiao> getListYanChiBiao(String account, String key);

    public int getChaoJianS(String account, int taskId, String volume, String chaoJianZTS);

    public ChaoBiaoSJ getChaoBiaoSJ(int taskId,String cH,String cId);

    public ChaoBiaoSJ getChaoBiaoSJByGroupId(int taskId, int groupId, String S_CID);

    public ChaoBiaoSJ getNextChaoBiaoSJNew(String account, int taskId, String S_CID, boolean isNotReading);

    public ChaoBiaoSJ getPreviousChaoBiaoSJNew(String account, int taskId, String S_CID, boolean isNotReading);

    public List<ChaoBiaoSJ> getChaoBiaoSJList(int taskId,String ch, List<String> cids);

    public List<ChaoBiaoSJ> getChaoBiaoSJList(int taskId,int groupId, List<String> cids);

    public ChaoBiaoSJ getNextChaoBiaoSJ(int taskId,String ch, long ceneixh, boolean isNotReading);

    public List<ChaoBiaoSJ> getNextUnfinishedChaoBiaoSJWithCeNeiXH(int renWuBH, String ch);

    public ChaoBiaoSJ getPreviousChaoBiaoSJ(int taskId, String ch, long ceneixh, boolean isNotReading);

    public BIAOKAXX getBiaoKaXX(String cId);

    public long getCountByTaskId(int taskId);

    public List<ChaoBiaoInfo> getchaoBiaoSJ(int chaoBiaoBZ, String cH,
                                            String chaoBiaoY);

    public int getBiaoKaCount(String s_ch);

    public ChaoBiaoSJ getChaoBSJBySCHAndCeNeiPX(String S_CH, Integer I_CeNeiPX);

    public Map<String, Integer> getChaoBiaoSJCount(String cH, String guSuan,
                                                   String chaoBiaoY);

    /**
     * 根据CH(册本号)获取本地数据对应的单条未完成抄表任务
     */
    public ChaoBiaoSJ getUnWorkChaoBiaoSJ(String CH);

    /**
     * 根据输入内容进行模糊查询
     */
    public List<ChaoBiaoSJ> getSelectChaoBiaoSJ(String str, String cH);

    /**
     * 保存抄表数据
     *
     * @param S_CID
     * @param I_ZhuangTaiBM
     * @param S_ZhuangTaiMC
     * @param I_BenCiCM
     * @param I_ChaoJianSL
     * @return
     */
//    public boolean updateChaobiaoSJ(String S_CID, int I_ZhuangTaiBM,
//                                    String S_ZhuangTaiMC, int I_BenCiCM, int I_ChaoJianSL, double N_JE,
//                                    String S_CH, int I_LiangGaoLDYYBM, String S_ChaoBiaoBeiZhu,
//                                    double x1, double y1, int chaoBiaoBiaoZhi, String jieTiTS);

  /**
   * 保存巡检表卡
   *
   * @param insertXunJianBK
   * @return
   */
  public boolean insertXunJianBK(List<BiaoKaBean> biaoKaBeans);

  public List<BiaoKaListBean> getXunJianListBK();

  public List<BiaoKaListBean> getXunJianListBK(String xiaogenghao);

  public List<BiaoKaBean> getXunJianBK();

  public boolean insertBKWholeEntity(BiaoKaWholeEntity newWholeEntity);

  public List<XunJianTaskBean> getLocalXunJianTasks(String type);

  public List<BiaoKaListBean> getWcorYcBiaoKalistbean(String renwumc, String type);

  public List<XunJianTaskBean> getXunJianTaskBean(String renwumc);

  public List<BiaoKaListBean> getBiaoKaListBean(String s_renwuid);

  public  void deleteBiaoKaListBean();

  public void saveXunjianBKlistBean(BiaoKaListBean biaoKaListBean);

  public List<BiaoKaWholeEntity> getBiaoKaWholeEntity2(String renwumc, int issave);

  public boolean SaveXunJianTasks(List<XunJianTaskBean> xunJianTaskBeans);

  public boolean saveBiaoKaListBean(List<BiaoKaListBean> biaoKaList);

  public boolean savebiaoKaBean(List<BiaoKaBean> biaoKaBeans);

  public void saveBiaoKaWholeEntityDao(BiaoKaWholeEntity biaoKaWholeEntity);


  /**
     * 查询简号下面各种费用组成明细
     *
     * @param S_JH
     * @return
     */
    public List<JianHaoMX> getJianHaoMXByJH(String S_JH);

    /**
     * 查询抄表状态
     *
     * @param i_chaobiaoztbm
     * @return
     */
    public ChaoBiaoZT getChaoBiaoZT(int i_chaobiaoztbm);

    /**
     * 获取所有抄表状态
     *
     * @return
     */
    public List<ChaoBiaoZT> getAllChaobiaozt();

    /**
     * 获取所有缴费信息
     */
    public List<JiaoFeiXX> getListJiaoFeiXX(String cId);

    /**
     * 获取所有欠费信息
     */
    public List<QianFeiXX> getListQianFeiXX(String cId);

    /**
     * 获取单个用户抄表记录
     */
    public List<ChaoBiaoJL> getListChaoBiaoJL(String cId);

    /**
     * 获取所有抄表记录
     */
    public List<ChaoBiaoJL> getAllListChaoBiaoJL();

    /**
     * 获取所有抄表分类状态
     */
    public List<ChaoBiaoZTFL> getAllChaobiaoZTFL();

    /**
     * 根据对应的抄表编码号获取相对应的list集合
     */
    public List<ChaoBiaoZT> getChaoBiaoZTFL(int zhuangtaiFLBM);

    /**
     * 根据抄表状态BM获取相对应的状态抄码集合
     */
    public List<ChaoBiaoZT> getZhuangTaiBMList(String zhuangTaiBM);

    public void deleteShuiLiangFTXX(String CIDAll);

    public boolean insertShuiLiangFTXX(ShuiLiangFTXX shuiLiangFTXX);

    public boolean updateShuiLiangFTXX(ShuiLiangFTXX shuiLiangFTXX);

    public void insertOrUpdateShuiLiangFTXXList(
            List<ShuiLiangFTXX> shuiLiangFTXXList);

    /**
     * 根据用户号查询混合用水分摊情况
     *
     * @param s_cid
     * @return
     */
    public List<ShuiLiangFTXX> getShuiLiangFenTXX(String s_cid);

    /**
     * 获取抄表任务中的CH
     *
     * @return List<String>
     */
    public List<String> getCH();

    /**
     * 保存多媒体信息
     *
     * @param duoMeiTXX
     * @return
     */
    public boolean insertDuoMeiTXX(DuoMeiTXX duoMeiTXX);

    /**
     * 更新金额
     *
     * @param S_CID
     * @param N_JE
     * @return
     */
    public boolean updateNJE(int taskId,String cH,String S_CID, double N_JE);

    /**
     * 获取chaobiaoid，cid对应的册本媒体照片集合
     *
     * @param chaoBiaoID
     * @param CID
     * @return List
     */
    public List<DuoMeiTXX> getDuoMeiTXXList(int chaoBiaoID, String CID);

    public List<DuoMeiTXX> getNotUploadedDuoMeiTXXList(String account, int renwubh, String ch);

    public List<DuoMeiTXX> getMoreDuoMeiTXXList(String account, int taskId, String volume, int offset, int limit);

    public List<DuoMeiTXX> getMoreDuoMeiTXXList(String account,int offset, int limit);

    public List<DuoMeiTXX> getMoreDelayDuoMeiTXXList(String account,int offset, int limit);

    /**
     * 删除多媒体文件信息
     *
     * @param wenJianMC
     * @return
     */
    public boolean deleteDuoMeiTXX(String wenJianMC);

    /**
     * 获取简号list
     */
    public List<JianHao> getJianHaoList();

    /**
     * 通过种类查询获取简号实体
     */
    public JianHao getJianHao(String strZhongLei);

    /**
     * 组合查询返回表卡信息
     *
     * @param swhere
     * @param GuBiaoLXCS
     * @param LiangGaoLDLXCS
     * @return
     */
    public List<BIAOKAXX> QueryBiaoKaxxsWhere(String swhere, int GuBiaoLXCS,
                                              int LiangGaoLDLXCS);

    /**
     * 模糊查询返回表卡信息
     *
     * @param key
     * @param ChaoBiaoRWCHAll
     * @return
     */
    public List<BIAOKAXX> QueryBiaoKaxxsWhere(String key, String ChaoBiaoRWCHAll);

    /**
     * 获取量高原因
     *
     * @return
     */
    public List<CiYuXX> getLiangGaoYY();

    /**
     * 获取量低原因
     *
     * @return
     */
    public List<CiYuXX> getLiangDiYY();

    public List<CiYuXX> getNoReadReason();

    public boolean authInterface(String account);

    /**
     * 获取定额单价
     *
     * @param S_CID
     * @param S_JH
     * @return
     */
    public double getDingEJiaGeByCIDAndJH(String S_CID, String S_JH);

    /**
     * 返回定额加价表数据
     *
     * @return
     */
    public List<DingEJJBL> getAllDingEJJBL();

    /**
     * 判断数据库中词语信息数据数据code是否存在
     *
     * @return
     */
    public boolean existCiYuXX(int id);

    /**
     * 获取词语列表
     *
     * @param belongid
     * @return
     */
    public List<CiYuXX> getCiYuXX(int belongid);

    /**
     * 删除词语表在数据库中的数据
     *
     * @return boolean
     */
    public boolean deleteCiYu();

    public boolean deleteCiYu(String code);

    /**
     * 向词语信息表插入最新数据
     *
     * @param ciyuXX 实体
     * @return boolean
     */
    public void insertCiYu(CiYuXX ciyuXX);

    /**
     * 返回员工信息
     */
    public YuanGongXX getYuanGongXX(String account);

    /**
     * 返回员工登陆结果
     */
    public int auth(String pwd, String account);

    /**
     * 修改登录记录
     */
    public boolean updateDengLuLS(DengLuLS dengLuLS);

    /**
     * 客户信息变更
     */
    public int updateYHxx(String s_CID, String s_LianXiDH, String s_LIANXISJ,
                          String s_JianHao, String s_BEIZHU);

    /**
     * 返回换表记录集合
     */
    public List<HuanBiaoJL> getHuanBiaoJLList(String cId);

    /**
     * 删除换表记录
     */
    public boolean clearHuanBiaoJL(String cId);

    /**
     * 插入换表信息
     */
    public boolean insertHuanBiaoJL(HuanBiaoJL huanbiaojl);

    public boolean insertHuanBiaoJLs(List<HuanBiaoJL> huanBiaoJLList);

    /**
     * 修改表卡信息
     */
    public boolean updateBiaoKaXX(BIAOKAXX biaoKaXX);

    public boolean updateBiaoKaXXList(List<BIAOKAXX> biaoKaXXList);

    /**
     * 删除抄表记录
     */
    public boolean clearChaoBiaoJL(String cId);

    /**
     * 插入抄表记录
     */
    public boolean insertChaoBiaoJL(ChaoBiaoJL chaoBiaoJL);

    public boolean insertChaoBiaoJLList(List<ChaoBiaoJL> chaoBiaoJLList);

    /**
     * 删除缴费信息
     */
    public boolean clearJiaoFeiXX(String cId);

    /**
     * 插入缴费信息
     */
    public boolean insertJiaoFeiXX(JiaoFeiXX jiaoFeiXX);

    public boolean insertJiaoFeiXXs(List<JiaoFeiXX> jiaoFeiXXList);

    /**
     * 删除欠费信息
     */
    public boolean clearQianFeiXX(String cId);

    public boolean clearQianFeiXX(List<String> cids);

    /**
     * 插入欠费信息
     */
    public boolean insertQianFeiXX(QianFeiXX qianFeiXX);

    public boolean insertQianFeiXXs(List<QianFeiXX> qianFeiXXList);

    /**
     * 获取词语list
     */
    public List<CiYuXX> getCiYuXXlist(int belongid);

    /**
     * 获取系统公告全部信息
     *
     * @return List<GongGaoXX>
     */
    public List<GongGaoXX> getGongGaoXXList();

    /**
     * 根据提供的gongGaoBH获取对应的公告信息
     *
     * @param gongGaoBH 公告编号
     * @return GongGaoXX
     */
    public GongGaoXX getGongGaoXX(int gongGaoBH);

    /**
     * 插入数据到系统公告
     *
     * @param gongGaoXX 公告信息
     * @return boolean
     */
    public boolean insertGongGaoXX(GongGaoXX gongGaoXX);

    /**
     * 获取登录历史
     */
    public DengLuLS getDengLuLS(String account);

    /**
     * 插入数据到抄表状态数据库中
     *
     * @param chaoBiaoZT 抄表状态数据实体
     */
    public void insertChaoBiaoZT(ChaoBiaoZT chaoBiaoZT);

    /**
     * 删除超标状态表在数据库中的数据
     */
    public void deleteChaoBiaoZT();

    /**
     * 根据状态编码查询对应的数据，如果存在则返回true，不存在返回false
     *
     * @param zhuangTaiBM 状态编码
     * @return boolean
     */
    public boolean existChaoBiaoZT(int zhuangTaiBM);

    /**
     * 根据状态编码查询对应的数据，如果存在则返回true，不存在返回false
     *
     * @param fenLeiBM 分类编码
     * @return boolean
     */
    public boolean existChaoBiaoZTFL(int fenLeiBM);

    /**
     * 删除超标状态分类表在数据库中的数据
     */
    public void deleteChaoBiaoZTFL();

    /**
     * 插入数据到抄表状态数分类据库中
     *
     * @param chaoBiaoZTFL 抄表状态分类数据实体
     */
    public void insertChaoBiaoZTFL(ChaoBiaoZTFL chaoBiaoZTFL);

    /**
     * 根据定额加价倍率id查询对应的数据，如果存在则返回true，不存在返回false
     *
     * @param id 状态编码
     * @return boolean
     */
    public boolean existDingEJJBL(int id);

    /**
     * 删除定额加价倍率表在数据库中的数据
     */
    public void deleteDingEJJBL();

    /**
     * 插入数据到抄表状态数据库中
     *
     * @param dingEJJBL 抄表状态数据实体
     */
    public void insertDingEJJBL(DingEJJBL dingEJJBL);

    /**
     * 根据费用组成id查询对应的数据，如果存在则返回true，不存在返回false
     *
     * @param id 费用组成id
     * @return boolean
     */
    public boolean existFeiYongZC(int id);

    /**
     * 删除费用组合表在数据库中的数据
     */
    public void deleteFeiYongZC();

    /**
     * 插入数据到费用组成数据库中
     *
     * @param feiYongZC 抄费用组成数据实体
     */
    public void insertFeiYongZC(FeiYongZC feiYongZC);

    /**
     * 根据简号id查询对应的数据，如果存在则返回true，不存在返回false
     *
     * @param id 简号id
     * @return boolean
     */
    public boolean existJianhao(int id);

    /**
     * 删除简号表在数据库中的数据
     */
    public void deleteJianHao();

    /**
     * 插入数据到简号数据库中
     *
     * @param Jianhao 简号数据实体
     */
    public void insertJianHao(JianHao Jianhao);

    /**
     * 根据简号明细id查询对应的数据，如果存在则返回true，不存在返回false
     *
     * @param id 简号明细id
     * @return boolean
     */
    public boolean existJianHaoMX(int id);

    /**
     * 删除简号明细表在数据库中的数据
     */
    public void deleteJianHaoMX();

    /**
     * 插入数据到简号明细数据库中
     *
     * @param jianHaoMX 简号明细数据实体
     */
    public void insertJianHaoMX(JianHaoMX jianHaoMX);

    /**
     * 插入表卡信息
     */
    public boolean insertBiaoKaXX(BIAOKAXX biaoKaXX);

    public void insertOrUpdateBiaoKaXXList(List<BIAOKAXX> biaoKaXXList);

    /**
     * 获取当前公告数据中最新时间
     *
     * @return
     */
    public long getGongGaoFaBuSJ();

    /**
     * 获取未读公告数据条数
     *
     * @return int
     */
    public int getGongGaoXXUnreadCount();

    /**
     * 修改公告未读信息为已读状态
     *
     * @param gongGaoBH 公告编号
     */
    public void updateCongGaoXXYD(int gongGaoBH);

    /**
     * 获取费用折扣率
     *
     * @param S_CID
     * @param S_JH
     * @param I_FeiYongDLBH
     * @return
     */
    public double getFeiYongZKL(String S_CID, String S_JH, int I_FeiYongDLBH);

    /**
     * 插入新的数据到抄表数据中
     *
     * @param chaoBiaoSJ 抄表数据实体
     */
    public void insertChaoBiaoSJ(ChaoBiaoSJ chaoBiaoSJ);

    public void insertChaoBiaoSJList(List<ChaoBiaoSJ> chaoBiaoSJList, boolean needDeletingFirstly);

    /**
     * 更新抄表数据
     *
     * @param chaoBiaoSJ
     */
    public void updateChaoBiaoSJ(ChaoBiaoSJ chaoBiaoSJ);

    public void updateChaoBiaoSJList(List<ChaoBiaoSJ> chaoBiaoSJList);

    /**
     * 根据任务编号删除对应的抄表数据
     *
     * @param renWuBH 任务编号
     */
    public void deleteChaoBiaoSJ(int renWuBH, String chaoBiaoYuan);

    public boolean deleteChaoBiaoSJ(String account, int renWuBH, String ch);

    public boolean deleteChaoBiaoSJ(String account, int renWuBH, int groupId);
    /**
     * 删除抄表任务
     */
    public boolean clearChaoBiaoRW(String chaoBiaoYBH, int renWuBH);

    /**
     * 修改抄表任务
     */
    public boolean updateChaoBiaoRW(ChaoBiaoRW chaoBiaoRW);


    public boolean updateChaoBiaoRW(String account,String ch,int renwuBH,int finishednum,int flag);

    public boolean updateChaoBiaoRW(String account, int renwuBH, String ch, boolean needSync);

    public boolean updateChaoBiaoRW(String account, int renwuBH, boolean needSync,int typeRM);

    public boolean updateChaoBiaoRW(String account, int renwuBH, String ch, int yichaos);
    /**
     * 新增抄表任务
     */
    public boolean insertChaoBiaoRW(ChaoBiaoRW chaoBiaoRW);

    /**
     * 根据任务编号查询抄表任务
     */
    public void delectChaoBiaoRW(String chaoBiaoYBH, String renWuBHAll);

    /**
     * 根据任务编号查找renWuBHAll不存在本地的任务
     */
    public String selectNonentityChaoBiaoRW(String chaoBiaoYBH,
                                            String renWuBHAll);

    /**
     * 根据抄表标志，任务编号，上传标志条件获取相对应的抄表数据
     *
     * @param chaoBiaoBZ   抄表标志
     * @param shangChuanBZ
     * @param chaobiaoRWAll      任务编号 上传标志
     * @return List<ChaoBiaoSJ>
     */
    public List<ChaoBiaoSJ> getChaoBiaoSJ(int chaoBiaoBZ, int shangChuanBZ,
                                          String chaobiaoRWAll);

    /**
     * 根据任务编号删除不存在renWuBHAll中的抄表任务
     *
     * @param chaoBiaoYBH
     * @return
     */
    public void delectChaoBiaoSJ(String renWuBHAll, String chaoBiaoYBH);

    /**
     * 根据抄表员编号查询所对应的抄表任务编号
     *
     * @param Account 抄表员编号
     * @return List<Integer>
     */
    public String getChaoBiaoRenWuBH(String Account);

    /**
     * 根据抄表标志，任务编号，上传标志条件获取相对应的抄表数据
     *
     * @param chaoBiaoBZ   抄表标志
     * @param shangChuanBZ
     * @param renWuBH      任务编号 上传标志 * @param flag 查询范围（0任意抄表状态，1已抄+延迟+外复延迟）
     * @return List<ChaoBiaoSJ>
     */
    public List<ChaoBiaoSJ> getChaoBiaoSJRenWuBH(int chaoBiaoBZ,
                                                 int shangChuanBZ, int renWuBH, int flag);

    /**
     * 根据抄表标志，抄表员，上传标志条件获取相对应的抄表数据
     *
     */
    public List<ChaoBiaoSJ> getChaoBiaoSJCBY(String chaoBiaoY,
                                             int renWuBH,
                                             String ch,
                                             int chaoBiaoBZ,
                                             int shangChuanBZ,
                                             Integer[] chaobiaoSJType);

    /**
     * 获取未上传的超标数据
     */
    public List<ChaoBiaoSJ> getNotUploadChaoBiaoSJ(String chaoBiaoY, int renWuBH, String ch);

    /**
     * 获取所有已抄数据
     * @param chaoBiaoY
     * @param renWuBH
     * @param ch
     * @param chaoBiaoBZ
     * @return
     */
    public List<ChaoBiaoSJ> getAllChaoBiaoSJCBY(String chaoBiaoY,
                                             int renWuBH,
                                             String ch,
                                             int chaoBiaoBZ);

    /**
     * 通过册本号，抄表员，获取册本下所有的册内序号
     *
     * @param ch      册本号
     * @param account 抄表员
     * @return Map<Integer, Integer> key：index，values：册内序号
     */
    public List<Integer> getCeNetXH(String ch, String account);

    /**
     * 更改上传标志
     *
     * @param chaoBiaoBZ
     *            抄表标志
     * @param shangChuanBZ
     *            上传标志
     * @param renWuBHAll
     *            任务编号
     */
    // public void updateShangChuanBZ(int chaoBiaoBZ, int shangChuanBZ,
    // String renWuBHAll);

    /**
     * 根据抄表员编号更改本地数据的上传状态
     *
     * @param chaoBiaoBZ
     *            抄表标志
     * @param shangChuanBZ
     *            上传标志
     * @param chaoBiaoY
     *            抄表员
     */
    // public void updateShangChuanMain(int chaoBiaoBZ, int shangChuanBZ,
    // String chaoBiaoY);

    /**
     * 更改上传状态
     *
     * @param cid 用户号
     */
    public void UpdateShangChuanBIaoZhi(int taskId,String cH,String cid, int ShangChuanBZ);

    /**
     * 删除公告信息
     *
     * @param gongGaoBH
     * @return
     */
    public boolean delectGongGaoXX(int gongGaoBH);

    /**
     * 根据抄表员编号查找用户号
     */
    public List<String> getChaoBiaoSJCID(String ChaoBiaoYBH, String renWuBHAll);

    /**
     * 删除表卡信息
     */
    public void deleteBiaoKaXXs(String ch);

    public void deleteBiaoKaXXs(List<String> chs);

    public void deleteBiaoKaXXs(int groupId);

    public void deleteBiaoKaXX(String cid);

    /**
     * 删除欠费信息
     */
    public void delectQianFeiXX(String cidAll);

    /**
     * 删除缴费信息
     */
    public void delectJiaoFeiXX(String cidAll);

    /**
     * 删除换表记录
     */
    public void delectHuanBiaoJL(String cidAll);

    /**
     * 插入费用折扣率
     */
    public boolean insertFeiYongZKL(FeiYongZKL feiYongZKL);

    public void insertFeiYongZKLList(List<FeiYongZKL> feiYongZKLList);

    /**
     * 删除费用折扣率
     */
    public void delectFeiYongZKL(String cidAll);

    /**
     * 删除费用折扣率
     */
    public void clearFeiYongZKL(String S_CID, String jianHao, int feiYongDLBH);

    /**
     * 用Ch查询表卡信息表中的表卡数
     */
    public int getBiaoKaXXCount(String S_CH);

    /**
     * 查询费用折扣率
     */
    public FeiYongZKL getFeiYongZKL(String S_CID, int I_FeiYongDLBH, String S_JH);

    /**
     * 获取操作人对应的的所有表务工单任务
     *
     * @param caoZuoR 操作人
     * @return List<BiaoWuGD>
     */
    public List<BiaoWuGD> getBiaoWuGDList(String caoZuoR);

    /**
     * 删除表务工单所有任务
     */
    public void deleteBiaoWuGD();

    /**
     * 删除表务工单
     */
    public void deleteBiaoWuGDByBH(int renwubh);

    /**
     * 插入新数据到表务工单表
     *
     * @param gd 表务工单实体类
     */
    public void insertBiaoWuGD(BiaoWuGD gd);

    /**
     * 获取任务编号对应的表务工单信息
     *
     * @param renWuBH 任务编号
     * @return BiaoWuGD
     */
    public BiaoWuGD getBiaoWuGD(int renWuBH);

    /**
     * 插入延迟
     */
    public boolean insertYanChiBiao(YanChiBiao yanChiBiao);

    public void insertYanChiBiaoList(List<YanChiBiao> list);

    public void updateYanChiBiaoList(List<YanChiBiao> list);

    /**
     * 获取延迟表list
     */
    public List<YanChiBiao> getYanChiBiaoList(String chaoBiaoYId, int yanChiLX,
                                              int sortType);

    /**
     * 删除工单任务
     *
     * @param account    抄表员
     */
    public void deleteRenWuXX(String account);

    /**
     * 删除任务信息
     */
    public void deleteRenWuXXByBH(int renwubh);

    /**
     * 删除表务工单信息
     *
     * @param account    操作员
     * @param renWuBHAll 所有需要的任务编号
     */
    public void deleteNotExistBiaoWuGD(String account, String renWuBHAll);

    /**
     * 插入工单任务
     *
     * @param rw 工单任务
     */
    public void insertRenWuXX(RenwuXX rw);

    /**
     * 获取已抄为上传表单信息
     *
     * @param account 操作员
     * @return List<BiaoWuGD>
     */
    public List<BiaoWuGD> getWeiShangChuanBiaoWuGD(String account);

    /**
     * 判断是否存在已存在的表务工单数据，不存在则插入，存在则更改
     *
     * @param gd 表务工单实体类
     */
    public void UpdateBiaoWuGD(BiaoWuGD gd);

    /**
     * 更改上传状态
     *
     * @param account 操作员
     * @param gd      工单任务
     */
    public void updateBiaoWuShangChuanZT(String account, BiaoWuGD gd);

    /**
     * 更改表务工单数据
     *
     * @param gd 表务工单数据
     */
    public boolean updateBiaoWuGDDate(BiaoWuGD gd);

    /**
     * 获取所有表务工单任务编号
     *
     * @return String
     */
    public String getAllRenWuXXRenWuBH(String account);

    /**
     * 从任务休息表获取所有延迟任务编号
     *
     * @return String
     */
    public String getAllRenWuXXYanChiBH(String account);

    /**
     * 根据任务编号查找renWuBHAll不存在本地的任务
     *
     * @param accoount 抄表员
     * @return String
     */
    public String selectNonentityBiaoWuGDBH(String accoount, String renWuBHAll);

    /**
     * 删除本地不需要的延迟编号下所有的延迟信息
     *
     * @param account     操作员
     * @param AllYanChiBH 所有需要的延迟编号
     */
    public void deleteNotExistYanChiXX(String account, String AllYanChiBH);

    /**
     * 根据延迟编号查找yanChiBHAll不存在本地的任务
     *
     * @param accoount 抄表员
     * @return String
     */
    public String selectNonentityYanChiBH(String accoount, String yanChiBHAll);

    /**
     * 获取已抄未上传的表务工单总条数
     *
     * @param account 操作员
     * @return int
     */
    public int getBiaoWuGDCount(String account);

    /**
     * 保存照片名称
     *
     * @param renWuBH 任务编号
     * @param imgPath 图片名称
     */
    public void updateImgPath(int renWuBH, String imgPath);

    /**
     * 获取照片名称
     *
     * @param renWuBH 任务编号
     * @return String 如“path,path”；
     */
    public String getBiaoWuImgName(int renWuBH);

    /**
     * 插入客户签名路径
     *
     * @param renWuBH       任务编号
     * @param signaturePath 图片路径
     */
    public void updateSignaturePath(int renWuBH, String signaturePath);

    /**
     * 获取操作人对应的的所有未抄表务工单任务
     *
     * @param account 操作人
     * @return List<BiaoWuGD>
     */
    public List<BiaoWuGD> getBiaoWuGDWeiChaoList(String account);

    /**
     * 更新延迟数据
     */
    public boolean updateYanChiSJ(String S_CID, int I_ZhuangTaiBM,
                                  String S_ZhuangTaiMC, int I_BenCiCM, int I_ChaoJianSL, double N_JE,
                                  String S_CH, int I_LiangGaoLDYYBM, String S_ChaoBiaoBeiZhu,
                                  int chaoBiaoBiaoZhi, String jieTiTS);

    /**
     * 根据CID查询延迟表信息
     */
    public YanChiBiao getYanChiBiaoXX(int taskId, String cH,String S_CID);

    /**
     * 查询表卡中不存在的CID编号
     */
    public String getBiaoKaXXCIDS(String CIDS);

    public List<String>  getBiaoKaXXCIDS(String str, String s_ch);

    public List<String> getBiaoKaXXCIDS(String str, int groupId);

    /**
     * 上报延迟，修改延迟标志
     */
    public boolean updateYanChiBZ(String S_CID, int yanChiLX, String S_CH,
                                  String chaobiaobeizhu);

    /**
     * 修改延迟上传标志
     */
    public boolean updateYanChiShangChuanBZ(String S_CID, int shangchuanBZ);

    /**
     * 更新全部延迟数据
     */
    public boolean updateYanChiSJ(YanChiBiao yanChiBiao);

    /**
     * 获取已抄未上传的延迟数据
     */
    public List<YanChiBiao> getWeiShangChuanYanChiBiaoList(String account,
                                                           int type);

    /**
     * 根据册本号 册内排序号 返回延迟数据实体
     *
     * @param S_CH
     * @param I_CeNeiPX
     * @return
     */
    public YanChiBiao getYanChiByCHAndCeNeiPX(String S_CH, Integer I_CeNeiPX);

    /**
     * 插入数据
     */
    public boolean insertWaiFuGDMain(WaiFuGDMain waiFuGDMain);

    /**
     * 获取操作人对应的的所有外复工单任务
     *
     * @param account 操作人
     */
    public List<WaiFuGDMain> getWaiFuGDMainList(String account);

    /**
     * 获取操作人对应的的所有外复工单任务
     *
     * @param renWuBH 操作人
     */
    public WaiFuGDMain getWaiFuGDMain(int renWuBH);

    /**
     * 获取所有外复工单任务编号
     *
     * @return String
     */
    public String getAllRenWuXXWaiFuGDBH(String account);

    /**
     * 删除本地不需要的任务编号下所有的外复工单信息
     *
     * @param account    操作员
     * @param AllRenWuBH 所有需要的任务编号
     */
    public void deleteNotExistWaiFuGD(String account, String AllRenWuBH);

    /**
     * 根据任务编号查找renWuBHAll不存在本地的任务
     *
     * @param accoount 抄表员
     * @return String
     */
    public String selectNonentityWaiFuGDBH(String accoount, String renWuBHAll);

    /**
     * 获取操作人对应的的所有已完成，未上传的外复工单任务
     *
     * @param account 操作人
     */
    public List<WaiFuGDMain> getWeiShangChuanWaiFuGDMainList(String account);

    /**
     * 获取操作人对应的的所有已完成的外复工单明细
     */
    public List<WaiFuGDDetail> getupdateWaiFuGDDetailList(String renwuBHs);

    /**
     * 删除外复工单明细
     */
    public void deleteWaiFuGDDetailByBH(int renwubh);

    /**
     * 获取已抄未上传的延迟数据总条数
     *
     * @param account 操作员
     * @return int
     */
    public int getWeiShangChuanYanChiCount(String account);

    /**
     * 获取已抄未上传的外复工单总条数
     *
     * @param account 操作员
     * @return int
     */
    public int getWeiShangChuanWaiFuGDCount(String account);

    /**
     * 获取操作人对应的的未完成的外复工单任务
     *
     * @param account 操作人
     */
    public List<WaiFuGDMain> getWaiFuGDMainWeiWanChengList(String account);

    /**
     * 插入抄表值班人员
     *
     */
    public void insertUsers(Users user);

    /**
     * 获取账号，站点对应的值班人员数据
     *
     * @param account 操作员
     * @param station 站点
     * @return List<Users>
     */
    public List<Users> getUserList(String account, String station, int type);

    /**
     * 删除对应操作员，类型的用户信息
     *
     * @param account 操作员
     * @param type    类型
     */
    public void deleteUsers(String account, int type);

    /**
     * 修改外复工单主表
     */
    public boolean updateWaiFuGDMain(int renWuBH, WaiFuGDMain waiFuGDMain,
                                     int type);

    /**
     * 删除外复工单工单
     */
    public void deleteWaiFuGDMainByBH(int renwubh);

    /**
     * 修改WaiFuMain上传标志
     */
    public void updateWaiFuMainShangChuanZT(String account, WaiFuGDMain wfm);

    /**
     * 插入外复工单明细数据
     */
    public boolean insertWaiFuGDDetailData(WaiFuGDDetail waiFuGDDetail);

    /**
     * 通过renwubh查找外复明细
     */
    public List<WaiFuGDDetail> getWaiFuGDDetailList(int renwuBH, boolean isAll);

    /**
     * 删除本地不需要的任务编号下所有的外复工单信息
     *
     * @param account    操作员
     * @param AllRenWuBH 所有需要的任务编号
     */
    public void deleteNotExistWaiFuGDDetail(String account, String AllRenWuBH);

    /**
     * 删除外复工单信息
     *
     * @param account 操作员
     * @param renWuBH 所有需要的任务编号
     */
    public void deleteWaiFuGDDetail(String account, int renWuBH);

    /**
     * 远传表自转核查修改
     */
    public boolean updateYuanChuanBZZHC(int renWubh, String cid,
                                        String fuChaJG, int tiaoJianSL);

    /**
     * 修改抄表标志
     *
     * @param account 操作员
     * @param renWuBH 任务编号
     */
    public void updateBiaoWuGDChaoBiaoBZ(String account, int renWuBH);

    /**
     * 远传表自转核查隐藏标志
     */
    public boolean updateYingCangBZ(int renWubh, String cid);

    /**
     * 获取照片名称
     *
     * @param renWuBH 任务编号
     * @return String 如“path,path”；
     */
    public String getWaiFuMainImgName(int renWuBH);

    /**
     * 保存照片名称
     *
     * @param renWuBH 任务编号
     * @param imgPath 图片名称
     */
    public void updateImgPathToWaiFuMain(int renWuBH, String imgPath);

    /**
     * 插入客户签名路径
     *
     * @param renWuBH       任务编号
     * @param signaturePath 图片路径
     */
    public void updateSignaturePathToWaiFuMain(int renWuBH, String signaturePath);

    /**
     * 判断是否存在已存在的外复工单数据，不存在则插入，存在则更改
     *
     * @param gd 表务工单实体类
     */
    public void UpdateWaiFuMainGD(WaiFuGDMain gd);

    /**
     * 删除单条外复工单信息
     *
     * @param account 操作员
     * @param renWuBH 所有需要的任务编号
     */
    public void deleteWaiFuGDDetail_one(String account, int renWuBH, String CID);

    /**
     * 通过renwubh查找单条外复明细
     */
    public WaiFuGDDetail getWaiFuGDDetail(int renwuBH, String CID);

    /**
     * 获取未抄的延迟数据总条数
     *
     * @param account 操作员
     * @return int
     */
    public int getWeiWanChengYanChiCount(String account, int yanChiLX);

    public int getWeiWanChengYanChiCount(String account);

    /**
     * 获取未抄的外复工单数据总条数
     *
     * @param account 操作员
     * @return int
     */
    public int getWeiWanChengWFGDCount(String account);

    /**
     * 获取未抄的表务工单总条数
     *
     * @param account 操作员
     * @return int
     */
    public int getWeiWanChengBWGDCount(String account);

    /**
     * 插入轨迹
     */
    public boolean insertGuiJiData(List<GuiJi> guiJiList);

    /**
     * 删除轨迹
     */
    public void deleteGuiJiData();

    /**
     * 获得轨迹
     */
    public List<GuiJi> getGuiJiData(String ch);

    /**
     * 本地认证，通过传入pwd，account判断本地是否存在这个数据，如果存在返回true，否则为false；
     *
     * @param mima    密码
     * @param account 用户名
     * @return boolean值
     */
    public int Login(String mima, String account);

    /**
     * 返回UserInfo对象
     */
    public UserInfo getUserInfo(String account);

    /**
     * 根据ch查询ch下所有的表卡信息
     *
     * @param ch 册本号
     * @return List<BiaoKaXX>
     */
    public List<BIAOKAXX> getBiaoKaXXList(String ch);

    public List<BIAOKAXX> getBiaoKaXXListByGroupId(int groupId);

    /**
     * 获取该册本下所有的抄表数据
     *
     * @param ch 册号
     * @return List<ChaoBiaoSJ>
     */
    public List<ChaoBiaoSJ> getNextChaoBiaoSJList(String ch);

    public YanChiBiao getNextYanChiCB(int renWuId, String ch, long ceNeiXH, boolean chaoBiaoBZ);

    public YanChiBiao getPreviousYanChiCB(int renWuId, String ch, long ceNeiXH, boolean chaoBiaoBZ);

    /**
     * 获取量高量低抄表数据
     *
     * @param account  操作员
     * @param loadType 获取类型
     * @return List<ChaoBiaoSJ>
     */
    public List<ChaoBiaoSJ> getLiangGaoLiangDiChaoBiaoSJ(String account,
                                                         int loadType);

    /**
     * 获取该表所有的表卡信息
     *
     * @return List<BiaoKaXX>
     */
    public List<BIAOKAXX> getBiaoKaXX();

    public boolean insertChaoBiaoGJ(ChaoBiaoGJ chaoBiaoGJ);

    public ChaoBiaoGJ getChaoBiaoGJ(String ch, String cid, int type);

    public List<ChaoBiaoGJ> getChaoBiaoGJList(String ch, int type);

    public List<ChaoBiaoGJ> getChaoBiaoGJList(String account);

    public List<ChaoBiaoGJ> getChaoBiaoGJList(String account,int renwuBH,String ch);

    /**
     * 获取量高量低延迟数据
     *
     * @param account  操作员
     * @param loadType 获取类型
     * @return List<ChaoBiaoSJ>
     */
    public List<YanChiBiao> getLiangGaoLiangDiYanChiSJ(String account,
                                                       int loadType);

    public List<WordsInfo> getWordsInfoList();

    /**
     * 修改备注
     *
     * @param s_cid
     * @param beiZhu
     */
    public void updateChaoBiaoBeiZhu(String s_cid, String beiZhu);

    /**
     * 修改备注
     *
     * @param s_cid
     * @param beiZhu
     */
    public void updateYanChiBeiZhu(String s_cid, String beiZhu);

    /**
     * 获取对应的cID，cid的多媒体信息集合
     *
     * @param cid
     * @param type
     * @return List<DuoMeiTXX>
     */
    public List<DuoMeiTXX> getDuoMeiTXXList(String account, String cid, int type);


    public List<DuoMeiTXX> getDuoMeiTXXList(String account, String cid,String ch,int renwuBH,int type);

    public boolean updateDuoMeiTXX(DuoMeiTXX duoMeiTXX);

    /**
     * 修改电话数据
     */
    public boolean updateBiaoKaXXPhoneNum(String CID, String phoneNum,
                                          String type);

    /**
     * 判断该用户是否有拍照
     *
     * @param cid 用户号
     * @return boolean
     */
    public boolean IsExistencePhone(String cid);

    /**
     * 修改本次抄码
     *
     * @param benCiCM 本次抄码
     * @param CID     用户号
     * @return boolean
     */
    public boolean updateChaoMa(int benCiCM, String CID);

    /**
     * 更改抄表数据延迟原因
     *
     * @param cid      用户号
     * @param yanChiYY 延迟 原因
     * @return boolean
     */
    public boolean updateChaoBiaoSjYanChiYY(String cid, String yanChiYY);

    /**
     * 上报延迟，更改表号，口径，换表日期数据
     *
     * @param cid        用户号
     * @param kouJing    口径
     * @param biaoHao    表号
     * @param huanBiaoRQ 换表日期
     * @return boolean
     */
    public boolean updateBiaoKaXXYanChiDialog(String cid, String kouJing,
                                              String biaoHao, long huanBiaoRQ);

    public List<GongDan> getGongDanXXList(String account, int type);

    public GongDan getGongDanXX(String gongdanbh);

    public GongDan getPrevGongDanXX(String gongdanbh);

    public GongDan getNextGongDanXX(String gongdanbh);

    public boolean updateGongDanXX(GongDan gongDan);

    /**
     * 插入工单抄表状态信息
     *
     * @param gd_chaoBiaoZT 工单抄表状态信息
     * @return boolean
     */
    public boolean insertGongDanChaoBiaoZT(GongDanCHAOBIAOZT gd_chaoBiaoZT);


    /**
     * 删除工单抄表状态所有数据
     */
    public void deleteGongDanYONGHUPZ();

    /**
     * 删除所有工单系统词语信息
     */
    public void deleteGongDanWords();

    /**
     * 插入系统词语信息
     *
     * @param gd_word 系统词语信息
     * @returnboolean
     */
    public boolean insertGongDanWords(GongDanWORDS gd_word);

    /**
     * 删除所有工单用户PZ
     */
    public void deleteGongDanChaoBiaoZT();

    /**
     * 插入用户PZ信息
     *
     * @param gd_YongHuPZ 用户PZ
     * @returnboolean
     */
    public boolean insertGongDanYONGHUPZ(GongDanYONGHUPZ gd_YongHuPZ);

    public List<GongDanCHAOBIAOZT> getGongDanChaoBiaoZTList();

    public List<GongDanWORDS> getGongDanWords(int belongId);

    public List<GongDanWORDS> getGongDanWords();

    public List<GongDanYONGHUPZ> getGongDanYongHuPZList();

    /**
     * 获取所有抄表备注信息
     *
     * @return List<ChaoBiaoBZ>
     */
    public List<ChaoBiaoBZ> getChaoBiaoBZList();

    public List<GongDanYONGHUPZ> getGongDanYongHuPZList(int I_LISHUID);

    public List<GongDanYONGHUPZ> getGongDanYongHuPZList(int gangDantype, int ciyuLX, String CANSHUZ);


    public List<BIAOKAXX> getCombinedQuery(ConditionInfo info);

    public List<ChaoBiaoSJ> getAllFinishedChaoBiaoSJ(int taskId, String volume, String account, int chaobiaobzYichao);

    public List<DuoMeiTXX> getAllDuoMeiTXXList(String account, int renwubh, String ch);

    public List<DuoMeiTXX> getUploadDuoMeiTXXList();

    public List<DuoMeiTXX> getAllDuoMeiTXXList(String account, int renwubh);

    public List<BIAOKAXX> getAllBiaoKaXXList();

    public List<BIAOKAXX> getBiaoKaXXListByTaskId(int taskId);

    public List<BIAOKAXX> getDelayBiaoKaXXList(String account);

    public List<ChaoBiaoSJ> getAllChaoBiaoSJ(String account);

    public List<ChaoBiaoSJ> getAllChaoBiaoSJ(String account, int taskId, String volume);

    public List<ChaoBiaoSJ> getAllChaoBiaoSJ(String account, int taskId);


    public boolean deleteRecords(String account, int taskId, String volume,String cid);

    public List<DuoMeiTXX> getAllDuoMeiTXXList(String account, String taskIdsArry);

    public boolean deleteDuoMeiTXX(String account, List<String> mediaList);

    public boolean deleteDuoMeiTXX(String account, int renwubh, String ch);

    public boolean deleteDuoMeiTXX(String account, int renwubh);

    public void deleteCHaoBiaoRW(String taskId, String account);

    public boolean deleteChaoBiaoRW(String account, int taskId, String ch);

    public boolean deleteChaoBiaoRW(String account, int taskId, int groupId);

    public void deleteChaoBiaoSJByTaskId(String account, String taskIdsArr);

    public List<ChaoBiaoRW> getRemovedChaoBiaoRW(String account, List<String> renWuBHList);

    public List<DuoMeiTXX> getDeleteDelayDuoMeiTXX(List<String> list);

    public void deleteDelayDuoMeiTXX(List<String> list);

    public boolean getDeleteDelayDuoMeiTXXAddYanChiBiao(String account, List<Integer> ids);

    public boolean deleteDuoMeiTXXByTaskId(String account, String taskIdsArry);

    public List<ChaoBiaoSJ> getAllChaoBiaoSJWithLimit(String account, int taskId, String volume, long orderNumber);

    public List<ChaoBiaoSJ> getListChaoBiaoSJWithLimit(int taskId, String volume, int i, List<String> userIds, long orderNumber);

    public List<ChaoBiaoSJ> getChaoBiaoSJListWithLimit(int taskId, String volume, List<String> userIds, long orderNumber);


    public ChaoBiaoRW getChaoBiaoRWByS_CH(String s_ch);

    public ChaoBiaoSJ getChaoBiaoSJByID(int id);

    public List<ChaoBiaoSJ> getTemporaryChaoBiaoSJ(String account);

    public void deleteTemporaryChaoBiaoSJ(String account);

    public List<BIAOKAXX> getBiaoKaXXTemporaryList(String account);

    public void deleteTemporaryBiaoKaXX(String account);

    public List<RushPayRW> getRushPayRWList(String account , int type);

    public boolean insertRushPayRW(RushPayRW rushPayRW);

    public void updateRushPayList(List<RushPayRW> rushPayRWs);

    public List<RushPayRW> getRemovedRushPayRW(String account, List<String> renWuBHList);

    public boolean deleteRushPayRW(String account, int taskId);

    public void updateRushPayTaskList(List<RushPayRW> rushPayRWList);

    public RushPayRW getNextRushPayTask(String account, int taskId);

    public RushPayRW getPreviousRushPayTask(String account, int taskId);

    public RushPayRW getRushPayRW(String account , int taskId);

    public List<DuoMeiTXX> getRushPayDuoMeiTXXList(String account, int renwubh);

    public int weiShangchuanRecordCount(long time,String account);

    public int weiShangchuanJiChaRecordCount(long time,String account);

    public JiChaRW getJiChaRW(String account, int taskId);

    public List<JiChaSJ> getListJiChaSJ(int taskId, int sortType);

    public boolean deleteJiChaSJ(String account, int renWuBH);

    public boolean deleteJiChaRW(String account, int taskId);

    public List<DuoMeiTXX> getJiChaDuoMeiTXXList(String account, int renwubh);

    public List<JiChaRW> getJiChaRWList(String account);

    /**
     * 新增稽查任务
     */
    public boolean insertJiChaRW(JiChaRW jiChaRW);

    public List<JiChaRW> getRemovedJiChaRW(String account, List<String> renWuBHList);

    public boolean updateJiChaRW(String account, int renwuBH, boolean needSync);

    public boolean updateJiChaRW(JiChaRW jiChaRW);

    public List<WaiFuCBSJ> getWaiFuCBSJList(String account, int filterType);

    public List<String> getBiaoKaXXByKeyWaiFu(String key);

    public List<WaiFuCBSJ> getWaiFuCBSJs(List<String> cids);

    public WaiFuCBSJ getWaiFuCBSJ(int taskId, String cH, String S_CID);

    public WaiFuCBSJ getPreviousWaiFuCBSJ(String account, String cid);

    public WaiFuCBSJ getNextWaiFuCBSJ(String account ,String cid);

    public void updateWaiFuCBSJList(List<WaiFuCBSJ> waiFuCBSJList);

    public void insertJiChaSJList(List<JiChaSJ> jiChaSJList, boolean needDeletingFirstly);

    public void updateJiChaSJList(List<JiChaSJ> jiChaSJList);

    public List<JiChaSJ> getJiChaSJList(int taskId, List<String> cids);

    public List<JiChaSJ> getNextUnfinishedJiChaSJWithCeNeiXH(int renWuBH, String ch);

    public List<JiChaSJ> getAllFinishedJiChaSJ(int taskId, String volume, String account, int chaobiaobzYichao);

    public List<JiChaSJ> getAllJiChaSJ(String account);

    public List<JiChaSJ> getAllJiChaSJ(String account, int taskId);

    public List<JiChaSJ> getJiChaSJCBY(String chaoBiaoY,
                                       int renWuBH,
                                       String ch,
                                       int chaoBiaoBZ,
                                       int shangChuanBZ);

    public JiChaSJ getJiChaSJ(int taskId, String cH, String S_CID);

    public JiChaSJ getPreviousJiChaSJNew(String account, int taskId, String S_CID, boolean isNotReading);

    public JiChaSJ getNextJiChaSJNew(String account, int taskId, String S_CID, boolean isNotReading);

    public void updateJiChaSJ(JiChaSJ jiChaSJ);

    public void insertWaiFuCBSJList(List<WaiFuCBSJ> waiFuCBSJList,String account);

    public List<WaiFuCBSJ> getRemoveWaiFuCBSJList(List<WaiFuCBSJ> waiFuCBSJList, String account);

    public List<BIAOKAXX> getBiaoKaXXWaiFuCBSJList(String account);

}
