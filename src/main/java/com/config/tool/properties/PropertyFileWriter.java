package com.config.tool.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.PropertiesConfigurationLayout;

import com.config.tool.constent.IConfigToolConstents;

public class PropertyFileWriter {

	public void writePropertyFile(Map<String, String> map) {
		try {
			// String filePath = "C:/Accen_Emp_13059506/configWiz/system.properties";
			String filePath = IConfigToolConstents.XSTORE_SYSTEM_PROPERTIES;
			File file = new File(filePath);
			PropertiesConfiguration config = new PropertiesConfiguration();
			PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout();
			InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
			layout.load(config, inputReader);

			FileWriter fw = new FileWriter(filePath, false);

			Set<String> keys = map.keySet();
			for (String key : keys) {
				config.setProperty(key, map.get(key));
			}

			layout.save(config, fw);
			inputReader.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}