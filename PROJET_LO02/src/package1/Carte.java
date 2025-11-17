/**
 * 
 */
package package1;


public class Carte {
	
    private Couleur couleur;
    private Valeur valeur;

    public Carte (Valeur valeur, Couleur couleur) {
	this.setCouleur(couleur);
	this.setValeur(valeur);
    }

    public Couleur getCouleur() {
	return couleur;
    }

    public void setCouleur(Couleur couleur) {
		    this.couleur = couleur;
	}

    public Valeur getValeur() {
	return valeur;
    }

    public void setValeur(Valeur valeur) {
	    this.valeur = valeur;
    }

    public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append(this.valeur);
	sb.append(" de ");
	sb.append(this.couleur);
	return sb.toString();
    }   
	
    public static void main(String[]args){
    	Carte c1=new Carte(Valeur.DIX, Couleur.COEUR);
    	Carte c2=new Carte(Valeur.ROI, Couleur.COEUR);   
    	
    	System.out.println(c1);
    	System.out.println(c2);
    	
    	// la méthode ordinal() renvoie la position de l'élèment dans l'énumération
    	// ici la valeur DIX renvoie 3, la valeur ROI renvoie 6 et ainsi de suite
    	System.out.println(c1.getValeur() );
    	System.out.println(c2.getValeur() );
    	System.out.println(c1.getValeur().ordinal());
    	System.out.println(c2.getValeur().ordinal());
    	
    	// Comment récupérer les éléments d'une énumération?
		Valeur[] v=Valeur.values();
		for(int i=0 ; i < v.length; i++){
			System.out.println(v[i]);
		}
    	
    	// compareTo: 
    	//Returns a negative integer, zero, or a positive integer ...
    	//as this object is less than, equal to, or greater than the specified object.    	
		System.out.println(c1.getValeur().compareTo(c2.getValeur()))  ;

    }
}

