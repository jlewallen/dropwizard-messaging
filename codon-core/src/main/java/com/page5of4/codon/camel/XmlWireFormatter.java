package com.page5of4.codon.camel;

import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;

import java.io.InputStream;
import java.io.OutputStream;

public class XmlWireFormatter implements DataFormat {
    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {

    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
        return null;
    }
    /*
   private Map<Class<?>, JaxbDataFormat> formatters = new HashMap<Class<?>, JaxbDataFormat>();

   @Override
   public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
      Class<?> klass = graph.getClass();
      getFormatter(klass).marshal(exchange, graph, stream);
   }

   @Override
   public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
      Message in = exchange.getIn();
      String messageType = in.getHeader(DefaultCamelTransport.MESSAGE_TYPE_KEY).toString();
      Class<?> klass = Thread.currentThread().getContextClassLoader().loadClass(messageType);
      return getFormatter(klass).unmarshal(exchange, stream);
   }

   private JaxbDataFormat getFormatter(Class<?> klass) throws JAXBException {
      if(!formatters.containsKey(klass)) {
         formatters.put(klass, new JaxbDataFormat(JAXBContext.newInstance(klass)));
      }
      return formatters.get(klass);
   }
   */
}
