package eu.luckyapp.mypdf.rs;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class UploadedFileMultiForm {

	
	private byte[] orderReference;
	private byte[] fileData;
	
	public byte[] getOrderReference() {
		return orderReference;
	}

	@FormParam("orderReference")
	public void setOrderReference(byte[] orderReference) {

		this.orderReference = orderReference;
	}

	public byte[] getFileData() {
		return fileData;
	}

	@FormParam("selectedFile")
	@PartType("application/octet-stream")
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
}
