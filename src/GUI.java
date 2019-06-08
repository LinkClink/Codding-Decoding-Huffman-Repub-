import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by LinkClink on 24.05.2019.
 */
public class GUI extends JFrame
{
    JButton button_coding = new JButton("CODING");
    JButton button_decoding = new JButton("DECODING");

    LOGIC logic = new LOGIC();

    GUI()
    {
        super("Huffmun Coding/Decoding");

        setSize(400,100);
        setVisible(true);
        setResizable(false);
        setLayout(new FlowLayout());

        add(button_coding);
        add(button_decoding);

        button_coding.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    logic.codding();
                } catch (FileNotFoundException e1)
                {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        button_decoding.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    logic.decoding();
                } catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }
}
