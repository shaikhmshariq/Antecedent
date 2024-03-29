/**
 * 
 */
package com.sitegraph.web.controller;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.sitegraph.core.attributes.ImageAttributes;
import com.sitegraph.core.image.IImageThumbnailer;
import com.sitegraph.core.util.WebAppConstants;
import com.sitegraph.core.util.WebAppUtils;

@Controller
@RequestMapping("/images")
public class SnapController implements ServletContextAware{

	@Autowired(required=true)
	private IImageThumbnailer thumbnailer;
	private ServletContext servletContext;
	
	/**
	 * Generates image using default PNG image type and size.
	 * @param url URL of web page.
	 * @return
	 * @throws RemoteException 
	 */
	@RequestMapping(method=RequestMethod.GET, value="/makesnap/")
	public String makeSnap(@RequestParam("URL") String url,@RequestParam(value="latest",required=false,defaultValue="false") boolean latest) throws RemoteException{
		String response = "redirect:/";
		ImageAttributes imageAttribute = new ImageAttributes();
		imageAttribute.setUrl(url);
		imageAttribute.setImagePath(WebAppUtils.resolveImageStoragePath(imageAttribute, url));
		if(!latest && new File(imageAttribute.getImagePath()).exists()){
			String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, imageAttribute.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}else{
				if(thumbnailer.makeSnap(imageAttribute))
				{
					String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, imageAttribute.getUrl().toString());
					response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
				}
				
		}
		return response;
	}
	
	/**
	 * Generates image of imageType specified with default size.
	 * @param url URL of web page
	 * @param imageType
	 * @return
	 * @throws RemoteException 
	 */
	@RequestMapping(method=RequestMethod.GET, value="/makesnap/{imageType}")
	public String makeSnapOfType(@RequestParam("URL") String url,@PathVariable String imageType,@RequestParam(value="latest",required=false,defaultValue="false") boolean latest) throws RemoteException{
		ImageAttributes imageAttribute = new ImageAttributes();
		imageType = WebAppUtils.validateImageType(imageType);
		imageAttribute.setUrl(url);
		imageAttribute.setImageSuffix(imageType);
		imageAttribute.setImagePath(WebAppUtils.resolveImageStoragePath(imageAttribute, url));
		String response = "redirect:/";
		
		if(!latest && new File(imageAttribute.getImagePath()).exists()){
			String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, imageAttribute.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}else{
				if(thumbnailer.makeSnap(imageAttribute))
				{
					String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, imageAttribute.getUrl().toString());
					response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
				}
		}
		return response;
	}
	
	/**
	 * Generates image using provided parameters.
	 * @param url
	 * @param imageType
	 * @param imageWidth
	 * @param imageHeight
	 * @return
	 * @throws RemoteException 
	 */
	@RequestMapping(method=RequestMethod.GET, value="/makesnap/{imageType}/{imageWidth}/{imageHeight}")
	public String makeSnapOfSize(@RequestParam("URL") String url,@PathVariable String imageType,@PathVariable int imageWidth, @PathVariable int imageHeight,@RequestParam(value="latest",required=false,defaultValue="false") boolean latest) throws RemoteException{
		ImageAttributes imageAttribute = new ImageAttributes();
		imageType = WebAppUtils.validateImageType(imageType);
		imageAttribute.setUrl(url);
		imageAttribute.setImageSuffix(imageType);
		imageAttribute.setImagePath(WebAppUtils.resolveImageStoragePath(imageAttribute, url));
		imageAttribute.setImageHeight(imageHeight);
		imageAttribute.setImageHeight(imageHeight);
		imageAttribute.setImageSuffix(imageType);
		
		String response = "redirect:/";
		if(!latest && new File(WebAppUtils.resolveImageStoragePath(imageAttribute, url)).exists()){
			String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute,imageAttribute.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}else{
				if(thumbnailer.makeSnap(imageAttribute))
				{
					String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, imageAttribute.getUrl().toString());
					response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
				}
			}
		return response;
	}
	
	/**
	 * Generates mirror image of web page.
	 * @param url
	 * @return
	 * @throws RemoteException 
	 */
	@RequestMapping(method=RequestMethod.GET, value="/mirrorsnap/")
	public String makeMirrorSnap(@RequestParam("URL") String url,@RequestParam(value="latest",required=false,defaultValue="false") boolean latest) throws RemoteException{
		ImageAttributes imageAttribute = new ImageAttributes();
		imageAttribute.setMirrored(true);
		imageAttribute.setImagePath(WebAppUtils.resolveImageStoragePath(imageAttribute, url));
		String response = "redirect:/";
		if(!latest && new File(WebAppUtils.resolveImageStoragePath(imageAttribute, url)).exists()){
			String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, imageAttribute.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}else{
				if(thumbnailer.makeSnap(imageAttribute))
				{
					String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, imageAttribute.getUrl().toString());
					response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
				}
			}
		return response;
	}
	
	/**
	 * Generates mirror image of imageType provided.
	 * @param url
	 * @param imageType
	 * @return
	 * @throws RemoteException 
	 */
	@RequestMapping(method=RequestMethod.GET, value="/mirrorsnap/{imageType}")
	public String makeMirrorSnapOfType(@RequestParam("URL") String url,@PathVariable String imageType,@RequestParam(value="latest",required=false,defaultValue="false") boolean latest) throws RemoteException{
		ImageAttributes imageAttribute = new ImageAttributes();
		imageAttribute.setMirrored(true);
		imageType = WebAppUtils.validateImageType(imageType);
		imageAttribute.setImageSuffix(imageType);
		imageAttribute.setImagePath(WebAppUtils.resolveImageStoragePath(imageAttribute, url));
		String response = "redirect:/";
		if(!latest && new File(WebAppUtils.resolveImageStoragePath(imageAttribute, url)).exists()){
			String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, imageAttribute.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}else{
				if(thumbnailer.makeSnap(imageAttribute))
				{
					String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, imageAttribute.getUrl().toString());
					response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
				}
			}
		return response;
	}
	
	/**
	 * Generates mirror image of provided parameters.
	 * @param url
	 * @param imageType
	 * @param imageWidth
	 * @param imageHeight
	 * @return
	 * @throws RemoteException 
	 */
	@RequestMapping(method=RequestMethod.GET, value="/mirrorsnap/{imageType}/{imageWidth}/{imageHeight}")
	public String makeMirrorSnapOfSize(@RequestParam("URL") String url,@PathVariable String imageType,@PathVariable int imageWidth, @PathVariable int imageHeight,@RequestParam(value="latest",required=false,defaultValue="false") boolean latest) throws RemoteException{
		ImageAttributes imageAttribute = new ImageAttributes();
		imageAttribute.setMirrored(true);
		imageAttribute.setImageHeight(imageHeight);
		imageAttribute.setImageHeight(imageHeight);
		imageAttribute.setImagePath(WebAppUtils.resolveImageStoragePath(imageAttribute, url));
		imageType = WebAppUtils.validateImageType(imageType);
		imageAttribute.setImageSuffix(imageType);
		
		String response = "redirect:/";
		if(!latest && new File(WebAppUtils.resolveImageStoragePath(imageAttribute, url)).exists()){
			String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, imageAttribute.getUrl().toString());
			response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
		}else{
				if(thumbnailer.makeSnap(imageAttribute))
				{
					String generatedImage =  WebAppUtils.resolveImageWebPath(imageAttribute, imageAttribute.getUrl().toString());
					response = "redirect:/"+generatedImage+"?rand="+new Date().getTime();
				}
		}
		return response;
	}
	
	/**
	 * Default landing page.
	 * @param response
	 * @return
	 */
	@RequestMapping("/")
	@ResponseBody()
	public String homePage(HttpServletResponse response){
		response.setContentType("text/html");
		return "<title>ImageThumbnailer</title><h1>Welcome to SiteGraph</h1>Soon some meaningful contents will be here. ";
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		WebAppConstants.IMAGE_ABSOLUTE_PATH = this.servletContext.getRealPath("/") + WebAppConstants.IMAGE_ABSOLUTE_PATH; 
		System.out.println("Default path : "+ WebAppConstants.IMAGE_ABSOLUTE_PATH);
	} 
	
	/**
	 * @return
	 */
	public ServletContext getServletContext(){
		return this.servletContext;
	} 
}
