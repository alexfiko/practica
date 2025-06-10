package com.tigo.hn.stream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/mobile/hn/customer/postpaid/file/download/s3/v1/consumer")
public class FileDownloadController {

    @PostMapping("/{consumerId}")
    public void downloadFile(
            @PathVariable String consumerId,
            @RequestBody String requestBody,
            HttpServletResponse response
    ) {
        try {
            // Dummy: siempre devuelve la misma imagen
            ClassPathResource imgFile = new ClassPathResource("cr7.jpg");
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + consumerId + "-goat.jpg\"");
            try (InputStream is = imgFile.getInputStream();
                 OutputStream os = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.flush();
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}