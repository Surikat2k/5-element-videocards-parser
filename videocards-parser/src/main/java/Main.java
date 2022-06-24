import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("https://5element.by/catalog/1675-videokarty?sort=cheap&items=100").get();
		Elements videocards = doc.getElementsByClass("catalog-item");
		
		JSONArray arr = new JSONArray();
		videocards.forEach(e -> {
			try {
				arr.add((JSONObject) JSONValue.parseWithException(e.attr("data-ec_product")));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		});
		
		List<Videocard> videocardList = new ArrayList<Videocard>();
		for (Object obj : arr.toArray()) {
			JSONObject localObj = (JSONObject) obj;
			videocardList.add(new Videocard(localObj.get("id").toString(), 
					localObj.get("brand").toString(), 
					localObj.get("name").toString(), 
					localObj.get("price_old").toString(), 
					localObj.get("price").toString(), 
					localObj.get("url").toString(), 
					localObj.get("image").toString()));
		}
		
//		videocardList.forEach(e -> System.out.println(e.toString()));
		
		createXlsx(videocardList);
		
	}
	
	public static void createXlsx(List<Videocard> videocards) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("5element videocards");

        List<Videocard> videocardList = videocards;

        int rownum = 0;
        Cell cell;
        Row row;
        
        CreationHelper helper = workbook.getCreationHelper();
        Drawing drawing = sheet.createDrawingPatriarch();
        
        XSSFCellStyle style = CellStyles.createDefaultStyle(workbook);
        XSSFCellStyle titleStyle = CellStyles.createStyleForTitle(workbook);
        XSSFCellStyle idTitleStyle = titleStyle.copy();
        idTitleStyle.setAlignment(HorizontalAlignment.RIGHT);
        XSSFCellStyle priceStyle = CellStyles.createStyleForPrice(workbook);
        XSSFCellStyle formulaStyle = CellStyles.createStyleForFormula(workbook);
        XSSFCellStyle oldPriceStyle = CellStyles.createStyleForOldPrice(workbook);
        XSSFCellStyle hyperlinksStyle = CellStyles.createStyleForHyperlinks(workbook);
        
        for (Videocard videocard : videocardList) {
        	
        	Hyperlink link = helper.createHyperlink(HyperlinkType.URL);
        	link.setAddress(videocard.getUrl());
        	
        	ClientAnchor anchor = helper.createClientAnchor();
        	anchor.setAnchorType( ClientAnchor.AnchorType.MOVE_AND_RESIZE );
        	InputStream stream = new URL(videocard.getImage()).openStream();
        	int pictureIndex = workbook.addPicture(IOUtils.toByteArray(stream), Workbook.PICTURE_TYPE_JPEG);

        	row = sheet.createRow(rownum);
        	row.setHeightInPoints((float) 7.5);
        	sheet.setColumnWidth(0, PixelUtil.pixel2WidthUnits(10));
        	sheet.setColumnWidth(5, PixelUtil.pixel2WidthUnits(70));
	        sheet.setColumnWidth(6, PixelUtil.pixel2WidthUnits(100));
	        sheet.setColumnWidth(8, PixelUtil.pixel2WidthUnits(75));
	        rownum++;
	        row = sheet.createRow(rownum);
	        // Image
	        anchor.setCol1(1);
	        anchor.setCol2(5);
	        anchor.setRow1(rownum);
	        anchor.setRow2(rownum+8);
	        Picture image = drawing.createPicture(anchor, pictureIndex);
	        // Brand
	        cell = row.createCell(5, CellType.STRING);
	        cell.setCellValue(videocard.getBrand()); 
	        cell.setCellStyle(titleStyle);
	        // ID title
	        cell = row.createCell(7, CellType.STRING);
	        cell.setCellValue("ID:");
	        cell.setCellStyle(idTitleStyle);
	        // ID
	        cell = row.createCell(8, CellType.NUMERIC);
	        cell.setCellValue(videocard.getId());
	        cell.setCellStyle(titleStyle);
	        // Next row
	        rownum++;
	        row = sheet.createRow(rownum);
	        // Name + link
	        cell = row.createCell(5, CellType.STRING);
	        cell.setCellValue(videocard.getName());
	        cell.setHyperlink(link); 
	        cell.setCellStyle(hyperlinksStyle);
	        sheet.addMergedRegion(CellRangeAddress.valueOf("F" + (rownum + 1) + ":R" + (rownum + 1)));
	        // Next row
	        rownum++;
	        row = sheet.createRow(rownum);
	        // Price title
	        cell = row.createCell(5, CellType.STRING);
	        cell.setCellValue("Цена: ");
	        cell.setCellStyle(style);
	        // Actual price
	        cell = row.createCell(6, CellType.NUMERIC);
	        cell.setCellValue(videocard.getPrice());
	        cell.setCellStyle(priceStyle);
	        // Old price
	        cell = row.createCell(7, CellType.NUMERIC);
	        cell.setCellValue(videocard.getPriceOld());
	        cell.setCellStyle(oldPriceStyle);
	        // Next row
	        rownum++;
	        row = sheet.createRow(rownum);
	        // Discount title
	        cell = row.createCell(5, CellType.STRING);
	        cell.setCellValue("Скидка:");
	        cell.setCellStyle(style);
	        // Discount
	        String formula = "(G" + (rownum) + "/H" + (rownum) + ") / 100";
	        cell = row.createCell(6, CellType.FORMULA);
	        cell.setCellFormula(formula);
	        cell.setCellStyle(formulaStyle);
	        // + 3 blank row
	        rownum += 4;

		}
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
        String filePath = new StringBuilder(System.getProperty("user.dir"))
        		.append("/5element-vidocards-")
        		.append(dateFormat.format(new Date()))
        		.append(".xlsx")
        		.toString();
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        System.out.println("Created file: " + file.getAbsolutePath());
	}

}