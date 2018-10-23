package tp2;
import java.util.ArrayList;
import java.util.Scanner;

public class Router{
        
    public static void main(String[] args) {
        while(true){
            RouterRIP mainRouter = new RouterRIP(args[0],Integer.parseInt(args[1]));
            InputDataReader input = new InputDataReader();
            Timmer timmer = new Timmer();
            input.start();
            timmer.start();
            if(input.command.equals("quit"))
                break;
            else if(input.command.equals("add")){
                mainRouter.addNewRoute(input.firstArg,input.firstArg,Integer.parseInt(input.secondArg));
            }else if(input.command.equals("del")){
                mainRouter.deleteRoute(input.firstArg);
            }else if(input.command.equals("trace")){
                mainRouter.sendTraceMessage(input.firstArg);
            }            
            
            if(timmer.timePassed >= mainRouter.getPeriod()){
                mainRouter.sendUpdateMessages();
                timmer.reset();
            }
        }
    }
}


class InputDataReader extends Thread{
    String command;
    String firstArg;
    String secondArg;
    int k;
        
    public InputDataReader(){
        this.command = "default-value";
        this.firstArg = "default-value";
        this.secondArg = "default-value";
        this.k = 0;
    }
    
    @Override
    public void run(){
        while(true){
            Scanner input = new Scanner(System.in);
            String inputLine = input.nextLine();
            command = inputLine.split(" ")[0];
            if(command.equals("stop")){
                break;
            }
            firstArg = inputLine.split(" ")[1];
            if(command.equals("add")){
                secondArg = inputLine.split(" ")[2];
            }
            k++;
            System.out.println(k);
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
