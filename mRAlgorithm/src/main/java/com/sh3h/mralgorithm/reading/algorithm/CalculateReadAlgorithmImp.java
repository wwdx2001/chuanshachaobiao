package com.sh3h.mralgorithm.reading.algorithm;

import com.sh3h.mralgorithm.reading.ReadingParameter;
import com.sh3h.mralgorithm.reading.ReadingResult;

/**
 * 用水量 = （新表抄码 / 新表用水天数） *  (旧表用水天数 + 新表用水天数)
 *
 * @author liukaiyu
 *
 */
public class CalculateReadAlgorithmImp implements IReadAlgorithm {

    @Override
    public int getCode() {
        return IReadAlgorithm.ALGORITHM_CALCULATE;
    }

    @Override
    public ReadingResult calculate(ReadingParameter entity) {
        boolean isSuccess = true;
        String outMsg = "";

        if (entity.get_shuibiaozt() <= 0 || entity.get_shuibiaozt() > 2) {
            isSuccess = false;
            outMsg = "该表现在状态不是换表";
            return new ReadingResult(isSuccess, outMsg, entity);
        }
        if (entity.get_bencicm() < 0) {
            outMsg = "输入的本次抄码必须是大于等于零的数字";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, entity);
        }

        if (entity.get_huanbiaohtqrsj().getTime() > entity.get_bencicbrq()) {
            isSuccess = false;
            outMsg = "换表日期必须小于本次抄表日期";
            return new ReadingResult(isSuccess, outMsg, entity);
        }
        if (entity.get_huanbiaohtqrsj().getTime() < entity.get_shangcicbrq()) {
            isSuccess = false;
            outMsg = "换表日期必须大于上次抄表日期";
            return new ReadingResult(isSuccess, outMsg, entity);
        }

        int bencicm = entity.get_bencicm();
        //TODO 原始代码被注释，现重新放开
        if (bencicm > entity.get_liangcheng()) {
            outMsg = "本次抄码不能大于量程,本表量程为:" + entity.get_liangcheng();
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, entity);
        }
        long xinbiaoysts = (entity.get_bencicbrq() - entity.get_huanbiaohtqrsj().getTime()) / 86400000;
        long jiubiaoysts = (entity.get_huanbiaohtqrsj().getTime() - entity.get_shangcicbrq()) / 86400000;
        int bencisl = (int)((bencicm * 1.0 / xinbiaoysts) * (jiubiaoysts + xinbiaoysts) * entity.get_shuibiaobeil() + 0.5);

        entity.set_bencisl(bencisl);
        if (entity.get_bencisl() < 0) {
            isSuccess = false;
            outMsg = "本次水量不能小于零";
            return new ReadingResult(isSuccess, outMsg, entity);
        }

        return new ReadingResult(isSuccess, outMsg, entity);
    }
}
