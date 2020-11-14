# interpolator4j
A simple expression interpolator written in java

# How to
```java
//Create a scope provider
ScopeProvider provider = new BasicScopeProvider();

//Register you scope implementation (@see Scope interface)
provider.register(new MathScope());//default to 'math' scope id

//Build your interpolator
Interpolator i = provider.build();

//Prepare your expression
String expression = "a quick brown fox jumps over ${math:(5-2)} dogs"; 

//Do interpolate and get evaluated expression
String actual = i.interpolate(expression);

//The expected is
boolean success = "a quick brown fox jumps over 3.0 dogs".equals(actual);

System.out.println("Success: " + success);

```

# A simple mapping Scope

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
  .register(new MathScope())
  .build();

String expression = "a quick brown fox jumps over ${map:3} dogs";
String actual = i.interpolate(expression);
boolean success = "a quick brown fox jumps over three dogs".equals(actual);
//...
```

# A custom Scope

```java
public class CastToLongScope extends AbstractScope {
  public CastToLongScope(String id) {
    super(id);
  }
  @Override 
  protected String doEval(String expression) {
    return Long.toString((long)Double.parseDouble(expression));
  }
}
//...
Interpolator i = provider
  .register(new CastToLongScope("long")) //scope id is 'long'
  .register(map) 
  .register(new MathScope())
  .build();

String expression = "a quick brown fox jumps over ${long:${math:sqrt(9)}} dogs";
String actual = i.interpolate(expression);
boolean success = "a quick brown fox jumps over 3 dogs".equals(actual);
//...
```

# A java bean Scope

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
  .register(new BeanScope("pojo", user)) //scope id is 'pojo'
  .register(new CastToLongScope("long"))
  .register(map)
  .register(new MathScope())
  .build();

String expression = "The user ${pojo:name} lives on ${pojo:address.street} " + 
  "street number ${map:${long:${math:sqrt(9)}}}";

String actual = i.interpolate(expression);

boolean success = "The user John lives on West Main " + 
  "street number tree".equals(actual);
//...
```

# A Supplier Scope

```java
//...
SupplierScope s = new SupplierScope.Builder()
  .map("availableProcessors", Runtime.getRuntime()::availableProcessors)
  .map("totalMemory", Runtime.getRuntime()::totalMemory)
  .map("freeMemory", Runtime.getRuntime()::freeMemory)
  .map("maxMemory", Runtime.getRuntime()::maxMemory)
  .build("runtime");

Interpolator i = provider
  .register(s)
  .register(new CastToLongScope("long"))
  .register(new BeanScope("pojo", user))
  .register(map)
  .register(new MathScope())
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

# Debugging Expressions
```java
//...
String expression = "The user ${pojo:name} lives on ${pojo:address.street} " + 
  "street number ${map:${long:${math:sqrt(9)}} and his macbook " + 
  "has ${runtime:availableProcessors} available processors";

//second argument is debugging output
String actual = i.interpolate(expression, DebugOption.SYSOUT);

boolean success = ("The user John lives on West Main " + 
  "street number tree and his macbook " + 
  "has 4 available processors").equals(actual);
//...
/*
The console output:
  ${pojo:name} -> John
  ${pojo:address.street} -> West Main
  ${math:sqrt(9)} -> 3.0
  ${runtime:availableProcessors} -> 4
  ${long:3.0} -> 3
  ${map:3} -> three
*/
```

# Custom Debugging Output

```java
class FileDebuggingOutput implements DebugMode {
  public FileDebuggingOutput(File file) throws IOException {
    this.out = new PrintWriter(new FileWriter(file));
  }
  @Override //implement this method
  public void debug(String expression, String evaluated) {
    this.out.println(expression + " -> " + evaluated);
  }
}

//output to ./debugging.log file
DebugMode mode = new FileDebuggingOutput(new File("./debugging.log"));

String actual = i.interpolate(expression, mode);
//output to ./debugging.log file
```

# Changing Defaults

```java
ScopeProvider provider = new BasicScopeProvider();

Interpolator i = provider
  .register(new MathScope())
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

# Availables API Scope's

 * AndScope
 * OrScope
 * BeanScope
 * BinaryScope
 * CacheScope
 * ConstScope
 * JavaScriptScope
 * MapScope
 * NashornScope
 * MathScope
 * PrintStreamScope
 * PropertiesScope
 * ScopeWrapper
 * SimpleMapScope
 * SupplierScope
 * SysoutScope

 * DefaultScope.SYSTEM
 * DefaultScope.RUNTIME
 * DefaultScope.LONG
 
