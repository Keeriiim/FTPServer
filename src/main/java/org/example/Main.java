package org.example;

// Settings -> Plugins -> check if maven is installed
// Settings -> build -> build tools -> maven -> use plugin registry
// build tools -> importing -> check maven jdk to mach runner jdk
// Go to maven repository -> https://mvnrepository.com/ -> search for dependency

import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {

         FTPHandler ftpHandler = new FTPHandler();
        // ftpHandler.createFile();
        // ftpHandler.listAllLocalFiles();
        // ftpHandler.listAllServerFiles();
        // ftpHandler.editLocalFile();
        // ftpHandler.deleteFile();
        // ftpHandler.uploadFileToServer();
        // ftpHandler.readLocalFile();
        // ftpHandler.readServerFile();
        // ftpHandler.downloadFileFromServer();
        ftpHandler.downloadAllFilesFromServer();


    }
}

