public class ForLoopFail {
    public void ForFail() {

        int i = 2;
        int result = 0;

        // Don't initialize variable
        for(j = 0; j < 100; i++)
        {

        }

        //Variable shouldn't exist outside for loop.
        for(int j = 0; j<3;j++) {}
        j = 7;


    }
}
