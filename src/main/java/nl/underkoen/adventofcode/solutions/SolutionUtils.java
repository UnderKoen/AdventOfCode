package nl.underkoen.adventofcode.solutions;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Under_Koen on 01/12/2019.
 */
class SolutionUtils {
    private static final File resources = getResources();
    private static final String session = getSession();

    private static File getResources() {
        File file = new File(System.getProperty("user.dir") + "\\inputs");
        if (!file.exists() && !file.mkdir()) throw new IllegalArgumentException("Cant create input folder");
        System.out.println(file);
        return file;
    }

    @SneakyThrows
    public static List<String> getInput(int year, int day) {
        File yearFolder = new File(resources, Integer.toString(year));

        if (!yearFolder.exists() && !yearFolder.mkdir())
            throw new IllegalArgumentException("Cant create folder for this year.");

        String file = String.format("day%02d.txt", day);
        File input = new File(yearFolder, file);

        if (!input.exists()) {
            if (!input.createNewFile()) throw new IllegalArgumentException("Cant create input for this day.");
            downloadInputFromAOC(year, day, input);
        }

        try (Scanner scanner = new Scanner(new FileInputStream(input))) {
            List<String> result = new ArrayList<>();
            while (scanner.hasNextLine()) {
                result.add(scanner.nextLine());
            }
            return result;
        }
    }

    @SneakyThrows
    public static void downloadInputFromAOC(int year, int day, File file) {
        String url = String.format("https://adventofcode.com/%d/day/%d/input", year, day);

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("cookie", String.format("session=%s", session));
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpEntity entity = httpclient.execute(httpGet).getEntity();

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(EntityUtils.toString(entity));
            fileWriter.flush();
            fileWriter.close();
        }
    }

    @SneakyThrows
    private static String getSession() {
        File session = new File(resources, "session.txt");
        if (!session.exists()) {
            if (!session.createNewFile()) System.err.println("Couldn't create input file");
            System.err.println("Created input/session.txt please fill this with your session key");
            System.exit(-1);
        }

        try (Scanner scanner = new Scanner(new FileInputStream(session))) {
            if (!scanner.hasNextLine()) {
                System.err.println("Please fill input/session.txt with your session key");
                System.exit(-1);
            }
            return scanner.next();
        }
    }
}
