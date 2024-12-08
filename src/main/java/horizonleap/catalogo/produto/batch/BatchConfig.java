package horizonleap.catalogo.produto.batch;

import horizonleap.catalogo.produto.model.ProdutoModel;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import org.springframework.batch.core.step.builder.StepBuilder;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public FlatFileItemReader<ProdutoModel> reader() {
        return new FlatFileItemReaderBuilder<ProdutoModel>()
                .name("produtoItemReader")
                .resource(new FileSystemResource("")) // Placeholder, will be set dynamically
                .delimited()
                .names("nome", "descricao", "preco", "quantidadeEstoque")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<ProdutoModel>() {{
                    setTargetType(ProdutoModel.class);
                }})
                .build();
    }

    @Bean
    public ProdutoItemProcessor processor() {
        return new ProdutoItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<ProdutoModel> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<ProdutoModel>()
                .sql("INSERT INTO produto (nome, descricao, preco, quantidade_estoque) VALUES (:nome, :descricao, :preco, :quantidadeEstoque)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      FlatFileItemReader<ProdutoModel> reader, JdbcBatchItemWriter<ProdutoModel> writer,
                      ProdutoItemProcessor processor) {
        return new StepBuilder("step1", jobRepository)
                .<ProdutoModel, ProdutoModel>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importProdutoJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("importProdutoJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }
}
