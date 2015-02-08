package autokey;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class SmartRobot extends Robot
{
   private static final int delayTime = 40;

   /**
    * Constructor:
    * 
    * Calls the super constructor of the parent class and also sets the
    * AutoDelay
    * 
    * @throws AWTException
    */
   public SmartRobot() throws AWTException
   {
      super();
      setAutoDelay(delayTime);
   }

   /**
    * Presses the key combination of <code>Alt+F</code> to open the file menu
    */
   private void openFileMenu() throws Exception
   {
      keyType(KeyEvent.VK_F, KeyEvent.VK_ALT);
   }

   /**
    * Checks the passed in char for special punctuation, if it matches then it
    * records the representative integer value and calls <code>keyType</code>
    * with the shift
    * modifier. <br>
    * <br>
    * If it doesn't find a match then it assumes it is a normal punctuation or
    * digit, records the integer representative of it and then calls
    * <code>keyType</code> with no modifiers.
    * 
    * @param pKey
    *           The char corresponding to the key you wish pressed
    */
   private void typeNonAlphaKey(char pKey)  throws Exception
   {
      boolean shift = true;
      int keyCode;

      switch (pKey)
      {
         case '~':
            keyCode = (int) '`';
            break;
         case '!':
            keyCode = (int) '1';
            break;
         case '@':
            keyCode = (int) '2';
            break;
         case '#':
            keyCode = (int) '3';
            break;
         case '$':
            keyCode = (int) '4';
            break;
         case '%':
            keyCode = (int) '5';
            break;
         case '^':
            keyCode = (int) '6';
            break;
         case '&':
            keyCode = (int) '7';
            break;
         case '*':
            keyCode = (int) '8';
            break;
         case '(':
            keyCode = (int) '9';
            break;
         case ')':
            keyCode = (int) '0';
            break;
         case ':':
            keyCode = (int) ';';
            break;
         case '_':
            keyCode = (int) '-';
            break;
         case '+':
            keyCode = (int) '=';
            break;
         case '|':
            keyCode = (int) '\\';
            break;
         case '"':
            keyCode = (int) '\'';
            break;
         case '?':
            keyCode = (int) '/';
            break;
         case '{':
            keyCode = (int) '[';
            break;
         case '}':
            keyCode = (int) ']';
            break;
         case '<':
            keyCode = (int) ',';
            break;
         case '>':
            keyCode = (int) '.';
            break;
         case ' ':
            keyCode = KeyEvent.VK_SPACE;
         default:
            keyCode = (int) pKey;
            shift = false;
      }

      if (shift)
         keyType(keyCode, KeyEvent.VK_SHIFT);
      else
         keyType(keyCode);
   }

   /**
    * Overload of the pressKey method by allowing for a character to be passed
    * in. Method type casts the character to an integer then passes to the
    * default pressKey method
    * 
    * @param pKey
    *           The char corresponding to the key you wish pressed
    */
   public void keyType(char pKey) throws Exception
   {
      // Guarantees the character key will map to the
      // proper location for the KeyEvent
      if (Character.isLetter(pKey))
      {
         // convert the character to uppercase because the KeyEvent maps
         // directly to the uppercase variants of the letters
         int keyCode = (int) Character.toUpperCase(pKey);
         if (Character.isUpperCase(pKey))
         {
            keyType(keyCode, KeyEvent.VK_SHIFT);
         }
         else
         {
            keyType(keyCode);
         }
      }
      else
      {
         typeNonAlphaKey(pKey);
      }
   }

   /**
    * Ensures proper use of the keyPress and keyRelease methods by
    * encapsulating them into a keyType method
    * 
    * @param pKeyCode
    *           The code for the key you want pressed
    */
   public void keyType(int pKeyCode) throws Exception
   {
      keyPress(pKeyCode);
      keyRelease(pKeyCode);
   }

   /**
    * Presses the keyModifier then keyCode and releases them in
    * reverse order
    * 
    * @param pKeyCode
    *           The keyCode for the keyboard key that you want pressed
    * @param pKeyModifier
    *           The keyEvent corresponding with <code>Shift</code>,
    *           <code>Alt</code> or <code>Ctrl</code>.
    */
   public void keyType(int pKeyCode, int pKeyModifier) throws Exception
   {
      keyType(pKeyCode, pKeyModifier, 1);
   }

   /**
    * Presses the keyModifier then presses keyCode key the requested number of
    * times and releases them in reverse order
    * 
    * @param pKeyCode
    *           The keyCode for the keyboard key that you want pressed
    * @param pKeyModifier
    *           The keyEvent corresponding with <code>Shift</code>,
    *           <code>Alt</code> or <code>Ctrl</code>.
    * @param numTimes
    *           The number of times you wish the keyCode pressed before
    *           releasing the modifying key.
    */
   public void keyType(int pKeyCode, int pKeyModifier, int numTimes) throws Exception
   {
      keyPress(pKeyModifier);

      for (int i = 0; i < numTimes; ++i)
      {
         keyType(pKeyCode);
      }

      keyRelease(pKeyModifier);
   }

   /**
    * Presses the keyboard buttons the required number of times to navigate to
    * the <code>Create Out of Unit Record</code> option in the program menu
    */
   public void openCreateRecord() throws Exception
   {
      openFileMenu();

      // To reach membership
      int numRepetions = 3;
      for (int i = 0; i < numRepetions; ++i)
      {
         keyType(KeyEvent.VK_RIGHT);
      }

      // to reach ordinances
      numRepetions = 14;
      for (int i = 0; i < numRepetions; ++i)
      {
         keyType(KeyEvent.VK_DOWN);
      }

      keyType(KeyEvent.VK_RIGHT);

      // to reach create out of unit record
      numRepetions = 6;
      for (int i = 0; i < numRepetions; ++i)
      {
         keyType(KeyEvent.VK_DOWN);
      }

      keyType(KeyEvent.VK_ENTER);
   }

   /**
    * <li><b><i>pressAltTab</i></b> <br>
    * <br>
    * <code>public void pressAltTab()</code> <br>
    * <br>
    * Executes the command <code>Alt+Tab</code></li>
    */
   public void pressAltTab() throws Exception
   {
      keyType(KeyEvent.VK_TAB, KeyEvent.VK_ALT, 1);
      delay(200);
   }

   /**
    * Press the space key
    */
   public void pressSpace() throws Exception
   {
      keyType(KeyEvent.VK_SPACE);
   }

   /**
    * Presses the tab key
    */
   public void tab() throws Exception
   {
      tab(1);
   }

   /**
    * Presses the tab key a specified number of times
    * 
    * @param pNumTimes
    *           the number of tabs you want pressed
    */
   public void tab(int pNumTimes) throws Exception
   {
      for (int i = 0; i < pNumTimes; ++i)
      {
         keyType(KeyEvent.VK_TAB);
      }
   }

   /**
    * Writes a string out to where ever the system cursor is currently
    * 
    * @param pStr
    *           Passed in string to be written out
    */
   public void type(String pStr) throws Exception
   {
      if (pStr != null)
      {
         for (int i = 0; i < pStr.length(); ++i)
         {
            keyType(pStr.charAt(i));
         }
      }
   }
}
