import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIRepository {
    private final String username;
    private final String URL;

    public APIRepository(String username) {
        this.username = username;
        this.URL = String.format("https://api.github.com/users/%s/events",username);
    }

    public HttpResponse<String> fetchData() throws URISyntaxException,IOException,InterruptedException {
        //Building the request
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(URL))
                .header("Accept","application/vnd.github+json")
                .GET()
                .build();

        HttpResponse<String> httpResponse = null;
        try(HttpClient httpClient = HttpClient.newHttpClient()){
        httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        }

        return httpResponse;
    }
}
