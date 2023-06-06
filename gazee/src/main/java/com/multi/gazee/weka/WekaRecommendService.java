package com.multi.gazee.weka;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.CSVLoader;

public class WekaRecommendService {

	public String ml(double[] values) throws Exception {
		System.out.println("service>> " + values[0]);

		// 1. 데이터 로딩(수집)
		// Instances data = new Instances(new BufferedReader(new
		// FileReader("data/titanic2_pre.arff")));
		String path2 = "C:\\Users\\gram\\Documents\\gazeegazee\\gazee";
		String file2 = path2 + "\\data\\customer_shopping_data_pre.csv";

		System.out.println(file2);
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(file2));
		System.out.println("여기까지 되었어...");
		Instances data = loader.getDataSet(); // arff형식으로 변환해서 가지고 온다.

		// 1. Attribute리스트를 만들자. 요기 고침!
		ArrayList attributes = new ArrayList();

		// 2. attributes리스트를 만들어서 Instances객체에 셋팅
		Enumeration<Attribute> attrList = data.enumerateAttributes();

		while (attrList.hasMoreElements()) {
			attributes.add(attrList.nextElement());
		}
		System.out.println(attributes); // [attr, attr, attr, attr, attr, ..]

		// 3. arff를 나타내는 Instances객체를 만들자.

		Instances instances = new Instances("test", attributes, 0); // target(분류할 대상, 분류할 항목, 정답)이 되는 인덱스를 설정
		instances.setClassIndex(instances.numAttributes() - 1);
		instances.add(new DenseInstance(1, values));

		System.out.println("=== 여기까지 만든 arff(instances) 확인 ===");
		System.out.println(instances);

		// 모델을 읽어와서, test데이터에 있는 것 꺼내서 검증해보자.

		Classifier model = (Classifier) SerializationHelper.read(path2 + "\\model\\customer_shopping.model");

		double result = model.classifyInstance(instances.firstInstance());

		System.out.println("============== 분류기의 결과>> " + result);

		if (result == 0.0) {
			return "의류";
		} else if (result == 1.0) {
			return "잡화";
		} else if (result == 2.0) {
			return "도서";
		} else if (result == 3.0) {
			return "뷰티/미용";
		} else if (result == 4.0) {
			return "생활/주방";
		} else if (result == 5.0) {
			return "취미/게임/음반";
		} else if (result == 6.0) {
			return "디지털기기";
		} else {
			return "가구/인테리어";
		}
	}
}
