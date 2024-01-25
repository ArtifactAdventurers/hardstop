package dev.gruff.hardstop.core.builder;

import dev.gruff.hardstop.api.HSStore;
import dev.gruff.hardstop.core.impl.HSStoreImpl;

public class HSStoreBuilder {


    public static Config config() {
        return new Config();
    }

    public static class Config {

        public HSStore build() {
            return new HSStoreImpl();
        }
    }

}
