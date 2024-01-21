package email.send.emailsendler.sevice.impl;

import email.send.emailsendler.sevice.HtmlLoaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Log4j
public class HtmlLoaderServiceImpl implements HtmlLoaderService {


    @Value("${html.template.path}")
    private String path;
    private final ResourceLoader resourceLoader;

    @Override
    public String loadHtmlContent() throws IOException {
        Resource resource = resourceLoader.getResource(path);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}
