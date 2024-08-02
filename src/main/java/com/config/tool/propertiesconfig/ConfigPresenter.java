package com.config.tool.propertiesconfig;

import java.util.List;
import java.util.Map;

import com.config.tool.properties.PropertiesFileReader;
import com.config.tool.properties.PropertiesHolder;
import com.config.tool.properties.PropertyFileWriter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConfigPresenter implements IConfigPresenter {

	private final IConfigView view;

	@Override
	public void initProperties() {
		List<PropertiesHolder> propertiesList = PropertiesFileReader.readPropEntities();
		view.showProperties(propertiesList);
	}

	@Override
	public void saveButtonClicked(Map<String, String> updatedKeyValuePair) {
		PropertyFileWriter propFileWriter = new PropertyFileWriter();
		propFileWriter.writePropertyFile(updatedKeyValuePair);
	}

}
