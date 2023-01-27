package com.socks.coursework3_webapplicationsocks.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SocksFileService  {

    @Value("${path.to.socksListJson.file}")
    private String socksListFilePath;

    @Value("${name.of.socksListJson.file}")
    private String socksListFileName;

    public boolean cleanSocksListJson(){
        try {
            Path path = Path.of(socksListFilePath, socksListFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public File getSocksListJson() {
        return new File(socksListFilePath + "/" + socksListFileName);
    }

    public boolean saveSocksListToJsonFile(String json) {
        try {
            cleanSocksListJson();
            Files.writeString(Path.of(socksListFilePath, socksListFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String readSocksListFromJsonFile(){
        String result;
        if (Files.exists(Path.of(socksListFilePath, socksListFileName))) {
            try {
                result = Files.readString(Path.of(socksListFilePath, socksListFileName));
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getSocksListFilePath() {
        return socksListFilePath;
    }

    public String getSocksListFileName() {
        return socksListFileName;
    }
}
