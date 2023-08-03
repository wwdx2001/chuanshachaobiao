/**
 * @author liukaiyu
 *
 */
package com.sh3h.mralgorithm.reading;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//import javax.lang.model.element.Element;
//import javax.swing.text.Document;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;

public class ReadCalculatorBuilder {

	private List<ReadState> _readStates = null;

	/**
	 * 从xml文件留内读取配置数据初始化
	 *
	 * @param inputStream
	 * @return 抄表计算代理对象
	 */
	public static ReadCalculator loadFromStream(InputStream instr) {

		// DocumentBuilder db = null; //
		// documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
		// DocumentBuilderFactory dbf = null;
		// Element element = null;
		ReadCalculator rcalc = new ReadCalculator();
		// try {
		// dbf = DocumentBuilderFactory.newInstance(); //
		// 返回documentBuilderFactory对象
		// db = dbf.newDocumentBuilder();//
		// 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象
		//
		// Document dt = (Document) db.parse(instr); // 得到一个DOM并返回给document对象
		// element = (Element) dt.getDefaultRootElement();// 得到一个elment根元素
		// NodeList childNodes = ((Node) element).getChildNodes(); // 获得根元素下的子节点
		//
		// for (int i = 0; i < childNodes.getLength(); i++) // 遍历这些子节点
		// {
		// Node node1 = childNodes.item(i); // 获得每个对应位置i的结点
		// if ("parent".equals(node1.getNodeName())) {
		// NodeList nodeDetail = node1.getChildNodes(); // 获得<parent>下的节点
		// ReadState rstate;
		// for (int j = 0; j < nodeDetail.getLength(); j++) {
		// // 遍历<parent>下的节点
		// Node detail = nodeDetail.item(j);
		// // 先实例化抄表状态信息
		// rstate = new ReadState(detail.getAttributes()
		// .getNamedItem("code").getNodeValue(),
		// Integer.parseInt(detail.getAttributes()
		// .getNamedItem("algorithmcode")
		// .getNodeValue()));
		// rcalc.appendReadState(rstate);// 将抄表状态加入到hasmap中
		// }
		// }
		// }
		// } catch (Exception e) {
		// ;
		// }
		return rcalc;
	}

	public static ReadCalculator loadFromXML(String filepath) {

		try {
			File f = new File(filepath);
			FileInputStream fis = new FileInputStream(f);
			ReadCalculator rc = loadFromStream(fis);
			fis.close();
			return rc;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 批量追加抄表状态
	 *
	 * @param readStates
	 *            抄表状态列表
	 * @return
	 */
	public ReadCalculatorBuilder appendReadStates(List<ReadState> readStates) {
		return this;
	}

	/**
	 * 追加抄表状态
	 *
	 * @param readState
	 *            抄表状态
	 * @return
	 */
	public ReadCalculatorBuilder appendReadState(ReadState readState) {
		if (_readStates == null)
			_readStates = new ArrayList<ReadState>();
		_readStates.add(readState);

		return this;
	}

	/**
	 * 构建
	 *
	 * @return 返回ReadCalculator对象
	 */
	public ReadCalculator build() {
		return new ReadCalculator(this._readStates);
	}
}
