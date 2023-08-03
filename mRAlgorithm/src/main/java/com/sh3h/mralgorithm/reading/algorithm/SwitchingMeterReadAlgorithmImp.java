package com.sh3h.mralgorithm.reading.algorithm;

import com.sh3h.mralgorithm.reading.ReadingParameter;
import com.sh3h.mralgorithm.reading.ReadingResult;

/**
 * 正常换表 水量= (本次抄码 – 新表起码) + (旧表底码 – 上次抄码) I_shuibiaozt in (1,2) 0<=本次抄码<= 量程 水量
 * >= 0
 * 换表(定换)
 * @author liukaiyu
 *
 */
public class SwitchingMeterReadAlgorithmImp implements IReadAlgorithm {

	@Override
	public int getCode() {
		return IReadAlgorithm.ALGORITHM_SWITCHINGMETER;
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

		if (entity.get_bencicm() < 0) {
			outMsg = "输入的本次抄码必须是大于等于零的数字";
			isSuccess = false;
			return new ReadingResult(isSuccess, outMsg, entity);
		}
		//TODO 原始代码被注释，现重新放开
		if (entity.get_bencicm() > entity.get_liangcheng()) {
			outMsg = "本次抄码不能大于量程,本表量程为:" + entity.get_liangcheng();
			isSuccess = false;
			return new ReadingResult(isSuccess, outMsg, entity);
		}
		if (entity.get_bencicm() < entity.get_xinbiaodm()) {
			outMsg = "本次抄码不能小于新表底码";
			isSuccess = false;
			return new ReadingResult(isSuccess, outMsg, entity);
		}
/*		int bencisl = (((entity.get_bencicm() - entity.get_xinbiaodm()) + (entity
				.get_jiubiaocm() - entity.get_shangcicm())) * entity
				.get_shuibiaobeil());*/
		int bencisl = (((entity.get_bencicm() - entity.get_xinbiaodm()) + (entity
				.get_jiubiaocm() - entity.get_shangcicm())));

/*		if (bencisl < 0) {
			isSuccess = false;
			outMsg = "抄码输入有误，水量小于零";
			return new ReadingResult(isSuccess, outMsg, entity);
		}*/
		if (bencisl < 0) {
			entity.set_bencisl(0);
			return new ReadingResult(isSuccess, outMsg, entity);
		}

        entity.set_bencisl(bencisl);

		return new ReadingResult(isSuccess, outMsg, entity);
	}

}
