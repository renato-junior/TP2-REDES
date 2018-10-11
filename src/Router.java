public class Router {
    
    public static void main(String[] args) {
        System.out.println("This is a test.");
        InnerClass ic = new InnerClass();
        ic.method();
    }

}

class InnerClass {

    public void method() {
        System.out.println("I'm here!");
    }

}