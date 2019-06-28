package kavine;

import java.io.BufferedReader;
// import java.io.FileInputStream;
import java.io.FileReader;
// import java.io.InputStream;
// import java.io.InputStreamReader;

public class Uzsakymai {
	
	public Patiekalas[] patiekalai;
	public int kiek_patiekalu = 0;
	
	public Uzsakymai() {
		
		this.patiekalai = new Patiekalas[100];
	}

	public void nuskaitytiIsFailo (String failas) {
		
		String was_read = null;
		int trukme_ruosimo; 
		int trukme_kaitinimo;
		
		try {
	
			BufferedReader br = new BufferedReader( new FileReader( failas ) );
		
			System.out.println ( "----------- duomenu failo turinys:\n" );
		
			while ( ( was_read = br.readLine() ) != null ) {
				
				System.out.println( "\t" + was_read );
				String[] langeliai = was_read.split ( "," );
				
				trukme_ruosimo = 0;
				trukme_kaitinimo = 0;
				
				if ( langeliai.length > 1 ) {
				
					trukme_ruosimo = Integer.parseInt( langeliai [ 1 ] );		
				}
				if ( langeliai.length > 2 ) {

					trukme_kaitinimo = Integer.parseInt( langeliai [ 2 ] );
				}
				
				if ( trukme_ruosimo == 0 ) {
					
					patiekalai [ kiek_patiekalu ] = new Patiekalas ( langeliai [0] );
					
				} else {
					
					if ( trukme_kaitinimo == 0) {
						
						patiekalai [ kiek_patiekalu ] = new RuosiamasPatiekalas ( langeliai [0], trukme_ruosimo );
						
					} else {
						
						patiekalai [ kiek_patiekalu ] = new KarstasPatiekalas ( langeliai [0], trukme_ruosimo, trukme_kaitinimo );						
					}
				}
				kiek_patiekalu++;
			}
			
			br.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public void ruostiPatiekalus() {
		
		int virejas_uztruks = 0;
		
		for (int i = 0; i < kiek_patiekalu; i++) {
			
			if ( patiekalai [ i ].trukmeRuosimo() > 0 ) {
				
				patiekalai [ i ].busPradetasRuostiUz( virejas_uztruks );
				virejas_uztruks += patiekalai [ i ].trukmeRuosimo();
			}
		}
	}
	
	public void patiekti() {
	
		int padavejos_laikas = 0;
		boolean uzsakymai_ivykdyti = false;
	
		while ( ! uzsakymai_ivykdyti ) {
			
			uzsakymai_ivykdyti = true;	
			boolean padaveja_pateike = false;			
			
			for (int i = 0; i < kiek_patiekalu; i++) {
				
				if ( patiekalai [ i ].bukle != PatiekaluPateikimoBusenos.Patiektas) {
				
					if ( ( patiekalai [ i ].trukmePateikimo() <= padavejos_laikas ) && ! padaveja_pateike ) {
						
						patiekalai [ i ].bukle = PatiekaluPateikimoBusenos.Patiektas;
						padavejos_laikas += 2;
						padaveja_pateike = true; 
						System.out.println ( "laikas: " +  padavejos_laikas + " patiekalas" + patiekalai[ i ].pavadinimas );
					}
					uzsakymai_ivykdyti = false;
				}
			}
			if ( ! padaveja_pateike ) {
				
				padavejos_laikas++;
			}
		}	
	}
	
	public void parodyti() {
		
		System.out.println ( "\n----------- uþsakymø eiga:\n" );		
	
		for (int i = 0; i < kiek_patiekalu; i++) {
			
			patiekalai [ i ].rodyk();
		}
	}
}
