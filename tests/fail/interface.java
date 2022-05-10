package fail;


interface Interface {
    void methodA();

    int methodR(){
        return "hej";
    }
}


interface SubInterf extends Interface {
    void methodB();
}


class InterfaceTest implements SubInterf  {
    int test = 1;
    void b(int a) {}
}