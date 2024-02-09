package dev.gruff.hardstop.core;

import dev.gruff.hardstop.api.HSClass;
import dev.gruff.hardstop.api.SemanticVersion;
import dev.gruff.hardstop.core.impl.SemanticVersionImpl;

public class JavaVersionInspector {


    public static SemanticVersion version(HSClass rrc) {
        return version(rrc.major(),rrc.minor());
    }



    private static SemanticVersion version(int major,int minor) {
        if (major < 45 || major > 200) return new SemanticVersionImpl(0,major,minor);
        if (major > 48) {
            if (major < 51) {
                if (major == 50) return new SemanticVersionImpl(6,0,0);
                else return new SemanticVersionImpl(5,0,0);
            } else {
                int dv = (major - 51) + 7;
                return new SemanticVersionImpl(dv,0,0);
            }
        } else {
            int dv = major - 44;
            return new SemanticVersionImpl(1,dv,0);

        }
    }
}
