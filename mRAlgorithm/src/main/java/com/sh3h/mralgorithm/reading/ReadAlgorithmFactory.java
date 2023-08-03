/**
 *
 */
package com.sh3h.mralgorithm.reading;

import com.sh3h.mralgorithm.reading.algorithm.AverageEvaluateReadAlgorithmImp;
import com.sh3h.mralgorithm.reading.algorithm.CalculateEvaluateReadAlgorithmImp;
import com.sh3h.mralgorithm.reading.algorithm.CalculateReadAlgorithmImp;
import com.sh3h.mralgorithm.reading.algorithm.ChaoMaBuBianReadAlgorithmImp;
import com.sh3h.mralgorithm.reading.algorithm.DelayingMeterReadAlgorithmImp;
import com.sh3h.mralgorithm.reading.algorithm.EvaluateReadAlgorithmImp;
import com.sh3h.mralgorithm.reading.algorithm.IReadAlgorithm;
import com.sh3h.mralgorithm.reading.algorithm.NormalReadAlgorithmImp;
import com.sh3h.mralgorithm.reading.algorithm.RealEvaluateReadAlgorithmImp;
import com.sh3h.mralgorithm.reading.algorithm.ReverseReadAlgorithmImp;
import com.sh3h.mralgorithm.reading.algorithm.RunoutReadAlgorithmImp;
import com.sh3h.mralgorithm.reading.algorithm.SwitchingMeterReadAlgorithmImp;

import java.util.HashMap;

/**
 * @author liukaiyu
 */
public class ReadAlgorithmFactory {

    private static HashMap<Integer, IReadAlgorithm> _innerCache = new HashMap<>();

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
                case IReadAlgorithm.ALGORITHM_EVALUATE:
                    ra = new EvaluateReadAlgorithmImp();
                    break;
                case IReadAlgorithm.ALGORITHM_REVERSE:
                    ra = new ReverseReadAlgorithmImp();
                    break;
                case IReadAlgorithm.ALGORITHM_CALCULATE_EVALUATE:
                    ra = new CalculateEvaluateReadAlgorithmImp();
                    break;
                case IReadAlgorithm.ALGORITHM_CALCULATE:
                    ra = new CalculateReadAlgorithmImp();
                    break;
                case IReadAlgorithm.ALGORITHM_SWITCHINGMETER:
                    ra = new SwitchingMeterReadAlgorithmImp();
                    break;
                case IReadAlgorithm.ALGORITHM_CHAOMABUBIAN:
                    ra = new ChaoMaBuBianReadAlgorithmImp();
                    break;
                case IReadAlgorithm.ALGORITHM_DELAYING:
                    ra = new DelayingMeterReadAlgorithmImp();
                    break;
                case IReadAlgorithm.ALGORITHM_REALEVALUATE:
                    ra = new RealEvaluateReadAlgorithmImp();
                    break;
                case IReadAlgorithm.ALGORITHM_RUNOUT:
                    ra = new RunoutReadAlgorithmImp();
                    break;
                case IReadAlgorithm.ALGORITHM_AVERAGEEVALUATE:
                    ra = new AverageEvaluateReadAlgorithmImp();
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
