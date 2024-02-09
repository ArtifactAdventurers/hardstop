package dev.gruff.hardstop.api.anz.rules.upward;

import dev.gruff.hardstop.api.HSClass;
import dev.gruff.hardstop.api.SemanticVersion;
import dev.gruff.hardstop.api.anz.RuleTest;
import dev.gruff.hardstop.api.anz.Rule;


@Rule(id="CCV.0",title="check compiler version")
public class CompilerVersion  {
    @Rule(id="CCV.01",title = "check if java compiler changed",
            onFail = LoweredCompilerState.class
    )
    public boolean check(HSClass a, HSClass b) {
        SemanticVersion v1= a.compilerVersion();
        SemanticVersion v2= b.compilerVersion();
        return v1.compareTo(v2) > 0;

    }
}
