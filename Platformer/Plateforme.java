import java.io.FileInputStream;
import java.util.Scanner;

/* Classe Plateforme
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
* créé le  : 17/12/13h45
*/


public class Plateforme
{
	//Attributs
		private int      posLig;
		private int      posCol;
		private int      numNiveau;

		private int[]    nivReussi;
		private boolean  nuit;

        private char     mvtAnterieur;

		private int[][]  coordoneePorte;
		private char[][] niveau;

	/*-------------------------------*/
	/*  constructeur :  Labyrinthe() */
	/*-------------------------------*/
		public Plateforme()
		{
			this.numNiveau = 1; 
			nivReussi = new int[] {0, 0, 0, 0, 0};
			this.initPlateforme(1);
		}

	/*--------------------------------*/
	/*			 ACCESSEUR 			  */
	/*--------------------------------*/

		// getNbLignes  -> retourne le nombre de lignes du niveau
			public int getNbLignes ()   { return this.niveau.length; }

		// getNbColonnes -> retourne le nombre de colonnes du niveau
			public int getNbColonnes () { return this.niveau[0].length; }

		// getPosLig -> retourne la position de la ligne du personnage
			public int getPosLig ()     { return this.posLig; }

		// getPosCol -> retourne la position de la colonne du personnage
			public int getPosCol ()     { return this.posCol; }

		// getCase -> retourne le caractere de la case
			public char getCase (int lig, int col)
			{
				//Variables
				char caseAct = ' ';
				//Instructions
				if (lig >= 0 && lig < this.getNbLignes() && col >= 0 && col < this.getNbColonnes())
				{
					caseAct = this.niveau[lig][col];
				}
				return caseAct; 
			}
		
		// getNiveau -> retourne le numero du niveau
			public int getNiveau () { return this.numNiveau; }

		// getNivReussi -> retourne le tableau des niveaux reussis
			public int getNivReussi(int nv) { return this.nivReussi[nv]; }

		
		// getMvrAnterieur -> retourne le mouvement anterieur
			public char getMvtAnterieur () { return this.mvtAnterieur; }
		

	/*--------------------------------*/
	/*		AUTRES MÉTHODES 		  */
	/*--------------------------------*/
		//deplacer -> deplace le personnage
			public void deplacer (char direction)
			{
				//Variables
				int     modifPosCol = this.posCol;
				int     modifPosLig = this.posLig;

				// choix de la direction
				switch (direction) 
				{
					case 'N' -> modifPosLig = this.posLig - 1;
					case 'S' -> modifPosLig = this.posLig + 1;
					case 'O' -> modifPosCol = this.posCol - 1;
					case 'E' -> modifPosCol = this.posCol + 1;

					case 'J' -> modifPosLig = this.posLig - 1;

					case 'D' ->
					{
						modifPosCol = this.posCol + 1;
						modifPosLig = this.posLig - 1;
					}
					case 'G' -> 
					{
						modifPosCol = this.posCol - 1;
						modifPosLig = this.posLig - 1;
					}
				}

				//Déplacement à l'interieur de la grille
				if (this.estValide(modifPosLig, modifPosCol))
				{
					//Pas sur une porte
					if ((int) this.niveau[modifPosLig][modifPosCol] < (int)'A'
					||
				(int) this.niveau[modifPosLig][modifPosCol] > (int)'Z') 
					{

						if (
							this.niveau[modifPosLig][modifPosCol] != '=')
						{
							this.posCol = modifPosCol;
							this.posLig = modifPosLig;
						}
					}
				}
		
			//Coups thorique ?
			if (direction == 'E' && modifPosCol >= this.getNbColonnes() && this.niveau[modifPosLig][0] != '=')
			{ 
				this.posCol = 0;
			}
			if (direction == 'O' && modifPosCol < 0 && this.niveau[modifPosLig][this.getNbColonnes()-1] != '=') 
			{
				this.posCol = this.getNbColonnes() - 1;
			}
			if (direction == 'N' && modifPosLig < 0 && this.niveau[this.getNbLignes()-1][modifPosCol] != '=')
			{
				this.posLig = this.getNbLignes() - 1;
			}
			if (direction == 'S' && modifPosLig >= this.getNbLignes() && this.niveau[0][modifPosCol] != '=')
			{
				this.posLig = 0;
			}
			if ((direction == 'D' || direction == 'd') && modifPosCol >= this.getNbColonnes() && this.niveau[modifPosLig][0] != '=')
			{ 
				this.posCol = 0;
				this.posLig = this.posLig - 1;
			}
			if ((direction == 'G' || direction == 'g') && modifPosCol < 0 && this.niveau[modifPosLig][this.getNbColonnes()-1] != '=')
			{
				this.posCol = this.getNbColonnes() - 1;
				this.posLig = this.posLig - 1;
			}
		}

		//aAtteintClef -> si le personnage a atteint la clef et suprime la clef et sa porte
			public void aAtteintClef()
			{
				//variables
				int porteLig = 0;
				int porteCol = 0;
				int caseAct  = getCase(this.posLig, this.posCol);

				//Instructions
				//Si le personnage est sur une clef
				if ( (int) caseAct >= (int) 'a' && (int) caseAct <= (int) 'z')
				{
					//Recherche de la porte correspondante
					for (int cptPorte = 0; cptPorte < coordoneePorte.length; cptPorte++)
					{
						//Si la porte correspond à la clef
						if ( (int) getCase(coordoneePorte[cptPorte][0], coordoneePorte[cptPorte][1]) 
								==
							(int) caseAct - 32
							)
						{
							porteLig = coordoneePorte[cptPorte][0];
							porteCol = coordoneePorte[cptPorte][1];
							this.niveau[porteLig][porteCol] = ' ';
						}
					}
					//Suppression de la clef et de la porte
					this.niveau[this.posLig][this.posCol] = ' ';
				}
			}

