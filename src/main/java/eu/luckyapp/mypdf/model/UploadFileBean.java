package eu.luckyapp.mypdf.model;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.http.Part;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import eu.luckyapp.mypdf.pdf.PdfTextExtractor;



@Model
public class UploadFileBean {

	private Part file;
	

	private String text;

	@Inject
	PdfTextExtractor pdfTextExtractor;

	public String uploadFile() throws IOException {
		System.out.println("Got file ...");

		//if (null != file) {
			System.out.println("... and trying to read it ...");
		//	 String serverPath= getServletContext().getRealPath("/"); 
			
/*			ServletContext context=(ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
			System.out.println("ścieżka: "+context.getRealPath("/"));*/
			
			try (InputStream is = file.getInputStream())

			{	
				//writeToFile(is, os);

				if (getFilename(file).endsWith(".pdf")) {
					//this.text = pdfTextExtractor.extract(is);
					this.text =pdfTextExtractor.extract(is);
				}

				return "success";
			} catch (SAXException | TikaException e) {
				e.printStackTrace();
			}

			
		//}

		return "error";
	}


	/**
	 * 
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private void writeToFile(InputStream is, OutputStream os)
			throws IOException {
		byte[] buffer = new byte[1024];
		int bytesRead = 0;
		
		while ((bytesRead = is.read(buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
	}

	public String getText() {
		System.out.println("Complete text: " + text);
		return text;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	
	/**
	 * 
	 * @param part
	 * @return file name 
	 */
	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1)
						.substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
		return null;
	}
}
