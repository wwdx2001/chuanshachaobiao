package com.sh3h.mralgorithm.reading.algorithm;


import com.sh3h.mralgorithm.reading.ReadingParameter;
import com.sh3h.mralgorithm.reading.ReadingResult;

public class RealEvaluateReadAlgorithmImp implements IReadAlgorithm {

    @Override
    public int getCode() {
        return IReadAlgorithm.ALGORITHM_REALEVALUATE;
    }

    @Override
    public ReadingResult calculate(ReadingParameter entity) {
        boolean isSuccess = true;
        String outMsg = "";

        if (entity.get_shuibiaozt() != 2) { // 0：非换表，2：换表
            isSuccess = false;
            outMsg = "该表现在状态不是换表";
            return new ReadingResult(isSuccess, outMsg, entity);
        }

        if (entity.get_bencisl() < 0) {
            outMsg = "输入的本次水量必须是大于等于零的数字";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, entity);
        }




        // 水表读数（新表读数）=新表底码+（输入水量-旧表量）
        // 旧表量=旧表抄码-上次抄码
        int bencicm = entity.get_xinbiaodm() + (entity.get_bencisl() - (entity.get_jiubiaocm() - entity.get_shangcicm()));

        //TODO xiaochao add
        if (bencicm > entity.get_liangcheng()) {
            outMsg = "本次抄码不能大于量程,本表量程为:" + entity.get_liangcheng();
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, entity);
        }

        if (bencicm < 0) {
            outMsg = "本次抄码小于零";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, entity);
        }

        entity.set_bencicm(bencicm);
        return new ReadingResult(isSuccess, outMsg, entity);
    }
}
