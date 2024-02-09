package dev.gruff.hardstop.api.anz.rules.upward;

import dev.gruff.hardstop.api.HSClass;
import dev.gruff.hardstop.api.anz.RuleTest;
import dev.gruff.hardstop.api.anz.Rule;
import dev.gruff.hardstop.api.anz.RulesEngine;

@Rule(id="CFA.0", title="Check final attribute")
public class CheckFinalState  {

    @Rule(id="CFA.1", title="check final attribute changed")
    public boolean finalBitChanged(HSClass v1, HSClass v2) {
        return v1.isFinal()==v2.isFinal();
    }
    @Rule(id="CFA.2", title="gained final attribute" , onFail={"CFA.1"})
    public boolean check1(HSClass v1, HSClass v2) {
        return v2.isFinal();

    }

    @Rule(id="CFA.3", title="lost final attribute" , onFail={"CFA.1"})
    public boolean check2(HSClass v1, HSClass v2) {
        return v1.isFinal();

    }
}
