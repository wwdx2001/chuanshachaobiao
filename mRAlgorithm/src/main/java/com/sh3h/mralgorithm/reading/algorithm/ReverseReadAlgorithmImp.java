package com.sh3h.mralgorithm.reading.algorithm;


import com.sh3h.mralgorithm.reading.ReadingParameter;
import com.sh3h.mralgorithm.reading.ReadingResult;

public class ReverseReadAlgorithmImp implements IReadAlgorithm {

    @Override
    public int getCode() {
        return IReadAlgorithm.ALGORITHM_REVERSE;
    }

    @Override
    public ReadingResult calculate(ReadingParameter entity) {
        boolean isSuccess = true;
        String outMsg = "";

        if(entity.get_shuibiaozt() == 2){
            isSuccess = false;
            outMsg = "该表为换表，抄表状态只能是换表";
            return new ReadingResult(isSuccess, outMsg, entity);
        }

        if (entity.get_bencisl() < 0) {
            outMsg = "输入的本次水量必须是大于等于零的数字";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, entity);
        }



        int bencicm = entity.get_bencisl() + entity.get_shangcicm();
        if (bencicm < 0) {
            outMsg = "本次抄码小于零";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, entity);
        }

        //TODO xiaochao add
        if (bencicm > entity.get_liangcheng()) {
            outMsg = "本次抄码不能大于量程,本表量程为:" + entity.get_liangcheng();
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, entity);
        }

        entity.set_bencicm(bencicm);
        return new ReadingResult(isSuccess, outMsg, entity);
    }
}
