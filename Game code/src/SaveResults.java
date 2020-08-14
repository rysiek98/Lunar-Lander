import java.io.BufferedWriter;
import java.io.FileWriter;

import static java.nio.charset.StandardCharsets.UTF_8;

/** Klasa zapisuje wynik gry do pliku*/

public class SaveResults {

    /**Metoda zapisująca wynik gracza do pilku txt
     * @param  name nick gracza
     * @param points zdobyte punkty
     * @param diffLevel poziom trudności
     */
    public static void saveResult(String name, int points, String diffLevel) throws Exception {
        FileWriter file = new FileWriter(LoadingLevel.readPathsTo("ResultsTable", LoadingLevel.pathToClientConfigFile), UTF_8, true);
        BufferedWriter out = new BufferedWriter(file);
        out.write("Nick gracza: "+name+" uzyskane punkty: "+Integer.toString(points)+" poziom trudności: "+diffLevel+"\r\n");
        out.close();
    }
}
