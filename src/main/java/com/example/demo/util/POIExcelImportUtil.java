package com.example.demo.util;


import com.example.demo.Vo.SynLoanVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *   POI 对excel文件的批量导入
 */
public class POIExcelImportUtil {
    //存储到读取的数据
    private static List<SynLoanVo> synLoanVoList = null;
    //excel规定的格式（表头）
    public static String[] rowHeads = new String[]
            {"项目名称","客户名称","企业性质","牵头行", "省份","城市","银团分类",
             "银团阶段","业务品种","币种","银团总金额(元)","分销金额(元)","利率",
             "期限","增信措施","业务投向","邀请截止期","承诺截止期","协议截止期",
             "客户经理姓名","所属支行","电话","邮箱","项目介绍"};

    /**
     *  @decription 从传入的文件读取对应的信息
     *  @param request 请求对象
     *  @param fileName 文件名字
     *  @param mfile 请求的对象文件
     *  @return List 返回对应的信息
     */
    public static List<SynLoanVo> getDateFromExcel(HttpServletRequest request, String fileName , MultipartFile mfile){
        //将传入的文件进行类型转换
       //CommonsMultipartFile cfile = (CommonsMultipartFile)mfile;
        //存储暂时路径
        String path = request.getServletContext().getRealPath("/excelfile/");
        File file = new  File(path);
        //创建一个目录
        if (!file.exists()) file.mkdirs();
        //新建一个文件（临时存储上传的文件）
        File newFile = new File(path + new Date().getTime() +
                fileName.substring(fileName.lastIndexOf(".")));
        //建上传的文件copy一份到服务器中
        try {
           //cfile.getFileItem().write(newFile);
            mfile.transferTo(newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //创建excel工作簿
        Workbook workbook = createWorkBook(newFile);
        //得到一个工作表
        Sheet sheet = workbook.getSheetAt(0);
        //验证表头
        if(!validateHeader(sheet)){
            return null;
        }
        //读取工作表
        synLoanVoList = readExcel(sheet);
        return synLoanVoList;
    }

    private static List<SynLoanVo> readExcel(Sheet sheet){
        //获取总行数
        int totalRowNum = sheet.getPhysicalNumberOfRows();

        //是否有除了表头之外的数据
        if(totalRowNum >= 2 && sheet.getRow(0) != null){
            synLoanVoList = new ArrayList<>();
            for(int r = 1;r < totalRowNum ;r++){
                Row row = sheet.getRow(r);
                if (row == null) continue;
                SynLoanVo synLoanVo = new SynLoanVo();
                //循环Excel的列
                for(int c = 0; c < rowHeads.length; c++){
                    Cell cell = row.getCell(c);
                    /*
                     * 判断语句可根据列名的增加做相应增减
                     */
                    if (null != cell) {
                        if(c==0) {
                            synLoanVo.setProjectName(cell.getStringCellValue());//项目名称
                        }else if(c==1) {
                            synLoanVo.setCustomerName(cell.getStringCellValue()); //客户名称
                        }else if(c==2) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)
                            synLoanVo.setEnterpriseProperty(cell.getStringCellValue()); //企业性质
                        }else if(c==3) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)
                            synLoanVo.setHeartBankName(cell.getStringCellValue()); //牵头行
                        }else if(c==4) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)ue()));
                            synLoanVo.setProvince(cell.getStringCellValue()); //省份
                        }else if(c==5) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)
                            synLoanVo.setCity(cell.getStringCellValue()); // 城市
                        }else if(c==6) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)
                            synLoanVo.setStageType(cell.getStringCellValue());//银团分类
                        }else if(c==7) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)
                            synLoanVo.setStatus(cell.getStringCellValue()); //银团阶段
                        }else if(c==8) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)
                            //carInfo.setPrice(Integer.parseInt(cell.getStringCellValue()));
                            synLoanVo.setBusinessVariety(cell.getStringCellValue());// 业务品种
                        }else if(c==9) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)
                            synLoanVo.setCurrency(cell.getStringCellValue()); // 币种
                        }else if(c==10) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)
                            synLoanVo.setTotalAmount(cell.getStringCellValue());// 银团总金额
                        }else if(c==11) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)
                            synLoanVo.setDistributionAmount(cell.getStringCellValue()); // 分销金额
                        }else if(c==12) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)
                            synLoanVo.setInterestRate(cell.getStringCellValue()); // 利率
                        }else if(c==13) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)
                            synLoanVo.setTerm(cell.getStringCellValue()); // 期限
                        }else if(c==14) {
                            synLoanVo.setGuaranteeWay(cell.getStringCellValue()); // 征信措施
                        }else if(c==15) {
                            synLoanVo.setInvestment(cell.getStringCellValue()); // 业务投向
                        }else if(c==16) {
                            cell.setCellType(CellType.STRING); //设置类型(读取类型为String)
                            synLoanVo.setInviteEndDate(cell.getStringCellValue()); // 邀请截止期
                        }else if(c==17) {
                            cell.setCellType(CellType.STRING); //设置类型(读取类型为String)
                            synLoanVo.setPromiseEndDate(cell.getStringCellValue()); // 承诺截止期
                        }else if(c==18) {
                            cell.setCellType(CellType.STRING); //设置类型(读取类型为String)
                            synLoanVo.setContractEndDate(cell.getStringCellValue()); // 协议截止期
                        }else if(c==19) {
                            synLoanVo.setResponsiblePersonId(cell.getStringCellValue()); // 客户经理姓名
                        }else if(c==20) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)
                            synLoanVo.setBankName(cell.getStringCellValue()); //所属支行
                        }else if(c==21) {
                            cell.setCellType(CellType.STRING); //设置类型(读取类型为String)
                            synLoanVo.setMobile(cell.getStringCellValue()); // 电话
                        }else if(c==22) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)
                            //carInfo.setPrice(Integer.parseInt(cell.getStringCellValue())); //起拍价
                            synLoanVo.setEmail(cell.getStringCellValue()); // 邮箱
                        }else if(c==23) {
                            //cell.setCellType(Cell.CELL_TYPE_STRING); //设置类型(读取类型为String)
                            //carInfo.setPrice(Integer.parseInt(cell.getStringCellValue())); //起拍价
                            synLoanVo.setRemark(cell.getStringCellValue()); //项目简介
                        }
                    }
                }
                synLoanVoList.add(synLoanVo); //存放进入list集合
            }
        }
        return synLoanVoList;
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

    /**
     *  @decription 验证上传的文件头部是否与规定的一致
     *  @param  sheet excel表
     *  @return
     */
    public static boolean validateHeader(Sheet sheet){
        boolean tag = true;
        Row row = sheet.getRow(0);
        //验证表头的长度是否和指定的一致
        if(row.getPhysicalNumberOfCells() != rowHeads.length){
            tag = false;
            return tag;
        }
        // 逐行检查每个表头是否正确
        for (int i = 0; i < rowHeads.length; i++) {
            Cell cell = row.getCell(i);
            //判断表头名字是否相符
            if(!getRightTypeCell(cell).toString().equals(rowHeads[i])){
                tag = false;
                return tag;
            }
        }
        return tag;
    }

    /**
     *  @decription  判断上传的文件是不是excel格式
     *  @param filename
     *  @return
     */
    public static boolean validateFile(String filename){
       if(!filename.endsWith(".xls") && !filename.endsWith(".xlsx")){
            return false;
       }
       return true;
    }

    /**
     *  @decription 获取对应单元格数值
     *  @param cell
     *  @return
     */
    public static Object getRightTypeCell(Cell cell){
        Object object = null;
        switch(cell.getCellType()) {
            case STRING : {
                object=cell.getStringCellValue();
                break;
            }
            case NUMERIC : {
                cell.setCellType(CellType.STRING);
                object=cell.getNumericCellValue();
                break;
            }
            case FORMULA : {
                cell.setCellType(CellType.NUMERIC);
                object=cell.getNumericCellValue();
                break;
            }
            case BLANK : {
                cell.setCellType(CellType.BLANK);
                object=cell.getStringCellValue();
                break;
            }
        }
        return object;
    }
}
