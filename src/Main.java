public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Terminal.clearScreen();
//        System.out.println(Terminal.getInt());
//        System.out.println(Terminal.getColour());

        Game game = new Game();

        game.makeMove(0, 0);
        game.makeMove(1, 0);
        game.makeMove(0, 1);
        game.makeMove(1, 1);
        game.makeMove(0, 2);
        game.makeMove(1, 2);
        game.makeMove(0, 3);
        game.makeMove(1, 3);
        System.out.println(game.checkForWin());
        game.makeMove(0, 4);
        System.out.println(game.checkForWin());
        game.makeMove(1, 4);
        System.out.println(game.checkForWin());
//        System.out.println(game.toString());
    }
}