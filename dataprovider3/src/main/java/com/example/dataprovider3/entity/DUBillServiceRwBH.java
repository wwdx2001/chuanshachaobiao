package com.example.dataprovider3.entity;


/**
 * 任务编号
 *
 * @author xiaochao.dev@gamil.com
 * @date 2019/3/21 13:35
 */
public class DUBillServiceRwBH {

    /**
     * id : 1
     * result : {"s_MESSAGE":"success","s_RENWUS":"4,2,3,1","i_CHAOBIAOJBH":"1"}
     */

    private int id;
    private ResultBean result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * s_MESSAGE : success
         * s_RENWUS : 4,2,3,1
         * i_CHAOBIAOJBH : 1
         */

        private String s_MESSAGE;
        private String i_RENWUBH;
        private String i_CHAOBIAOJBH;

        public String getS_MESSAGE() {
            return s_MESSAGE;
        }

        public void setS_MESSAGE(String s_MESSAGE) {
            this.s_MESSAGE = s_MESSAGE;
        }

        public String getS_RENWUS() {
            return i_RENWUBH;
        }

        public void setS_RENWUS(String s_RENWUS) {
            this.i_RENWUBH = s_RENWUS;
        }

        public String getI_CHAOBIAOJBH() {
            return i_CHAOBIAOJBH;
        }

        public void setI_CHAOBIAOJBH(String i_CHAOBIAOJBH) {
            this.i_CHAOBIAOJBH = i_CHAOBIAOJBH;
        }
    }
}
