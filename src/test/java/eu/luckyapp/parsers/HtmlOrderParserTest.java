package eu.luckyapp.parsers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import eu.luckyapp.mypdf.model.Order;

public class HtmlOrderParserTest {
	private HtmlOrderParser parser;
	private String text;

	@Before
	public void setUp() throws Exception {
		System.out.println(this.getClass().getClassLoader().getResource("").getPath());

		parser = new HtmlOrderParser();

		Path file = Paths.get("d:///4500450384.htm");// 4500413761.htm");//4500413690.htm");//4500413761.htm
														// 4500413690.htm
		text = new String(Files.readAllBytes(file));

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@org.junit.Ignore
	public void testParse() {
		try {
			parser.parse(text);
			// System.out.println(parser.toString());
		} catch (ParserException e) {
			System.out.println("parser eroor");
			e.printStackTrace();
		}

	}

	@Ignore
	@Test
	public void testParseToTableView() throws ParserException {
		// try {
		System.out.println(text);

		Order o = parser.parseToOrder(text);
		System.out.println(o);
		// System.out.println(parser.parseToTableView(text).toString());

		// System.out.println(parser.toString());
		// } catch (ParserException e) {
		// System.out.println("parser error");
		// e.printStackTrace();
		// }
	}

	@Test
	public void testParseToOrder() throws ParserException {
		Order o = parser.parseToOrder(text);
		System.out.println("to jest to " + o);
	}

}
