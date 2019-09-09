import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver{

    public static MyImage imageObject; 
    public static Integer desiredWidth;
    public static String outputFileName;
    public static String fileType;

    public static void main(String[] args) throws Exception{
        parseArgs(args);
        testRun();
        saveResult();
    }

    public static void parseArgs(String[] args) throws Exception{
        //Verify that there are three inputs given
        if(args.length!=3){
            System.out.println("Input Error: Wrong amount of input arguments provided.");
            System.exit(0);
        }
        else{
            //Verify that image type is either .png or .jpg
            fileType = args[1];
            if(!fileType.equals("png") && !fileType.equals("jpg")) {
                System.out.println("Input Error: Image file type must be either png or jpg");
                System.exit(0);
            }
            //Verify that image file provided exists.
            File imageFile = new File(args[0]+"."+args[1]);
            if(!imageFile.exists()){
                System.out.println("Input Error: Image file not found.");
                System.exit(0);
            }
            outputFileName = args[0]+"_reduced."+args[1];
            //Read file to a BufferedImage object.
            BufferedImage inputImage = ImageIO.read(imageFile);
            imageObject = new MyImage(inputImage);
            //Verify that desired width provided is an integer.
            try {
                desiredWidth = Integer.parseInt(args[2]);
            }
            catch(NumberFormatException e) {
                System.out.println("Input Error: Desired width must be an int value.");
                System.exit(0); 
            }
            if(desiredWidth>inputImage.getWidth()){
                System.out.println("Input Error: Desired width must be smaller than original width.");
                System.exit(0); 
            }
            else if(desiredWidth<=0){
                System.out.println("Input Error: Desired width must be a positive integer.");
                System.exit(0); 
            }  
        }              
    }

    public static void testRun(){
        MyImageManipulator program = new MyImageManipulator();
        program.cleverWidthReduction(imageObject,desiredWidth);
    }

    public static void saveResult() throws IOException, Exception{
        int resultWidth = imageObject.getImage().getWidth();
        if(resultWidth!=desiredWidth){
            System.out.println("Program 3 failed to reduce width to "+String.valueOf(desiredWidth)+". Width of resulting image is: "+String.valueOf(resultWidth));
            System.exit(0);
        }
        //
        File finalImageFile = new File(outputFileName); 
        boolean finalImageDrawing = ImageIO.write(imageObject.getImage(), fileType, finalImageFile); 
        if(!finalImageDrawing){
            System.out.println("Drawing final image failed.");
        } 
        System.out.println("Result has successfuly been saved at "+outputFileName);
    }
}