// <editor-fold defaultstate="collapsed" desc="JARVIS - TIGER AUTOMATED TESTING TOOL">
/**
 *************************************************************************************************************
 *************************************************************************************************************
 * Program Name: JARVIS - TIGER AUTOMATED TESTING
 * TOOL******************************************************** Created By: Kass
 * Miranda***********************************************************************************
 * Date Created: March
 * 2013***********************************************************************************
 * Version:
 * 3.0***********************************************************************************************
 * ************************************************************************************************************
 * ***********************************************************************************************************
 */
// </editor-fold>
package com.jarvis.app;

import com.thoughtworks.selenium.Selenium;
import java.io.File;
import org.openqa.selenium.WebDriver;

public class AppTest {

    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    private static BrowserFunctions _BrowserFunctions;
    private static EmailNotification_1 _EmailNotification_1;
    private static GenericFunctions _GenericFunctions;

    // </editor-fold>s
    public static void main(String[] args) throws Exception {

// <editor-fold defaultstate="collapsed" desc="INSTANTIATE">
        _BrowserFunctions = new BrowserFunctions();
        _EmailNotification_1 = new EmailNotification_1();
        _GenericFunctions = new GenericFunctions();

        String[][] scenarios;
        String[][] steps;
        String fPath = _GenericFunctions.GetAbsolutePath();
        scenarios = _GenericFunctions.excelRead(fPath + "/DATA/Control_File.xls");
        WebDriver wd = null;
        Selenium wdh = null;
        String PNR = "";
        // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="RESULT FOLDER">
        //Create Results Folder
        String FolderTimeStamp = _GenericFunctions.GenerateTimeStamp();
        Global.FolderTimeStamp = FolderTimeStamp;
        String ResultsFolder = fPath + "/RESULTS/" + FolderTimeStamp;
        new File(ResultsFolder).mkdirs();

        //Create Screenshot Folder
        String ScreenshotResultsFolder = fPath + "/RESULTS/" + FolderTimeStamp + "/SCREENSHOTS/";
        new File(ScreenshotResultsFolder).mkdirs();
        // </editor-fold>

        //Main Loop : Count Scenario to Run
        for (int imain = 1; imain < scenarios.length; imain++) {
            // <editor-fold defaultstate="collapsed" desc="INSTANTIATE">
            String RunScenario = scenarios[imain][0];
            String TestCategory = scenarios[imain][1];
            String Scenario_Filename = scenarios[imain][2];
            String TestCase_ID = scenarios[imain][3];
            Global.TestCase_ID = TestCase_ID;
            Global.OverallTestingStatus = "PASSED";
            Global.ContinueExecution = true;
            Global.MCC = "Not Available";

            // </editor-fold>

            if (RunScenario.equalsIgnoreCase("Y")) {
                System.out.println(imain + " of " + (scenarios.length - 1) + ": " + Scenario_Filename
                        + " is currently running.");
                steps = _GenericFunctions.excelRead(fPath + "/DATA/" + TestCategory + "/"
                        + Scenario_Filename);

                //Steps Loop : Count Steps to Run
                for (int idata = 1; idata < steps.length; idata++) {
                    // <editor-fold defaultstate="collapsed" desc="INSTANTIATE">
                    //Initialize Parameters
                    String Flag = steps[idata][0];
                    String StepName = steps[idata][1];
                    String Keyword = steps[idata][2];
                    String param1 = steps[idata][3];
                    String param2 = steps[idata][4];
                    String param3 = steps[idata][5];
                    String param4 = steps[idata][6];

                    //Initialized Results File
                    String ResultsFileName = ResultsFolder + "\\" + TestCase_ID + "_Results.xls";
                    String ScreenshotFileName = ResultsFolder + "\\" + TestCase_ID + "_SCREENSHOT.docx";

                    //Store Results Path/Files to Global
                    Global.Scenario_Filename = Scenario_Filename;
                    Global.ComputationFileName = ResultsFolder + "\\" + TestCase_ID + "_Computation.txt";
                    Global.ResultsFolder = ResultsFolder;
                    Global.ScreenshotFileName = ScreenshotFileName;
                    Global.ResultsFileName = ResultsFileName;


                    //Create Result file on start of the program
                    if (idata == 1) {
                        _GenericFunctions.CreateOutputFile(ResultsFileName);
                    }
                    // </editor-fold>
                    if (Global.ContinueExecution) {

                        if (Flag.equalsIgnoreCase("Y")) {
                            //Tracking Tool
                            System.out.println(Global.TestCase_ID + ": " + idata + " of "
                                    + (steps.length - 1) + ": " + StepName);
                            Global.iData = idata;
                            Global.Keyword = Keyword;

                            switch (Keyword.toUpperCase()) {
                                case "NAVIGATE_TO":
                                    _BrowserFunctions.navigate_to(wd, param3);
                                    break;
                                case "OPEN_BROWSER":
                                    wd = _BrowserFunctions.open_browser(param1, param2, param3, param4);
                                    break;
                                case "LAUNCHSELENIUM":
                                    wdh = _BrowserFunctions.LaunchSelenium(param4);
                                    break;
                                case "STOPSELENIUM":
                                    _BrowserFunctions.stopSelenium(wdh);
                                    break;

                                // <editor-fold defaultstate="collapsed" desc="SWITCH DEFAULT">
                                default:
                                    System.out.println("LINE: " + idata + " - " + Keyword
                                            + ": NOT FOUND ON THE VALID KEYWORD LIST.");
                                    System.exit(0);
                                // </editor-fold>
                            }
                        }
                    } else {
                        //Capture Screenshot
                        if (Global.ActivateScreenshot) {
                            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "ZZZ_Error Screen");
                        }

                        _EmailNotification_1.SendEmailHasFailed(Global.EmailAddress, Scenario_Filename, Global.iData, Global.Keyword, ResultsFolder);
                        System.out.println("WARNING: Jarvis Test has stopped at line " + Global.iData
                                + " where current step " + Global.Keyword + " is being executed."
                                + "Check your current screen for possible errors.");
                        _GenericFunctions.wait("5000");
                        idata = steps.length + 1;
                    }
                }
            }
        }
        //<editor-fold defaultstate="collapsed" desc="EXIT NOTIFICATION">
        _GenericFunctions.CloseNotification(wd,
                "Program has been completed. "
                + "Want to quit the current run?");
        //</editor-fold>
    }
}
