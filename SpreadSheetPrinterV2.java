import java.util.Arrays;


public class SpreadSheetPrinterV2 {
    public static final int PP = 0;
    public static final int CSV = 1;
    public static int FORMAT;

    public static void main(String[] args){
        if(args.length < 3){ //Chcek lenght of args
            System.out.println("Invalid input: must specify at least a format (csv or pp), as well as the highest column and row");
            return;
        }else{
           args[0] = args[0].toLowerCase();
           if(args[0].equals("pp")){
                FORMAT = PP;
            }else if(args[0].equals("csv")){
                FORMAT = CSV;
            }else{
                System.out.println("The first argument must specify either csv or pp");
                return;
            }
                if(validateRange(args)){ 
                    if(((args.length) % 2) != 0){
                        int lastRow = Integer.parseInt(args[2]);
                        char lastColumn = args[1].charAt(0);
                        args = Arrays.copyOfRange(args, 3, args.length);
                        if(validateAllCellLabels(args, lastColumn, lastRow) == null){
                            if(validateAllCellValues(args) == null){
                                printSpreadSheet(args, lastColumn, lastRow);
                            }else{
                                System.out.println("Invalid cell value: " + validateAllCellValues(args));
                                return;
                            }    
                        }else{
                            System.out.println("Invalid cell label: " + validateAllCellLabels(args, lastColumn, lastRow));
                            return; 
                        }    
                    }else{
                        System.out.println("Invalid input: must specify the format, spreadsheet range, and then cell-value pairs. You entered an even number of inputs");
                        return;
                    } 
                }else{
                    System.out.println("Please specify a valid spreadsheet range, with highest column between A and Z and highest row as an integer > 0");
                    return;
                }  
        }       
    }
                
                                   



    private static void printSpreadSheet(String[] input, char lastColumn, int lastRow){
        printColumnHeaders(lastColumn, lastRow);
        if(FORMAT == PP){  
            for(int numberIterator = 1; numberIterator <= lastRow; numberIterator++){
                System.out.print(numberIterator);
                System.out.print("\t");
                for(char columnIterator = 'A'; columnIterator <= lastColumn; columnIterator++){
                    System.out.print(getCellValue(columnIterator, numberIterator, input));
                    System.out.print("\t");
                }
                if(numberIterator<lastRow){
                    System.out.println();
                }
            }
        }else{
            for(int numberIterator = 1; numberIterator <= lastRow; numberIterator++){
                for(char columnIterator = 'A'; columnIterator <= lastColumn; columnIterator++){
                    if(! getCellValue(columnIterator, numberIterator, input) .equals(" ")){                   
                        System.out.print(getCellValue(columnIterator, numberIterator, input));
                    }
                    if(columnIterator < lastColumn){
                        System.out.print(",");
                    }
                }
                if(numberIterator<lastRow){
                    System.out.println();
                }
            }
        }
        System.out.println();

    }

    public static String getCellValue(char col, int row, String[] input){
        for(int cellCounter = 0; cellCounter < input.length; cellCounter+=2){
            if(isCurrent(col, row, input[cellCounter])){
                return input[cellCounter+1];
            }
        }
        return " ";
    }
        

    public static boolean isCurrent(char col, int row, String cellLabel){
        String str2 = String.valueOf(col) + row;
        if(str2.equals(cellLabel)){
            return true;
        }else{
            return false;
        }
    }


    public static void printColumnHeaders(char lastColumn, int lastRow){
        String lastRowString = String.valueOf(lastRow);
        if(FORMAT == PP){    
            for(int space = 0; space < lastRowString.length(); space++){
                System.out.print(" ");
            }
            for(char sheetHeader = 'A'; sheetHeader <= lastColumn; sheetHeader++){
                System.out.print("\t" + sheetHeader);
            }
        }else{
            for(char sheetHeader = 'A'; sheetHeader <= lastColumn; sheetHeader++){
                System.out.print(sheetHeader);
                if(sheetHeader < lastColumn){
                    System.out.print(",");
                }
            }
           
        }
        System.out.println();

    }


    public static String validateAllCellLabels(String[] input, char lastCol, int lastRow){
        for(int lableCheck = 0; lableCheck < input.length; lableCheck+=2){
            String str = input[lableCheck].substring(1);
            char letter = input[lableCheck].charAt(0);
            if((input[lableCheck].length() < 2) || (letter > lastCol) || (letter < 65) || (getInteger(str) < 1) || (getInteger(str) > lastRow)){
                return input[lableCheck];
            }
        }
        return null;

    }


    public static String validateAllCellValues(String[] input){
        for(int valueCheck = 1; valueCheck < input.length; valueCheck+=2){
            if(isValidDouble(input[valueCheck])){
            }else{
                return input[valueCheck];
            }   
        }
        return null;
    }


    public static boolean validateRange(String[] args){
        if(args[1].length() != 1 ){
            return false;
        }
        char checkLetter = args[1].charAt(0);
        if(checkLetter < 65 || checkLetter > 90){
            return false;

        }else{
            if(getInteger(args[2]) < 1){
                return false;
            }else{
                return true;
            }
        }   
    }
            

    public static int getInteger(String arg){
        try{
            return Integer.parseInt(arg);
        }catch (NumberFormatException e){
            return -1;
        }
        
    }


    public static boolean isValidDouble(String str){
        try{
            Double.parseDouble(str);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }


}
