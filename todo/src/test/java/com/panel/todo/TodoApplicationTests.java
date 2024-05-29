package com.panel.todo;

import com.panel.todo.Entity.Item;
import com.panel.todo.Entity.ItemDetails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static com.panel.todo.Entity.PriorityItem.LOW;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TodoApplicationTests {

	@Test
	void contextLoads() {
	}
	RestTemplate restTemplate=new RestTemplate();

	@Test
	public void testFindById(){
		String url="http://localhost:8070/api/v1/item/findItem?id=52";
		ResponseEntity<Item> item=restTemplate.getForEntity(url,Item.class);
		assertEquals(item.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testAddItem(){
		String url="http://localhost:8070/api/v1/item/save";
		ItemDetails itemDetails=new ItemDetails("this is My Task",new Date("2021-5-6"),LOW,true);
		Item item=new Item("task1",itemDetails);
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1lZEBnbWFpbC5jb20iLCJleHAiOjE3MTcwMTA4NTIsImlhdCI6MTcxNzAwMDA1Mn0.Brgg-w4933WOQ3hnVd1RQoOAhGBfEdWXFnJ0SniNbJo";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<Item> response =restTemplate.exchange(url, HttpMethod.POST, entity, Item.class);

		System.out.println(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testUpdateItem(){
		String url="http://localhost:8070/api/v1/item/update";
		ItemDetails itemDetails=new ItemDetails("this is My Task",new Date("2021-5-6"),LOW,true);
		Item item=new Item("task2",itemDetails);
		restTemplate.put(url,item);
		System.out.println("updated");
	}

	@Test
	public void testDeleteItem(){
		String url="http://localhost:8070/api/v1/item/delete?id=2";
		restTemplate.delete(url);
		System.out.println("deleted");
	}

}
