package com.example.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 	excel  解析有单元格合并的数据（基于POI 4.0.1）
 */
public class ExcelParseMoreUtils {

	/**
	 * 获取单元格的值
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		if (cell == null)
			return "";
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == CellType.BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == CellType.FORMULA) {
			return cell.getCellFormula();
		} else if (cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		}
		return "";
	}

	/**
	 *  获取单元格 日期是时间值
	 * @param cell
	 * @return
	 */
	public static LocalDate getLocalDate(Cell cell) {
		if(cell == null) {
			return null;
		} else if(cell.getCellType() == CellType.NUMERIC) {
			return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		} else if(cell.getCellType() == CellType.STRING) {
			String dateString=cell.getStringCellValue();
			return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		} else {
			return null;
		}
	}

	/**
	 * 合并单元格处理,获取合并行
	 * @param sheet
	 * @return List<CellRangeAddress>
	 */
	public static List<CellRangeAddress> getCombineCell(Sheet sheet) {
		List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
		// 获得一个 sheet 中合并单元格的数量
		int sheetmergerCount = sheet.getNumMergedRegions();
		// 遍历所有的合并单元格
		for (int i = 0; i < sheetmergerCount; i++) {
			// 获得合并单元格保存进list中
			CellRangeAddress ca = sheet.getMergedRegion(i);
			list.add(ca);
		}
		return list;
	}

	public static int getRowNum(List<CellRangeAddress> listCombineCell, Cell cell) {
		int xr = 0;
		int firstC = 0;
		int lastC = 0;
		int firstR = 0;
		int lastR = 0;
		for (CellRangeAddress ca : listCombineCell) {
			// 获得合并单元格的起始行, 结束行, 起始列, 结束列
			firstC = ca.getFirstColumn();
			lastC = ca.getLastColumn();
			firstR = ca.getFirstRow();
			lastR = ca.getLastRow();
			if (cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR) {
				if (cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC) {
					xr = lastR;
				}
			}

		}
		return xr;

	}

