package Unsupported_test.pass;
public class LOR {
    public static void main(String[] args) {
        boolean x = true;
        boolean y = false;
        x = x^x && y || x;
    }
}
