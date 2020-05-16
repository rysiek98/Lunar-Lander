import java.io.*;
import java.util.ArrayList;

/** KLASA WCZYTUJĄCA DANE O POZIOMIE Z PLIKU TEKSTOWEGO, ZAPISUJĄCA WYNIK DO PLIKU  */
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
	
	public LoadingLevel(File file) throws Exception{

		BufferedReader br = new BufferedReader(new FileReader(file));
		 
		String st1 = br.readLine().split("=")[1];;
		String st2 = br.readLine().split("=")[1];;
		String st3 = br.readLine().split("=")[1];;
		String st4 = br.readLine().split("=")[1];;
		String st5 = br.readLine().split("=")[1];;
		String st6 = br.readLine().split("=")[1];;
		String st7 = br.readLine().split("=")[1];;
		String st8 = br.readLine().split("=")[1];;
		String st9 = br.readLine().split("=")[1];;
		br.close();

		String[] position_str = st1.split(" ");

		for (String item : position_str) {
			position.add(Integer.parseInt(item));
		}

		String[] elevation_str = st2.split(" ");

		for (String value : elevation_str) {
			elevation.add(Integer.parseInt(value));
		}

		gravity = Integer.parseInt(st3);
		
		meteorites = Integer.parseInt(st4);

		String[] planetColorRGB_str = st5.split(" ");
		for(int i = 0; i < 3; i++) {
			planetColorRGB[i] = Integer.parseInt(planetColorRGB_str[i]);
		}

		String[] landerStartPosition_str = st6.split(" ");
		for(int i = 0; i < 2; i++) {
			landerStartPosition[i] = Integer.parseInt(landerStartPosition_str[i]);
		}

		String[] lands_str = st7.split(" ");

		for (String s : lands_str) {
			lands.add(Integer.parseInt(s));
		}

		vYMax = Integer.parseInt(st8);

		aPlanet = Double.parseDouble(st9);

	}
	
	public int[] getElevation() {
		int[] tmp = new int[elevation.size()];
		for(int i=0; i<elevation.size(); i++){
			tmp[i] =  elevation.get(i);
		}
		return tmp;
	}

	public int[] getPosition() {
		int[] tmp = new int[position.size()];
		for(int i=0; i<position.size(); i++){
			tmp[i] = position.get(i);
		}
		return tmp;
	}

	public int getGravity() {
		return gravity;
	}

	public int getMeteorites() {
		return meteorites;
	}

	public int[] getPlanetColor(){
		return planetColorRGB;
	}

	public int[] getLanderStartPosition(){
		return landerStartPosition;
	}

	public int[] getLands() {
		int[] tmp = new int[lands.size()];
		for(int i=0; i<lands.size(); i++){
			tmp[i] =  lands.get(i);
		}
		return tmp;
	}

	public int getvYMax() {
		return vYMax;
	}

	public double getaPlanet() {
		return aPlanet;
	}

	public static void saveResult(String name, int points, String diffLevel) throws Exception {
		FileWriter file = new FileWriter("TabelaWynikow.txt", true);
		BufferedWriter out = new BufferedWriter(file);
		out.write("Nick gracza: "+name+" uzyskane punkty: "+Integer.toString(points)+" poziom trudności: "+diffLevel+"\r\n");
		out.close();
	}

	public static int[] getDataTable(String data) {
		String[] data_str = data.split(" ");
		int[] tmp = new int[data_str.length];
		for (int i=0; i<data_str.length; i++) {
			tmp[i] = Integer.parseInt(data_str[i]);
		}
		return tmp;
	}

	public static int getDataInt(String data) {
		return Integer.parseInt(data);
	}

	public static double getDataDouble(String data) {
		return Double.parseDouble(data);
	}
}
