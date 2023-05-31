package ec.edu.utpl.computacion.pa;

import ec.edu.utpl.clasica.computacion.pa.s7.pe.URIData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
public class Demo2 {
    public static void main(String[] args) {
        int nroCores = Runtime.getRuntime().availableProcessors();
        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
            Future<URIData> uriDataFuture =
                    executorService.submit(new ProcessURI("https://dlcdn.apache…"));
            try {
                URIData data = uriDataFuture.get();
                System.out.println(data);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        var urls = List.of("https://dlcdn.apache.org/zeppelin/zeppelin-0.10.1/zeppelin-0.10.1-bin-netinst.tgz",
                "https://freeling-user-manual.readthedocs.io/en/v4.2/processing-classes/",
                "https://freeling-user-manual.readthedocs.io/en/v4.2/modules/tagger/",
                "https://freeling-tutorial.readthedocs.io/en/latest/example01/",
                "https://alta.law/partners/",
                "https://www.amazon.com/Debt-Collection-Answers-Protect-Rights-ebook/dp/B01AR8H2U0",
                "https://d.tube/!/v/billionairepal/l044yenn",
                "https://yourbestboss.com/",
                "https://www.investors.com/news/management/leaders-and-success/warren-buffetts-net-worth-driven-ironclad-rules/",
                "https://medium.com/swlh/think-like-an-economist-9dda35681e51?aduc=knxn5Qx1561843084525",
                "https://www.dta.fau.de/",
                "https://dlcdn.apache.org/zeppelin/zeppelin-0.10.1/zeppelin-0.10.1-bin-netinst.tgz");
    }
}