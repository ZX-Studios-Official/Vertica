package me.zxstudios.vertica.libs;

import me.zxstudios.vertica.Vertica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.*;

public class VersionChecker {

    private final Vertica plugin;

    public VersionChecker(Vertica plugin) {
        this.plugin = plugin;
    }

    public String checkVersion() {
        try {
            URL url = new URL("https://api.modrinth.com/v2/project/vertica/version");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                JsonArray array = JsonParser.parseString(response.toString()).getAsJsonArray();

                if (array.isEmpty()) return null;

                return array.get(0)
                        .getAsJsonObject()
                        .get("version_number")
                        .getAsString();
            }

        } catch (IOException e) {
            plugin.getLogger().severe("Failed to check version: " + e.getMessage());
        }
        return null;
    }
}



