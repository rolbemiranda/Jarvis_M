package com.jarvis.app;

//import io.appium.java_client.AppiumDriver;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import junit.framework.Test;
import org.openqa.selenium.Capabilities;


import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserFunctions {

    private static GenericFunctions _GenericFunctions;

    public BrowserFunctions() {
        _GenericFunctions = new GenericFunctions();
    }

    public void navigate_to(WebDriver wd, String data) {
        wd.manage().window().setPosition(new Point(700, 0));
        wd.manage().window().setSize(new Dimension(1000, 1000));
        wd.manage().window().maximize();
        wd.get(data);
        Global.TigerUrl = data;
    }

    public void RefreshPage(WebDriver wd) {
        wd.navigate().refresh();
    }

    public void close_browser(WebDriver wd, String ScenarioName) {
        wd.close();
        wd.quit();
        System.out.println("Browser closed. Scenario " + ScenarioName + " has been completed.");
    }

    public Selenium LaunchSelenium(String URL) {
        Selenium selenium = new DefaultSelenium("localhost", 4444, "*firefox", URL);
        selenium.start();
        selenium.windowMaximize();
        selenium.waitForPageToLoad("1000");
        assert selenium.isTextPresent("kass");
        return selenium;
    }

    public void stopSelenium(Selenium selenium) {
        selenium.stop();
    }

    public WebDriver open_browser(String OSType, String BrowserVersion,
            String browserType, String SauceLabsMode) throws MalformedURLException {
        WebDriver wd1 = null;

        if (SauceLabsMode.equalsIgnoreCase("YES")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(CapabilityType.BROWSER_NAME, browserType);
            capabilities.setCapability(CapabilityType.VERSION, BrowserVersion.trim().replace(".", ""));
            capabilities.setCapability(CapabilityType.PLATFORM, OSType);
            wd1 = new RemoteWebDriver(
                    new URL("http://" + "TigerairQA" + ":" + "62ea010d-e374-4868-b2cf-2be04c646d16" + "@ondemand.saucelabs.com:80/wd/hub"),
                    capabilities);

            //https://saucelabs.com/docs/additional-config#alternative-annotation-methods
            JavascriptExecutor js = (JavascriptExecutor) wd1;
            js.executeScript("sauce:job-name=" + Global.Scenario_Filename + "_" + Global.FolderTimeStamp);
            js.executeScript("sauce:max-duration=300");
            js.executeScript("sauce:command-timeout=300");
            js.executeScript("sauce:idle-timeout=300");

        } else {
            if (browserType.equalsIgnoreCase("Firefox")) {
                DesiredCapabilities capabillities = DesiredCapabilities.firefox();
                capabillities.setCapability("version", "8");
                capabillities.setPlatform(Platform.WINDOWS);
                wd1 = new FirefoxDriver(capabillities);
            }

            if (browserType.equalsIgnoreCase("IE")) {
                String fPath = _GenericFunctions.GetAbsolutePath();
                File file = new File(fPath + "\\VB\\IEDriverServer.exe");
                System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
                wd1 = new InternetExplorerDriver();
            }

            if (browserType.equalsIgnoreCase("chrome")) {
                String fPath = _GenericFunctions.GetAbsolutePath();
                File file = new File(fPath + "\\VB\\chromedriver.exe");
                System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
                wd1 = new ChromeDriver();
            }

            if (browserType.equalsIgnoreCase("appium")) {
                //String fPath = _GenericFunctions.GetAbsolutePath();

                String fPath = "./ANDROID/SDK/adt-bundle-windows-x86_64-20130729"
                        + "/sdk/build-tools/android-4.3/";
                File file = new File(fPath + "com.litl.FireDrill_quickdownload_330.apk");
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("device", "Android");
                capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
                capabilities.setCapability(CapabilityType.VERSION, "4.3");
                capabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");
                capabilities.setCapability("app", file.getAbsolutePath()); //This is a native app
                capabilities.setCapability("app-package", "me.onemobile.android");
                capabilities.setCapability("app-activity", "me.onemobile.android.launchactivity");
                //wd1 = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            }
        }
        return wd1;
    }

    public String get_Page_Source(WebDriver wd) {
        String content = wd.getPageSource();
        System.out.println(content);
        return content;
    }
}
