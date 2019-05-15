package com.saiko.service;

public interface AnalyzerService {

	String analyze(String url);

	<T> T analyze(String url, Class<T> responseType);
}
