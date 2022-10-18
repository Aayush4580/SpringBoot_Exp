package com.example.demo.controller;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.demo.dto.NameOnlyDTO;
import com.example.demo.dto.ProgressCallable;
import com.example.demo.entity.Department;
import com.example.demo.entity.DepartmentReqBody;
import com.example.demo.entity.Product;
import com.example.demo.exception.DepartmentNotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.AsyncService;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.ProductService;
import com.example.demo.service.impl.ExcelHelper;
import com.example.demo.util.Util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
//@CrossOrigin("*")
public class DepartmentController {
    // http://localhost:8080/swagger-ui/index.html#/
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private ProductRepository productRepository;
//	@Autowired
//	private ProductExcelProcessStateService excelProcessStateService;
    private Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
    public static final String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");

//	@Autowired
//	private ProductService productService;
//
//	@PostMapping("/upload")
//	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
//	example upload excel exists in resource
//		if (ExcelHelper.checkExcelFormat(file)) {
//			ProductExcelProcessState excelProcessState = productService.saveExcel(file);
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("excelProcessId", excelProcessState.getProcessId().toString());
//			map.put("message", "Please request with the processId to get the updated status");
//			return ResponseEntity.ok(map);
//		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
//	}
//
//	@GetMapping("/getExcelUploadStatus/{id}")
//	public ProductExcelProcessState getExcelUploadStatus(@PathVariable("id") String id) throws InterruptedException {
//		log.info("inside getExcelUploadStatus method");
////		Thread.sleep(3000);
//		return excelProcessStateService.getProductProcessStatus(Long.parseLong(id));
//	}
    @GetMapping("/normalExport")
    public void normalExportToExcel(HttpServletResponse response) throws IOException, InterruptedException {
        try {
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headervalue = "attachment; filename=Student_info.xlsx";
            Thread.sleep(1000);
            response.setHeader(headerKey, headervalue);
            List<Product> products = productService.get200Products();
            ExcelHelper exp = new ExcelHelper();
            exp.export(products, response, new ProgressCallable() {
                @Override
                public void onProgess(int percentage) throws IOException {
                    System.err.println("percentage >> " + percentage);

                }

            });
//			return "excel ready";
        } catch (IOException e) {
            e.printStackTrace();
//			return "Error";
        }
    }

    @GetMapping("/export/{guid}")
    public void exportToExcel(@PathVariable("guid") String guid, HttpServletResponse response) throws IOException {
        System.err.println("inside with guid >>> " + guid);
        SseEmitter sseEmitter = sseEmitters.get(guid);
        try {
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headervalue = "attachment; filename=Student_info.xlsx";

            response.setHeader(headerKey, headervalue);
            List<Product> products = productService.get200Products();
            ExcelHelper exp = new ExcelHelper();
            exp.export(products, response, new ProgressCallable() {
                @Override
                public void onProgess(int percentage) throws IOException {
                    System.err.println("percentage >> " + percentage);
                    if (percentage < 100) {
                        sseEmitter.send(SseEmitter.event().name(guid).data(percentage));
                    } else {
                        sseEmitter.send(SseEmitter.event().name(guid).data(100));
                        sseEmitters.remove(guid);
                    }
                }
            });
        } catch (IOException e) {
            sseEmitter.completeWithError(e);
        }
    }

    @GetMapping("/progress")
    public SseEmitter eventEmitter() throws IOException {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        UUID guid = UUID.randomUUID();
        sseEmitters.put(guid.toString(), sseEmitter);
        sseEmitter.send(SseEmitter.event().name("TEST_SSE").data(guid));
        sseEmitter.onCompletion(() -> sseEmitters.remove(guid.toString()));
        sseEmitter.onTimeout(() -> sseEmitters.remove(guid.toString()));
        return sseEmitter;
    }

