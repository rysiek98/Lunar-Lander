import java.io.*;
import java.lang.String;
import java.util.ArrayList;

//KLASA WCZYTUJĄCA DANE O POZIOMIE Z PLIKU TEKSTOWEGO

public class LoadingLevel {

	private ArrayList<Integer> elevation = new ArrayList<Integer>();
	private ArrayList<Integer> position = new ArrayList<Integer>();
	private int gravity;
	private int meteorites;
	private int[] planetColorRGB =  new int[3];;
	
	public LoadingLevel(File file) throws Exception{

		BufferedReader br = new BufferedReader(new FileReader(file));
		 
		String st1 = br.readLine();
		String st2 = br.readLine();
		String st3 = br.readLine();
		String st4 = br.readLine();
		String st5 = br.readLine();
		br.close();

		String[] position_str = st1.split(" ");

		for(int i = 0; i < position_str.length; i++) {
			position.add(Integer.parseInt(position_str[i]));
		}

		String[] elevation_str = st2.split(" ");

		for(int i = 0; i < elevation_str.length; i++) {
			elevation.add(Integer.parseInt(elevation_str[i]));
		}

		gravity = Integer.parseInt(st3);
		
		meteorites = Integer.parseInt(st4);

		String[] planetColorRGB_str = st5.split(" ");
		for(int i = 0; i < 3; i++) {
			planetColorRGB[i] = Integer.parseInt(planetColorRGB_str[i]);
		}

	}
	
	public int[] getElevation() {
		int tmp[] = new int[elevation.size()];
		for(int i=0; i<elevation.size(); i++){
			tmp[i] =  elevation.get(i);
		}
		return tmp;
	}

	public int[] getPosition() {
		int tmp[] = new int[position.size()];
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
}
