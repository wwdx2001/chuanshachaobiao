/**
 * @author qiweiwei
 */
package com.sh3h.mralgorithm.reading.algorithm;

import com.sh3h.mralgorithm.reading.ReadingParameter;
import com.sh3h.mralgorithm.reading.ReadingResult;

/**
 * 正常水量计算算法
 */
public class NormalReadAlgorithmImp implements IReadAlgorithm {

    /**
     * 默认构造函数
     */
    public NormalReadAlgorithmImp() {
    }

    @Override
    public int getCode() {
        return IReadAlgorithm.ALGORITHM_NORMAL;
    }

    @Override
    public ReadingResult calculate(ReadingParameter param) {
        boolean isSuccess = true;
        String outMsg = "";

        if (param.get_shuibiaozt() == 2) {
            isSuccess = false;
            outMsg = "该表为换表，抄表状态只能是换表";
            return new ReadingResult(isSuccess, outMsg, param);
        }

        if (param.get_bencicm() < 0) {
            outMsg = "输入的本次抄码必须是大于等于零的数字";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, param);
        }


        //TODO 原始代码被注释，现重新放开
        if (param.get_bencicm() > param.get_liangcheng()) {
            outMsg = "本次抄码不能大于量程,本表量程为:" + param.get_liangcheng();
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, param);
        }

        param.set_bencisl((param.get_bencicm() - param.get_shangcicm()));
        /*				* param.get_shuibiaobeil());*/
        if (param.get_bencisl() <= 0) {
            outMsg = "本次抄码不能小于或等于上次抄码";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, param);
        }
        return new ReadingResult(isSuccess, outMsg, param);
    }
}