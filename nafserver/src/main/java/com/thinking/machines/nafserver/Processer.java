package com.thinking.machines.nafserver;
import com.thinking.machines.nafcomman.*;
import com.thinking.machines.nafserver.model.*;
import java.lang.reflect.*;
import java.net.*;
import java.io.*;
class Processer extends Thread
{
public Socket client;
public Application application;
public Processer()
{
}
public void startProcesser(Socket client,Application application)
{
this.client=client;
this.application=application;
start();

}
public void run()
{
try
{
InputStream is=null;
OutputStream os;
byte requestLengthInBytes[]=new byte[4];
int requestLength;
int byteCount;
int bytesToRead;
int bytesToWrite;
byte ack[]=new byte[1];
ByteArrayOutputStream baos;
byte requestBytes[];
byte chunk[]=new byte[1024];
ByteArrayInputStream bais;
ObjectInputStream ois;
ObjectOutputStream oos;
byte responseBytes[];
byte responseLengthInBytes[];
int responseLength;
int chunkSize;
String path;
Service service;
Method method;
Class ccc;
Object returnValue=null; 
Object o;
Object arguments[];
int numberOfParameters;
Class<?> params[];
Response response=new Response();
Request request=null;
///// yaha par while ki condition he

System.out.println("Request is arrived.");
is=client.getInputStream();
byteCount=is.read(requestLengthInBytes);
requestLength=(requestLengthInBytes[0] & 0xFF) <<24 | (requestLengthInBytes[1] & 0xFF) <<16 | (requestLengthInBytes[2] & 0xFF) <<8 | (requestLengthInBytes[3] & 0xFF);
ack[0]=79;
os=client.getOutputStream();
os.write(ack,0,1);
os.flush();
baos=new ByteArrayOutputStream();
bytesToRead=requestLength;
while(bytesToRead>0)
{
byteCount=is.read(chunk);
if(byteCount>0)
{
baos.write(chunk,0,byteCount);
}
bytesToRead-=byteCount;
}
ack[0]=79;
os.write(ack,0,1);
os.flush();
requestBytes=baos.toByteArray();
bais=new ByteArrayInputStream(requestBytes);
ois=new ObjectInputStream(bais);
request=(Request)ois.readObject();
path=request.getPath();
try
{
service=application.getService(path);
ccc=service.getModule().getServiceClass();
o=ccc.newInstance();
method=service.getMethod();
returnValue=method.getReturnType();
arguments=request.getArguments();
numberOfParameters=service.getNumberOfParameters();
response.setArguments(arguments);
if(!service.getIsVoid())
{
if(numberOfParameters==0)
{
returnValue=method.invoke(o);
}
else
{
returnValue=method.invoke(o,arguments);
}
}
if(service.getIsVoid())
{
if(numberOfParameters==0)
{
method.invoke(o);
}
else
{
method.invoke(o,arguments);
}
}
response.setResult(returnValue);
response.setIsVoid(returnValue!=null);
response.setIsSuccessful(true);
response.setIsError(false);
response.setIsException(false);
}
catch(ApplicationException ap)
{
response.setResult(null);
response.setIsSuccessful(false);
response.setIsError(false);
response.setIsException(true);
response.setException(ap.getMessage());
}
catch(Exception e)
{
response.setResult(null);
response.setIsSuccessful(false);
response.setIsError(false);
response.setIsException(true);
response.setException(e.getMessage());
}
baos=new ByteArrayOutputStream();
oos=new ObjectOutputStream(baos);
oos.writeObject(response);
oos.flush();
responseBytes=baos.toByteArray();
responseLength=responseBytes.length;
responseLengthInBytes=new byte[4];
responseLengthInBytes[0]=(byte)(responseLength>>24);
responseLengthInBytes[1]=(byte)(responseLength>>16);
responseLengthInBytes[2]=(byte)(responseLength>>8);
responseLengthInBytes[3]=(byte)responseLength;
os.write(responseLengthInBytes,0,4);
os.flush();
byteCount=is.read(ack);
if(ack[0]!=79) throw new RuntimeException("Unable to receive acknowledgement");
bytesToWrite=responseLength;
chunkSize=1024;
int i=0;
while(bytesToWrite>0)
{
 if(bytesToWrite<chunkSize) chunkSize=bytesToWrite;
os.write(responseBytes,i,chunkSize);
os.flush();
i+=chunkSize;
bytesToWrite-=chunkSize;
}
byteCount=is.read(ack);
if(ack[0]!=79) throw new RuntimeException("Unable to receive acknowledgement");
client.close();
}catch(Exception e)
{
System.out.println(e.getMessage());
}

}// run ends
}