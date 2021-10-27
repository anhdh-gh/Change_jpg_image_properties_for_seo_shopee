package thread;

import java.io.*;
import org.apache.commons.imaging.*;
import utils.*;
import view.*;

public class JpgImageFileThread extends Thread {
    private final int index;
    private final File file;
    private final View view;
    private boolean validate;

    public JpgImageFileThread(int index, File file, View view) {
        this.index = index;
        this.file = file;
        this.view = view;
        this.validate = false;
        this.view.addRow(toRow());
    }

    private Object[] toRow() {
        String fileName = file.getName();
        if(!fileName.substring(fileName.length() - 4).equals(".jpg")) {
            return new Object[] {index, file.getName(), "Error" , "File extension is not \".jpg\""};
        }
        
        String[] words = file.getName().split("-");
        if(words.length == 0 || !words[0].matches("^[0-9]+[.][ ].+")) {
            return new Object[] {index, file.getName(), "Error" , "The file name is not in the correct format"};
        }
        
        this.validate = true;
        return new Object[] {index, file.getName(), "" , ""}; 
    }
    
    @Override
    public void run() {
        if(this.validate == true) {
            try {
                JpgImageFileUtils.changeExifMetadata(file, new File(view.getSaveFolder() + "/" + file.getName()));
                view.setStatus(index, "Success");
            } catch (IOException | ImageReadException | ImageWriteException e) {
                view.setStatus(index, "Error");
                view.setNote(index, e.getMessage());
            }            
        }
    }
}
