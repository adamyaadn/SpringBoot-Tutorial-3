package training.oracle.springjdbcapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProductUtilImpl implements ProductUtil{

	JdbcTemplate template;
	public ProductUtilImpl() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		DataSource ds = (DataSource) ctx.getBean("dataSource");
		template = new JdbcTemplate(ds);
	}

	@Override
	public boolean save(Product p) {
		int pid = p.getProductId();
		String name = p.getName();
		Float price = (float) p.getPrice();
		
		try {
			template.update("insert into product values(?,?,?)",pid,name,price);
		} catch(DuplicateKeyException ex) {
			return false;
		}
		
		return true;
	}

	@Override
	public void update(Product p) {
		int pid = p.getProductId();
		String name = p.getName();
		Float price = (float) p.getPrice();
		
		try {
			template.update("update product set pricae=? where productId=?",price,pid);
		} catch(RuntimeException ex) {
			System.out.println("Update failed");
		}
		
	}

	@Override
	public boolean delete(Product p) {
		int pid = p.getProductId();

	    try {
	        template.update("DELETE FROM product WHERE productId=?", pid);
	        return true; 
	    } catch (RuntimeException ex) {
	        System.out.println("Delete failed");
	        return false; 
	    }
	}

	@Override
	public Product get(int id) {
		 Product product = null;

		    List<Map<String, Object>> objectsList = template.queryForList("SELECT * FROM product WHERE productId=?", id);

		    if (!objectsList.isEmpty()) {
		        Map<String, Object> row = objectsList.get(0);
		        int productId = (int) row.get("productId");
		        String name = row.get("name").toString();
		        float price = ((Number) row.get("pricae")).floatValue();
		        product = new Product(productId, name, price);
		    }

		    return product;
	}

	@Override
	public List<Product> getAll() {
		List<Product> listOfProducts = new ArrayList<>();
		List<Map<String, Object>> objectsList = template.queryForList("select * from product");
		objectsList.forEach(obj -> {
			Product product = new Product((int) obj.get("productId"), 
					obj.get("name").toString(),(float) obj.get("pricae"));
			listOfProducts.add(product);
		});
		
		return listOfProducts;
	}

}
