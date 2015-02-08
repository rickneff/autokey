package autokey;

import autokey.EnterKeyStrokesInMLS.Type;

public class Run
{
   public static void main(String[] args)
   {
      new EnterKeyStrokesInMLS(Boolean.getBoolean("debug"), Type.INT).run();
   }
}
