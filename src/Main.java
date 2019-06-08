import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by LinkClink on 24.05.2019.
 */
public class Main
{

    public static void main( String []args )
    {

        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new GUI();
            }
        });

    }

}
