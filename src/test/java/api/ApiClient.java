package api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class ApiClient {

    Playwright playwright;
    APIRequestContext request;

    public ApiClient() {
        playwright = Playwright.create();
        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("http://localhost:5000"));
    }

    public APIResponse postQuote(String id, String author, String quote) {
        return request.post("/quotes", RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData("{\"id\": " + id + ", \"author\": \"" + author + "\", \"quote\": \"" + quote + "\"}"));
    }

}
