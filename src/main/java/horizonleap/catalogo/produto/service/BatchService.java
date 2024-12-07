package horizonleap.catalogo.produto.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    public void runBatchJob() throws Exception {
        jobLauncher.run(job, new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters());
    }
}
