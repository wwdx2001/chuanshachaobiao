package com.sh3h.mralgorithm.reading.algorithm;

import com.sh3h.mralgorithm.reading.ReadingParameter;
import com.sh3h.mralgorithm.reading.ReadingResult;


public class CalculateEvaluateReadAlgorithmImp implements IReadAlgorithm {

    @Override
    public int getCode() {
        return IReadAlgorithm.ALGORITHM_CALCULATE_EVALUATE;
    }

    @Override
    public ReadingResult calculate(ReadingParameter entity) {
        boolean isSuccess = true;
        String outMsg = "";
        if (entity.get_shuibiaozt() != 2) { // 0：非换表，2：换表
            isSuccess = false;
            outMsg = "该表现在状态不是换表";//
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

        // 新表用水量=本次抄码-新表底码
        int xinbiaoysl = entity.get_bencicm() - entity.get_xinbiaodm();

        // 新表使用天数=本次抄表日期-换表日期（非回填日期）
        long xinbiaosyts = (entity.get_bencicbrq() - entity.get_huanbiaohtqrsj().getTime()) / 86400000;

        // 上个读表周期天数
        int shanggedbzqts = entity.get_shanggedbzqts();

        // 用水量 A=（新表用水量/新表使用天数）*上个读表周期天数
        int bencisl = (int)((xinbiaoysl * 1.0 / xinbiaosyts) * shanggedbzqts + 0.5);

        // 上个读表周期的估表数=上月开账水量
        int shangyuekzsl = entity.get_shangcicjsl();

        if (bencisl > shangyuekzsl) {
            // 本个和上个读表周期总天数=本月抄表日期-上上月抄表日期
            long bengehsgdbzqzts =  (entity.get_bencicbrq() - entity.get_shangshanggycbrq()) / 86400000;

            // 用水量=（新表用水量/新表使用天数）*（本个和上个读表周期总天数）-上个抄表周期的估表数
            bencisl = (int)((xinbiaoysl * 1.0 / xinbiaosyts) * bengehsgdbzqzts  - shangyuekzsl + 0.5);
        }
        else if (bencisl < shangyuekzsl) {
            // 本个读表周期天数=本次抄表日期-上次抄表日期
            long bengedbzqts =  (entity.get_bencicbrq() - entity.get_shangcicbrq()) / 86400000;

            // 用水量=（新表用水量/新表使用天数）*本个读表周期天数
            bencisl = (int)((xinbiaoysl * 1.0 / xinbiaosyts) * bengedbzqts + 0.5);
        }

        if (bencisl < 0) {
            bencisl = 0;
        }

        entity.set_bencisl(bencisl);
        return new ReadingResult(isSuccess, outMsg, entity);
    }
}
