package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataReaders {
	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;   
	String path;
	
	public DataReaders(String path)
	{
		this.path=path;
	}
		
	public int getRowCount(String sheetName) 
	{
		int rowcount=0;
		try {
			fi=new FileInputStream(path); //Before running the code, a text file with path is required to be created. 
			workbook=new XSSFWorkbook(fi);
			sheet=workbook.getSheet(sheetName);
			rowcount=sheet.getLastRowNum();
			workbook.close();
			fi.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("File not Found");
			System.out.println("Error Message: "+e);
		}
		catch(IOException e) {
			System.out.println("IOException occured");
			System.out.println("Error Message: "+e);
		}
		catch(Exception e) {
			System.out.println("An Exception occured");
			System.out.println("Error Message: "+e);
		}
		
		return rowcount;		
	}
	
	public int getCellCount(String sheetName,int rownum) 
	{
		int cellcount=0;
		try {
			fi=new FileInputStream(path);
			workbook=new XSSFWorkbook(fi);
			sheet=workbook.getSheet(sheetName);
			row=sheet.getRow(rownum);
			cellcount=row.getLastCellNum();
			workbook.close();
			fi.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("File not Found");
			System.out.println("Error Message: "+e);
		}
		catch(IOException e) {
			System.out.println("IOException occured");
			System.out.println("Error Message: "+e);
		}
		catch(Exception e) {
			System.out.println("An Exception occured");
			System.out.println("Error Message: "+e);
		}
		
		return cellcount;	
	}
	
	
	public String getCellData(String sheetName,int rownum,int colnum) 
	{
		String data="";
		try {
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		DataFormatter formatter = new DataFormatter();
		data = formatter.formatCellValue(cell); //Returns the formatted value of a cell as a String regardless of the cell type.
		
		workbook.close();
		fi.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("File not Found");
			System.out.println("Error Message: "+e);
		}
		catch(IOException e) {
			System.out.println("IOException occured");
			System.out.println("Error Message: "+e);
		}
		catch(Exception e) {
			System.out.println("An Exception occured");
			System.out.println("Error Message: "+e);
		}
		
		return data;
	}
	
	public void setCellData(String sheetName,int rownum,int colnum,String data) 
	{
		try {
		File xlfile=new File(path);  // create an Object
		if(!xlfile.exists())    //return true or false
		{
		workbook=new XSSFWorkbook();
		fo=new FileOutputStream(path);
		workbook.write(fo);
		}
				
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
			
		if(workbook.getSheetIndex(sheetName)==-1) // If sheet not exists then create new Sheet
			workbook.createSheet(sheetName);
		sheet=workbook.getSheet(sheetName);
					
		if(sheet.getRow(rownum)==null)   // If row not exists then create new Row
				sheet.createRow(rownum);
		row=sheet.getRow(rownum);
		
		cell=row.createCell(colnum);
		cell.setCellValue(data);
		fo=new FileOutputStream(path);
		workbook.write(fo);		
		workbook.close();
		fi.close();
		fo.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("File not Found");
			System.out.println("Error Message: "+e);
		}
		catch(IOException e) {
			System.out.println("IOException occured");
			System.out.println("Error Message: "+e);
		}
		catch(Exception e) {
			System.out.println("An Exception occured");
			System.out.println("Error Message: "+e);
		}
		
	}
	
	
	public void fillGreenColor(String sheetName,int rownum,int colnum) 
	{
		try {
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		style=workbook.createCellStyle();
		
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
				
		cell.setCellStyle(style);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("File not Found");
			System.out.println("Error Message: "+e);
		}
		catch(IOException e) {
			System.out.println("IOException occured");
			System.out.println("Error Message: "+e);
		}
		catch(Exception e) {
			System.out.println("An Exception occured");
			System.out.println("Error Message: "+e);
		}
	}
	
	
	public void fillRedColor(String sheetName,int rownum,int colnum)
	{
		try {
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		style=workbook.createCellStyle();
		
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
		
		cell.setCellStyle(style);		
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("File not Found");
			System.out.println("Error Message: "+e);
		}
		catch(IOException e) {
			System.out.println("IOException occured");
			System.out.println("Error Message: "+e);
		}
		catch(Exception e) {
			System.out.println("An Exception occured");
			System.out.println("Error Message: "+e);
		}
	}
}
