package com.sh3h.datautil.data.remote;


import android.util.Xml;

import com.sh3h.dataprovider.schema.ChaoBiaoZTColumns;
import com.sh3h.dataprovider.schema.ChaoBiaoZTFLColumns;
import com.sh3h.dataprovider.schema.CiYuXXColumns;
import com.sh3h.dataprovider.schema.DingEJJBLColumns;
import com.sh3h.dataprovider.schema.FeiYongZCColumns;
import com.sh3h.dataprovider.schema.JianHaoColumns;
import com.sh3h.dataprovider.schema.JianHaoMXColumns;
import com.sh3h.dataprovider.schema.Tables;
import com.sh3h.datautil.data.entity.DUChaoBiaoZT;
import com.sh3h.datautil.data.entity.DUChaoBiaoZTFL;
import com.sh3h.datautil.data.entity.DUCiYuXX;
import com.sh3h.datautil.data.entity.DUDingEJJBL;
import com.sh3h.datautil.data.entity.DUFeiYongZC;
import com.sh3h.datautil.data.entity.DUFileResult;
import com.sh3h.datautil.data.entity.DUJianHao;
import com.sh3h.datautil.data.entity.DUJianHaoMX;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.db.DbHelper;
import com.sh3h.datautil.event.DataBusEvent;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.datautil.util.FileUtil;
import com.sh3h.datautil.util.ZipUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Parser {
    private static final String TAG = "Parser";
    private static final String CONFIG = "config";
    private static final String DATA = "data";
    private static final String INPUT_ENCODING = "UTF-8";
    private static final String XML_SUFFIX = ".xml";
    private static final String APK_NAME = "meterreading";
    private static final String APK_SUFFIX = ".patch";

    private final DbHelper mDbHelper;
    private final ConfigHelper mConfigHelper;
    private final EventPosterHelper mEventPosterHelper;

    public Parser(DbHelper dbHelper, ConfigHelper configHelper, EventPosterHelper eventPosterHelper) {
        mDbHelper = dbHelper;
        mConfigHelper = configHelper;
        mEventPosterHelper = eventPosterHelper;
    }

    public void parseData(DUFileResult duFileResult) {
        LogUtil.i(TAG, "---xml2db---");
        postEvent(DataBusEvent.ParserResult.OperationType.DATA_START, true, "start");

        if ((duFileResult == null)
                || (TextUtil.isNullOrEmpty(duFileResult.getName()))
                || (TextUtil.isNullOrEmpty(duFileResult.getPath()))
                || (mDbHelper == null)
                || (mConfigHelper == null)
                || (mEventPosterHelper == null)) {
            LogUtil.i(TAG, "---xml2db null---");
            postEvent(DataBusEvent.ParserResult.OperationType.DATA_END, false, "xml2db null");
            return;
        }

        String destFolder = getDestFolder(duFileResult.getName());
        if (TextUtil.isNullOrEmpty(destFolder)) {
            LogUtil.i(TAG, "---destination folder is error---");
            postEvent(DataBusEvent.ParserResult.OperationType.DATA_END, false,
                    "destination folder is error");
            return;
        }

        if (!unzipFile(duFileResult.getPath(), destFolder)) {
            LogUtil.i(TAG, "---failure to unzip data.zip---");
            postEvent(DataBusEvent.ParserResult.OperationType.DATA_END, false,
                    "failure to unzip data.zip");
            return;
        }

        removeConfigFiles(destFolder);

        File[] files = getXmlFiles(destFolder);
        if (files == null) {
            LogUtil.i(TAG, "---getXmlFiles is null---");
            postEvent(DataBusEvent.ParserResult.OperationType.DATA_END, false,
                    "getXmlFiles is null");
            return;
        } else if (files.length == 0) {
            LogUtil.i(TAG, "---getXmlFiles length is 0---");
            postEvent(DataBusEvent.ParserResult.OperationType.DATA_END, false,
                    "getXmlFiles length is 0");
            return;
        }

        for (File file : files) {
            String name = file.getName();
            if (TextUtil.isNullOrEmpty(name)) {
                continue;
            }

            if (name.equals(Tables.CB_ChaoBiaoZT + XML_SUFFIX)) {
                List<DUChaoBiaoZT> duChaoBiaoZTList = AnalyzeChaoBiaoZT(file.getPath());
                if (!mDbHelper.saveChaoBiaoZTList(duChaoBiaoZTList)) {
                    postEvent(DataBusEvent.ParserResult.OperationType.DATA_DOING, false,
                            "failure to saveChaoBiaoZTList");
                }
            } else if (name.equals(Tables.CB_ChAOBIAOZTFL + XML_SUFFIX)) {
                List<DUChaoBiaoZTFL> duChaoBiaoZTFLList = AnalyzeChaoBiaoZTFL(file.getPath());
                if (!mDbHelper.saveChaoBiaoZTFLList(duChaoBiaoZTFLList)) {
                    postEvent(DataBusEvent.ParserResult.OperationType.DATA_DOING, false,
                            "failure to saveChaoBiaoZTFLList");
                }
            } else if (name.equals(Tables.CY_CiYuXX + XML_SUFFIX)) {
                List<DUCiYuXX> duCiYuXXList = AnalyzeCiYuXX(file.getPath());
                if (!mDbHelper.saveCiYuXXList(duCiYuXXList)) {
                    postEvent(DataBusEvent.ParserResult.OperationType.DATA_DOING, false,
                            "failure to saveCiYuXXList");
                }
            } else if (name.equals(Tables.JG_DingEJJBL + XML_SUFFIX)) {
                List<DUDingEJJBL> duDingEJJBLList = AnalyzeDingEJJBL(file.getPath());
                if (!mDbHelper.saveDingEJJBLList(duDingEJJBLList)) {
                    postEvent(DataBusEvent.ParserResult.OperationType.DATA_DOING, false,
                            "failure to saveDingEJJBLList");
                }
            } else if (name.equals(Tables.JG_FeiYongZC + XML_SUFFIX)) {
                List<DUFeiYongZC> duFeiYongZCList = AnalyzeFeiYongZC(file.getPath());
                if (!mDbHelper.saveFeiYongZCList(duFeiYongZCList)) {
                    postEvent(DataBusEvent.ParserResult.OperationType.DATA_DOING, false,
                            "failure to saveFeiYongZCList");
                }
            } else if (name.equals(Tables.JG_JianHao + XML_SUFFIX)) {
                List<DUJianHao> duJianHaoList = AnalyzeJianHao(file.getPath());
                if (!mDbHelper.saveJianHaoList(duJianHaoList)) {
                    postEvent(DataBusEvent.ParserResult.OperationType.DATA_DOING, false,
                            "failure to saveJianHaoList");
                }
            } else if (name.equals(Tables.JG_JianHaoMX + XML_SUFFIX)) {
                List<DUJianHaoMX> duJianHaoMXList = AnalyzeJianHaoMX(file.getPath());
                if (!mDbHelper.saveJianHaoMXList(duJianHaoMXList)) {
                    postEvent(DataBusEvent.ParserResult.OperationType.DATA_DOING, false,
                            "failure to saveJianHaoMXList");
                }
            }
        }

        deleteFiles(new File(duFileResult.getPath()), new File(destFolder));
        boolean b = mConfigHelper.saveDataVersion(duFileResult.getVersion());
        postEvent(DataBusEvent.ParserResult.OperationType.DATA_END, b, "end");
    }

    public void unzipFile(DUFileResult duFileResult) {
        LogUtil.i(TAG, "---unzipFile---");
        postEvent(DataBusEvent.ParserResult.OperationType.APK_START, true, "start");

        if ((duFileResult == null)
                || (TextUtil.isNullOrEmpty(duFileResult.getName()))
                || (TextUtil.isNullOrEmpty(duFileResult.getPath()))
                || (mDbHelper == null)
                || (mConfigHelper == null)
                || (mEventPosterHelper == null)) {
            LogUtil.i(TAG, "---unzipFile null---");
            postEvent(DataBusEvent.ParserResult.OperationType.APK_END, false, "xml2db null");
            return;
        }

        String destFolder = getDestFolder(duFileResult.getName());
        if (TextUtil.isNullOrEmpty(destFolder)) {
            LogUtil.i(TAG, "---destination folder is error---");
            postEvent(DataBusEvent.ParserResult.OperationType.APK_END, false,
                    "destination folder is error");
            return;
        }

        if (!unzipFile(duFileResult.getPath(), destFolder)) {
            LogUtil.i(TAG, "---failure to unzip data.zip---");
            postEvent(DataBusEvent.ParserResult.OperationType.APK_END, false,
                    "failure to unzip data.zip");
            return;
        }

        String path = getApkPath(destFolder);
        if (TextUtil.isNullOrEmpty(path)) {
            postEvent(DataBusEvent.ParserResult.OperationType.APK_END, false, "");
        } else {
            postEvent(DataBusEvent.ParserResult.OperationType.APK_END, true, path);
        }
    }

    public boolean writeAccount(File file, String account){
        if (file == null || TextUtil.isNullOrEmpty(account)){
            LogUtil.i(TAG, "---writeAccount param is null---");
            return false;
        }

        String fileName = file.getName();
        if (TextUtil.isNullOrEmpty(fileName)) {
            LogUtil.i(TAG, "---writeAccount name is null---");
            return false;
        }

        if (!fileName.equals(Tables.CBJPZ + XML_SUFFIX)){
            LogUtil.i(TAG, "---writeAccount name is error---");
            return false;
        }


        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            //update attribute value
            updateAttributeValue(doc, account);

            //write the updated document to file or console
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            System.out.println("XML file updated successfully");
            return true;
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    private void updateAttributeValue(Document doc, String account) {
        NodeList configs = doc.getElementsByTagName("CBJConfig");
        if (configs == null || configs.getLength() != 1){
            LogUtil.i(TAG, "---updateAttributeValue configs is error---");
            return;
        }

        Element config = (Element) configs.item(0);
        NodeList pdas = config.getElementsByTagName("PDA");
        if (pdas == null || pdas.getLength() != 1){
            LogUtil.i(TAG, "---updateAttributeValue pdas is error---");
            return;
        }

        Element pda = (Element) pdas.item(0);
        pda.setAttribute("ID", account);
    }

    private boolean unzipFile(String zipFile, String destFolder) {
        try {
            ZipUtil.UnZipFolder(zipFile, destFolder);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void removeConfigFiles(String srcFolder) {
        File[] files = getConfigFiles(srcFolder);
        if ((files == null) || (files.length <= 0)) {
            return;
        }

        File destFolder = mConfigHelper.getConfigFolderPath();
        boolean b = false;
        for (File file : files) {
            b = file.renameTo(new File(destFolder, file.getName()));
        }

        mConfigHelper.getOtherConfig().setRead(false);
        mConfigHelper.getSystemConfig().setRead(false);
    }

    private File[] getConfigFiles(String folder) {
        File[] childFiles = new File(folder).listFiles();
        if (childFiles.length == 0) {
            return null;
        }
        File path = new File(childFiles[0].getPath(), "config");
        return path.listFiles();
    }

    private File[] getXmlFiles(String destFolder) {
        File[] childFiles = new File(destFolder).listFiles();
        if (childFiles.length == 0) {
            return null;
        }
        File path = new File(childFiles[0].getPath(), "data");
        return path.listFiles();
    }

    private String getApkPath(String destFolder) {
        File path = new File(destFolder);
        File[] files = path.listFiles();
        if ((files == null) || (files.length <= 0)) {
            return null;
        }

        for (File file : files) {
            String name = file.getName().toLowerCase();
            if ((name.indexOf(APK_NAME) == 0)
                    && (name.lastIndexOf(APK_SUFFIX) > 0)) {
                return file.getPath();
            }
        }

        return null;
    }

    // update/data_v10
    private String getDestFolder(String name) {
        if (TextUtil.isNullOrEmpty(name)) {
            return null;
        }

        File path = mConfigHelper.getUpdateFolderPath();
        if (!path.exists()) {
            path.mkdir();
        }

        name = name.substring(0, name.lastIndexOf(".zip"));
        if (TextUtil.isNullOrEmpty(name)) {
            return null;
        }

        path = new File(path, name);
        if (!path.exists()) {
            path.mkdir();
        }

        return path.getPath();
    }

    /**
     * 解析抄表状态数据文件
     */
    private List<DUChaoBiaoZT> AnalyzeChaoBiaoZT(String filePath) {
        List<DUChaoBiaoZT> list = new ArrayList<>();
        DUChaoBiaoZT chaoBiaoZT = null;
        try {
            InputStream fis = new FileInputStream(filePath);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(fis, INPUT_ENCODING);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (Tables.CB_ChaoBiaoZT.equals(name))
                            chaoBiaoZT = new DUChaoBiaoZT();
                        else if (ChaoBiaoZTColumns.I_ZHUANGTAIBM.equals(name))
                            chaoBiaoZT.setZhuangtaibm(TextUtil.getInt(parser.nextText()));
                        else if (ChaoBiaoZTColumns.I_SHUILIANGSFBM.equals(name))
                            chaoBiaoZT.setShuiliangsfbm(TextUtil.getInt(parser.nextText()));
                        else if (ChaoBiaoZTColumns.I_ZHUANGTAIFLBM.equals(name))
                            chaoBiaoZT.setZhuangtaiflbm(TextUtil.getInt(parser.nextText()));
                        else if (ChaoBiaoZTColumns.S_ZHUANGTAIMC.equals(name))
                            chaoBiaoZT.setZhuangtaimc(TextUtil.getString(parser.nextText()));
                        else if (ChaoBiaoZTColumns.S_KUAIJIEJPC.equals(name))
                            chaoBiaoZT.setKuaijiejpc(TextUtil.getString(parser.nextText()));
                        break;
                    case XmlPullParser.END_TAG:
                        if (Tables.CB_ChaoBiaoZT.equals(name)) {
                            list.add(chaoBiaoZT);
                            chaoBiaoZT = null;
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析抄表状态分类文件数据
     */
    private List<DUChaoBiaoZTFL> AnalyzeChaoBiaoZTFL(String filePath) {
        List<DUChaoBiaoZTFL> list = new ArrayList<>();
        DUChaoBiaoZTFL chaobiaoZTFL = null;
        try {
            InputStream fis = new FileInputStream(filePath);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(fis, INPUT_ENCODING);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (Tables.CB_ChAOBIAOZTFL.equals(name))
                            chaobiaoZTFL = new DUChaoBiaoZTFL();
                        else if (ChaoBiaoZTFLColumns.I_FENLEIBM.toUpperCase().equals(name))
                            chaobiaoZTFL.setFenleibm(TextUtil.getInt(parser.nextText()));
                        else if (ChaoBiaoZTFLColumns.S_FENLEIMC.toUpperCase().equals(name))
                            chaobiaoZTFL.setFenleimc(TextUtil.getString(parser.nextText()));
                        break;
                    case XmlPullParser.END_TAG:
                        if (Tables.CB_ChAOBIAOZTFL.equals(name)) {
                            list.add(chaobiaoZTFL);
                            chaobiaoZTFL = null;
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析词语信息文件数据
     */
    private List<DUCiYuXX> AnalyzeCiYuXX(String filePath) {
        List<DUCiYuXX> list = new ArrayList<>();
        DUCiYuXX duCiYuxx = null;
        try {
            InputStream fis = new FileInputStream(filePath);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(fis, INPUT_ENCODING);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (Tables.CY_CiYuXX.equals(name))
                            duCiYuxx = new DUCiYuXX();
                        else if (CiYuXXColumns.ID.equals(name))
                            duCiYuxx.setId(TextUtil.getInt(parser.nextText()));
                        else if (CiYuXXColumns.WORDSID.equals(name))
                            duCiYuxx.setWordsid(TextUtil.getInt(parser.nextText()));
                        else if (CiYuXXColumns.WORDSCONTENT.equals(name))
                            duCiYuxx.setWordscontent(TextUtil.getString(parser.nextText()));
                        else if (CiYuXXColumns.WORDSVALUE.equals(name))
                            duCiYuxx.setWordsvalue(TextUtil.getString(parser.nextText()));
                        else if (CiYuXXColumns.WORDSREMARK.equals(name))
                            duCiYuxx.setWordsremark(TextUtil.getString(parser.nextText()));
                        else if (CiYuXXColumns.BELONGID.equals(name))
                            duCiYuxx.setBelongid(TextUtil.getInt(parser.nextText()));
                        else if (CiYuXXColumns.SORTID.equals(name))
                            duCiYuxx.setSortid(TextUtil.getInt(parser.nextText()));
                        else if (CiYuXXColumns.ISACTIVE.equals(name))
                            duCiYuxx.setIsactive(TextUtil.getInt(parser.nextText()));
                        break;
                    case XmlPullParser.END_TAG:
                        if (Tables.CY_CiYuXX.equals(name)) {
                            list.add(duCiYuxx);
                            duCiYuxx = null;
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析定额加价倍率表文件数据
     */
    private List<DUDingEJJBL> AnalyzeDingEJJBL(String filePath) {
        List<DUDingEJJBL> dingEJJBL_list = new ArrayList<>();
        DUDingEJJBL dingEJJBL = null;
        try {
            InputStream fis = new FileInputStream(filePath);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(fis, INPUT_ENCODING);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (Tables.JG_DingEJJBL.equals(name))
                            dingEJJBL = new DUDingEJJBL();
                        else if (DingEJJBLColumns.ID.equals(name))
                            dingEJJBL.setId(TextUtil.getInt(parser.nextText()));
                        else if (DingEJJBLColumns.N_BEIL.equals(name))
                            dingEJJBL.setBeil(TextUtil.getDouble(parser.nextText()));
                        else if (DingEJJBLColumns.I_KAISHIFLOOR.equals(name))
                            dingEJJBL.setKaishifloor(TextUtil.getInt(parser.nextText()));
                        else if (DingEJJBLColumns.I_JIESHUFLOOR.equals(name))
                            dingEJJBL.setJieshufloor(TextUtil.getInt(parser.nextText()));
                        break;
                    case XmlPullParser.END_TAG:
                        if (Tables.JG_DingEJJBL.equals(name)) {
                            dingEJJBL_list.add(dingEJJBL);
                            dingEJJBL = null;
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dingEJJBL_list;
    }

    /**
     * 解析费用组成文件数据
     *
     * @return List<FeiYongZC>
     */
    private List<DUFeiYongZC> AnalyzeFeiYongZC(String filePath) {
        List<DUFeiYongZC> feiYongZC_list = new ArrayList<>();
        DUFeiYongZC feiYongZC = null;
        try {
            InputStream fis = new FileInputStream(filePath);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(fis, INPUT_ENCODING);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (Tables.JG_FeiYongZC.equals(name))
                            feiYongZC = new DUFeiYongZC();
                        else if (FeiYongZCColumns.ID.equals(name))
                            feiYongZC.setId(TextUtil.getInt(parser.nextText()));
                        else if (FeiYongZCColumns.I_TIAOJIAH.equals(name))
                            feiYongZC.setTiaojiah(TextUtil.getInt(parser.nextText()));
                        else if (FeiYongZCColumns.I_FEIYONGID.equals(name))
                            feiYongZC.setFeiyongid(TextUtil.getInt(parser.nextText()));
                        else if (FeiYongZCColumns.S_FEIYONGMC.equals(name))
                            feiYongZC.setFeiyongmc(TextUtil.getString(parser.nextText()));
                        else if (FeiYongZCColumns.N_JIAGE.equals(name))
                            feiYongZC.setJiage(TextUtil.getDouble(parser.nextText()));
                        else if (FeiYongZCColumns.I_FEIYONGDLID.equals(name))
                            feiYongZC.setFeiyongdlid(TextUtil.getInt(parser.nextText()));
                        else if (FeiYongZCColumns.N_XISHU.equals(name))
                            feiYongZC.setXishu(TextUtil.getDouble(parser.nextText()));
                        break;
                    case XmlPullParser.END_TAG:
                        if (Tables.JG_FeiYongZC.equals(name)) {
                            feiYongZC_list.add(feiYongZC);
                            feiYongZC = null;
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return feiYongZC_list;
    }

    /**
     * 解析简号文件数据
     */
    private List<DUJianHao> AnalyzeJianHao(String filePath) {
        List<DUJianHao> jianHao_list = new ArrayList<>();
        DUJianHao jianHao = null;
        try {
            InputStream fis = new FileInputStream(filePath);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(fis, INPUT_ENCODING);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (Tables.JG_JianHao.equals(name))
                            jianHao = new DUJianHao();
                        else if (JianHaoColumns.ID.equals(name))
                            jianHao.setId(TextUtil.getInt(parser.nextText()));
                        else if (JianHaoColumns.TIAOJIAH.equals(name))
                            jianHao.setTiaojiah(TextUtil.getInt(parser.nextText()));
                        else if (JianHaoColumns.DALEI.equals(name))
                            jianHao.setDalei(TextUtil.getString(parser.nextText()));
                        else if (JianHaoColumns.ZHONGLEI.equals(name))
                            jianHao.setZhonglei(TextUtil.getString(parser.nextText()));
                        else if (JianHaoColumns.XIAOLEI.equals(name))
                            jianHao.setXiaolei(TextUtil.getString(parser.nextText()));
                        else if (JianHaoColumns.JIANHAO.equals(name))
                            jianHao.setJianhao(TextUtil.getString(parser.nextText()));
                        else if (JianHaoColumns.JIETIS.equals(name))
                            jianHao.setJietis(TextUtil.getInt(parser.nextText()));
                        else if (JianHaoColumns.BEIZHU.equals(name))
                            jianHao.setBeizhu(TextUtil.getString(parser.nextText()));
                        break;
                    case XmlPullParser.END_TAG:
                        if (Tables.JG_JianHao.equals(name)) {
                            jianHao_list.add(jianHao);
                            jianHao = null;
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jianHao_list;
    }

    /**
     * 解析简号明细信息文件数据
     */
    private List<DUJianHaoMX> AnalyzeJianHaoMX(String filePath) {
        List<DUJianHaoMX> jianHaoMX_list = new ArrayList<>();
        DUJianHaoMX jianHaoMX = null;
        try {
            InputStream fis = new FileInputStream(filePath);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(fis, INPUT_ENCODING);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (Tables.JG_JianHaoMX.equals(name))
                            jianHaoMX = new DUJianHaoMX();
                        else if (JianHaoMXColumns.ID.equals(name))
                            jianHaoMX.setId(TextUtil.getInt(parser.nextText()));
                        else if (JianHaoMXColumns.TIAOJIAH.equals(name))
                            jianHaoMX.setTiaojiah(TextUtil.getInt(parser.nextText()));
                        else if (JianHaoMXColumns.JIANHAO.equals(name))
                            jianHaoMX.setJianhao(TextUtil.getString(parser.nextText()));
                        else if (JianHaoMXColumns.FEIYONGDLID.equals(name))
                            jianHaoMX.setFeiyongdlid(TextUtil.getInt(parser.nextText()));
                        else if (JianHaoMXColumns.FEIYONGID.equals(name))
                            jianHaoMX.setFeiyongid(TextUtil.getInt(parser.nextText()));
                        else if (JianHaoMXColumns.QISHIY.equals(name))
                            jianHaoMX.setQishiy(TextUtil.getInt(parser.nextText()));
                        else if (JianHaoMXColumns.JIESHUY.equals(name))
                            jianHaoMX.setJieshuy(TextUtil.getInt(parser.nextText()));
                        else if (JianHaoMXColumns.KAISHISL.equals(name))
                            jianHaoMX.setKaishisl(TextUtil.getInt(parser.nextText()));
                        else if (JianHaoMXColumns.JIESHUSL.equals(name))
                            jianHaoMX.setJieshusl(TextUtil.getInt(parser.nextText()));
                        else if (JianHaoMXColumns.JIETIJB.equals(name))
                            jianHaoMX.setJietijb(TextUtil.getInt(parser.nextText()));
                        else if (JianHaoMXColumns.ZHEKOUL.equals(name))
                            jianHaoMX.setZhekoul(TextUtil.getDouble(parser.nextText()));
                        else if (JianHaoMXColumns.ZHEKOULX.equals(name))
                            jianHaoMX.setZhekoulx(TextUtil.getInt(parser.nextText()));
                        else if (JianHaoMXColumns.JIAGE.equals(name))
                            jianHaoMX.setJiage(TextUtil.getDouble(parser.nextText()));
                        break;
                    case XmlPullParser.END_TAG:
                        if (Tables.JG_JianHaoMX.equals(name)) {
                            jianHaoMX_list.add(jianHaoMX);
                            jianHaoMX = null;
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jianHaoMX_list;
    }

    private void deleteFiles(File zipFile, File destFolder) {
        try {
            FileUtil.deleteFile(zipFile);
            FileUtil.deleteFile(destFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postEvent(DataBusEvent.ParserResult.OperationType operationType,
                           boolean isSuccess, String message) {
        if ((mEventPosterHelper != null)
                && (!TextUtil.isNullOrEmpty(message))) {
            mEventPosterHelper.postEventSafely(new DataBusEvent.ParserResult(
                    operationType, isSuccess, message));
        }
    }
}
