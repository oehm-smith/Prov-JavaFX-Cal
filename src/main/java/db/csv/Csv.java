package db.csv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameMappingStrategy;

public class Csv {
	private CSVReader reader;
	private List<DataBean> dataList;
	
	/**
	 * Open File as CSV from given String
	 * @param string
	 * @throws FileNotFoundException 
	 */
	public Csv(URL url) throws FileNotFoundException {
		if (url == null || url.getFile() == null) {
			throw new FileNotFoundException();
		}
		reader = new CSVReader(new FileReader(url.getFile()));
		readJBeanData();
	}

	private void readJBeanData() {
		HeaderColumnNameMappingStrategy<DataBean> strat = new HeaderColumnNameMappingStrategy<>();
		strat.setType(DataBean.class);
//		String[] columns = new String[] {"Date", "Facebook", "Twitter","YouTube"};
//		strat. setColumnMapping(columns);

		CsvToBean<DataBean> csv = new CsvToBean<>();
		dataList = csv.parse(strat, reader);		
	}

	public List<DataBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<DataBean> dataList) {
		this.dataList = dataList;
	}
}
