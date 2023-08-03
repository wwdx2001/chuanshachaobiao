/**
 * @author liukaiyu
 *
 */
package com.sh3h.mralgorithm.billing;

import java.util.List;

/**
 * 开账通用计算公式
 *
 */
public class BillingCalculator {
    private static final int EACH_PERSON_TON = 8;
    //private List<BillingParameter> billentitylist;

    //public BillingCalculator(List<BillingParameter> params) {
    //	this.billentitylist = params;
    //}

    public static BillingResult calculate(BillingParameterEx billingParameterEx) {
        BillingResult billingResult = new BillingResult(false, 0.0);

        if ((billingParameterEx == null) || (billingParameterEx.getBillingParameterList() == null)) {
            return billingResult;
        }

        List<BillingParameter> billingParameterList = billingParameterEx.getBillingParameterList();
        // 判断是否阶梯
        if (isJieTi(billingParameterList)) {
            // 处理多人口
            processPopulation(billingParameterList, billingParameterEx.getRenkous());
        }

        // 处理抄表周期
        processChaobiaozq(billingParameterList, billingParameterEx.getChaobiaozq());

        // 计算金额
        double cash = calculateCash(billingParameterEx);

        // 处理优惠
        cash = processYouhui(billingParameterEx, cash);
        if (cash < 1e-10) {
            cash = 0.0;
        }
        else {
            cash += 0.0005;
        }

        String temp = String.format("%.3f", cash);
        cash = Double.parseDouble(temp);
        //n_total = Math.ceil(n_total * 100) / 100; // 保留两位小数 （已经为四舍五入）

        return new BillingResult(true, cash);

        /**
         * 计算账务营业账子表中每种费用组成对应的金额（含阶梯）
         */
		/*double n_total = 0;
		boolean isSuccess = true;
		if (billentitylist != null && billentitylist.size() > 0) {
			try {

				for (int i = 0; i < billentitylist.size(); i++) {
					BillingParameter entity = billentitylist.get(i);
					if (entity.get_bencisl() < 0) {
						isSuccess = false;
						n_total = 0;
						break;
					}
					entity.set_jietije(entity.get_jietisl()
							* entity.get_jiage() * entity.get_xishu());// 计算阶梯内水量金额（阶梯内水量*阶梯价格）
					billentitylist.set(i, entity);
					n_total += entity.get_jietije();
				}
			} catch (Exception e) {
				isSuccess = false;
				n_total = 0;
			}
		} else {
			isSuccess = false;
		}

		String temp = String.format("%.2f", n_total);
		n_total = Double.parseDouble(temp);
		//n_total = Math.ceil(n_total * 100) / 100; // 保留两位小数 （已经为四舍五入）

		return new BillingResult(isSuccess, n_total, billentitylist);*/
    }

    // 判断是否阶梯
    public static boolean isJieTi(List<BillingParameter> billingParameterList) {
        if (billingParameterList == null) {
            return false;
        }

        int jietijb = -1;
        int jietijbCount = 0;
        for (BillingParameter item : billingParameterList) {
            if (item == null) {
                continue;
            }

            if (jietijb < item.get_jietijb()) {
                jietijb = item.get_jietijb();
                ++jietijbCount;
            }
        }

        if (jietijbCount > 1) {
            return true;
        }
        else {
            return false;
        }
    }

    // 处理多人口
    public static void processPopulation(List<BillingParameter> billingParameterList,
                                         double renkous) {
        if ((billingParameterList == null) || (renkous < 1e-10)) {
            return ;
        }

        int jietijb = 1;
        int lastIndex = 0;
        int kaishisl = 0;
        int jieshusl = 0;
        int i = 0;
        for (; i < billingParameterList.size(); i++) {
            BillingParameter billingParameter = billingParameterList.get(i);
            if (billingParameter == null) {
                continue;
            }

            if (jietijb < billingParameter.get_jietijb()) {
                jietijb = billingParameter.get_jietijb();
                jieshusl = (int)(billingParameterList.get(lastIndex).get_renkousl() * renkous);

                while (lastIndex < i) {
                    billingParameterList.get(lastIndex).set_kaishisl(kaishisl);
                    billingParameterList.get(lastIndex).set_jieshusl(jieshusl);
                    ++lastIndex;
                }

                kaishisl = jieshusl + 1;
                lastIndex = i;
            }
        }

        while (lastIndex < i) {
            billingParameterList.get(lastIndex).set_kaishisl(kaishisl);
            ++lastIndex;
        }
    }

    // 处理抄表周期
    public static void processChaobiaozq(List<BillingParameter> billingParameterList,
                                         String chaobiaozq) {
        if ((billingParameterList == null) || (chaobiaozq == null)) {
            return;
        }

        // 抄表周期
        int zq = 1;
        int bl = 1;
        if (chaobiaozq.equals("单月抄")) {
            zq = -2;
            bl = 2;
        }
        else if (chaobiaozq.equals("半月抄")) {
            zq = 0;
            bl = 0;
        }
        else if (chaobiaozq.equals("每月抄")) {
            zq = 1;
            bl = 1;
        }
        else if (chaobiaozq.equals("双月抄")) {
            zq = 2;
            bl = 2;
        }
        else if (chaobiaozq.equals("三月抄")) {
            zq = 3;
            bl = 3;
        }
        else if (chaobiaozq.equals("半年抄")) {
            zq = 6;
            bl = 6;
        }
        else if (chaobiaozq.equals("一年抄")) {
            zq = 12;
            bl = 12;
        }
        else { // 每月抄
            zq = 1;
            bl = 1;
        }

        int jietijb = 1;
        int lastIndex = 0;
        int kaishisl = 0;
        int jieshusl = 0;
        int i = 0;
        for (; i < billingParameterList.size(); i++) {
            BillingParameter billingParameter = billingParameterList.get(i);
            if (billingParameter == null) {
                continue;
            }

            if (jietijb < billingParameter.get_jietijb()) {
                jietijb = billingParameter.get_jietijb();
                jieshusl = billingParameterList.get(lastIndex).get_jieshusl() * bl;

                while (lastIndex < i) {
                    billingParameterList.get(lastIndex).set_kaishisl(kaishisl);
                    billingParameterList.get(lastIndex).set_jieshusl(jieshusl);
                    ++lastIndex;
                }

                kaishisl = jieshusl + 1;
                lastIndex = i;
            }
        }

        while (lastIndex < i) {
            billingParameterList.get(lastIndex).set_kaishisl(kaishisl);
            ++lastIndex;
        }
    }

