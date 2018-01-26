import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.KeyStroke;

/**
 * class that fills out a form given name of txt file and waits 10 seconds before beginning
 * @author DanielLuo
 * 
 * Instructions:
 * 1. Run the program
 * 2. Type name of file; make sure file is in correct project folder
 * 3. Hit return, then go to Google Form or other form and click on first input box
 * 4. After 10 secs, FormFill will enter all information into the areas
 * 
 * Format of txt file:
 * For each entry, just add a comma and hit "return" button after each entry is complete (only hit it once)
 * If you want to leave an entry blank make sure to leave a comma then skip a line
 *
 */
public class FormFill {
	public static void main(String[] args) throws AWTException{
		Robot bot = new Robot();
		System.out.println("Please type in the name of the file:");
		Scanner s = new Scanner(System.in);
		
		String file_name = s.nextLine();
		
		bot.delay(5000);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file_name));
			
			StringBuilder entry = new StringBuilder();
			
			while(br.ready()) {
				char curr = (char) br.read();
				
				//if character is ',' followed by '\n' then entry ends, hit tab
				if(curr == ',') {
					char next = (char) br.read();
					//write entry then go to next
					if(next == '\n') {
						bot.delay(40);
						//write the entry
						StringSelection stringSelection = new StringSelection(entry.toString());
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						clipboard.setContents(stringSelection, stringSelection);

						bot.keyPress(KeyEvent.VK_META);
						bot.keyPress(KeyEvent.VK_V);
						bot.keyRelease(KeyEvent.VK_V);
						bot.keyRelease(KeyEvent.VK_META);
						
						bot.delay(40);
						//hit tab and reset entry content
						bot.keyPress(KeyEvent.VK_TAB);
						bot.keyRelease(KeyEvent.VK_TAB);
						entry = new StringBuilder();
					}
					//add to entry
					else {
						entry.append(curr);
						entry.append(next);
					}
				}
				
				else {
					entry.append(curr);
				}
			}
			
			while(br.ready()) {
				char curr = (char)br.read();
				bot.keyPress((int)curr);
				System.out.print(curr);
			}
			
			br.close();
			s.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}