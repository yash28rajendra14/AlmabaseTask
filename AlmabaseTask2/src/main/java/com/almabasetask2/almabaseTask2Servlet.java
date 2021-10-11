package com.almabasetask2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class almabaseTask2Servlet
 */
@WebServlet("/almabaseTask2Servlet")
public class almabaseTask2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static HttpURLConnection connection;
	public static JSONArray albums;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public almabaseTask2Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		BufferedReader reader;
	    String line;
	    StringBuffer responseContent = new StringBuffer();
	    
	    
		try {
			URL url =new URL("https://ab-solution-engineer-task.s3.ap-southeast-1.amazonaws.com/list.txt");
			connection = (HttpURLConnection) url.openConnection();
			
			//Request Setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			int status = connection.getResponseCode();
			//System.out.println(status);
			
			if(status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine())!= null) {
					responseContent.append(line);
				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				responseContent.append("[");
				while((line = reader.readLine())!= null) {
					responseContent.append(line);
					responseContent.append(",");
				}
				responseContent.setLength(responseContent.length() - 1);				
				responseContent.append("]");
				reader.close();
			}
			parse(responseContent.toString());
			//System.out.println(responseContent.toString());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}		
		response.setContentType("application/json");
		response.getWriter().append(albums.toString());
	}
	public static void parse(String responseBody) throws Exception {
		albums = new JSONArray(responseBody);
		for(int i=0;i<albums.length();i++) {
			JSONObject album = albums.getJSONObject(i);
			String name = album.getString("name");
			String email = album.getString("email");
			String image = album.getString("image");
			
			//javaMailUtil.sendMail("yashrathoreyashrathore@gmail.com");
			
		}
		
	}
	
	private static String getGreeting(String string) {
		// TODO Auto-generated method stub
		BufferedReader reader;
	    String line;
	    StringBuffer responseContent = new StringBuffer();
	    
		try {
			URL url =new URL(string);
			connection = (HttpURLConnection) url.openConnection();
			
			//Request Setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			int status = connection.getResponseCode();
			//System.out.println(status);
			
			if(status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine())!= null) {
					responseContent.append(line);
				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));				
				while((line = reader.readLine())!= null) {
					responseContent.append(line);
					//System.out.println(line);
				}
				//System.out.println(responseContent.toString());
				reader.close();
			}
			return responseContent.toString();
			//System.out.println(responseContent.toString());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return null;
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
