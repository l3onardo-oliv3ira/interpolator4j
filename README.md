# interpolator4j
A simple expression interpolator written in java

## How to
```java
//Create a scope provider
ScopeProvider provider = new BasicScopeProvider();

//Register a scope implementation (@see Scope interface)
provider.register(new MathScope("math"));//default to 'math' scope id

//Build an interpolator
Interpolator i = provider.build();

//Prepare an expression
String expression = "a quick brown fox jumps over ${math:(5-2)} dogs"; 

//Interpolate and get the evaluated expression
String actual = i.interpolate(expression);

//The expected is (math scope eval to double)
boolean success = "a quick brown fox jumps over 3.0 dogs".equals(actual);

System.out.println("Success: " + success);

```

## A simple mapping scope

```java
//...
Scope map = new SimpleMapScope.Builder()
  .map("1", "one")
  .map("2", "two")
  .map("3", "three")
  .build("map"); //<-- This is your scope id 'map'
 
//We can register multiples scope's (ex: 'map' and 'math')
Interpolator i = provider 
  .register(map) 
  .register(new MathScope("math")) //This is the scope id 'math'
  .build();

String expression = "a quick brown fox jumps over ${map:3} dogs";
String actual = i.interpolate(expression);
boolean success = "a quick brown fox jumps over three dogs".equals(actual);
//...
```

## A custom scope

```java
public class CastToLongScope extends AbstractScope {
  public CastToLongScope(String id) {
    super(id);
  }
  @Override 
  protected String doEval(String expression) {
    return Long.toString((long)Double.parseDouble(expression)); //eval to long
  }
}
//...
Interpolator i = provider
  .register(new CastToLongScope("long")) //Here scope id is 'long'
  .register(map) 
  .register(new MathScope("math"))
  .build();

String expression = "a quick brown fox jumps over ${long:${math:sqrt(9)}} dogs";
String actual = i.interpolate(expression);
boolean success = "a quick brown fox jumps over 3 dogs".equals(actual);
//...
```

## A java bean scope

```java
//...
class Address {
  private String street;
  public Address(String street){ this.street = street;}
  public String getStreet() { return street;}
}
class User {
  private String name;
  private Address address;
  public User(String name, Address address){ 
    this.name = name; 
    this.address = address; 
  }
  public String getName() { return this.name;}
  public Address getAddress() { return this.address;}
}

//...
User user = new User("John", new Address("West Main"));

Interpolator i = provider
   //scope id below is 'pojo' (you might want to rename it to 'user' instead) 
  .register(new BeanScope("pojo", user)) 
  .register(new CastToLongScope("long"))
  .register(map)
  .register(new MathScope("math"))
  .build();

String expression = "The user ${pojo:name} lives on ${pojo:address.street} " + 
  "street number ${map:${long:${math:sqrt(9)}}}";

String actual = i.interpolate(expression);

boolean success = ("The user John lives on West Main " + 
  "street number tree").equals(actual);

```

## A supplier scope

```java
//...
SupplierScope rt = new SupplierScope.Builder()
  .map("availableProcessors", Runtime.getRuntime()::availableProcessors)
  .map("totalMemory", Runtime.getRuntime()::totalMemory)
  .map("freeMemory", Runtime.getRuntime()::freeMemory)
  .map("maxMemory", Runtime.getRuntime()::maxMemory)
  .build("runtime");

Interpolator i = provider
  .register(rt)
  .register(new CastToLongScope("long"))
  .register(new BeanScope("pojo", user))
  .register(map)
  .register(new MathScope("math"))
  .build();
  
String expression = "The user ${pojo:name} lives on ${pojo:address.street} " + 
  "street number ${map:${long:${math:sqrt(9)}}} and his macbook " + 
  "has ${runtime:availableProcessors} available processors";

String actual = i.interpolate(expression);

boolean success = ("The user John lives on West Main " + 
  "street number three and his macbook " + 
  "has 4 available processors").equals(actual);
//...
```

## Debugging expressions
```java
//...
String expression = "The user ${pojo:name} lives on ${pojo:address.street} " + 
  "street number ${map:${long:${math:sqrt(9)}} and his macbook " + 
  "has ${runtime:availableProcessors} available processors";

//second argument is debugging instance output
String actual = i.interpolate(expression, DebugOption.SYSOUT);

boolean success = ("The user John lives on West Main " + 
  "street number tree and his macbook " + 
  "has 4 available processors").equals(actual);

/*
The console output will be:
  ${pojo:name} -> John
  ${pojo:address.street} -> West Main
  ${math:sqrt(9)} -> 3.0
  ${runtime:availableProcessors} -> 4
  ${long:3.0} -> 3
  ${map:3} -> three
*/
```

## Custom debugging output

```java
class FileDebuggingOutput implements DebugMode {
  public FileDebuggingOutput(File file) throws IOException {
    this.out = new PrintWriter(new FileWriter(file));
  }
  @Override //Reimplement this method if you want
  public void debug(String expression, String evaluated) {
    this.out.println(expression + " -> " + evaluated);
  }
}

//output to ./debugging.log file
DebugMode mode = new FileDebuggingOutput(new File("./debugging.log"));

String actual = i.interpolate(expression, mode);
//...
```

