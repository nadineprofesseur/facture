package magasin.modele;
import java.text.NumberFormat;

import magasin.outil.Journal;

public class Facture 
{
	protected float [] listeAchats;
	protected float sousTotal;
	protected float tps;
	protected float tvq;
	protected float total;

	// Fonction qui a le meme nom que la classe et qui n'a pas de type de retour
	public Facture() // Constructeur - appel� avec le new
	{
		this.sousTotal = 0;
		this.tps = 0;
		this.tvq = 0;
		this.total = 0;
		this.listeAchats = this.lireAchats();
	}
	
	public Facture(float [] listePrix)
	{		
		this.sousTotal = 0;
		this.tps = 0;
		this.tvq = 0;
		this.total = 0;
		this.listeAchats = listePrix;
	}
	
	public void journaliser()
	{
		// https://docs.oracle.com/javase/7/docs/api/java/text/NumberFormat.html
		NumberFormat imprimeurNombres = NumberFormat.getInstance();
		imprimeurNombres.setMaximumFractionDigits(2);
		imprimeurNombres.setMinimumFractionDigits(2);
		
		for(int numeroAchat = 0; numeroAchat < this.listeAchats.length; numeroAchat++)
		{
			float achat = this.listeAchats[numeroAchat];
			Journal.memoriser(imprimeurNombres.format(achat) + " $");
		}
		
		Journal.memoriser("--------------------");		
		Journal.memoriser("Sous-total:" + imprimeurNombres.format(this.sousTotal) + " $");
		Journal.memoriser("TPS:       " + imprimeurNombres.format(this.tps) + " $");
		Journal.memoriser("TVQ:       " + imprimeurNombres.format(this.tvq) + " $");
		Journal.memoriser("--------------------");		
		Journal.memoriser("Total:    " + imprimeurNombres.format(this.total) + " $");	
	}
	
	public Facture preparer()
	{
		this.sousTotal = calculerSousTotal(this.listeAchats);
		this.tps = calculerTaxeFederale(this.sousTotal);
		this.tvq = calculerTaxeProvinciale(this.sousTotal);
		this.total = this.sousTotal + this.tps + this.tvq;		
		return this;
	}

	
	public static float calculerTaxeFederale(float montant)
	{
		return montant * 0.05f;
	}

	public static float calculerTaxeProvinciale(float montant)
	{
		return montant * 0.09975f;
	}

	public static float calculerSousTotal(float[] listePrix)
	{
		float somme = 0;
		
		for(int position = 0; position < listePrix.length; position++)
		{
			somme += listePrix[position];
		}
		
		return somme;
	}

	public float [] lireAchats()
	{
		float [] listeAchats = new float[5];
		listeAchats[0] = 2.7f;
		listeAchats[1] = 6f;
		listeAchats[2] = 8.2f;
		listeAchats[3] = 4.25f;
		listeAchats[4] = 1.0f;		
		return listeAchats;
	}
	
}
