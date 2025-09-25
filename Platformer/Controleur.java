import ihmgui.Controle;
import ihmgui.FrameGrille;

/* Classe Controleur
* 
* Exercice 5
*
* groupe  : 7
* auteurs : * Donovan Prévost  
*           * Joshua  Hermilly 
*           * Marta   Azenha    Nascimento
*           * William Millereux Bienvault
*           * Paul    Gricourt
*           
* créé le    : 16/12/2024 8:45
*
*/

public class Controleur extends Controle
{
	/*-------------------------------*/
	/*          ATTRIBUTS            */
	/*-------------------------------*/
	private   Plateforme  metier;
	private   FrameGrille frame;

	private   String      perso;
	private   String      etat;
	private   String      rep;

	private	  boolean     action;
	private   boolean     nuit;

	private   char        direction;

	private   int         niveau;


	/*-------------------------------*/
	/*			CONSTRUCTEUR		 */
	/*-------------------------------*/
		public Controleur()
		{
			//Classes
			this.metier = new Plateforme()        ;
			this.frame  = new FrameGrille( this );
			
			//Variables
			this.rep	= "./images/link/";		//reperoire des images
			this.perso 	= "_droit.png";			//posture de base
			this.etat   = "Droit";				//etat de base
			this.niveau = metier.getNiveau();	//niveau de base
			this.nuit	= metier.estNuit();		//nuit de base

			//Paramétrage de la fenêtre
			this.frame.setSize     ( 52*(metier.getNbColonnes()+5), 52*(metier.getNbLignes()+1) );
			this.frame.setLocation ( 200,  100				 									);	
			this.frame.setTitle    ( "Niveau_0" + this.niveau								  	);
			this.frame.setVisible  ( true												        );

			//Afficher le personnage au début
			setImage(metier.getPosLig() ,metier.getPosCol() ,1);
		}

	/*-------------------------------*/
	/*          MODIFICATUERS        */	
	/*-------------------------------*/
		//setImage -> permet de changer et gerer les images du jeu
		public String setImage ( int ligne, int colonne, int couche)
		{
			char   symbole;
			
			String sImage=null;
			String e = "bloc/sol";
			String echelle = "bloc/echelle";

			symbole = this.metier.getCase(ligne, colonne);
			if ( couche == 0)
			{
				switch (symbole) {
					//bloc
					case ' ' -> sImage = this.rep + "bloc/vide.png";
					case '+' -> sImage = this.rep + "bloc/vide.png";
					case '@' -> sImage = this.rep + "bloc/sortie.png";
					//porte
					case 'V' -> sImage = this.rep + "bloc/porteverte.png";
					case 'R' -> sImage = this.rep + "bloc/porterouge.png";
					case 'B' -> sImage = this.rep + "bloc/portebleue.png";
					//clef
					case 'v' -> sImage = this.rep + "objet/cleverte.png";
					case 'r' -> sImage = this.rep + "objet/clerouge.png";
					case 'b' -> sImage = this.rep + "objet/clebleue.png";
					//gererBouton
					case '^' -> sImage = this.rep + "objet/bouton.png";
				}
				if (symbole == '=') 
				{
					if (this.metier.estValide (ligne, colonne-1)) 
						if (symbole != this.metier.getCase(ligne    , colonne - 1 ))  e += "_gauche";    

					if (this.metier.estValide (ligne, colonne+1)) 
						if (symbole != this.metier.getCase(ligne    , colonne + 1 ))  e += "_droit";
					sImage = rep + e + ".png";
				}
				if (symbole == '#')
				{
					if (metier.estValide (ligne - 1, colonne))
					{	
						if ('#' != this.metier.getCase(ligne - 1   , colonne)) 
						{
							echelle += "_haut" ;
						}
					}
					else {echelle += "_haut" ;}
					sImage = this.rep + echelle + ".png";
				}   
			}
			if (couche == 1) 
			{
				if (ligne == metier.getPosLig()  && colonne == metier.getPosCol() )
				{
					sImage = this.rep + "pers/pers" + this.perso;
				}
				else
				{
					if (metier.estNuit() && this.nuit)
					{
						sImage = this.rep + "bloc/nuit" + metier.determinerNuit(ligne, colonne) + ".png";
					}
				}
			}
			return sImage;
		}

