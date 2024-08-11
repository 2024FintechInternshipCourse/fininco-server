package com.fininco.finincoserver.exchange.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ExchangeCompleteJobConfig {

    private final ExchangeBuyTasklet exchangeBuyTasklet;
    private final ExchangeSellTasklet exchangeSellTasklet;
    private final PlatformTransactionManager platformTransactionManager;
    private final JobRepository jobRepository;

    @Bean
    public Job exchangeCompleteJob(JobRepository jobRepository, Step exchangeBuyStep, Step exchangeSellStep) {
        return new JobBuilder("exchangeCompleteJob", jobRepository)
                .start(exchangeBuyStep)
                .next(exchangeSellStep)
                .build();
    }

    @Bean
    @Qualifier("exchangeBuyStep")
    public Step exchangeBuyStep() {
        return new StepBuilder("exchangeBuyStep", jobRepository)
                .tasklet(exchangeBuyTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    @Qualifier("exchangeSellStep")
    public Step exchangeSellStep() {
        return new StepBuilder("exchangeSellStep", jobRepository)
                .tasklet(exchangeSellTasklet, platformTransactionManager)
                .build();
    }

}
