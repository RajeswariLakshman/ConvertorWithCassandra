package com.java.Util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

import org.jboss.logging.Logger;

public class ConverterUtil {

	private static Logger log = Logger.getLogger(ConverterUtil.class);

	public static void pdfConverter() {

	}

	public static void csvConverter(String orderDetails) {
		try {
			String csvStr;
			JsonNode jsonTree = new ObjectMapper().readTree(orderDetails);
			Builder csvSchemaBuilder = CsvSchema.builder();
			JsonNode firstObject = jsonTree.elements().next();
			firstObject.fieldNames().forEachRemaining(fieldName -> {
				csvSchemaBuilder.addColumn(fieldName);
			});
			CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
			CsvMapper csvMapper = new CsvMapper();
			csvStr = csvMapper.writerFor(JsonNode.class).with(csvSchema).writeValueAsString(jsonTree);
			System.out.println("converted str " + csvStr);
		} catch (JsonProcessingException e) {
			log.error("Error in parsing json", e);
		} catch (IOException e) {
			log.error("Error in writing to file", e);
		}
	}

	public static void xmlConverter() {

	}

}
