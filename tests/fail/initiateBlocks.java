package fail;



public class initiateBlocks{
    int a = 5;
    
    static {
        a = 2;
    }

    static {
        int c = a;
    }

    static {
        this.a = 2;
    }

    static {
        return 0;
    }
    
    {
        return 0;
    }

}