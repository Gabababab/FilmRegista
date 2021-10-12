package it.prova.test;

import it.prova.dao.*;
import it.prova.model.*;

public class TestDBFilmRegista {
	public static void main(String[]args) {
		
		FilmDao filmDaoInstance=new FilmDao();
		RegistaDao registaDaoInstance=new RegistaDao();
		
		//Test CRUD
		
//		Regista registaInsert=new Regista("Mario", "Verdi", 5);
//		registaDaoInstance.insert(registaInsert);
//	
//		Film filmInsert=new Film("Profondo rosso", "Horror", 120);
//		filmInsert.setRegista(registaDaoInstance.findById(1));
//		filmDaoInstance.insert(filmInsert);
//		
//		registaInsert=new Regista("Giuseppe", "Rossi", 12);
//		registaDaoInstance.insert(registaInsert);
//		filmInsert=new Film("Porco rosso", "animazione", 120);
//		filmInsert.setRegista(registaDaoInstance.findById(1));
//		filmDaoInstance.insert(filmInsert);
//		System.out.println(filmDaoInstance.list());
		
		

		//TEst regista con piu oscar
		System.out.println(registaDaoInstance.findChiConPiuOscar());
		
		
	}
}
