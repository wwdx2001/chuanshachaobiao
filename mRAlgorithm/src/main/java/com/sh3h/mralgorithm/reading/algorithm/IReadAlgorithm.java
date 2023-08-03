/**
 * @author qiweiwei
 */
package com.sh3h.mralgorithm.reading.algorithm;

import com.sh3h.mralgorithm.reading.ReadingParameter;
import com.sh3h.mralgorithm.reading.ReadingResult;

/**
 * 水量计算，算法接口抽象
 */
public interface IReadAlgorithm {

    //算法对应的编码

    public final static int ALGORITHM_NORMAL = 1;               // 正常
    public final static int ALGORITHM_EVALUATE = 2;             // 估表
    public final static int ALGORITHM_REVERSE = 3;            // 倒装
    public final static int ALGORITHM_CALCULATE_EVALUATE = 4;      // 推估
    public final static int ALGORITHM_CALCULATE = 5;        // 推正
    public final static int ALGORITHM_SWITCHINGMETER = 6;         // 定换
    public final static int ALGORITHM_CHAOMABUBIAN = 7;               // 无量
    public final static int ALGORITHM_DELAYING = 8;               // 延迟
    public final static int ALGORITHM_REALEVALUATE = 9;        //实发估计
    public final static int ALGORITHM_RUNOUT = 10;              //溢出
    public final static int ALGORITHM_AVERAGEEVALUATE = 11;         //平均估计

    /**
     * 算法代码，永久固定、唯一。
     *
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
