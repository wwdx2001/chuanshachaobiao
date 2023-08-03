package com.sh3h.mralgorithm.controlcheck;

import com.sh3h.mralgorithm.reading.algorithm.IReadAlgorithm;

public class CheckInPutControl {

    public CheckInPutControl() {
    }

    public static ControlItems ControlChecked(int suanfaBM) {

        /** guilin
         * 算法编码	算法名称					输入内容
         0		正常抄表算法				抄表状态、本次抄码
         1		估表算法(水量推算抄码)	    抄表状态、水量
         2		故障换表 (新表日用量推算)	抄表状态、本次抄码
         3		溢出						抄表状态、本次抄码
         4		正常换表					抄表状态、本次抄码
         5		抄表水量无关				抄表状态、本次抄码、水量
         6		无算法					    抄表状态、本次抄码、水量
         9		抄码不变水量为0			    抄表状态
         */

        /** shanghai
         * 算法编码	算法名称					输入内容
         1		正常抄表算法				抄表状态、本次抄码
         2		估计算法            	    抄表状态、水量
         3		推算 	                    抄表状态、本次抄码
         4		坏估						抄表状态、水量
         5		无量					    抄表状态
         6		定换				        抄表状态、本次抄码
         */

        /**shanghai  new
         0	正常抄表算法					抄表状态、本次抄码
         1	估表算法（水量推算抄码）		抄表状态、水量
         2	溢出（过圈）					抄表状态、本次抄码
         3	故障换表						抄表状态、本次抄码
         4	定换 正常换表				抄表状态、本次抄码
         5	抄表水量无关					抄表状态、本次抄码、水量
         6	无法算						抄表状态、本次抄码、水量
         9	本次抄码不变, 水量为0			抄表状态
         */
        switch (suanfaBM) {
            case IReadAlgorithm.ALGORITHM_NORMAL:
                return new ControlItems(true, false);
            case IReadAlgorithm.ALGORITHM_EVALUATE:
                return new ControlItems(false, true);
            case IReadAlgorithm.ALGORITHM_REVERSE:
                return new ControlItems(false, true);
            case IReadAlgorithm.ALGORITHM_CALCULATE_EVALUATE:
                return new ControlItems(true, false);
            case IReadAlgorithm.ALGORITHM_CALCULATE:
                return new ControlItems(true, false);
            case IReadAlgorithm.ALGORITHM_SWITCHINGMETER:
                return new ControlItems(true, false);
            case IReadAlgorithm.ALGORITHM_CHAOMABUBIAN:
                return new ControlItems(true, false);
            case IReadAlgorithm.ALGORITHM_DELAYING:
                return new ControlItems(true, false);
            case IReadAlgorithm.ALGORITHM_REALEVALUATE:
                return new ControlItems(false, true);
            case IReadAlgorithm.ALGORITHM_RUNOUT:
                return new ControlItems(true, false);
            case IReadAlgorithm.ALGORITHM_AVERAGEEVALUATE:
                return new ControlItems(false, true);
            default:
                return new ControlItems(true, false);
        }
    }

}