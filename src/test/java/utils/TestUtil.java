package utils;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class TestUtil {

    private TestUtil() {
    }

    public static void approveFakeConfirmation(String decodedHtmlContent) throws IOException {
        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setRedirectEnabled(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            Path write = Files.write(Paths.get("fakeConfirmationPage.html"), decodedHtmlContent.getBytes(StandardCharsets.UTF_8));
            HtmlPage startPage = webClient.getPage(write.toFile().toURI().toURL());
            DomElement elementById = startPage.getElementById("submitBtn");
            elementById.click();
        }
    }
}
