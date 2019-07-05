package kavine;

import java.io.BufferedReader;
// import java.io.FileInputStream;
import java.io.FileReader;
// import java.io.InputStream;
// import java.io.InputStreamReader;

public class Uzsakymai {
	
	public Patiekalas[] patiekalai;
	public int kiek_patiekalu = 0;
	public int[] seka_patiekalu;
	
	public Uzsakymai() {
		
		this.patiekalai = new Patiekalas[100];
		this.seka_patiekalu = new int [100];
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
	
	/**
	* vir�jas ruo�ia patiekalus
	*
	*/
	public void ruostiPatiekalus() {
		
		int virejas_uztruks = 0;
		
		for (int i = 0; i < kiek_patiekalu; i++) {
			
			if ( patiekalai [ i ].trukmeRuosimo() > 0 ) {
				
				patiekalai [ i ].busPradetasRuostiUz( virejas_uztruks ); // 	      prisumuojam prie ruo�imo laiko
				
				virejas_uztruks = patiekalai [ i ].trukmeRuosimo();  //               kada gal�s ruo�ti kit� patiekal�
				
				/* ---------------------------------------------------------- tikrinimas
				if (i == 4) {
					
					System.out.println(  
							
						patiekalai [ i ].bus_paruostas_uz + " " + patiekalai [ i ].bus_patiektas_apytiksliai_uz 
					);
					System.out.println( i + " --- " + virejas_uztruks);
				}
				*/
			}
		}
	}
	
	public void patiekti() {
	
		int padavejos_laikas = 0;
		boolean uzsakymai_ivykdyti = false;
		int k = 0;
	
		while ( ! uzsakymai_ivykdyti ) {							// kol yra ne�vykdyt� u�sakym�
			
			uzsakymai_ivykdyti = true;								// o gal jie �vykdyti? 	
			boolean padaveja_pateike = false;						// kol kas padav�ja nieko nepatiek�
			
			for (int i = 0; i < kiek_patiekalu; i++) {				// per�i�rime patiekal� s�ra��:
				
				if ( patiekalai [ i ].bukle != PatiekaluPateikimoBusenos.Patiektas) { // radom nepatiekt� patiekal� >>> a1
				
					if ( 
								( patiekalai [ i ].trukmePateikimo() <= padavejos_laikas ) // ar jau paruo�tas
							&& 
								! padaveja_pateike 											// ir padav�ja nieko naptiek�
					) {
						/*
						 * patiekalo pateikimas
						 */
						patiekalai [ i ].bukle = PatiekaluPateikimoBusenos.Patiektas;
						padavejos_laikas += 2;
						padaveja_pateike = true; 							// �itos per�i�ros metu paitek� patiekal�
						patiekalai [ i ].patiekti ( padavejos_laikas );
						seka_patiekalu [ k ] = i;
						k++;
					}
					uzsakymai_ivykdyti = false;									// <<< a1 u�sakymai dar buvo ne�vykdyti
				}
			}
			if ( ! padaveja_pateike ) {	// jei nieko nepatiek� laikas did�ja 1-a minute
				
				padavejos_laikas++;
			}
		}	
	}
	
	public void isnesioti() {
		
		for(int i = 0; i < kiek_patiekalu; i++ ) {
	
			System.out.println ( 		// i�vedam prane�im�, apie patiekimo laik� ..
					
					"laikas: " +  patiekalai [ seka_patiekalu [ i ] ].kadaPatiekta() 
					+ " patiekalas: " + patiekalai[ seka_patiekalu [ i ] ].pavadinimas // .. ir pavadinim�.
			);	
		}
	}
	
	/*
	 * pagalbine testine struktura
	 */
	public void parodyti() {
		
		System.out.println ( "\n----------- u�sakym� eiga:\n" );		
	
		for (int i = 0; i < kiek_patiekalu; i++) {
			
			patiekalai [ i ].rodyk();
		}
	}
}
