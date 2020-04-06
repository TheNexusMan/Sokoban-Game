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
	
	public void setLevelCase(char c, int i, int j) {
		tabLevel[i][j] = c;
	}
	
	public char getLevelCase(int i, int j) {
		return tabLevel[i][j];
	}
	
	//For debug only
	public void showTable() {
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 25; j++) {
				System.out.print(tabLevel[i][j]);
			}
			System.out.print("\r");
		}
	}
}
