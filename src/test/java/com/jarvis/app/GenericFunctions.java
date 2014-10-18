package com.jarvis.app;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.ErrorHandler.UnknownServerException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GenericFunctions {

    public String readFile(String path) throws IOException {
        FileInputStream stream = new FileInputStream(new File(path));
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            /* Instead of using default, pass in a decoder. */
            return Charset.defaultCharset().decode(bb).toString();
        } finally {
            stream.close();
        }
    }

    public void clickOnSkySales(WebDriver wd) {
        WebElement baseElement = wd.findElement(By.id("SkySales"));
        Actions clicker = new Actions(wd);
        clicker.moveToElement(baseElement).moveByOffset(100, 100).click();
    }

    public Boolean IsElementExist(WebDriver wd, String locString) {
        boolean present = false;
        try {

            if (wd.findElement(By.id(locString)).isDisplayed()) {
                present = true;
            } else {
                present = false;
            }
        } catch (UnknownServerException e) {
            present = false;
        } catch (ElementNotVisibleException e) {
            present = false;
        } catch (NoSuchElementException e) {
            present = false;
        } catch (WebDriverException e) {
            present = false;
        }
        return present;

    }

    public String ExtractHrefFromTable(WebDriver wd, String locator, String locString, String TableLocation) {
        String strValue = "", value;
        Boolean blnExist = false;
        blnExist = IsElementExistXpath(wd, TableLocation);

        if (blnExist) {
            List<WebElement> rows = wd.findElements(By.xpath(locString));
            for (WebElement row : rows) {
                WebElement val = row.findElement(By.xpath(TableLocation));
                strValue = val.getText();
                value = val.getAttribute("href");
            }
        } else {
            strValue = "Element does not exist.";
        }
        return strValue;
    }

    public String ExtractTextFromTable(WebDriver wd, String locator, String TableLocation, String CellLocation) {
        String strValue = "", value;
        Boolean blnExist = false;
        blnExist = IsElementExistXpath(wd, TableLocation);

        if (blnExist) {
            List<WebElement> rows = wd.findElements(By.xpath(TableLocation));
            for (WebElement row : rows) {
                WebElement val = row.findElement(By.xpath(CellLocation));
                strValue = val.getText();
                value = val.getAttribute("href");
            }
        } else {
            strValue = "Element does not exist.";
        }
        return strValue;
    }

    public void CompareTextValueTable(WebDriver wd, String StepName, String locString,
            String TableLocation, String ExpectedResults) {
        String ActualResult = ExtractTextFromTable(wd, "", locString, TableLocation);
        String Flag = "PASSED";

        //Writing Results 
        if (ExpectedResults.trim().toUpperCase().equals(ActualResult.trim().toUpperCase())) {
            WriteResults(Global.ResultsFileName, StepName,
                    "Text Value Check", ExpectedResults, ActualResult, "PASSED");
            Flag = "PASSED";
        } else {
            WriteResults(Global.ResultsFileName, StepName,
                    "Text Value Check", ExpectedResults, ActualResult,
                    "FAILED: Missing Page Text: " + ExpectedResults);
            Flag = "FAILED";
        }
        //Overall Testing Status
        if (Flag.toUpperCase().equals("FAILED")) {
            Global.OverallTestingStatus = "FAILED";
        }
    }

    public void CompareTextValueElement(WebDriver wd, String StepName, String locString, String ExpectedResults) {
        String ActualResult = wd.findElement(By.xpath(locString)).getText();
        String Flag = "PASSED";
        //Writing Results 
        if (ExpectedResults.trim().toUpperCase().equals(ActualResult.trim().toUpperCase())) {
            WriteResults(Global.ResultsFileName, StepName,
                    "Text Value Check", ExpectedResults, ActualResult, "PASSED");
            Flag = "PASSED";
        } else {
            WriteResults(Global.ResultsFileName, StepName,
                    "Text Value Check", ExpectedResults, ActualResult,
                    "FAILED: Missing Page Text");
            Flag = "FAILED";
        }
        //Overall Testing Status
        if (Flag.toUpperCase().equals("FAILED")) {
            Global.OverallTestingStatus = "FAILED";
        }
    }

    public void ACTIVATE_SCREENSHOT(String YesNo) {
        switch (YesNo.toUpperCase()) {
            case "YES":
                Global.ActivateScreenshot = true;
                break;
            default:
                Global.ActivateScreenshot = false;
                break;
        }
    }

    public void InputData_ById(WebDriver wd, String StrElementID, String data) {
        String CurrSelected = "";
        Boolean blnExist = false;
        blnExist = IsElementExist(wd, StrElementID);
        if (blnExist) {
            while (CurrSelected.toUpperCase().compareTo(data.replace(".0", "").toUpperCase()) != 0) {
                ClearData_ById(wd, StrElementID);
                wd.findElement(By.id(StrElementID)).sendKeys(data);
                CurrSelected = wd.findElement(By.id(StrElementID)).getAttribute("value");
            }
            Global.ContinueExecution = true;
        } else {
            Global.ContinueExecution = false;
        }
        wait("500");
    }

    public void ClearData_ById(WebDriver wd, String StrElementID) {
        Boolean blnExist = false;
        blnExist = IsElementExist(wd, StrElementID);
        try {
            if (blnExist) {
                wd.findElement(By.id(StrElementID)).clear();
                Global.ContinueExecution = true;
            } else {
                Global.ContinueExecution = false;
            }
        } catch (WebDriverException e) {
            //Do Nothing
        }
    }

    public void ClickElement_ById(WebDriver wd, String StrElementID) {
        Boolean blnExist = false;
        blnExist = IsElementExist(wd, StrElementID);
        if (blnExist) {
            wd.findElement(By.id(StrElementID)).click();
            Global.ContinueExecution = true;
        } else {
            Global.ContinueExecution = false;
        }
    }

    public void CAPTURE_SCREENSHOT(WebDriver wd, String strPage) throws Exception {
        File scrFile = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(Global.ResultsFolder + "\\SCREENSHOTS\\" + strPage + ".png"));
    }

    public void SAVE_SCREENSHOT() throws Exception {
        String files;
        String arrFile[];
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

        //Scan All images in screenshot folder
        File folder = new File(Global.ResultsFolder + "\\SCREENSHOTS\\");
        File[] listOfFiles = folder.listFiles();

        //Print Scenario ID
        //wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Heading1", Global.Scenario_Filename.replace(".xls", ""));

        //Print PNR
        if (Global.Booking_PNR.length() == 6) {
            wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Heading2", "PNR: " + Global.Booking_PNR);
        }

        //Attach all screencaps
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                files = listOfFiles[i].getName();
                if (files.endsWith(".png")) {
                    File file = new File(Global.ResultsFolder + "\\SCREENSHOTS\\" + files);
                    InputStream is = new java.io.FileInputStream(file);
                    long length = file.length();

                    if (length > Integer.MAX_VALUE) {
                        System.out.println("File too large!!");
                    }

                    byte[] bytes = new byte[(int) length];
                    int offset = 0;
                    int numRead = 0;
                    while (offset < bytes.length
                            && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                        offset += numRead;
                    }

                    // Ensure all the bytes have been read in
                    if (offset < bytes.length) {
                        System.out.println("Could not completely read file " + file.getName());
                    }
                    is.close();

                    String filenameHint = null;
                    String altText = null;
                    int id1 = 0;
                    int id2 = 1;

                    org.docx4j.wml.P p3 = newImage(wordMLPackage, bytes, filenameHint, altText, id1, id2, 6000);

                    //Add Heading
                    arrFile = files.split("_");
                    wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Heading2", arrFile[1].replace(".png", ""));

                    //Timestamp 
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Heading3", "Time Taken: " + timeStamp);

                    //Attach Image
                    wordMLPackage.getMainDocumentPart().addObject(p3);
                }
            }

        }
        wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Heading3", Global.OverallTestingStatus);

        //Save Document
        wordMLPackage.save(new java.io.File(Global.ScreenshotFileName));
        for (int Rep = 1; Rep < 6; Rep++) {
            System.out.println("SAVE SCREENSHOT COMPLETED.....");
        }
    }

    //@SuppressWarnings("deprecation")
    public static org.docx4j.wml.P newImage(WordprocessingMLPackage wordMLPackage, byte[] bytes,
            String filenameHint, String altText, int id1, int id2, long cx) throws Exception {
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
        Inline inline = imagePart.createImageInline(filenameHint, altText,
                id1, id2, cx);

        // Now add the inline in w:p/w:r/w:drawing
        org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();
        org.docx4j.wml.P p = factory.createP();
        org.docx4j.wml.R run = factory.createR();
        p.getParagraphContent().add(run);
        org.docx4j.wml.Drawing drawing = factory.createDrawing();
        run.getRunContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
        return p;
    }

    public String verify_element(WebDriver wd, String locator, String locString, String ExpectedResults,
            String ResultsFileName, String TestCaseName, String StepName) {
        WebDriverWait wait = new WebDriverWait(wd, 100);
        String Flag = "FAILED";
        String strActualResults = "Not Available";
        switch (locator) {
            case "xpath":
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locString)));
                    Flag = "PASSED";
                    strActualResults = "Object does exists";

                } catch (Exception e) {
                    strActualResults = "Object does not exists";
                }
                break;
            case "name":
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locString)));
                    Flag = "PASSED";
                    strActualResults = "Object does exists";

                } catch (Exception e) {
                    strActualResults = "Object does not exists";
                }
                break;

            case "id":
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locString)));
                    Flag = "PASSED";
                    strActualResults = "Object does exists";

                } catch (Exception e) {
                    strActualResults = "Object does not exists";
                }
                break;

        }
        WriteResults(ResultsFileName, StepName, TestCaseName, ExpectedResults, strActualResults, Flag);
        wait = null;
        return Flag;
    }

    public void TextWriter(String ComputationFileName, String TextToWrite) throws IOException {
        try {
            FileWriter fstream = new FileWriter(ComputationFileName, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(TextToWrite);
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    public void KeyPress(String strKey) {
        try {
            Robot robot = new Robot();
            switch (strKey.toUpperCase()) {
                case "ENTER":
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    break;

                case "PAGEUP":
                    robot.keyPress(KeyEvent.VK_PAGE_UP);
                    robot.keyRelease(KeyEvent.VK_PAGE_UP);
                    break;

            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void RunProgram(String strProgramLocation) {
        String fPath = GetAbsolutePath();
        String Program = fPath + "\\VB\\" + strProgramLocation;
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec(Program);
        } catch (Exception e) {
        }
    }

    public void WriteResults(String ResultFileName, String Test_ID, String TestCase_Name, String Expected_Results, String Actual_Results, String Flag) {

        InputStream inp;
        try {
            inp = new FileInputStream(ResultFileName);
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.createRow((short) (sheet.getLastRowNum() + 1));

            Cell c = null;
            CreationHelper createHelper = wb.getCreationHelper();

            //Create Font Style
            CellStyle style = wb.createCellStyle();
            Font font = wb.createFont();

            // Default is FAILED
            font.setFontName("Arial");
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.RED.index);

            switch (Flag.toUpperCase()) {
                case "PASSED":
                    font.setColor(HSSFColor.BLACK.index);
                    style.setFont(font);
                    break;
            }

            style.setFont(font);
            c = row.createCell(0);
            c.setCellStyle(style);
            c.setCellValue(createHelper.createRichTextString(Test_ID));

            c = row.createCell(1);
            c.setCellStyle(style);
            c.setCellValue(createHelper.createRichTextString(TestCase_Name));

            c = row.createCell(2);
            c.setCellStyle(style);
            c.setCellValue(createHelper.createRichTextString(Expected_Results));

            c = row.createCell(3);
            c.setCellStyle(style);
            c.setCellValue(createHelper.createRichTextString(Actual_Results));

            c = row.createCell(4);
            c.setCellStyle(style);
            c.setCellValue(createHelper.createRichTextString(Flag));

            FileOutputStream fileOut = new FileOutputStream(ResultFileName);
            wb.write(fileOut);
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CreateOutputFile(String ResultsFileName) {
        Workbook wb = new HSSFWorkbook();
        Font f = wb.createFont();
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
        CellStyle cs = wb.createCellStyle();
        cs.setFont(f);

        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("First Sheet");
        Row row = sheet.createRow((short) 0);

        Cell c = null;

        c = row.createCell(0);
        c.setCellStyle(cs);
        c.setCellValue(createHelper.createRichTextString("Test_ID"));

        c = row.createCell(1);
        c.setCellStyle(cs);
        c.setCellValue(createHelper.createRichTextString("Testcase_Name"));

        c = row.createCell(2);
        c.setCellStyle(cs);
        c.setCellValue(createHelper.createRichTextString("Expected Result"));

        c = row.createCell(3);
        c.setCellStyle(cs);
        c.setCellValue(createHelper.createRichTextString("Actual Result"));

        c = row.createCell(4);
        c.setCellStyle(cs);
        c.setCellValue(createHelper.createRichTextString("Flag"));

        // Write the output to a file
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(ResultsFileName);
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Out of method one!");

    }

    public String[][] excelRead(String MainDataTable) throws Exception {
        // TODO Auto-generated method stub

        File excel = new File(MainDataTable);
        FileInputStream fis = new FileInputStream(excel);
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        HSSFSheet ws = wb.getSheet("Input");


        int rowNum = ws.getLastRowNum() + 1;
        int colNum = ws.getRow(0).getLastCellNum();

        String[][] data = new String[rowNum][colNum];

        for (int i = 0; i < rowNum; i++) {
            HSSFRow row = ws.getRow(i);
            for (int j = 0; j < colNum; j++) {
                HSSFCell cell = row.getCell(j);
                String value = cellToString(cell);
                data[i][j] = value;
            }
        }
        return data;

    }

    public String cellToString(HSSFCell cell) {
        int type;
        Object result;

        type = cell.getCellType();
        switch (type) {

            case 0: //numeric value in excel
                result = cell.getNumericCellValue();
                break;
            case 1: //string value in excel
                result = cell.getStringCellValue();
                break;
            default:
                throw new RuntimeException("There are no support for this type of cell");
        }

        return result.toString();
    }

    public void send_keys_tab(WebDriver wd, String locator, String locString, String data) {
        String CurrSelected = "";
        switch (locator) {
            case "xpath":
                while (CurrSelected.toUpperCase().compareTo(data.replace(".0", "").toUpperCase()) != 0) {
                    wd.findElement(By.xpath(locString)).sendKeys(Keys.TAB);
                }
                break;
        }
    }

    public String getSelectedValue(WebDriver wd, String locString, String ByWhat) {
        String SelectedValue = "";
        switch (ByWhat.toUpperCase()) {
            case "XPATH":
                Select CurrElement = new Select(wd.findElement(By.xpath(locString)));
                SelectedValue = CurrElement.getFirstSelectedOption().getText();
                break;
            case "ID":
                Select CurrElementA = new Select(wd.findElement(By.id(locString)));
                SelectedValue = CurrElementA.getFirstSelectedOption().getText();
                break;
        }
        return SelectedValue;
    }

    public void send_keys(WebDriver wd, String locator, String locString, String data) {
        String CurrSelected = "";
        switch (locator) {
            case "xpath":
                while (CurrSelected.toUpperCase().compareTo(data.replace(".0", "").toUpperCase()) != 0) {
                    wd.findElement(By.xpath(locString)).sendKeys(data);
                    CurrSelected = getSelectedValue(wd, locString, "XPATH");
                }
                break;

            case "name":
                wd.findElement(By.name(locString)).sendKeys(data);
                wd.findElement(By.name(locString)).sendKeys(Keys.RETURN);
                wd.findElement(By.name(locString)).sendKeys(Keys.TAB);
                break;

            case "id":
                wd.findElement(By.id(locString)).sendKeys(data);
                break;

        }

    }

    public String GenerateTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy_hmmss");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public String GetAbsolutePath() {
        File f = new File("");
        String fPath = f.getAbsolutePath();
        return fPath;
    }

    public String getTxtValue(WebDriver wd, String locator, String locString) {
        String stringValue = "";
        switch (locator) {
            case "xpath":
                stringValue = wd.findElement(By.xpath(locString)).getText();
            case "id":
                stringValue = wd.findElement(By.id(locString)).getText();

        }
        return stringValue;
    }

    public void wait(String intWait) {
        long l = Long.parseLong(intWait);
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void click_element(WebDriver wd, String locator, String locString,
            String ResultsFileName, String TestCaseName, String StepName) {
        verify_element(wd, locator, locString, "Object does Exist", ResultsFileName, TestCaseName, StepName);
        switch (locator) {
            case "xpath":
                wd.findElement(By.xpath(locString)).click();
                break;

            case "name":
                wd.findElement(By.name(locString)).click();
                break;

            case "id":
                wd.findElement(By.id(locString)).click();
                break;
            case "linkText":
                wd.findElement(By.linkText(locString)).click();
                break;
        }
    }

    public Boolean IsChecked(WebDriver wd, String locator, String locString) {
        Boolean blnValue = false;
        switch (locator) {
            case "xpath":
                blnValue = wd.findElement(By.xpath(locString)).isSelected();
            case "id":
                blnValue = wd.findElement(By.id(locString)).isSelected();

        }
        return blnValue;
    }

    public Boolean IsElementExistXpath(WebDriver wd, String locString) {
        boolean present = false;
        try {
            if (wd.findElement(By.xpath(locString)).isDisplayed()) {
                present = true;
            } else {
                present = false;
            }
        } catch (UnknownServerException e) {
            present = false;
        } catch (ElementNotVisibleException e) {
            present = false;
        } catch (NoSuchElementException e) {
            present = false;
        } catch (WebDriverException e) {
            present = false;
        }
        return present;
    }

    public void JarvisVerificationPoint(String Test_ID, String TestCase_Name, String ExpectedResults,
            String ActualResult, String FailureDetails) {
        String Flag = "PASSED";
        //Writing Results 
        if (ExpectedResults.toUpperCase().trim().equals(ActualResult.toUpperCase().trim())) {
            WriteResults(Global.ResultsFileName, Test_ID,
                    TestCase_Name, ExpectedResults, ActualResult, "PASSED");
            Flag = "PASSED";
        } else {
            if (!ExpectedResults.toUpperCase().equals("NOT APPLICABLE")) {
                WriteResults(Global.ResultsFileName, Test_ID,
                        TestCase_Name, ExpectedResults, ActualResult, "FAILED: " + FailureDetails);
                Flag = "FAILED";
            }
        }
        //Overall Testing Status
        if (Flag.toUpperCase().equals("FAILED")) {
            Global.OverallTestingStatus = "FAILED";
        }
    }

    public void Scheduler(String Day, String Hour, String Minutes) {
        // Get calendar set to the current date and time  
        Calendar cal = Calendar.getInstance();

        // Set time of calendar to 18:00  
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth + 1);
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(Hour));
        cal.set(Calendar.MINUTE, Integer.parseInt(Minutes));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // Check if current time is after 18:00 today  
        boolean afterTime = Calendar.getInstance().after(cal);
        while (!afterTime) {
            afterTime = Calendar.getInstance().after(cal);

        }
        System.out.println("Tigerair Shakeout has commenced.");
    }

    public void GenerateLastName() throws ParseException {
        String[] DateParse;
        String StMonth = "No";
        String StDay = "MAD";
        String LName;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = sdf.parse(sdf.format(new Date()));
        DateParse = dateWithoutTime.toString().split(" ");

        // <editor-fold defaultstate="collapsed" desc="COMMENTED CODE">
//        switch (DateParse[1].toUpperCase()) {
//            case "01":
//                StMonth = "JAN";
//                break;
//            case "02":
//                StMonth = "FEB";
//                break;
//            case "03":
//                StMonth = "MAR";
//                break;
//            case "04":
//                StMonth = "APR";
//                break;
//            case "05":
//                StMonth = "MAY";
//                break;
//            case "06":
//                StMonth = "JUN";
//                break;
//            case "07":
//                StMonth = "JUL";
//                break;
//            case "08":
//                StMonth = "AUG";
//                break;
//            case "09":
//                StMonth = "SEP";
//                break;
//            case "10":
//                StMonth = "OCT";
//                break;
//            case "11":
//                StMonth = "NOV";
//                break;
//            case "12":
//                StMonth = "DEC";
//                break;
//        }
        // </editor-fold>
        switch (DateParse[2].toUpperCase()) {
            case "01":
                StDay = "ONE";
                break;
            case "02":
                StDay = "TWO";
                break;
            case "03":
                StDay = "THREE";
                break;
            case "04":
                StDay = "FOUR";
                break;
            case "05":
                StDay = "FIVE";
                break;
            case "06":
                StDay = "SIX";
                break;
            case "07":
                StDay = "SEVEN";
                break;
            case "08":
                StDay = "EIGHT";
                break;
            case "09":
                StDay = "NINE";
                break;
            case "10":
                StDay = "TEN";
                break;
            case "11":
                StDay = "ELEVEN";
                break;
            case "12":
                StDay = "TWELVE";
                break;
            case "13":
                StDay = "THIRTEEN";
                break;
            case "14":
                StDay = "FOURTEEN";
                break;
            case "15":
                StDay = "FIFTEEN";
                break;
            case "16":
                StDay = "SIXTEEN";
                break;
            case "17":
                StDay = "SEVENTEEN";
                break;
            case "18":
                StDay = "EIGHTTEEN";
                break;
            case "19":
                StDay = "NINETEEN";
                break;
            case "20":
                StDay = "TWENTY";
                break;
            case "21":
                StDay = "TWENTYONE";
                break;
            case "22":
                StDay = "TWENTYTWO";
                break;
            case "23":
                StDay = "TWENTYTHREE";
                break;
            case "24":
                StDay = "TWENTYFOUR";
                break;
            case "25":
                StDay = "TWENTYFIVE";
                break;
            case "26":
                StDay = "TWENTYSIX";
                break;
            case "27":
                StDay = "TWENTYSEVEN";
                break;
            case "28":
                StDay = "TWENTYEIGHT";
                break;
            case "29":
                StDay = "TWENTYNINE";
                break;
            case "30":
                StDay = "THIRTY";
                break;
            case "31":
                StDay = "THIRTYONE";
                break;
        }
        LName = DateParse[1] + StDay;
        Global.UniversalLName = LName;

    }

    public void CloseNotification(WebDriver wd, String CustomMessage) {
        JFrame frmOpt;
        frmOpt = new JFrame();
        frmOpt.setVisible(true);
        frmOpt.setLocation(100, 100);
        frmOpt.setAlwaysOnTop(true);
        int intYes = JOptionPane.showConfirmDialog(null, CustomMessage, "WARNING",
                JOptionPane.YES_NO_OPTION);

        if (intYes == 0) {
            //Quit Program
            wd.close();
            wd.quit();
            frmOpt.setVisible(false);
        } else {
            //no option
        }
    }

    public void SelectLanguage(WebDriver wd, String Culture) {
        Boolean blnExist = false;
        String ActualResult = "FALSE", CultureButton = ".//*[@id='enGB']";
        String LanguageButton = ".//*[@id='languageLabel']";
        blnExist = IsElementExistXpath(wd, LanguageButton);

        if (blnExist) {
            wd.findElement(By.xpath(LanguageButton)).click();
            switch (Culture.toUpperCase()) {
                case "EN":
                    CultureButton = ".//*[@id='enGB']";
                    break;
                case "ID":
                    CultureButton = ".//*[@id='idID']";
                    break;
                case "TW":
                    CultureButton = ".//*[@id='zhTW']";
                    break;
                case "CN":
                    CultureButton = ".//*[@id='zhCN']";
                    break;
                case "HK":
                    CultureButton = ".//*[@id='zhHK']";
                    break;
                case "TH":
                    CultureButton = ".//*[@id='thTH']";
                    break;
            }
            wd.findElement(By.xpath(CultureButton)).click();
        }

        blnExist = IsElementExistXpath(wd, ".//*[@id='divProgress']");
        while (blnExist) {
            wait("5000");
            blnExist = IsElementExistXpath(wd, ".//*[@id='divProgress']");
        }
    }

    public void SelectMCC(WebDriver wd, String MCC) {
        Boolean blnExist = false;
        String ActualResult = "FALSE", MCCButton = "", MCCText = "";
        String CurrencyButton = ".//*[@id='currencyLabel']";
        blnExist = IsElementExistXpath(wd, CurrencyButton);
        int intStarting = Global.intMCCStart;


        if (!MCC.contains(Global.MCC)) {
            if (blnExist) {
                wd.findElement(By.xpath(CurrencyButton)).click();
                for (int i = intStarting; i < 10000; i++) {
                    if (MCC.toUpperCase().contains("DEFAULT")) {
                        wd.findElement(By.xpath(".//*[@id='default']")).click();
                        break;
                    } else {
                        MCCButton = ".//*[@id='" + Global.MCCStart + Integer.toString(i) + "']";
                        blnExist = IsElementExistXpath(wd, MCCButton);
                        if (blnExist) {
                            MCCText = wd.findElement(By.xpath(MCCButton)).getText();
                            if (MCCText.trim().toUpperCase().contains(MCC.toUpperCase())) {
                                wd.findElement(By.xpath(MCCButton)).click();
                                break;
                            }
                        }
                    }

                }
            }
        }

        blnExist = IsElementExistXpath(wd, ".//*[@id='divProgress']");
        while (blnExist) {
            wait("5000");
            blnExist = IsElementExistXpath(wd, ".//*[@id='divProgress']");
        }

        Global.MCC = MCC;
    }
}
