package com.config.tool.properties;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.config.tool.constent.IConfigToolConstents;

public class PropertiesFileReader {
	
	static Properties properties = new Properties();
	
	public static List<PropertiesHolder> readPropEntities() {
		String filePath = IConfigToolConstents.XSTORE_SYSTEM_PROPERTIES;
		PropertiesHolder propertyEntityHolder = null;
		List<PropertiesHolder> propHolder = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if ((line.trim().startsWith("#@") || line.trim().startsWith("!@"))
						|| (line.contains("=") && !(line.trim().startsWith("#@")) && !(line.trim().startsWith("#")))) {

					// Ignore blank lines and lines starting with '#' or '!'
					if (line.trim().startsWith("#@") || line.trim().startsWith("!@")) {
						propertyEntityHolder = new PropertiesHolder();
						propertyEntityHolder.setDescription(line.substring(2));
					}
					if (line.contains("=") && !(line.trim().startsWith("#@")) && !(line.trim().startsWith("#"))) {
						propertyEntityHolder.setPropKey(line.split("=")[0].trim());
						propertyEntityHolder.setPropValue(line.split("=")[1].trim());
						// keyValuePairFound =true;
						if (propertyEntityHolder.getDescription() != null) {
							propHolder.add(propertyEntityHolder);
						}
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return propHolder;
	}
	
	 public static String readProperties(String key) throws IOException {
		 String filePath = IConfigToolConstents.TRANSLATION_PROPERTIES;
	        //properties = new Properties();
	        try  {
	        	InputStream input = new FileInputStream(filePath);
					properties.load(input);
	        }
	        catch (Exception e) {
				// TODO: handle exception
			}
	        return properties.getProperty(key);
	    }
}
