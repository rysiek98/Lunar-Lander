import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;

/** Klasa służąca do wczytywania danych o poziomie i zapisywania wyników */
public class LoadingLevel {

	private final ArrayList<Integer> elevation = new ArrayList<>();
	private final ArrayList<Integer> position = new ArrayList<>();
	private final ArrayList<Integer> lands = new ArrayList<>();
	private final int gravity;
	private final int meteorites;
	private final int vYMax;
	private final double aPlanet;
	private final int[] planetColorRGB =  new int[3];
	private final int[] landerStartPosition =  new int[2];

	/** Konstruktor klasy
	 * @param file plik z którego mają być wczytane dane poziomu
	 */
	public LoadingLevel(File file) throws Exception{

		BufferedReader br = new BufferedReader(new FileReader(file, UTF_8));
		 
		String readPosition = br.readLine().split("=")[1];
		String readElevation = br.readLine().split("=")[1];
		String readGravity = br.readLine().split("=")[1];
		String readMeteorites = br.readLine().split("=")[1];
		String readPlanetColorRGB = br.readLine().split("=")[1];
		String readLanderStartPosition = br.readLine().split("=")[1];
		String readLands = br.readLine().split("=")[1];
		String readvYMax = br.readLine().split("=")[1];
		String readaPlanet = br.readLine().split("=")[1];
		br.close();

		String[] position_str = readPosition.split(" ");

		for (String item : position_str) {
			position.add(Integer.parseInt(item));
		}

		String[] elevation_str = readElevation.split(" ");

		for (String value : elevation_str) {
			elevation.add(Integer.parseInt(value));
		}

		gravity = Integer.parseInt(readGravity);
		
		meteorites = Integer.parseInt(readMeteorites);

		String[] planetColorRGB_str = readPlanetColorRGB.split(" ");
		for(int i = 0; i < 3; i++) {
			planetColorRGB[i] = Integer.parseInt(planetColorRGB_str[i]);
		}

		String[] landerStartPosition_str = readLanderStartPosition.split(" ");
		for(int i = 0; i < 2; i++) {
			landerStartPosition[i] = Integer.parseInt(landerStartPosition_str[i]);
		}

		String[] lands_str = readLands.split(" ");

		for (String s : lands_str) {
			lands.add(Integer.parseInt(s));
		}

		vYMax = Integer.parseInt(readvYMax);

		aPlanet = Double.parseDouble(readaPlanet);

	}

	/**Metoda zwracająca dane Y terenu
	 * @return tablica wartości Integer
	 */
	public int[] getElevation() {
		int[] tmp = new int[elevation.size()];
		for(int i=0; i<elevation.size(); i++){
			tmp[i] =  elevation.get(i);
		}
		return tmp;
	}

	/**Metoda zwracająca dane X terenu
	 * @return tablica wartości Integer
	 */
	public int[] getPosition() {
		int[] tmp = new int[position.size()];
		for(int i=0; i<position.size(); i++){
			tmp[i] = position.get(i);
		}
		return tmp;
	}

	/**Metoda zwracająca wartość grawitacji na planecie
	 * @return Integer wartości grawitacji
	 */
	public int getGravity() {
		return gravity;
	}

	/**Metoda zwracająca ilość meteorytów na planecie
	 * @return Integer ilości meteorytów
	 */
	public int getMeteorites() {
		return meteorites;
	}

	/**Metoda zwracająca kolor planety
	 * @return tablica trzech wartości Integer odpowiadających wartościom RGB
	 */
	public int[] getPlanetColor(){
		return planetColorRGB;
	}

	/**Metoda zwracająca początkowe położenie lądownika
	 * @return tablica dwóch wartości Integer [współrzędna x, współrzędna y]
	 */
	public int[] getLanderStartPosition(){
		return landerStartPosition;
	}

	/**Metoda zwracająca położenie lądowisk na planecie
	 * @return tablica wartości Integer [wspołrzędna x, współrzędna y, szerokość lądowiska] * ilość lądowisk
	 */
	public int[] getLands() {
		int[] tmp = new int[lands.size()];
		for(int i=0; i<lands.size(); i++){
			tmp[i] =  lands.get(i);
		}
		return tmp;
	}

	/**Metoda zwracająca maksymalną prędkość na planecie
	 * @return Integer wartości maksymalnej prędkości
	 */
	public int getvYMax() {
		return vYMax;
	}

	/**Metoda zwracająca wartość maksymalnego przyspieszenia na planecie
	 * @return Integer wartości maksymalnego przyspieszenia
	 */
	public double getaPlanet() {
		return aPlanet;
	}

	/**Metoda zapisująca wynik gracza do obiektu BufferedWriter
	 * @return Integer wartości maksymalnego przyspieszenia
	 */
	public static int[] getDataTable(String data) {
		String[] data_str = data.split(" ");
		int[] tmp = new int[data_str.length];
		for (int i=0; i<data_str.length; i++) {
			tmp[i] = Integer.parseInt(data_str[i]);
		}
		return tmp;
	}

	/** Metoda pomocnicza do odczytywania liczb z obiektów String
	 * @param data liczba jako String
	 * @return liczba jako Integer
	 */
	public static int getDataInt(String data) {
		return Integer.parseInt(data);
	}

	/** Metoda pomocnicza do odczytywania liczb z obiektów String
	 * @param data liczba jako String
	 * @return liczba jako Double
	 */
	public static double getDataDouble(String data) {
		return Double.parseDouble(data);
	}

	/** Metoda odczytująca dane poziomu z pliku
	 * @param lvl nazwa danego poziomu
	 * @return dane poziomu w postaci String[]
	 */
	static String[] readLevel(String lvl) throws Exception{
		String[] level = {"", "", "", "", "", "", "", "", ""};
		File file = new File("levelConfig/"+lvl+".txt");
		BufferedReader br = new BufferedReader(new FileReader(file, UTF_8));
		for(int i = 0; i < 9; i++){
			String str = br.readLine();
			level[i] = str.split("=")[1];
		}

		return level;
	}

	/** Metoda odczytująca wyniki graczy z pliku txt
	 * @param path ścieżka do pliku txt z wynikami
	 * @return  zwraca ArrayList z wynikami
	 */
	static ArrayList<String> readResults(String path) throws Exception{
		ArrayList<String> results = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(path, UTF_8));

		String textLine = br.readLine();

		do {
			results.add(textLine);
			textLine = br.readLine();
		} while (textLine != null);

		br.close();
		return results;
	}

	/** Metoda odczytuje numer portu serwera z serverConfig
	 * @param path ścieżka do pliku txt z wynikami
	 * @return zwraca numer portu */

	static int readPort(String path) throws Exception{
		String[] text;
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file, UTF_8));
		do {
			String str = br.readLine();
			text = str.split("=");
		}while (!text[0].equals("Port"));
		br.close();
		return Integer.parseInt(text[1]);
	}

	/** Metoda odczytująca z pliku serverConfige dostępne poziomy
	 * @param path ścieżka do pliku txt z wynikami
	 * @return zwraca ArrayList z nazwami poziomów*/

	static ArrayList<String> readLevelsName(String path) throws Exception{
		String[] text;
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file, UTF_8));
		do {
			String str = br.readLine();
			text = str.split("=");
		}while (!text[0].equals("Levels"));
		br.close();
		ArrayList<String> levels =new ArrayList<>(Arrays.asList(text[1].split(",")));
		return levels;
	}
}