	/**
	 * 判断单元格是否为合并单元格，是的话则将单元格的值返回
	 * 
	 * @param listCombineCell 存放合并单元格的list
	 * @param cell            需要判断的单元格
	 * @param sheet           sheet
	 * @return
	 */
	public static String isCombineCell(List<CellRangeAddress> listCombineCell, Cell cell, Sheet sheet) throws Exception {
		int firstC = 0;
		int lastC = 0;
		int firstR = 0;
		int lastR = 0;
		String cellValue = null;
		for (CellRangeAddress ca : listCombineCell) {
			// 获得合并单元格的起始行, 结束行, 起始列, 结束列
			firstC = ca.getFirstColumn();
			lastC = ca.getLastColumn();
			firstR = ca.getFirstRow();
			lastR = ca.getLastRow();
			if (cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR) {
				if (cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC) {
					Row fRow = sheet.getRow(firstR);
					Cell fCell = fRow.getCell(firstC);
					cellValue = getCellValue(fCell);
					break;
				}
			} else {
				cellValue = "";
			}
		}
		return cellValue;
	}

	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell);
				}
			}
		}

		return null;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row    行下标
	 * @param column 列下标
	 * @return
	 */
	public static boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
     * @Title: createWorkBook
     * @Description: 通过传入的文件返回一个Excel工作簿（03或者07版本）
     * @param file
     * @return Workbook
     * @throws
     */
    public static Workbook createWorkBook(File file) {
        Workbook workbook = null;
        FileInputStream fis =null;
        try {
            //获取一个绝对地址的流
            fis = new FileInputStream(file);
            //2003版本的excel，用.xls结尾
            workbook = new HSSFWorkbook(fis);//得到工作簿
        } catch (Exception ex) {
            try{
                //2007版本的excel，用.xlsx结尾
                fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);//得到工作簿
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return workbook;
    }
	static List<Map<String,Object>> results=new ArrayList<>();
	
	public static void getLoan(File file) {
		Workbook wb  = null;
		wb=createWorkBook(file);
		Sheet sheet = wb.getSheetAt(0);    //获得第一个表单
		int rowCount = sheet.getLastRowNum()+1;//总行数
		List<CellRangeAddress> cras = getCombineCell(sheet); //所有单元格
		int colCount;
		int rowRangeStart,rowRangeEnd,colRangeStart,colRangeEnd;
		for(int i = 3; i < rowCount;i++){
			Row row = sheet.getRow(i);
			rowRangeStart=i;
			Map<String,Object> map=new HashMap<>();
			map.put("序号", getCellValue(row.getCell(0)));
			map.put("分行", getCellValue(row.getCell(1)));
			map.put("银团类型", getCellValue(row.getCell(2)));
			map.put("项目名称", getCellValue(row.getCell(3)));
			map.put("项目所在地省级", getCellValue(row.getCell(4)));
			map.put("项目所在地地区", getCellValue(row.getCell(5)));
			map.put("借款人名称", getCellValue(row.getCell(6)));
			map.put("借款人所在地省级", getCellValue(row.getCell(7)));
			map.put("借款人所在地地区", getCellValue(row.getCell(8)));
			map.put("借款人所属行业", getCellValue(row.getCell(9)));
			map.put("合同币种", getCellValue(row.getCell(10)));
			map.put("银团合同总金额", getCellValue(row.getCell(11)));
			map.put("签约日期(YYYY-MM-DD)", getLocalDate(row.getCell(12)));
			map.put("贷款期限（月）", getCellValue(row.getCell(13)));
			map.put("贷款用途", getCellValue(row.getCell(14)));
			map.put("贷款利率合同约定利率", getCellValue(row.getCell(15)));
			map.put("贷款利率计息方式说明", getCellValue(row.getCell(16)));
			map.put("担保方式", getCellValue(row.getCell(17)));
			map.put("牵头行总行名称", getCellValue(row.getCell(18)));
			map.put("牵头行分行名称", getCellValue(row.getCell(19)));
			map.put("牵头行承贷金额", getCellValue(row.getCell(20)));
			//map.put("参加行总行名称", getCellValue(row.getCell(21)));
			//map.put("参加行分行名称", getCellValue(row.getCell(22)));
			//map.put("参加行承贷金额", getCellValue(row.getCell(23)));
			map.put("代理行总行名称", getCellValue(row.getCell(24)));
			map.put("代理行分行名称", getCellValue(row.getCell(25)));
			//map.put("收费金额安排费", getCellValue(row.getCell(26)));
			//map.put("收费金额代理费", getCellValue(row.getCell(27)));
			//map.put("收费金额承诺费", getCellValue(row.getCell(28)));
			//map.put("收费金额其他1", getCellValue(row.getCell(29)));
			//map.put("收费金额其他2", getCellValue(row.getCell(30)));
			//map.put("费率（%）安排费", getCellValue(row.getCell(31)));
			//map.put("费率（%）代理费", getCellValue(row.getCell(32)));
			//map.put("费率（%）承诺费", getCellValue(row.getCell(33)));
			//map.put("费率（%）其他1", getCellValue(row.getCell(34)));
			//map.put("费率（%）其他2", getCellValue(row.getCell(35)));
			map.put("是否为ppp融资项目", getCellValue(row.getCell(36)));
			//map.put("备注", getCellValue(row.getCell(37)));
			List<Map<String,String>> list=new ArrayList<>();
			if(isMergedRegion(sheet,i,0)) {
				int lastRow = getRowNum(cras,row.getCell(0));
				for(;i<=lastRow;i++){
    				row = sheet.getRow(i);
    				Map<String,String> m=new HashMap<>();
    				m.put("参加行总行名称", getCellValue(row.getCell(21)));
    				m.put("参加行分行名称", getCellValue(row.getCell(22)));
    				m.put("参加行承贷金额", getCellValue(row.getCell(23)));
    				m.put("收费金额安排费", getCellValue(row.getCell(26)));
    				m.put("收费金额代理费", getCellValue(row.getCell(27)));
    				m.put("收费金额承诺费", getCellValue(row.getCell(28)));
    				m.put("收费金额其他1", getCellValue(row.getCell(29)));
    				m.put("收费金额其他2", getCellValue(row.getCell(30)));
    				m.put("费率（%）安排费", getCellValue(row.getCell(31)));
    				m.put("费率（%）代理费", getCellValue(row.getCell(32)));
    				m.put("费率（%）承诺费", getCellValue(row.getCell(33)));
    				m.put("费率（%）其他1", getCellValue(row.getCell(34)));
    				m.put("费率（%）其他2", getCellValue(row.getCell(35)));
    				m.put("备注", getCellValue(row.getCell(37)));
    				list.add(m);
    			}
    			i--;
			} else {
				Map<String,String> m=new HashMap<>();
				m.put("参加行总行名称", getCellValue(row.getCell(21)));
				m.put("参加行分行名称", getCellValue(row.getCell(22)));
				m.put("参加行承贷金额", getCellValue(row.getCell(23)));
				m.put("收费金额安排费", getCellValue(row.getCell(26)));
				m.put("收费金额代理费", getCellValue(row.getCell(27)));
				m.put("收费金额承诺费", getCellValue(row.getCell(28)));
				m.put("收费金额其他1", getCellValue(row.getCell(29)));
				m.put("收费金额其他2", getCellValue(row.getCell(30)));
				m.put("费率（%）安排费", getCellValue(row.getCell(31)));
				m.put("费率（%）代理费", getCellValue(row.getCell(32)));
				m.put("费率（%）承诺费", getCellValue(row.getCell(33)));
				m.put("费率（%）其他1", getCellValue(row.getCell(34)));
				m.put("费率（%）其他2", getCellValue(row.getCell(35)));
				m.put("备注", getCellValue(row.getCell(37)));
				list.add(m);
			}
			map.put("list", list);
			results.add(map);
		}
	}
	
	public static void main(String[] args) {
		File file=new File("C:\\Users\\snipe\\Desktop\\working\\历史银团项目信息（案例）.xlsx");
		getLoan(file);
		System.out.println(results.toString());
	}
}
