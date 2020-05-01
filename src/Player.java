
public class Player {
	private String pseudo;
	private int scores [] = new int[Game.nbLevel];
	private int nextLevelToPass;

	Player() {
		
	}
	
	Player(String pseudo) {
		this.pseudo = pseudo;
		this.nextLevelToPass = 0;
		allScoreToMinusOne();
	}
	
	Player(String pseudo, int [] scoreTab, int nextLevelToPass) {
		this.pseudo = pseudo;
		allScoreToMinusOne();
		
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
	
	private void allScoreToMinusOne() {
		for(int i = 0; i < Game.nbLevel; i++) {
			scores[i] = -1;
		}
	}
	
}
