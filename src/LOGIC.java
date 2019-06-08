import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Republic by LinkClink on 24.05.2019.
 */

public class LOGIC
{
    JFileChooser fileChooser_getFileURL = new JFileChooser();
    FileNameExtensionFilter filter_1 = new FileNameExtensionFilter("TxT", new String[]{"txt"});

    static String file_name_get = null;
    static String file_name_get_1 = null;

    static String file_name_1 = "file_0ut1.txt";
    static String file_name_2 = "file_0ut2.txt";

    static String[] char_index = new String[256];
    static String[] char_a = new String[256];

    static PriorityQueue<Node> nodes = new PriorityQueue<>((o1, o2) -> (o1.value < o2.value) ? -1 : 1);
    static TreeMap<Character, String> codes = new TreeMap<>();

    static String text_encode = "";
    static String encoded = "";
    static String decoded = "";

    static int ASCII[] = new int[128];

    int result;

    // Function get file path (MY)
    public String GetFile(String dialog_title)
    {
        String file_url = null;
        fileChooser_getFileURL.addChoosableFileFilter(filter_1);
        fileChooser_getFileURL.setDialogTitle(dialog_title);
        result = fileChooser_getFileURL.showSaveDialog(new JPanel());

        if (fileChooser_getFileURL.getSelectedFile() != null)
        {
            file_url = String.valueOf(fileChooser_getFileURL.getSelectedFile());
        }
        return file_url;
    }

    // Main function code text with file (MY)
    public void codding() throws IOException
    {
        file_name_get = GetFile("Text to coding:");
        File file_in_1 = new File(file_name_get);

        Scanner scanner1 = new Scanner(file_in_1);
        while(scanner1.hasNextLine())
              text_encode+=scanner1.nextLine();
        scanner1.close();

        System.out.println("Text to Encode: " + text_encode);
        handleNewText(text_encode);
    }

    // Function encode text with file
    private static boolean handleNewText(String text) throws IOException
    {
        text_encode = text;

        ASCII = new int[128];

        nodes.clear();
        codes.clear();
        encoded = "";
        decoded = "";

        calculateCharIntervals(nodes, true);
        buildTree(nodes);
        generateCodes(nodes.peek(), "");
        printCodes();
        write_txt_char(file_name_2);
        System.out.println("-- Encoding --");
        encodeText();
        write_txt_encode_decode(encoded,file_name_1);

        return true;
    }

    // Main function decode (MY)
    public void decoding() throws IOException
    {
      int size;

      file_name_get = GetFile("Text to decode:");
      file_name_get_1 = GetFile("Chars table:");
      size = decode_read_txt(file_name_get,file_name_get_1);

      decodeText(size);
    }

    // Main class Node [tree]
    static class Node
    {
        Node left, right;
        double value;
        String character;

        public Node(double value, String character)
        {
            this.value = value;
            this.character = character;
            left = null;
            right = null;
        }

        public Node(Node left, Node right)
        {
            this.value = left.value + right.value;
            character = left.character + right.character;
            if (left.value < right.value)
            {
                this.right = right;
                this.left = left;
            }
            else
            {
                this.right = left;
                this.left = right;
            }
        }
    }

    // Print encode text
    private static void encodeText()
    {
        encoded = "";
        for (int i = 0; i < text_encode.length(); i++)
             encoded += codes.get(text_encode.charAt(i));
        System.out.println("Encoded Text: " + encoded);
    }

    // Function build tree with vector
    private static void buildTree(PriorityQueue<Node> vector)
    {
        while (vector.size() > 1)
            vector.add(new Node(vector.poll(), vector.poll()));
    }

    // Print codes symbols
    private static void printCodes()
    {
        System.out.println("--- Printing Codes ---");
        codes.forEach((k, v) -> System.out.println("'" + k + "' : " + v));
    }

