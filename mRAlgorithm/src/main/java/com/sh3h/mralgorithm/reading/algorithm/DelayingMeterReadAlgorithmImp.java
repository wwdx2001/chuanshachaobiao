package com.sh3h.mralgorithm.reading.algorithm;


import com.sh3h.mralgorithm.reading.ReadingParameter;
import com.sh3h.mralgorithm.reading.ReadingResult;

public class DelayingMeterReadAlgorithmImp implements IReadAlgorithm {

    @Override
    public int getCode() {
        return IReadAlgorithm.ALGORITHM_DELAYING;
    }

    @Override
    public ReadingResult calculate(ReadingParameter entity) {
        boolean isSuccess = true;
        String outMsg = "";
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

        entity.set_bencisl(0);
        return new ReadingResult(isSuccess, outMsg, entity);
    }
}
