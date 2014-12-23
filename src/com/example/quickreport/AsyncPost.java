package com.example.quickreport;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;


public class AsyncPost
{
	public static boolean done;
    public static void post( //with filePath
        final String url,
        final String description,
        final String filePath,
        final double lattitude,
        final double longitude
    )
    {
        post(
            url,
            description,
            new File(filePath),
            lattitude,
            longitude
        );
    }
    
    public static void post( //with file
        final String url,
        final String description,
        final File file,
        final double lattitude,
        final double longitude
    )
    {
        (new Thread() //calling perform() in new inline thread
        {
            @Override
            public void run() 
            {
                perform(url,description,file, lattitude, longitude);
            }
        }).start();
    }
    
    public static void perform(
        String url,
        String description,
        File file,
        double lattitude,
        double longitude
    )
    {
    	done = false;
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url); //POST request
        MultipartEntityBuilder builder = MultipartEntityBuilder.create(); //used to add parameters
        
        builder.addPart("post[image]", new FileBody(file))
               .addTextBody("post[description]", description)
               .addTextBody("post[lattitude]", ""+lattitude)
               .addTextBody("post[longitude]", ""+longitude); //add all parameters
                
        HttpEntity ent = builder.build(); //build parameters
        post.setEntity(ent); //bind paramets to POST request
	
        try {client.execute(post);} //execute request
        catch(Exception e){/*TODO add some Log statements here}*/}
        done = true;
    }
}
