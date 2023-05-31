package ec.edu.utpl.computacion.pa;

import ec.edu.utpl.clasica.computacion.pa.s7.pe.URIData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws IOException, InterruptedException {
        String uri1 = "https://www.assocalciatori.it/…";
        String uri2 = "https://dlcdn.apache.org/zeppelin/zeppelin-0.10.1/…";
        String uri3 = "https://medium.com/swlh/think-like-an-…";

        ControllerUri t1 = new ControllerUri(uri1);
        ControllerUri t2 = new ControllerUri(uri2);
        ControllerUri t3 = new ControllerUri(uri3);

        List<ControllerUri> gets = new ArrayList();

        Thread thread1 = new Thread(t1);
        Thread thread2 = new Thread(t2);
        Thread thread3 = new Thread(t3);

        gets.add(t1);
        gets.add(t2);
        gets.add(t3);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        URIData data1 = t1.getUridata();
        URIData data2 = t2.getUridata();
        URIData data3 = t3.getUridata();

        System.out.println("---------------------");
        System.out.println(data1);
        System.out.println(data2);
        System.out.println(data3);


    }
}
