package eu.luckyapp.parsers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HtmlOrderParserTest {
	private HtmlOrderParser parser;
	private String text;

	@Before
	public void setUp() throws Exception {
		System.out.println(this.getClass().getClassLoader().getResource("")
				.getPath());

		parser = new HtmlOrderParser();

		Path file = Paths.get("/home/luke/Pobrane/4500413690.htm");//4500413761.htm");//4500413690.htm");//4500413761.htm  4500413690.htm
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
			//System.out.println(parser.toString());
		} catch (ParserException e) {
			System.out.println("parser eroor");
			e.printStackTrace();
		}



	}
	
	
	@Test
	public void testParseToTableView(){
	//	try {
			System.out.println(text);
			
		//	System.out.println(parser.parseToTableView(text).toString());
			
			//System.out.println(parser.toString());
	//	} catch (ParserException e) {
	//		System.out.println("parser error");
	//		e.printStackTrace();
	//	}
	}

}
