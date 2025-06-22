package stepdefs;

import api.ApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class QuoteStepDefs {

    APIRequestContext request;
    APIResponse response;

    private static Playwright playwright;
    private String bearerToken;

    ApiClient client = new ApiClient();
    String responseBody;
    int responseStatus;
    private String latestJsonPayload = null;

    @Given("je prépare un client HTTP Playwright")
    public void initClient() {
        playwright = Playwright.create();
        request = playwright.request().newContext();
    }

    @Given("je prépare un client HTTP Playwright avec le token {string}")
    public void initClientWithToken(String token) {
        playwright = Playwright.create();
        bearerToken = token;
        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setExtraHTTPHeaders(Map.of("Authorization", "Bearer " + token)));
    }

    @When("j'envoie une requête POST sur {string} avec le body:")
    public void postRequestWithBody(String url, String body) {
        response = request.post(url, RequestOptions.create().setData(body));
        if (body.contains("Ada Lovelace"))
            System.out.println("------ response = " + response.text());
    }


    @When("j'envoie une requête POST sur {string} avec l'id {string}, l'auteur {string}, et la citation {string}")
    public void postWithExamples(String url, String id, String author, String quote) {
        String body = String.format("{\"id\": %s, \"author\": \"%s\", \"quote\": \"%s\"}", id, author, quote);
        response = request.post(url, RequestOptions.create().setData(body));
    }

    @Given("je lis le fichier JSON {string}")
    public void loadJsonFromFile(String filePath) throws IOException {
        String content = Files.readString(Paths.get(filePath));
        response = request.post("http://localhost:5000/quotes", RequestOptions.create().setData(content));
    }

    @Then("le code réponse doit être {int}")
    public void assertStatusCode(int statusCode) {
        Assertions.assertEquals(statusCode, response.status());
    }

    @Then("la réponse doit contenir {string}")
    public void assertResponseContains(String key) throws IOException {
        String json = response.text();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(json, Map.class);
        Assertions.assertTrue(map.containsKey(key));
    }

    @Then("un rapport de test doit être généré en JUnit XML")
    public void dummyStepReport() {
        // Géré automatiquement par la config de test (pom.xml + surefire/junit)
    }

    @When("j'envoie une requête POST sur {string} avec le corps {string}")
    public void sendPostRequest(String endpoint, String bodyPath) throws Exception {
        String jsonBody = FileUtils.readFileAsString(bodyPath);
        response = request.post(endpoint, RequestOptions.create().setData(jsonBody));
    }

    @When("j'envoie une requête GET sur {string}")
    public void sendGetRequest(String endpoint) {
        response = request.get(endpoint);
    }

    @Given("the API server is running")
    public void api_server_running() {
        // Optionnel : ping or setup
        System.out.println("------- The server is running ----- ");
    }

    @When("I post a new quote with id {string}, author {string}, and quote {string}")
    public void post_quote(String id, String author, String quote) {
        APIResponse response = client.postQuote(id, author, quote);
        responseStatus = response.status();
        responseBody = response.text();
    }

    @Then("the response status should be {int}")
    public void verify_status(int status) {
        assertEquals(status, responseStatus);
    }

    @Then("the response should contain {string}")
    public void verify_response_contains(String text) {
        assertTrue(responseBody.contains(text));
    }


    @When("j'envoie une requête POST sur {string} avec ce payload")
    public void postWithLatestPayload(String endpoint) {
        RequestOptions options = RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData(latestJsonPayload);

        response = request.post(endpoint, options);
    }
}