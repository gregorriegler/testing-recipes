package web;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * ContractTest without PACT
 * Further reading: https://martinfowler.com/bliki/ContractTest.html
 *
 * Motivation:
 * We not only want to test our client code, but the external server as well.
 * We want to know if the external server keeps behaving as we would expect it to.
 *
 * Careful, this test will be flaky.
 * If the internet connection drops, or the remote system is down, it will fail.
 * So we probably don't want to run this test within our commit suite.
 */
public class WebContractTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    /**
     * Testing our own code, our client.
     * We are stubbing the response we'd expect the real service to respond with.
     * We know if this test fails, that there has to be a problem within our client.
     */
    @Test
    public void fetch_doctype_from_expected_response() {
        stubFor(get("/")
            .willReturn(ok().withBody("<!doctype html>")));

        testFetchDocType("http://localhost:8089");
    }

    /**
     * Testing the server (connects to an external system).
     * We know, that if this test fails while the above succeeds, that it's not our clients fault.
     * Could be a problem with the connection, or the server just returned a bad response.
     */
    @Test
    public void fetch_doctype_from_actual_response() {
        testFetchDocType("http://www.google.com");
    }

    /**
     * Both tests share the same "act & assert" part.
     * So we are replaying the same scenario against both a stub that behaves as "expected", and the "actual" service.
     * And we can compare the two results.
     *
     * @param url
     */
    private void testFetchDocType(String url) {
        Client client = new Client(url);

        String docType = client.readDocType();

        assertThat(docType).isEqualTo("html");
    }
}

/**
 * Reads the doctype from the response.
 */
class Client {

    private final String url;

    public Client(String url) {
        this.url = url;
    }

    String readDocType() {
        String docType;
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String response = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
            docType = response.substring(10, 14);
        } catch (Exception e) {
            docType = null;
        }
        return docType;
    }
}
