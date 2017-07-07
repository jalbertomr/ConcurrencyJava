/**
 * Created by Bext on 06/07/2017.
 */
public class Drop {
    //mensaje enviado del productor al consumidor
    private String message;
    // true: consumidor debe esperar a que productor envie mensaje
    // flase: productor deve esperar a que consumidor tome el mensaje
    private boolean consumidorEspera = true;

    public synchronized String take() {
        //Espera hasta que el mensaje este disponible
        System.out.format("Drop.take: [%s]%n", message);
        while(consumidorEspera) {
            try {
                wait();
            } catch (InterruptedException e) { e.printStackTrace();}
        };
        consumidorEspera = true;     //cambia el estado
        notifyAll();      //notifica al productor que el estado ha cambiado
        return message;
    }

    public synchronized void put(String message) {
        //Espera hasta que el mensaje se haya tomado
        System.out.format("Drop.put: [%s]%n", message);
        while ( !consumidorEspera){
            try {
                wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        consumidorEspera = false;      //cambia el estado
        this.message = message;
        notifyAll();        //nofica al consumidor que el estado ha cambiado
    }
}
