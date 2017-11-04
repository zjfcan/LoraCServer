package com.guina.loratracker.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtils
{
	public static <T> T convertStringToJson(Class<T> classObj, String strJsonData)
	{
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			
			return mapper.readValue(strJsonData, classObj);
		}
		catch (Exception e1)
		{
			String errMessage = String.format("Failed to parse as Json object. Type: %s, Error: %s",
							classObj.getName(), e1.getMessage());
			throw new RuntimeException(errMessage, e1);
		}
	}

	public static <T> List<T> convertStringToJsonList(Class<T> classObj, String strJsonData)
	{
		try
		{
			ObjectMapper mapper = new ObjectMapper();

			return mapper.readValue(strJsonData,
							mapper.getTypeFactory().constructCollectionType(List.class, classObj));
		}
		catch (Exception e1)
		{
			String errMessage = String.format("Failed to parse as Json object. Type: %s, Error: %s",
							classObj.getName(), e1.getMessage());
			throw new RuntimeException(errMessage, e1);
		}
	}

	public static String convertJsonToString(Object jsonObj)
	{
		boolean prettyPrinter = true;
		return convertJsonToString(jsonObj, JsonInclude.Include.ALWAYS, prettyPrinter);
	}

	public static String convertJsonToString(Object jsonObj, boolean prettyPrinter)
	{
		return convertJsonToString(jsonObj, JsonInclude.Include.ALWAYS, prettyPrinter);
	}

	public static String convertJsonToString(Object jsonObj, JsonInclude.Include jsonInclude,
					boolean prettyPrinter)
	{
		if (jsonObj == null)
		{
			return null;
		}

		try
		{
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(jsonInclude);
			String strJsonObj;
			if (prettyPrinter)
			{
				ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
				strJsonObj = writer.writeValueAsString(jsonObj);
			}
			else
			{
				strJsonObj = mapper.writeValueAsString(jsonObj);
			}

			return strJsonObj;
		}
		catch (Exception e)
		{
			String errMessage = "Failed to convert from Json to String.";
			throw new RuntimeException(errMessage, e);
		}
	}
}
