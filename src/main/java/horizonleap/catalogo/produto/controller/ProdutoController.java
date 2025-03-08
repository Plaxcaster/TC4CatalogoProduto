package horizonleap.catalogo.produto.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import horizonleap.catalogo.produto.model.ProdutoModel;
import horizonleap.catalogo.produto.service.BatchService;
import horizonleap.catalogo.produto.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@EnableScheduling
@RestController
@RequestMapping("/produtos")
@Tag(name = "Produto", description = "Endpoints para gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final BatchService batchService;
    private final ScheduledExecutorService scheduledExecutorService;

    // Constructor injection
    public ProdutoController(ProdutoService produtoService, BatchService batchService,
            ScheduledExecutorService scheduledExecutorService) {
        this.produtoService = produtoService;
        this.batchService = batchService;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @GetMapping
    @Operation(summary = "Obter todos os produtos", description = "Recuperar uma lista de todos os produtos")
    public List<ProdutoModel> getAllProducts() {
        return produtoService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter produto por ID", description = "Recuperar um produto pelo seu ID")
    public Optional<ProdutoModel> getProductById(@PathVariable Integer id) {
        return produtoService.getProductById(id);
    }

    @PostMapping
    @Operation(summary = "Adicionar um novo produto", description = "Criar um novo produto")
    public ProdutoModel addProduct(@RequestBody ProdutoModel product) {
        return produtoService.addProduct(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar quantidade do produto", description = "Atualizar a quantidade de um produto existente pelo seu ID")
    public ProdutoModel updateProductQuantity(@PathVariable Integer id, @RequestParam Integer quantity) {
        return produtoService.updateProductQuantity(id, quantity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto", description = "Excluir um produto pelo seu ID")
    public void deleteProduct(@PathVariable Integer id) {
        produtoService.deleteProduct(id);
    }

    @PostMapping("/upload")
    @Operation(summary = "Carregar produtos em massa", description = "Carregar dados de produtos em massa a partir de um arquivo CSV")
    public String uploadCSV(@RequestParam("file") MultipartFile file) {
        try {
            // Save the file to a specific location
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);

            // Update resource in reader
            batchService.setResource(new FileSystemResource(convFile));

            // Run the batch job
            batchService.runBatchJob();
            return "Arquivo CSV carregado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao carregar o arquivo CSV: " + e.getMessage();
        }
    }

    @PostMapping("/schedule-upload")
    @Operation(summary = "Agendar processamento de produtos em massa", description = "Agendar processamento de dados de produtos em massa a partir de um arquivo CSV")
    public String scheduleUploadCSV(@RequestParam("file") MultipartFile file,
            @RequestParam("scheduleDateTime") String scheduleDateTime) {
        try {
            // Save the file to a specific location
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);

            // Parse the schedule date and time
            LocalDateTime dateTime = LocalDateTime.parse(scheduleDateTime,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            long delay = dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
                    - System.currentTimeMillis();

            // Schedule the batch job
            scheduledExecutorService.schedule(() -> {
                try {
                    // Update resource in reader
                    batchService.setResource(new FileSystemResource(convFile));

                    // Run the batch job
                    batchService.runBatchJob();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, delay, TimeUnit.MILLISECONDS);

            return "Processamento agendado para " + scheduleDateTime + " com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao agendar o processamento do arquivo CSV: " + e.getMessage();
        }
    }
}
