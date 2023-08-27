package org.example;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;


public class FTPHandler {

    String server = "localhost";
    int port = 21;
    String username = "Kerim";
    String password = "Kerimadmin12";
    String virtualPath = "/upload";

    // sökvägen till den lokala filen, denna funktionen heter relativ sökväg & är bättre än absolut sökväg då. Man slipper permission issues (c:/users/kerim/...) & programmet fattar vilken user man använder då den letar efter current projekt.
    // Annat alternativ är att använda String userHome = System.getProperty("user.home");
    String localFilePath = "src/main/resources/";
    Scanner scan;
    String fileName;



    public void createFile(){ // Creates a file in the directory
        this.scan = new Scanner(System.in); //
        System.out.println("Name the text file that you want to create: " );
        this.fileName = scan.nextLine();

        try {
            File file = new File(localFilePath,fileName + ".txt") ; // Creates a new file object with the path to the directory
            if(!file.exists()){ // Checks if the file already exists
                file.createNewFile(); // Creates a new file
                System.out.println("File created successfully!");

                System.out.println("Do you want to write to the file? (Y/N)");
                String answer = scan.nextLine();

                if (answer.toUpperCase().equals("Y")){ // If the user wants to write to the file
                    FileWriter writer = new FileWriter(file); // Creates a new file writer with the file that the user has created
                    System.out.println("Write to the file: ");
                    String text = scan.nextLine();
                    writer.write(text); // Writes the text that the user has given to the file
                    writer.close(); // Closes the file writer because we don't need it anymore
                    System.out.println("Successfully wrote to the file.");
                }
            }
            else {
                System.out.println("File already exists!");
            }

        } catch (Exception e) { // Catches the exception if something goes wrong with the file creation
            System.out.println("Error: " + e);
        }
    }
    public void listAllLocalFiles(){ // List all files in the directory
         File file = new File(localFilePath); // Creates a new file object with the path to the directory
         System.out.println("There are currently " + file.listFiles().length + " files in the local directory: "); // Lists the amount of files in the directory
         for (File f : file.listFiles()) { // Lists all the files in the directory
              if (f.isFile()) {
                  System.out.println(f.getName());
              }
         }
    }

