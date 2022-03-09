package com.blogcrawling.api.service;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.blogcrawling.api.repository.CronConfRepository;

@Service
@EnableScheduling
public class DynamicScheduler implements SchedulingConfigurer {

	@Autowired
	CronConfRepository cronRepository;

	private final static Integer CRON_CONF_ID = 1;

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job importCrawlingBatch;

	@Bean
	public TaskScheduler poolScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
		scheduler.setPoolSize(1);
		scheduler.initialize();
		return scheduler;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(poolScheduler());

		taskRegistrar.addTriggerTask(() -> {
			try {
				scheduleCron(getCronConfFromDB());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, t -> {
			CronTrigger crontrigger = new CronTrigger(getCronConfFromDB());
			return crontrigger.nextExecutionTime(t);
		});
	}

	private void scheduleCron(String cron) throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addString("time", new Date().toString())
				.toJobParameters();

		jobLauncher.run(importCrawlingBatch, jobParameters);
		System.out.println("scheduleCron: Next execution time of this taken from cron expression -> " + cron);
	}

	private String getCronConfFromDB() {
		return cronRepository.findById(CRON_CONF_ID).get().getCronExp();
	}

}
