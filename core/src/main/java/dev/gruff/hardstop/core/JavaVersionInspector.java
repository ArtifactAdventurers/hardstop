package dev.gruff.hardstop.core;

import dev.gruff.hardstop.api.RavenClass;

public class JavaVersionInspector {


    public static String version(RavenClass rrc) {
        return version(rrc.major(),rrc.minor());
    }



    private static String version(int major,int minor) {
        if (major < 45 || major > 200) return "invalid";
        if (major > 48) {
            if (major < 51) {
                if (major == 50) return "6.0";
                else return "5.0";
            } else {
                int dv = (major - 51) + 7;
                return "" + dv;
            }
        } else {
            int dv = major - 44;
            return "1." + dv;
        }
    }
}
