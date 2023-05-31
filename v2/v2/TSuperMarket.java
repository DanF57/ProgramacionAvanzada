

public class TSuperMarket {
  public static void main(String[] args) throws InterruptedException {
      TCashRegister cashRegister1 = new TCashRegister("CR-1");
      TCashRegister cashRegister2 = new TCashRegister("CR-2");

      Client clientA = new Client("Client-A", 4);
      Client clientB = new Client("Client-B", 5);

      cashRegister1.setCurrentClient(clientB);
      cashRegister2.setCurrentClient(clientA);

      Thread thCR1 = new Thread(cashRegister1);
      Thread thCR2 = new Thread(cashRegister2);

      long starTime = System.nanoTime();

      thCR1.start();
      thCR2.start();

      thCR1.join();
      thCR2.join();

      long endTime = System.nanoTime();
      long totalTime = endTime - starTime;

      System.out.printf("Tiempo total: %f minutos\n", totalTime * Math.pow(10, -9) / 60);
  }
}