package config;

import POJO.Drive;
import POJO.SMS;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.SSHException;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.HostKeyVerifier;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import service.DriveService;
import service.SMSService;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.security.PublicKey;
import java.sql.Date;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"controller", "DAO", "config", "service"})
@EnableScheduling
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    SMSService smsService;
    @Autowired
    DriveService driveService;

    @Bean
    public SSHClient getSSHClient(){
        SSHClient ssh = new SSHClient();
        ssh.addHostKeyVerifier(new PromiscuousVerifier());
        try {
            InetAddress addr = InetAddress.getByName("192.168.1.36");
            ssh.connect(addr, 8022);
            ssh.authPassword("u0_a126", "haslo");

            return ssh;

            //return null;


        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void markDoneDrives(){
        System.out.println("jestem w markDoneDrives");
        List<Drive> list = driveService.listUndone();
        if(list.size()==0) return;

        for(Drive d : list){
            System.out.println("STOp:"+d.getStop());
            System.out.println("currentTime milis: "+System.currentTimeMillis());
            System.out.println("d.getStop Milis: "+d.getStop().getTime());
            System.out.println("d.getDate Milis: "+d.getDate().getTime());
            long asdf = d.getStop().getTime() + d.getDate().getTime();
            System.out.println("getStop + getDate Milis: "+asdf);
            if(System.currentTimeMillis() > d.getMyTimestamp().getTime()){
                d.setDone(Boolean.TRUE);
                driveService.update(d);
            }
        }
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void smsDrives(){
        System.out.println("jestem w smsDrives");
        List<SMS> smsList = smsService.listCheckTime(0);
        if(smsList.size()>=1){
            int i=1;
            for(SMS sms : smsList) {

                try {
                    Session session = getSSHClient().startSession();
                    Session.Command cmd = session.exec("termux-sms-send -n "+sms.getTelephone()+" "+sms.getText());
                    //System.out.println(cmd.getOutputAsString());
                    System.out.println("wysylam chyba: "+sms.getText());
                    if(i==smsList.size()){
                        session.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sms.setSent(true);
                smsService.updateSent(sms);


                i++;
            }
            //session.close();
            //ssh.disconnect();*/
        }


    }

    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addDialect(new SpringSecurityDialect());
        engine.addDialect(new Java8TimeDialect());
        engine.setTemplateResolver(templateResolver);
        return engine;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }

    @Override //nwm
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }



}
