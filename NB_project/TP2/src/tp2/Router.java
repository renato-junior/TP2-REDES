package tp2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Router {

    public static void main(String[] args) {
        RouterRIP mainRouter = new RouterRIP(args[0], Integer.parseInt(args[1]));
        InputHandler input = new InputHandler();

        mainRouter.start();
        input.start();
        while (true) {
            String inputLine = input.getNextCommand();
            if (inputLine == null) {
                try {
                    Thread.sleep(10); // Workaround para possiblitar leitura dos comandos
                } catch (InterruptedException ex) {
                }
                continue;
            }
            try {
                String command = inputLine.split(" ")[0];
                String firstArg;
                String secondArg;
                if (command.equals("add")) {
                    firstArg = inputLine.split(" ")[1];
                    secondArg = inputLine.split(" ")[2];
                    mainRouter.addNewRoute(firstArg, firstArg, Integer.parseInt(secondArg));
                } else if (command.equals("del")) {
                    firstArg = inputLine.split(" ")[1];
                    mainRouter.deleteRoute(firstArg);
                } else if (command.equals("send")) {
                    firstArg = inputLine.split(" ")[1];
                    secondArg = inputLine.split(" ")[2];
                    mainRouter.sendDataMessage(firstArg, secondArg);
                } else if (command.equals("trace")) {
                    firstArg = inputLine.split(" ")[1];
                    mainRouter.sendTraceMessage(firstArg);
                } else if (command.equals("print")) {
                    mainRouter.printTable();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Erro de sintaxe no comando: " + inputLine);
            }
        }
    }
}

class InputHandler extends Thread {

    ArrayList<String> inputs;

    InputHandler() {
        inputs = new ArrayList<String>();
    }

    public void run() {
        while (true) {
            Scanner inpt = new Scanner(System.in);
            inputs.add(inpt.nextLine());
        }
    }

    public String getNextCommand() {
        if (this.inputs.isEmpty()) {
            return null;
        } else {
            return this.inputs.remove(0);
        }
    }

}
