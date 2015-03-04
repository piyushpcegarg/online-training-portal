/**
 * This file stores user image into org_user_image_mst for first time for super administrator user.
 */
package com.gargorg.common.Utils;

/**
 * @author piyush.garg
 *
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class ImageInsertionUtility {
  public static void main(String[] args) throws Exception, IOException, SQLException {
	  
	// For MySQL database
	  
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/tportal", "root", "password");
    
    // For Oracle database
    /*
    Class.forName("oracle.jdbc.driver.OracleDriver");
    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "tportal", "tportal");
    */
    
    
    String INSERT_PICTURE = "insert into org_user_image_mst(USER_IMAGE_ID,USER_ID,IMAGE,ACTIVATE_FLAG,CREATED_USER_ID,CREATED_DATE) values(?,?,?,?,?,?)";

    FileInputStream fis = null;
    PreparedStatement ps = null;
    Scanner sc = null;
    try {
      conn.setAutoCommit(false);
      // Get current directory of project - Start
      File currDir = new File(".");
      String path = currDir.getAbsolutePath();
      path = path.substring(0, path.length()-1);
      // Get current directory of project - End
      System.out.println(path);
      System.out.println("Convert above path by replacing single backward slash by double backward slash");
      System.out.println("Enter Modified Path:\n");
      sc = new Scanner(System.in);
      String modifiedPath = sc.nextLine();
      File file = new File(modifiedPath+"web\\resources\\images\\user_image.jpg");
      fis = new FileInputStream(file);
      ps = conn.prepareStatement(INSERT_PICTURE);
      ps.setLong(1, 1L);
      ps.setLong(2, 1L);
      ps.setBinaryStream(3, fis, (int) file.length());
      ps.setLong(4, 1L);
      ps.setLong(5, 1L);
      Date javaDate = new Date();
      ps.setDate(6, new java.sql.Date(javaDate.getTime()));
      ps.executeUpdate();
      conn.commit();
      System.out.println("Image successfully inserted.");
    } finally {
    	if(fis != null)
    		fis.close();
    	if(ps != null)
    		ps.close();
    	if(conn != null)
    		conn.close();
    	if(sc != null)
    		sc.close();
    }
  }
}

           
        