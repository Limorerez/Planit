package edu.sfsu.cs.orange.ocr.utils;

import java.util.HashMap;
import java.util.Map;

public enum SendToEnum{
	JIRA_BLI("JIRA BLI"), JIRA_TASK("JIRA Task"), mail("Mail");

	private final String name;
	private static Map<String, SendToEnum> valueMap;

    private SendToEnum(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }
    
    public static SendToEnum getValue(String value){
    	if (valueMap == null)
        {
            valueMap = new HashMap<String, SendToEnum>();
            for(SendToEnum provider: values())
                valueMap.put(provider.toString(), provider);
        }
    	
        return valueMap.get(value);
    }
}
