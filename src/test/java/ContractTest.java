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
public class ContractTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    /**
     * Testing our own code, our client.
     * We know if this test fails, that there has to be a problem within our client.
     */
    @Test
    public void can_fetch_doctype_from_expected_response() {
        // here we are stubbing the response we'd expect the real service to respond with.
        // the test tells us, if our client works.
        // meaning: our client is able to handle an expected response.
        stubFor(get("/")
            .willReturn(ok().withBody("<!doctype html>")));

        testReadDocType("http://localhost:8089");
    }

    /**
     * Testing the server (connects to an external system).
     * We know, that if this test fails while the above succeeds, that it's not our clients fault.
     * Could be a problem with the connection, or the server just returned a bad response.
     */
    @Test
    public void can_fetch_doctype_from_actual_response() {
        testReadDocType("http://www.google.com");
    }

    /**
     * Both tests share the same "act & assert" part, but against different systems.
     * This is important so we are actually replaying the same scenario, and compare the two worlds:
     * "Actual" (=Real Service) vs "Expected" (=What we would expect from the Real Service)
     *
     * @param url
     */
    private void testReadDocType(String url) {
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
