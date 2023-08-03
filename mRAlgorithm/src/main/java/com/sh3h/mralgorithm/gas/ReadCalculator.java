package com.sh3h.mralgorithm.gas;


import java.util.HashMap;
import java.util.List;

public class ReadCalculator {
    /**
     * 默认构造函数
     */
    public ReadCalculator() {
    }

    /**
     * 根据传入的抄表参数计算抄表结果
     *
     * 通过抄表状态，获取对应的抄表算法对象，调用其计算方法 如果未找到对应的抄表算法返回失败或者抛出异常
     *
     * @param param
     *            抄表参数
     * @return 抄表计算结果
     */
    public static ReadingResult calculate(ReadingParameter param) {
        if (param == null) {
            return new ReadingResult(false, "", null);
        }

        IReadAlgorithm a = ReadAlgorithmFactory.create(param.get_chaobiaosuanfabm());

        return a.calculate(param);
    }
}
