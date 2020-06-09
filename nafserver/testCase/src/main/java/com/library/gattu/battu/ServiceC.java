package com.library.gattu.battu;
import com.thinking.machines.nafserver.annotation.*;
@Path("/ServiceC")
public class ServiceC
{
@Path("/whatever")
public String getWhatever()
{
return "Whatever";
}
@Path("/add")
public void add(int a,int b)
{
System.out.println(a+b);
}
@Path("/getProduct")
public int getProduct(int a,int b)
{
return a*b;
}
public int getDiff(int e,int f)
{
return e-f;
}
}