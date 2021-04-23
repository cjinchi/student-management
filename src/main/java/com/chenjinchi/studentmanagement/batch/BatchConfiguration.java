package com.chenjinchi.studentmanagement.batch;

import com.chenjinchi.studentmanagement.model.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.mapping.PassThroughRowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public PoiItemReader<Student> reader() {
		PoiItemReader<Student> reader = new PoiItemReader<>();
		reader.setName("studentItemReader");
		reader.setResource(new FileSystemResource("src/main/resources/测试数据.xlsx"));
		reader.setLinesToSkip(1);
		reader.setRowMapper(new StudentExcelRowMapper());
		return reader;

	}

	@Bean
	public StudentItemProcessor processor() {
		return new StudentItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Student> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Student>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO students VALUES (:id, :name, :birthDate, :gender, :phone, :department)")
				.dataSource(dataSource).build();
	}

	@Bean
	public Step step1(JdbcBatchItemWriter<Student> writer) {
		return stepBuilderFactory.get("step1").<Student, Student>chunk(1).reader(reader()).processor(processor())
				.writer(writer).build();
	}

	@Bean
	public Job importStudentJob(Step step1) {
		return jobBuilderFactory.get("importStudentJob").incrementer(new RunIdIncrementer()).flow(step1).end().build();
	}

}
