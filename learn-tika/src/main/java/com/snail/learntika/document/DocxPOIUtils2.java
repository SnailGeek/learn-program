package com.snail.learntika.document;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.List;

/*
 * 使用POI库对word进行处理的工具类
 */
public class DocxPOIUtils2 {
    public static void main(String[] args) throws Exception {
        String sourceFile = "F:\\temp\\文档结构化测试\\tika文档测试.docx";
        getAllTable2(sourceFile);
    }

    public static void getAllTable2(String sourceFile) {

        try {
            XWPFDocument docx = new XWPFDocument(new FileInputStream(sourceFile));
            List<XWPFTable> tables = docx.getTables();
            List<XWPFTableRow> rows;
            List<XWPFTableCell> cells;
            for (XWPFTable table : tables) {


                String str = "";
                str += "<table border='1px solid black' cellspacing='0'>";
                // 获取表格对应的行
                rows = table.getRows();
                for (int i = 0; i < rows.size(); i++) {
                    System.out.println("row---" + i);
                    str += "<tr>";
                    // 获取行对应的单元格
                    cells = rows.get(i).getTableCells();
                    int originalIndex = 0;
                    for (int j = 0; j < cells.size(); j++) {
                        System.out.println("cell---" + j);
                        CTVMerge rowspan = cells.get(j).getCTTc().getTcPr().getVMerge();//跨行
                        CTDecimalNumber colspan = cells.get(j).getCTTc().getTcPr().getGridSpan();//跨列
                        originalIndex += colspan != null ? colspan.getVal().intValue() : 1;
                        //------------------
                        int d = 1;//跨行行数//计算是从跨行下一行开始统计的，故加上首行
                        if (rowspan != null) {//rowspan存在未有跨行信息
                            //跨行的第一行为restart，其他行为null
                            STMerge.Enum enum1 = rowspan.getVal();
                            if (enum1 != null && "restart".equals(enum1.toString())) {//发现跨行信息为第一行时进行计算行数，非跨行首行则绕过不处理，已经计算在内

                                for (int k = i + 1; k < rows.size(); k++) {
                                    int tmpIndex = 0;
                                    XWPFTableCell cell = null;
                                    for (XWPFTableCell tableCell : rows.get(k).getTableCells()) {
                                        CTDecimalNumber tmpcolspan = tableCell.getCTTc().getTcPr().getGridSpan();
                                        tmpIndex += tmpcolspan != null ? tmpcolspan.getVal().intValue() : 1;
                                        if (tmpIndex == originalIndex) {
                                            cell = tableCell;
                                            break;
                                        }
                                    }
                                    if (cell != null) {
                                        CTVMerge mer = cell.getCTTc().getTcPr().getVMerge();
                                        if (mer != null) {//只要不为null就是存在连续的跨行
                                            STMerge.Enum enum2 = mer.getVal();
                                            if (enum2 != null && "restart".equals(enum2.toString())) {//若再次遇到restart，为下一次跨行，终止此次
                                                break;
                                            } else {//只要不是restart就加计算
                                                d++;
                                            }
                                        } else {//连续中断，出现null则为此单元格跨行结束
                                            break;
                                        }
                                    }
                                }


//                                for (int k = i + 1; k < rows.size(); k++) {//从当前单元格的跨行首行开始遍历，避免不必要的计算
//
//                                    if (rows.get(k).getTableCells().size() > j) {
//                                        CTVMerge mer = rows.get(k).getTableCells().get(j + 1).getCTTc().getTcPr().getVMerge();
//                                        if (mer != null) {//只要不为null就是存在连续的跨行
//                                            STMerge.Enum enum2 = mer.getVal();
//                                            if (enum2 != null && "restart".equals(enum2.toString())) {//若再次遇到restart，为下一次跨行，终止此次
//                                                break;
//                                            } else {//只要不是restart就加计算
//                                                d++;
//                                            }
//                                        } else {//连续中断，出现null则为此单元格跨行结束
//                                            break;
//                                        }
//                                    } else {
//                                        d++;
//                                    }
//                                }
                            }
                        }
                        //------------------

                        if (colspan != null && rowspan != null) {
                            STMerge.Enum enum1 = rowspan.getVal();
                            if (enum1 != null) {
                                str += "<td colspan='" + colspan.getVal() + "' rowspan='" + d + "'>";
                            }
                        } else if (colspan == null && rowspan != null) {
                            STMerge.Enum enum1 = rowspan.getVal();
                            if (enum1 != null) {
                                str += "<td rowspan='" + d + "'>";
                            }
                        } else if (colspan != null && rowspan == null) {
                            str += "<td colspan='" + colspan.getVal() + "'>";
                        } else if (colspan == null && rowspan == null) {
                            str += "<td>";
                        }
                        str += cells.get(j).getText().trim().replaceAll("[\r\n]", "").toLowerCase();
                        str += "</td>";
                    }
                    str += "</tr>";
                }
                str += "</table>";
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

