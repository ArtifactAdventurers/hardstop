package dev.gruff.hardstop.api.cfp.testdata;

public abstract class KitchenSinkClass17 {

    private class S1 extends KitchenSinkClass17 {
        protected S2 s2=new S2();

        @Override
        public String a1(String s) {
            return KitchenSinkClass17.this.a1(s);
        }
    }
    public static class S2 extends KitchenSinkClass17 {

        @Override
        protected String a1(String s) {
            return null;
        }
    }
    private S1 s1=new S1();


     abstract String a1(String s);

}
