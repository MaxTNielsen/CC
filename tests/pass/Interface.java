package pass;


interface Interf {
    static int d = 4;

    int e = 5;
    
    int a();
}

interface SubInterf extends pass.Interf {
    int b();
}


class InterfaceTest implements SubInterf  {
    int test = 1;
    static int a = 1;

    static {
        a = 2;
    }

    void b(int a) {

    }

    void a(int a) {

    }

    public int a() {
        return 1;
    }

    @Override
    public int b() {
        return 42;
    }

    String abc(){
        return "hejsa";
    }

}