    @GetMapping("/testSSE/{guid}")
    public ResponseEntity<String> testSSE(@PathVariable("guid") String guid) throws IOException {
        System.err.println("inside method with guid >> " + guid);
        String message = "";
        SseEmitter sseEmitter = sseEmitters.get(guid);
        try {
            int uploadPercentage = 0;
            for (int i = 0; i <= 10; i++) {
                uploadPercentage = i;
                Thread.sleep(1000);
                System.err.println("upload percentage >>> " + uploadPercentage);
                sseEmitter.send(SseEmitter.event().name(guid).data(uploadPercentage));
            }

            sseEmitters.remove(guid);
            message = "Uploaded the file successfull:";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file:";
            sseEmitters.remove(guid);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    // define a location
    public static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads/";

    // Define a method to upload files
    @PostMapping("/uploadToDir")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files") List<MultipartFile> multipartFiles)
            throws IOException {
        System.err.println("inside method >>>> ");
        List<String> filenames = new ArrayList<>();
        System.err.println(" System.getProperty(\"user.home\") >>> " + System.getProperty("user.home"));
        System.err.println("DIRECTORY >> " + DIRECTORY);
        for (MultipartFile file : multipartFiles) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            filenames.add(filename);
        }
        return ResponseEntity.ok().body(filenames);
    }

    // Define a method to download files
    @GetMapping("downloadFromDir/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename)
            throws IOException, InterruptedException {
        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(filename + " was not found on the server");
        }
        Thread.sleep(1000);
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        Thread.sleep(1000);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }

    @RequestMapping(value = "/zip", produces = "application/zip")
    public void zipFiles(HttpServletResponse response) throws IOException {

        // setting headers
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"test.zip\"");

        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

        // create a list to add files to be zipped
        List<Product> products = productService.get200Products();
        ExcelHelper exp = new ExcelHelper();

        ArrayList<File> files = exp.exportMultiple(products, TEMP_DIRECTORY);
        // package files
        for (File file : files) {
            // new zip entry and copying inputstream with file to zipOutputStream, after all
            // closing streams
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file);

            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        zipOutputStream.close();
        String pathName = TEMP_DIRECTORY + "temp_excel";
        File file = new File(pathName);
        System.err.println("file after zip >> " + file);
        if (Files.exists(Paths.get(pathName))) {
            Util.deleteDir(file);
        }
    }

    @GetMapping("createExcelInFolder")
    public void createExcelInFolder(HttpServletResponse response) throws FileNotFoundException, IOException {
        try (// creating an instance of Workbook class
                Workbook wb = new HSSFWorkbook()) {
            File file = new File(DIRECTORY);
            if (!Files.exists(Paths.get(DIRECTORY))) {
                file.mkdir();
            }
            OutputStream fileOut = new FileOutputStream(DIRECTORY + "BankStatement1.xlsx");
            System.out.println("Excel File has been created successfully.");
            wb.write(fileOut);
        }
    }

    @PostMapping("/department")
    public String saveDepartment(@RequestBody @Valid DepartmentReqBody department) {
        log.info("inside save department method");
        return departmentService.saveDepartment(department);
    }

    @GetMapping("/getDepartment")
    public List<Department> getDepartment(@RequestParam("code") String code,
            @RequestParam("departmentName") String departmentName, @RequestParam("board") String board) {
        log.info("inside save department method");
        return departmentService.getDepartment(code, departmentName, board.toUpperCase());
    }

    @GetMapping("/getDepartmentByPojo")
    public List<Department> getDepartmentByPojo() {
        Department department = new Department();
//		department.setDepartmentId(Long.parseLong("8"));
//		department.setBoard("CBSC");
        department.setDepartmentName("ME");
//		department.setDepartmentCode(code);
        System.out.println("department >> " + department);
        return departmentService.getDepartmentByPojo(department);
    }

    @GetMapping("/department")
    public List<Department> fetchDepartment() {
        return departmentService.fetchDepartment();
    }

    @GetMapping("/department/{id}")
    public Department fetchDepartmentById(@PathVariable("id") Long id) throws DepartmentNotFoundException {
        return departmentService.fetchDepartmentById(id);
    }

    @GetMapping("/department/name/{name}")
    public Department fetchDepartmentByName(@PathVariable("name") String name) {
        return departmentService.fetchDepartmentByName(name);
    }

    @PutMapping("/department")
    public Department updateDepartmentById(@RequestBody Department department) throws DepartmentNotFoundException {
        return departmentService.updateDepartment(department);
    }

    @GetMapping("/projection")
    public NameOnlyDTO projectionTest(@RequestBody Department department) throws DepartmentNotFoundException {
        return productRepository.findByNativeQuery(1);
    }

    @DeleteMapping("/department/{id}")
    public String deletehDepartmentById(@PathVariable("id") Long id) {
        log.info(">>>>> delete called >>>> ");
        try {
            departmentService.deleteDepartmentById(id);
            return "deparment deleted successfully";
        } catch (Exception ex) {
            log.info("inside catch block");
            throw new EntityNotFoundException("not found with id " + id);
        }

    }

    @GetMapping("/completableTest")
    public String completableTest() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        asyncService.sendReminderToEmployee().get();
        long end = System.currentTimeMillis();
        float sec = (end - start) / 1000F;
        System.err.println("completed in >>> " + sec + " seconds");
        return "working";
    }

    @GetMapping("/withoutTest")
    public String withoutTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        asyncService.withoutFutureAsync();
        long end = System.currentTimeMillis();
        float sec = (end - start) / 1000F;
        System.err.println("completed in >>> " + sec + " seconds");
        return "working";
    }

    @GetMapping("/asyncMethod")
    public String method1() throws ExecutionException, InterruptedException {
        asyncService.asyncMethod();
        return "working";
    }

    @GetMapping("/concurrentAwait")
    public List<String> anotherMetjhod1() throws ExecutionException, InterruptedException {
        List<String> str = asyncService.concurrentAwait();
        return str;
    }

}
