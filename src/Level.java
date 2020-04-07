import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class Level {
	private char tabLevel[][] = new char[25][25]; //Where the level is loaded
	
	public void initLevel(int levelNum) {
		File file = new File("data\\levels\\level-" + levelNum + ".txt");
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
	
	//Two functions to access to tabLevel to avoid confusion between XY axes and ij indexes
	public void setLevelCaseIJ(char c, int i, int j) {
		tabLevel[i][j] = c;
	}
	
	public char getLevelCaseIJ(int i, int j) {
		return tabLevel[i][j];
	}
	
	public void setLevelCaseXY(char c, int X, int Y) {
		tabLevel[Y][X] = c;
	}
	
	public char getLevelCaseXY(int X, int Y) {
		return tabLevel[Y][X];
	}
	
	//For debug only, show the level as it is loaded in the table
	public void showTable() {
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 25; j++) {
				System.out.print(tabLevel[i][j]);
			}
			System.out.print("\r");
		}
	}
}