    public void listAllServerFiles(){
        FTPClient ftpClient = new FTPClient(); // Creates a new FTPClient object that can connect to a FTP server and upload/download files

        try {
            ftpClient.connect(server, port); // Connects to the FTP server
            ftpClient.login(username, password); // Logs in to the FTP server
            ftpClient.changeWorkingDirectory(virtualPath); // Changes the working directory to the virtual path that the user has chosen

            System.out.println("There are currently " + ftpClient.listFiles().length + " files in the server directory: "); // Lists the amount of files in the directory
            for (org.apache.commons.net.ftp.FTPFile f : ftpClient.listFiles()) { // Lists all the files in the directory
                if (f.isFile()) {
                    System.out.println(f.getName());
                }
            }
            ftpClient.logout(); // Log out from the FTP server
            ftpClient.disconnect(); // Close the connection to the FTP server
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    public void editLocalFile(){
        this.scan = new Scanner(System.in);
        listAllLocalFiles();
        System.out.println("Which file do you want to rewrite? (Don't forget to type .txt)");
        this.fileName = scan.nextLine();
        // pick the file

        File file = new File(localFilePath+fileName);

        try {
            Desktop desktop = Desktop.getDesktop(); // Creates a new desktop object that can open files on the computer
            if (file.exists()) {
                desktop.open(file); // Opens the file that the user has chosen with the default program for that file type (Notepad for .txt files)
                System.out.println("File has been successfully opened");
            } else {
                System.out.println("File not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void deleteLocalFile() {
        this.scan = new Scanner(System.in);
        listAllLocalFiles();
        System.out.println("Which file do you want to delete? (Don't forget to type .txt)");
        this.fileName = scan.nextLine();

        try {
            File file = new File(localFilePath + fileName); // Creates a new file with the name that the user has given
            if (file.exists()) {
                file.delete(); // Deletes the file that the user has chosen
                System.out.println("File deleted successfully");
            } else {
                System.out.println("File not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void deleteServerFile(){
        this.scan = new Scanner(System.in);
        try {
            FTPClient ftpClient = new FTPClient(); // Creates a new FTPClient object that can connect to a FTP server and upload/download files
            ftpClient.connect(server, port); // Connects to the FTP server
            ftpClient.login(username, password); // Logs in to the FTP server
            ftpClient.changeWorkingDirectory(virtualPath); // Changes the working directory to the virtual path that the user has chosen

            listAllServerFiles();
            System.out.println("Which file do you want to delete? (Don't forget to type .txt)");
            String fileName = scan.nextLine();


            // Check if the file exists before attempting to delete
            FTPFile[] serverFiles = ftpClient.listFiles(); // List files in the current directory
            boolean fileFound = false;

            for (FTPFile serverFile : serverFiles) {
                if (serverFile.getName().equals(fileName)) {
                    fileFound = true;
                    break;
                }
            }

            if (fileFound) {
                if (ftpClient.deleteFile(fileName)) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("File deletion failed.");
                }
            }
            else {
                System.out.println("File not found.");
            }
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }

    }



    // Read for local
    // Read from Server // retrieve from server

public void readLocalFile(){
        scan = new Scanner(System.in);
        listAllLocalFiles();
    System.out.println("Which file do you want to read? (Don't forget to type .txt)");
    this.fileName = scan.nextLine();
    String currentLocalFilePath = localFilePath + fileName; // Creates a new string with the path to the file that the user has chosen
    File file = new File(currentLocalFilePath);
    if(file.exists()){

        try {
            // FileOutputStream outputStream = new FileOutputStream(file);
             Scanner readFile = new Scanner(file); // Create a new scanner object that can read the file that the user has chosen
            while(readFile.hasNextLine()){ // While there is a next line in the file
                System.out.println(readFile.nextLine()); //  Print the next line in the file
            }
            readFile.close();
            scan.close();
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

}

public void readServerFile() {
    this.scan = new Scanner(System.in);
    FTPClient ftpClient = new FTPClient(); // Creates a new FTPClient object that can connect to a FTP server and upload/download files

    try {
        ftpClient.connect(server, port); // Connects to the FTP server
        ftpClient.login(username, password); // Logs in to the FTP server
        ftpClient.changeWorkingDirectory(virtualPath); // Changes the working directory to the virtual path that the user has chosen
        listAllServerFiles();
        System.out.println("Which file do you want to read? (Don't forget to type .txt)");
        String fileName = scan.nextLine();

        // Check if the file exists before attempting to delete
        FTPFile[] serverFiles = ftpClient.listFiles(); // List files in the current directory
        boolean fileFound = false;

        for (FTPFile serverFile : serverFiles) { // Lists all the files in the directory and checks if the file that the user has chosen exists
            if (serverFile.getName().equals(fileName)) { // If the file exists then set fileFound to true
                fileFound = true;
                System.out.println("File found");
                InputStream inputStream = ftpClient.retrieveFileStream(fileName); // Retrieve the file as an InputStream, meaning that we can read the file line by line
                if (inputStream != null) { // If the file exists
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)); // Create a new BufferedReader object that can read the file line by line from the InputStream
                    String line; // Create a new string that will hold the current line of the file
                    while ((line = reader.readLine()) != null) { // While there is a next line in the file
                        System.out.println(line); // Print each line of the file
                    }
                    reader.close(); // Close the BufferedReader because we don't need it anymore
                    inputStream.close(); // Close the InputStream because we don't need it anymore
                } else {
                    System.out.println("Error retrieving the file.");
                }
                break;
            }
        }

        if (!fileFound) {
            System.out.println("File not found");
        }

        ftpClient.logout(); // Log out from the FTP server
        ftpClient.disconnect(); // Close the connection to the FTP server
        scan.close();

    } catch (Exception e) {
        System.out.println("Error: " + e);
    }
}



    public void uploadFileToServer(){
        this.scan = new Scanner(System.in);
        FTPClient ftpClient = new FTPClient(); // Creates a new FTPClient object that can connect to a FTP server and upload/download files

       try {
           ftpClient.connect(server, port); // Connects to the FTP server
           ftpClient.login(username, password); // Logs in to the FTP server
           ftpClient.changeWorkingDirectory(virtualPath); // Changes the working directory to the virtual path that the user has chosen

       }
         catch (Exception e){
              System.out.println("Error: " + e);
       }


        listAllLocalFiles(); // List all files in the directory
        System.out.println("--------------------------------");
        System.out.println("Do you want to: \n 1. Upload all files \n 2. Upload one file ");
        System.out.println("!! CAUTION !! - Existing files on the server of same name will be overwritten and you will not be able to recover them");
        int input = scan.nextInt();

        switch (input){

            case 1:
                try {
                    File file = new File(localFilePath); // Creates a new file object with the path to the directory
                    FileInputStream inputStream = null; // Creates a new file input stream object that can read the file that the user has chosen
                    System.out.println("Uploading all files...");

                    for (File f : file.listFiles()) { // Lists all the files in the directory
                        if (f.isFile()) {
                            System.out.println("Uploading " + f.getName());
                            String currentLocalFilePath = localFilePath + f.getName(); // Creates a new string with the path to the file that the user has chosen
                            inputStream = new FileInputStream(currentLocalFilePath);
                            ftpClient.storeFile(f.getName(), inputStream); // Uploads the file that the user has chosen
                            System.out.println("Uploaded " + f.getName() + " successfully");
                        }

                    }
                    inputStream.close(); // Close the input stream because we don't need it anymore
                    ftpClient.logout(); // Log out from the FTP server
                    ftpClient.disconnect(); // Close the connection to the FTP server
                }
                catch (Exception e){
                    System.out.println("Error: " + e);
                }
                System.out.println("All files uploaded successfully");

                break;


            case 2:
                System.out.println("Which file do you want to upload? (Don't forget to type .txt)");
                scan.nextLine();
                this.fileName = scan.nextLine();
                String currentLocalFilePath = localFilePath + fileName; // Creates a new string with the path to the file that the user has chosen
                try {
                    File file = new File(currentLocalFilePath); // Chooses the file that the user has chosen
                    if (file.exists()) {
                        FileInputStream inputStream = new FileInputStream(currentLocalFilePath); // Creates a new file input stream object that can read the file that the user has chosen
                        ftpClient.storeFile(fileName,inputStream); // Uploads the file that the user has chosen

                        inputStream.close(); // Close the input stream because we don't need it anymore
                        ftpClient.logout(); // Log out from the FTP server
                        ftpClient.disconnect(); // Close the connection to the FTP server
                        System.out.println("File uploaded successfully");
                    } else {
                        System.out.println("File not found.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                }
                break;

            default: System.exit(0);
        }
    }

    public void downloadFileFromServer(){
        scan = new Scanner(System.in);
        try {
            FTPClient ftpClient = new FTPClient(); // Creates a new FTPClient object that can connect to a FTP server and upload/download files
            ftpClient.connect(server, port); // Connects to the FTP server
            ftpClient.login(username, password); // Logs in to the FTP server
            ftpClient.changeWorkingDirectory(virtualPath); // Changes the working directory to the virtual path that the user has chosen

            listAllServerFiles(); // List all files in the directory
            System.out.println("Which file do you want to download? (Don't forget to type .txt)");
            String fileName = scan.nextLine();

            // Download the file
            FileOutputStream output = new FileOutputStream(localFilePath+fileName); // Creates a new file output stream object that can write to the file that the user has chosen
            ftpClient.retrieveFile(fileName, output); // Downloads the file that the user has chosen and writes it to the file output stream object

            // Disconnect
            output.close(); // Closes the output stream
            ftpClient.logout(); // Log out from the FTP server
            ftpClient.disconnect(); // Disconnects from the FTP server
            scan.close(); // Closes the scanner
            System.out.println("The file has been downloaded successfully");

        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void downloadAllFilesFromServer() {
        FTPClient ftpClient = new FTPClient(); // Creates a new FTPClient object that can connect to a FTP server and upload/download files
        try {
            ftpClient.connect(server, port); // Connects to the FTP server
            ftpClient.login(username, password); // Logs in to the FTP server
            ftpClient.changeWorkingDirectory(virtualPath); // Changes the working directory to the virtual path that the user has chosen


        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }

}
