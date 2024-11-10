package org.crazyit.activiti.oa;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class TestJDBC {

	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/graduation_oa", "root", "123456");
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select BYTES_ from act_ge_bytearray where ID_ = 18");
		while(rs.next()) {                                                                                       
			byte[] bs = rs.getBytes(1);
			String s = new String(bs);
			System.out.println(s);
		}
		rs.close();
		stmt.close();
		conn.close();
	}

	private static byte[] getImgByte(InputStream is) throws IOException {
//		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
//		int b;
//		while ((b = is.read()) != -1) {
//			bytestream.write(b);
//		}
//		byte[] bs = bytestream.toByteArray();
//		bytestream.close();
//		return bs;
		
		
        BufferedInputStream bufin = new BufferedInputStream(is);  
        int buffSize = 1024;  
        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize); 
  
        byte[] temp = new byte[buffSize];  
        int size = 0;  
        while ((size = bufin.read(temp)) != -1) {  
            out.write(temp, 0, size);  
        }  
        bufin.close();  
        is.close();  
        byte[] content = out.toByteArray();  
        out.close();  
        return content; 
	}
}
