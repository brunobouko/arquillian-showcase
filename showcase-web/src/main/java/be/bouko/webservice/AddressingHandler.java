package be.bouko.webservice;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Iterator;
import java.util.Set;

public class AddressingHandler implements SOAPHandler<SOAPMessageContext> {
    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        SOAPMessage soapMessage = context.getMessage();
        try {
            SOAPHeader header = soapMessage.getSOAPHeader();
            Iterator headerElements = header.getChildElements();
            while (headerElements.hasNext()) {
                SOAPHeaderElement element = (SOAPHeaderElement) headerElements.next();
                if(element.getTagName().equals("MessageID")){
                    AddressIdThreadLocal.setMessageId(element.getValue());
                    break;
                }
            }
        } catch (SOAPException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }
}
