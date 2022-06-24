import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CellStyles {
	
	static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
		XSSFFont font = workbook.createFont();
        font.setBold(true);
		font.setFontHeightInPoints((short) 16);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        return style;
    }
	
	static XSSFCellStyle createStyleForFormula(XSSFWorkbook workbook) {
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 14);
		XSSFCellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("0.00%"));
		style.setFont(font);
		return style;
	}	
	
	static XSSFCellStyle createStyleForHyperlinks(XSSFWorkbook workbook) {
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 14);
		font.setUnderline(Font.U_SINGLE);
		font.setColor(IndexedColors.BLUE.getIndex());
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		return style;
	}
	
	static XSSFCellStyle createStyleForPrice(XSSFWorkbook workbook) {
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 14);
		XSSFCellStyle style = workbook.createCellStyle();
		style.setDataFormat((short) 5);
		style.setFont(font);
		return style;
	}
	
	static XSSFCellStyle createStyleForOldPrice(XSSFWorkbook workbook) {
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setStrikeout(true);
		XSSFCellStyle style = workbook.createCellStyle();
		style.setDataFormat((short) 5);
		style.setFont(font);
		return style;
	}
	
	static XSSFCellStyle createDefaultStyle(XSSFWorkbook workbook) {
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 14);
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		return style;
	}
	
}