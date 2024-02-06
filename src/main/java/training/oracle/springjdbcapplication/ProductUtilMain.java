package training.oracle.springjdbcapplication;

import java.util.List;

public class ProductUtilMain {
	public static void main(String[] args) {
		ProductUtil util = new ProductUtilImpl();
		
		Product productToInsert = new Product(99,"Notebooks",500);
		Boolean saved = util.save(productToInsert);
		if(!saved) {
			System.out.println("The product already exists");
		} 
		
		util.update(new Product(99,"Monitor",88585));
		
		util.delete(new Product(33,"Monitor",88585));
		
		List<Product> listAfterInsertion = util.getAll();
		System.out.println("All products after insertion - " + listAfterInsertion);
		
		Product productById = util.get(77);
		System.out.println("Product by ID - " + productById);
		
		
	}
}
