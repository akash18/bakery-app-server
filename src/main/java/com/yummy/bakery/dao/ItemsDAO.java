package com.yummy.bakery.dao;

import java.util.List;

import com.yummy.bakery.entity.Product;
import org.hibernate.Criteria;
/*import org.hibernate.Query;
import org.hibernate.Session;
*/
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ItemsDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<Product> getItems() {
		
		/*Session currentSession = sessionFactory.getCurrentSession();
		Query<Product> theQuery = 
				currentSession.createQuery("from product", Product.class);
		*/
		
		Criteria criteria = sessionFactory.openSession().createCriteria(Product.class);
		return criteria.list();
	}

}
