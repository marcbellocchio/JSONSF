import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
 
 // http://www.programcreek.com/2009/02/java-convert-a-file-to-byte-array-then-convert-byte-array-to-a-file/
 
public class genFile {
 
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File("java.pdf");
 
        FileInputStream fis = new FileInputStream(file);
        //System.out.println(file.exists() + "!!");
        //InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            Logger.getLogger(genJpeg.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] bytes = bos.toByteArray();
 
        //below is the different part
        File someFile = new File("java2.pdf");
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }
}