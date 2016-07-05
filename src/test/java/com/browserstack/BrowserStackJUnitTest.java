package com.browserstack;

import com.browserstack.local.Local;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileReader;
import java.net.URL;
import java.util.*;

@RunWith(Parallelized.class)
@Ignore
public class BrowserStackJUnitTest {
    public WebDriver driver;
    private Local browserStackLocal;

    private static JSONObject config;

    @Parameter(value = 0)
    public int taskId;

    @Parameters
    public static Iterable<? extends Object> data() throws Exception {
        JSONParser parser = new JSONParser();
        config = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/" + System.getProperty("config")));
        int envs = ((JSONArray) config.get("environments")).size();

        List<Integer> taskIds = new ArrayList<Integer>();
        for (int i = 0; i < envs; i++) {
            taskIds.add(i);
        }

        return taskIds;
    }

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        JSONArray envs = (JSONArray) config.get("environments");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        envs.get(taskId);

        Map<String, Object> commonCapabilities = (HashMap<String, Object>) config.get("capabilities");
        for (Map.Entry<String, Object> capability : commonCapabilities.entrySet()) {
            capabilities.setCapability(capability.getKey(), capability.getValue().toString());
        }

        Map<String, Object> envCapabilities = (HashMap<String, Object>) envs.get(taskId);
        for (Map.Entry<String, Object> capability : envCapabilities.entrySet()) {
            capabilities.setCapability(capability.getKey(), capability.getValue().toString());
        }

        String username = System.getenv("BROWSERSTACK_USERNAME");
        if (username == null) {
            username = (String) config.get("user");
        }

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (accessKey == null) {
            accessKey = (String) config.get("key");
        }

        if ("true".equals(capabilities.getCapability("browserstack.local"))) {
            browserStackLocal = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", accessKey);
            browserStackLocal.start(options);
        }

        driver = new RemoteWebDriver(new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
    }

    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }

        if (browserStackLocal != null) {
            browserStackLocal.stop();
        }
    }
}
