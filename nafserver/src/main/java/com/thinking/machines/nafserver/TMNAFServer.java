package com.thinking.machines.nafserver;
import com.thinking.machines.nafserver.model.*;
import com.thinking.machines.nafserver.tool.*;
import java.net.*;
import java.io.*;
public class TMNAFServer
{
public  int portNumber;
public Application application;
public TMNAFServer()
{
this.portNumber=5000;
initialize();
}
public void startServer()
{
startServer(this.portNumber);
}
private void initialize()
{
application=ApplicationUtility.getApplication();
}
public void startServer(int portNumber)
{
this.portNumber=portNumber;
try
{
ServerSocket serverSocket=new ServerSocket(this.portNumber);
Socket socket;
while(true)
{
System.out.println("Server is listening at portNumber"+this.portNumber);
socket=serverSocket.accept();
new Processer().startProcesser(socket,application);

}
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
}