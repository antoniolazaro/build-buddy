package org.build.buddy.repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class MavenRepositoryParser {


    public static String[] generateInputFromResponse(String dependecyOwner, String dependencyName) throws IOException, InterruptedException {
        String body = searchOnMavenRepository(dependecyOwner,dependencyName);
        var docsStart = body.indexOf("\"docs\":[") + 8;
        var docsEnd = body.indexOf("]", docsStart);
        String docsJson = body.substring(docsStart, docsEnd);

        var docs = docsJson.split("\\},\\{");

        for (int i = 0; i < docs.length; i++) {
            String doc = docs[i];
            String groupId = extract(doc, "g");
            String artifactId = extract(doc, "a");
            String version = extract(doc, "latestVersion");
            System.out.printf("%d) %s:%s:%s%n", i+1, groupId, artifactId, version);
        }
        return docs;
    }

    private static String searchOnMavenRepository(String dependecyOwner, String dependencyName) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String url = "https://search.maven.org/solrsearch/select?q=g:%s+AND+a:%s&rows=5&wt=json".formatted(dependecyOwner, dependencyName);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String extract(String json, String key) {
        int start = json.indexOf("\"" + key + "\":\"") + key.length() + 4;
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}

