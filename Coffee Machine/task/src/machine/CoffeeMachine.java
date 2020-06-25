 package machine;


 import java.util.Scanner;

 public class CoffeeMachine {
     static boolean haveSource = true;
     static  boolean onOf = true;

     static class Machine {
         int money = 550;
         int tankWater = 400;
         int tankMilk = 540;
         int tankBeans = 120;
         int tankCups = 9;

         public void setMoney(int money) {
             this.money = money;
         }

         public void setTankWater(int tankWater) {
             this.tankWater = tankWater;
         }

         public void setTankMilk(int tankMilk) {
             this.tankMilk = tankMilk;
         }

         public void setTankBeans(int tankBeans) {
             this.tankBeans = tankBeans;
         }

         public void setTankCups(int tankCups) {
             this.tankCups = tankCups;
         }
     }

     public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
         Machine machine = new Machine();
         while (true) {
             if (!onOf) {
                 break;
             }
             System.out.print("Write action (buy, fill, take, remaining, exit): \n>");
             String action = scanner.next();
             System.out.println();
             switchStates(States.findByAction(action), machine);

         }
     }
     enum States {
         // buy, fill, take, remaining, exit
         BUY("buy"), FILL("fill"), TAKE("take"), REMAINING("remaining"), EXIT("exit");
         String s;
         States(String s) {
             this.s = s;
         }
         public String getS() {
             return s;
         }
         public static States findByAction(String action) {
             for (States value : values()) {
                 if (action.equals(value.s)) {
                     return value;
                 }
             }
             return null;
         }
     }
     enum Coffee {
         ESPRESSO("1", 250, 0 , 16, 4),
         LATTE("2", 350, 75, 20, 7),
         CAPPUCCINO("3", 200, 100, 12, 6);
         String name;
         int waterPerCup;
         int milkPerCup;
         int coffeePerCup;
         int coastEspresso;

         Coffee(String name,int waterPerCup, int milkPerCup, int coffeePerCup, int coastEspresso) {
             this.name = name;
             this.waterPerCup = waterPerCup;
             this.milkPerCup = milkPerCup;
             this.coffeePerCup = coffeePerCup;
             this.coastEspresso = coastEspresso;
         }

         public int getWaterPerCup() {
             return waterPerCup;
         }

         public int getMilkPerCup() {
             return milkPerCup;
         }

         public int getCoffeePerCup() {
             return coffeePerCup;
         }

         public int getCoastEspresso() {
             return coastEspresso;
         }
         public static Coffee findByCoffee(String name) {
             for (Coffee value : values()) {
                 if (name.equals(value.name)) {
                     return value;
                 }
             }
             return null;
         }
     }
     public static void checkError(Machine machine, Coffee coffee) {

         if (machine.tankWater < coffee.waterPerCup){
             System.out.println("Sorry, not enough water!\n");
             haveSource = false;

         } else if (machine.tankMilk < coffee.milkPerCup){
             System.out.println("Sorry, not enough milk!\n");
             haveSource = false;

         } else if (machine.tankBeans < coffee.coffeePerCup) {
             System.out.println("Sorry, not enough Coffee Beans!\n");
             haveSource = false;

         } else if (machine.tankCups < 1) {
             System.out.println("Sorry, not enough cups!\n");
             haveSource = false;
         }
     }

    public static CoffeeMachine.Coffee findByCoffee(String name) {
        return Coffee.findByCoffee(name);
    }

     public static void switchStates (States states, Machine machine) {
         Scanner scanner = new Scanner(System.in);
         haveSource = true;
         // write your implementation here
         if (states.equals(States.BUY)) {
             System.out.print("What do you want to buy? 1 - espresso," +
                     " 2 - latte, 3 - cappuccino, back - to main menu: \n>");
             String coffee = scanner.next();
             switch (coffee) {
                 case "back":
                     break;
                 default:
                     checkError(machine, findByCoffee(coffee));
                     if (!haveSource) {
                         break;
                     }
                     System.out.println("I have enough resources, making you a coffee!\n");
                     machine.setTankWater(machine.tankWater - findByCoffee(coffee).waterPerCup);
                     machine.setTankMilk(machine.tankMilk - findByCoffee(coffee).milkPerCup);
                     machine.setTankBeans(machine.tankBeans - findByCoffee(coffee).coffeePerCup);
                     machine.setTankCups(machine.tankCups - 1);
                     machine.setMoney(machine.money + findByCoffee(coffee).coastEspresso);
             }

         } else if (states.equals(States.FILL)) {
             System.out.print("Write how many ml of water do you want to add: \n>");
             int addWater = scanner.nextInt();
             machine.setTankWater(machine.tankWater + addWater);
             System.out.print("Write how many ml of milk do you want to add: \n>");
             int addMilk = scanner.nextInt();
             machine.setTankMilk(machine.tankMilk + addMilk);
             System.out.print("Write how many grams of coffee beans do you want to add: \n>");
             int addBeans = scanner.nextInt();
             machine.setTankBeans(machine.tankBeans + addBeans);
             System.out.print("Write how many disposable cups of coffee do you want to add: \n>");
             int addCups = scanner.nextInt();
             machine.setTankCups(machine.tankCups + addCups);
             System.out.println();

         } else if (states.equals(States.TAKE)) {
             System.out.println("I gave you $"+machine.money+"\n");
             machine.setMoney(0);
         } else if (states.equals(States.REMAINING)) {
             checkSource(machine.tankWater,machine.tankMilk,machine.tankBeans,machine.tankCups,machine.money);
         } else if (states.equals(States.EXIT)) {
             onOf = false;
         }
     }

     public static void checkSource ( int tankWater, int tankMilk, int tankBeans, int tankCups, int money){
             System.out.println("The coffee machine has:");
             System.out.println(tankWater + " of water");
             System.out.println(tankMilk + " of milk");
             System.out.println(tankBeans + " of coffee beans");
             System.out.println(tankCups + " of disposable cups");
             System.out.println("$" + money + " of money");
             System.out.println();

         }
 }