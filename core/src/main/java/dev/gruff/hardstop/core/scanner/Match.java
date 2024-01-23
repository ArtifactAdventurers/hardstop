package dev.gruff.hardstop.core.scanner;

import java.io.InputStream;

public interface Match {
    public void handleClass(String root, String ref, InputStream is);
}
