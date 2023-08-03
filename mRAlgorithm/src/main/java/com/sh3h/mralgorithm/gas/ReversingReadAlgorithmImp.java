package com.sh3h.mralgorithm.gas;

public class ReversingReadAlgorithmImp extends ReadAlgorithm {
    public ReversingReadAlgorithmImp() {

    }

    @Override
    public int getCode() {
        return IReadAlgorithm.ALGORITHM_REVERSING;
    }

    @Override
    public ReadingResult calculate(ReadingParameter param) {
        boolean isSuccess = true;
        String outMsg = "";
        if (param == null) {
            outMsg = "输入参数错误！";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, param);
        }
        else if (param.get_chaobiaoztmc().contains("估计")
                || param.get_chaobiaoztmc().contains("补收")) {
            outMsg = "算法错误！";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, param);
        }
        else if (param.get_bencicm() < 0) {
            outMsg = "输入的本次抄码必须是大于等于零的数字！";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, param);
        }
        else if (param.get_bencicm() < param.get_shangcicm()) {
            outMsg = "本次抄码不能小于上次抄码！";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, param);
        }

        int xinbiaoxfl = 0;
        int jiubiaoxfl = 0;
        int buyalc = 0;
        int bushoul = 0;
        int xiaofeizl = 0;

        if (param.getJuMin() != 1) {
            buyalc = (int)((xinbiaoxfl + jiubiaoxfl) * (param.getYaLiCXS() / 100.0) + 0.5);
        }

        if (param.getBuShouLX() == 1) {
            bushoul = 0;
        }
        else if (param.getBuShouLX() == 2) {
            bushoul = (int)param.getShiZhiBSBL();
        }
        else if (param.getBuShouLX() == 3) {
            bushoul = (int)param.getYibanSXBSL();
        }
        else {
            bushoul = 0;
        }

        xiaofeizl = xinbiaoxfl + jiubiaoxfl + buyalc + bushoul;

        param.setXinBiaoXFL(xinbiaoxfl);
        param.setJiuBiaoXFL(jiubiaoxfl);
        param.setBuYaLC(buyalc);
        param.setBuShouL(bushoul);
        param.setXiaoFeiZL(xiaofeizl);
        param.setKongZhiRLF(calKongZhiRLF(param));
        param.set_feizhengcjl(0);

        return new ReadingResult(isSuccess, outMsg, param);
    }
}
