package com.sh3h.mralgorithm.gas;

import java.util.Date;

public class NormalReadAlgorithmImp extends ReadAlgorithm {
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
        if (param == null) {
            outMsg = "输入参数错误！";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, param);
        }
        else if (param.get_chaobiaoztmc().contains("估计")) {
            return estimate(param);
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
        else if (param.get_chaobiaoztmc().contains("补收")
                && (param.getBuShouFS() != 3)) {
            return recharge(param);
        }

        int xinbiaoxfl = param.get_bencicm() - param.get_shangcicm();
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
        if (!param.get_chaobiaoztmc().contains("补收")) {
            param.set_feizhengcjl(0);
        }

        return new ReadingResult(isSuccess, outMsg, param);
    }

    private ReadingResult estimate(ReadingParameter param) {
        boolean isSuccess = true;
        String outMsg = null;
        Date oldDate = param.get_shangcicbrq();
        if (oldDate == null) {
            outMsg = "输入参数错误！";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, param);
        }

        Date newDate = new Date();
        long days = (newDate.getTime() - oldDate.getTime()) / (1000 * 60 * 60 * 24);
        double rijunyl = param.getRiJunYL();
        int xinbiaoxfl = (int)(rijunyl * days + 0.5);
        if (param.getGuSuanFS() == 3) {
            xinbiaoxfl = (int)(rijunyl + 0.5);
        }

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
        param.set_bencicm(xinbiaoxfl + param.get_shangcicm());
        param.set_feizhengcjl(0);

        return new ReadingResult(isSuccess, outMsg, param);
    }

    private ReadingResult recharge(ReadingParameter param) {
        boolean isSuccess = true;
        String outMsg = null;
        Date oldDate = param.get_shangcicbrq();
        if (oldDate == null) {
            outMsg = "输入参数错误！";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, param);
        }

        Date newDate = new Date();
        long days = (newDate.getTime() - oldDate.getTime()) / (1000 * 60 * 60 * 24);
        double rijunyl = param.getRiJunYL();
        int xiaofeizl = (int)(rijunyl * days + 0.5);

        int xinbiaoxfl = param.get_bencicm() - param.get_shangcicm();
        int jiubiaoxfl = 0;
        int buyalc = 0;
        int bushoul = 0;

        if (param.getJuMin() != 1) {
            buyalc = (int)((xinbiaoxfl + jiubiaoxfl) * (param.getYaLiCXS() / 100.0) + 0.5);
        }

        double yalicxs = param.getYaLiCXS();
        if (param.getBuShouFS() == 5) { // 自定义按次
            bushoul = (int)rijunyl;
            xiaofeizl = (int)(bushoul + jiubiaoxfl * (1 + yalicxs / 100.0) + xinbiaoxfl * (1 + yalicxs / 100.0) + 0.5);
            if (xiaofeizl < 0) {
                xiaofeizl = 0;
            }
        }
        else {
            bushoul = (int)(xiaofeizl - jiubiaoxfl * (1 + yalicxs / 100.0) - xinbiaoxfl * (1 + yalicxs / 100.0) + 0.5);
            if (bushoul < 0) {
                bushoul = 0;
            }
        }

        if (xiaofeizl != (xinbiaoxfl + jiubiaoxfl + buyalc + bushoul)) {
            outMsg = "请重新输入抄码或选择自定义！";
            isSuccess = false;
            return new ReadingResult(isSuccess, outMsg, param);
        }

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
