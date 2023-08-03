package com.sh3h.mralgorithm.gas;

import java.util.List;
import java.util.Map;

public abstract class ReadAlgorithm implements IReadAlgorithm {
    @Override
    public int getCode() {
        return IReadAlgorithm.ALGORITHM_NORMAL;
    }

    @Override
    public ReadingResult calculate(ReadingParameter param) {
        return new ReadingResult(false, "", param);
    }

    protected double calKongZhiRLF(ReadingParameter param) {
        double value = 0.0;
        if (param == null) {
            return value;
        }

        if (param.getBingLianZH() == null) {
            return value;
        }

        if (!param.getBingLianZH().equals("")) {
            value = calBingLianKZF(param);
        }
        else {
            if (param.get_chaobiaoztmc().indexOf("免补偿") > 0) {
                return value;
            }
            else if (param.getJuMin() == 1) {
                return value;
            }

            int buchangfs = param.getBuChangFS();
            switch (buchangfs) {
                case 1: // 固定值每月
                    value = calBuChangXS(param);
                    break;
                case 2: // 表能量乘补偿费系数
                {
                    double biaonengl = param.getBiaoNengL();
                    if (biaonengl > 6) {
                        double buchangxs = calBuChangXS(param);
                        value = buchangxs * biaonengl;
                    }
                    else if (biaonengl == 6) {
                        int qiyuan = param.getQiYuan();
                        if (qiyuan == 2) {
                            value = 90;
                        }
                        else {
                            value = 72;
                        }
                    }
                    else {
                        value = 60;
                    }
                }
                break;
                case 0: // 不收补偿费
                default:
                    value = 0.0;
                    break;
            }
        }

        if (value < 0) {
            value = 0.0;
        }

        return value;
    }

    private double calBingLianKZF(ReadingParameter param) {
        double value = 0.0;
        if (param == null) {
            return value;
        }

        if (param.getBingLianZH().equals("")) {
            return value;
        }

        if (!param.getIsFinalBingLian()) {
            return value;
        }

        int binglianerror = param.getBingLianError();
        String chaobiaoztmc = param.get_chaobiaoztmc();
        if (!chaobiaoztmc.contains("免补偿")) {
            binglianerror++;
        }

        double biaonengl = param.getBiaoNengL();
        if (biaonengl > 6) {
            double buchangxs = calBuChangXS(param);
            value = buchangxs * biaonengl * binglianerror;
        }
        else if (biaonengl == 6) {
            int qiyuan = param.getQiYuan();
            if (qiyuan == 2) {
                value = 90 * binglianerror;
            }
            else {
                value = 72 * binglianerror;
            }
        }
        else {
            value = 60 * binglianerror;
        }

        if (value < 0) {
            value = 0.0;
        }

        return value;
    }

    private double calBuChangXS(ReadingParameter param) {
        double value = 0.0;
        if (param == null) {
            return value;
        }

        List<Map<String, Double>> buChangXSList = param.getBuChangXSList();
        if ((buChangXSList == null) || (buChangXSList.size() <= 0)) {
            return value;
        }

        int buchangfs = param.getBuChangFS();
        switch (buchangfs) {
            case 1: // 固定值每月
            {
                int qiyuan = param.getQiYuan();
                if (qiyuan == 2) { // 天然气
                    value = getBuChangXS(buChangXSList, "天燃气固定值");
                }
                else {
                    value = getBuChangXS(buChangXSList, "人工煤气固定值");
                }
            }
            break;
            case 2: // 表能量乘补偿费系数
            {
                int qiyuan = param.getQiYuan();
                if (qiyuan == 2) { // 天然气
                    value = getBuChangXS(buChangXSList, "天燃气表能量系数");
                }
                else {
                    value = getBuChangXS(buChangXSList, "人工煤气表能量系数");
                }
            }
            break;
            case 0: // 不收补偿费
            default:
                value = 0.0;
                break;
        }

        return value;
    }

    private double getBuChangXS(List<Map<String, Double>> buChangXSList, String qiyuan) {
        double value = 0.0;
        if ((buChangXSList == null) || (qiyuan == null)) {
            return value;
        }

        for (Map<String, Double> item : buChangXSList) {
            if (item.containsKey(qiyuan)) {
                value = item.get(qiyuan).doubleValue();
                break;
            }
        }

        return value;
    }
}