## Changing defaults

```java
ScopeProvider provider = new BasicScopeProvider();

Interpolator i = provider
  .register(new MathScope("math"))
  .build(DefaultCharConfig.HASH_BRACKETS); //config to #[]. See options below

String expression = "Interpolator version #[math:(9-8)] avaiable";
String actual = i.interpolate(expression);
boolean success = "Interpolator version 1.0 available".equals(actual);

/*
Options:

  DefaultCharConfig.DOLLAR_BRACES    //set ${}
  DefaultCharConfig.DOLLAR_BRACKETS  //set $[]
  DefaultCharConfig.HASH_BRACKETS    //set #[]
  DefaultCharConfig.HASH_BRACES      //set #{}
  DefaultCharConfig.AT_BRACKETS      //set @[]
  DefaultCharConfig.AT_BRACES        //set @{}
  DefaultCharConfig.AND_BRACKETS     //set &[]
  DefaultCharConfig.AND_BRACES       //set &{}
  DefaultCharConfig.PERCENT_BRACKETS //set %[]
  DefaultCharConfig.PERCENT_BRACES   //set %{}
*/

```

## Available scope API
 
 * AndScope
 ```java
    "${and:exp1,exp2}"              -> (exp1) AND (exp2)
    "(${and:exp1,exp2,exp3})"       -> ((exp1) AND (exp2) AND (exp3))
    "${and:field;exp1,exp2}"        -> (field:"exp1") AND (field:"exp2") //Lucene sintaxe integration
    "(${and:field;exp1,exp2;exp3})" -> ((field:"exp1") AND (field:"exp2") AND (field:"exp3"))
 ```
 * OrScope
 ```java
    "${or: exp1,exp2}"             -> (exp1) OR (exp2)
    "(${or:exp1,exp2,exp3})"       -> ((exp1) OR (exp2) OR (exp3))
    "${or:field;exp1,exp2}"        -> (field:"exp1") OR (field:"exp2")  //Lucene sintaxe integration
    "(${or:field;exp1,exp2;exp3})" -> ((field:"exp1") OR (field:"exp2") OR (field:"exp3"))
  ```
 * BeanScope
 ```java
    User user = new User("John", "Kennedy");
    "Hello ${user:name} ${user:surname}" ->  "Hello John Kennedy"
 ```
 * CacheScope
  ```java
    Interpolator i = provider.register(new CacheScope(new MathScope("math"))).build();
    String e1 = "Number ${math:(3+2*sqrt(9)}";
    String e2 = i.interpolate(e1); //eval to "Number 9.0"

    //Expression 3+2*sqrt(9) was cached, so it wil not eval again 
    //(sqrt will not invoked more than 1 time, for example)
    String e3 = i.interpolate(e1); 
  ```
 * ConstScope
  ```java
    Interpolator i = provider.register(new ConstScope("const", "World")).build();
    String e1 = "Hello ${const:Whatever}";
    
    //eval to "Hello World" (Whatever is ignored and replaced to "World")
    String e2 = i.interpolate(e1);
  ``` 
 * DefaultScope.SYSTEM
  ```java
    Interpolator i = provider.register(DefaultScope.SYSTEM).build();
    "Hello ${system:user.home}" -> "Hello /home/user" //eval to System.getProperty("user.home", "")
  ```
 * PropertiesScope
  ```java
    Properties properties = ....
    Interpolator i = provider.register(new PropertiesScope("prop", properties)).build();
    "Hello ${prop:key}" -> "Hello World" //eval to properties.getProperty("key", "");
  ```
 * JavaScriptScope
  ```java
    String code = "function sum(a, b) { return a + b; } sum(3,4);";
    Interpolator i = getBasicProvider()
      .register(new ConstScope("code", code))
      .register(new JavaScriptScope("js"))
      .build(DefaultCharConfig.HASH_BRACKETS); //prevent braces conflicts with javascript code! 
    String e1 = "Result is #[js:#[code:source]]";
    String actual = i.interpolate(e1);
    String expected = "Result is 7.0";
  ```
 * SysoutScope
  ```java
    "Hello ${sysout:World}" -> "Hello World"
    /*
    The console output will be:
      World
    */
  ```
 * DefaultScope.RUNTIME
  ```java
    Interpolator i = provider.register(DefaultScope.RUNTIME).build();
    "Available processors: ${runtime:availableProcessors}" -> "Available processors: 4"
    "Total memory: ${runtime:totalMemory}" -> "Total memory: 567892" 
    "Free memory: ${runtime:freeMemory}"  -> "Free memory: 8927836"
    "Max memory: ${runtime:maxMemory}"   -> "Max memory: 1225612"
  ```
 * DefaultScope.LONG
 ```java
    Interpolator i = provider
      .register(DefaultScope.LONG)
      .register(new MathScope())
      .build();
    "sqrt(9) is ${long:${math:sqrt(9)}}" -> "sqrt(9) is 3" //cast double 3.0 to long 3
 ```
 * MathScope
 * BinaryScope (base class api used by AndScope and OrScope)
 * ScopeWrapper (base class api)
 * MapScope (base class api for custom implementation Mapping Scope)
 * SimpleMapScope
 * SupplierScope
 * PrintStreamScope
 ```
 
