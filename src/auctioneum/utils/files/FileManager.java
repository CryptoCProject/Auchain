package auctioneum.utils.files;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class FileManager {

    public final String filesDirPath = "";

    private File requestedFile;

    public synchronized boolean storeToDisk(String key, String content){
        try {
            this.requestedFile = new File(this.filesDirPath + key + ".json");
            if (!this.requestedFile.exists()) {
                this.requestedFile.createNewFile();
                FileWriter fw = new FileWriter(this.requestedFile);
                fw.write(content);
                fw.flush();
                fw.close();
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    public synchronized String getFile(String key){
        this.requestedFile = new File(this.filesDirPath + key + ".json");
        try {
            if (this.requestedFile.exists()){
                StringBuilder sb = new StringBuilder();
                FileReader fr = new FileReader(this.requestedFile);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line=br.readLine())!= null){
                    sb.append(line);
                }
                br.close();
                fr.close();
                return sb.toString();
            }
            else {
                throw new FileNotFoundException();
            }

        }
        catch (Exception e){
            return null;
        }
    }

    public synchronized boolean fileExists(String key){
        this.requestedFile = new File(this.filesDirPath + key + ".json");
        return this.requestedFile.exists();
    }

    public void removeFiles(List<File> filesToRemove){
        for (File file:filesToRemove){
            file.delete();
        }
    }

    public static void main(String[] args){
        try {
            Socket s = new Socket("zafeiratosv.ddns.net",54321);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject("HEllo mr Zafeiratos!!");
            oos.flush();
            oos.close();
        }catch (Exception e){}

    }


}
