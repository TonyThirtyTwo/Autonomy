package autonomy;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatUtilities {
	
	private String txtFilePath;
	
    public static String[] readLines(String filename) throws IOException {
    	//create objects needed to read lines sequentially
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        List<String> lines = new ArrayList<String>(); //lines arraylist which will be returned as an array
        String line = null;
         
        while ((line = bufferedReader.readLine()) != null) //add to our lines array list as long as our line isnt empty 
        {
            lines.add(line);
        }
         
        bufferedReader.close();
         
        return lines.toArray(new String[lines.size()]); //return our lines arraylist as an array
    }   
	
	public static String generatePhrase(int numberOfPhrases, String[] phrases) {
		//String[] words = {"...", "YO", "ALPHA BIRD"};
		String sentence = "";
		int phrasePick = 0;
		
		if(numberOfPhrases == 1) {
			phrasePick = (int) (Math.random() * phrases.length);
			sentence += phrases[phrasePick];
		}else {
			//generate phrases with a space at the end EXCEPT for the last one
			for(int i = 0; i < numberOfPhrases - 1; i++) {
				phrasePick = (int) (Math.random() * phrases.length);
				sentence += phrases[phrasePick] + " ";
			}
			phrasePick = (int) (Math.random() * phrases.length);
			sentence += phrases[phrasePick];
		}
		
		return sentence;
	}
	
	public static void sendMessage() {
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e3) {
			e3.printStackTrace();
		}
	}

}
