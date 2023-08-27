# FTPServer

## Description
This is a simple project that involves an FTP Server. In order to run this project you need to have
FTP Server installed & configured an User.

## FTP Server installation
1. Go to filezilla-project.org and download FTP Server
2. Install it
3. Open -> Server -> configure -> Users -> Add -> Type your username & password
4. Under Mount points -> Add -> Virtual Path: "/upload" -> Native Path: "Your path in the C: drive"
5. Apply -> OK

## How to run
1. Clone the repository
2. Open  the project in VS code / IntelliJ
3. Open the FTPHandler file and write your username & password
4. Open Main file -> Un-comment createAFile and create some files.
5. Run the project
6. Play around with the other comments in Main