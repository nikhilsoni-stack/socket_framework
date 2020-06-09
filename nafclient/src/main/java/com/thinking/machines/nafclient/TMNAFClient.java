package com.thinking.machines.nafclient;
import com.thinking.machines.nafcomman.*;
import java.io.*;
import java.net.*;
public class TMNAFClient
{
private String ipAddress;
private int portNumber;
public TMNAFClient(String ipAddress,int portNumber)
{
this.ipAddress=ipAddress;
this.portNumber=portNumber;
}
public Object process(String path,Object... args) throws RuntimeException
{
try
{
Request request=new Request();
request.setPath(path);
request.setArguments(args);
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(request);
oos.flush();
byte requestBytes[]=baos.toByteArray();
int requestSize=requestBytes.length;
byte requestSizeInBytes[]=new byte[4];
requestSizeInBytes[0]=(byte)(requestSize >>24);
requestSizeInBytes[1]=(byte)(requestSize >>16);
requestSizeInBytes[2]=(byte)(requestSize >>8);
requestSizeInBytes[3]=(byte)requestSize;
Socket socket=new Socket(ipAddress,portNumber);
OutputStream os=socket.getOutputStream();
os.write(requestSizeInBytes,0,4);
os.flush();
InputStream is=socket.getInputStream();
byte ack[]=new byte[1];
int byteCount=is.read(ack);
if(ack[0]!=79) throw new RuntimeException("Unable to receive acknowledgement");
int bytesToSend=requestSize;
int chunkSize=1024;
int i=0;
while(bytesToSend>0)
{
 if(bytesToSend<chunkSize) chunkSize=bytesToSend;
os.write(requestBytes,i,chunkSize);
os.flush();
i=i+chunkSize;
bytesToSend-=chunkSize;
}
byteCount=is.read(ack);
if(ack[0]!=79) throw new RuntimeException("Unable to receive acknowledgement");
byte [] responseLengthInBytes=new byte[4];
byteCount=is.read(responseLengthInBytes);
int responseLength;
responseLength=(responseLengthInBytes[0] & 0xFF) << 24 | (responseLengthInBytes[1] & 0xFF) <<
16 | (responseLengthInBytes[2] & 0xFF) << 8 | (responseLengthInBytes[3] & 0xFF);
ack[0]=79;
os.write(ack,0,1);
os.flush();
baos=new ByteArrayOutputStream();
byte chunk[]=new byte[1024];
int bytesToRead=responseLength;
while(bytesToRead>0)
{
byteCount=is.read(chunk);
if(byteCount>0)
{
baos.write(chunk,0,byteCount);
baos.flush();
}
bytesToRead-=byteCount;
}
os.write(ack,0,1);
os.flush();
byte responseBytes[]=baos.toByteArray();
ByteArrayInputStream bais=new ByteArrayInputStream(responseBytes);
ObjectInputStream ois=new ObjectInputStream(bais);
Response responseObject=(Response)ois.readObject();
if(responseObject.getIsException())
{
socket.close();
throw new RuntimeException(responseObject.getException());
}
if(responseObject.getIsError())
{
socket.close();
throw new RuntimeException(responseObject.getError());
}
socket.close();
return responseObject.getResult();
}catch(Exception e)
{
System.out.println(e);
throw new RuntimeException(e.getMessage());
}
///
}
}
