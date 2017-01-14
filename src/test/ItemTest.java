package test;

import static org.junit.Assert.*;
import org.junit.Test;
import filesystem.Item;

public class ItemTest {

	@Test
	public void testSetGetName() {
		Item item = new Item();

		item.setName("name");
		assertEquals("name", item.getName());

		item.setName("newname");
		assertEquals("newname", item.getName());
	}
}
