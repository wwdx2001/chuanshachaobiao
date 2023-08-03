package com.sh3h.mralgorithm.reading.algorithm;

import com.sh3h.mralgorithm.reading.ReadingParameter;
import com.sh3h.mralgorithm.reading.ReadingResult;

/**
 * 本次抄码不变 水量为0
 * 水表状态:
 * 0 本次抄码 = 上次抄码
 * 1 本次抄码 = 旧表抄码
 * 2 本次抄码 = 新表起码
 *
 * @author liukaiyu
 */
public class ChaoMaBuBianReadAlgorithmImp implements IReadAlgorithm {

    @Override
    public int getCode() {
        return IReadAlgorithm.ALGORITHM_CHAOMABUBIAN;
    }

    @Override
    public ReadingResult calculate(ReadingParameter param) {
        String outMsg = "";
        if (param.get_shuibiaozt() == 2) {
            outMsg = "该表为换表，抄表状态只能是换表";
            return new ReadingResult(false, outMsg, param);
        }

        int shangcicm = param.get_shangcicm();
        int bencicm = param.get_bencicm();
        if (bencicm > shangcicm) {
            outMsg = "用户输入的有误，重新输入";
            return new ReadingResult(false, outMsg, param);
        }

        //TODO xiaochao add
        if (bencicm > param.get_liangcheng()) {
            outMsg = "本次抄码不能大于量程,本表量程为:" + param.get_liangcheng();
            return new ReadingResult(false, outMsg, param);
        }

        param.set_bencicm(bencicm);
        param.set_bencisl(0);
        return new ReadingResult(true, outMsg, param);
    }

}
