package interpolator4j.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import interpolator4j.DebugOption;
import interpolator4j.Interpolator;
import interpolator4j.ScopeProvider;
import interpolator4j.imp.BeanScope;
import interpolator4j.imp.DefaultScopeProvider;
import interpolator4j.imp.MathScope;
import interpolator4j.imp.SimpleMapScope;

public class ReadmeMD {
  
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
  
  @Test
  public void test() {
    User user = new User("John", new Address("West Main"));
    
    ScopeProvider provider = new DefaultScopeProvider();

    Interpolator i = provider
      .register(new MathScope("math"))
      .register(new BeanScope("pojo", user))
      .register(new SimpleMapScope.Builder()
        .map("1", "one")
        .map("2", "two")
        .map("3", "three")
        .build("map"))
      .build();
      
    String expression = "The user ${pojo:name} lives on ${pojo:address.street} " + 
      "street number ${map:${long:${math:sqrt(9)}}} and his macbook " + 
      "has ${runtime:availableProcessors} available processors";

    String actual = i.interpolate(expression, DebugOption.SYSOUT);

    assertEquals(actual, "The user John lives on West Main " + 
      "street number three and his macbook " + 
      "has 4 available processors");
  }
}
