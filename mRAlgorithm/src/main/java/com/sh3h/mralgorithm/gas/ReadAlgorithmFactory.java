package com.sh3h.mralgorithm.gas;


import java.util.HashMap;

public class ReadAlgorithmFactory {
    private static HashMap<Integer, IReadAlgorithm> _innerCache = new HashMap<Integer, IReadAlgorithm>();

    public static IReadAlgorithm create(int algorithmCode) {
        IReadAlgorithm ra = null;
        /**
         * 根据抄表算法编码 返回对应的算法实体类，并执行计算
         */
        if (!_innerCache.containsKey(algorithmCode)) {
            switch (algorithmCode) {
                case IReadAlgorithm.ALGORITHM_NORMAL:
                    ra = new NormalReadAlgorithmImp();
                    break;
                case IReadAlgorithm.ALGORITHM_SWITCHTINGMETER:
                    ra = new SwitchingMeterReadAlgorithmImp();
                    break;
                case IReadAlgorithm.ALGORITHM_REVERSING:
                    ra = new ReversingReadAlgorithmImp();
                    break;
                default:
                    ra = new NormalReadAlgorithmImp();
                    break;
            }
            _innerCache.put(algorithmCode, ra);
        } else {
            ra = _innerCache.get(algorithmCode);
        }

        return ra;
    }
}