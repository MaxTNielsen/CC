//package pass;
import java.lang.Integer;
public interface InterFaceTest {
    int method1();
    String method2(int l);
    //int method3();

}

interface InterfaceB extends InterFaceTest {
    int method3();
}

class InterfaceTestImpl1 implements InterfaceB {
    public int method1() {
        return 1;
    }

    public String method2(int l) {
        String a = ">";
        return a.concat(Integer.toString(l));
    }

    public int method3() {
        return 3;
    }
}

class InterfaceTestImpl2 implements InterfaceB {
    public int method1() {
        return -1;
    }

    public String method2(int l) {
        String a = "<";
        return a.concat(Integer.toString(l));
    }

    public int method3() {
        return -3;
    }
}

class InterfaceTestImpl3 extends InterfaceTestImpl2 {
    public int method1() {
        return -11;
    }
}
