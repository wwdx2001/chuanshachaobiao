package com.sh3h.mralgorithm.gas;


public class ReadState {
    private String _code;
    private int _algorithmCode;

    /**
     * 默认构造函数
     */
    public ReadState() {
    }

    /**
     * 构造函数，指定抄表状态代码和抄表算法编号
     *
     * @param code 抄表状态
     * @param algorithmCode 抄表算法代码
     */
    public ReadState(String code, int algorithmCode)
    {
        this._code = code;
        this._algorithmCode = algorithmCode;
    }



    public String getCode() {
        return _code;
    }

    public int getReadingAlgorithmCode() {
        return _algorithmCode;
    }
}
