package com.fininco.finincoserver.exchange.batch;

import com.fininco.finincoserver.point.entity.CurrencyCode;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExchangeJobLauncherRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job exchangeCompleteJob;

    public void runJob(String currencyCode) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("currencyCode", currencyCode)
                .addLong("time", System.currentTimeMillis()) // 고유한 매개변수를 위해 현재 시간을 추가
                .toJobParameters();

        jobLauncher.run(exchangeCompleteJob, jobParameters);
    }

}
