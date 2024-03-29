/**
 * This class will hold utility methods to be used in web layer.
 */
package com.sitegraph.core.util;

import java.io.File;
import java.util.UUID;

import com.sitegraph.core.attributes.ImageAttributes;
import com.sitegraph.core.attributes.pdf.PdfAttributes;

public class WebAppUtils {

	/*
	 * This method returns Unique GUID for each input URL String
	 */
	public static String getUniquePath(String inputString){
		return UUID.nameUUIDFromBytes(inputString.getBytes()).toString()+"/";
	}
	
	/*
	 * This method is used to resolve image name based on its attributes.
	 */
	public static String resolveImageStoragePath(ImageAttributes imageAttributes,String inputUrl){
		String imageFilepath = WebAppConstants.IMAGE_ABSOLUTE_PATH+ getUniquePath(inputUrl);
		File domainDir = new File(imageFilepath);
		domainDir.mkdir();
		return  imageFilepath + WebAppConstants.DEFAULT_IMAGE_PREFIX + imageAttributes.getImageWidth() + "x" + imageAttributes.getImageHeight() + imageAttributes.getImageSuffix();
	}
	/*
	 * This method is used to resolve image relative path of server.
	 */
	public static String resolveImageWebPath(ImageAttributes imageAttributes,String inputUrl){
		return "/"+ WebAppConstants.DEFAULT_IMAGE_ABSOLUTE_PATH + getUniquePath(inputUrl) + WebAppConstants.DEFAULT_IMAGE_PREFIX + imageAttributes.getImageWidth() + "x" + imageAttributes.getImageHeight() + imageAttributes.getImageSuffix();
	}
	/*
	 * This method is used to resolve image name based on its attributes.
	 */
	public static String resolvePdfStoragePath(PdfAttributes pdfAttributes,String inputUrl){
		String pdfFilepath = WebAppConstants.PDF_ABSOLUTE_PATH+ getUniquePath(inputUrl);
		File domainDir = new File(pdfFilepath);
		domainDir.mkdir();
		return  pdfFilepath + WebAppConstants.DEFAULT_PDF_PREFIX + pdfAttributes.getPdfTemplateSize()+SiteGraphConstants.PDF_FILE_SUFFIX;
	}
	/*
	 * This method is used to resolve image relative path of server.
	 */
	public static String resolvePdfWebPath(PdfAttributes pdfAttributes,String inputUrl){
		return "/"+ WebAppConstants.DEFAULT_PDF_ABSOLUTE_PATH + getUniquePath(inputUrl) + WebAppConstants.DEFAULT_PDF_PREFIX + pdfAttributes.getPdfTemplateSize() + SiteGraphConstants.PDF_FILE_SUFFIX;
	}
	/*
	 * This method is used to validate Image Type
	 */
	public static String validateImageType(String imageSuffix){
		String result = "";
		String imageType= ".";
		imageType += imageSuffix;
		if(SiteGraphConstants.PNG_IMAGE_SUFFIX.equals(imageType))
			result = SiteGraphConstants.PNG_IMAGE_SUFFIX;
		else if(SiteGraphConstants.JPEG_IMAGE_SUFFIX.equals(imageType))
			result = SiteGraphConstants.JPEG_IMAGE_SUFFIX;
		else
			result = SiteGraphConstants.PNG_IMAGE_SUFFIX;
		return result;
		
	} 
}
