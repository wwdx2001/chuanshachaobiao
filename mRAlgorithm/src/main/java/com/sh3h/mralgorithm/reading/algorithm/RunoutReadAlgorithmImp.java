package com.sh3h.mralgorithm.reading.algorithm;

import com.sh3h.mralgorithm.reading.ReadingParameter;
import com.sh3h.mralgorithm.reading.ReadingResult;

/**
 * 溢出算法 水量=本次抄码 + 量程 －上次抄码
 *
 * @author liukaiyu
 */
public class RunoutReadAlgorithmImp implements IReadAlgorithm {

    @Override
    public int getCode() {
        return IReadAlgorithm.ALGORITHM_RUNOUT;
    }

    @Override
    public ReadingResult calculate(ReadingParameter entity) {
        boolean isSuccess = true;
        String outMsg = "";
        if (entity.get_shuibiaozt() == 2) {
            isSuccess = false;
            outMsg = "该表为换表，抄表状态只能是换表";
            return new ReadingResult(isSuccess, outMsg, entity);
        }
        if (entity.get_bencicm() < 0) {
            outMsg = "输入的本次抄码必须是大于等于零的数字";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, entity);
        }

        //TODO xiaochao add
        if (entity.get_bencicm() > entity.get_liangcheng()) {
            outMsg = "本次抄码不能大于量程,本表量程为:" + entity.get_liangcheng();
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, entity);
        }

        entity.set_bencisl(entity.get_bencicm() + entity.get_liangcheng()
                - entity.get_shangcicm());

        if (entity.get_bencisl() < 0) {
            outMsg = "本次水量不能小于零";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, entity);
        }
        return new ReadingResult(isSuccess, outMsg, entity);
    }

}
