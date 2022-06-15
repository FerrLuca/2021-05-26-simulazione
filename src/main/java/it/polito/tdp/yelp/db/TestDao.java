package it.polito.tdp.yelp.db;

public class TestDao {

	public static void main(String[] args) {
		YelpDao dao = new YelpDao();
		System.out.println(dao.listCityYear("Wickenburg", 2009));
		
	}

}
