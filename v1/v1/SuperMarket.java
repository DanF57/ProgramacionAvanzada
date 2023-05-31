public class SuperMarket {
    public static void main( String[] args ) {
        CashRegister cashRegister1 = new CashRegister("CR-1");
        CashRegister cashRegister2 = new CashRegister("CR-2");

        Client clientA = new Client("Client-A", 4);
        Client clientB = new Client("Client-B", 5);

        long starTime = System.nanoTime();

        cashRegister1.attend(clientB);
        cashRegister2.attend(clientA);

        long endTime = System.nanoTime();
        long totalTime = endTime - starTime;

        System.out.printf("Tiempo total: %f minutos\n", totalTime * Math.pow(10, -9) / 60);
    }
}
