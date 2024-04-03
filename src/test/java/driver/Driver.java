package driver;

import com.microsoft.sqlserver.jdbc.StringUtils;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class Driver {

    public static WebDriver driver;
    protected Logger logger = LoggerFactory.getLogger(getClass());
    ChromeOptions chromeOptions;


    @BeforeScenario
    public void setUp() throws MalformedURLException,Exception {

        String Baseurl = "https://testcase.myideasoft.com/";
        DesiredCapabilities capabilities;

        if (StringUtils.isEmpty(System.getenv("key"))) {
            capabilities = DesiredCapabilities.chrome();
            ChromeOptions options = new ChromeOptions();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);

            System.setProperty("webdriver.chrome.driver", "web_driver/chromedriver.exe");
            driver = new ChromeDriver(capabilities);

        }else{
            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("key", System.getenv("key"));
        }

        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS).implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(Baseurl);
    }

    @AfterScenario
    public void tearDown()throws Exception{
        driver.quit();
    }
}
