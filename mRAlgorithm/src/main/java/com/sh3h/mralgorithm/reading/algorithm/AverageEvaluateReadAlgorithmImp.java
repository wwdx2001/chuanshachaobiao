package com.sh3h.mralgorithm.reading.algorithm;


import com.sh3h.mralgorithm.reading.ReadingParameter;
import com.sh3h.mralgorithm.reading.ReadingResult;

public class AverageEvaluateReadAlgorithmImp implements IReadAlgorithm {

    @Override
    public int getCode() {
        return IReadAlgorithm.ALGORITHM_AVERAGEEVALUATE;
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



        // 旧表读数为空
        // 旧表量=输入水量/本个抄表周期天数*（换表日期-上次抄表日期）
        // 新表读数=新表底码+输入水量-旧表量

        // 本个抄表周期天数
        long bengecbzqts = (entity.get_bencicbrq() - entity.get_shangcicbrq()) / 86400000;
        // 换表日期-上次抄表日期
        long jiubiaoysts = (entity.get_huanbiaohtqrsj().getTime() - entity.get_shangcicbrq()) / 86400000;
        // 旧表量=输入水量/本个抄表周期天数*（换表日期-上次抄表日期）
        int jiubiaol = (int)(entity.get_bencisl() * 1.0 / bengecbzqts * jiubiaoysts + 0.5);
        // 新表读数=新表底码+输入水量-旧表量
        int bencicm = entity.get_xinbiaodm() + entity.get_bencisl() - jiubiaol;
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
