package dataProviders;

import java.io.File;

import org.testng.annotations.DataProvider;

import utilities.DataReaders;

public class RegisterDataProviders {

	String registerDataPath=System.getProperty("user.dir")+File.separator+"testDatas"+File.separator+"LumaStoreTestDatas.xlsx";
	
	@DataProvider(name="ValidInfos")
	public String[][] validAccountData(){
		
		String sheetName="ValidAccountInfos";
		return readData(sheetName);
	}
	
	@DataProvider(name="InvalidConPassInfo")
	public String[][] invalidConPassData(){
		
		String sheetName="InvalidConfirmPass";
		return readData(sheetName);
	}
	
	@DataProvider(name="TrailingLeadingPassInfo")
	public String[][]trailingLeadingPassData(){
	
		String sheetName="TrailingLeadingPass";
		return readData(sheetName);
	}
	
	@DataProvider(name="PassMinimumLengthInfo")
	public String[][] passMinimumLengthData(){
		
		String sheetName="PassMinimumLength";
		return readData(sheetName);
	}
	
	@DataProvider(name="MinimumPassCharacterInfo")
	public String[][] passMinimumSymbolData(){
		String sheetName="PassMinimumCharacters";
		return readData(sheetName);
	}
	
	@DataProvider(name="NewsLetterSignUpInfo")
	public String[][] newsLetterSignUoadata(){
		String sheetName="NewsLetterSignUp";
		return readData(sheetName);
	}
	
	@DataProvider(name="InvalidFormatEmailInfo")
	public String[][] invalidFormatEmailData(){
		String sheetName="InvalidFormatEmail";
		return readData(sheetName);
	}
	
	@DataProvider(name="TrailingLeadingNameInfo")
	public String[][] TrailingLeadingNameData(){
		String sheetName="TrailingLeadingName";
		return readData(sheetName);
	}
	
	@DataProvider(name="EmptyFieldInfo")
	public String[][] EmptyFieldData(){
		String sheetName="EmptyFields";
		return readData(sheetName);
	}
	
	@DataProvider(name="ItemQuantity")
	public String[] setItemQuantityData() {
		String[] qty= {"-3","-1","-10","0"};
		return qty;
	}
	
	@DataProvider(name="EditItemQuantity")
	public String[][] editItemQuantity() {
		String[][] quantities= {{"0","-2","-5","10","3","04","+3","+4","6",
								"","abc","-abf3","$2","+mm3m","*-4"}};
		return quantities;
	}
	
	@DataProvider(name="EditItemQuantityFromProductPage")
	public String[][] EditItemQuantityFromProductPage() {
		String[][] qty= {{"2","1","4","6","09","04"}};
		return qty;
	}
	
	public String[][] readData(String sheetName){
		
		DataReaders reader=new DataReaders(registerDataPath);
		int rowcount=reader.getRowCount(sheetName);
		int cellcount=reader.getCellCount(sheetName, 1);
		String[][] datas=new String[rowcount][cellcount];
		
		for(int r=1; r<=rowcount; r++ ) {
			for(int c=0; c<cellcount; c++) {
				datas[r-1][c]=reader.getCellData(sheetName, r, c);
			}
		}
		return datas;
	}
}
