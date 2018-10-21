package com.datasource.affinity.calculateAffinity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import com.datasource.affinity.calculateAffinity.controller.AffinityController;
import com.datasource.affinity.calculateAffinity.controller.GeneratorController;
import com.datasource.affinity.calculateAffinity.extras.GetPropertyValues;
import com.datasource.affinity.calculateAffinity.runner.Generator;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class CalculateAffinityApplication {
	@Autowired
	GeneratorController generatorController;
	@Autowired
	Generator generator;
	@Autowired
	AffinityController affinityController;

	private static GetPropertyValues propValues = new GetPropertyValues(); // store config

	public static void main(String[] args) {
		SpringApplication.run(CalculateAffinityApplication.class, args);
	}

	// To run multiple threads
	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor(); // Or use another one of your liking
	}

	@Bean
	public CommandLineRunner schedulingRunner(TaskExecutor executor) {
		return new CommandLineRunner() {
			public void run(String... args) throws Exception {
				simulateDataDatabase();

				// initially insert the config values
				generatorController.setPropValues(propValues);
				affinityController.setPropValues(propValues);

//				generatorController.run();	
				affinityController.run();
			}
		};
	}

	// read the config properties
	public static void simulateDataDatabase() {
		propValues.readValues();
	}
}
