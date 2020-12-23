import java.util.*;

/*
* Something along these lines
* Maybe implement log at the end idk
*/

public class CLI {

    private Scanner scanner;
    private int option;
    private int delay;
    private String data;

    CLI() {
        Scanner scanner = new Scanner(System.in);
        this.scanner = scanner;
    }

    void printInitMenu(){
        System.out.println("************************MENU************************");
        System.out.println("Please select your operation:");
        System.out.println("(1) Send normal message");
        System.out.println("(2) Send delayed message");
        System.out.println("(3) Close connection and Exit");
        System.out.println("****************************************************");

        option = scanner.nextInt();

        if(option == 1) {
            printNormalMenu();
        }

        else if(option == 2) {
            printDelayMenu();
        }

        else if(option == 3) {
            //close communication & quit
        }

        else {
            System.out.println("Option unavailable. Please try again");
            printInitMenu();
        }

    }

    void printNormalMenu() {
        System.out.println("Select message: ");
        data = scanner.nextLine();
    }

    void printDelayMenu() {
        System.out.print("Select delay: ");
        delay = scanner.nextInt();
        System.out.println("Select message: ");
        data = scanner.nextLine();

        //do something
    }

    void printResultScreen() throws InterruptedException {
        System.out.println("************************RESULTS*********************");
        System.out.println("Status: ");
        System.out.println("Time: ");
        System.out.println("Data Received: ");
        System.out.println("Number of retransmissions: ");
        System.out.println("****************************************************");

        Thread.sleep(5000);
    }
}
