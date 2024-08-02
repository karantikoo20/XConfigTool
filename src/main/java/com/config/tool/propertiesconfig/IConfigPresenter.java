package com.config.tool.propertiesconfig;

import java.util.Map;

public interface IConfigPresenter {
 void initProperties();
 void saveButtonClicked(Map<String, String> updatedKeyValuePair);
}