		//aAtteintEchelle -> retourne si le personnage a atteint l'echelle
			public boolean aAtteintEchelle()
			{
				//Variables
				boolean atteint = false;

				//Instructions
				//Si le personnage est sur une echelle
				if (getCase(this.posLig, this.posCol) ==  '#' && (getCase(this.posLig+1, this.posCol) != '='))
				{
					atteint = true;
				}
				return atteint;
			}
	
		//estValide -> retourne si la position est valide
			public boolean estValide(int lig, int col)
			{	
				return (lig < this.getNbLignes() && lig >= 0 && col < this.getNbColonnes() && col >=0 );
			}
	
		//estConfondu
			public boolean estConfondu(char symbole)
			{
				//variables
					boolean confondu = false;
				//Instructions
					if (getCase(this.posLig, this.posCol) == symbole)
					{
						confondu = true;
					}
				return confondu;
			}

		//estSortie
			public void estSortie() 
			{ 
				//Instructions
					if (this.niveau[this.posLig][this.posCol] == '@') 
					{
						this.nivReussi[this.numNiveau -1] = 1;
					}
			}
	
		//estGagner
			public boolean finJeu() { return this.numNiveau == 6;}

		//estNuit -> retourne si il fait nuit et supprime la nuit
			public boolean estNuit() 
			{ 
				if (getCase(this.posLig, this.posCol) == '^')
				{
					this.niveau[this.posLig][this.posCol] = ' ';
					this.nuit = false;
				}
				return this.nuit; 
			}

		//recommencer
			public void recommencer() { this.initPlateforme(this.numNiveau); }

		//determinerNuit
			public char determinerNuit(int lig, int col)
			{
				char bloc = ' ';

				if ((lig <= this.posLig || (col >= this.posCol || col <= this.posCol) ))
						{ bloc = '1';}
				if ((lig >= this.posLig || lig <= this.posLig-1 )
					|| (col >= this.posCol +1 || col <= this.posCol -1))
						{ bloc = '2';}
				if ((lig >= this.posLig +1 || lig <= this.posLig-2)
					|| (col >= this.posCol+2 || col <= this.posCol-2))
						{ bloc = '3';}
				if ((lig >= this.posLig +2 || lig <= this.posLig-3)
					|| (col >= this.posCol+3 || col <= this.posCol-3))
						{ bloc = '4';}

				return bloc;
			}

		//prochainNiveau
			public void prochainNiveau(int nv)
			{
				estSortie();
				if (nivReussi[nv-1] == 1)
				{
					this.numNiveau++;
					this.initPlateforme(this.numNiveau);
				}
			}
			
		//precedentNiveau
			public void precedentNiveau()
			{
				if(this.numNiveau > 1)
				{
					this.numNiveau--;
					this.initPlateforme(this.numNiveau);
				}
			}

	/*--------------------------------*/
	/*		INITIALISATION 			  */
	/*--------------------------------*/
			
		//initPlateforme -> initialise la plateforme
			private void initPlateforme(int numNiveau)
			{
				/* Initialisation de la grille du jeu  */
				//Données :     
					//Variables
						String 	ligne	  = "";
						int 	nbLigne   = 0;
						int		nbColonne = 0;
						int 	cptPorte  = 0;
						String fileName = "data/niveau_0" + this.numNiveau + ".data";
			
				//Instructions
				try
				{
					Scanner sc = new Scanner(new FileInputStream(fileName));
					// Lire toutes les lignes pour déterminer le nombre de lignes et de colonnes
					while (sc.hasNextLine())
					{
						ligne = sc.nextLine();
						nbLigne++;
						nbColonne = ligne.length();
					}
					sc.close();
		
					// Créer le tableau de caractères pour le niveau
					this.niveau = new char[nbLigne][nbColonne];
		
					// Réinitialiser le scanner pour lire le fichier à nouveau
					sc = new Scanner(new FileInputStream(fileName));
					int cptLig= 0;
					while (sc.hasNextLine())
					{
						ligne = sc.nextLine();
						for (int cptCol = 0; cptCol < nbColonne; cptCol++)
						{
							// Remplir le tableau de caractères
							this.niveau[cptLig][cptCol] = ligne.charAt(cptCol);

							// Compter le nombre de portes
							if ((int)this.niveau[cptLig][cptCol] >= (int)'A' && (int)this.niveau[cptLig][cptCol] <= (int)'Z')
							{
								cptPorte++;
							}
						}
						cptLig++;
					}
					sc.close();
				} catch (Exception e) { e.printStackTrace(); }
			
				//Initialisation des attributs
				this.coordoneePorte = new int[cptPorte][2];
				cptPorte 			= 0;
				this.nuit 			= false;
				this.mvtAnterieur 	= ' ';

				for (int cptLig = 0; cptLig < nbLigne; cptLig++)
				{
					for (int cptCol = 0; cptCol < nbColonne; cptCol++)
					{
						char caseAct = this.niveau[cptLig][cptCol];
						if (caseAct == '+') // Position initiale du personnage
						{
							this.posLig = cptLig;
							this.posCol = cptCol;
						}
						if ((int)caseAct >= 'A' && (int)caseAct <= 'Z') // Coordonnées des portes
						{
							this.coordoneePorte[cptPorte][0] = cptLig;
							this.coordoneePorte[cptPorte][1] = cptCol;
							cptPorte++;
						}
						if (caseAct == '^')  // Nuit
						{
							this.nuit = true;
						}
					}
				}
			}
}
