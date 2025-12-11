import com.google.gson.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;


public class GitHubActivity {
    static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Incorrect usage, pass in a github-username when running the program.");
            return;
        }

        APIRepository apiRepository = new APIRepository(args[0]);
        HttpResponse<String> httpResponse;

        try{
            httpResponse = apiRepository.fetchData();
            if(httpResponse.statusCode() == 404){
                System.out.println("Username not found");
                return;
            }
            if(httpResponse.statusCode() != 200){
                System.out.println("Error, response code: "+httpResponse.statusCode());
                return;
            }
            listEvents(httpResponse,args[0]);
        }
        catch(URISyntaxException e)
        {
            System.out.println("The URL specified is not valid");
            System.out.println(e.getReason());
        }
        catch(InterruptedException | IOException e){
            System.out.println("An error occurred while fetching data");
            System.out.println(e.getMessage());
        }
    }

    static void listEvents(HttpResponse<String> httpResponse,String username){
        JsonArray jsonArray = JsonParser.parseString(httpResponse.body()).getAsJsonArray();
        if(jsonArray.isEmpty())
        {
            System.out.println("No events available from this user");
            return;
        }

        System.out.println("Github Activity for "+username);
        for(var elements : jsonArray){
            JsonObject jsonObject = elements.getAsJsonObject();
            String type = jsonObject.get("type").getAsString(); //Get type of event
            String repoName = jsonObject.get("repo").getAsJsonObject().get("name").getAsString(); //Get repository name
            JsonObject payload = jsonObject.get("payload").getAsJsonObject(); //Get payload activity

            switch(type){
                case "PushEvent":
                    System.out.println("\n-------------------------------");
                    System.out.println("Pushed commit(s) to "+repoName);
                    System.out.println("-------------------------------");
                    break;
                case "WatchEvent":
                    System.out.println("\n-------------------------------");
                    System.out.println("Starred "+repoName);
                    System.out.println("-------------------------------");
                    break;
                case "CreateEvent":
                    System.out.println("\n-------------------------------");
                    System.out.println("Created git branch at "+repoName);
                    System.out.println("-------------------------------");
                    break;
                case "DeleteEvent":
                    System.out.println("\n-------------------------------");
                    System.out.println("Deleted git branch at "+repoName);
                    System.out.println("-------------------------------");
                    break;
                case "IssuesEvent":
                    String issuesEventAction = payload.get("action").getAsString();
                    System.out.println("\n-------------------------------");
                    System.out.println(issuesEventAction+ " an issue in "+repoName);
                    System.out.println("-------------------------------");
                    break;
                case "PullRequestEvent":
                    String pullRequestAction = payload.get("action").getAsString();
                    System.out.println("\n-------------------------------");
                    System.out.println(pullRequestAction+ " a pull request in "+repoName);
                    System.out.println("-------------------------------");
                    break;
                case "PublicEvent":
                    System.out.println("\n-------------------------------");
                    System.out.println(repoName+" made public");
                    System.out.println("-------------------------------");
                    break;
                case "ForkEvent":
                    String forkEventAction = payload.get("action").getAsString();
                    System.out.println("\n-------------------------------");
                    System.out.println(forkEventAction +" "+repoName);
                    System.out.println("-------------------------------");
                    break;
                default:
                    System.out.println("\n-------------------------------");
                    System.out.println("Event Type: "+type);
                    System.out.println("GitHub Repository: "+repoName);
                    System.out.println("-------------------------------");
                    break;
            }
        }
    }
}
