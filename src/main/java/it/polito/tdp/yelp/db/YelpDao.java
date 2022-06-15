package it.polito.tdp.yelp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Review;
import it.polito.tdp.yelp.model.User;

public class YelpDao {

	public List<Business> getAllBusiness() {
		String sql = "SELECT * FROM Business";
		List<Business> result = new ArrayList<Business>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Business business = new Business(res.getString("business_id"), res.getString("full_address"),
						res.getString("active"), res.getString("categories"), res.getString("city"),
						res.getInt("review_count"), res.getString("business_name"), res.getString("neighborhoods"),
						res.getDouble("latitude"), res.getDouble("longitude"), res.getString("state"),
						res.getDouble("stars"));
				result.add(business);
			}
			res.close();
			st.close();
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Review> getAllReviews() {
		String sql = "SELECT * FROM Reviews";
		List<Review> result = new ArrayList<Review>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Review review = new Review(res.getString("review_id"), res.getString("business_id"),
						res.getString("user_id"), res.getDouble("stars"), res.getDate("review_date").toLocalDate(),
						res.getInt("votes_funny"), res.getInt("votes_useful"), res.getInt("votes_cool"),
						res.getString("review_text"));
				result.add(review);
			}
			res.close();
			st.close();
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<User> getAllUsers() {
		String sql = "SELECT * FROM Users";
		List<User> result = new ArrayList<User>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				User user = new User(res.getString("user_id"), res.getInt("votes_funny"), res.getInt("votes_useful"),
						res.getInt("votes_cool"), res.getString("name"), res.getDouble("average_stars"),
						res.getInt("review_count"));

				result.add(user);
			}
			res.close();
			st.close();
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getAllCity() {
		List<String> ris = new ArrayList<>();
		String sql = "SELECT DISTINCT(city) FROM business";
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				ris.add(res.getString("city"));
			}
			res.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ris;
	}

	public List<Business> listCityYear(String city, int year) {
		List<Business> ris = new ArrayList<Business>();
		String sql = "SELECT DISTINCT(b.business_id), B.* FROM Business B, Reviews R "
				+ "WHERE B.business_id = r.business_id AND YEAR(R.review_date) = ? AND B.city = ?";
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			st.setString(2, city);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Business business = new Business(res.getString("business_id"), res.getString("full_address"),
						res.getString("active"), res.getString("categories"), res.getString("city"),
						res.getInt("review_count"), res.getString("business_name"), res.getString("neighborhoods"),
						res.getDouble("latitude"), res.getDouble("longitude"), res.getString("state"),
						res.getDouble("stars"));
				ris.add(business);
			}
			res.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ris;
	}

	/*
	 * 0wRPvS-sG5x-pEMKVuDBJg 5NyNCV8gy_UCVr08zStn4w HAKDE_lETkIj_JJYLBh3sQ
	 * k41DURkrKj8_fSyRIxQtcA ltAPE16jLxC-BAnHhcI9CQ T2xiF9kMc_WRH0GeKAXC-g
	 * ZgT_GjPHjwzk7l04xU-u_A
	 */

	public double getPeso(Business b1, Business b2, int year) {
		double ris = 0;
		String sql = "SELECT AVG(r1.stars) - AVG(r2.stars) AS peso FROM Reviews r1, Reviews r2 WHERE YEAR(r1.review_date) = YEAR(r2.review_date) "
				+ "AND YEAR(r1.review_date) = ? " + "AND r1.business_id = ? " + "AND r2.business_id = ?";
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			st.setString(2, b1.getBusinessId());
			st.setString(3, b2.getBusinessId());
			ResultSet res = st.executeQuery();
			while (res.next()) {
				ris = res.getDouble("peso");
			}
			res.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ris;
	}
}
