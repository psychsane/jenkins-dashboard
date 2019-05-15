package com.saiko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JobAnalyzerServiceImpl implements AnalyzerService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public <T> T analyze(String url, Class<T> responseType) {
		return restTemplate.getForObject(url, responseType);
	}

	@Override
	public String analyze(String url) {
		return restTemplate.getForObject(url, String.class);
	}

}
