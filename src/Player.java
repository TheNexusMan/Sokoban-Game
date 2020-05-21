
//Player class is used to store a player
public class Player {
	private String pseudo;
	private int scores [] = new int[Game.nbLevels]; //All the scores of the player
	private int nextLevelToPass; //The id of the last level the player unlocked
	private int characterChoice; //The character to display for this player
	private int sceneryChoice; //The character to display for this player

	Player() {
		this.nextLevelToPass = 0;
		this.characterChoice = 0;
		this.sceneryChoice = 0;
		scoresTabInitialization();
	}
	
	Player(String pseudo) {
		this.pseudo = pseudo;
		this.nextLevelToPass = 0;
		this.characterChoice = 0;
		this.sceneryChoice = 0;
		scoresTabInitialization();
	}
	
	Player(String pseudo, int [] scoreTab, int nextLevelToPass, int characterChoice, int sceneryChoice) {
		this.pseudo = pseudo;
		scoresTabInitialization();
		
		for(int i = 0; i < scoreTab.length; i++) {
			scores[i] = scoreTab[i];
		}
		
		this.nextLevelToPass = nextLevelToPass;
		this.characterChoice = characterChoice;
		this.sceneryChoice = sceneryChoice;
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
	
	public int getCharacterChoice() {
		return characterChoice;
	}

	public void setCharacterChoice(int characterChoice) {
		this.characterChoice = characterChoice;
	}

	public int getSceneryChoice() {
		return sceneryChoice;
	}

	public void setSceneryChoice(int sceneryChoice) {
		this.sceneryChoice = sceneryChoice;
	}

	//Function to initialize all scores to -1
	private void scoresTabInitialization() {
		for(int i = 0; i < Game.nbLevels; i++) {
			scores[i] = -1;
		}
	}
	
}
