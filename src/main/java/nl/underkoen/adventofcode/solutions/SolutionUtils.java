package nl.underkoen.adventofcode.solutions;

import com.google.common.reflect.ClassPath;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by Under_Koen on 01/12/2019.
 */
@UtilityClass
class SolutionUtils {
    File resources = new File(System.getProperty("user.dir") + "\\inputs");
    boolean download = true;
    private String session;

    private File getResources() {
        if (!resources.exists() && !resources.mkdir()) throw new IllegalArgumentException("Cant create input folder");
        return resources;
    }

    @SneakyThrows
    public List<String> getInput(int year, int day) {
        File yearFolder = new File(getResources(), Integer.toString(year));

        if (!yearFolder.exists() && !yearFolder.mkdir())
            throw new IllegalArgumentException("Cant create folder for this year.");

        String file = String.format("day%02d.txt", day);
        File input = new File(yearFolder, file);

        if (!input.exists()) {
            if (!input.createNewFile()) throw new IllegalArgumentException("Cant create input for this day.");
            if (download)downloadInputFromAOC(year, day, input);
            else {
                System.err.println("Created input file, fill this with your input.");
                System.exit(-1);
            }
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
    public void downloadInputFromAOC(int year, int day, File file) {
        String url = String.format("https://adventofcode.com/%d/day/%d/input", year, day);

        HttpGet httpGet = new HttpGet(url);
        if (session == null) session = getSession();
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
    public String submit(int year, int day, String value, int level) {
        String url = String.format("https://adventofcode.com/%d/day/%d/answer", year, day);

        HttpPost httpPost = new HttpPost(url);
        if (session == null) session = getSession();
        httpPost.addHeader("cookie", String.format("session=%s", session));

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("level", Integer.toString(level)));
        params.add(new BasicNameValuePair("answer", value));

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpEntity entity = httpclient.execute(httpPost).getEntity();

            String answer = EntityUtils.toString(entity);
            return answer.split("article")[1];
        }

    }

    @SneakyThrows
    private String getSession() {
        File session = new File(getResources(), "session.txt");
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

    @SneakyThrows
    static Map<Integer, List<Solution>> getAllSolutions(String pkg) {
        Set<ClassPath.ClassInfo> classes = ClassPath.from(SolutionUtils.class.getClassLoader()).getTopLevelClassesRecursive(pkg);
        Map<Integer, List<Solution>> solutions = new HashMap<>();

        for (ClassPath.ClassInfo info : classes) {
            Class<?> cls = info.load();
            if (Solution.class.isAssignableFrom(cls) && cls != Solution.class) {
                Class<? extends Solution> day = (Class<? extends Solution>) cls;
                Solution solution = day.getConstructor().newInstance();
                if (!solutions.containsKey(solution.getYear())) solutions.put(solution.getYear(), new ArrayList<>());
                solutions.get(solution.getYear()).add(solution);
            }
        }

        return solutions;
    }
}
