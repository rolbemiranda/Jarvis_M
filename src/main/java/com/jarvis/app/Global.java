package com.jarvis.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Global {

    public static Boolean MarketCheckIndicator = true;
    public static String MCCStart = "502731";
    public static int intMCCStart = 8000;
    public static String OverallTestingStatus = "PASSED";
    public static String WebCheckinCarrier = "";
    public static String ResultsFileName;
    public static String UniversalLName = "";
    public static String Booking_PNR = "Not Available";
    public static String MCC = "Not Available";
    public static String TigerUrl = "";
    public static String MMBMode = "";
    public static String TestCase_ID;
    public static Double AmountTotal;
    public static String InfantFeeTaxable;
    public static String SelectVariant;
    public static String AdultCount;
    public static String ChildCount;
    public static String InfantCount;
    public static String ComputationFileName;
    public static String ScreenshotFileName;
    public static String FolderTimeStamp;
    public static Boolean IsRoundTrip = false;
    public static Boolean IsWebcheckin = false;
    public static Boolean ContinueExecution = true;
    public static int iData = 0;
    public static String Keyword = "start";
    public static Boolean ActivateScreenshot = true;
    public static String ResultsFolder;
    public static String Scenario_Filename;
    //ADULT DETAILS
    public static List<String> AdultTitle = Arrays.asList("Mr", "Mrs", "Ms", "Mr",
            "Mrs", "Ms", "Mr", "Mrs", "Ms");
    public static List<String> AdultLastName = Arrays.asList("a", "b",
            "c", "d",
            "e", "f",
            "g", "h",
            "i");
    public static List<String> AdultFirstName = Arrays.asList("Alpha", "Alpha", "Alpha", "Alpha",
            "Alpha", "Alpha", "Alpha", "Alpha", "Alpha");
    public static List<String> AdultBDAY = Arrays.asList("10", "2", "3", "4",
            "5", "6", "7", "8", "9");
    public static List<String> AdultBMonth = Arrays.asList("Sep", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug", "Sep");
    public static List<String> AdultBYear = Arrays.asList("1978", "1973", "1988", "1977",
            "1945", "1980", "1971", "1986", "1960");
    public static List<String> AdultNationality = Arrays.asList("United States of America", "Argentina", "China", "Austria",
            "Philippines", "Vietnam", "Philippines",
            "Singapore", "Argentina");
    public static List<String> AdultPExpiryDay = Arrays.asList("7", "2", "3", "4",
            "5", "6", "7", "8", "9");
    public static List<String> AdultPExpiryMonth = Arrays.asList("Jul", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug", "Sep");
    public static List<String> AdultPExpiryYear = Arrays.asList("2019", "2020", "2020", "2020",
            "2020", "2020", "2020", "2020", "2020");
    public static List<String> AdultPNumber = Arrays.asList("447741510", "BBBB2015", "CCCC2015", "DDDD2015",
            "EEEE2015", "FFFF2015", "GGGG2015", "HHHH2015",
            "IIII2015");
    //Child DETAILS
    public static List<String> ChildTitle = Arrays.asList("Miss", "Master", "Miss", "Master",
            "Miss", "Master", "Miss", "Master", "Miss");
    public static List<String> ChildLastName = Arrays.asList("Childa", "Childb",
            "Childc", "Childd",
            "Childe", "Childf",
            "Childg", "Childh",
            "Childi");
    public static List<String> ChildFirstName = Arrays.asList("Child", "Child", "Child", "Child",
            "Child", "Child", "Child", "Child", "Child");
    public static List<String> ChildBDAY = Arrays.asList("13", "13", "13", "14",
            "15", "16", "7", "8", "9");
    public static List<String> ChildBMonth = Arrays.asList("Jul", "Jul", "Jul", "Jul",
            "Jul", "Jul", "Jul", "Jul", "Jul");
    public static List<String> ChildBYear = Arrays.asList("2005", "2005", "2005", "2006",
            "2006", "2006", "2004", "2004", "2004");
    public static List<String> ChildNationality = Arrays.asList("Singapore", "Argentina", "China", "Austria",
            "Philippines", "Vietnam", "Philippines",
            "Singapore", "Argentina");
    public static List<String> ChildPExpiryDay = Arrays.asList("1", "2", "3", "4",
            "5", "6", "7", "8", "9");
    public static List<String> ChildPExpiryMonth = Arrays.asList("Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug", "Sep");
    public static List<String> ChildPExpiryYear = Arrays.asList("2016", "2016", "2016", "2016",
            "2016", "2016", "2016", "2016", "2016");
    public static List<String> ChildPNumber = Arrays.asList("AAAA2016", "BBBB2016", "CCCC2016", "DDDD2016",
            "EEEE2016", "FFFF2016", "GGGG2016",
            "HHHH2016", "IIII2016");
    //Infant DETAILS
    public static List<String> InfantGender = Arrays.asList("Male", "Female", "Male", "Female",
            "Male", "Female", "Male", "Female", "Male");
    public static List<String> InfantLastName = Arrays.asList("Infanta", "Infantb",
            "Infantc", "Infantd",
            "Infante", "Infantf",
            "Infantg", "Infanth",
            "Infanti");
    public static List<String> InfantFirstName = Arrays.asList("Alpha", "Bravo", "Charlie", "Delta",
            "Echo", "Foxtrot", "Golf", "Hotel",
            "India");
    public static List<String> InfantBDAY = Arrays.asList("1", "2", "3", "4",
            "5", "6", "7", "8", "9");
    public static List<String> InfantBMonth = Arrays.asList("Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug", "Sep");
    public static List<String> InfantBYear = Arrays.asList("2013", "2013", "2013", "2013",
            "2013", "2013", "2013", "2013", "2013");
    public static List<String> InfantNationality = Arrays.asList("Singapore", "Argentina", "China", "Austria",
            "Philippines", "Vietnam", "Philippines",
            "Singapore", "Argentina");
    public static List<String> InfantPExpiryDay = Arrays.asList("1", "2", "3", "4",
            "5", "6", "7", "8", "9");
    public static List<String> InfantPExpiryMonth = Arrays.asList("Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug", "Sep");
    public static List<String> InfantPExpiryYear = Arrays.asList("2017", "2017", "2017", "2017",
            "2017", "2017", "2017", "2017", "2017");
    public static List<String> InfantPNumber = Arrays.asList("AAAA2017", "BBBB2017", "CCCC2017", "DDDD2017",
            "EEEE2017", "FFFF2017", "GGGG2017", "HHHH2017",
            "IIII2017");
    //Contact Details
    public static String ContactTitle = "Mr";
    public static String ContactFirstName = "Edwin";
    public static String ContactLastName = "Reyes";
    public static String AddressLine1 = "Street 35";
    public static String AddressLine2 = "Tulsa";
    public static String AddressLine3 = "Chesapeake Energy Arena";
    public static String City = "Oklahoma City";
    public static String Country = "Singapore";
    public static String StateProvince = "Singapore";
    public static String PostalCode = "353535";
    public static String HomePhone = "81180035";
    public static String OtherPhone = "81180035";
    public static String EmailAddress = "edwinreyes@tigerair.com";
    //VISA Details
    public static String VISANUMBER = "4012001021000605";
    public static String VISANUMBER_ThreeDS = "4444333322221111";
    //MASTERCARD Details
    public static String MASTERCARDNUMBER = "5210000010001001";
    public static String MASTERCARDNUMBER_ThreeDS = "5105105105105100";
    public static String MASTERCARDNUMBER_Prod = "5525928020001340";
}
