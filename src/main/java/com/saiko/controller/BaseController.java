package com.saiko.controller;

import java.util.Optional;
import java.util.stream.Stream;

import com.saiko.domain.Job;
import com.saiko.domain.Jobs;
import com.saiko.util.CsvUtils;
import com.saiko.util.TimeUtils;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.saiko.service.AnalyzerService;

@Controller
@RequestMapping("/dashboard")
public class BaseController {

	private static final Logger log = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	AnalyzerService jobAnalyzerService;

	@Value("${serenity.result.file}")
	private String serenityFilePath;

	@Value("${jenkins.url}")
	private String jenkinsUrl;

	@Value("${jenkins.api.path}")
	private String jenkinsApiPath;

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	private String sumTotalDuration(Job[] jobs) {
		long totalDurationInMillis = Stream.of(jobs).mapToLong(job -> job.getLastBuild().getDuration()).sum();
		return TimeUtils.durationToHhMm(totalDurationInMillis);
	}

	@RequestMapping("/refresh")
	public String refreshStats(@RequestParam String jobCategory, Model model) {
		String requestUrl = jenkinsUrl + jobCategory + jenkinsApiPath;
		log.info("requestUrl: {}", requestUrl);
		Jobs jobs = jobAnalyzerService.analyze(requestUrl, Jobs.class);
		if (jobs != null) {
			jobs.setTotalDuration(sumTotalDuration(jobs.getAllJobs()));
			jobs.setCount(jobs.getAllJobs().length);
		}
		model.addAttribute(jobs);
		//analyzeResult(jobs);
		return "index";
	}

	private void analyzeResult(Jobs jobs) {
		Stream.of(jobs.getAllJobs()).forEach(job -> {
			Optional<Iterable<CSVRecord>> records = CsvUtils.getRecords(job.getUrl() + serenityFilePath);
			records.ifPresent(data -> {
				data.forEach(record -> {
					String status = record.get("Result");
					log.info(status);
				});
			});
		});

	}

}
