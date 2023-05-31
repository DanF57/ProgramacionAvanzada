import java.util.concurrent.TimeUnit;

public class CashRegister {
    private final String chashRegisterId;
    public final long ATTEND_AVG_TIME = 7;

    public CashRegister(String chashRegisterId) {
        this.chashRegisterId = chashRegisterId;
    }

    public void attend(Client currentClient) {
        System.out.printf("Caja: %s - atendiendo al cliente: %s\n", this.chashRegisterId, currentClient.name());
        var seconds2Wait = currentClient.nroProducts() * ATTEND_AVG_TIME;
        try {
            TimeUnit.SECONDS.sleep(seconds2Wait);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        System.out.printf("Caja: %s - cliente %s atendido\n", this.chashRegisterId, currentClient.name());
    }

    public String getChashRegisterId() {
        return chashRegisterId;
    }
}