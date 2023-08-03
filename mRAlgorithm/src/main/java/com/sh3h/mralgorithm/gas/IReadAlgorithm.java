package com.sh3h.mralgorithm.gas;


public interface IReadAlgorithm {
    public final static int ALGORITHM_NORMAL = 0;               // 正常
    public final static int ALGORITHM_SWITCHTINGMETER = 1;     // 换表
    public final static int ALGORITHM_REVERSING = 2;            // 倒装


    /**
     * 算法代码，永久固定、唯一。
     * @return 返回算法代码
     */
    public int getCode();

    /**
     * 算法核心逻辑
     *
     * @param param 抄表计算参数对象
     * @return 返回抄表计算结果
     */
    public ReadingResult calculate(ReadingParameter param);
}
