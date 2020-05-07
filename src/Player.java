
//Player class is used to store a player
public class Player {
	private String pseudo;
	private int scores [] = new int[Game.nbLevels]; //All the scores of the player
	private int nextLevelToPass; //The id of the last level the player unlocked

	Player() {
		scoresTabInitialization();
	}
	
	Player(String pseudo) {
		this.pseudo = pseudo;
		this.nextLevelToPass = 0;
		scoresTabInitialization();
	}
	
	Player(String pseudo, int [] scoreTab, int nextLevelToPass) {
		this.pseudo = pseudo;
		scoresTabInitialization();
		
		for(int i = 0; i < scoreTab.length; i++) {
			scores[i] = scoreTab[i];
		}
		
		this.nextLevelToPass = nextLevelToPass;
	}
	
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getLevelScore(int level) {
		return scores[level];
	}
	
	public void setLevelScore(int level, int score) {
		scores[level] = score;
	}

	public int getNextLevelToPass() {
		return nextLevelToPass;
	}

	public void setNextLevelToPass(int nextLevelToPass) {
		this.nextLevelToPass = nextLevelToPass;
	}
	
	//Function to initialize all scores to -1
	private void scoresTabInitialization() {
		for(int i = 0; i < Game.nbLevels; i++) {
			scores[i] = -1;
		}
	}
	
}
