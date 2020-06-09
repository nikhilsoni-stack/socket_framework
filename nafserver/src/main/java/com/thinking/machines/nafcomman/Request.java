package com.thinking.machines.nafcomman;
public class Request implements java.io.Serializable
{
private String path;
private Object[] arguments;
private String clientId;
public Request()
{ 
this.path=null;
this.arguments=null;
this.clientId=null;
}
public void setPath(String path)
{ this.path=path;
}
public String getPath()
{
return this.path;
}
public void setArguments(Object []arguments)
{ 
this.arguments=arguments;
}
public Object[] getArguments()
{
return this.arguments;
}
public void setClientId(String clientId)
{ 
this.clientId=clientId;
}
public String getClientId()
{
return this.clientId;
}
}