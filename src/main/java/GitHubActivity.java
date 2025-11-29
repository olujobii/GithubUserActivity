import com.google.gson.Gson;

import java.io.IOException;
import java.net.URISyntaxException;

public class GitHubActivity {
    static void main(String[] args) {
        String username = "olujobii";
        Gson gson = new Gson();
        APIRepository apiRepository = new APIRepository(username);

        try{
            apiRepository.fetchData();
        }
        catch(URISyntaxException e)
        {
            System.out.println("The URL specified is not valid");
            System.out.println(e.getReason());
        }
        catch(InterruptedException | IOException e){
            System.out.println("An error occurred while fetching data");
        }
    }
}
