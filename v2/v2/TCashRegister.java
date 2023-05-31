import java.util.concurrent.TimeUnit;

public class TCashRegister implements Runnable {
    private Client currentClient;
    private final String chashRegisterId;
    public final long ATTEND_AVG_TIME = 7;

    public TCashRegister(String chashRegisterId) {
        this.chashRegisterId = chashRegisterId;
    }

    public void run() {
        System.out.printf("Caja: %s - atendiendo al cliente: %s\n", this.chashRegisterId, currentClient.name());
        var seconds2Wait = currentClient.nroProducts() * ATTEND_AVG_TIME;
        try {
            TimeUnit.SECONDS.sleep(seconds2Wait);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        System.out.printf("Caja: %s - cliente %s atendido\n", this.chashRegisterId, currentClient.name());
    }

    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;
    }

    public String getChashRegisterId() {
        return chashRegisterId;
    }
}