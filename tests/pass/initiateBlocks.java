package pass;

class MyClass {
    static int x = 1;
    int y = 2;
}

public class initiateBlocks extends MyClass {
    public int a = 3;
    public static int b = 1;
    public int c = 5;
    double x = 3.14;

    public initiateBlocks() {}

    public initiateBlocks(int a) {
        this();
        this.a = a;
    }

    {
        a = 3;
    }

    {
        this.a = b;
        c = super.y;
    }
    
    static {
        b = 2;
    }
}