package ec.edu.utpl.computacion.pa;

import ec.edu.utpl.clasica.computacion.pa.s7.pe.URIData;
import ec.edu.utpl.clasica.computacion.pa.s7.pe.WebClient;

import java.io.IOException;

public class ControllerUri implements Runnable{

    private String uri;

    private URIData uridata;

    public ControllerUri(String uri){
        this.uri = uri;
    }

    @Override
    public void run() {
        try {
            var resUri = WebClient.getStatus(uri);
            System.out.println(resUri);
            uridata = resUri;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public URIData getUridata() {
        return uridata;
    }

    public void setUridata(URIData uridata) {
        this.uridata = uridata;
    }
}
