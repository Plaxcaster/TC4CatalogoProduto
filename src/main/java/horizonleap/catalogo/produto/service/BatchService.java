package horizonleap.catalogo.produto.service;

import horizonleap.catalogo.produto.model.ProdutoModel;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private FlatFileItemReader<ProdutoModel> reader;

    public void runBatchJob() throws Exception {
        jobLauncher.run(job, new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters());
    }

    public void setResource(Resource resource) {
        reader.setResource(resource);
    }
}
