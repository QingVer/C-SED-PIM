import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

public class Note{
	
	private String titleText;
	private String bodyText;
	
	private File noteFile;
	
	public Note(File noteFile){
		Scanner noteReader = new Scanner(noteFile);
		
		titleText = noteReader.readLine();
		
		while (noteReader.hasNext){
			bodyText = bodyText + "\n" + noteReader.readLine();
		}
		
		noteReader.close();
	}
	
	public Note(String title, String body){
		titleText = title;
		bodyText = body;
	}
	
	public void setBodyText(String body){
		bodyText = body;
	}
	
	public String getBodyText(){
		return bodyText;
	}
	
	public void setTitle(String title){
		titleText = title;
	}
	
	public String getTitleText(){
		return titleText;
	}
	
}