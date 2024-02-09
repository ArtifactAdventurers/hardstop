package dev.gruff.hardstop.api.anz.rules.upward;

import dev.gruff.hardstop.api.HSClass;
import dev.gruff.hardstop.api.anz.RuleTest;


/* check if type has changed from class / interface / enum to something else */

public class CheckType  {
    public boolean check(HSClass a, HSClass b) {

        HSClass.Type aType=a.type();
        HSClass.Type bType=b.type();

        return aType==bType;


    }
}
