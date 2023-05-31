package ec.edu.utpl.computacion.pa;
import ec.edu.utpl.clasica.computacion.pa.s7.pe.URIData;
import ec.edu.utpl.clasica.computacion.pa.s7.pe.WebClient;
import java.util.concurrent.Callable;

public class ProcessURI implements Callable<URIData> {
    private String uri;
    public ProcessURI(String uri) {
        this.uri = uri;
    }
    @Override
    public URIData call() throws Exception {
        return WebClient.getStatus(uri);
    }
}