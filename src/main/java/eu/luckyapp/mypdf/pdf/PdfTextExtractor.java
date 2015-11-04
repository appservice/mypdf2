package eu.luckyapp.mypdf.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;


@ApplicationScoped
public class PdfTextExtractor {
	
	public String extract(String file) throws IOException, SAXException, TikaException{
		return	this.extract(new FileInputStream(new File(file)));
		
	}
	
	
	public String extract(InputStream inputstream) throws IOException, SAXException, TikaException{
		   BodyContentHandler handler = new BodyContentHandler();

		 Metadata metadata = new Metadata();
	
	      ParseContext pcontext = new ParseContext();
	      
	      //parsing the document using PDF parser
	      PDFParser pdfparser = new PDFParser(); 
	     pdfparser.parse(inputstream, handler, metadata,pcontext);
		return  handler.toString();
		
	}

}