		//setBouton -> permet de changer le texte des gererBoutons
			public String  setBouton(int  numBtn)
			{
				String texte = null;
		
				if(numBtn == 0)
					texte = "Niveau suivant";
				if(numBtn == 1)
					texte = "Niveau précédent";
				if(numBtn == 2)
					texte = "Recommencer";
				if(numBtn == 3)
					texte = "Changer de thème";
				if(numBtn == 4)
					texte = "Enlever nuit";
		
				return texte;
			}
		
			public int     setNbLigne        () { return metier.getNbLignes();     }
			public int     setNbColonne      () { return metier.getNbColonnes();   }
			public boolean setNumLigneColonne() { return true;                     }
			public int     setLargeurImg     () { return 52;                       }
			public String  setFondGrille     () { return this.rep+"bloc/fond.png"; }

	/*-------------------------------*/
	/*        AUTRES METHODES        */
	/*-------------------------------*/
		//lancer -> permet de lancer le jeu
			//lancer -> permet de lancer le jeu
            public void lancer ()
            {
                while (!metier.finJeu())
                {
                    metier.aAtteintClef();

                    if (metier.aAtteintEchelle() && !etat.equals("Echelle"))
                    {
                        this.etat = "Echelle";
                        this.perso = "_monte1.png";
                        setImage(metier.getPosLig() , metier.getPosCol() , 1);
                    }

					if ( this.etat.equals("HautDroit") || this.etat.equals("HautGauche"))
					{
						if ( this.etat.equals("HautDroit"))
						{
							gererDeplacementDroite();
							rafraichirPersonnage();
						}
						if (this.etat.equals("HautGauche"))
						{
							gererDeplacementGauche();
							rafraichirPersonnage();
						}
					}

                    if (this.action 
                    && (    metier.getCase(metier.getPosLig()   + 1, metier.getPosCol()) == '=' 
                        || !metier.estValide(metier.getPosLig() + 1, metier.getPosCol()) 
                        ||  metier.getCase(metier.getPosLig()      , metier.getPosCol()) == '#' 
                        ))
                    {
                        gestionDirection();
                        rafraichirPersonnage();
                        this.action = false;
                    }
                    else
                    {
                        if (!etat.equals("Echelle")) {gererParapluie();}
                        rafraichirPersonnage();
                    }
                    try{Thread.sleep(100);}catch(InterruptedException ex){}
                }
                frame.fermer();
            }

		//jouer -> permet de gerer les actions du joueur
			public void jouer (String touche)
			{
				switch (touche)
				{
					case "FL-H"    -> this.direction = 'N';
					case "FL-B"    -> this.direction = 'S';
					case "FL-G"    -> this.direction = 'O';
					case "FL-D"    -> this.direction = 'E';
		
					case "CR-"     -> this.direction = 'J';
					case "CR-FL-D" -> this.direction = 'D';
					case "CR-FL-G" -> this.direction = 'G';
				}
				this.action = true;
			}

		//gestionDirection -> permet de gerer les directions du personnage
			public void gestionDirection()
			{
				switch (this.direction)
				{
					case 'N'-> gererDeplacementNord();
					case 'S'-> gererDeplacementSud();
					case 'J'-> gererDeplacementJump();
					case 'D'-> gererDeplacementDroite();
					case 'G'-> gererDeplacementGauche();
					case 'O'-> gererDeplacementOuest();
					case 'E'-> gererDeplacementEst();
				}
			}
			

