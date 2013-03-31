/**
 * 
 */
package com.sitegraph.web.controller;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Date;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;

import com.sitegraph.core.attributes.pdf.PdfAttributes;
import com.sitegraph.core.pdf.IPdfThumbnailer;
import com.sitegraph.core.util.WebAppConstants;
import com.sitegraph.core.util.WebAppUtils;

@Controller
@RequestMapping("/pdf")
public class PdfController implements ServletContextAware{

	@Autowired(required=true)
	private IPdfThumbnailer thumbnailer;
	private ServletContext servletContext;
	
	/**
	 * Generates image using default PNG image type and size.
	 * @param url URL of web page.
	 * @return
	 * @throws RemoteException 
	 */
	@RequestMapping(method=RequestMethod.GET, value="/makepdf/")
	public String makePdf(@RequestParam("URL") String url,@RequestParam(value="latest",required=false,defaultValue="false") boolean latest) throws RemoteException{
		String response = "redirect:/";
		PdfAttributes pdfAttribute = new PdfAttributes();
		pdfAttribute.setUrl(url);
		pdfAttribute.setPdfPath(WebAppUtils.resolvePdfStoragePath(pdfAttribute, url));
		if(!latest && new File(pdfAttribute.getPdfPath()).exists()){
			String generatedImage =  WebAppUtils.resolvePdfWebPath(pdfAttribute, pdfAttribute.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}else{
				if(thumbnailer.makePdfFromUrl(pdfAttribute))
				{
					String generatedImage =  WebAppUtils.resolvePdfWebPath(pdfAttribute, pdfAttribute.getUrl().toString());
					response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
				}
				
		}
		return response;
	}
	
	/**
	 * Generates image using default PNG image type and size.
	 * @param url URL of web page.
	 * @return
	 * @throws RemoteException 
	 */
	@RequestMapping(method=RequestMethod.POST, value="/makepdf/")
	public String makePdf(@RequestParam("HTML") String html) throws RemoteException{
		long handler = new Date().getTime();
		PdfAttributes pdfAttribute = new PdfAttributes();
		pdfAttribute.setPdfPath(WebAppUtils.resolvePdfStoragePath(pdfAttribute, html));
		pdfAttribute.setHtml(html);
		String response = "redirect:/";
		
		
		if(thumbnailer.makePdfFromHTML(pdfAttribute,WebAppUtils.getUniquePath(pdfAttribute.getHtml())))
		{
			String generatedImage =  WebAppUtils.resolvePdfWebPath(pdfAttribute, String.valueOf(handler));
			response = "redirect:/"+generatedImage+"?rand="+handler;
		}	
		
		return response;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		WebAppConstants.PDF_ABSOLUTE_PATH = this.servletContext.getRealPath("/") + WebAppConstants.PDF_ABSOLUTE_PATH; 
		System.out.println("Default path : "+ WebAppConstants.PDF_ABSOLUTE_PATH);
	} 
	
	/**
	 * @return
	 */
	public ServletContext getServletContext(){
		return this.servletContext;
	} 
}