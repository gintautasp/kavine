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
	* virëjas ruoðia patiekalus
	*
	*/
	public void ruostiPatiekalus() {
		
		int virejas_uztruks = 0;
		
		for (int i = 0; i < kiek_patiekalu; i++) {
			
			if ( patiekalai [ i ].trukmeRuosimo() > 0 ) {
				
				patiekalai [ i ].busPradetasRuostiUz( virejas_uztruks ); // 	      prisumuojam prie ruoðimo laiko
				
				virejas_uztruks = patiekalai [ i ].trukmeRuosimo();  //               kada galës ruoðti kità patiekalà
				
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
	
		while ( ! uzsakymai_ivykdyti ) {							// kol yra neávykdytø uþsakymø
			
			uzsakymai_ivykdyti = true;								// o gal jie ávykdyti? 	
			boolean padaveja_pateike = false;						// kol kas padavëja nieko nepatiekë
			
			for (int i = 0; i < kiek_patiekalu; i++) {				// perþiûrime patiekalø sàraðà:
				
				if ( patiekalai [ i ].bukle != PatiekaluPateikimoBusenos.Patiektas) { // radom nepatiektà patiekalà >>> a1
				
					if ( 
								( patiekalai [ i ].trukmePateikimo() <= padavejos_laikas ) // ar jau paruoðtas
							&& 
								! padaveja_pateike 											// ir padavëja nieko naptiekë
					) {
						/*
						 * patiekalo pateikimas
						 */
						patiekalai [ i ].bukle = PatiekaluPateikimoBusenos.Patiektas;
						padavejos_laikas += 2;
						padaveja_pateike = true; 							// ðitos perþiûros metu paitekë patiekalà
						patiekalai [ i ].patiekti ( padavejos_laikas );
						seka_patiekalu [ k ] = i;
						k++;
					}
					uzsakymai_ivykdyti = false;									// <<< a1 uþsakymai dar buvo neávykdyti
				}
			}
			if ( ! padaveja_pateike ) {	// jei nieko nepatiekë laikas didëja 1-a minute
				
				padavejos_laikas++;
			}
		}	
	}
	
	public void isnesioti() {
		
		for(int i = 0; i < kiek_patiekalu; i++ ) {
	
			System.out.println ( 		// iðvedam praneðimà, apie patiekimo laikà ..
					
					"laikas: " +  patiekalai [ seka_patiekalu [ i ] ].kadaPatiekta() 
					+ " patiekalas: " + patiekalai[ seka_patiekalu [ i ] ].pavadinimas // .. ir pavadinimà.
			);	
		}
	}
	
	/*
	 * pagalbine testine struktura
	 */
	public void parodyti() {
		
		System.out.println ( "\n----------- uþsakymø eiga:\n" );		
	
		for (int i = 0; i < kiek_patiekalu; i++) {
			
			patiekalai [ i ].rodyk();
		}
	}
}
