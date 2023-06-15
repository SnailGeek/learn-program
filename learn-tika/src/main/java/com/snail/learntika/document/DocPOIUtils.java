package com.snail.learntika.document;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DocPOIUtils {
    public static void parseDoc(InputStream inputStream) {
        StringBuilder text = new StringBuilder();
        try (HWPFDocument document = new HWPFDocument(inputStream)) {
            //区间
            Range range = document.getRange();
            boolean t = true;
            for (int i = 0; i < range.numParagraphs(); i++) {
                //段落
                Paragraph paragraph = range.getParagraph(i);
                String htmlTextTbl = "";
                if (paragraph.isInTable() && t) {
                    t = false;

                    Table table = range.getTable(paragraph);
                    Integer lcm = getLCM(table);

                    Integer maxRowCellCount = maxRowCellCount(table);
                    Integer rowWidth = getRowWidth(table);

                    htmlTextTbl += "<table border>";
                    for (int j = 0; j < table.numRows(); j++) {
                        htmlTextTbl += "<tr>";
                        TableRow row = table.getRow(j);
                        int cellX = 0;

                        for (int k = 0; k < row.numCells(); k++) {

                            TableCell cell = row.getCell(k);
                            String s = cell.text().toString().trim();

//                                htmlTextTbl += "<td";
//                            int colspan = (int) Math.ceil((((double) cell.getWidth()) * ((double) maxRowCellCount)) / ((double) rowWidth));
                            int colspan = (int) (lcm * (((double) cell.getWidth()) / ((double) rowWidth)));

                            Integer rowspan = getRowspan(cell, j, k, table, cellX);
                            cellX += cell.getWidth();
                            if (colspan > 1 && !cell.isVerticallyMerged()) {
                                htmlTextTbl += "<td" + " colspan='" + colspan + "'>";
                            } else if (colspan <= 1 && cell.isVerticallyMerged()) {
                                if (cell.isFirstVerticallyMerged()) {
                                    htmlTextTbl += "<td" + " rowspan='" + rowspan + "'>";
                                }
                            } else if (colspan > 1 && cell.isVerticallyMerged()) {
                                if (cell.isFirstVerticallyMerged()) {
                                    htmlTextTbl += "<td" + " colspan='" + colspan + "' " + " rowspan='" + rowspan + "'>";
                                }
                            } else if (colspan <= 1 && !cell.isVerticallyMerged()) {
                                htmlTextTbl += "<td>";
                            }

                            htmlTextTbl += s;

                            htmlTextTbl += "</td>";


                        }
                        htmlTextTbl += "</tr>";
                    }
                    htmlTextTbl += "</table>";
                    System.out.println(htmlTextTbl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(text);
    }

    public static Integer getRowWidth(Table table) {
        int width = 0;
        for (int i = 0; i < table.getRow(0).numCells(); i++) {
            width += table.getRow(0).getCell(i).getWidth();
        }
        return width;
    }

    public static Integer maxRowCellCount(Table table) {
        int count = 0;
        for (int i = 0; i < table.numRows(); i++) {
            TableRow row = table.getRow(i);
            if (row.numCells() > count) {
                count = row.numCells();
            }
        }
        return count;
    }

    public static Integer getLCM(Table table) {
        List<Integer> array = new ArrayList<>();
        for (int i = 0; i < table.numRows(); i++) {
            array.add(table.getRow(i).numCells());
        }
        return getLCM(array);
    }

    public static int getLCM(List<Integer> arr) {
        int lcm = arr.get(0);
        for (int i = 1; i < arr.size(); i++) {
            lcm = getLCM(lcm, arr.get(i));
        }
        return lcm;
    }

    public static int getLCM(int a, int b) {
        return a * b / getGCD(a, b);
    }

    public static int getGCD(int a, int b) {
        if (b == 0)
            return a;
        else {
            return getGCD(b, a % b);
        }
    }


    public static Integer getRowspan(TableCell cell, int rowNum, int colNum, Table table, int cellX) {
        int rowSpan = 1;
        if (cell.isFirstVerticallyMerged()) {
            for (int i = rowNum + 1; i < table.numRows(); i++) {
                TableRow row = table.getRow(i);
                int tmpCellX = 0;
                for (int j = 0; j < row.numCells(); j++) {
                    TableCell tableCell = row.getCell(j);
                    if (tmpCellX == cellX) {
                        if (tableCell.isVerticallyMerged() && !tableCell.isFirstVerticallyMerged()) {
                            rowSpan++;
                            break;
                        } else {
                            return rowSpan;
                        }
                    }
//                    if (tmpCellX == cellX && tableCell.isVerticallyMerged() && !tableCell.isFirstVerticallyMerged()) {
//                        rowSpan++;
//                        break;
//                    }
                    tmpCellX += tableCell.getWidth();
                }
            }
        }
        return rowSpan;
    }

    public static Integer getColspan(TableCell cell, int rowNum, int colNum, Table table) {
        int colSpan = 0;
        if (cell.isMerged()) {
            if (cell.isFirstMerged()) {
                for (int i = colNum + 1; i < table.numRows(); i++) {
                    TableCell tableCell = table.getRow(rowNum).getCell(i);
                    if (tableCell.isFirstMerged() || !tableCell.isMerged()) {
                        colSpan = i - colNum;
                        break;
                    } else if (i == table.numRows() - 1) {
                        colSpan = i - colNum + 1;
                    }
                }
            }
        }
        return colSpan;
    }

    public static void readTable(InputStream stream) throws Exception {

        try (HWPFDocument document = new HWPFDocument(stream)) {
            Range range = document.getRange();
            TableIterator it = new TableIterator(range);
            String htmlTextTbl = "";
            //迭代文档中的表格

            while (it.hasNext()) {
                htmlTextTbl = "";
                Table tb = (Table) it.next();


                htmlTextTbl += "<table border>";
                for (int i = 0; i < tb.numRows(); i++) {
                    TableRow tr = tb.getRow(i);

                    htmlTextTbl += "<tr>";
                    //迭代列，默认从0开始
                    for (int j = 0; j < tr.numCells(); j++) {
                        TableCell td = tr.getCell(j);//取得单元格
                        int cellWidth = td.getWidth();

                        //取得单元格的内容
                        for (int k = 0; k < td.numParagraphs(); k++) {
                            Paragraph para = td.getParagraph(k);
                            String s = para.text().toString().trim();
                            if (s == "") {
                                s = " ";
                            }
                            htmlTextTbl += "<td width=" + cellWidth + ">" + s + "</td>";
                        }
                    }
                }
                htmlTextTbl += "</table>";
            } //end while
            System.out.println(htmlTextTbl);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws Exception {
        String sourceFile = "F:\\temp\\文档结构化测试\\图片可以用文档测试2.doc";
        parseDoc(new FileInputStream(sourceFile));
//        readTable(new FileInputStream(sourceFile));
    }
}
