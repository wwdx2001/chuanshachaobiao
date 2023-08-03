/**
 * @author qiweiwei
 */
package com.sh3h.mralgorithm.reading;

import java.util.HashMap;
import java.util.List;

import com.sh3h.mralgorithm.reading.algorithm.IReadAlgorithm;

/**
 * 抄表计算代理对象， 管理算法和抄表状态的对应关系
 *
 */
public class ReadCalculator {

	private HashMap<String, ReadState> _readStateDic;

	/**
	 * 默认构造函数
	 */
	public ReadCalculator() {
		_readStateDic = new HashMap<String, ReadState>();
	}

	/**
	 * 构造函数，支持传入默认的算法列表
	 *
	 * @param readStates
	 *            抄表状态
	 */
	public ReadCalculator(List<ReadState> readStates) {
	}

	/**
	 * 添加抄表状态, 抄表状态不允许重复
	 *
	 * @param readState
	 *            抄表状态对象
	 */
	public void appendReadState(ReadState readState) {
		if(!_readStateDic.containsKey(readState.getCode()))
			_readStateDic.put(readState.getCode(), readState);
		else
			;
	}

	/**
	 * 移除抄表状态
	 *
	 * @param readStateCode
	 *            抄表状态代码
	 */
	public void removeReadState(String readStateCode) {
		this._readStateDic.remove(readStateCode);
	}

	// /**
	// * 添加算法实现， 不允许有重复的code（相同算法）存在
	// *
	// * @param algorithmObject
	// */
	// public void appendAlgorithm(IReadAlgorithm algorithmObject) {
	//
	// }
	//
	// /**
	// * 移除指定算法
	// */
	// public void removeAlgorithm(int code) {
	//
	// }

	/**
	 * 根据传入的抄表参数计算抄表结果
	 *
	 * 通过抄表状态，获取对应的抄表算法对象，调用其计算方法 如果未找到对应的抄表算法返回失败或者抛出异常
	 *
	 * @param param
	 *            抄表参数
	 * @return 抄表计算结果
	 */
	public ReadingResult calculate(ReadingParameter param) {

		if (!this._readStateDic.containsKey(param.get_chaobiaoztbm()))
		{
			ReadingResult result  = new ReadingResult(false,"未找到该抄表状态对应的算法",param);
			return result;
		}

		ReadState rs = this._readStateDic.get(param.get_chaobiaoztbm());
		int algorithmCode = rs.getReadingAlgorithmCode();

		IReadAlgorithm a = ReadAlgorithmFactory.create(algorithmCode);

		return a.calculate(param);
	}

}
