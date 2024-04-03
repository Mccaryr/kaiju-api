package com.kaiju.kaijuapi.soap_endpoints;

import jakarta.servlet.ServletRegistration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import javax.security.auth.callback.CallbackHandler;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableWs
public class WebServiceConfig extends WsConfigurerAdapter {
    //Message Dispatcher Servlet

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(context);
        messageDispatcherServlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
    }

    @Bean(name="courses")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {
       DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
       definition.setPortTypeName("CoursePort");
       definition.setTargetNamespace("http://soap-example.com/courses");
       definition.setLocationUri("/ws");
       definition.setSchema(coursesSchema);

       return definition;
    }

    @Bean
    public XsdSchema coursesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();

        securityInterceptor.setValidationActions("UsernameToken");
        securityInterceptor.setValidationCallbackHandler(callbackHandler());

        //securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));

        return securityInterceptor;
    }

    @Bean
    public org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler callbackHandler() {
        org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
        Properties users = new Properties();
        users.setProperty("user", "password");
        handler.setUsers(users);
        return handler;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(securityInterceptor());
    }

}
