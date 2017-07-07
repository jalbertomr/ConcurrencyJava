/**
 * Created by Bext on 05/07/2017.
 */
public class SleepMessage {

    public static void main(String... args) throws InterruptedException {
        String[] importantInfo = {
                "Pepe come papas",
                "Papas viven bien",
                "Bienes me caen Siempre",
                "Siempre soy feliz"
        };

        for (int i = 0; i < importantInfo.length; i++) {
            Thread.sleep(3000);
            System.out.println(importantInfo[i]);
        }
    }
}
