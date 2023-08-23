package com.sh3h.serverprovider.entity;

import java.util.List;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2020/1/19 17:56
 */
public class BiaoWeiImage {

    /**
     * MsgCode : 0
     * MsgInfo : 成功
     * data : [{"URL":"http://10.6.87.32:4444/File202001/981027542.jpg","S_WENJIANMC":"981027542.jpg"},{"URL":"http://10.6.87.32:4444/File202001/981027542-BJ.JPG","S_WENJIANMC":"981027542-BJ.JPG"},{"URL":"http://10.6.87.32:4444/File202001/981027542-BM.JPG","S_WENJIANMC":"981027542-BM.JPG"},{"URL":"http://10.6.87.32:4444/File202001/981027542-BW.JPG","S_WENJIANMC":"981027542-BW.JPG"},{"URL":"http://10.6.87.32:4444/File202001/981027542-QF.JPG","S_WENJIANMC":"981027542-QF.JPG"}]
     */

    private String MsgCode;
    private String MsgInfo;
    private List<DataBean> data;

    public String getMsgCode() {
        return MsgCode;
    }

    public void setMsgCode(String MsgCode) {
        this.MsgCode = MsgCode;
    }

    public String getMsgInfo() {
        return MsgInfo;
    }

    public void setMsgInfo(String MsgInfo) {
        this.MsgInfo = MsgInfo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * URL : http://10.6.87.32:4444/File202001/981027542.jpg
         * S_WENJIANMC : 981027542.jpg
         */

        private String URL;
        private String S_WENJIANMC;
        private int RN;

        public int getRN() {
            return RN;
        }

        public void setRN(int RN) {
            this.RN = RN;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        public String getS_WENJIANMC() {
            return S_WENJIANMC;
        }

        public void setS_WENJIANMC(String S_WENJIANMC) {
            this.S_WENJIANMC = S_WENJIANMC;
        }
    }
}
