package autokey;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser<br>
 */
public class Parser
{
   public static final String NAME_REGEX = "((?:[a-z][a-z]+))(,)(\\s+)((?:[a-z][a-z]+))((?:\\s[a-z][a-z]+))?((?:\\s[a-z]+))?";
   public static final String DATE_REGEX = "((?:(?:[0-2]?\\d{1})|(?:[3][01]{1})))(?![\\d])(?=\\s[a-zA-Z]).*?((?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Sept|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?)).*?((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])";
   public static final String EMAIL_REGEX = "([\\w-+]+(?:\\.[\\w-+]+)*@(?:[\\w-]+\\.)+[a-zA-Z]{2,7})";
   public static final String GENDER_REGEX = "(?:M(?:ale)?|F(?:emale)?)";
   public static final String PHONE_REGEX = "\\(?\\b[0-9]{3}\\)?[-. ]?[0-9]{3}[-. ]?[0-9]{4}\\b";
   public static final String RECORD_REGEX = "(?:(\\d{3})).*?(?:(\\d{4})).*?(?:(\\w{4}))";
   public static final String SKIP_REGEX = ".*?";

   private String childRegex;

   /**
    * Constructs a parser objects and builds the regular expression for a child
    * value
    */
   public Parser()
   {
      setChildRegex(NAME_REGEX + SKIP_REGEX + GENDER_REGEX + SKIP_REGEX
            + DATE_REGEX);
   }

   /**
    * Checks to see if a part of the passed in string contains a match to the
    * passed in regular expression
    * 
    * @param pStr
    *           the string to check
    * @param regex
    *           the regular expression to check against
    * @return A true or false depending on whether or not the string contains
    *         the regular expression
    */
   public static boolean containsMatch(String pStr, String pRegex)
   {
      Pattern p = Pattern.compile(pRegex, Pattern.CASE_INSENSITIVE
            | Pattern.DOTALL);
      Matcher m = p.matcher(pStr);

      return m.find();
   }

   /**
    * @param strToParse
    * @param regex
    * @return
    */
   public Matcher parse(String strToParse, String regex)
   {
      Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE
            | Pattern.DOTALL);
      Matcher m = p.matcher(strToParse);

      return m.find() ? m : null;
   }

   /**
    * @param str
    * @return
    */
   public String parseChildInformation(String str)
   {
      Matcher m = parse(str, this.childRegex);
      if (m != null)
      {
         return m.group(1) + " " + m.group(2) + " " + m.group(3);
      }
      else
      {
         return null;
      }
   }

   /**
    * @param str
    * @return
    */
   public String parseDate(String str)
   {
      Matcher m = parse(str, DATE_REGEX);
      if (m != null)
      {
         return m.group(1) + " " + m.group(2) + " " + m.group(3);
      }
      else
      {
         return null;
      }
   }

   /**
    * @param str
    * @return
    */
   public String parseEmail(String str)
   {
      Matcher m = parse(str, EMAIL_REGEX);
      if (m != null)
      {
         return m.group();
      }
      else
      {
         return null;
      }
   }

   /**
    * Tokenize a group of words that match a name
    * 
    * @param str
    *           the string containing the values
    * @return a string with only the tokenized values
    */
   public String parseName(String str)
   {
      Matcher m = parse(str, NAME_REGEX);

      if (m != null)
      {
         return m.group();
      }
      else
      {
         return null;
      }
   }

   /**
    * Tokenize a record number from the passed in string. Skips over characters
    * between the numbers.
    * 
    * @param str
    *           the string containing a record number in it
    * @return the tokenized record number
    */
   public String parseRecord(String str)
   {
      Matcher m = parse(str, RECORD_REGEX);
      if (m != null)
      {
         return m.group(1) + "-" + m.group(2) + "-" + m.group(3);
      }
      else
      {
         return null;
      }
   }

   /**
    * @param str
    * @return
    */
   public String parsePhone(String str)
   {
      Matcher m = parse(str, PHONE_REGEX);
      if (m != null)
      {
         return m.group();
      }
      else
      {
         return null;
      }
   }

   /**
    * @return the childRegex
    */
   public String getChildRegex()
   {
      return childRegex;
   }

   /**
    * @param childRegex
    *           the childRegex to set
    */
   public void setChildRegex(String childRegex)
   {
      this.childRegex = childRegex;
   }
}
