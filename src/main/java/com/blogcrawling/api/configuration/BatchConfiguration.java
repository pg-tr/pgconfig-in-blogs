package com.blogcrawling.api.configuration;

import java.util.ArrayList;
import java.util.HashSet;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.NonSkippableReadException;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;

import com.blogcrawling.api.batchconfigurationlistener.ItemSkipPolicy;
import com.blogcrawling.api.batchconfigurationlistener.JobCompletionNotificationListener;
import com.blogcrawling.api.domain.Blog;
import com.blogcrawling.crawlingmodul.ParameterSearch;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	@StepScope
	public ListItemReader<Blog> reader() {
		ParameterSearch crawling = new ParameterSearch();
		HashSet<Blog> blogSet = crawling.search("https://planet.postgresql.org/feeds.html");
		return new ListItemReader<Blog>(new ArrayList<Blog>(blogSet));
	}

	@Bean
	@StepScope
	public JdbcBatchItemWriter<Blog> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Blog>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Blog>())
				.sql("INSERT INTO blog (param, blog_url, blog_title) VALUES (:identity.param,:identity.blog_url, :blogTitle)")
				.dataSource(dataSource).build();
	}

	@Bean
	public Job importCrawlingBatch(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importCrawlingBatch").incrementer(new RunIdIncrementer()).listener(listener)
				.flow(step1).end().build();
	}

	@Bean
	public Step step1(JdbcBatchItemWriter<Blog> writer) {
		return stepBuilderFactory.get("step1").<Blog, Blog>chunk(10).reader(reader()).writer(writer).faultTolerant()
				.skip(DuplicateKeyException.class).skipPolicy(new ItemSkipPolicy()).build();
	}
}
