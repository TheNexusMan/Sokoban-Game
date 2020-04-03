import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class Level {
	public char tabLevel[][] = new char[25][25];
	
	public void initLevel() {
		File file = new File("data\\levels\\level-1.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		int i = 0;
		
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			
			for(int j = 0; j < line.length(); j++)
			{
				tabLevel[i][j] = line.charAt(j);
			}
			
			i++;
			
		}
		
		sc.close();
	}
}
