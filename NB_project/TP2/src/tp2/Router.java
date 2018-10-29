package tp2;
import java.util.ArrayList;
import java.util.Scanner;

public class Router{
        
    public static void main(String[] args) {
        RouterRIP mainRouter = new RouterRIP(args[0],Integer.parseInt(args[1]));
        Timmer timmer = new Timmer();
        InputHandler input = new InputHandler();
        
        timmer.start();
        mainRouter.start();
        input.start();
        while(true){
            if(timmer.timePassed >= 4*mainRouter.getPeriod()){
                mainRouter.sendUpdateMessages();
                timmer.reset();
            }
            
            String inputLine = input.getNextCommand();
            if(inputLine == null)
                continue;
            String command = inputLine.split(" ")[0];
            String firstArg = inputLine.split(" ")[1];
            String secondArg;
            if(command.equals("add")){
                secondArg = inputLine.split(" ")[2];
                mainRouter.addNewRoute(firstArg, firstArg, Integer.parseInt(secondArg));
            }
            else if(command.equals("del")){
                mainRouter.deleteRoute(firstArg);
            }
            else if(command.equals("send")){
                secondArg = inputLine.split(" ")[2];
                mainRouter.sendDataMessage(firstArg, secondArg);
            }
            else if(command.equals("trace")){
                mainRouter.sendTraceMessage(firstArg);
            }
            else if(command.equals("print")){
                mainRouter.printTable();
            }
        }
    }
}

class InputHandler extends Thread{
    ArrayList<String> inputs;    
    InputHandler(){
        inputs = new ArrayList<String>();
    }
    
    public void run(){
        while(true){
            Scanner inpt = new Scanner(System.in);
            inputs.add(inpt.nextLine());
        }
    }
    
    public String getNextCommand(){
        if(this.inputs.size() == 0)
            return null;
        else{
            return this.inputs.remove(0);
        }
    }

}

class Timmer extends Thread{
    float timePassed;
    float startTime;
        
    public Timmer(){
        this.startTime = System.nanoTime();
        this.timePassed = 0;
    }
    
    public void reset(){
        this.timePassed = 0;
        startTime = System.nanoTime();
    }
    
    @Override
    public void run(){
        while(true)
            timePassed = System.nanoTime() - startTime;
    }
}