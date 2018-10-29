package tp2;
import java.util.ArrayList;
import java.util.Scanner;

public class Router{
        
    public static void main(String[] args) {
        RouterRIP mainRouter = new RouterRIP(args[0],Integer.parseInt(args[1]));
        Timmer timmer = new Timmer();
        
        timmer.start();
        mainRouter.start();
        while(true){
            if(timmer.timePassed >= 4*mainRouter.getPeriod()){
                mainRouter.sendUpdateMessages();
                timmer.reset();
            }
            Scanner inpt = new Scanner(System.in);
            String inputLine = inpt.nextLine();            
            String command = inputLine.split(" ")[0];
            String firstArg = inputLine.split(" ")[1];
            String secondArg;
            if(command.equals("add")){
                secondArg = inputLine.split(" ")[2];
                mainRouter.addNewRoute(firstArg, firstArg, Integer.parseInt(secondArg));
            }
            else if(command.equals("del")){
                mainRouter.deleteRoute(firstArg);
                mainRouter.sendUpdateMessages();
            }
            else if(command.equals("send")){
                secondArg = inputLine.split(" ")[2];
                mainRouter.sendDataMessage(firstArg, secondArg);
            }
            else if(command.equals("trace")){
                mainRouter.sendTraceMessage(firstArg);
            }
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