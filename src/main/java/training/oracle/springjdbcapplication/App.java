package training.oracle.springjdbcapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		DataSource ds = (DataSource) ctx.getBean("dataSource");
		JdbcTemplate template = new JdbcTemplate(ds);

		int count = template.queryForObject("select count(*) from product", Integer.class);
		System.out.println("Number of rows : " + count);

		String name = template.queryForObject("select name from product where productId=11", String.class);
		System.out.println("Name of rows : " + name);

		List<Map<String, Object>> listOfObjects = template.queryForList("select * from product order by pricae");
		System.out.println("List of rows : " + listOfObjects);

		final List<String> streamedObjects = new ArrayList<String>();
		listOfObjects.stream().forEach(mapObject -> {
			streamedObjects.add(mapObject.values().toString());
		});
		System.out.println("List of streamed names : " + streamedObjects);

		listOfObjects.stream().forEach(mapObject -> {
			System.out.println("Row - " + mapObject);
		});
		
		for(Map<String, Object> eachRecord : listOfObjects) {
			eachRecord.forEach((cname,cval) -> System.out.print(cval + "\t"));
			System.out.println();
		}
		
		for(Map<String, Object> eachRecord : listOfObjects) {
			System.out.print(eachRecord.get("name") + "\t" +
					eachRecord.get("productId") + "\t" + eachRecord.get("pricae"));
			System.out.println();
		}
		
		System.out.println("************************************************************");
		List<Product> listOfProducts = new ArrayList<>();
		List<Map<String, Object>> objectsList = template.queryForList("select * from product order by pricae");
		objectsList.forEach(obj -> {
			Product product = new Product((int) obj.get("productId"), 
					obj.get("name").toString(),(float) obj.get("pricae"));
//			product.setName(obj.get("name").toString());
//			product.setProductId((int) obj.get("productId"));
//			product.setPrice((float) obj.get("pricae"));
			listOfProducts.add(product);
			System.out.println("Product number " + product.getProductId() + "\t" + product);
			
		});
		
		System.out.println("List of all products - " + listOfProducts);
		
//		Product mostExpensiveproduct = template.queryForObject("SELECT * FROM product ORDER BY pricae DESC LIMIT 1", RowMapper<T>);
//		System.out.println("Most expensive product - " + mostExpensiveproduct);
		
		ctx.close();
	}
}
