import com.google.gson.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;


public class GitHubActivity {
    static void main(String[] args) {
        String username = "olujobii";

        if(args.length == 0){
            System.out.println("No username is specified");
            return;
        }

        if(args.length != 1){
            System.out.println("Wrong usage");
        }
        APIRepository apiRepository = new APIRepository(username);
        HttpResponse<String> httpResponse;

        try{
            httpResponse = apiRepository.fetchData();
            if(httpResponse.statusCode() == 404){
                System.out.println("Username not found");
                return;
            }
            listEvents(httpResponse);
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

    static void listEvents(HttpResponse<String> httpResponse){
        JsonArray jsonArray = JsonParser.parseString(httpResponse.body()).getAsJsonArray();
        if(jsonArray.isEmpty())
        {
            System.out.println("No events available from this user");
            return;
        }

        for(var elements : jsonArray){
            JsonObject jsonObject = elements.getAsJsonObject();
            String type = jsonObject.get("type").getAsString(); //Get type of event
            String repoUrl = jsonObject.get("repo").getAsJsonObject().get("url").getAsString(); //Get url to repository

            switch(type){
                case "PushEvent":
                    System.out.println("\n-------------------------------");
                    System.out.println("Event Type: "+type);
                    System.out.println("Made commits to "+ repoUrl);
                    System.out.println("-------------------------------");
                    break;
                case "WatchEvent":
                    System.out.println("\n-------------------------------");
                    System.out.println("Event Type: "+type);
                    System.out.println("Starred repository: "+ repoUrl);
                    System.out.println("-------------------------------");
                    break;
                case "CreateEvent":
                    System.out.println("\n-------------------------------");
                    System.out.println("Event Type: "+type);
                    System.out.println("Git branch created: "+ repoUrl);
                    System.out.println("-------------------------------");
                    break;
                case "DeleteEvent":
                    System.out.println("\n-------------------------------");
                    System.out.println("Event Type: "+type);
                    System.out.println("Deleted Git branch: "+ repoUrl);
                    System.out.println("-------------------------------");
                    break;
                default:
                    System.out.println("\n-------------------------------");
                    System.out.println("Event Type: "+type);
                    System.out.println("GitHub Repository: "+repoUrl);
                    System.out.println("-------------------------------");
            }
        }
    }
}
