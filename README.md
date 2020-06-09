# socket_framework
# Client Side:
Client side consists of the classes named as NAFCLIENT for connecting to the NAFSERVER. The user of the socket framework will create an object of NAFCLIENT and in that it will pass the IP ADDRESS and the PORT NUMBER. Now for calling any of the method on the server side will call the PROCESS method on the client side and the arguments required to call the process method is PATH and the (OBJECT…  ARGS)  PARAMETER which can accept any number of arguments. Now for sending the request on the server side and for receiving response from the server, generalize objects is created named as REQUEST and RESPONSE.

# REQUEST Object:
It consists of the path, array of the arguments (Object type) and the clientId.
# RESPONSE Object:
It consists of the array of the arguments (Object type), result (Object type), exception, error (String type), isVoid, isError, isException, isSuccessful.
The user will call the PROCESS method on the client side, all the arguments will be wrapped up in the request object. First the size of the request is calculated and converted in bytes and then send it to the server. Then the request is converted into bytes and then sends it to the server along with the checking of acknowledgement on each request send. On Receiving the response, it will again desterilizes into the response object.
# Server Side:
The annotations on the server side are Path, Autowired, Session scope, Application Scope. When the server gets started all the classes and services will be scanned using Reflection API. The classes and the methods with the annotation PATH will be scanned and kept in the data Structure (Hash Map). The key for the data Structure is the PATH followed by the value which is instance of the class. All the information related to the method like the object of the class in which the class information is wrapped up, number of parameters, METHOD class object, return type is void or not, isSessionInjected, isApplicationInjected is wrapped up in the object. The information related to the class is wrapped up in another object. The information of the properties like the name, type will be wrapped in another object. On receiving any request first the path is checked in the data Structure if the path Is matched with the keys in the data structure then the value i.e. the object will be extracted and all the information like the class name, method return type, number of arguments. Now the object of that is created and the appropriate method will be invoked and the result will be wrapped in the response type object.
# How to use.
For your help in client side one testCase folder is created in which your will find the src folder go in that folder and a dummy code is written for your refrence.
Similarly at server side i had create one test case folder in which i had created to some dummy class in whose method had to be called from client side.
for running the server just run the bat file RN.BAT and now run the cliend side code to call the method at server side.
you can change the port number of server by making changes in nafserver\src\main\java\com\thinking\machines\nafserver\TMNAFSERVER.java
and the again build the jar and use it in your project.
