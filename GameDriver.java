public class GameDriver {
	public static void main(String[] args) {

		GameBuilder gameBuilder = new GameBuilder();

		GameInitiator.singleCreate(gameBuilder);					//membuat dunia game

		GameInitiator gameInitiator = GameInitiator.getInstance();	//mengambil instance GameInitiator
		gameInitiator.startGame();									//memulai game
		FramePrototype2 fp = new FramePrototype2(GameInitiator.getGame());
		GameCondition.MonitorGame();								//memastikan game berjalan sesuai dengan aturan
	}


}
