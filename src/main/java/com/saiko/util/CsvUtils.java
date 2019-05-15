package com.saiko.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CsvUtils {

	private CsvUtils() {
		// to be used for static methods
	}

	public static Optional<Iterable<CSVRecord>> getRecords(String url) {

		try {
			Reader in = new InputStreamReader(new URL(url).openStream());
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
			return Optional.ofNullable(records);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.empty();
	}

}