		//gererBouton -> permet de gerer les actions des gererBoutons
			public void bouton  (int nulBtn)
			{
				if ( nulBtn == 0 ) { metier.prochainNiveau(this.niveau) ; this.autreNiveau(); this.frame.majIHM();}
				if ( nulBtn == 1 ) { metier.precedentNiveau(); this.autreNiveau(); this.frame.majIHM();}
				if ( nulBtn == 2 ) { metier.recommencer();     					   this.frame.majIHM();}
				if ( nulBtn == 3 ) 
				{ 
					switch (this.rep)
					{
						case "./images/link/"    -> this.rep = "./images/asterix/" ;
						case "./images/asterix/" -> this.rep = "./images/lem/"     ;
						case "./images/lem/"     -> this.rep = "./images/indiana/" ;
						case "./images/indiana/" -> this.rep = "./images/link/"    ;
					}
		
				}
				if(nulBtn == 4) { this.frame.majIHM(); this.nuit = !this.nuit;}
			}
		
		
		//autreNiveau -> permet de changer de niveau
			public void autreNiveau()
			{
				if (this.niveau != metier.getNiveau())
				{
					this.niveau = metier.getNiveau();
					if(metier.getNiveau() == 4 || metier.getNiveau() == 5)
					{
						frame.setSize     ( 52*(metier.getNbColonnes()+9), 52*(metier.getNbLignes()+2)        );
					}
					else
					{
						frame.setSize     ( 52*(metier.getNbColonnes()+5), 52*(metier.getNbLignes()+1)        );
					}
					
					frame.setLocation ( 200,  10            );
					frame.setTitle    ( "Niveau_0" + this.niveau );
					frame.setVisible  ( true                );

					this.nuit = metier.estNuit();        

					//affichage perso au début
					this.etat = "Droit";
					rafraichirPersonnage();
				}
			}

	/*-------------------------------*/
	/*         METHODES PRIVES       */
	/*-------------------------------*/
		//gererDirection -> permet de gerer les directions du personnage
			private void gererDeplacementNord()
			{
				if (etat.equals("Echelle") 
				    && metier.estValide(metier.getPosLig() - 1, metier.getPosCol()) 
					&& metier.getCase(metier.getPosLig() - 1, metier.getPosCol()) == '#')
				{
					metier.deplacer('N');
				}
				if (metier.estConfondu('#'))
				{
					this.etat = "Echelle";
					if (metier.getCase(metier.getPosLig() - 1, metier.getPosCol()) == '#')
					{
						if (this.perso.equals("_monte1.png")) {this.perso = "_monte2.png";}
						else                                           {this.perso = "_monte1.png";}
					}
				}
			}
		
		//gererDeplacementSud -> permet de gerer les deplacements vers le bas
			private void gererDeplacementSud()
			{
				if (etat.equals("Echelle") && metier.estConfondu('#'))
				{
					metier.deplacer( 'S' );
					if (perso.equals("_monte1.png")){perso = "_monte2.png";}
					else{perso = "_monte1.png";}
				}
				if (!  metier.estConfondu('#') 
			        || metier.getCase(metier.getPosLig()+1, metier.getPosCol()) == '=')
				{
					actualiserPersonnageEtat("Droit", "_droit.png");
				}
			}
			
			private void gererDeplacementJump()
			{
				if (metier.estValide(metier.getPosLig() - 1, metier.getPosCol()))
				{
					if (metier.getCase(metier.getPosLig() - 1, metier.getPosCol()) != '=' )
					{
						if (metier.getCase(metier.getPosLig(), metier.getPosCol()) == '#')
						{
							if (metier.getCase(metier.getPosLig() - 1, metier.getPosCol()) != ' ')
							{
								metier.deplacer('N');
							}
						}
						else
						{
							metier.deplacer('N');
						}
					}
				}
			}
			
