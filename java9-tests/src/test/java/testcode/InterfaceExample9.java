package testcode;

public interface InterfaceExample9 {

    abstract int a1();

    default int a2() {
        return a3();
    }
    private int a3() {
        return a1();
    }
}
