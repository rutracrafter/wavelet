import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    // int num = 0;
    ArrayList<String> stringList = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "Hello!\nuse \"/add?s={string}\" to add string to search engine.\nuse \"/search?s={string} to search for string.\"";
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    stringList.add(parameters[1]);
                    return parameters[1] + " was added to the search engine!";
                } 
            }
            else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    ArrayList<String> returnStrings = new ArrayList<>();
                    for (int i = 0; i < stringList.size(); i++) {
                        if (stringList.get(i).contains(parameters[1])) {
                            returnStrings.add(stringList.get(i));
                        }
                    }
                    if (returnStrings.size() == 0) {
                        return "No result found.";
                    }
                    else {
                        return returnStrings.toString();
                    }
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
