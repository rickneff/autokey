package autokey;

import java.awt.event.KeyEvent;
import java.util.Properties;
import java.util.regex.Matcher;

public class EnterKeyStrokesInMLS
   implements Runnable
{
   /**
    * When set to TRUE puts the output only to the console. When set
    * to false outputs to the console and to MLS.
    */
   private boolean debugMode;

   private Type mMonthEntryType;

   private Properties mTokenizedData;
   private Parser mParser;
   private SmartRobot mRobot;

   /**
    * Type of resource path is one of the following:
    * <p>
    * INT = Enters the month in its integer format
    * <p>
    * POP_UP = Enters the month via the pop up box
    * <p>
    * If no type is specified or the type can't be found, the default is POPUP.
    */
   public enum Type
   {
      INT, POPUP
   };

   /**
    * @param pKey
    *           the key where the date is stored in the properties list
    * @return a string array containing the string components
    */
   private String[] getDate(String pKey)
   {
      String date = mTokenizedData.getProperty(pKey);
      Matcher m = mParser.parse(date, Parser.DATE_REGEX);

      String[] retString = new String[m.groupCount()];

      for (int i = 0; i < retString.length; ++i)
      {
         retString[i] = m.group(i + 1);
      }
      return retString;
   }

   /**
    * Gets the index of a month for use in the MLS month picker
    * 
    * @param pMonth
    *           the month you which to find an index for
    * @return the index of the passed in month
    */
   private int getMonthIndex(String pMonth, Type mMonthEntryType)
   {
      int retInt = -1;
      String[] monthArray = null;
      if (mMonthEntryType.equals(Type.POPUP))
      {
         monthArray = new String[] { "Jan", "Apr", "Jul", "Oct", "Feb", "May",
               "Aug", "Nov", "Mar", "Jun", "Sep", "Dec" };
      }
      else
      {
         monthArray = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
               "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
      }

      for (int i = 0; i < monthArray.length; ++i)
      {
         if (pMonth.equals(monthArray[i]))
         {
            retInt = i;
            break;
         }
      }
      
      if (mMonthEntryType.equals(Type.INT))
         retInt++;
      
      return retInt;
   }

   /**
    * Inserts the date into MLS
    * 
    * @param pKey
    *           the type of date you want returned
    */
   private void insertDate(String pKey) throws Exception
   {
      String[] date = getDate(pKey);

      insertDate(date);
   }

   /**
    * Inserts the date into MLS
    * 
    * @param dateArray
    *           an already formed date array
    */
   private void insertDate(String[] dateArray) throws Exception
   {
      type(dateArray[0]); // day
      tab();
      insertMonth(dateArray[1]);
      tab();
      type(dateArray[2]); // year
   }

   /**
    * Uses the MLS month picker to select the correct month
    * 
    * @param pMonth
    *           the month you wish selected
    */
   private void insertMonth(String pMonth) throws Exception
   {
      /*
       * purposefully mess up the month in order for MLS to be "helpful and
       * launch the month picker
       */
      int monthIndex = getMonthIndex(pMonth, mMonthEntryType);
      
      if (mMonthEntryType.equals(Type.POPUP))
      {
         type("ay");
   
         // get to the requested month
         tab(monthIndex);
   
         // select it
         pressSpace();
   
         // press okay
         mRobot.keyType(KeyEvent.VK_O, KeyEvent.VK_ALT);
      }
      else
      {
         type(Integer.toString(monthIndex));
      }
   }

   /**
    * When an item is inserted into a single field, this makes that simple
    * 
    * @param pKey
    *           the type of item you want inserted
    */
   private void insertSingleItem(String pKey) throws Exception
   {
      type(mTokenizedData.getProperty(pKey));
   }

   public void run()
   {
      try
      {
         prepareMLS();
         tab();
         insertDate("sustained");
         tab(4);
         mRobot.pressSpace();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Gets focus of the MLS program
    */
   private void prepareMLS() throws Exception
   {
      if (!debugMode)
      {
         mRobot.pressAltTab();
      }
   }

   /**
    * Presses the down arrow a number of times
    * 
    * @param pNumTimes
    *           the number of times required to get to the correct priesthood
    *           office
    */
   private void pressDownArrow(int pNumTimes) throws Exception
   {
      for (int i = 0; i < pNumTimes; ++i)
      {
         System.out.println("<Down Arrow>");
         if (!debugMode)
         {
            mRobot.keyType(KeyEvent.VK_DOWN);
         }
      }
   }

   /**
    * Tells the SmartRobot to send a spacebar key event
    */
   private void pressSpace() throws Exception
   {
      System.out.println("<Space>");
      if (!debugMode)
      {
         mRobot.pressSpace();
      }
   }

   /**
    * Presses the keys required for the next step in MLS
    */
   private void nextStep() throws Exception
   {
      System.out.println("<Next Step>");
      if (!debugMode)
      {
         mRobot.keyType(KeyEvent.VK_N, KeyEvent.VK_ALT);
      }
   }

   /**
    * Remove item from the string
    * 
    * @param pString
    *           The string containing the item
    * @param pItem
    *           the item you wish removed
    * @return a string without the item
    */
   private String removeItem(String pString, String pItem)
   {
      return pString.replace(pItem, "");
   }

   /**
    * Tabs backwards once
    */
   private void reverseTab() throws Exception
   {
      reverseTab(1);
   }

   /**
    * Tabs backwards a user defined number of times
    * 
    * @param pNumTimes
    *           the number of times you wish to tab backwards
    */
   private void reverseTab(int pNumTimes) throws Exception
   {
      for (int i = 0; i < pNumTimes; ++i)
      {
         System.out.println("<Reverse Tab>");
         if (!debugMode)
         {
            mRobot.keyType(KeyEvent.VK_TAB, KeyEvent.VK_SHIFT);
         }
      }
   }

   /**
    * Removes bad characters from a string
    * 
    * @param pStrToSanitize
    *           String to be sanitized
    * @return a sanitized string
    */
   private String sanitizeString(String pStrToSanitize)
   {
      if (pStrToSanitize != null)
      {
         if (pStrToSanitize.contains("\r"))
         {
            pStrToSanitize = removeItem(pStrToSanitize, "\r");
         }
         if (pStrToSanitize.contains("\n"))
         {
            pStrToSanitize = removeItem(pStrToSanitize, "\n");
         }
      }
      return pStrToSanitize;
   }

   /**
    * Overloads the class's tab method to allow a default single tab.
    */
   private void tab() throws Exception
   {
      tab(1);
   }

   /**
    * Calls the SmartRobot's tab a number of times equal to the passed in int
    * 
    * @param pNumTimes
    *           the number of times you wish the tab button pressed
    */
   private void tab(int pNumTimes) throws Exception
   {
      for (int i = 0; i < pNumTimes; ++i)
      {
         System.out.println("<tab>");
         if (!debugMode)
         {
            mRobot.tab();
         }
      }
   }

   /**
    * Wraps the SmartRobot's type method into an easier to type method
    * 
    * @param pStringToType
    *           the string you wish passed to the SmartRobot object
    */
   private void type(String pStringToType) throws Exception
   {
      String sanitizedString = sanitizeString(pStringToType);
      
      if (sanitizedString == null)
      {
         System.out.println("<blank>");
      }
      else
      {
         System.out.println(sanitizedString);
         if (!debugMode)
         {
            mRobot.type(sanitizedString);
         }
      }
   }

   /**
    * <b>CONSTRUCTOR</b> <br>
    * <br>
    * Construct the SmartRobot object and catch an error if the object cannot be
    * constructed.
    */
   public EnterKeyStrokesInMLS(Type pType)
   {
      this(true, pType);
   }

   /**
    * <b>CONSTRUCTOR</b> <br>
    * <br>
    * Construct the SmartRobot object and catch an error if the object cannot be
    * constructed.
    */
   public EnterKeyStrokesInMLS(boolean pDebugMode, Type pType)
   {
      debugMode = pDebugMode;
      mParser = new Parser();
      mTokenizedData = new Properties(System.getProperties());
      try
      {
         mRobot = new SmartRobot();
      }
      catch (Exception e)
      {
         System.out.println("java.awt.Robot is not allowed to start in your enviroment.\n");
         e.printStackTrace();
      }

      if (pType != null && pType == Type.INT)
      {
         mMonthEntryType = Type.INT;
      }
      else
      {
         mMonthEntryType = Type.POPUP;
      }
   }

   /**
    * @return the mTokenizedData
    */
   public Properties getTokenizedData()
   {
      return mTokenizedData;
   }

   /**
    * @param pTokenizedData
    *           a properties list that is used to set the mTokenizedData
    *           instance variable.
    */
   public void setTokenizedData(Properties pTokenizedData)
   {
      mTokenizedData = pTokenizedData;
   }
}