    // 计算金额
    private static double calculateCash(BillingParameterEx billingParameterEx) {
        double cash = 0.0;
        if (billingParameterEx == null) {
            return cash;
        }

        List<BillingParameter> billingParameterList = billingParameterEx.getBillingParameterList();
        if (billingParameterList == null) {
            return cash;
        }

        int jietijb = 1;
        int lastIndex = 0;
        int i = 0;
        int bencisl = billingParameterEx.getBencisl();
        int kaishisl = 0;
        int jieshusl = 0;
        int chazhi = 0;
        int jietisl_cur = 0;
        int jietisl_sum = 0;
        double zhekoul = 0.0;
        boolean finish = false;
        for (; i < billingParameterList.size(); i++) {
            BillingParameter billingParameter = billingParameterList.get(i);
            if (billingParameter == null) {
                continue;
            }

            if (jietijb < billingParameter.get_jietijb()) {
                jietijb = billingParameter.get_jietijb();
                kaishisl = billingParameterList.get(lastIndex).get_kaishisl();
                jieshusl = billingParameterList.get(lastIndex).get_jieshusl();
                chazhi = jieshusl - kaishisl;
                if (kaishisl != 0) {
                    ++chazhi;
                }

                if (bencisl < jieshusl) {
                    jietisl_cur = bencisl - jietisl_sum;
                    finish = true;
                }
                else {
                    jietisl_cur = chazhi;
                    jietisl_sum += jietisl_cur;
                }

                while (lastIndex < i) {
                    switch (billingParameterList.get(lastIndex).get_feiyongdlid()) {
                        case 0:
                            zhekoul = billingParameterEx.getYongshuizkl();
                            break;
                        case 1:
                            zhekoul = billingParameterEx.getPaishuizkl();
                            break;
                        case 2:
                            zhekoul = billingParameterEx.getZhekoul1();
                            break;
                        case 3:
                            zhekoul = billingParameterEx.getZhekoul2();
                            break;
                        case 4:
                            zhekoul = billingParameterEx.getZhekoul3();
                            break;
                        default:
                            zhekoul = 1.0;
                            break;
                    }
                    cash += billingParameterList.get(lastIndex).get_jiage() * jietisl_cur * zhekoul;
                    ++lastIndex;
                }

                lastIndex = i;

                if (finish) {
                    return cash;
                }
            }
        }

        if (lastIndex < i) {
            jietisl_cur = bencisl - jietisl_sum;

            while (lastIndex < i) {
                switch (billingParameterList.get(lastIndex).get_feiyongdlid()) {
                    case 0:
                        zhekoul = billingParameterEx.getYongshuizkl();
                        break;
                    case 1:
                        zhekoul = billingParameterEx.getPaishuizkl();
                        break;
                    case 2:
                        zhekoul = billingParameterEx.getZhekoul1();
                        break;
                    case 3:
                        zhekoul = billingParameterEx.getZhekoul2();
                        break;
                    case 4:
                        zhekoul = billingParameterEx.getZhekoul3();
                        break;
                    default:
                        zhekoul = 1.0;
                        break;
                }

                cash += billingParameterList.get(lastIndex).get_jiage() * jietisl_cur * zhekoul;
                ++lastIndex;
            }
        }

        return cash;
    }

    // 处理优惠
    private static double processYouhui(BillingParameterEx billingParameterEx, double cash) {
        if ((billingParameterEx == null) || (Math.abs(cash) <= 1e-10)) {
            return cash;
        }

        // 低保优惠
        int youhuisl = 0;
        if (billingParameterEx.getDibaoyhsl() != 0) {
            youhuisl = billingParameterEx.getDibaoyhsl();
        }

        // 公厕优惠
        if (billingParameterEx.getGongceyhsl() != 0) {
            youhuisl = billingParameterEx.getGongceyhsl();
        }

        if (youhuisl == 0) {
            return cash;
        }

        //
        List<BillingParameter> billingParameterList = billingParameterEx.getBillingParameterList();
        if (billingParameterList == null) {
            return cash;
        }

        int jietijb = -1;
        int lastIndex = 0;
        int kaishisl = 0;
        int jieshusl = 0;
        int i = 0;
        int bencisl = billingParameterEx.getBencisl();
        for (; i < billingParameterList.size(); i++) {
            BillingParameter billingParameter = billingParameterList.get(i);
            if (billingParameter == null) {
                continue;
            }

            if (jietijb < billingParameter.get_jietijb()) {
                jieshusl = billingParameterList.get(lastIndex).get_jieshusl();
                if (bencisl <= jieshusl) {
                    cash -= youhuisl * billingParameterList.get(lastIndex).get_jiage();
                }

                break;
            }
        }

        return cash;
    }
}
