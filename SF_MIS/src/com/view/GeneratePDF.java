package com.view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import com.db.DB;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class GeneratePDF {

	/*
	 * create file, create document, get PDF writer instance, open document, add
	 * content, close document
	 */

//	Image pdfIcon = new ImageIcon(this.getClass().getResource("/file.png")).getImage();
	java.awt.Image pdfIcon = new ImageIcon(this.getClass().getResource("/file.png")).getImage();

	public static void main(String[] args) {
		String file_name = "E:\\test\\testpdf.pdf";
		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(file_name));
			document.open();

			Paragraph para = new Paragraph("This is test for pdf generator.");

			document.add(para);

			// for whitespace in pdf
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));

			// adding table in pdf
			PdfPTable tbl = new PdfPTable(3);
			tbl.addCell("User id");
			tbl.addCell("Name");
			tbl.addCell("Address");

			// add image in pdf
			try {
				// E:\\Broadway\\workspace\\SF_MIS\\icons\\javalogo.png
				document.add(Image.getInstance("icons/file.png"));

				Connection con = (Connection) DB.getDBObject();

				ResultSet rs;
				try {
					PreparedStatement pst = (PreparedStatement) con.prepareStatement("select * from tbl_user");
					rs = pst.executeQuery();
					while (rs.next()) {
						Paragraph p = new Paragraph(
								rs.getString("user_id") + " " + rs.getString("name") + " " + rs.getString("address"));
						tbl.addCell(rs.getString("user_id"));
						tbl.addCell(rs.getString("name"));
						tbl.addCell(rs.getString("address"));

						document.add(new Paragraph(" "));
						document.add(p);
						document.add(new Paragraph(" "));
					}

					document.add(tbl);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			document.close();

			System.err.println("Writing in pdf finished. ");
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
