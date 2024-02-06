package training.oracle.springjdbcapplication;

import java.util.List;

public interface ProductUtil {
	
	boolean save(Product p);
	
	void update(Product p);
	
	boolean delete(Product p);
	
	Product get(int id);
	
	List<Product> getAll();
}
