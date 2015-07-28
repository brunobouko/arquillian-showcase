package be.bouko.repository;

import be.bouko.webservice.HelloService;
import be.bouko.webservice.HelloServiceService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.AddressingFeature;
import java.net.MalformedURLException;
import java.net.URL;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Arquillian.class)
@RunAsClient
public class AsClientRepositoryContainerTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, "be.bouko")
                .addAsResource("persistence-test.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }



    @Test
    public void create_should_persist_a_JpaEntity() throws MalformedURLException, InterruptedException {

    }

    @Test
    public void sayHello_via_webservice_call_should_return_an_answer() throws MalformedURLException {
        URL wsdlURL = getClass().getResource("src/main/resources/wsdl/hello.wsdl");
        HelloServiceService helloServiceService = new HelloServiceService(wsdlURL);
        HelloService helloService = helloServiceService.getHelloService(new AddressingFeature(true, true));
        BindingProvider bp = (BindingProvider)helloService;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://LBNL10270:8181/test/HelloServiceImplService");
        String hello = helloService.sayHello("Bruno");
        assertThat(hello.substring(0, hello.indexOf("uuid"))).isEqualTo("hello Bruno your message id was ");
    }


}
