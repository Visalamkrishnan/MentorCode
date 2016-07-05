package com.data.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataUtils {

  private static DataUtils INSTANCE;

  /**
   * This method used to getInstance.
   * 
   * @return INSTANCE
   */
  public static DataUtils getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new DataUtils();
    }
    return INSTANCE;
  }

  private String dataPropFile = "com/eintern/v2/shared/data/utils/dataMessages.properties";

  /**
   * This method used to getPropertyFileValue.
   * 
   * @return value
   */
  public String getPropertyFileValue(String keyName) {
    String value = null;
    Properties prop = new Properties();
    try {
      InputStream propertiesFile =
          DataUtils.class.getClassLoader().getResourceAsStream(dataPropFile);
      if (propertiesFile != null) {
        prop.load(propertiesFile);
        String tempString = prop.getProperty(keyName);
        if (tempString != null && !tempString.equalsIgnoreCase("")) {
          value = tempString;
        } else {
          value = keyName;
        }
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return value;
  }

}
