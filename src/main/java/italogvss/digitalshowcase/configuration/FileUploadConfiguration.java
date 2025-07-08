package italogvss.digitalshowcase.configuration;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import jakarta.servlet.MultipartConfigElement;

@Configuration
public class FileUploadConfiguration {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        // Defina o tamanho máximo para cada arquivo
        factory.setMaxFileSize(DataSize.ofMegabytes(150));
        return factory.createMultipartConfig();
    }
}