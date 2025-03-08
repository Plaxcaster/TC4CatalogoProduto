package horizonleap.catalogo.produto.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import horizonleap.catalogo.produto.model.ProdutoModel;

@Service
public class BatchService {

    private final JobLauncher jobLauncher;
    private final Job job;
    private final FlatFileItemReader<ProdutoModel> reader;

    // Constructor injection
    public BatchService(JobLauncher jobLauncher, Job job, FlatFileItemReader<ProdutoModel> reader) {
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.reader = reader;
    }

    public void runBatchJob() throws Exception {
        jobLauncher.run(job, new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters());
    }

    public void setResource(Resource resource) {
        reader.setResource(resource);
    }
}
