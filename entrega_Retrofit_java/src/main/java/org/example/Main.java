

package org.example;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public final class Main {
    public static final String API_URL = "https://api.github.com";

    public static class Repo {
        public final String name;
        public final int stargazers_count;
        public final int id;

        public Repo(String name, int stargazers_count,int id) {
            this.name = name;
            this.stargazers_count = stargazers_count;
            this.id=id;
        }
    }

    public interface GitHub {
        @GET("users/{user}/repos")
        Call<List<Repo>> listRepos(@Path("user") String user);
    }

    public static void main(String... args) throws IOException {
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Repo>> call = github.listRepos("DennisRuizB");

        // Fetch and print a list of the contributors to the library.
        List<Repo> repoList = call.execute().body();
        for (Repo repo : repoList) {
            System.out.println("Id:"+repo.id + " (name:" + repo.name + ")"+ "(stars:"+repo.stargazers_count+")");
        }
    }
}