/**
 *
 */
package com.sh3h.mralgorithm;

//import java.util.ArrayList;
//
//import com.sh3h.mralgorithm.billing.BillingCalculator;
//import com.sh3h.mralgorithm.billing.BillingParameter;
//import com.sh3h.mralgorithm.billing.BillingResult;
//import com.sh3h.mralgorithm.reading.ReadCalculator;
//import com.sh3h.mralgorithm.reading.ReadCalculatorBuilder;
//import com.sh3h.mralgorithm.reading.ReadState;
//import com.sh3h.mralgorithm.reading.ReadingParameter;
//import com.sh3h.mralgorithm.reading.ReadingResult;

/**
 * @author qiweiwei
 *
 */
public class AlgorithmTest {

	/**
	 *
	 */
	public AlgorithmTest() {
	}

//	public void test1() {
//
//		/**
//		 * 水量计算调用接口Demo 从配置文件中读取抄表状态和算法关系
//		 */
//		ReadCalculator rc = ReadCalculatorBuilder
//				.loadFromXML("E:\\ChaobiaoZT.xml");
//
//		ReadingResult result = rc.calculate(new ReadingParameter());
//
//		/**
//		 * 从抄表机传过来的参数中读取抄表状态和算法
//		 */
//		ReadCalculator calc = new ReadCalculator();
//		ReadingParameter param = new ReadingParameter();
//		calc.appendReadState(new ReadState(param.get_chaobiaoztbm(), param
//				.get_chaobiaosuanfabm()));
//		result = calc.calculate(param);
//
//		/**
//		 * 金额计算接口调用Demo
//		 */
//		BillingResult result1 = (new BillingCalculator(
//				new ArrayList<BillingParameter>())).calculate();
//	}

}
