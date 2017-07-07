/**
 * Created by Bext on 06/07/2017.
 */
public class TwoThreads {
    static class Entidad {
        private final String nombre;

        public Entidad(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }

        public void llamaA(Entidad elOtro) {
            System.out.format("%s: %s me ha llamado!%n", this.nombre, elOtro.getNombre());
            elOtro.responde(this);
        }

        public void responde(Entidad elOtro) {
            System.out.format("%s: %s me ha respondido!%n", this.nombre, elOtro.getNombre());
        }
    }

    public static void main(String... args) {
        final Entidad AAA = new Entidad("AAA");
        final Entidad BBB = new Entidad("BBB");

        new Thread( () -> AAA.llamaA(BBB)).start();
        new Thread( () -> BBB.llamaA(AAA)).start();
    }
}
