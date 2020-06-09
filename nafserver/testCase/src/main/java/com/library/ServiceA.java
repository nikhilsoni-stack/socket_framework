package com.library;
import com.thinking.machines.nafserver.annotation.*;
@Path("/serviceA")
public class ServiceA
{
@Path("/whatever")
public String getWhatever()
{
return "whatever";
}
@Path("/add")
public void add(int a,int b)
{
System.out.println(a+b);
}
@Path("/product")
public int getProduct(int a,int b)
{
System.out.println(a*b);
return a*b;
}
public int getDiff(int a,int b)
{
return a-b;
}
}