		//gererDeplacementDroite -> permet de gerer les deplacements vers la droite
			private void gererDeplacementDroite()
			{
				if (this.etat.equals("HautDroit"))
				{
					if (metier.getCase(metier.getPosLig() + 1, metier.getPosCol() + 1) != '=' &&
					    metier.getCase(metier.getPosLig(), metier.getPosCol()) != '#')
						{
							actualiserPersonnageEtat("Droit", "_droit.png");
							metier.deplacer('E');
							metier.deplacer('S');
						}
				}

				else if ((this.etat.equals("Droit") || this.etat.equals("Echelle"))
					      && metier.estValide(metier.getPosLig() - 1, metier.getPosCol())             )
				{
					if (metier.getCase(metier.getPosLig() - 1, metier.getPosCol() + 1) != '=')
					{
						metier.deplacer('D');
						actualiserPersonnageEtat("HautDroit", "_droit.png");
					}
				}
			}
		
		//gererDeplacementGauche -> permet de gerer les deplacements vers la gauche
			private void gererDeplacementGauche()
			{
				if (this.etat.equals("HautGauche"))
				{
					if (metier.getCase(metier.getPosLig() + 1, metier.getPosCol() - 1) != '=' &&
					    metier.getCase(metier.getPosLig(), metier.getPosCol()) != '#')
					{
						actualiserPersonnageEtat("Gauche", "_gauche.png");
						metier.deplacer('O');
						metier.deplacer('S');
					}
				}
				
				else if((this.etat.equals("Gauche") || this.etat.equals("Echelle"))
				         && metier.estValide(metier.getPosLig() - 1, metier.getPosCol())             )
				{
					if (metier.getCase(metier.getPosLig() - 1, metier.getPosCol() - 1) != '=')
					{
						metier.deplacer('G');
						actualiserPersonnageEtat("HautGauche", "_gauche.png");
					}
				}
			}
		
		//gererDeplacementOuest -> permet de gerer les deplacements vers l'ouest
			private void gererDeplacementOuest()
			{
				if (metier.getCase(metier.getPosLig()     , metier.getPosCol() ) == '#' &&
		    	    metier.getCase(metier.getPosLig() + 1 , metier.getPosCol() ) != '='   )
				{
					metier.deplacer('O');
					actualiserPersonnageEtat("Gauche", "_gauche.png");
				}
				else
				{
					if (etat.equals("Gauche")) {metier.deplacer('O');}
					actualiserPersonnageEtat("Gauche", "_gauche.png");
				}
			}
			
		//gererDeplacementEst -> permet de gerer les deplacements vers l'est
			private void gererDeplacementEst()
			{
				if (metier.getCase(metier.getPosLig()     , metier.getPosCol() ) == '#' &&
					metier.getCase(metier.getPosLig() + 1 , metier.getPosCol() ) != '='    )
				{
					metier.deplacer('E');
					actualiserPersonnageEtat("Droit", "_droit.png");
				}
				else
				{
					if (etat.equals("Droit")){metier.deplacer('E');}
					actualiserPersonnageEtat("Droit", "_droit.png");
				}
			}
		
		//actualiserPersonnageEtat -> permet de changer l'etat du personnage
			private void actualiserPersonnageEtat(String nouveauEtat, String nouvelleImage)
			{
				this.etat = nouveauEtat;
				this.perso = nouvelleImage;
			}

			private void rafraichirPersonnage()
			{
				setImage(metier.getPosLig() , metier.getPosCol() , 1);
				this.frame.majIHM();
			}

		//gererParapluie -> permet de gerer le gererParapluie
			private void gererParapluie()
			{
				if (metier.estValide (metier.getPosLig()  +2, metier.getPosCol() ))
				{
					if (metier.getCase(metier.getPosLig() +2,metier.getPosCol() ) == ' ')
					{
						this.perso = "_tombe.png";
					}
				}
				if (metier.getCase(metier.getPosLig() +1,metier.getPosCol() ) == '=')
				{
					if (etat.equals("Gauche")){this.perso = "_gauche.png";}
					if (etat.equals("Droit" )){this.perso = "_droit.png";}
					setImage(metier.getPosLig() , metier.getPosCol() , 1);
				}
				else{metier.deplacer( 'S' );}
			}
			

	/*-------------------------------*/
	/*              main             */
	/*-------------------------------*/
	public static void main(String[] arg)
	{
	new Controleur().lancer();
	}

}