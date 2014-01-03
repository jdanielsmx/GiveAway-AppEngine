package com.example.giveaway;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class UploadImageHandler extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        //Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
    	Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
        List<BlobKey> blobKey = blobs.get("file");

        if (blobKey == null) {
            res.sendRedirect("/");
        } else {
            res.sendRedirect("/serve?blob-key=" + blobKey.get(0).getKeyString());
        }
    }
	
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
    	PrintWriter out = response.getWriter();        
        out.println(blobstoreService.createUploadUrl("/image/uploadImage"));
    }
    
    /*
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        BlobKey blobKey = ParameterExtractor.getBlobParameter(req, "blob-key", blobstoreService);
        if (blobKey == null) {
            log.info("blob Id is null. POST failed");
        } else {
            log.info("ze business logic");
        }
    }*/

}