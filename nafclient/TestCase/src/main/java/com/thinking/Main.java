package com.thinking;
import com.thinking.machines.nafclient.*;
import com.thinking.machines.nafcomman.*;
public class Main
{
public static void main(String[] gg)
{
int x;
try
{
TMNAFClient client=new TMNAFClient("localhost",5000);
System.out.println(client.process("/serviceA/add",20,30));
x=(Integer)client.process("/serviceA/product",20,30);
System.out.println(x);
System.out.println(client.process("/serviceA/whatever"));
System.out.println(client.process("/serviceA/add",20,30));

}catch(RuntimeException e)
{
System.out.println(e.getMessage());
}

}
}