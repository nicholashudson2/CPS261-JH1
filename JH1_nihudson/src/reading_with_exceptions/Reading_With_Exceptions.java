package reading_with_exceptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Scanner;

public class Reading_With_Exceptions
{
  /**
   * This method, given a valid file name, processes the file (if one exists) of 
   * the given name, creates a new file with the name specified in the given 
   * file, and copies the specified number of integer values from the input 
   * file to the output file. If no number is specified, or if the specified 
   * number is invalid, all integer values will be copied.
   * @param inputFilename The name of the input file containing the desired output 
   * filename, the number of integer values to copy, and the integer values to be 
   * copied.
   */
  void process(String inputFilename)
  {
    int count = -1;
    try
    {
      Scanner inputFileReader = new Scanner(new FileReader(new File(inputFilename)));
      String outputFileName = inputFileReader.next();
      File outputFile = new File(outputFileName);
      if(inputFileReader.hasNextInt())
      {
        count = inputFileReader.nextInt();
      }
      else
      {
        System.out.println("process(" + inputFilename + "): Number of values to copy to " + outputFileName
            + " invalid or unspecified. Copying all values.");
        if(inputFileReader.hasNext())
        {
          inputFileReader.next();
        }
      }
      PrintStream filePrintStream = new PrintStream(outputFile);
      copyNumbers(inputFileReader, filePrintStream, count);
      filePrintStream.close();
      System.out.println(outputFileName + " created with the following output:");
      printToScreen(outputFileName);
      if(inputFileReader != null)
      {
        inputFileReader.close();
      }
    }
    catch(FileNotFoundException e)
    {
      System.out.println("process(" + inputFilename + "): Unable to locate file with the provided name.");
    }
  }

  /**
   * This method copies the specified number of integer values from the given
   * input Scanner to the given PrintStream.
   * 
   * @param scan A Scanner object to copy integers from
   * @param ps A PrintStream to write the integers to
   * @param numIntsToRead The number of integers to read. A value of -1 ==> read all integers.
   */
  void copyNumbers(Scanner scan, PrintStream ps, int numIntsToRead)
  {
    int currentIndex = 0;
    while(currentIndex != numIntsToRead)
    {
      if(currentIndex % 10 == 0)
      { // After every 10th number is printed,
        ps.println(); // move to a new line in the output file.
      }
      if(scan.hasNext())
      {
        if(scan.hasNextInt())
        {
          ps.print(scan.nextInt() + " ");
          ps.flush();
        }
        else
        {
          System.out.println("copyNumbers(): Invalid integer encountered: " + scan.next() + " - Skipping to next value.");
        }
      }
      else 
      {
        break;
      }
      currentIndex++;
    }
  }

  public static void main(String[] args)
  {
    if(args.length == 0)
    {
      args = new String[]
      { "file1.txt", "non-existent-file", "file2.txt", "file3.txt" };
    }
    Reading_With_Exceptions rwe = new Reading_With_Exceptions();
    for(int i = 0; i < args.length; i++)
    {
      System.out.println("\n\n=========== Processing " + args[i] + " ==========\n");
      rwe.process(args[i]);
    }
  }

  /**
   * This method opens the file with the given filename, and copies 
   * the contents of the file to the standard output.
   * @param filename The name of the file to output.
   */
  private void printToScreen(String filename)
  {
    Scanner scan = null;
    try
    {
      FileInputStream fileInputStream = new FileInputStream(filename);
      scan = new Scanner(fileInputStream);
      while(scan.hasNextLine())
      {
        System.out.println(scan.nextLine());
      }
    }
    catch(FileNotFoundException e)
    {
      System.out.println("printToScreen(" + filename + "): Unable to locate file with the given name.");
    }
    finally
    {
      if(scan != null)
        scan.close();
    }
  }
}