    // Function calculate char intervals with ASCII chars table
    private static void calculateCharIntervals(PriorityQueue<Node> vector, boolean printIntervals)
    {
        if (printIntervals) System.out.println("-- intervals --");
        for (int i = 0; i < text_encode.length(); i++)
             ASCII[text_encode.charAt(i)]++;

        for (int i = 0; i < ASCII.length; i++)
            if (ASCII[i] > 0)
            {
                vector.add(new Node(ASCII[i] / (text_encode.length() * 1.0), ((char) i) + ""));
                if (printIntervals)
                    System.out.println("'" + ((char) i) + "' : " + ASCII[i] / (text_encode.length() * 1.0));
            }
    }

    // Function generate codes from chars [symbol] with help [ tree and intervals ]
    private static void generateCodes(Node node, String s)
    {
        if (node != null)
        {
            if (node.right != null)
                generateCodes(node.right, s + "1");

            if (node.left != null)
                generateCodes(node.left, s + "0");

            if (node.left == null && node.right == null)
                codes.put(node.character.charAt(0), s);
        }
    }

    // Function decode text with .txt , two file [chars and encode text ] (MY)
    private static void decodeText(int size) throws IOException
    {
        int i = 0 ;
        int a = 0 ;

        String resol="";
        decoded = "";

        boolean flag = true;

        while (flag == true)
        {
            if( i == encoded.length()-1 ) flag = false;


            resol += String.valueOf(encoded.charAt(i));

            for(a = 0; a<=size ; a++)
            {

                if(resol.equals(char_index[a]))
                {
                    System.out.print("Hash: "+resol + " Char: " + char_a[a]);
                    System.out.print("\n");
                    decoded+=char_a[a];
                    resol="";
                }
            }
            i++;
        }
        System.out.println("Decoded Text: " + decoded);
        write_txt_encode_decode(decoded,"decode_results.txt");
    }

    // Function write to .txt file encode text results [10010001100101] (MY)
    public static void write_txt_encode_decode(String text , String file_name) throws IOException
    {
        File file_out_1 = new File(file_name);
        FileWriter fileWriter_1 = new FileWriter(file_out_1);
        BufferedWriter bufferedWriter_1 = new BufferedWriter(fileWriter_1);

        bufferedWriter_1.write(text);
        bufferedWriter_1.close();
        fileWriter_1.close();
    }

    // Function write to .txt file [char {symbols} Hash ] results (MY)
    public static void write_txt_char(String file_name) throws IOException
    {
        File file_out_1 = new File(file_name);
        FileWriter fileWriter_1 = new FileWriter(file_out_1);
        BufferedWriter bufferedWriter_1 = new BufferedWriter(fileWriter_1);

        for(Map.Entry<Character,String> entry : codes.entrySet())
        {
            Character key = entry.getKey();
            String value = entry.getValue();

            bufferedWriter_1.write(key);
            bufferedWriter_1.newLine();
            bufferedWriter_1.write(value);
            bufferedWriter_1.newLine();
        }
        bufferedWriter_1.close();
        fileWriter_1.close();
    }

    // Function reading file .txt with results encode [ hash and encode text ] (MY)
    public int decode_read_txt(String file_name_1 ,String file_name_2) throws IOException
    {
        int flag = 0;
        int i=0;
        int a=0;

        File file_in_1 = new File(file_name_1);
        FileReader fileReader_1 = new FileReader(file_in_1);
        BufferedReader bufferedReader_1 = new BufferedReader(fileReader_1);

        encoded = bufferedReader_1.readLine();

        bufferedReader_1.close();
        fileReader_1.close();

        File file_in_2 = new File(file_name_2);
        Scanner scanner1 = new Scanner(file_in_2);

        while (scanner1.hasNextLine())
        {
            String text = scanner1.nextLine();

            if(flag== 0)
            {
                char_a[i]=text;
                flag = 1;
                i++;
            }
            else
            {
                char_index[a]= text;
                flag=0;
                a++;
            }
        }
        scanner1.close();

        return a;
    }
}
