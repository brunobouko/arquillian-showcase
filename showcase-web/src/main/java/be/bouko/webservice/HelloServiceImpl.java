package be.bouko.webservice;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

@WebService(endpointInterface = "be.bouko.webservice.HelloService")
@Addressing(required = true)
@HandlerChain(file="handler-chain.xml")
public class HelloServiceImpl implements HelloService {
    @WebMethod
    public String sayHello(String name) {
        return "hello " + name + " your message id was " + AddressIdThreadLocal.getMessageId();
    }
}
