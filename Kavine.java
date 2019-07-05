package kavine;

public class Kavine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Uzsakymai uzsakymai = new Uzsakymai();
		uzsakymai.nuskaitytiIsFailo( "uzsakymai.csv" ); // tik ivedimas
		uzsakymai.ruostiPatiekalus();
		uzsakymai.patiekti();
		uzsakymai.isnesioti(); 							// tik iðvedimas
		// uzsakymai.parodyti();		
	}
}
