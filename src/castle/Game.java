package castle;


import java.util.HashMap;
import java.util.Scanner;

public class Game {
    private Room currentRoom;
    private HashMap<String, Handler> handlers = new HashMap<>();

    public class HandlerGo extends Handler {

        @Override
        public void doCmd(String word) {
            goRoom(word);
        }
    }

    public class HandlerHelp extends Handler{

        @Override
        public void doCmd(String word) {
            System.out.print("迷路了吗？你可以做的命令有：go bye help");
            System.out.println("如：\tgo east");
        }
    }
    public class HandlerBye extends Handler{

        @Override
        public boolean isBye() {
            return true;
        }
    }

    public Game() 
    {

        handlers.put("go", new HandlerGo());
        handlers.put("help", new HandlerHelp());
        handlers.put("bye", new HandlerBye());
        createRooms();
    }



    private void createRooms()
    {
        Room outside, lobby, pub, study, bedroom;
      
        //	制造房间
        outside = new Room("城堡外");
        lobby = new Room("大堂");
        pub = new Room("小酒吧");
        study = new Room("书房");
        bedroom = new Room("卧室");
        
        //	初始化房间的出口
        outside.setExit("east", lobby);
        outside.setExit("south", study);
        outside.setExit("west", pub);
        lobby.setExit("west", outside);
        study.setExit("north", outside);
        study.setExit("east", study);
        bedroom.setExit("west", study);
        pub.setExit("east", outside);

        currentRoom = outside;  //	从城堡门外开始
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("欢迎来到城堡！");
        System.out.println("这是一个超级无聊的游戏。");
        System.out.println("如果需要帮助，请输入 'help' 。");
        System.out.println();
        showPrompt(currentRoom);
    }

    // 以下为用户命令

    private void showPrompt(Room currentRoom){
        System.out.println("现在你在" + currentRoom);
        System.out.print("出口有：");
        System.out.println(currentRoom.getExitDesc());
    }

    public void goRoom(String direction)
    {
        Room nextRoom = currentRoom.getExit(direction);
        if(nextRoom == null){
            System.out.println("那里没门！");
        }
        else {
            currentRoom = nextRoom;
            showPrompt(currentRoom);
        }
    }

    public void play(){
        Scanner in = new Scanner(System.in);
        while ( true ){
            String line = in.nextLine();
            String[] words = line.split(" ");
            Handler handler = handlers.get(words[0]);
            String value = "";
            if( words.length > 1){
                value = words[1];
            }
            if( handler != null){
                handler.doCmd(value);
                if(handler.isBye()){
                    break;
                }
            }
        }

    }

    public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Game game = new Game();
		game.printWelcome();

		game.play();
		in.close();
	}


}
